package ro.seaurchin.websurvey.support.dao.impl;

import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Query;
import ro.seaurchin.websurvey.support.util.HibernateUtil;
import ro.seaurchin.websurvey.support.util.FiltruLista;
import ro.seaurchin.websurvey.support.rezultate.SetRezultat;

import java.util.List;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.sql.SQLException;

/**
 * User: BogdanCo
 * Date: Sep 24, 2006
 * Time: 12:07:21 PM
 */
public class SetRezultatDao
{
    private SessionFactory factory= HibernateUtil.getSessionFactory();

    public String getJudet(Long id)
    {
        SetRezultat sr ;
        Session s = factory.openSession();
        Query q = s.createQuery( "from SetRezultat where idSetRezultat=?");
        q.setLong(0,id.longValue());
        sr= (SetRezultat)q.uniqueResult();
        s.close();
        return sr.getJudet();
    }

    public String getLocalitate(Long id)
    {
        SetRezultat sr ;
        Session s = factory.openSession();
        Query q = s.createQuery( "from SetRezultat where idSetRezultat=?");
        q.setLong(0,id.longValue());
        sr= (SetRezultat)q.uniqueResult();
        s.close();
        return sr.getLocalitate();
    }

    public String getUnitate(Long id)
    {
        SetRezultat sr ;
        Session s = factory.openSession();
        Query q = s.createQuery( "from SetRezultat where idSetRezultat=?");
        q.setLong(0,id.longValue());
        sr= (SetRezultat)q.uniqueResult();
        s.close();
        return sr.getUnitate();
    }

    public String getSerie(Long id)
    {
        SetRezultat sr ;
        Session s = factory.openSession();
        Query q = s.createQuery( "from SetRezultat where idSetRezultat=?");
        q.setLong(0,id.longValue());
        sr= (SetRezultat)q.uniqueResult();
        s.close();
        return sr.getSerie();
    }

    public String getEtapa(Long id)
    {
        SetRezultat sr ;
        Session s = factory.openSession();
        Query q = s.createQuery( "from SetRezultat where idSetRezultat=?");
        q.setLong(0,id.longValue());
        sr= (SetRezultat)q.uniqueResult();
        s.close();
        return sr.getEtapa();
    }

    public List getLista(Long idChestionar)
    {
        List sr;
        Session s = factory.openSession();
        Query q = s.createQuery( "from SetRezultat where idChestionar=?");
        q.setLong(0,idChestionar.longValue());
        sr=q.list();
        s.close();
        return sr;
    }

    public List getLista(Long idChestionar, FiltruLista filtru)
    {
        List sr;
        Session s = factory.openSession();
        Query q = s.createQuery( "from SetRezultat where idChestionar=? ".concat(filtru.getSQL()));
        q.setLong(0,idChestionar.longValue());
        sr=q.list();
        s.close();
        return sr;
    }

    public void stergeSetRezultate(Long idSetRezultat)
    {
        Session s = factory.openSession();
        try {
            s.connection().prepareStatement("DELETE FROM REZULTAT WHERE ID_SET_REZULTAT=" + idSetRezultat).executeUpdate();
            s.connection().prepareStatement("DELETE FROM SET_REZULTAT WHERE ID_SET_REZULTAT=" + idSetRezultat).executeUpdate();
            Logger.global.warning("Am sterg setul de rezultate cu id-ul: " + idSetRezultat);
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

}
