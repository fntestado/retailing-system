package controller;

import database.Data;
import model.Customers;
import model.Products;
import model.Sales;

import java.sql.SQLException;
import java.util.List;

public final class DataController {
    private static DataController instance = null;

    private DataController() {
        Data.setConnection();
    }

    public static DataController getInstance() {
        if (instance == null) {
            instance = new DataController();
        }

        return instance;
    }

    public void disconnectDatabase() throws Exception {
        Data.DbDone();
    }

    public List<Sales> getSales() {
        return Data.getSales();
    }

    public List<Products> getProducts() {
        return Data.getProducts();
    }

    public List<Customers> getCustomers() {
        return Data.getCustomers();
    }

    public void saveSales(Sales sale) throws SQLException {
        Data.saveSales(sale);
    }
}
