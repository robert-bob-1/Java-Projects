package presentation;

import bll.Bill;
import bll.ClientBLL;
import bll.OrderBLL;
import bll.ProductBLL;
import model.Client;
import model.Order;
import model.Product;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
/**
*  The Controller is the highest level class from the project.
 *  Here we manage the button actions from all frames.
 *
*/

public class Controller {
    private View  view;
    private ClientBLL clientBLL;
    private ProductBLL productBLL;
    private OrderBLL orderBLL;

    public Controller(View view) {
        this.clientBLL = new ClientBLL();
        this.productBLL = new ProductBLL();
        this.orderBLL = new OrderBLL();
        this.view  = view;

        //... Add listeners to the view.
        view.addClientListener(new AddClientListener());
        view.editClientListener(new EditClientListener());
        view.deleteClientListener(new DeleteClientListener());

        view.addProductListener(new AddProductListener());
        view.editProductListener(new EditProductListener());
        view.deleteProductListener(new DeleteProductListener());

        view.setOrderListener(new SetOrderListener());

        view.manageClientListener(new ManageClientListener());
        view.manageProductListener(new ManageProductListener());
        view.manageOrdersListener(new ManageOrdersListener());
    }
    class AddClientListener implements ActionListener {// works
        public void actionPerformed(ActionEvent e) {
            try {
                Client client = new Client (view.getClientName(), view.getClientAddress(), view.getClientEmail());
                clientBLL.insertClient(client);
                view.refreshTable(clientBLL.findAll(),view.getClientModel());
            } catch (Exception exception) {
                view.showError("Eroare de input. Va rugam reincercati. \nExceptie detectata: "+ exception);
            }
        }
    }

    class EditClientListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                Client oldClient = view.getSelected(new Client());
                clientBLL.updateClient(oldClient, new Client( view.getClientName(),
                                        view.getClientAddress(), view.getClientEmail()));
                view.refreshTable(clientBLL.findAll(),view.getClientModel());
            } catch (Exception exception) {
                view.showError("Eroare de input. Va rugam reincercati. \nExceptie detectata: "+ exception);
            }
        }
    }

    class DeleteClientListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                clientBLL.deleteClient(view.getSelected(new Client()));
                view.refreshTable(clientBLL.findAll(),view.getClientModel());
            } catch (Exception exception) {
                view.showError("Eroare de input. Va rugam reincercati. \nExceptie detectata: "+ exception);
            }
        }
    }

    class AddProductListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                Product product = new Product (view.getProductName(), Integer.parseInt(view.getProductQuantity()));
                productBLL.insertProduct(product);
                view.refreshTable(productBLL.getAll(), view.getProductModel());
            } catch (Exception exception) {
                view.showError("Eroare de input. Va rugam reincercati. \nExceptie detectata: "+ exception);
            }
        }
    }
    class EditProductListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                Product oldProduct = view.getSelected(new Product());
                productBLL.updateProduct(oldProduct, new Product( view.getProductName(),
                        Integer.parseInt(view.getProductQuantity())));
                view.refreshTable(productBLL.getAll(),view.getProductModel());
            } catch (Exception exception) {
                view.showError("Eroare de input. Va rugam reincercati. \nExceptie detectata: "+ exception);
            }
        }
    }

    class DeleteProductListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                productBLL.deleteProduct(view.getSelected(new Product()));
                view.refreshTable(productBLL.getAll(),view.getProductModel());
            } catch (Exception exception) {
                view.showError("Eroare de input. Va rugam reincercati. \nExceptie detectata: "+ exception);
            }
        }
    }

    class SetOrderListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                JTable productTable = view.getOrderProductTable();
                JTable clientTable = view.getOrderClientTable();
                int productRow = productTable.getSelectedRow();
                int clientRow = clientTable.getSelectedRow();
                int productId = (int) productTable.getValueAt(productRow, 0);
                int clientId = (int) clientTable.getValueAt(clientRow, 0);

                Client client = new Client (clientId,
                        (String) clientTable.getValueAt(clientRow, 1),
                        (String) clientTable.getValueAt(clientRow, 2),
                        (String) clientTable.getValueAt(clientRow, 3)
                        );
                Product product = new Product (productId,
                        (String) productTable.getValueAt(productRow, 1),
                        (Integer) productTable.getValueAt(productRow, 2)
                        );
                Order order = new Order( productId, clientId, Integer.parseInt(view.getOrderQuantity()));

                orderBLL.insertOrder (order, product);
                Bill.generateBill(client, product, order);
                ArrayList<Product> products = productBLL.getAll();
                view.refreshTable (products, view.getProductModel());
            } catch (Exception exception) {
                view.showError("Eroare de input. Va rugam reincercati. \nExceptie detectata: "+ exception);
            }
        }
    }


    class ManageClientListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                ArrayList<Client> clients=clientBLL.findAll();
                view.refreshTable(clients,view.getClientModel());
                view.viewClientFrame();
            } catch (Exception exception) {
                view.showError("Eroare de input. Va rugam reincercati. \nExceptie detectata: "+ exception);
            }
        }
    }

    class ManageProductListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                ArrayList<Product> products=productBLL.getAll();
                view.refreshTable(products,view.getProductModel());
                view.viewProductFrame();
            } catch (Exception exception) {
                view.showError("Eroare de input. Va rugam reincercati. \nExceptie detectata: "+ exception);
            }
        }
    }

    class ManageOrdersListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                ArrayList<Client> clients=clientBLL.findAll();
                view.refreshTable(clients,view.getClientModel());
                ArrayList<Product> products=productBLL.getAll();
                view.refreshTable(products,view.getProductModel());
                view.viewOrderFrame();
            } catch (Exception exception) {
                view.showError("Eroare de input. Va rugam reincercati. \nExceptie detectata: "+ exception);
            }
        }
    }

    public static void main(String[] args){
        View view = new View();
        Controller controller = new Controller(view);
    }
}
