package dao;

import models.Deposit;
import models.ClientAccount;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateSessionFactoryUtil;

import java.util.List;

public class DepositDAO {
    public Deposit findById(int id) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            return session.get(Deposit.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Deposit> findAll() {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Deposit", Deposit.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<ClientAccount> findAccountsByDeposit(int id) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            return session
                    .createQuery("FROM ClientAccount AS CA WHERE CA.deposit.id = :depositId", ClientAccount.class)
                    .setParameter("depositId", id)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void insert(Deposit deposit) {
        Transaction tx1 = null;
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            tx1 = session.beginTransaction();
            session.persist(deposit);
            tx1.commit();
        } catch (Exception e) {
            if (tx1 != null) {
                tx1.rollback();
            }
            e.printStackTrace();
        }
    }

    public void update(Deposit deposit) {
        Transaction tx1 = null;
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            tx1 = session.beginTransaction();
            session.merge(deposit);
            tx1.commit();
        } catch (Exception e) {
            if (tx1 != null) {
                tx1.rollback();
            }
            e.printStackTrace();
        }
    }

    public void delete(Deposit deposit) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = null;
        Deposit depositToDelete = session.get(Deposit.class, deposit.getId());
        try {
            tx1 = session.beginTransaction();
            session.remove(depositToDelete);
            tx1.commit();
        } catch (Exception e) {
            if (tx1 != null) {
                tx1.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public List<Deposit> searchDepositsByName(String searchTerm) {
        String hql = "FROM Deposit AS D WHERE " +
                "D.name LIKE :searchTerm";

        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery(hql, Deposit.class)
                    .setParameter("searchTerm", "%" + searchTerm + "%")
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
