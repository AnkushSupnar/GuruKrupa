package com.ankush.controller.transaction;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import com.ankush.data.entities.PurchaseMode;
import com.ankush.data.entities.PurchaseParty;
import com.ankush.data.entities.PurchaseTransaction;
import com.ankush.data.entities.RawMetal;
import com.ankush.data.service.ItemStockService;
import com.ankush.data.service.PurchasePartyService;
import com.ankush.data.service.RateService;
import com.ankush.data.service.RawMetalService;
import com.ankush.view.AlertNotification;
import com.ankush.view.StageManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

@Component
public class PurchaseInvoiceController implements Initializable {
    @Autowired
    @Lazy
    private StageManager stageManager;
    @FXML private Button btnAdd,btnClear,btnClearBill,btnHome,btnNew,btnRemove,btnSave,btnSearch,btnUpdate,btnUpdateBill;
    @FXML private ComboBox<?> cmbBankName;
    @FXML private ComboBox<String> cmbMetal;
    @FXML private ComboBox<String> cmbPurity;
    @FXML private TableView<PurchaseTransaction> tableTr;
    @FXML private TableColumn<PurchaseTransaction,Float> colAmount;
    @FXML private TableColumn<PurchaseTransaction,Long> colHsn;
    @FXML private TableColumn<PurchaseTransaction,String> colItemName;
    @FXML private TableColumn<PurchaseTransaction,Float> colMajuri;
    @FXML private TableColumn<PurchaseTransaction,String> colMetal;
    @FXML private TableColumn<PurchaseTransaction,Long> colNo;
    @FXML private TableColumn<PurchaseTransaction,String> colPurity;
    @FXML private TableColumn<PurchaseTransaction,Float> colQty;
    @FXML private TableColumn<PurchaseTransaction,Float> colRate;
    @FXML private DatePicker date;

    @FXML private TextField txtAmount,txtDiscount,txtGrandTotal,txtHsn,txtInvoiceNo,txtItemName;
    @FXML private TextField txtLabour,txtMajuriRate,txtNetTotal,txtOther,txtPaid;
    @FXML private TextArea txtPartyInfo;
    @FXML private TextField txtPartyName,txtQuantity,txtRate,txtTotalMajuri,txtWeight;
    @FXML private Button btnModAdd;
    @FXML private Button btnModClear;
    @FXML private Button btnModRemove;
    @FXML private Button btnModUpdate;
    @FXML private ComboBox<String> cmbModeMetal;
    @FXML private ComboBox<String> cmbModePurity;
    @FXML private TextField txtModeAmount, txtModeRate, txtModeWeight, txtModTotal;
    @FXML private TableView<PurchaseMode> tableMod;
    @FXML private TableColumn<PurchaseMode,Float> colModAmount;
    @FXML private TableColumn<PurchaseMode,String> colModMetal;
    @FXML private TableColumn<PurchaseMode,String> colModPurity;
    @FXML private TableColumn<PurchaseMode,Float> colModRate;
    @FXML private TableColumn<PurchaseMode,Float> colModWeight;
    @FXML private TableColumn<PurchaseMode,Integer> colModeSr;

    @Autowired private PurchasePartyService partyService;
    @Autowired private ItemStockService stockService;
    @Autowired RateService rateService;
    @Autowired AlertNotification alert;
    @Autowired private RawMetalService rawService;
    private PurchaseParty party;

    private SuggestionProvider<String> partyNameProvider;
    private  SuggestionProvider<String> itemNameProvider;
    private ObservableList<PurchaseTransaction>trList = FXCollections.observableArrayList();
    private ObservableList<PurchaseMode>trModeList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        party = null;
        date.setValue(LocalDate.now());
        cmbMetal.getItems().addAll(rateService.getMetalNames());
        cmbPurity.getItems().addAll(rateService.getPurityNames());
        cmbModeMetal.getItems().addAll(rateService.getMetalNames());
        cmbModePurity.getItems().addAll(rateService.getPurityNames());

