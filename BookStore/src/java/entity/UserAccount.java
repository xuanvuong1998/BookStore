/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author TiTi
 */
@Entity
@Table(name = "UserAccount", catalog = "BookStore", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserAccount.findAll", query = "SELECT u FROM UserAccount u")
    , @NamedQuery(name = "UserAccount.findByUsername", query = "SELECT u FROM UserAccount u WHERE u.username = :username")
    , @NamedQuery(name = "UserAccount.findByPassword", query = "SELECT u FROM UserAccount u WHERE u.password = :password")
    , @NamedQuery(name = "UserAccount.findByFullname", query = "SELECT u FROM UserAccount u WHERE u.fullname = :fullname")
    , @NamedQuery(name = "UserAccount.findByIsAdmin", query = "SELECT u FROM UserAccount u WHERE u.isAdmin = :isAdmin")})
public class UserAccount implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "username")
    private Collection<ShoppingOrder> shoppingOrderCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Username", nullable = false, length = 100)
    private String username;
    @Basic(optional = false)
    @Column(name = "Password", nullable = false, length = 50)
    private String password;
    @Column(name = "Fullname", length = 50)
    private String fullname;
    @Basic(optional = false)
    @Column(name = "IsAdmin", nullable = false)
    private boolean isAdmin;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "username")
    private Collection<Discount> discountCollection;

    public UserAccount() {
    }

    public UserAccount(String username) {
        this.username = username;
    }

    public UserAccount(String username, String password, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    @XmlTransient
    public Collection<Discount> getDiscountCollection() {
        return discountCollection;
    }

    public void setDiscountCollection(Collection<Discount> discountCollection) {
        this.discountCollection = discountCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (username != null ? username.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserAccount)) {
            return false;
        }
        UserAccount other = (UserAccount) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.UserAccount[ username=" + username + " ]";
    }

    @XmlTransient
    public Collection<ShoppingOrder> getShoppingOrderCollection() {
        return shoppingOrderCollection;
    }

    public void setShoppingOrderCollection(Collection<ShoppingOrder> shoppingOrderCollection) {
        this.shoppingOrderCollection = shoppingOrderCollection;
    }

}
