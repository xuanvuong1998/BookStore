/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listener;

import constants.ConfigConstants;
import crawler.muasachhay.MuaSachHayThread;
import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import utils.DBUtils;

public class BookStoreAppListener implements ServletContextListener {

    private static String realPath;
    private static MuaSachHayThread muaSachHayThread;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        final ServletContext context = sce.getServletContext();

        realPath = context.getRealPath("/");

        if (ConfigConstants.ENABLE_CRAWLING) {
            muaSachHayThread = new MuaSachHayThread(context);
            muaSachHayThread.start();
        }

        context.setAttribute("MUASACHHAY_THREAD", muaSachHayThread);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        EntityManager em = DBUtils.getEntityManager();
        if (em != null) {
            em.close();
        }
    }
}
