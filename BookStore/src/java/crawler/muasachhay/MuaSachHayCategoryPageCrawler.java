/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler.muasachhay;

import constants.ConfigConstants;
import crawler.BaseCrawler;
import crawler.BaseThread;
import dao.CategoryDAO;
import entity.Category;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import utils.ElementChecker;
import utils.TextUtils;

public class MuaSachHayCategoryPageCrawler extends BaseCrawler implements Runnable {

    private String pageUrl;
    private String categoryName;

    public MuaSachHayCategoryPageCrawler(ServletContext context, String pageUrl,
            String categoryName) {
        super(context);
        this.pageUrl = pageUrl;
        this.categoryName = categoryName;
    }

    @Override
    public void run() {
        Category category = createCategory(categoryName);
        if (category == null) {
            Logger.getLogger(MuaSachHayCategoryPageCrawler.class.getName())
                    .log(Level.SEVERE, null, new Exception("Error: category null"));
            return;
        }

        BufferedReader reader = null;
        try {
            reader = getBufferedReaderForUrl(pageUrl);
            String document = getCategoryPageDocument(reader);

            document = TextUtils.refineHtml(document);

            synchronized (BaseThread.getInstance()) {
                while (BaseThread.isSuspended()) {
                    BaseThread.getInstance().wait();
                }
            }

            int lastPage = getLastPage(document);
            lastPage = lastPage > ConfigConstants.CRAWL_PAGE_THRESHOLD 
                    ? ConfigConstants.CRAWL_PAGE_THRESHOLD : lastPage;

            for (int i = 1; i <= lastPage; ++i) {
                String categoryPageUrl = pageUrl + "page/" + i;
                System.out.println("PAGE URL: " + categoryPageUrl);
                Thread modelListCrawler = new Thread(
                        new MuaSachHayBookListCrawler(getContext(), categoryPageUrl, category));
                modelListCrawler.start();
                if (true) break;

                if (i % ConfigConstants.CRAWL_THREAD_REDUCE > 0) {
                    modelListCrawler.join();
                }

                synchronized (BaseThread.getInstance()) {
                    while (BaseThread.isSuspended()) {
                        BaseThread.getInstance().wait();
                    }
                }
            }
        } catch (IOException | InterruptedException
                | XMLStreamException | NumberFormatException ex) {
            Logger.getLogger(MuaSachHayCategoryPageCrawler.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    private String getCategoryPageDocument(BufferedReader reader) throws IOException {
        String line = "";
        String document = "";
        boolean isStart = false;
        while ((line = reader.readLine()) != null) {
            if (!isStart && line.contains("<ul class='page-numbers'>")) {
                isStart = true;
            }
            if (isStart) {
                document += line.trim();
            }
            if (isStart && line.contains("</ul>")) {
                break;
            }
        }
        return document;
    }

    private int getLastPage(String document)
            throws UnsupportedEncodingException, XMLStreamException, NumberFormatException {
        document = document.trim();
        XMLEventReader eventReader = parseStringToXMLEventReader(document);
        int lastPage = 1;
        while (eventReader.hasNext()) {
            XMLEvent event = (XMLEvent) eventReader.next();

            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();

                if (ElementChecker.isElementWith(startElement, "a", "class", "page-numbers")) {
                    event = (XMLEvent) eventReader.next();
                    Characters chars = event.asCharacters();
                    String pageStr = chars.getData();
                    lastPage = Integer.parseInt(pageStr);
                }
            }
        }
        return lastPage;
    }

    private static final Object LOCK = new Object();

    protected Category createCategory(String name) {
        synchronized (LOCK) {
            Category category = null;
            CategoryDAO dao = CategoryDAO.getInstance();
            category = dao.getFirstCategory(name);
            if (category == null) {
                category = new Category(0, name);
                dao.create(category);
            }
            return category;
        }
    }

    private String getHref(StartElement a) {
        Attribute href = a.getAttributeByName(new QName("href"));
        return href == null ? "" : href.getValue();
    }
}
