package alistair.controllers;

import alistair.Main;
import alistair.business.SalesReport;
import alistair.data.SalesReportDB;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.StageStyle;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DashboardController {

    public DatePicker fromDate;
    public DatePicker toDate;
    public Label totalSales;
    public ScrollPane scrollPane;

    public ListView<String> idList;
    public ListView<String> dateList;
    public ListView<String> productNameList;
    public ListView<String> categoryList;
    public ListView<String> quantityList;
    public ListView<String> itemPriceList;
    public ListView<String> totalAmountList;
    public ListView<String> staffNameList;

    private ArrayList<SalesReport> salesReports;

    private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private Date date;

    public DashboardController() {
       // display(SalesReportDB.getSalesReport());
    }

    public void display(ArrayList<SalesReport> salesReports){
        if (salesReports != null){
            for (SalesReport salesReport : salesReports){
                idList.getItems().add(salesReport.getId() + "");
                dateList.getItems().add(salesReport.getDate() + "");
                productNameList.getItems().add(salesReport.getDescription());
                categoryList.getItems().add(salesReport.getCategoryName());
                quantityList.getItems().add(salesReport.getQuantity() + "");
                itemPriceList.getItems().add(salesReport.getItemPriceFormatted());
                totalAmountList.getItems().add(salesReport.getTotalAmountFormatted());
                staffNameList.getItems().add(salesReport.getStaffName());
            }
            totalSales.setText("Total Sales: " + salesReports.size());
        }
    }

    public void clearList(){
        idList.getItems().clear();
        dateList.getItems().clear();
        productNameList.getItems().clear();
        categoryList.getItems().clear();
        quantityList.getItems().clear();
        itemPriceList.getItems().clear();
        totalAmountList.getItems().clear();
        staffNameList.getItems().clear();
    }

    @FXML
    public void close() {
        Main.getLoginStage().show();
        Main.getDashboardStage().close();
    }

    public void daySales() {
        date = Date.valueOf(dateFormat.format(LocalDate.now()));
        salesReports = SalesReportDB.getDaySalesReport(date);

        if (salesReports != null && salesReports.size() != 0){
            clearList();
            display(salesReports);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No Reports Found!");
            alert.showAndWait();
        }
    }

    public void showCustomSales() {
        if (fromDate.getValue() != null && toDate.getValue() != null) {

            LocalDate localDate = fromDate.getValue();
            Date fromDate = Date.valueOf(dateFormat.format(localDate));

            localDate = toDate.getValue();
            Date toDate = Date.valueOf(dateFormat.format(localDate));

            salesReports = SalesReportDB.getCustomDateSalesReport(fromDate, toDate);
            if (salesReports != null) {
                clearList();
                display(salesReports);
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initStyle(StageStyle.UTILITY);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("No Reports Found!");
                alert.showAndWait();
            }
        }
    }

    public void allSales() {
        clearList();
        display(SalesReportDB.getSalesReport());
    }
}