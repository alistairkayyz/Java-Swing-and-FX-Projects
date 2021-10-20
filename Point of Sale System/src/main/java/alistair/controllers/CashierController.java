package alistair.controllers;

import alistair.Main;
import alistair.business.*;
import alistair.data.*;
import alistair.utility.Session;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.ScrollEvent;
import javafx.stage.StageStyle;

import java.sql.Date;
import java.sql.Time;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@SuppressWarnings("ALL")
public class CashierController {
    public ListView<String> proList;
    public ListView<String> descList;
    public ListView<String> itemPriceList;
    public ListView<String> quantityList;
    public ListView<String> totalPriceList;
    public Label subtotal;
    public Label vatAmount;
    public Label totalAmount;
    public TextField cash;
    public Label change;
    public Button process;

    private Product product;
    private Payment payment;
    private TransactionDetails transactionDetails;
    private Transaction transaction;
    private final VatSetting vatSetting;
    private InvoiceLine invoiceLine;

    private final ArrayList<InvoiceLine> invoiceItems = new ArrayList<>();

    private final NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
    private double sub_total;
    private double vat_amount;
    private double total_amount;
    private double cash_amount;
    private double change_amount;

    private final DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DateTimeFormatter time = DateTimeFormatter.ofPattern("hh:mm:ss");

    @FXML
    private Label description;
    @FXML
    private Label barcode;
    @FXML
    private Label unitPrice;
    @FXML
    private Label categoryNo;
    @FXML
    private TextField productID;
    @FXML
    private Label productCode;
    @FXML
    private Label stocksOnHand;
    @FXML
    private Spinner quantity;

    public CashierController() {
        vatSetting = VatSettingDB.getVatSetting(1);
    }

    @FXML
    public void close() {
        Main.getLoginStage().show();
        Main.getCashierStage().close();
    }

    @FXML
    public void populate() {
        int _id;
        try {
            if (!productID.getText().isEmpty()) {
                _id = Integer.parseInt(productID.getText());

                if (ProductDB.productExists(_id)){
                    product = ProductDB.getProduct(_id);
                    assert product != null;
                    productCode.setText(product.getProductCode());
                    description.setText(product.getDescription());
                    barcode.setText(product.getBarcode());
                    unitPrice.setText(product.getUnitPriceFormatted());
                    stocksOnHand.setText(product.getStocksOnHand() + "");
                    categoryNo.setText(product.getCategoryNo() + "");
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.initStyle(StageStyle.UTILITY);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Product not found");
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.showAndWait();
                }
            }
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Invalid Product ID");
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.showAndWait();
        }
    }

    // insert the current item into the invoice line item
    @FXML
    public void insert() {
        invoiceLine = new InvoiceLine(
                product.getId(),
                product.getDescription(),
                product.getUnitPrice(),
                Integer.parseInt(quantity.getValue().toString())
        );

        invoiceItems.add(invoiceLine);
        populateTable(invoiceItems);
        populateTotalAmount(invoiceItems);
        clearFields();
    }

    @FXML
    public void undo() {
        if (!invoiceItems.isEmpty()) {
            invoiceItems.remove(invoiceItems.size() - 1);
            populateTable(invoiceItems);
            populateTotalAmount(invoiceItems);

            clearFields();
        }
    }

    @FXML
    public void clear() {
        if (!invoiceItems.isEmpty()) {
            invoiceItems.clear();
            populateTable(invoiceItems);
            populateTotalAmount(invoiceItems);

            clearFields();
        }
    }
    public void emptyList(){
        proList.getItems().add("--Empty");
        descList.getItems().add("--Empty");
        itemPriceList.getItems().add("--Empty");
        quantityList.getItems().add("--Empty");
        totalPriceList.getItems().add("--Empty");
    }

    public void clearFields(){
        productCode.setText("");
        description.setText("");
        barcode.setText("");
        unitPrice.setText("");
        stocksOnHand.setText("");
        categoryNo.setText("");
        quantity.setPromptText("1");
    }
    public void populateTable(ArrayList<InvoiceLine> list){

        proList.getItems().clear();
        descList.getItems().clear();
        itemPriceList.getItems().clear();
        quantityList.getItems().clear();
        totalPriceList.getItems().clear();

        if (list.isEmpty())
            emptyList();
        else {
            for (InvoiceLine line : list) {
                proList.getItems().add(line.getProductID() + "");
                descList.getItems().add(line.getDescription() + "");
                itemPriceList.getItems().add(line.getItemPriceFormatted() + "");
                quantityList.getItems().add(line.getQuantity() + "");
                totalPriceList.getItems().add(line.getTotalPriceFormatted() + "");
            }
        }

    }

