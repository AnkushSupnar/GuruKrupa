package com.ankush.controller.transaction;

import com.ankush.data.entities.Customer;
import com.ankush.data.service.CustomerService;
import com.ankush.data.service.ItemService;
import com.ankush.data.service.RateService;
import com.ankush.view.StageManager;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

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
    @FXML private TableView<?> tableBill;
    @FXML private TableColumn<?, ?> colSrno;
    @FXML private TableColumn<?, ?> colItemName;
    @FXML private TableColumn<?, ?> colMetal;
    @FXML private TableColumn<?, ?> colPurity;
    @FXML private TableColumn<?, ?> colRate;
    @FXML private TableColumn<?, ?> colQty;
    @FXML private TableColumn<?, ?> colMajuri;
    @FXML private TableColumn<?, ?> colAmount;
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
    private SuggestionProvider<String>customerNameProvide;
    private SuggestionProvider<String>itemNameProvide;
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
        cmbPurity.getItems().addAll(rateService.getPurityNames());
        cmbMetal.getItems().addAll(rateService.getMetalNames());
        cmbMetal.setOnAction(e->{
            System.out.println("Metal Action");
            System.out.println(cmbPurity.getValue());
            if(cmbPurity.getValue()!=null)
            {
                txtRate.setText(String.valueOf(rateService.getLastRate(cmbMetal.getValue(),cmbPurity.getValue()).getRate()));
            }
        });
        cmbPurity.setOnAction(e->{
            if(cmbMetal.getValue()!=null)
            {
                txtRate.setText(String.valueOf(rateService.getLastRate(cmbMetal.getValue(),cmbPurity.getValue()).getRate()));
            }
        });
        btnSearchCustomer.setOnAction(e->searchCustomer());

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
}
