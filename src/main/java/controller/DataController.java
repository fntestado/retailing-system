package controller;

import database.Data;
import model.Sales;

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
}
