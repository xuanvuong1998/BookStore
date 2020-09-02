/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listener;

import constants.ConfigConstants;
import crawler.muasachhay.MuaSachHayThread;
import crawler.newshop.NewShopThread;
import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import utils.DBUtils;

public class BookStoreAppListener implements ServletContextListener {

    private static MuaSachHayThread muaSachHayThread;
    private static NewShopThread newShopThread;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        final ServletContext context = sce.getServletContext();

        if (ConfigConstants.ENABLE_CRAWLING) {
            muaSachHayThread = new MuaSachHayThread(context);
            muaSachHayThread.start();
            
            newShopThread = new NewShopThread(context);
            newShopThread.start();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        EntityManager em = DBUtils.getEntityManager();
        if (em != null) {
            em.close();
        }
    }
}
