package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Sales;
import util.DateModifier;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class SalesController {
    private final DataController dataController = DataController.getInstance();
    private final ObservableList<Sales> sales = FXCollections.observableArrayList();

    @FXML
    private DatePicker datepicker;

    @FXML
    TableView<Sales> tableView;

    @FXML
    private TableColumn<Sales, String> idColumn;

    @FXML
    private TableColumn<Sales, String> soldItemsColumn;

    @FXML
    private TableColumn<Sales, String> customerColumn;

    @FXML
    private TableColumn<Sales, Double> priceColumn;

    @FXML
    private TableColumn<Sales, Double> totalColumn;

    @FXML
    private TableColumn<Sales, Double> amountPaidColumn;

    @FXML
    private void initialize() {
        initializeTableProperty();
        initializeDateProperty();
        refreshTable();
    }

    private void initializeTableProperty() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("invid"));
        soldItemsColumn.setCellValueFactory(new PropertyValueFactory<>("details"));
        customerColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("customerBalance"));
        amountPaidColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

    }

    private void initializeDateProperty() {
        datepicker.setValue(LocalDate.now());
    }

    @FXML
    private void datepickerOnAction() {
        String selectedDate = datepicker.getValue().toString();
        clearTableView();

        for (Sales sale : sales) {
            String saleDate = sale.getInvdate().toString();

            if (selectedDate.equals(saleDate)) {
                tableView.getItems().add(sale);
            }
        }
    }

    @FXML
    private void nextWeekButtonOnAction() {
        LocalDate currentDate = datepicker.getValue();
        LocalDateTime lastWeekOfCurrentDate = DateModifier.convertToLocalDateTime(currentDate).plusDays(1);

        datepicker.setValue(lastWeekOfCurrentDate.toLocalDate());
    }

    @FXML
    private void previousWeekButtonOnAction() {
        LocalDate selectedDate = datepicker.getValue();
        LocalDateTime nextWeekOfSelectedDate = DateModifier.convertToLocalDateTime(selectedDate).minusDays(1);

        datepicker.setValue(nextWeekOfSelectedDate.toLocalDate());
    }

    @FXML
    private void dateTodayButtonOnAction() {
        datepicker.setValue(LocalDate.now());
    }

    @FXML
    private void addSaleButtonOnAction() throws IOException {
        final Parent root = FXMLLoader.load(getClass().getResource("/AddSaleView.fxml"));
        final Stage currentStage = (Stage) tableView.getScene().getWindow();

        Dialog<String> dialog = new Dialog<>();
        dialog.getDialogPane().setContent(root);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(currentStage);
        dialog.initStyle(StageStyle.TRANSPARENT);

        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        centerStage(stage, 366, 175);

        dialog.show();
        if (!dialog.isShowing()) {
            System.out.println("Dialog closed");
        }
    }

    private void centerStage(Stage stage, double width, double height) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - width) / 2);
        stage.setY((screenBounds.getHeight() - height) / 2);
    }

    private void clearTableView() {
        tableView.getItems().clear();
    }

    public void refreshTable() {
        sales.addAll(dataController.getSales());
        datepickerOnAction();
    }
}
