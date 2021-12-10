package com.ankush.controller.transaction;

import com.ankush.config.SpringFXMLLoader;
import com.ankush.data.entities.*;
import com.ankush.data.service.*;
import com.ankush.view.AlertNotification;
import com.ankush.view.StageManager;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
public class BillingFramController implements Initializable {
    @Autowired
    @Lazy
    private StageManager stageManager;

    @Autowired
    SpringFXMLLoader fxmlLoader;

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
    @FXML private TableColumn<Transaction, Long> colSrno;
    @FXML private TableColumn<Transaction, String> colItemName;
    @FXML private TableColumn<Transaction, String> colMetal;
    @FXML private TableColumn<Transaction, String> colPurity;
    @FXML private TableColumn<Transaction, Float> colRate;
    @FXML private TableColumn<Transaction, Float> colQty;
    @FXML private TableColumn<Transaction, Float> colMajuri;
    @FXML private TableColumn<Transaction, Float> colAmount;
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
    @FXML private TableView<ModeTransaction> tableMod;
    @FXML private TableColumn<ModeTransaction, Long> colModSrno;
    @FXML private TableColumn<ModeTransaction,String> colModItemName;
    @FXML private TableColumn<ModeTransaction,String> colModMetal;
    @FXML private TableColumn<ModeTransaction,String> colModPurity;
    @FXML private TableColumn<ModeTransaction,Float> colModRate;
    @FXML private TableColumn<ModeTransaction,Float> colModeQty;
    @FXML private TableColumn<ModeTransaction,Float> colModGhat;
    @FXML private TableColumn<ModeTransaction,Float> colModeFinalWeight;
    @FXML private TableColumn<ModeTransaction,Float> colModAmount;
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
    @FXML private TextField txtModeTotalAmount;
    //@FXML private TextField txtModeAmount;
    @FXML private TextField txtDiscount;
    @FXML private TextField txtPayable;
    @FXML private TextField txtPaid;
    @FXML private Button btnSave;
    @FXML private Button btnUpdate2;
    @FXML private Button btnClear2;
    @FXML private Button btnPrint;
    @FXML private Button btnHome;

