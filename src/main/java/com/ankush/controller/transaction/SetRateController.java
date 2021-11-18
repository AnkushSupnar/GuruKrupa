package com.ankush.controller.transaction;

import com.ankush.data.entities.Rate;
import com.ankush.data.service.RateService;
import com.ankush.view.AlertNotification;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

@Component
public class SetRateController implements Initializable {
    @FXML private AnchorPane mainPane;
    @FXML private DatePicker date;
    @FXML private TextField txtMetal;
    @FXML private TextField txtPurity;
    @FXML private TextField txtRate;
    @FXML private Button btnSave;
    @FXML private Button btnClear;
    @FXML private Button btnHome;
    @FXML private TableView<Rate> table;
    @FXML private TableColumn<Rate,Long> colSrNo;
    @FXML private TableColumn<Rate, LocalDate> colDate;
    @FXML private TableColumn<Rate,String> colMetal;
    @FXML private TableColumn<Rate,String> colPurity;
    @FXML private TableColumn<Rate,Float> colRate;

    @Autowired private RateService service;
    @Autowired private AlertNotification alert;
    private ObservableList<Rate>list = FXCollections.observableArrayList();
    private Long id;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id=null;
        date.setValue(LocalDate.now());
        colSrNo.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colMetal.setCellValueFactory(new PropertyValueFactory<>("metal"));
        colPurity.setCellValueFactory(new PropertyValueFactory<>("purity"));
        colRate.setCellValueFactory(new PropertyValueFactory<>("rate"));
        list.addAll(service.getAllRates());
        table.setItems(list);
        btnSave.setOnAction(e->save());
    }

    private void save() {
        if(date.getValue()==null)
        {
            alert.showError("Select Date");
            date.requestFocus();
            return;
        }
        if(txtMetal.getText().isEmpty())
        {
            alert.showError("Enter Metal Name");
            txtMetal.requestFocus();
            return;
        }
        if(txtPurity.getText().isEmpty())
        {
            alert.showError("Enter Purity Name");
            txtPurity.requestFocus();
            return;
        }
        if(txtRate.getText().isEmpty())
        {
            alert.showError("Enter Rate");
            txtRate.requestFocus();
            return;
        }
        Rate rate = Rate.builder()
                .rate(Float.parseFloat(txtRate.getText()))
                .metal(txtMetal.getText())
                .purity(txtPurity.getText())
                .date(date.getValue())
                .build();
        if(id!=null)rate.setId(id);
        if(service.save(rate)==1)
        {
            alert.showSuccess("Rate Save Success");
            addInList(rate);
            clear();

        }

    }

    private void clear() {
        date.setValue(LocalDate.now());
        txtPurity.setText("");
        txtRate.setText("");
        id= null;
    }

    private void addInList(Rate rate) {
        int flag=-1;
        for(Rate rt:list)
        {
            if(rt.getDate().equals(rate.getDate()) &&
                    rt.getPurity().equalsIgnoreCase(rate.getPurity()) &&
                    rt.getMetal().equalsIgnoreCase(rate.getMetal()))
            {
                flag=list.indexOf(rt);
            }
        }
        if(flag==-1)
        {
            list.add(rate);
            table.refresh();
        }
        else{
            list.remove(flag);
            list.add(flag,rate);
            table.refresh();
        }
    }
}
