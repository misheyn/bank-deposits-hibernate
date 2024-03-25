package services;

import dao.ClientDAO;
import models.Address;
import models.Client;
import models.ClientAccount;

import java.util.List;

public class ClientService {
    private final ClientDAO clientsDao = new ClientDAO();

    public ClientService() {
    }

    public Client findClient(int id) {
        return clientsDao.findById(id);
    }

    public List<Client> findAllClients() {
        return clientsDao.findAll();
    }

    public Address findAddress(int id) {
        return clientsDao.findAddress(id);
    }

    public List<ClientAccount> findAccountByClient(int id) {
        return clientsDao.findAccountsByClient(id);
    }

    public void insertClient(Client client, Address address) {
        clientsDao.insert(client, address);
    }

    public void insertAccount(ClientAccount clientAccount) {
        clientsDao.addAccount(clientAccount);
    }

    public void updateClient(Client client, Address address) {
        clientsDao.update(client, address);
    }

    public void updateAccount(ClientAccount clientAccount) {
        clientsDao.updateAccount(clientAccount);
    }

    public void deleteClient(Client client) {
        clientsDao.delete(client);
    }

    public void deleteAccount(ClientAccount clientAccount) {
        clientsDao.removeAccount(clientAccount);
    }

    public List<Client> findClientsByFullname(String searchTerm) {
        return clientsDao.searchClientsByFullname(searchTerm);
    }

    public List<Client> findClientsByPassportData(String searchTerm) {
        return clientsDao.searchClientsByPassportData(searchTerm);
    }

    public List<Address> findClientsByAddress(String searchTerm) {
        return clientsDao.searchClientsByAddress(searchTerm);
    }
}
