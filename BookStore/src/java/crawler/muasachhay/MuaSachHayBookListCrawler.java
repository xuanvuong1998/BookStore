/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler.muasachhay;

import constants.ConfigConstants;
import crawler.BaseCrawler;
import crawler.BaseThread;
import dao.BookDAO;
import entity.Book;
import entity.Category;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import utils.TextUtils;

public class MuaSachHayBookListCrawler extends BaseCrawler implements Runnable {

    private String pageUrl;
    private Category category;

    public MuaSachHayBookListCrawler(ServletContext context, String pageUrl,
            Category category) {
        super(context);
        this.pageUrl = pageUrl;
        this.category = category;
    }

    @Override
    public void run() {
        BufferedReader reader = null;
        try {
            reader = getBufferedReaderForUrl(pageUrl);
            String document = getModelListDocument(reader);

            document = TextUtils.refineHtml(document);

            if (ConfigConstants.DEBUG && ConfigConstants.DEBUG_PRINT_DOC) {
                System.out.println("DEBUG ModelList document: " + document);
            }

            List<String> bookLinks = getBookLinks(document);

            for (String modelLink : bookLinks) {
                MuaSachHayBookCrawler bookCrawler
                        = new MuaSachHayBookCrawler(getContext(), modelLink, category);

                Book book = bookCrawler.getBook();
                
                if (book == null) {
                    continue;
                }
                BookDAO.getInstance().saveBookWhileCrawling(book);

                if (ConfigConstants.DEBUG) {
                    System.out.println("DEBUG saved model " + book.getLink());
                }

                synchronized (BaseThread.getInstance()) {
                    while (BaseThread.isSuspended()) {
                        BaseThread.getInstance().wait();
                    }
                }
            }
        } catch (IOException | XMLStreamException | InterruptedException ex) {
            Logger.getLogger(MuaSachHayBookListCrawler.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    private String getModelListDocument(BufferedReader reader) throws IOException {
        String line = "";
        String document = "<books>";
        boolean isStart = false;
        while ((line = reader.readLine()) != null) {
            if (!isStart && line.contains("<ul  class=\"products-loop row grid clearfix\">")) {
                isStart = true;
            }
            if (isStart) {
                document += line.trim();
            }
            if (isStart && line.contains("</ul>")) {
                break;
            }
        }
        document += "</books>";
        return document;
    }

    private List<String> getBookLinks(String document)
            throws UnsupportedEncodingException, XMLStreamException {
        XMLEventReader eventReader = parseStringToXMLEventReader(document);
        XMLEvent event = null;
        List<String> links = new ArrayList<>();
        while (eventReader.hasNext()) {
            event = (XMLEvent) eventReader.next();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                String tagName = startElement.getName().getLocalPart();
                if ("h4".equals(tagName)) {
                    event = (XMLEvent) eventReader.next();
                    startElement = event.asStartElement();
                    String link = getHref(startElement);
                    links.add(link);
                }

            }
        }
        return links;
    }

    private String getHref(StartElement element) {
        Attribute href = element.getAttributeByName(new QName("href"));
        return href == null ? "" : href.getValue();
    }
}
