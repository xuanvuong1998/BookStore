/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler.newshop;

import constants.ConfigConstants;
import crawler.BaseCrawler;
import entity.Book;
import static entity.Book_.author;
import static entity.Book_.numOfPages;
import entity.Category;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.Date;
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
import utils.ParseUtils;
import utils.TextUtils;

public class NewShopBookCrawler extends BaseCrawler {

    private String pageUrl;
    private Category category;

    public NewShopBookCrawler(ServletContext context, String pageUrl, Category category) {
        super(context);
        this.pageUrl = pageUrl;
        this.category = category;
    }

    public Book getBook() {
        BufferedReader reader = null;
        Book book = null;
        try {
            reader = getBufferedReaderForUrl(pageUrl);
            String document = getBookDocument(reader);
            return stAXParserForBook(document);
        } catch (IOException | XMLStreamException | RuntimeException ex) {
            System.out.println("ERROR: " + pageUrl);
            Logger.getLogger(NewShopBookCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return book;
    }

    private Book stAXParserForBook(String document)
            throws XMLStreamException, UnsupportedEncodingException {
        document = TextUtils.refineHtml(document);

        if (ConfigConstants.DEBUG && ConfigConstants.DEBUG_PRINT_DOC) {
            System.out.println("DEBUG: book document: " + document);
        }

        XMLEventReader eventReader = parseStringToXMLEventReader(document);

        String imageSrc = getBookImageSource(eventReader);

        String name = TextUtils.parseUnicode(getBookName(eventReader));
        if (name == null) {
            return null;
        }

        String description = TextUtils.parseUnicode(getDescription(eventReader));
        Integer price = getPrice(eventReader);

        String link = pageUrl;
        int quantity = 100;
        String isbn = "";
        String author = "";
        int numOfPages = 0;
        Date importDate = Date.from(Instant.now());
        boolean isActive = true;

        Book book = new Book(0, name, price.doubleValue(), author, imageSrc, quantity,
                isbn, numOfPages, link, description, importDate, isActive, category);

        return book;
    }

    private String getBookDocument(BufferedReader reader) throws IOException {
        String document = "";
        String line = "";
        boolean isStart = false;

        while ((line = reader.readLine()) != null) {
            if (!isStart && line.contains("<section class=\"main-product\">")) {
                isStart = true;
            }
            if (isStart) {
                document += line.trim();
            }
            if (isStart && line.contains("</section>")) {
                break;
            }
        }
        return document;
    }

    private String getBookName(XMLEventReader eventReader) throws XMLStreamException {
        String name = null;
        XMLEvent event;
        while (eventReader.hasNext()) {
            event = (XMLEvent) eventReader.next();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                if (ElementChecker.isElementWith(startElement, "h1", "class", "product-title")) {
                    event = (XMLEvent) eventReader.next();
                    Characters nameChars = event.asCharacters();
                    name = nameChars.getData();
                    return name;
                }
            }
        }

        return name;
    }

    private String getDescription(XMLEventReader eventReader) throws XMLStreamException {
        String description = null;
        XMLEvent event;
        while (eventReader.hasNext()) {
            event = (XMLEvent) eventReader.next();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                if (ElementChecker.isElementWith(startElement, "div", "class", "description")) {
                    event = (XMLEvent) eventReader.next();
                    if (event.isCharacters()) {
                        Characters nameChars = event.asCharacters();
                        description = nameChars.getData();
                    }
                    return description;
                }
            }
        }

        return description;
    }

    private String getBookImageSource(XMLEventReader eventReader) {
        XMLEvent event;
        String src = null;
        while (eventReader.hasNext()) {
            event = (XMLEvent) eventReader.next();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                if (ElementChecker.isElementWith(startElement, "div", "class", "preview-image")) {
                    while (!event.isStartElement() || !ElementChecker.isElementWith(startElement, "img")) {
                        event = (XMLEvent) eventReader.next();
                        startElement = event.isStartElement()
                                ? event.asStartElement() : startElement;
                    }
                    startElement = event.asStartElement();
                    Attribute srcAttr = startElement.getAttributeByName(new QName("src"));
                    src = srcAttr.getValue();
                    return src;
                }
            }
        }
        return src;
    }

    private Integer getPrice(XMLEventReader eventReader) {
        int numOfPages = 0;
        XMLEvent event;
        while (eventReader.hasNext()) {
            event = (XMLEvent) eventReader.next();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                if (ElementChecker.isElementWith(startElement, "div", "class", "price-group")) {
                    event = (XMLEvent) eventReader.next();
                    while (!event.isCharacters()) {
                        event = (XMLEvent) eventReader.next();
                    }
                    Characters chars = event.asCharacters();
                    String text = chars.getData();

                    return ParseUtils.extractNumber(text.replace(".", ""));
                }
            }
        }
        return numOfPages;
    }
}
