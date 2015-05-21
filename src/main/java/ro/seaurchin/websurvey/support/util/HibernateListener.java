package ro.seaurchin.websurvey.support.util;


import org.hibernate.HibernateException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * User: BogdanCo
 * Date: Aug 23, 2006
 * Time: 8:29:32 PM
 */
public class HibernateListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent event) {
        HibernateUtil.getSessionFactory(); // Just call the static initializer of that class
    }

    public void contextDestroyed(ServletContextEvent event) {
        try {
            HibernateUtil.getSessionFactory().close(); // Free all resources
        } catch (HibernateException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
