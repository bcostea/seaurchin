package ro.seaurchin.websurvey.support;

import org.springframework.web.servlet.FrameworkServlet;
import org.hibernate.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ro.seaurchin.websurvey.support.dao.ChestionarDao;
import ro.seaurchin.websurvey.support.util.HibernateUtil;
import ro.seaurchin.websurvey.support.rezultate.Rezultat;
import ro.seaurchin.websurvey.support.rezultate.SetRezultat;

import java.util.Map;
import java.util.Iterator;
import java.sql.SQLException;

/**
 * User: BogdanCo
 * Date: Aug 25, 2006
 * Time: 3:20:55 PM
 */
public class FormDataProcessor extends FrameworkServlet
{
    ChestionarDao chestionarDao;
    private SessionFactory factory= HibernateUtil.getSessionFactory();
    private String whereShouldIGo = "chestionare.html";

    protected void doService(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception
    {
        Map date = httpServletRequest.getParameterMap();
        whereShouldIGo=useData(date);
        httpServletResponse.sendRedirect(whereShouldIGo);
    }

    private String useData(Map date)
    {
        long stage = Long.parseLong(((String[]) date.get("stage"))[0]);
        long chestionar = Long.parseLong(((String[]) date.get("idChestionar"))[0]);

        switch((int) stage)
        {
            case 4:
                Long id = saveThisResultSet(date);
                whereShouldIGo = "process-chestionar.html?c=" + chestionar + "&sr="+id +"&stage="+(stage+1);
                break;
            case 5:
                whereShouldIGo = "chestionare.html";
                saveDateChestionar(date);
                break;
        }
        return whereShouldIGo;
    }

    private Long saveThisResultSet(Map date)
    {
        String judetSelectat=getValueFromMap("judetSelectat",date);
        String localitateSelectata=getValueFromMap("localitateSelectata",date);
        String unitateSelectata=getValueFromMap("unitateSelectata",date);
        String seria=getValueFromMap("seria",date);
        String etapa=getValueFromMap("etapa",date);
        String user = getValueFromMap("user", date);
        String idChestionar=getValueFromMap("idChestionar",date);
        String act =getValueFromMap("act",date);

        SetRezultat setr;
            Session s = factory.openSession();
            Transaction tx = s.beginTransaction();

            if(act.equals("Salveaza si treci la pasul urmator")){
                SetRezultat sr = new SetRezultat();
                sr.setJudet(judetSelectat);
                sr.setUtilizator(user);
                sr.setLocalitate(localitateSelectata);
                sr.setUnitate(unitateSelectata);
                sr.setSerie(seria);
                sr.setEtapa(etapa);
                sr.setIdChestionar(new Long(Long.parseLong(idChestionar)));
                s.saveOrUpdate(sr);
                tx.commit();
            }

            Query q = s.createQuery( "from SetRezultat where judet=? and localitate=? and unitate=? and serie=? and etapa=? and idChestionar=?");
            q.setString(0,judetSelectat);
            q.setString(1, localitateSelectata);
            q.setString(2, unitateSelectata);
            q.setString(3, seria);
            q.setString(4, etapa);
            q.setLong(5, Long.parseLong(idChestionar));
            setr= (SetRezultat) q.uniqueResult();
            s.close();
        return setr.getIdSetRezultat();  //To change body of created methods use File | Settings | File Templates.
    }

    private void saveDateChestionar(Map date)
    {
        Session s = factory.openSession();
        Transaction tx = s.beginTransaction();
        tx.commit();
        Long idSetRezultat = new Long(Long.parseLong(((String[]) date.get("idSetRezultat"))[0]));

        try {
            s.connection().prepareStatement("DELETE from REZULTAT where ID_SET_REZULTAT=" + idSetRezultat).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        for (Iterator i = date.keySet().iterator(); i.hasNext();)
        {
            tx = s.beginTransaction();
            Rezultat rezultat = new Rezultat();
            String key = (String) i.next();
            DataParser dp = new DataParser(key, date.get(key));
            rezultat.setUCD(key);
            rezultat.setIdSetRezultat(idSetRezultat);
            rezultat.setIdIntrebare(dp.getIdIntrebare());
            rezultat.setIdOptiune(dp.getIdOptiune());
            if(dp.getIntrebareRadioWhithoutClearText()!=1)
            {
                rezultat.setClearText(((String[])date.get(key))[0]);
            } else
            {
                rezultat.setClearText(""+dp.getIdOptiune());
            }
            if(dp.getIdOptiune()!=null && (((String[])date.get(key))[0]).length()>0 && dp.getIdIntrebare()!=null )
            {
                s.merge(rezultat);
                tx.commit();
            }
        }
        s.close();
    }

    public void setChestionarDao(ChestionarDao chestionarDao) {
        this.chestionarDao = chestionarDao;
    }


    private String getValueFromMap(String key, Map map)
    {
        return ((String[] )map.get(key))[0];
    }


}
