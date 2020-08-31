/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listener;

import crawler.muasachhay.MuaSachHayThread;
import dao.BookDAO;
import entity.Book;
import java.util.List;
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

//        List<Book> models = getAllModels();
//        context.setAttribute("MODELS", models);
//        context.setAttribute("CACHE_TIME", System.currentTimeMillis());
//        if (models != null) {
//            System.out.println("INFO models cached with " + models.size() + " models at " + System.currentTimeMillis());
//        }

        muaSachHayThread = new MuaSachHayThread(context);
        muaSachHayThread.start();

        context.setAttribute("MUASACHHAY_THREAD", muaSachHayThread);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        EntityManager em = DBUtils.getEntityManager();
        if (em != null) {
            em.close();
        }
    }
    
//    private List<Book> getAllModels() {
//        BookDAO bookDao = BookDAO.getInstance();
//        return bookDao.getAllModels();
//    }
}
