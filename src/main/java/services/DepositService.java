package services;

import dao.DepositDAO;
import models.Deposit;
import models.ClientAccount;

import java.util.List;

public class DepositService {
    private final DepositDAO depositsDao = new DepositDAO();

    public DepositService() {
    }

    public Deposit findDeposit(int id) {
        return depositsDao.findById(id);
    }

    public List<Deposit> findAllDeposits() {
        return depositsDao.findAll();
    }

    public List<ClientAccount> findAccountByDeposit(int id) {
        return depositsDao.findAccountsByDeposit(id);
    }

    public void insertDeposit(Deposit deposit) {
        depositsDao.insert(deposit);
    }

    public void updateDeposit(Deposit deposit) {
        depositsDao.update(deposit);
    }

    public void deleteDeposit(Deposit deposit) {
        depositsDao.delete(deposit);
    }

    public List<Deposit> searchDeposits(String searchTerm) {
        return depositsDao.searchDepositsByName(searchTerm);
    }
}
