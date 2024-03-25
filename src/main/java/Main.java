import models.Address;
import models.Client;
import models.ClientAccount;
import models.Deposit;

import services.ClientService;
import services.DepositService;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static List<Client> clients;
    private static List<Deposit> deposits;

    public static void main(String[] args) throws SQLException {
        showMainMenu();
    }

    private static void showMainMenu() {
        System.out.println("******************");
        System.out.println("Choose an option:");
        System.out.println("0. Exit");
        System.out.println("1. Clients");
        System.out.println("2. Deposits");
        System.out.println("******************");

        int choice = getUserChoice();
        switch (choice) {
            case 1:
                showClientsMenu();
                break;
            case 2:
                showDepositsMenu();
                break;
            case 0:
                System.out.println("Exiting...");
                break;
            default:
                System.out.println("\nInvalid choice. Please try again.\n");
                showMainMenu();
        }
    }

    private static void showClientsMenu() {
        ClientService clientService = new ClientService();

        while (true) {
            System.out.println("Choose what you want to do:");
            System.out.println("0. Back to Main Menu");
            System.out.println("Client's Data:");
            System.out.println("1. Get All Clients");
            System.out.println("2. Add new Client");
            System.out.println("3. Update Client Data");
            System.out.println("4. Delete Client");
            System.out.println("5. Find Clients by Fullname");
            System.out.println("6. Find Clients by Passport Data");
            System.out.println("7. Find Clients by Address");
            System.out.println("Client's Account:");
            System.out.println("8. Get Client Account");
            System.out.println("9. Add new Client Account");
            System.out.println("10. Update Client Account");
            System.out.println("11. Delete Client Account");

            int choice = getUserChoice();
            switch (choice) {
                case 0:
                    showMainMenu();
                    return;
                case 1:
                    getAllClients(clientService);
                    break;
                case 2:
                    addNewClient(clientService);
                    break;
                case 3:
                    updateClientData(clientService);
                    break;
                case 4:
                    deleteClient(clientService);
                    break;
                case 5:
                    findClientsByFullname(clientService);
                    break;
                case 6:
                    findClientsByPassportData(clientService);
                    break;
                case 7:
                    findClientsByAddress(clientService);
                    break;
                case 8:
                    getClientAccount();
                    break;
                case 9:
                    addNewClientAccount(clientService);
                    break;
                case 10:
                    updateClientAccount(clientService);
                    break;
                case 11:
                    deleteClientAccount(clientService);
                    break;
                default:
                    System.out.println("\nInvalid choice. Please try again.\n");
            }
        }
    }

    private static void getAllClients(ClientService clientService) {
        if (clients != null && !clients.isEmpty()) {
            printAllClients();
        } else {
            clients = clientService.findAllClients();
            if (clients.isEmpty()) {
                System.out.println("No clients found.");
            } else {
                for (Client client : clients) {
                    client.setAddress(clientService.findAddress(client.getId()));
                }
                printAllClients();
            }
        }
    }

    private static void addNewClient(ClientService clientService) {
        System.out.print("Enter first name: ");
        String firstName = scanner.next();
        System.out.print("Enter last name: ");
        String lastName = scanner.next();
        System.out.print("Enter patronymic: ");
        String patronymic = scanner.next();
        System.out.print("Enter passport series: ");
        int passportSeries = scanner.nextInt();
        System.out.print("Enter passport number: ");
        int passportNumber = scanner.nextInt();
        System.out.print("Enter phone number (format: 79x(9)): ");
        long phoneNumber = scanner.nextLong();
        System.out.println("Enter address: ");
        System.out.print("City: ");
        String city = scanner.next();
        System.out.print("Street: ");
        String street = scanner.next();
        System.out.print("House number: ");
        Integer houseNumber = scanner.nextInt();
        Client newClient = new Client(firstName, lastName, patronymic, passportSeries, passportNumber, phoneNumber);
        Address newAddress = new Address(city, street, houseNumber);
        newClient.setAddress(newAddress);
        newAddress.setClient(newClient);
        clientService.insertClient(newClient, newAddress);
        clients.add(newClient);
        System.out.println("\nClient inserted successfully.\n");
    }

    private static void updateClientData(ClientService clientService) {
        if (clients != null && !clients.isEmpty()) {
            printAllClients();
            boolean f = false;
            int clientNumber = 0;
            while (!f) {
                System.out.print("Enter client's serial number: ");
                clientNumber = getUserChoice();
                if (clientNumber < 1 || clientNumber > clients.size())
                    System.out.println("Invalid serial number. Try again.");
                else f = true;
            }
            System.out.println("Choose what you want to update: ");
            System.out.println("0. Exit");
            System.out.println("1. First name");
            System.out.println("2. Last name");
            System.out.println("3. Patronymic");
            System.out.println("4. Passport series");
            System.out.println("5. Passport number");
            System.out.println("6. Phone number");
            System.out.println("7. Address");
            int userChoice = getUserChoice();
            Client client = clients.get(clientNumber - 1);
            switch (userChoice) {
                case 0:
                    showClientsMenu();
                    break;
                case 1:
                    System.out.print("Enter new first name: ");
                    String newFirstName = scanner.next();
                    client.setFirstName(newFirstName);
                    clientService.updateClient(client, client.getAddress());
                    System.out.println("\nFirst name updated successfully.\n");
                    break;
                case 2:
                    System.out.print("Enter new last name: ");
                    String newLastName = scanner.next();
                    client.setLastName(newLastName);
                    clientService.updateClient(client, client.getAddress());
                    System.out.println("\nLast name updated successfully.\n");
                    break;
                case 3:
                    System.out.print("Enter new patronymic: ");
                    String newPatronymic = scanner.next();
                    client.setPatronymic(newPatronymic);
                    clientService.updateClient(client, client.getAddress());
                    System.out.println("\nPatronymic updated successfully.\n");
                    break;
                case 4:
                    System.out.print("Enter new passport series (4 numbers): ");
                    int newPassportSeries = scanner.nextInt();
                    client.setPassportSeries(newPassportSeries);
                    clientService.updateClient(client, client.getAddress());
                    System.out.println("\nPassport series updated successfully.\n");
                    break;
                case 5:
                    System.out.print("Enter new passport number (6 numbers): ");
                    int newPassportNumber = scanner.nextInt();
                    client.setPassportNumber(newPassportNumber);
                    clientService.updateClient(client, client.getAddress());
                    System.out.println("\nPassport number updated successfully.\n");
                    break;
                case 6:
                    System.out.print("Enter new phone number (79x(9)): ");
                    long newPhoneNumber = scanner.nextLong();
                    client.setPhoneNumber(newPhoneNumber);
                    clientService.updateClient(client, client.getAddress());
                    System.out.println("\nPhone number updated successfully.\n");
                    break;
                case 7:
                    System.out.println("Enter new address: ");
                    System.out.print("City: ");
                    String newCity = scanner.next();
                    System.out.print("Street: ");
                    String newStreet = scanner.next();
                    System.out.print("House number: ");
                    int newHouseNumber = scanner.nextInt();
                    client.getAddress().setCity(newCity);
                    client.getAddress().setStreet(newStreet);
                    client.getAddress().setHouseNumber(newHouseNumber);
                    clientService.updateClient(client, client.getAddress());
                    System.out.println("\nAddress updated successfully.\n");
                    break;
                default:
                    System.out.println("\nInvalid choice. Please try again.\n");
                    break;
            }
        } else {
            System.out.println("\n! First, get a list of clients !\n");
        }
    }

    private static void deleteClient(ClientService clientService) {
        if (clients != null && !clients.isEmpty()) {
            printAllClients();
            boolean f = false;
            int clientNumber = 0;
            while (!f) {
                System.out.print("Enter client's serial number: ");
                clientNumber = getUserChoice();
                if (clientNumber < 1 || clientNumber > clients.size())
                    System.out.print("Invalid serial number. Try again.");
                else f = true;
            }
            Client client = clients.get(clientNumber - 1);
            System.out.print("Are you sure? (yes - 1/no - 0) ");
            int userChoice = getUserChoice();
            if (userChoice == 1) {
                try {
                    clientService.deleteClient(client);
                    System.out.println("\nClient deleted successfully.\n");
                    clients.remove(client);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("\n! First, get a list of clients !\n");
        }
    }

    private static void findClientsByFullname(ClientService clientService) {
        System.out.println("Enter the full or partial name of the client: ");
        String searchName = scanner.next();
        List<Client> foundClients = clientService.findClientsByFullname(searchName);
        System.out.println("Found client(s):\n");
        for (Client client : foundClients) {
            System.out.println(client.toString());
            System.out.println(client.getAddress().toString());
        }
    }

    private static void findClientsByPassportData(ClientService clientService) {
        System.out.println("Enter the passport series or number of the client: ");
        String searchPassport = scanner.next();
        List<Client> foundClients = clientService.findClientsByPassportData(searchPassport);
        System.out.println("Found client(s):\n");
        for (Client client : foundClients) {
            System.out.println(client.toString());
            System.out.println(client.getAddress().toString());
        }
    }

    private static void findClientsByAddress(ClientService clientService) {
        System.out.println("Enter the address (city, street or house number) of the client: ");
        String searchAddress = scanner.next();
        List<Address> foundAddresses = clientService.findClientsByAddress(searchAddress);
        System.out.println("Found client(s):\n");
        for (Address address : foundAddresses) {
            System.out.print(clientService.findClient(address.getClient().getId()).toString());
            System.out.println(address.toString());
        }
    }

    private static void printAllClients() {
        for (int i = 0; i < clients.size(); i++) {
            Client client = clients.get(i);
            System.out.println("Client #" + (i + 1) + ":");
            System.out.print(client.toString());
            System.out.println(client.getAddress().toString());
        }
        System.out.println();
    }

    private static void getClientAccount() {
        if (clients == null || clients.isEmpty()) {
            System.out.println("\n! First, get a list of clients !\n");
        } else {
            printAllClients();
            boolean f = false;
            int clientNumber = 0;
            while (!f) {
                System.out.print("Enter client's serial number: ");
                clientNumber = getUserChoice();
                if (clientNumber < 1 || clientNumber > clients.size())
                    System.out.println("Invalid serial number. Try again.");
                else f = true;
            }
            List<ClientAccount> clientAccounts = clients.get(clientNumber - 1).getClientAccounts();
            if (clientAccounts.isEmpty()) {
                System.out.println("No client's account(s) found.");
            } else {
                System.out.println();
                for (ClientAccount clientAccount : clientAccounts) {
                    System.out.println(clientAccount.toString());
                }
                System.out.println();
            }
        }
    }

    private static void addNewClientAccount(ClientService clientService) {
        boolean f = false;
        int clientNumber = 0;
        int depositNumber = 0;
        if (clients == null || clients.isEmpty()) {
            System.out.println("\n! First, get a list of clients !\n");
        } else {
            printAllClients();
            while (!f) {
                System.out.print("Enter client's serial number: ");
                clientNumber = getUserChoice();
                if (clientNumber < 1 || clientNumber > clients.size())
                    System.out.print("Invalid serial number. Try again.");
                else f = true;
            }
        }
        if (deposits == null || deposits.isEmpty()) {
            System.out.println("\n! First, get a list of deposits !\n");
        } else {
            printAllDeposits();
            f = false;
            while (!f) {
                System.out.print("Enter deposit's serial number: ");
                depositNumber = getUserChoice();
                if (depositNumber < 1 || depositNumber > deposits.size())
                    System.out.print("Invalid serial number. Try again.");
                else f = true;
            }
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateOpen = null;
        Date dateClose = null;
        f = false;
        while (!f) {
            System.out.print("Enter opening date (format yyyy-MM-dd): ");
            String stringDateOpen = scanner.next();
            System.out.print("Enter closing date (format yyyy-MM-dd): ");
            String stringDateClose = scanner.next();
            try {
                dateOpen = dateFormat.parse(stringDateOpen);
                dateClose = dateFormat.parse(stringDateClose);
                f = true;
            } catch (ParseException e) {
                System.out.println("There was an error parsing the date. Ensure that the input format is as expected.");
            }
        }
        System.out.print("Enter invested amount: ");
        long amount = scanner.nextLong();

        ClientAccount newClientAccount = new ClientAccount(dateOpen, dateClose, amount);
        deposits.get(depositNumber - 1).addClientAccount(newClientAccount);
        clients.get(clientNumber - 1).addClientAccount(newClientAccount);
        clientService.insertAccount(newClientAccount);
        System.out.println("\nClient account inserted successfully.\n");
    }

    private static void updateClientAccount(ClientService clientService) {
        if (clients != null && !clients.isEmpty()) {
            printAllClients();
            boolean f = false;
            int clientNumber = 0;
            while (!f) {
                System.out.print("Enter client's serial number: ");
                clientNumber = getUserChoice();
                if (clientNumber < 1 || clientNumber > clients.size())
                    System.out.println("Invalid serial number. Try again.");
                else f = true;
            }
            Client client = clients.get(clientNumber - 1);

            List<ClientAccount> clientAccounts = client.getClientAccounts();
            printClientAccounts(clientAccounts);
            f = false;
            int accountNumber = 0;
            while (!f) {
                System.out.print("Enter client account's serial number: ");
                accountNumber = getUserChoice();
                if (accountNumber < 1 || accountNumber > clientAccounts.size())
                    System.out.println("Invalid serial number. Try again.");
                else f = true;
            }
            ClientAccount clientAccount = clientAccounts.get(accountNumber - 1);
            System.out.println("Choose what you want to update: ");
            System.out.println("0. Exit");
            System.out.println("1. Closing date");
            System.out.println("2. Invested amount");
            int userChoice = getUserChoice();
            switch (userChoice) {
                case 0:
                    showClientsMenu();
                    break;
                case 1:
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date newDateClose = null;
                    System.out.print("Enter closing date (format yyyy-MM-dd): ");
                    String newStringDateClose = scanner.next();
                    try {
                        newDateClose = dateFormat.parse(newStringDateClose);
                    } catch (ParseException e) {
                        System.out.println("There was an error parsing the date. Ensure that the input format is as expected.");
                    }
                    clientAccount.setDateClose(newDateClose);
                    client.setClientAccounts(clientAccounts);
                    clientService.updateAccount(clientAccount);
                    System.out.println("\nAccount closing date updated successfully.\n");
                    break;
                case 2:
                    System.out.print("Enter invested amount: ");
                    long newAmount = scanner.nextLong();
                    clientAccount.setAmount(newAmount);
                    client.setClientAccounts(clientAccounts);
                    clientService.updateAccount(clientAccount);
                    System.out.println("\nInvested amount updated successfully.\n");
                    break;
                default:
                    System.out.println("\nInvalid choice. Please try again.\n");
                    break;
            }
        } else {
            System.out.println("\n! First, get a list of clients !\n");
        }
    }

    private static void printClientAccounts(List<ClientAccount> clientAccounts) {
        ClientAccount clientAccount;
        System.out.println();
        for (int i = 0; i < clientAccounts.size(); i++) {
            clientAccount = clientAccounts.get(i);
            System.out.println("Client Account #" + (i + 1) + ":");
            System.out.println(clientAccount.toString());
        }
        System.out.println();
    }

    private static void deleteClientAccount(ClientService clientService) {
        if (clients != null && !clients.isEmpty()) {
            printAllClients();
            boolean f = false;
            int clientNumber = 0;
            while (!f) {
                System.out.print("Enter client's serial number: ");
                clientNumber = getUserChoice();
                if (clientNumber < 1 || clientNumber > clients.size())
                    System.out.print("Invalid serial number. Try again.");
                else f = true;
            }
            Client client = clients.get(clientNumber - 1);
            List<ClientAccount> clientAccounts = client.getClientAccounts();
            printClientAccounts(clientAccounts);
            f = false;
            int accountNumber = 0;
            while (!f) {
                System.out.print("Enter client account's serial number: ");
                accountNumber = getUserChoice();
                if (accountNumber < 1 || accountNumber > clientAccounts.size())
                    System.out.println("Invalid serial number. Try again.");
                else f = true;
            }
            ClientAccount clientAccount = clientAccounts.get(accountNumber - 1);
            System.out.print("Are you sure? (yes - 1/no - 0) ");
            int userChoice = getUserChoice();
            if (userChoice == 1) {
                try {
                    clientService.deleteAccount(clientAccount);
                    System.out.println("\nClient Account deleted successfully.\n");
                    client.removeClientAccount(clientAccount);
                    Deposit deposit = clientAccount.getDeposit();
                    deposit.removeClientAccount(clientAccount);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("\n! First, get a list of clients !\n");
        }
    }

    private static void showDepositsMenu() {
        DepositService depositService = new DepositService();

        while (true) {
            System.out.println("Deposits Menu:");
            System.out.println("0. Back to Main Menu");
            System.out.println("1. Get All Deposits");
            System.out.println("2. Add new Deposit");
            System.out.println("3. Update Deposit");
            System.out.println("4. Delete Deposit");
            System.out.println("5. Get Client Accounts");
            System.out.println("6. Find Deposits by Name");

            int choice = getUserChoice();
            switch (choice) {
                case 0:
                    showMainMenu();
                    return;
                case 1:
                    getAllDeposits(depositService);
                    break;
                case 2:
                    addNewDeposit(depositService);
                    break;
                case 3:
                    updateDeposit(depositService);
                    break;
                case 4:
                    deleteDeposit(depositService);
                    break;
                case 5:
                    getClientAccounts(depositService);
                    break;
                case 6:
                    findDepositsByName(depositService);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void getAllDeposits(DepositService depositService) {
        if (deposits != null && !deposits.isEmpty()) {
            printAllDeposits();
        } else {
            deposits = depositService.findAllDeposits();
            if (deposits.isEmpty()) {
                System.out.println("No deposits found.");
            } else {
                printAllDeposits();
            }
        }
    }

    private static void printAllDeposits() {
        Deposit deposit;
        for (int i = 0; i < deposits.size(); i++) {
            deposit = deposits.get(i);
            System.out.println("Deposit #" + (i + 1) + ":");
            System.out.println(deposit.toString());
        }
        System.out.println();
    }

    private static void addNewDeposit(DepositService depositService) {
        System.out.print("Enter name: ");
        String depositName = scanner.next();
        System.out.print("Enter storage period (month): ");
        int period = scanner.nextInt();
        System.out.print("Enter rate, %: ");

        while (!scanner.hasNextDouble()) {
            System.out.println("Error! Enter the correct value for the rate.");
            scanner.next();
        }
        double rate = scanner.nextDouble();
        Deposit newDeposit = new Deposit(depositName, period, rate);
        depositService.insertDeposit(newDeposit);
        deposits.add(newDeposit);
        System.out.println("\nDeposit inserted successfully.\n");
    }

    private static void updateDeposit(DepositService depositService) {
        if (deposits != null && !deposits.isEmpty()) {
            printAllDeposits();
            boolean f = false;
            int depositNumber = 0;
            while (!f) {
                System.out.print("Enter deposit's serial number: ");
                depositNumber = getUserChoice();
                if (depositNumber < 1 || depositNumber > deposits.size())
                    System.out.println("Invalid serial number. Try again.");
                else f = true;
            }
            System.out.println("Choose what you want to update: ");
            System.out.println("0. Exit");
            System.out.println("1. Name");
            System.out.println("2. Storage period");
            System.out.println("3. Rate");
            int userChoice = getUserChoice();
            Deposit deposit = deposits.get(depositNumber - 1);
            switch (userChoice) {
                case 0:
                    showDepositsMenu();
                    break;
                case 1:
                    System.out.print("Enter new name: ");
                    String newName = scanner.next();
                    deposit.setName(newName);
                    depositService.updateDeposit(deposit);
                    System.out.println("\nName updated successfully.\n");
                    break;
                case 2:
                    System.out.print("Enter new storage period: ");
                    int newPeriod = scanner.nextInt();
                    deposit.setPeriod(newPeriod);
                    depositService.updateDeposit(deposit);
                    System.out.println("\nStorage period updated successfully.\n");
                    break;
                case 3:
                    System.out.print("Enter new rate: ");
                    double newRate = scanner.nextDouble();
                    deposit.setRate(newRate);
                    depositService.updateDeposit(deposit);
                    System.out.println("\nRate updated successfully.\n");
                    break;
                default:
                    System.out.println("\nInvalid choice. Please try again.\n");
                    break;
            }
        } else {
            System.out.println("\n! First, get a list of deposits !\n");
        }
    }

    private static void deleteDeposit(DepositService depositService) {
        if (deposits != null && !deposits.isEmpty()) {
            printAllDeposits();
            boolean f = false;
            int depositNumber = 0;
            while (!f) {
                System.out.print("Enter deposit's serial number: ");
                depositNumber = getUserChoice();
                if (depositNumber < 1 || depositNumber > deposits.size())
                    System.out.print("Invalid serial number. Try again.");
                else f = true;
            }
            Deposit deposit = deposits.get(depositNumber - 1);
            System.out.print("Are you sure? (yes - 1/no - 0) ");
            int userChoice = getUserChoice();
            if (userChoice == 1) {
                try {
                    depositService.deleteDeposit(deposit);
                    System.out.println("\nDeposit deleted successfully.\n");
                    deposits.remove(deposit);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("\n! First, get a list of deposits !\n");
        }
    }

    private static void getClientAccounts(DepositService depositService) {
        if (deposits == null || deposits.isEmpty()) {
            System.out.println("\n! First, get a list of deposits !\n");
        } else {
            printAllDeposits();
            boolean f = false;
            int depositNumber = 0;
            while (!f) {
                System.out.print("Enter deposit's serial number: ");
                depositNumber = getUserChoice();
                if (depositNumber < 1 || depositNumber > deposits.size())
                    System.out.println("Invalid serial number. Try again.");
                else f = true;
            }
            List<ClientAccount> clientAccounts = depositService.findAccountByDeposit(deposits.get(depositNumber - 1).getId());
            if (clientAccounts.isEmpty()) {
                System.out.println("No client's account(s) found.");
            } else {
                System.out.println();
                for (ClientAccount clientAccount : clientAccounts) {
                    System.out.println(clientAccount.toString());
                }
                System.out.println();
            }
        }
    }

    private static void findDepositsByName(DepositService depositService) {
        System.out.println("Enter the full or partial name of the deposit: ");
        String searchName = scanner.next();
        List<Deposit> foundDeposits = depositService.searchDeposits(searchName);
        System.out.println("Found deposit(s):\n");
        for (Deposit deposit : foundDeposits) {
            System.out.println(deposit.toString());
        }
        System.out.println();
    }

    private static int getUserChoice() {
        try {
            return scanner.nextInt();
        } catch (java.util.InputMismatchException e) {
            scanner.nextLine();
            return -1;
        }
    }
}
