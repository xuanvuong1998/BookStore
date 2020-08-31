/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "Book", catalog = "BookStore", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Book.findAll", query = "SELECT b FROM Book b ORDER BY b.clickCount DESC")
    , @NamedQuery(name = "Book.findById", query = "SELECT b FROM Book b WHERE b.id = :id")
    , @NamedQuery(name = "Book.findByName", query = "SELECT b FROM Book b WHERE b.name = :name")
    , @NamedQuery(name = "Book.findByNameAndRangePriceActive", query = "SELECT b FROM Book b WHERE b.name LIKE :name AND b.price >= :minPrice AND b.price <= :maxPrice AND b.isActive = TRUE AND b.quantity > 0 ORDER BY b.clickCount DESC")
    , @NamedQuery(name = "Book.findByNameAndRangePrice", query = "SELECT b FROM Book b WHERE b.name LIKE :name AND b.price >= :minPrice AND b.price <= :maxPrice AND b.quantity > 0 ORDER BY b.clickCount DESC")
    , @NamedQuery(name = "Book.findByImage", query = "SELECT b FROM Book b WHERE b.image = :image")
    , @NamedQuery(name = "Book.findByDescription", query = "SELECT b FROM Book b WHERE b.description = :description")
    , @NamedQuery(name = "Book.findByPrice", query = "SELECT b FROM Book b WHERE b.price = :price")
    , @NamedQuery(name = "Book.findByAuthor", query = "SELECT b FROM Book b WHERE b.author = :author")
    , @NamedQuery(name = "Book.findByLink", query = "SELECT b FROM Book b WHERE b.link = :link")
    , @NamedQuery(name = "Book.findByImportDate", query = "SELECT b FROM Book b WHERE b.importDate = :importDate")
    , @NamedQuery(name = "Book.findByQuantity", query = "SELECT b FROM Book b WHERE b.quantity = :quantity")
    , @NamedQuery(name = "Book.findByIsActive", query = "SELECT b FROM Book b WHERE b.isActive = :isActive")})
public class Book implements Serializable {

    @Column(name = "ClickCount")
    private Integer clickCount;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Price", precision = 53)
    private Double price;
    @Column(name = "Quantity")
    private Integer quantity;
    @Column(name = "ISBN", length = 100)
    private String isbn;
    @Column(name = "NumOfPages")
    private Integer numOfPages;
    @Column(name = "Link", length = 500)
    private String link;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bookId")
    private Collection<OrderDetails> orderDetailsCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "Name", nullable = false, length = 500)
    private String name;
    @Column(name = "Image", length = 500)
    private String image;
    @Column(name = "Description", length = 2147483647)
    private String description;
    @Column(name = "Author", length = 200)
    private String author;
    @Column(name = "ImportDate")
    @Temporal(TemporalType.DATE)
    private Date importDate;
    @Basic(optional = false)
    @Column(name = "IsActive", nullable = false)
    private boolean isActive;
    @JoinColumn(name = "CategoryId", referencedColumnName = "Id", nullable = false)
    @ManyToOne(optional = false)
    private Category categoryId;

    public Book() {
    }

    public Book(Integer id) {
        this.id = id;
    }

    public Book(
            Integer id,
            String name,
            Double price,
            String author,
            String image,
            Integer quantity,
            String isbn,
            Integer numOfPages,
            String link,
            String description,
            Date importDate,
            boolean isActive,
            Category categoryId
    ) {
        this.price = price;
        this.quantity = quantity;
        this.isbn = isbn;
        this.numOfPages = numOfPages;
        this.link = link;
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
        this.author = author;
        this.importDate = importDate;
        this.isActive = isActive;
        this.categoryId = categoryId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getImportDate() {
        return importDate;
    }

    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Category getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Category categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Book)) {
            return false;
        }
        Book other = (Book) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Book[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<OrderDetails> getOrderDetailsCollection() {
        return orderDetailsCollection;
    }

    public void setOrderDetailsCollection(Collection<OrderDetails> orderDetailsCollection) {
        this.orderDetailsCollection = orderDetailsCollection;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getNumOfPages() {
        return numOfPages;
    }

    public void setNumOfPages(Integer numOfPages) {
        this.numOfPages = numOfPages;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getClickCount() {
        return clickCount;
    }

    public void setClickCount(Integer clickCount) {
        this.clickCount = clickCount;
    }

}
