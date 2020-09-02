/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler.newshop;

import constants.URLConstants;
import crawler.BaseCrawler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
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

public class NewShopCategoryCrawler extends BaseCrawler {

    public NewShopCategoryCrawler(ServletContext context) {
        super(context);
    }

    public Map<String, String> getCategories(String url) {
        try (BufferedReader reader = getBufferedReaderForUrl(url)) {
            String document = getCategoryDocument(reader);
            document = TextUtils.refineHtml(document);

            return stAXParserForCategories(document);
        } catch (IOException | XMLStreamException ex) {
            Logger.getLogger(NewShopCategoryCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private String getCategoryDocument(final BufferedReader reader) throws IOException {
        String line = "";
        String document = "";
        boolean isStart = false;
        while ((line = reader.readLine()) != null) {
            if (!isStart && line.contains("<nav class=\"categories-list-box for-home\">")) {
                isStart = true;
            }
            if (isStart) {
                document += line.trim();
            }
            if (isStart && line.contains("</nav>")) {
                break;
            }
        }
        return document;
    }

    public Map<String, String> stAXParserForCategories(String document)
            throws UnsupportedEncodingException, XMLStreamException {
        document = document.trim();
        XMLEventReader eventReader = parseStringToXMLEventReader(document);
        Map<String, String> categories = new HashMap<>();

        while (eventReader.hasNext()) {
            XMLEvent event = (XMLEvent) eventReader.next();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                if (ElementChecker.isElementWith(startElement, "div", "class", "menuItem-box")) {
                    event = (XMLEvent) eventReader.next();
                    startElement = event.asStartElement();
                    
                    Attribute href = startElement.getAttributeByName(new QName("href"));
                    String link = href.getValue();
                    link = URLConstants.NEWSHOP + link;
                    
                    event = (XMLEvent) eventReader.next();
                    Characters categoryNameChars = event.asCharacters();

                    categories.put(link, categoryNameChars.getData());
                }
            }
        }

        return categories;
    }
}
