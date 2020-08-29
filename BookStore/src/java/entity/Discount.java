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
@Table(name = "Discount", catalog = "BookStore", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Discount.findAll", query = "SELECT d FROM Discount d")
    , @NamedQuery(name = "Discount.findUnuseAllOfUser", query = "SELECT d FROM Discount d WHERE d.username = :username AND d.isUsed = false")
    , @NamedQuery(name = "Discount.findById", query = "SELECT d FROM Discount d WHERE d.id = :id")
    , @NamedQuery(name = "Discount.findByDiscountCode", query = "SELECT d FROM Discount d WHERE d.discountCode = :discountCode")
    , @NamedQuery(name = "Discount.findByDiscountPercent", query = "SELECT d FROM Discount d WHERE d.discountPercent = :discountPercent")
    , @NamedQuery(name = "Discount.findByCreatedDate", query = "SELECT d FROM Discount d WHERE d.createdDate = :createdDate")
    , @NamedQuery(name = "Discount.findByIsUsed", query = "SELECT d FROM Discount d WHERE d.isUsed = :isUsed")})
public class Discount implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "DiscountCode", nullable = false, length = 10)
    private String discountCode;
    @Basic(optional = false)
    @Column(name = "DiscountPercent", nullable = false)
    private double discountPercent;
    @Basic(optional = false)
    @Column(name = "CreatedDate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Basic(optional = false)
    @Column(name = "IsUsed", nullable = false)
    private boolean isUsed;
    @JoinColumn(name = "Username", referencedColumnName = "Username", nullable = false)
    @ManyToOne(optional = false)
    private UserAccount username;

    public Discount() {
    }

    public Discount(Integer id) {
        this.id = id;
    }

    public Discount(Integer id, String discountCode, double discountPercent, Date createdDate, boolean isUsed) {
        this.id = id;
        this.discountCode = discountCode;
        this.discountPercent = discountPercent;
        this.createdDate = createdDate;
        this.isUsed = isUsed;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(double discountPercent) {
        this.discountPercent = discountPercent;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public boolean getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }

    public UserAccount getUsername() {
        return username;
    }

    public void setUsername(UserAccount username) {
        this.username = username;
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
        if (!(object instanceof Discount)) {
            return false;
        }
        Discount other = (Discount) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Discount[ id=" + id + " ]";
    }

}
