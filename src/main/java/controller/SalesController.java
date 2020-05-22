package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customers;
import model.Products;
import model.Sales;
import model.SalesDet;

public class SalesController {
    private final DataController dataController = DataController.getInstance();

    @FXML
    private DatePicker datepicker;

    @FXML
    private Button previousWeekButton;

    @FXML
    private Button nextWeekButton;

    @FXML
    private Button dateTodayButton;

    @FXML
    private TableColumn<Sales, String> idColumn;

    @FXML
    private TableColumn<SalesDet, String> soldItemsColumn;

    @FXML
    private TableColumn<Customers, String> customerColumn;

    @FXML
    private TableColumn<Products, Double> priceColumn;

    @FXML
    private TableColumn<Customers, Double> totalColumn;

    @FXML
    private TableColumn<Sales, Double> amountPaidColumn;

    private ObservableList<Sales> sales = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        soldItemsColumn.setCellValueFactory(new PropertyValueFactory<>("summary"));
        customerColumn.setCellValueFactory(new PropertyValueFactory<>("custname"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));
        amountPaidColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

        sales.addAll(dataController.getSales());
    }
}
