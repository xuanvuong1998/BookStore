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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import utils.ElementChecker;
import utils.ParseUtils;
import utils.TextUtils;

public class MuaSachHayBookCrawler extends BaseCrawler {

    private String pageUrl;
    private Category category;
    private List<String> authorLabels;

    public MuaSachHayBookCrawler(ServletContext context, String pageUrl, Category category) {
        super(context);
        this.pageUrl = pageUrl;
        this.category = category;
        this.authorLabels = new ArrayList<>();
        this.authorLabels.add("Tác giả");
        this.authorLabels.add("Dịch giả");
        this.authorLabels.add("Tòa soạn");
        this.authorLabels.add("Biên soạn");
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
        Integer numOfPages = 0;
        if (author != null) {
            numOfPages = getNumOfPages(eventReader, isTableDisplay);
        }
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
            if (!isTable) {
                if (event.isCharacters()) {
                    Characters chars = event.asCharacters();
                    String text = chars.getData();
                    String authorLabel = getAuthorLabel(text);
                    if (authorLabel != null) {
                        text = text.replace(":", "");
                        text = text.replace(authorLabel, "");
                        author = text.trim();
                        return author;
                    }
                }
            } else {
                while (!event.isStartElement()) {
                    event = (XMLEvent) eventReader.next();
                }
                StartElement startElement = event.asStartElement();
                if (ElementChecker.isElementWith(startElement, "td")) {
                    event = (XMLEvent) eventReader.next();
                    if (event.isCharacters()) {
                        Characters chars = event.asCharacters();
                        String text = chars.getData();
                        String authorLabel = getAuthorLabel(text);
                        if (authorLabel != null) {
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

            if (event.isEndElement()) {
                EndElement endElement = event.asEndElement();
                if (ElementChecker.isElementWith(endElement, "div")) {
                    return null; // author not found
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

            if (event.isEndElement()) {
                EndElement endElement = event.asEndElement();
                if (ElementChecker.isElementWith(endElement, "div")) {
                    return 0; // num of pages not found
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

    private String getAuthorLabel(String text) {
        for (String authorLabel : authorLabels) {
            if (text.contains(authorLabel)) {
                return authorLabel;
            }
        }
        return null;
    }
}
