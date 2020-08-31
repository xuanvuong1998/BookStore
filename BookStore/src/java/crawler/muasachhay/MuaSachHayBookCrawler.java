/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler.muasachhay;

import constants.ConfigConstants;
import crawler.BaseCrawler;
import entity.Book;
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

public class MuaSachHayBookCrawler extends BaseCrawler {

    private String pageUrl;
    private Category category;

    public MuaSachHayBookCrawler(ServletContext context, String pageUrl, Category category) {
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
        } catch (IOException | XMLStreamException ex) {
            Logger.getLogger(MuaSachHayBookCrawler.class.getName()).log(Level.SEVERE, null, ex);
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

        String name = getBookName(eventReader);
        if (name == null) {
            return null;
        }

        boolean isTableDisplay = isInfoDisplayInTable(eventReader);

        String author = getAuthor(eventReader, isTableDisplay);
        Integer numOfPages = getNumOfPages(eventReader, isTableDisplay);
        Integer price = getPrice(eventReader);
        String link = pageUrl;
        int quantity = 100;
        String isbn = "";
        String description = "";
        Date importDate = Date.from(Instant.now());
        boolean isActive = true;

        Book book = new Book(0, name, price.doubleValue(), author, imageSrc, quantity, isbn, numOfPages, link,
                description, importDate, isActive, category);

        return book;
    }

    private String getBookDocument(BufferedReader reader) throws IOException {
        String document = "";
        String line = "";
        boolean isStart = false;

        while ((line = reader.readLine()) != null) {
            if (!isStart && line.contains("<div class=\"single-product-top row\">")) {
                isStart = true;
            }
            if (isStart && line.contains("<div class=\"col-lg-12\">")) {
                break;
            }
            if (isStart) {
                document += line.trim();
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
                if (ElementChecker.isElementWith(startElement, "h1", "class", "product_title")) {
                    event = (XMLEvent) eventReader.next();
                    Characters nameChars = event.asCharacters();

                    name = nameChars.getData();
                    return name;
                }
            }
        }

        return name;
    }

    private boolean isInfoDisplayInTable(XMLEventReader eventReader) throws XMLStreamException {
        boolean isTable = false;
        XMLEvent event;
        while (eventReader.hasNext()) {
            event = (XMLEvent) eventReader.next();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                if (ElementChecker.isElementWith(startElement, "h2", "class", "quick-overview")) {
                    eventReader.next();
                    event = (XMLEvent) eventReader.next();

                    startElement = event.asStartElement();
                    return ElementChecker.isElementWith(startElement, "table");
                }
            }
        }

        return isTable;
    }

    private String getAuthor(XMLEventReader eventReader, boolean isTable) throws XMLStreamException {
        String author = null;
        XMLEvent event;
        while (eventReader.hasNext()) {
            event = (XMLEvent) eventReader.next();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                String tagName = startElement.getName().getLocalPart();
                if (!isTable) {
                    if ("p".equals(tagName)) {
                        event = (XMLEvent) eventReader.next();
                        if (event.isCharacters()) {
                            Characters chars = event.asCharacters();
                            String text = chars.getData();
                            if (text.contains("Tác giả")) {
                                author = text.split(":")[1].trim();
                                return author;
                            }
                        }
                    }
                } else {
                    if ("td".equals(tagName)) {
                        event = (XMLEvent) eventReader.next();
                        if (event.isCharacters()) {
                            Characters chars = event.asCharacters();
                            String text = chars.getData();
                            if (text.contains("Tác giả")) {
                                eventReader.next();
                                eventReader.next();
                                event = (XMLEvent) eventReader.next();
                                chars = event.asCharacters();
                                author = chars.getData();
                                return author;
                            }
                        }
                    }
                }
            }
        }

        return author;
    }

    private String getBookImageSource(XMLEventReader eventReader) {
        XMLEvent event;
        String src = null;
        while (eventReader.hasNext()) {
            event = (XMLEvent) eventReader.next();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                if (ElementChecker.isElementWith(startElement, "img", "class",
                        "attachment-shop_single size-shop_single wp-post-image")) {
                    Attribute srcAttr = startElement.getAttributeByName(new QName("src"));
                    src = srcAttr.getValue();
                    return src;
                }
            }
        }
        return src;
    }

    private Integer getNumOfPages(XMLEventReader eventReader, boolean isTable) {
        int numOfPages = 0;
        XMLEvent event;
        while (eventReader.hasNext()) {
            event = (XMLEvent) eventReader.next();
            if (!isTable) {
                if (event.isCharacters()) {
                    Characters chars = event.asCharacters();
                    String text = chars.getData();
                    if (text.contains("Số trang")) {
                        numOfPages = ParseUtils.extractNumber(text);
                        return numOfPages;
                    }
                }
            } else {
                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    if (ElementChecker.isElementWith(startElement, "td")) {
                        event = (XMLEvent) eventReader.next();
                        if (event.isCharacters()) {
                            Characters chars = event.asCharacters();
                            String text = chars.getData();
                            if (text.contains("Số trang")) {
                                eventReader.next();
                                eventReader.next();
                                event = (XMLEvent) eventReader.next();
                                chars = event.asCharacters();
                                text = chars.getData();
                                numOfPages = ParseUtils.extractNumber(text);
                                return numOfPages;
                            }
                        }
                    }
                }
            }
        }
        return numOfPages;
    }

    private Integer getPrice(XMLEventReader eventReader) {
        int numOfPages = 0;
        XMLEvent event;
        while (eventReader.hasNext()) {
            event = (XMLEvent) eventReader.next();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                if (ElementChecker.isElementWith(startElement, "p", "class", "price")) {
                    eventReader.next();
                    eventReader.next();
                    event = (XMLEvent) eventReader.next();
                    Characters chars = event.asCharacters();
                    String text = chars.getData();

                    return ParseUtils.extractNumber(text.replace(".", ""));
                }
            }
        }
        return numOfPages;
    }
}
