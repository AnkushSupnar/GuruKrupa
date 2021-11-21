package com.ankush.controller.transaction;

import com.ankush.data.entities.Customer;
import com.ankush.data.entities.Transaction;
import com.ankush.data.service.CustomerService;
import com.ankush.data.service.ItemService;
import com.ankush.data.service.RateService;
import com.ankush.view.AlertNotification;
import com.ankush.view.StageManager;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.awt.event.KeyEvent;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

@Component
public class BillingFramController implements Initializable {
    @Autowired
    @Lazy
    private StageManager stageManager;

    @FXML private TextField txtBillNo;
    @FXML private DatePicker date;
    @FXML private TextField txtItemName;
    @FXML private ComboBox<String> cmbMetal;
    @FXML private ComboBox<String> cmbPurity;
    @FXML private TextField txtRate;
    @FXML private TextField txtQty;
    @FXML private TextField txtMajuri;
    @FXML private TextField txtAmount;
    @FXML private Button btnAdd;
    @FXML private Button btnUpdate;
    @FXML private Button btnRemove;
    @FXML private Button btnClear;
    @FXML private TableView<Transaction> tableBill;
    @FXML private TableColumn<Transaction,Long> colSrno;
    @FXML private TableColumn<Transaction,String> colItemName;
    @FXML private TableColumn<Transaction,String> colMetal;
    @FXML private TableColumn<Transaction,String> colPurity;
    @FXML private TableColumn<Transaction,Float> colRate;
    @FXML private TableColumn<Transaction,Float> colQty;
    @FXML private TableColumn<Transaction,Float> colMajuri;
    @FXML private TableColumn<Transaction,Float> colAmount;
    @FXML private TextField txtTotalMajuri;
    @FXML private TextField txtNetTotal;
    @FXML private TextField txtGrandTotal;
    @FXML private TextField txtModNo;
    @FXML private DatePicker dateMod;
    @FXML private TextField txtModeItemName;
    @FXML private ComboBox<String> cmbModMetal;
    @FXML private ComboBox<String> cmbModPurity;
    @FXML private TextField txtModeRate;
    @FXML private TextField txtModeWieght;
    @FXML private TextField txtModeGhat;
    @FXML private TextField txtModAmount;
    @FXML private Button btnModeAdd;
    @FXML private Button btnModeUpdate;
    @FXML private Button btnModeRemove;
    @FXML private Button btnModclear;
    @FXML private TableView<?> tableMod;
    @FXML private TableColumn<?, ?> colModSrno;
    @FXML private TableColumn<?, ?> colModItemName;
    @FXML private TableColumn<?, ?> colModMetal;
    @FXML private TableColumn<?, ?> colModPurity;
    @FXML private TableColumn<?, ?> colModRate;
    @FXML private TableColumn<?, ?> colModeQty;
    @FXML private TableColumn<?, ?> colModGhat;
    @FXML private TableColumn<?, ?> colModeFinalWeight;
    @FXML private TableColumn<?, ?> colModAmount;
    @FXML private TextField txtModGrandTotal;
    @FXML private TextField txtModNetTotal;
    @FXML private TextField txtCustomer;
    @FXML private Button btnSearchCustomer;
    @FXML private Button btnAddCustomer;
    @FXML private TextArea txtCustomerInformation;
    @FXML private RadioButton rdbtnCash;
    @FXML private RadioButton rdbtnCredit;
    @FXML private ComboBox<String> cmbBank;
    @FXML private TextField txtBillAmount;
    @FXML private TextField txtModeAmount;
    @FXML private TextField txtDiscount;
    @FXML private TextField txtPayable;
    @FXML private TextField txtPaid;
    @FXML private Button btnSave;
    @FXML private Button btnUpdate2;
    @FXML private Button btnClear2;
    @FXML private Button btnPrint;
    @FXML private Button btnHome;

