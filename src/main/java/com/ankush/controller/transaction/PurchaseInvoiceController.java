package com.ankush.controller.transaction;

import com.ankush.data.entities.PurchaseParty;
import com.ankush.data.service.ItemStockService;
import com.ankush.data.service.PurchasePartyService;
import com.ankush.data.service.RateService;
import com.ankush.view.AlertNotification;
import com.ankush.view.StageManager;
import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

@Component
public class PurchaseInvoiceController implements Initializable {
    @Autowired
    @Lazy
    private StageManager stageManager;
    @FXML private Button btnAdd,btnClear,btnClearBill,btnHome,btnNew,btnRemove,btnSave,btnSearch,btnUpdate,btnUpdateBill;
    @FXML private ComboBox<?> cmbBankName;
    @FXML private ComboBox<String> cmbMetal;
    @FXML private ComboBox<String> cmbPurity;
    @FXML private TableColumn<?, ?> colAmount;
    @FXML private TableColumn<?, ?> colHsn;
    @FXML private TableColumn<?, ?> colItemName;
    @FXML private TableColumn<?, ?> colMajuri;
    @FXML private TableColumn<?, ?> colMetal;
    @FXML private TableColumn<?, ?> colNo;
    @FXML private TableColumn<?, ?> colPurity;
    @FXML private TableColumn<?, ?> colQty;
    @FXML private TableColumn<?, ?> colRate;
    @FXML private DatePicker date;
    @FXML private TableView<?> tableTr;
    @FXML private TextField txtAmount,txtDiscount,txtGrandTotal,txtHsn,txtInvoiceNo,txtItemName;
    @FXML private TextField txtLabour,txtMajuriRate,txtNetTotal,txtOther,txtPaid;
    @FXML private TextArea txtPartyInfo;
    @FXML private TextField txtPartyName,txtQuantity,txtRate,txtTotalMajuri,txtWeight;
    @Autowired private PurchasePartyService partyService;
    @Autowired private ItemStockService stockService;
    @Autowired RateService rateService;
    @Autowired AlertNotification alert;
    private PurchaseParty party;

    private SuggestionProvider<String> partyNameProvider;
    private  SuggestionProvider<String> itemNameProvider;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        party = null;
        cmbMetal.getItems().addAll(rateService.getMetalNames());
        cmbPurity.getItems().addAll(rateService.getPurityNames());
        partyNameProvider = SuggestionProvider.create(partyService.getAllPartyNames());
        itemNameProvider = SuggestionProvider.create(stockService.getItemNames());
        new AutoCompletionTextFieldBinding<>(txtPartyName,partyNameProvider);
        new AutoCompletionTextFieldBinding<>(txtItemName,itemNameProvider);
        btnSearch.setOnAction(e->searchParty());
        btnNew.setOnAction(e->showAddParty(e));
        setTextProperties();
        cmbMetal.setOnAction(e-> {
            if(cmbMetal.getValue()!=null && cmbPurity.getValue()!=null)
            txtRate.setText(String.valueOf(getMetalRate(cmbMetal.getValue(),cmbPurity.getValue())));
        });
        cmbPurity.setOnAction(e->{
            if(cmbMetal.getValue()!=null && cmbPurity.getValue()!=null)
                txtRate.setText(String.valueOf(getMetalRate(cmbMetal.getValue(),cmbPurity.getValue())));
        });
        btnAdd.setOnAction(e->add());
    }

    private float getMetalRate(String metal, String purity) {
        return rateService.getByDateAndMetalAndPurity(date.getValue(),metal,purity).getRate();
    }

    private void add() {
        if(!validate()) return;

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
