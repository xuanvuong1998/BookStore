/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name = "Book", catalog = "BookStore", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Book.findAll", query = "SELECT b FROM Book b")
    , @NamedQuery(name = "Book.findById", query = "SELECT b FROM Book b WHERE b.id = :id")
    , @NamedQuery(name = "Book.findByName", query = "SELECT b FROM Book b WHERE b.name = :name")
    , @NamedQuery(name = "Book.findByNameAndRangePrice", query = "SELECT b FROM Book b WHERE b.name LIKE :name AND b.price >= :minPrice AND b.price <= :maxPrice AND b.isActive = TRUE AND b.quantity > 0")   
    , @NamedQuery(name = "Book.findByImage", query = "SELECT b FROM Book b WHERE b.image = :image")
    , @NamedQuery(name = "Book.findByDescription", query = "SELECT b FROM Book b WHERE b.description = :description")
    , @NamedQuery(name = "Book.findByPrice", query = "SELECT b FROM Book b WHERE b.price = :price")
    , @NamedQuery(name = "Book.findByAuthor", query = "SELECT b FROM Book b WHERE b.author = :author")
    , @NamedQuery(name = "Book.findByImportDate", query = "SELECT b FROM Book b WHERE b.importDate = :importDate")
    , @NamedQuery(name = "Book.findByQuantity", query = "SELECT b FROM Book b WHERE b.quantity = :quantity")
    , @NamedQuery(name = "Book.findByIsActive", query = "SELECT b FROM Book b WHERE b.isActive = :isActive")})
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "Name", nullable = false, length = 500)
    private String name;
    @Column(name = "Image", length = 500)
    private String image;
    @Column(name = "Description", length = 2147483647)
    private String description;
    @Basic(optional = false)
    @Column(name = "Price", nullable = false)
    private double price;
    @Column(name = "Author", length = 200)
    private String author;
    @Column(name = "ImportDate")
    @Temporal(TemporalType.DATE)
    private Date importDate;
    @Basic(optional = false)
    @Column(name = "Quantity", nullable = false)
    private int quantity;
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

    public Book(Integer id, String name, double price, int quantity, boolean isActive) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.isActive = isActive;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

}
