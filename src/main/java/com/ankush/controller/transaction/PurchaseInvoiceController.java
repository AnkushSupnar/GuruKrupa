package com.ankush.controller.transaction;

import com.ankush.common.CommonData;
import com.ankush.data.entities.PurchaseParty;
import com.ankush.data.service.PurchasePartyService;
import com.ankush.view.StageManager;
import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class PurchaseInvoiceController implements Initializable {
    @Autowired
    @Lazy
    private StageManager stageManager;
    @FXML private Button btnAdd,btnClear,btnClearBill,btnHome,btnNew,btnRemove,btnSave,btnSearch,btnUpdate,btnUpdateBill;
    @FXML private ComboBox<?> cmbBankName;
    @FXML private ComboBox<?> cmbMetal;
    @FXML private ComboBox<?> cmbPurity;
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
    @Autowired
    private PurchasePartyService partyService;
    private PurchaseParty party;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        party = null;
        CommonData.setPARTYNAMES(partyService.getAllPartyNames());
        new AutoCompletionTextFieldBinding<>(txtPartyName,CommonData.getPARTYNAMES());
        btnSearch.setOnAction(e->searchParty());
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
                "PAN:"+party.getContact()+".  GST:"+party.getGst());
    }

}
