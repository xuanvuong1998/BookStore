/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler.newshop;

import constants.ConfigConstants;
import constants.URLConstants;
import crawler.BaseThread;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;

public class NewShopThread extends BaseThread implements Runnable {

    private final String URL = URLConstants.NEWSHOP;
    
    private ServletContext context;

    public NewShopThread(ServletContext context) {
        this.context = context;
    }

    @Override
    public void run() {
        while (true) {
            try {
                NewShopCategoryCrawler categoryCrawler = new NewShopCategoryCrawler(context);
                Map<String, String> categories = categoryCrawler.getCategories(URL);
                
                System.out.println("CATEGORIES: ");
                for (Map.Entry<String, String> cat: categories.entrySet()) {
                    System.out.println(cat.getValue() + ": " + cat.getKey());
                }
                
                for (Map.Entry<String, String> entry : categories.entrySet()) {
//                    Thread pageCrawlingThread = new Thread(
//                        new MuaSachHayCategoryPageCrawler(context, entry.getKey(), entry.getValue()));
//                    pageCrawlingThread.start();
//                    
//                    if (ConfigConstants.DEBUG) {
//                        System.out.println("DEBUG MuaSachHay Id = " + pageCrawlingThread.getId()
//                            + "(name, link) = " + entry.getKey() + ", " + entry.getValue());
//                    }
                    
                    synchronized (BaseThread.getInstance()) {
                        while (BaseThread.isSuspended()) {
                            BaseThread.getInstance().wait();
                        }
                    }
                }
                
                NewShopThread.sleep(ConfigConstants.CRAWLING_INTERVAL);
                synchronized (BaseThread.getInstance()) {
                    while (BaseThread.isSuspended()) {
                        BaseThread.getInstance().wait();
                    }
                }
            } catch (InterruptedException e) {
                Logger.getLogger(NewShopThread.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }
    
    
}
