package bll;

import bll.validators.EmailValidator;
import bll.validators.Validator;
import dao.ClientDAO;
import model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Business Logic for Client class (managing the DAO functions at a higher level)
 */

public class ClientBLL {
    private List<Validator<Client>> validators;
    private ClientDAO clientDAO;

    public ClientBLL() {
        validators = new ArrayList<Validator<Client>>();
        validators.add(new EmailValidator());
        clientDAO = new ClientDAO();
    }

    public Client findClientById(int id) {
        Client st = clientDAO.findById(id);
        if (st == null) {
            throw new NoSuchElementException("The client with id =" + id + " was not found!");
        }
        return st;
    }

    public ArrayList<Client> findAll() {
        ArrayList<Client> clients = (ArrayList<Client>) clientDAO.getAll();
        if (clients == null) {
            throw new NoSuchElementException("No clients were found.");
        }
        return clients;
    }


    public void insertClient(Client client){
        clientDAO.insert(client);
    }

    public void updateClient(Client oldClient, Client newClient){
        clientDAO.update(oldClient, newClient);
    }

    public void deleteClient(Client client){
        clientDAO.deleteAllOrders(client.getId());
        clientDAO.delete(client);
    }
}