        partyNameProvider = SuggestionProvider.create(partyService.getAllPartyNames());
        itemNameProvider = SuggestionProvider.create(stockService.getItemNames());
        new AutoCompletionTextFieldBinding<>(txtPartyName,partyNameProvider);
        new AutoCompletionTextFieldBinding<>(txtItemName,itemNameProvider);
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colHsn.setCellValueFactory(new PropertyValueFactory<>("hsn"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemname"));
        colMajuri.setCellValueFactory(new PropertyValueFactory<>("majuri"));
        colMetal.setCellValueFactory(new PropertyValueFactory<>("metal"));
        colNo.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPurity.setCellValueFactory(new PropertyValueFactory<>("purity"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colRate.setCellValueFactory(new PropertyValueFactory<>("rate"));
        tableTr.setItems(trList);
        btnSearch.setOnAction(e->searchParty());
        btnNew.setOnAction(e->showAddParty(e));
        setTextProperties();
        btnAdd.setOnAction(e->add());
        btnUpdate.setOnAction(e->update());
    }


    private void update() {
        if(tableTr.getSelectionModel().getSelectedItem()==null)return;
        PurchaseTransaction tr = tableTr.getSelectionModel().getSelectedItem();
        txtAmount.setText(String.valueOf(tr.getAmount()));
        txtHsn.setText(String.valueOf(tr.getHsn()));
        txtItemName.setText(tr.getItemname());
        txtMajuriRate.setText(String.valueOf(tr.getMajurirate()));
        txtTotalMajuri.setText(String.valueOf(tr.getMajuri()));
        txtRate.setText(String.valueOf(tr.getRate()));
        cmbMetal.setValue(tr.getMetal());
        cmbPurity.setValue(tr.getPurity());
        
        
    }


    private void add() {
        if(!validate()) return;
        PurchaseTransaction tr = PurchaseTransaction.builder()
                .quantity(Float.parseFloat(txtQuantity.getText()))
                .amount(Float.parseFloat(txtAmount.getText()))
                .rate(Float.parseFloat(txtRate.getText()))
                .hsn(Long.parseLong(txtHsn.getText()))
                .itemname(txtItemName.getText())
                .majuri(Float.parseFloat(txtTotalMajuri.getText()))
                .majurirate(Float.parseFloat(txtMajuriRate.getText()))
                .metal(cmbMetal.getValue())
                .purity(cmbPurity.getValue())
                .build();
        System.out.println(tr);
        addInTrList(tr);



    }

    private void addInTrList(PurchaseTransaction tr) {
        int index=-1;
        for(PurchaseTransaction t:trList)
        {
            if(t.getHsn().longValue()==tr.getHsn().longValue() &&
            t.getItemname().equalsIgnoreCase(tr.getItemname()) &&
            t.getRate().compareTo(tr.getRate())==0 &&
            t.getMetal().equalsIgnoreCase(tr.getMetal()) &&
            t.getPurity().equalsIgnoreCase(tr.getPurity()) &&
            t.getMajurirate().equals(tr.getMajurirate()))
            {
                index=trList.indexOf(t);
                break;
            }
        }
        if(index==-1)
        {
            tr.setId((long) (trList.size()+1));
            trList.add(tr);
        }
        else
        {
            trList.get(index).setQuantity(trList.get(index).getQuantity()+tr.getQuantity());
            tableTr.refresh();
        }
        txtNetTotal.setText(
                String.valueOf(Float.parseFloat(txtNetTotal.getText())+tr.getAmount()-tr.getMajuri())
        );
        txtLabour.setText(
                String.valueOf(Float.parseFloat(txtLabour.getText())+tr.getMajuri())
        );
        calculateGrandTotal();
    }

    private boolean validate() {
        if(txtHsn.getText().isEmpty())
        {
            txtHsn.setText(""+0);
        }
        if(txtItemName.getText().isEmpty() && txtItemName.getText().trim().equals(""))
        {
            alert.showError("Enter Item Name");
            txtItemName.requestFocus();
            return false;
        }
        if(cmbMetal.getValue()==null)
        {
            alert.showError("Select Item Metal");
            cmbMetal.requestFocus();
            return false;
        }
        if(cmbPurity.getValue()==null)
        {
            alert.showError("Select Item Purity");
            cmbPurity.requestFocus();
            return false;
        }
        if(txtRate.getText().isEmpty())
        {
            alert.showError("Enter Metal Rate of 10gm");
            txtRate.requestFocus();
            return false;
        }
        if(txtWeight.getText().isEmpty())
        {
            alert.showError("Enter Item Metal Weight in Gram");
            txtWeight.requestFocus();
            return false;
        }
        if(txtMajuriRate.getText().isEmpty())
        {
            txtMajuriRate.setText(""+0.0f);
        }
        if(txtQuantity.getText().isEmpty())
        {
            alert.showError("Enter Item Quantity");
            txtQuantity.requestFocus();
            return false;
        }

        return true;

    }
    private void setTextProperties() {
        txtHsn.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (!t1.matches("\\d{0,100}([\\.]\\d{0,4})?")) {
                    txtHsn.setText(s);
                }
                else{
                    itemNameProvider.clearSuggestions();
                    itemNameProvider.addPossibleSuggestions(stockService.getItemNamesByHsn(Long.valueOf(txtHsn.getText())));
                }
            }
        });
        txtRate.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (!t1.matches("\\d{0,100}([\\.]\\d{0,4})?"))
                    txtRate.setText(s);
            }
        });
        txtWeight.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (!t1.matches("\\d{0,100}([\\.]\\d{0,4})?")) {
                    txtWeight.setText(s);
                }

            }
        });
        txtMajuriRate.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (!t1.matches("\\d{0,100}([\\.]\\d{0,4})?"))
                    txtMajuriRate.setText(s);

            }
        });
        txtQuantity.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (!t1.matches("\\d{0,100}([\\.]\\d{0,4})?"))
                    txtQuantity.setText(s);
            }
        });
        txtTotalMajuri.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (!t1.matches("\\d{0,100}([\\.]\\d{0,4})?"))
                    txtTotalMajuri.setText(s);
            }
        });
        txtOther.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (!t1.matches("\\d{0,100}([\\.]\\d{0,4})?"))
                    txtOther.setText(s);
            }
        });
        txtDiscount.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (!t1.matches("\\d{0,100}([\\.]\\d{0,4})?"))
                    txtDiscount.setText(s);
            }
        });
        txtPaid.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (!t1.matches("\\d{0,100}([\\.]\\d{0,4})?"))
                    txtPaid.setText(s);
            }
        });
        txtRate.setOnAction(e->{
            calculateAmount();txtWeight.requestFocus();});
        txtWeight.setOnAction(e->{calculateAmount();txtMajuriRate.requestFocus();});
        txtMajuriRate.setOnAction(e->{calculateAmount();txtQuantity.requestFocus();});
        txtQuantity.setOnAction(e->{calculateAmount();txtTotalMajuri.requestFocus();});
        txtTotalMajuri.setOnAction(e->{
            txtMajuriRate.setText(
                    String.valueOf(
                            (
                              Float.parseFloat(txtTotalMajuri.getText())/Float.parseFloat(txtWeight.getText())
                            )///Float.parseFloat(txtQuantity.getText())
                    )
            );
            calculateAmount();
            txtAmount.requestFocus();
        });
        txtModeRate.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (!t1.matches("\\d{0,100}([\\.]\\d{0,4})?"))
                txtModeRate.setText(s);
            }
        });
        txtModeWeight.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (!t1.matches("\\d{0,100}([\\.]\\d{0,4})?"))
                txtModeWeight.setText(s);
            }
        });
        cmbModeMetal.setOnAction(e->{
            if(cmbModePurity.getValue()!=null && !cmbModePurity.getValue().equals(""))
            {

                if(rawService.getByMetalAndPurity(cmbModeMetal.getValue(), cmbModePurity.getValue())!=null)
                txtModeWeight.setText(
                    String.valueOf(rawService.getByMetalAndPurity(cmbModeMetal.getValue(), cmbModePurity.getValue()).getWeight())
                );
            }
        });
        cmbModePurity.setOnAction(e->{
            if(cmbModeMetal.getValue()!=null && !cmbModePurity.getValue().equals(""))
            {

                if(rawService.getByMetalAndPurity(cmbModeMetal.getValue(), cmbModePurity.getValue())!=null)
                txtModeWeight.setText(
                    String.valueOf(rawService.getByMetalAndPurity(cmbModeMetal.getValue(), cmbModePurity.getValue()).getWeight())
                );
            }
        });
        txtModeRate.setOnAction(e->{
            if(txtModeWeight.getText().equals("")) txtModeWeight.setText(""+0.0f);
            if(txtModeRate.getText().isEmpty()) return;

            txtModeAmount.setText(String.valueOf(                
            (Float.parseFloat(txtModeRate.getText())/10)* Float.parseFloat(txtModeWeight.getText())
            )
            );
            txtModeAmount.requestFocus();
        });
        txtModeAmount.setOnAction(e->{
            if(txtModeRate.getText().isEmpty()) txtModeRate.setText(""+0.0f);
            if(txtModeWeight.getText().isEmpty()) txtModeWeight.setText(""+0.0f);
            txtModeAmount.setText(String.valueOf(                
            (Float.parseFloat(txtModeRate.getText())/10)* Float.parseFloat(txtModeWeight.getText()))
            );
            btnModAdd.requestFocus();
        });
        btnModAdd.setOnAction(e->addMode());
    }
    private void addMode() {
        try {
            if(txtModeAmount.getText().isEmpty())
            {
                alert.showError("No data to save");
                return;
            }
            if(txtModeWeight.getText().isEmpty()|| txtModeWeight.getText().equals(""+0.0f))
            {
                alert.showError("No Raw Metal available");
                return;
            }
            if(cmbModeMetal.getValue()==null|| cmbModeMetal.getValue().equals(""))
            {
                alert.showError("Please Select Metal Name");
                return;
            }
            if(cmbModePurity.getValue()==null|| cmbModePurity.getValue().equals(""))
            {
                alert.showError("Please Select Purity Name");
                return;
            }
          RawMetal r=  rawService.getByMetalAndPurity(cmbModeMetal.getValue(), cmbModePurity.getValue());
            if(r.getWeight()<Float.parseFloat(txtModeWeight.getText()))
            {
                alert.showError("Not Enough Metal Quantity\n Available Quantity= "+r.getWeight());
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void calculateAmount()
    {
        if(txtRate.getText().isEmpty()) txtRate.setText(""+0.0);
        if(txtWeight.getText().isEmpty()) txtWeight.setText(""+0.0);
        if(txtMajuriRate.getText().isEmpty()) txtMajuriRate.setText(""+0.0);
        if(txtTotalMajuri.getText().isEmpty()) txtTotalMajuri.setText(""+0.0);
        if(txtQuantity.getText().isEmpty()) txtQuantity.setText(""+0.0);
        if(txtAmount.getText().isEmpty()) txtAmount.setText(""+0.0);

        txtTotalMajuri.setText(
                String.valueOf(
                        (Float.parseFloat(txtMajuriRate.getText())*Float.parseFloat(txtWeight.getText())))             
        );
        // txtAmount.setText(
        //         String.valueOf(
        //             (
        //                 (
        //                     (
        //                         Float.parseFloat(txtRate.getText())/10)*Float.parseFloat(txtWeight.getText()))* Float.parseFloat(txtQuantity.getText()))
        //         +(Float.parseFloat(txtTotalMajuri.getText()))
        //         )
        // );
        txtAmount.setText(
                String.valueOf(
                    (
                        (
                            (Float.parseFloat(txtRate.getText())/10)
                            *Float.parseFloat(txtWeight.getText())
                        )                            
                    )
                +(Float.parseFloat(txtTotalMajuri.getText()))
                )
        );
    }
    private void calculateGrandTotal()
    {
        if(txtNetTotal.getText().isEmpty())txtNetTotal.setText(""+0.0f);
        if(txtLabour.getText().isEmpty())txtLabour.setText(""+0.0f);
        if(txtOther.getText().isEmpty())txtOther.setText(""+0.0f);
        if(txtDiscount.getText().isEmpty())txtDiscount.setText(""+0.0f);
        if(txtGrandTotal.getText().isEmpty())txtGrandTotal.setText(""+0.0f);
        txtGrandTotal.setText(
                String.valueOf(Float.parseFloat(txtNetTotal.getText())+
                        Float.parseFloat(txtLabour.getText())+
                        Float.parseFloat(txtOther.getText())-
                        Float.parseFloat(txtDiscount.getText()))
        );

    }
    private void searchParty() {
        if(txtPartyName.getText().isEmpty()){
            txtPartyName.requestFocus();
            return;
        }
        List<PurchaseParty> partyList = partyService.getPartyByName(txtPartyName.getText());
        if(!partyList.isEmpty()) {
            party = partyList.get(0);
            setParty(party);
        }
        else{
            txtPartyName.requestFocus();
            txtPartyInfo.setText("");
        }

    }
    private void setParty(PurchaseParty party)
    {
        txtPartyInfo.setText("" +
                "Name: "+party.getName()+"\n" +
                "Address: "+party.getAddress()+"\n" +
                "Contact No:"+party.getContact()+"\n" +
                "PAN:"+party.getPan()+".  GST:"+party.getGst());
    }
    private void showAddParty(ActionEvent e) {
        Stage stage = new Stage();
        Parent rootNode = null;
        try {
            rootNode = stageManager.getFxmlLoader().load("/fxml/create/PurchaseParty.fxml");
            Objects.requireNonNull(rootNode, "A Root FXML node must not be null");
            stage.setScene(new Scene(rootNode));
            stage.setTitle("My modal window");
            stage.setTitle("My modal window");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) e.getSource()).getScene().getWindow());
            stage.show();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent e) {
                    partyNameProvider.clearSuggestions();
                    partyNameProvider.addPossibleSuggestions(partyService.getAllPartyNames());
                }
            });
        } catch (Exception exception) {

        }
    }


}
