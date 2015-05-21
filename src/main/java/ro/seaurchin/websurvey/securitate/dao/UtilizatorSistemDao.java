package ro.seaurchin.websurvey.securitate.dao;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UserDetailsService;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.dao.DataAccessException;
import ro.seaurchin.websurvey.securitate.RolUtilizator;
import ro.seaurchin.websurvey.support.util.HibernateUtil;
import ro.seaurchin.websurvey.securitate.UtilizatorSistem;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

/**
 * User: BogdanCo
 * Date: Oct 4, 2006
 * Time: 4:25:54 PM
 */
public class UtilizatorSistemDao implements UserDetailsService
{
    private SessionFactory factory = HibernateUtil.getSessionFactory();

    public UtilizatorSistem getUtilizator(String numeUtilizator)
    {
        Session s = factory.openSession();
        UtilizatorSistem us = (UtilizatorSistem) s.createQuery("from UtilizatorSistem where numeUtilizator='" + numeUtilizator + "'" ).uniqueResult();

        List<RolUtilizator> roluri = s.createQuery("from RolUtilizator where numeUtilizator='" + numeUtilizator + "'" ).list();
        us.setGrantedAuthorities(roluri.toArray(new GrantedAuthority[roluri.size()]));
        s.close();

        return us;
    }


    public void updateUser(String idUtilizator, String email, String parola)
    {
        if(!idUtilizator.equals("-1"))
        {
            Session s = factory.openSession();
            Transaction tx = s.beginTransaction();
            UtilizatorSistem us = (UtilizatorSistem) s.createQuery("from UtilizatorSistem where idUtilizatorSistem = " + idUtilizator).uniqueResult();
            if (!email.equals("n/a")){
                us.setEmail(email);
            }
            if(!parola.equals("n/a"))
            {
                us.setParola(parola);
            }
            s.update(us);
            tx.commit();
            s.close();
            Logger.global.warning("Am modificat un utilizator!!!");
        }
        //To change body of created methods use File | Settings | File Templates.
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException, DataAccessException {
        return getUtilizator(userName);
    }
}
