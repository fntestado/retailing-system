package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import main.Main;
import model.Customers;
import model.Products;
import model.Sales;
import model.SalesDet;
import org.kordamp.ikonli.javafx.FontIcon;
import util.GuiUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class AddSaleController extends SalesController {
    private final DataController dataController = DataController.getInstance();
    private final ObservableList<Products> products = FXCollections.observableArrayList();
    private final ObservableList<Customers> customers = FXCollections.observableArrayList();
    private List<Sales> sales;

    @FXML
    private FontIcon closeDialog;

    @FXML
    private ComboBox<String> customersComboBox;

    @FXML
    private ComboBox<String> productsComboBox;

    @FXML
    private TextField quantityTextField;

    @FXML
    private VBox salesResultBox;

    @FXML
    private Label totalAmountOfSale;

    @FXML
    private void initialize() {
        products.addAll(dataController.getProducts());
        customers.addAll(dataController.getCustomers());

        for (Products product : products) {
            productsComboBox.getItems().add(product.getDescription());
        }

        for (Customers customer : customers) {
            customersComboBox.getItems().add(customer.getCustname());
        }
    }

    @FXML
    private void addProductOnAction() throws Exception {
        sales = new ArrayList<>();

        if (isRequiredFieldComplete()) {
            final String productDescription = productsComboBox.getValue();
            String productToParse = "id," + productDescription + ", 0.0";
            Products selectedProduct = Products.parseProduct(productToParse);
            selectedProduct = fetchProduct(selectedProduct);

            final int quantitySold = Integer.parseInt(quantityTextField.getText());
            final double productTotalAmountPerItem = selectedProduct.getPrice() * Integer.parseInt(quantityTextField.getText());
            String productInfo = productDescription + " x " + quantitySold + " = " + productTotalAmountPerItem; // TODO: use printf or String modifier
            salesResultBox.getChildren().add(new Label(productInfo));

            String sampleInvid = " ";
            Date invdate = Date.valueOf(LocalDate.now());

            SalesDet salesDet = new SalesDet(selectedProduct, quantitySold);

            String customerName = customersComboBox.getValue();
            String customerToParse = "0," + customerName + ",address,telno,0.0";
            Customers selectedCustomer = Customers.parseCustomer(customerToParse);
            selectedCustomer = fetchCustomer(selectedCustomer);

            sales.add(new Sales(sampleInvid, invdate, salesDet, selectedCustomer, productTotalAmountPerItem));

            double computedTotalAmountOfSale = Double.parseDouble(totalAmountOfSale.getText()) + productTotalAmountPerItem;
            totalAmountOfSale.setText(String.valueOf(computedTotalAmountOfSale));

            resetFields();
        }
    }

    private Products fetchProduct(Products product) throws Exception {
        final int index = products.indexOf(product);
        final boolean hasProduct = index > -1;

        if (hasProduct) {
            return products.get(index);
        }

        throw new Exception("No product found!");
    }

    private Customers fetchCustomer(Customers customer) throws Exception {
        final int index = customers.indexOf(customer);
        final boolean hasCustomer = index > -1;

        if (hasCustomer) {
            return customers.get(index);
        }

        throw new Exception("No customer found!");
    }

    @FXML
    private void closeDialogOnClicked() throws IOException {
        Stage current = (Stage) closeDialog.getScene().getWindow();

        current.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/SalesView.fxml"));
        Parent root = loader.load();
        Main.primaryStage.setScene(new Scene(root));
    }

    @FXML
    private void closeDialogOnMouseEntered() {
        closeDialog.setIconColor(Color.RED);
        GuiUtil.setCursorToHand(closeDialog);
    }

    @FXML
    private void closeDialogOnMouseExited() {
        closeDialog.setIconColor(Color.web("#7f8183"));
        GuiUtil.setCursorToDefault(closeDialog);
    }

    @FXML
    private void saveButtonOnAction() throws SQLException, IOException {
        for (Sales sale : sales) {
            dataController.saveSales(sale);
        }

        closeDialogOnClicked();
    }

    private boolean isRequiredFieldComplete() {
        return !productsComboBox.getItems().isEmpty() && !quantityTextField.getText().isEmpty()
                && !customersComboBox.getSelectionModel().isEmpty();
    }

    private void resetFields() {
        productsComboBox.getSelectionModel().clearSelection();
        quantityTextField.clear();
    }
}
