package ro.seaurchin.websurvey.support.dao.impl;

import ro.seaurchin.websurvey.support.dao.UnitateInvatamantDao;
import ro.seaurchin.websurvey.support.util.HibernateUtil;

import java.util.List;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.math.BigDecimal;

import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SQLQuery;

/**
 * User: BogdanCo
 * Date: Sep 21, 2006
 * Time: 8:41:07 PM
 */
public class UnitateInvatamantJDBCDaoImpl implements UnitateInvatamantDao
{

    private SessionFactory factory= HibernateUtil.getSessionFactory();

    public List getJudete()
    {
        Session s = factory.openSession();
        List judete = new ArrayList();

        try {
            Transaction tx= s.beginTransaction();
            judete = s.createSQLQuery("SELECT DISTINCT JUDET FROM UNITATE_INVATAMANT").list();
            tx.commit();
            return judete;
        }
        catch (Exception e) {
            s.getTransaction().rollback();
            e.printStackTrace();
            }
        finally {
            s.close();
        }
        return judete;
    }

    public List getLocalitatiForJudet(String judet) {
        Session s = factory.openSession();
        List localitati = new ArrayList();

        try {
            Transaction tx= s.beginTransaction();
            localitati = s.createSQLQuery("SELECT DISTINCT LOCALITATE FROM UNITATE_INVATAMANT WHERE JUDET='" + judet + "'").list();
            tx.commit();
            return localitati;
        }
        catch (Exception e) {
            s.getTransaction().rollback();
            e.printStackTrace();
            }
        finally {
            s.close();
        }
        return localitati;
    }

    public List getUnitatiForJudetAndLocalitate(String judet, String Localitate)
    {
        Session s = factory.openSession();
        List unitati = new ArrayList();

        try {
            Transaction tx= s.beginTransaction();
            unitati = s.createSQLQuery("SELECT DISTINCT UNITATE FROM UNITATE_INVATAMANT WHERE JUDET='" + judet + "' AND LOCALITATE='" + Localitate + "'" ).list();
            tx.commit();
            return unitati;
        }
        catch (Exception e) {
            s.getTransaction().rollback();
            e.printStackTrace();
            }
        finally {
            s.close();
        }
        return unitati;
    }

    public String getSerieForUnitateDinJudetSiLocalitate(String judet, String localitate, String unitate) {
        Session s = factory.openSession();
        String serie = "";

        try {
            Transaction tx= s.beginTransaction();
            SQLQuery sqlq = s.createSQLQuery("SELECT SERIE FROM UNITATE_INVATAMANT WHERE JUDET='" + judet + "' AND LOCALITATE='" + localitate + "' AND UNITATE='" + unitate + "'" );
            List serii = sqlq.list();
            for (int i=0;i<serii.size();i++)
            {
                if (i==0 ) {
                    serie=(String)serii.get(i);
                }  else
                {
                    serie=serie + ", " + (String)serii.get(i);
                }
            }
            tx.commit();
            return serie;
        }
        catch (Exception e) {
            s.getTransaction().rollback();
            e.printStackTrace();
            }
        finally {
            s.close();
        }
        return serie;
    }

    public String getEtapaForUnitateDinJudetSiLocalitate(String judet, String localitate, String unitate) {
        Session s = factory.openSession();
        String etapa = "";

        try {
            Transaction tx= s.beginTransaction();
            SQLQuery sqlq = s.createSQLQuery("SELECT ETAPA FROM UNITATE_INVATAMANT WHERE JUDET='" + judet + "' AND LOCALITATE='" + localitate + "' AND UNITATE='" + unitate + "'" );
            List etape = sqlq.list();
            for (int i=0;i<etape.size();i++)
            {
                if (i==0 ) {
                    etapa=(String)etape.get(i);
                }  else
                {
                    etapa=etapa + ", " + (String)etape.get(i);
                }
            }
            tx.commit();
            return etapa;
        }
        catch (Exception e) {
            s.getTransaction().rollback();
            e.printStackTrace();
            }
        finally {
            s.close();
        }
        return etapa;
    }

    public Long checkForDateGetId(String judetSelectat, String localitateSelectata, String unitateSelectata, Long idChestionar) {
        Session s = factory.openSession();
        try {
            return new Long(((BigDecimal)s.createSQLQuery("SELECT NVL(ID_SET_REZULTAT,0) FROM SET_REZULTAT WHERE JUDET='" + judetSelectat + "' AND LOCALITATE='" + localitateSelectata + "' AND UNITATE='" + unitateSelectata + "' AND ID_CHESTIONAR='" + idChestionar + "'" ).uniqueResult()).intValue());
        }
        catch (NullPointerException np){
            Logger.global.warning("Am prins null deci nu are date. ignore!");
            return new Long(0);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new Long(0);
            }
        finally {
            s.close();
        }
    }
}
