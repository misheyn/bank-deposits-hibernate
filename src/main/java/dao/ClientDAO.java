package dao;

import models.Address;
import models.Client;
import models.ClientAccount;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateSessionFactoryUtil;

import java.util.List;

public class ClientDAO {
    public Client findById(int id) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            return session.get(Client.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Client> findAll() {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Client", Client.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Address findAddress(int id) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Address AS A WHERE A.client.id = :clientId", Address.class)
                    .setParameter("clientId", id).getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<ClientAccount> findAccountsByClient(int id) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM ClientAccount AS CA WHERE CA.client.id = :clientId", ClientAccount.class)
                    .setParameter("clientId", id)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addAccount(ClientAccount clientAccount) {
        Transaction tx1 = null;
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            tx1 = session.beginTransaction();
            session.persist(clientAccount);
            tx1.commit();
        } catch (Exception e) {
            if (tx1 != null) {
                tx1.rollback();
            }
            e.printStackTrace();
        }
    }

    public void insert(Client client, Address address) {
        Transaction tx1 = null;
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            tx1 = session.beginTransaction();
            session.persist(address);
            session.persist(client);
            tx1.commit();
        } catch (Exception e) {
            if (tx1 != null) {
                tx1.rollback();
            }
            e.printStackTrace();
        }
    }

    public void update(Client client, Address address) {
        Transaction tx1 = null;
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            tx1 = session.beginTransaction();
            session.merge(address);
            session.merge(client);
            tx1.commit();
        } catch (Exception e) {
            if (tx1 != null) {
                tx1.rollback();
            }
            e.printStackTrace();
        }
    }

    public void updateAccount(ClientAccount clientAccount) {
        Transaction tx1 = null;
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            tx1 = session.beginTransaction();
            session.merge(clientAccount);
            tx1.commit();
        } catch (Exception e) {
            if (tx1 != null) {
                tx1.rollback();
            }
            e.printStackTrace();
        }
    }

    public void delete(Client client) {
        Transaction tx1 = null;
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            tx1 = session.beginTransaction();
            session.remove(client.getAddress());
            session.remove(client);
            tx1.commit();
        } catch (Exception e) {
            if (tx1 != null) {
                tx1.rollback();
            }
            e.printStackTrace();
        }
    }

    public void removeAccount(ClientAccount clientAccount) {
        Transaction tx1 = null;
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            tx1 = session.beginTransaction();
            session.remove(clientAccount);
            tx1.commit();
        } catch (Exception e) {
            if (tx1 != null) {
                tx1.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<Client> searchClientsByFullname(String searchTerm) {
        String hql = "FROM Client AS C WHERE " +
                "C.firstName LIKE :searchTerm OR " +
                "C.lastName LIKE :searchTerm OR " +
                "C.patronymic LIKE :searchTerm";

        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery(hql, Client.class)
                    .setParameter("searchTerm", "%" + searchTerm + "%")
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Client> searchClientsByPassportData(String searchTerm) {
        String hql = "FROM Client AS C WHERE " +
                "CAST(C.passportSeries AS string) LIKE :searchTerm OR " +
                "CAST(C.passportNumber AS string) LIKE :searchTerm";

        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery(hql, Client.class)
                    .setParameter("searchTerm", "%" + searchTerm + "%")
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Address> searchClientsByAddress(String searchTerm) {
        String hql = "FROM Address AS A WHERE " +
                "A.city LIKE :searchTerm OR " +
                "A.street LIKE :searchTerm OR " +
                "CAST(A.houseNumber AS string) LIKE :searchTerm";

        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery(hql, Address.class)
                    .setParameter("searchTerm", "%" + searchTerm + "%")
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