    @FXML private TableView<Bill> tableOld;
    @FXML private TableColumn<Bill,Long> colSrNo;
    @FXML private TableColumn<Bill,LocalDate> colDate;
    @FXML private TableColumn<Bill,String> colBillNo;
    @FXML private TableColumn<Bill,String> colCustomer;
    @FXML private TableColumn<Bill,String> colPaymode;
    @FXML private TableColumn<Bill,String> colBillAmount;
    @FXML private DatePicker dateSearch;
    @FXML private TextField txtBillNoSearch;
    @FXML private TextField txtCustomerSearch;
    @FXML private Button btnSearchAll;
    private Pane pane;
    @Autowired private CustomerService customerService;
    @Autowired private ItemService itemService;
    @Autowired private RateService rateService;
    @Autowired private BankService bankService;
    @Autowired private BillService billService;
    @Autowired private ModeService modeService;
    @Autowired private AlertNotification alert;
    private SuggestionProvider<String> customerNameProvide;
    private SuggestionProvider<String> itemNameProvide;
    private ObservableList<Transaction> trList = FXCollections.observableArrayList();
    private ObservableList<ModeTransaction> modetrList = FXCollections.observableArrayList();
    private ObservableList<Bill> billList = FXCollections.observableArrayList();
    private Long id;
    private Long modeid;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bill();
        mod();
        id=null;
        modeid = null;
        colSrNo.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colBillNo.setCellValueFactory(new PropertyValueFactory<>("billno"));
        colPaymode.setCellValueFactory(new PropertyValueFactory<>("paymode"));
        colBillAmount.setCellValueFactory(new PropertyValueFactory<>("billamount"));
        colCustomer.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getCustomer().getCustomername()));
        billList.addAll(billService.getBillByDate(date.getValue()));
        tableOld.setItems(billList);
        cmbBank.getItems().addAll(bankService.getAllBankNames());
        TextFields.bindAutoCompletion(txtCustomerSearch, customerNameProvide);
        btnSave.setOnAction(e->save());
        txtDiscount.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,100}([\\.]\\d{0,6})?"))
                    txtDiscount.setText(oldValue);
            }
        });
        txtDiscount.setOnAction(e->calculatePayableAmount());
        btnUpdate2.setOnAction(e->updateBill());
        btnClear2.setOnAction(e->clearBill());

        dateSearch.setOnAction(e->serchByDate());
        txtBillNoSearch.setOnAction(e->searchByBillNo());
        txtCustomerSearch.setOnAction(e->searchByCustomer());
        btnSearchAll.setOnAction(e->searchAll());
    }
    private void clearBill() {
        id=null;
        modeid =null;
        txtBillNo.setText("A"+billService.getNewBillNo());
        date.setValue(LocalDate.now());
        txtModNo.setText("A"+modeService.getNewModeNo());
        dateMod.setValue(LocalDate.now());
        txtCustomer.setText("");
        txtCustomerInformation.setText("");
        trList.clear();
        modetrList.clear();
        txtNetTotal.setText(""+0.0f);
        txtTotalMajuri.setText(""+0.0f);
        txtGrandTotal.setText(""+0.0f);
        txtBillAmount.setText(""+0.0f);
        txtModeTotalAmount.setText(""+0.0f);
        txtDiscount.setText(""+0.0f);
        txtPayable.setText(""+0.0f);
        txtPaid.setText(""+0.0f);
        txtModNetTotal.setText(""+0.0f);
        txtModGrandTotal.setText(""+0.0f);
        rdbtnCash.setSelected(true);
        cmbBank.getSelectionModel().clearSelection();
    }
    private void updateBill() {
        if(tableOld.getSelectionModel().getSelectedItem()==null) return;
        clearBill();
        Bill bill = billService.getBillByBillno(tableOld.getSelectionModel().getSelectedItem().getBillno());
        id = bill.getId();
        txtBillNo.setText(bill.getBillno());
        date.setValue(bill.getDate());
        txtCustomer.setText(bill.getCustomer().getCustomername());
        searchCustomer();
        trList.clear();
        for(Transaction tr:bill.getTransactions())
           addInTransactionList(tr);
        if(bill.getPaymode().equals("Cash")) rdbtnCash.setSelected(true);
        else rdbtnCredit.setSelected(true);
        cmbBank.setValue(bill.getBank().getName());
        txtPaid.setText(String.valueOf(bill.getPaid()));
        txtDiscount.setText(String.valueOf(bill.getDiscount()));
        if(!bill.getModno().equals("0"))
        {
            Mode mode = modeService.getModeByModeNo(bill.getModno());
            modeid = mode.getId();
            for(ModeTransaction tr:mode.getModTransactions())
            {
                addModeInModeTransaction(tr);
            }
            txtModNo.setText(mode.getModeno());
            dateMod.setValue(mode.getDate());
        }
        calculatePayableAmount();


    }
    private void save() {
        if (!validateBill()) return;
        Mode mode = Mode.builder()
                .amount(Float.parseFloat(txtModeTotalAmount.getText()))
                .customer(customerService.getByCustomerName(txtCustomer.getText()))
                .modTransactions(new ArrayList<ModeTransaction>())
                .date(dateMod.getValue())
                .modeno(txtModNo.getText())
                .payby("Bill")
                .build();
        if(modeid!=null)mode.setId(modeid);
        for(ModeTransaction tr:modetrList)
        {
           tr.setId(null);
           tr.setMode(mode);
           mode.getModTransactions().add(tr);
       }
        String paymode = rdbtnCash.isSelected()?"Cash":"credit";
        Bill bill = Bill.builder()
                .transactions(new ArrayList<Transaction>())
                .bank(bankService.getByName(cmbBank.getValue()))
                .date(date.getValue())
                .billamount(Float.parseFloat(txtBillAmount.getText()))
                .customer(customerService.getByCustomerName(txtCustomer.getText()))
                .discount(Float.parseFloat(txtDiscount.getText()))
                .modeamount(Float.parseFloat(txtModeTotalAmount.getText()))
                .modno(txtModNo.getText())
                .billno(txtBillNo.getText())
                .paid(Float.parseFloat(txtPaid.getText()))
                .paymode(paymode)
                .build();
        if(txtModeTotalAmount.getText().equals(""+0.0))
        {
            bill.setModno("0");
        }
        if(id!=null) bill.setId(id);
        else bill.setId(null);
        for(Transaction tr:trList)
        {
            tr.setId(null);
            tr.setBill(bill);
            bill.getTransactions().add(tr);
        }
        int f = billService.saveBill(bill);
        if(f==1)
        {
            if(!txtModeTotalAmount.getText().equals(""+0.0)) {
                modeService.saveMode(mode);
            }
            System.out.println("Saved id "+mode.getId());
            alert.showSuccess("Bill Saved Success ");
            addInBIllList(bill);
            clearBill();
            return;
        }
        if(f==2)
        {
            if(!txtModeTotalAmount.getText().equals(""+0.0)) {
                modeService.saveMode(mode);
            }
            alert.showSuccess("Bill update Success ");
            addInBIllList(bill);
            clearBill();
            return;
        }
        //System.out.println(bill);
    }
    private void addInBIllList(Bill bill) {
        int index=-1;
        for(Bill b:billList)
        {
            if(b.getBillno().equals(bill.getBillno()))
            {
                index=billList.indexOf(b);
                break;
            }
        }
        if(index ==-1)
        {
           billList.add(bill);
           tableOld.refresh();
        }
        else{
            billList.remove(index);
            billList.add(index,bill);
            tableOld.refresh();
        }
    }
    private boolean validateBill() {
        if(trList.size()==0)
        {
            alert.showError("No Billing data to show");
            return false;
        }
        if(txtCustomerInformation.getText().isEmpty())
        {
            alert.showError("Enter Customer Name");
            txtCustomer.requestFocus();
            return false;
        }
        if(cmbBank.getValue()==null)
        {
            alert.showError("Select Bank Name");
            cmbBank.requestFocus();
            return false;
        }
        if(!rdbtnCash.isSelected() && !rdbtnCredit.isSelected())
        {
            alert.showError("Select Payment Method Cash or Credit!!!");
            return false;
        }
        if(rdbtnCash.isSelected() && (txtPaid.getText().isEmpty() || !isNumeric(txtPaid.getText()) || txtPaid.getText().equals(""+0.0)))
        {
            alert.showError("Enter Paid Amount");
            txtPaid.requestFocus();
            return false;
        }
        if(rdbtnCash.isSelected() &&(Float.parseFloat(txtPaid.getText()))<Float.parseFloat(txtPayable.getText()))
        {
            alert.showError("Enter Correct Paid Amount Should be equal to Payable Amount");
            txtPaid.requestFocus();
            return false;
        }


        return true;
    }
    private void bill() {
        txtBillNo.setText("A"+billService.getNewBillNo());
        date.setValue(LocalDate.now());
        ToggleGroup tg = new ToggleGroup();
        rdbtnCash.setToggleGroup(tg);
        rdbtnCredit.setToggleGroup(tg);
        customerNameProvide = SuggestionProvider.create(customerService.getAllCustomerNames());
        TextFields.bindAutoCompletion(txtCustomer, customerNameProvide);
        itemNameProvide = SuggestionProvider.create(itemService.getAllItemNames());
        TextFields.bindAutoCompletion(txtItemName, itemNameProvide);

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
            }
        });
        cmbPurity.getItems().addAll(rateService.getPurityNames());
        cmbMetal.getItems().addAll(rateService.getMetalNames());
        txtItemName.setOnAction(e -> {
            if (!txtItemName.getText().isEmpty() || !txtItemName.getText().trim().equals("")) {
                cmbMetal.requestFocus();
            }
        });
        cmbMetal.setOnAction(e -> {
            cmbPurity.requestFocus();
        });
        cmbPurity.setOnAction(e -> txtRate.requestFocus());
        cmbMetal.setOnAction(e -> {
            // System.out.println(cmbPurity.getValue());
            if (cmbPurity.getValue() != null && cmbMetal.getValue() != null) {
                txtRate.setText(String.valueOf(rateService.getTodayRate(LocalDate.now(), cmbMetal.getValue(), cmbPurity.getValue()).getRate()));
                calculateAmount();
            }
        });
        cmbPurity.setOnAction(e -> {

            if (cmbMetal.getValue() != null && cmbPurity.getValue() != null) {
                txtRate.setText(String.valueOf(rateService.getTodayRate(LocalDate.now(), cmbMetal.getValue(), cmbPurity.getValue()).getRate()));
                calculateAmount();
            }
        });
        txtRate.setOnAction(e -> {
            if (isNumeric(txtRate.getText())) {
                txtQty.requestFocus();
                calculateAmount();
            }
        });
        txtQty.setOnAction(e -> {
            if (isNumeric(txtQty.getText())) {
                txtMajuri.requestFocus();
                calculateAmount();
            }
        });
        txtMajuri.setOnAction(e -> {
            if (isNumeric(txtMajuri.getText())) {
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
                if (!txtRate.getText().isEmpty() || !txtRate.getText().equals("")) {
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
                if (!txtMajuri.getText().isEmpty() || !txtMajuri.getText().equals("")) {
                    calculateAmount();
                }
            }
        });
        btnSearchCustomer.setOnAction(e -> searchCustomer());
        btnAdd.setOnAction(e -> add());
        btnClear.setOnAction(e -> clear());
        btnUpdate.setOnAction(e -> update());
        btnRemove.setOnAction(e -> remove());
    }
    private void remove() {
        if (tableBill.getSelectionModel().getSelectedItem() == null) return;
        Transaction tr = tableBill.getSelectionModel().getSelectedItem();
        txtTotalMajuri.setText(
                String.valueOf(Float.parseFloat(txtTotalMajuri.getText()) - tr.getMajuri()
                ));
        txtNetTotal.setText(
                String.valueOf(Float.parseFloat(txtNetTotal.getText()) -
                        (tr.getAmount() - tr.getMajuri())
                )
        );
        txtGrandTotal.setText(
                String.valueOf(
                        Float.parseFloat(txtNetTotal.getText()) + Float.parseFloat(txtTotalMajuri.getText())
                )
        );
        trList.remove(tableBill.getSelectionModel().getSelectedIndex());
        tableBill.refresh();
    }
    private void update() {
        if (tableBill.getSelectionModel().getSelectedItem() == null) return;
        Transaction tr = tableBill.getSelectionModel().getSelectedItem();
        txtItemName.setText(tr.getItemname());
        cmbMetal.setValue(tr.getMetal());
        cmbPurity.setValue(tr.getPurity());
        txtRate.setText(String.valueOf(tr.getRate()));
        txtQty.setText(String.valueOf(tr.getQuantity()));
        txtMajuri.setText(String.valueOf(tr.getMajuri() / tr.getQuantity()));
        txtAmount.setText(String.valueOf(tr.getAmount()));
    }
    private void clear() {
        txtItemName.setText("");
        cmbMetal.getSelectionModel().clearSelection();
        cmbPurity.getSelectionModel().clearSelection();
        txtRate.setText("" + 0.0f);
        txtQty.setText("" + 0.0f);
        txtMajuri.setText("" + 0.0f);
        txtAmount.setText("" + 0.0f);
    }
    private void add() {
        if (!validateItem()) return;
        Transaction tr = Transaction.builder()
                .amount(Float.parseFloat(txtAmount.getText()))
                .metal(cmbMetal.getValue())
                .purity(cmbPurity.getValue())
                .rate(Float.parseFloat(txtRate.getText()))
                .itemname(txtItemName.getText())
                .majuri(Float.parseFloat(txtMajuri.getText()) * Float.parseFloat(txtQty.getText()))
                .quantity(Float.parseFloat(txtQty.getText()))
                .build();
        addInTransactionList(tr);
        clear();

    }
    private void addInTransactionList(Transaction t) {
        int index = -1;
        for (Transaction tr : trList) {
            System.out.println(tr);
            System.out.println(t);
            if (tr.getItemname().equalsIgnoreCase(t.getItemname()) &&
                    tr.getMetal().equalsIgnoreCase(t.getMetal()) &&
                    tr.getPurity().equalsIgnoreCase(t.getPurity()) &&
                    tr.getMajuri().equals(t.getMajuri()) &&
                    tr.getRate().equals(t.getRate())) {
                index = trList.indexOf(tr);
                System.out.println("found");
                break;
            }
        }
        if (index == -1) {
            System.out.println("not found");
            trList.add(t);
            tableBill.refresh();
        } else {
            trList.get(index).setQuantity(trList.get(index).getQuantity() + t.getQuantity());
            trList.get(index).setAmount(trList.get(index).getAmount() + t.getAmount());

            tableBill.refresh();
        }
        txtNetTotal.setText(String.valueOf(Float.parseFloat(txtNetTotal.getText()) + (t.getAmount() - t.getMajuri())));
        txtTotalMajuri.setText(String.valueOf(Float.parseFloat(txtTotalMajuri.getText()) + t.getMajuri()));
        txtGrandTotal.setText(String.valueOf(Float.parseFloat(txtGrandTotal.getText()) + t.getAmount()));
        txtBillAmount.setText(txtGrandTotal.getText());
        calculatePayableAmount();
    }
    private void calculatePayableAmount()
    {
        if(txtDiscount.getText().isEmpty() || !isNumeric(txtDiscount.getText())) txtDiscount.setText(""+0.0f);
        txtPayable.setText(
                String.valueOf(Float.parseFloat(txtBillAmount.getText())- Float.parseFloat(txtModeTotalAmount.getText())-Float.parseFloat(txtDiscount.getText()))
        );
    }
    private boolean validateItem() {
        if (txtItemName.getText().isEmpty() || txtItemName.getText().trim().equals("")) {
            alert.showError("Enter Item Name");
            txtItemName.requestFocus();
            return false;
        }
        if (txtRate.getText().isEmpty()) {
            alert.showError("Enter Rate or Select Metal and Purity");
            txtRate.requestFocus();
            return false;
        }
        if (txtQty.getText().isEmpty()) {
            alert.showError("Enter Quantity in Gram");
            txtQty.requestFocus();
            return false;
        }
        if (txtMajuri.getText().isEmpty()) {
            txtMajuri.setText("" + 0.0f);
        }
        if (txtAmount.getText().isEmpty() || txtAmount.getText().equals("" + 0.0)) {
            alert.showError("Enter Item Again");
            txtItemName.requestFocus();
            return false;
        }

        return true;
    }
    void calculateAmount() {
        if (txtRate.getText().isEmpty() || txtRate.getText().equals("")) txtRate.setText("" + 0.0);
        if (txtQty.getText().isEmpty() || txtQty.getText().equals("")) txtQty.setText("" + 0.0);
        if (txtMajuri.getText().isEmpty() || txtMajuri.getText().equals("")) txtMajuri.setText("" + 0.0);
        txtAmount.setText(
                String.valueOf(((Float.parseFloat(txtRate.getText()) / 10) * Float.parseFloat(txtQty.getText()))
                        + (Float.parseFloat(txtMajuri.getText()) * Float.parseFloat(txtQty.getText())))
        );

    }
    private void searchCustomer() {
        if (txtCustomer.getText().isEmpty()) txtCustomer.requestFocus();
        Customer customer = customerService.getByCustomerName(txtCustomer.getText());
        if (customer != null) {
            txtCustomerInformation.setText(
                    customer.getAddressline() + "\nVillage/City:"
                            + customer.getVillage() + " Taluka: "
                            + customer.getTaluka() + " District: "
                            + customer.getDistrict() + "\nContact:"
                            + customer.getContact() + " , " + customer.getMobile()
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
//mode Controller
    private void mod() {
        txtModNo.setText("A"+modeService.getNewModeNo());
        dateMod.setValue(LocalDate.now());
        cmbModMetal.getItems().add("Gold");
        cmbModMetal.getItems().add("Silver");
        cmbModPurity.getItems().add("22K");
        cmbModPurity.getItems().add("24K");
        colModSrno.setCellValueFactory(new PropertyValueFactory<>("id"));
        colModItemName.setCellValueFactory(new PropertyValueFactory<>("itemname"));
        colModMetal.setCellValueFactory(new PropertyValueFactory<>("metal"));
        colModPurity.setCellValueFactory(new PropertyValueFactory<>("purity"));
        colModRate.setCellValueFactory(new PropertyValueFactory<>("rate"));
        colModeQty.setCellValueFactory(new PropertyValueFactory<>("weight"));
        colModGhat.setCellValueFactory(new PropertyValueFactory<>("ghat"));
        colModeFinalWeight.setCellValueFactory(new PropertyValueFactory<>("finalweight"));
        colModAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        tableMod.setItems(modetrList);
        txtModeRate.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,100}([\\.]\\d{0,6})?"))
                    txtModeRate.setText(oldValue);
            }
        });
        txtModeItemName.setOnAction(e->{
            if(txtModeItemName.getText().isEmpty() || txtModeItemName.getText().trim().equals("")) return;
            cmbModMetal.requestFocus();
        });
        txtModeWieght.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,100}([\\.]\\d{0,6})?"))
                    txtModeWieght.setText(oldValue);
            }
        });
        txtModeGhat.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,100}([\\.]\\d{0,6})?"))
                    txtModeGhat.setText(oldValue);
            }
        });
        txtModeRate.setOnAction(e->{calculateModeAmount();txtModeWieght.requestFocus();});
        txtModeWieght.setOnAction(e->{calculateModeAmount();txtModeGhat.requestFocus();});
        txtModeGhat.setOnAction(e->{calculateModeAmount();btnModeAdd.requestFocus();});
        btnModeAdd.setOnAction(e->modeAdd());
        btnModeUpdate.setOnAction(e->updateMode());
        btnModeRemove.setOnAction(e->modeRemove());
        btnModclear.setOnAction(e->modeClear());
    }

    private void modeClear() {
        txtModeItemName.setText("");
        cmbModMetal.getSelectionModel().clearSelection();
        cmbModPurity.getSelectionModel().clearSelection();
        txtModeRate.setText("");
        txtModeWieght.setText("");
        txtModeGhat.setText("");
        txtModAmount.setText("");
    }

    private void modeRemove() {
        if(tableMod.getSelectionModel().getSelectedItem()==null) return;
        txtModeTotalAmount.setText(String.valueOf(Float.parseFloat(txtModeTotalAmount.getText())-tableMod.getSelectionModel().getSelectedItem().getAmount()));
        modetrList.remove(tableMod.getSelectionModel().getSelectedIndex());
        calculatePayableAmount();
    }

    private void updateMode() {
        if(tableMod.getSelectionModel().getSelectedItem()==null) return;
        ModeTransaction tr =tableMod.getSelectionModel().getSelectedItem();
        txtModeItemName.setText(tr.getItemname());
        cmbModMetal.setValue(tr.getMetal());
        cmbModPurity.setValue(tr.getPurity());
        txtModeRate.setText(String.valueOf(tr.getRate()));
        txtModeWieght.setText(String.valueOf(tr.getWeight()));
        txtModeGhat.setText(String.valueOf(tr.getGhat()));
        txtModAmount.setText(String.valueOf(tr.getAmount()));
    }

    private void modeAdd() {
        if(!validateMode()) return;
        ModeTransaction modeTransaction = ModeTransaction.builder()
                .amount(Float.parseFloat(txtModAmount.getText()))
                .finalweight(Float.parseFloat(txtModeWieght.getText())-Float.parseFloat(txtModeGhat.getText()))
                .ghat(Float.parseFloat(txtModeGhat.getText()))
                .itemname(txtModeItemName.getText())
                .metal(cmbModMetal.getValue())
                .purity(cmbModPurity.getValue())
                .rate(Float.parseFloat(txtModeRate.getText()))
                .weight(Float.parseFloat(txtModeWieght.getText()))
                .build();
        addModeInModeTransaction(modeTransaction);
        modeClear();
        calculatePayableAmount();

    }

    private void addModeInModeTransaction(ModeTransaction tr) {
        int index=-1;
        for(ModeTransaction t:modetrList)
        {
            if(tr.getItemname().equalsIgnoreCase(t.getItemname()) &&
            tr.getMetal().equalsIgnoreCase(t.getMetal())&&
            tr.getPurity().equalsIgnoreCase(t.getPurity())&&
            tr.getRate().equals(t.getRate()))
            {
                index = modetrList.indexOf(t);
                break;
            }
        }
        if(index==-1)
        {
            tr.setId((long) (modetrList.size()+1));
            modetrList.add(tr);
            tableMod.refresh();
        }
        else
        {
            modetrList.get(index).setWeight(modetrList.get(index).getWeight()+tr.getWeight());
            modetrList.get(index).setGhat(modetrList.get(index).getGhat()+tr.getGhat());
            modetrList.get(index).setAmount(modetrList.get(index).getAmount()+tr.getAmount());
            modetrList.get(index).setFinalweight(modetrList.get(index).getFinalweight()+tr.getFinalweight());
            tableMod.refresh();

        }

        txtModeTotalAmount.setText(String.valueOf(Float.parseFloat(txtModeTotalAmount.getText())+tr.getAmount()));
        txtModNetTotal.setText(String.valueOf(Float.parseFloat(txtModNetTotal.getText())+tr.getAmount()));
        txtModGrandTotal.setText(String.valueOf(Float.parseFloat(txtModGrandTotal.getText())+tr.getAmount()));

    }

    private boolean validateMode() {
        if(txtModeItemName.getText().isEmpty())
        {
            alert.showError("Enter Mode Item Name");
            txtModeItemName.requestFocus();
            return false;
        }
        if(cmbModMetal.getValue()==null)
        {
            alert.showError("Enter Mode Metal Name");
            cmbModMetal.requestFocus();
            return false;
        }
        if(cmbModPurity.getValue()==null)
        {
            alert.showError("Select Mode Item Purity");
            cmbModPurity.requestFocus();
            return false;
        }
        if(txtModeRate.getText().isEmpty() ||txtModeRate.getText().equals(""+0.0) )
        {
            alert.showError("Enter Mode Item Metal Rate");
            txtModeRate.requestFocus();
            return false;
        }
        if(txtModeWieght.getText().isEmpty() || txtModeWieght.getText().equals(""+0.0))
        {
            alert.showError("Enter Mode Weight");
            txtModeWieght.requestFocus();
            return false;
        }
        if(txtModeGhat.getText().isEmpty()) txtModeGhat.setText(""+0.0f);
        if(txtModAmount.getText().isEmpty() || txtModAmount.getText().equals(""+0.0))
        {
            alert.showError("Enter Details Again");
            txtModeItemName.requestFocus();
            return false;
        }
        return true;
    }

    void calculateModeAmount()
    {
        if(txtModeRate.getText().isEmpty() || !isNumeric(txtModeRate.getText())) txtModeRate.setText(""+0.0f);
        if(txtModeWieght.getText().isEmpty() || !isNumeric(txtModeWieght.getText())) txtModeWieght.setText(""+0.0f);
        if(txtModeGhat.getText().isEmpty() || !isNumeric(txtModeGhat.getText())) txtModeGhat.setText(""+0.0f);
        txtModAmount.setText(
                String.valueOf(
                        (Float.parseFloat(txtModeRate.getText())/10)*
                                (
                                        Float.parseFloat(txtModeWieght.getText())-Float.parseFloat(txtModeGhat.getText())
                                        )
                )
        );
    }

    private void serchByDate() {
        if(dateSearch.getValue()==null)return;
        billList.clear();
        billList.addAll(billService.getBillByDate(dateSearch.getValue()));
    }
    private void searchByBillNo() {
        billList.clear();
        if(billService.getBillByBillno(txtBillNoSearch.getText())==null) return;
        billList.add(billService.getBillByBillno(txtBillNoSearch.getText()));
    }
    private void searchByCustomer() {
        if(customerService.getByCustomerName(txtCustomerSearch.getText())!=null)
        {
            billList.clear();
            billList.addAll(billService.getBillByCustomer(customerService.getByCustomerName(txtCustomerSearch.getText()).getId()));
        }
    }
    private void searchAll() {
        billList.clear();
        billList.addAll(billService.getAllBills());
    }


}