    @Autowired private CustomerService customerService;
    @Autowired private ItemService itemService;
    @Autowired private RateService rateService;
    @Autowired private AlertNotification alert;
    private SuggestionProvider<String>customerNameProvide;
    private SuggestionProvider<String>itemNameProvide;
    private ObservableList<Transaction>trList= FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bill();
        System.out.println(url);
        System.out.println(resourceBundle.getBaseBundleName());
    }

    private void bill() {
        date.setValue(LocalDate.now());
        customerNameProvide = SuggestionProvider.create(customerService.getAllCustomerNames());
        TextFields.bindAutoCompletion(txtCustomer,customerNameProvide);
        itemNameProvide = SuggestionProvider.create(itemService.getAllItemNames());
        TextFields.bindAutoCompletion(txtItemName,itemNameProvide);

        colSrno.setCellValueFactory(new PropertyValueFactory<>("id"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemname"));
        colMetal.setCellValueFactory(new PropertyValueFactory<>("metal"));
        colPurity.setCellValueFactory(new PropertyValueFactory<>("purity"));
        colRate.setCellValueFactory(new PropertyValueFactory<>("rate"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colMajuri.setCellValueFactory(new PropertyValueFactory<>("majuri"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        tableBill.setItems(trList);

        txtRate.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,100}([\\.]\\d{0,6})?"))
                    txtRate.setText(oldValue);
            } });
        cmbPurity.getItems().addAll(rateService.getPurityNames());
        cmbMetal.getItems().addAll(rateService.getMetalNames());
        txtItemName.setOnAction(e->{
            if(!txtItemName.getText().isEmpty() || !txtItemName.getText().trim().equals(""))
            {
                cmbMetal.requestFocus();
            }
        });
        cmbMetal.setOnAction(e->{
            cmbPurity.requestFocus();
        });
        cmbPurity.setOnAction(e->txtRate.requestFocus());
        cmbMetal.setOnAction(e->{
           // System.out.println(cmbPurity.getValue());
            if(cmbPurity.getValue()!=null && cmbMetal.getValue()!=null)
            {
                System.out.println(cmbPurity.getValue());
                System.out.println(cmbMetal.getValue());
                txtRate.setText(String.valueOf(rateService.getTodayRate(LocalDate.now(),cmbMetal.getValue(),cmbPurity.getValue()).getRate()));
                calculateAmount();
            }
        });
        cmbPurity.setOnAction(e->{
            System.out.println(cmbPurity.getValue());
            System.out.println(cmbMetal.getValue());
            if(cmbMetal.getValue()!=null && cmbPurity.getValue()!=null)
            {
                txtRate.setText(String.valueOf(rateService.getTodayRate(LocalDate.now(),cmbMetal.getValue(),cmbPurity.getValue()).getRate()));
                calculateAmount();
            }
        });
       txtRate.setOnAction(e->{
           if(isNumeric(txtRate.getText())){
               txtQty.requestFocus();
               calculateAmount();
           }
       });
       txtQty.setOnAction(e->{
           if(isNumeric(txtQty.getText())){
               txtMajuri.requestFocus();
               calculateAmount();
           }
       });
       txtMajuri.setOnAction(e->{
           if(isNumeric(txtMajuri.getText())){
               btnAdd.requestFocus();
               calculateAmount();
           }
       });
        txtQty.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,100}([\\.]\\d{0,4})?")) {
                    txtQty.setText(oldValue);
                }
                if(!txtRate.getText().isEmpty() || !txtRate.getText().equals(""))
                {
                    calculateAmount();
                }
            }
        });
        txtMajuri.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,100}([\\.]\\d{0,4})?")) {
                    txtMajuri.setText(oldValue);
                }
                if(!txtMajuri.getText().isEmpty() ||!txtMajuri.getText().equals(""))
                {
                    calculateAmount();
                }
            }
        });
        btnSearchCustomer.setOnAction(e->searchCustomer());
        btnAdd.setOnAction(e->add());
        btnClear.setOnAction(e->clear());


        }

    private void clear() {
        txtItemName.setText("");
        cmbMetal.getSelectionModel().clearSelection();
        cmbPurity.getSelectionModel().clearSelection();
        txtRate.setText(""+0.0f);
        txtQty.setText(""+0.0f);
        txtMajuri.setText(""+0.0f);
        txtAmount.setText(""+0.0f);
    }

    private void add() {
        if(!validateItem())return;
        Transaction tr = Transaction.builder()
                .amount(Float.parseFloat(txtAmount.getText()))
                .metal(cmbMetal.getValue())
                .purity(cmbPurity.getValue())
                .rate(Float.parseFloat(txtRate.getText()))
                .itemname(txtItemName.getText())
                .majuri(Float.parseFloat(txtMajuri.getText()))
                .quantity(Float.parseFloat(txtQty.getText()))
                .build();
        System.out.println(tr);
        addInTransactionList(tr);
        clear();

    }

    private void addInTransactionList(Transaction t) {
        int index=-1;
        for(Transaction tr:trList)
        {
            if(tr.getItemname().equalsIgnoreCase(t.getItemname())&&
                    tr.getMetal().equalsIgnoreCase(t.getMetal())&&
                    tr.getPurity().equalsIgnoreCase(t.getPurity())&&
                    tr.getRate()==t.getRate())
            {
                index=trList.indexOf(tr);
                System.out.println("found");
                break;
            }
        }
        if(index==-1)
        {
            trList.add(t);
            tableBill.refresh();
        }
        else{
            trList.remove(index);
            trList.add(index,t);
            tableBill.refresh();
        }
        txtNetTotal.setText(String.valueOf(Float.parseFloat(txtNetTotal.getText())+(t.getAmount()-t.getMajuri())));
        txtTotalMajuri.setText(String.valueOf(Float.parseFloat(txtTotalMajuri.getText())+t.getMajuri()));
        txtGrandTotal.setText(String.valueOf(Float.parseFloat(txtGrandTotal.getText())+t.getAmount()));
        txtBillAmount.setText(txtGrandTotal.getText());
    }


    private boolean validateItem() {
        if(txtItemName.getText().isEmpty() || txtItemName.getText().trim().equals(""))
        {
            alert.showError("Enter Item Name");
            txtItemName.requestFocus();
            return false;
        }
        if(txtRate.getText().isEmpty())
        {
            alert.showError("Enter Rate or Select Metal and Purity");
            txtRate.requestFocus();
            return false;
        }
        if(txtQty.getText().isEmpty())
        {
            alert.showError("Enter Quantity in Gram");
            txtQty.requestFocus();
            return false;
        }
        if(txtMajuri.getText().isEmpty())
        {
            txtMajuri.setText(""+0.0f);
        }
        if(txtAmount.getText().isEmpty() || txtAmount.getText().equals(""+0.0))
        {
            alert.showError("Enter Item Again");
            txtItemName.requestFocus();
            return false;
        }

        return true;
    }

    void calculateAmount()
    {
        if(txtRate.getText().isEmpty() || txtRate.getText().equals("")) txtRate.setText(""+0.0);
        if(txtQty.getText().isEmpty() || txtQty.getText().equals("")) txtQty.setText(""+0.0);
        if(txtMajuri.getText().isEmpty() || txtMajuri.getText().equals("")) txtMajuri.setText(""+0.0);
        txtAmount.setText(
                String.valueOf(((Float.parseFloat(txtRate.getText())/10)*Float.parseFloat(txtQty.getText()))
                +(Float.parseFloat(txtMajuri.getText())*Float.parseFloat(txtQty.getText())))
        );

    }
    private void searchCustomer() {
        if(txtCustomer.getText().isEmpty()) txtCustomer.requestFocus();
        Customer customer = customerService.getByCustomerName(txtCustomer.getText());
        if(customer!=null)
        {
            txtCustomerInformation.setText(
                    customer.getAddressline()+"\nVillage/City:"
                    +customer.getVillage()+" Taluka: "
                    +customer.getTaluka()+" District: "
                    +customer.getDistrict()+"\nContact:"
                    +customer.getContact()+" , "+customer.getMobile()
            );
        }
    }
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