    public void populateTotalAmount(ArrayList<InvoiceLine> list){

        sub_total = 0;
        vat_amount = 0;
        total_amount = 0;

        if (!list.isEmpty()){
            for (InvoiceLine line : list)
                sub_total += line.getTotalPrice();

            vat_amount = sub_total * vatSetting.getVatPercent()/100;

            total_amount = sub_total + vat_amount;

            subtotal.setText(numberFormat.format(sub_total));
            vatAmount.setText(numberFormat.format(vat_amount));
            totalAmount.setText(numberFormat.format(total_amount));
        }
        else {
            subtotal.setText("R0.00");
            vatAmount.setText("R0.00");
            totalAmount.setText("R0.00");
        }

    }

    public void scroll(ScrollEvent scrollEvent) {
        descList.scrollTo(scrollEvent.getTouchCount());
    }

    public void calculateChange() {
        if (!cash.getText().isEmpty()){
            try {
                cash_amount = Double.parseDouble(cash.getText());

                change_amount = cash_amount - total_amount;
                if (change_amount >= 0) {
                    change.setText(numberFormat.format(change_amount));
                    process.setVisible(true);
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.initStyle(StageStyle.UTILITY);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Insufficient funds");
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.showAndWait();
                    process.setVisible(false);
                }
            }
            catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initStyle(StageStyle.UTILITY);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Invalid Amount");
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.showAndWait();
            }
        }
    }

    public void processPayment() {
        transaction = new Transaction();
        Date sqlDate = Date.valueOf(date.format(LocalDate.now()));
        Time sqlTime = Time.valueOf(time.format(LocalTime.now()));

        transaction.setDate(sqlDate);
        transaction.setTime(sqlTime);
        transaction.setSubTotal(sub_total);
        transaction.setVatAmount(vat_amount);
        transaction.setTotalAmount(total_amount);
        transaction.setStaffID(Session.getId());
        transaction.setStatus(1);

        if (TransactionsDB.insert(transaction)){

            int invoiceNo = TransactionsDB.getLastInvoiceNumber();
            payment = new Payment();
            payment.setInvoiceNo(invoiceNo);
            payment.setCash(cash_amount);
            payment.setChange(change_amount);

            if (PaymentDB.insert(payment)){
                System.out.println("Payment inserted");
            }

            for (InvoiceLine invoiceLine : invoiceItems){
                transactionDetails = new TransactionDetails();

                if (TransactionDetailsDB.productExists(invoiceLine.getProductID())) {
                    int _id = invoiceLine.getProductID();
                    int _qty = TransactionDetailsDB.getQuantity(_id);

                    if (TransactionDetailsDB.updateQuantity(_id,_qty + invoiceLine.getQuantity())) {
                        System.out.println("Transaction Quantity updated");

                        _qty = StockDB.getQuantity(_id);
                        if (StockDB.updateQuantity(_id,_qty - invoiceLine.getQuantity()))
                            System.out.println("Stock Quantity updated");
                    }
                }
                else {
                    transactionDetails.setInvoiceNo(invoiceNo);
                    transactionDetails.setProductNo(invoiceLine.getProductID());
                    transactionDetails.setItemPrice(invoiceLine.getItemPrice());
                    transactionDetails.setQuantity(invoiceLine.getQuantity());
                    transactionDetails.setDiscount(0);

                    if (TransactionDetailsDB.insert(transactionDetails)) {
                        System.out.println("Transaction details inserted");

                        int _id = invoiceLine.getProductID();
                        int _qty = StockDB.getQuantity(_id);
                        if (StockDB.updateQuantity(_id,_qty - invoiceLine.getQuantity()))
                            System.out.println("Stock Quantity updated");
                    }
                }
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("All Done");
            alert.showAndWait();

            cash.setText("");
            change.setText("");
            emptyList();
            clear();
        }
    }
}
