/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import entity.UserAccount;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import utils.DBUtils;


public class UserAccountDAO {
public UserAccount getUserByUsername(String username) {
        EntityManager em = DBUtils.getEntityManager();
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            List<UserAccount> users = em.createNamedQuery("UserAccount.findByUsername")
                    .setParameter("username", username)
                    .getResultList();

            transaction.commit();

            if (!users.isEmpty()) {
                return users.get(0);
            }
        } catch (Exception e) {
            Logger.getLogger(UserAccountDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (em != null) {
                em.close();
            }
        }

        return null;
    }
}
