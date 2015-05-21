package ro.seaurchin.websurvey.support.dao.impl;

import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SQLQuery;
import ro.seaurchin.websurvey.support.util.HibernateUtil;
import ro.seaurchin.websurvey.support.Chestionar;
import ro.seaurchin.websurvey.support.Sectiune;
import ro.seaurchin.websurvey.support.Intrebare;
import ro.seaurchin.websurvey.support.dao.ChestionarDao;

import java.util.*;

/**
 * User: BogdanCo
 * Date: Aug 25, 2006
 * Time: 3:06:11 PM
 */
public class ChestionarDaoImpl implements ChestionarDao
{
    private SessionFactory factory= HibernateUtil.getSessionFactory();
    private List chestionare = new ArrayList();

    public List getChestionare()
    {

        Session s = factory.openSession();

        try {
            Transaction tx= s.beginTransaction();
             chestionare = s.createQuery
            (
                "from Chestionar"
                )
                .list();
            tx.commit();
            return chestionare;
        }
        catch (Exception e) {
            s.getTransaction().rollback();
            e.printStackTrace();
            }

        finally {
            s.close();
        }
        return chestionare;
    }

    public void stergeChestionar(Long id)
    {

        Session s = factory.openSession();
        s.createQuery("DELETE from Chestionar where idChestionar=" + id).executeUpdate();
        s.close();
    }

    public Chestionar getChestionar(Long id)
    {
        Session s = factory.openSession();
        Chestionar c = (Chestionar)s.createQuery("from Chestionar where idChestionar=" + id).uniqueResult();
        s.close();
        return c;
    }

    public Map getRezultateMap(Long idChestionar, Long idSetRezultat) {

        String SQL="\n" +
                "SELECT r.UCD, r.CLEAR_TEXT FROM REZULTAT r, OPTIUNE o\n" +
                "WHERE \n" +
                "r.id_optiune=o.id_optiune\n" +
                "and r.id_set_rezultat=?";
        Map rMap = new HashMap();
        List tmp=new ArrayList();

        Session s = factory.openSession();
        SQLQuery sq = s.createSQLQuery(SQL);
        sq.setLong(0,idSetRezultat.longValue());
        tmp=sq.list();
        s.close();

        for (Iterator iterator = tmp.iterator(); iterator.hasNext();) {
            Object[] strings = (Object[]) iterator.next();
            rMap.put( (String) strings[0], (String) strings[1] );
        }

        return rMap;
    }
}
