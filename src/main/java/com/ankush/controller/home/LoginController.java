package com.ankush.controller.home;

import com.ankush.common.CommonData;

import com.ankush.data.entities.Login;
import com.ankush.data.service.LoginService;
import com.ankush.view.AlertNotification;
import com.ankush.view.FxmlView;
import com.ankush.view.StageManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class LoginController implements Initializable {
    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);
    @Autowired @Lazy
    StageManager stageManager;

    @FXML private AnchorPane mainPane;
    @FXML private StackPane loginBox;
    @FXML private StackPane registerPane;
    @FXML private ComboBox<String> cmbUserName;
    @FXML private PasswordField txtPassword;
    @FXML private Button btnLogin;
    @FXML private Button btnCancel;

    @FXML private TextField txtNewUserName;
    @FXML private PasswordField txtNewPassword;
    @FXML private PasswordField txtNewRePassword;
    @FXML private Button btnRegister;
    @FXML private Button btnNewCancel;
    @Autowired
    public  AlertNotification alert;
    @Autowired
    LoginService loginService;
    List<String> usernames = new ArrayList<>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        usernames.addAll(loginService.getAllUserNames());

        if(usernames.size()==0)
        {
            registerPane.setVisible(true);
            registerPane.setLayoutX(loginBox.getLayoutX());
            registerPane.setLayoutY(loginBox.getLayoutY());
            loginBox.setVisible(false);
        }else {cmbUserName.getItems().addAll(usernames);}
    btnLogin.setOnAction(e->login());
    btnCancel.setOnAction(e->{
        cmbUserName.getSelectionModel().clearSelection();
        txtPassword.setText("");
    });
    btnRegister.setOnAction(e->register());
    }

    private void register() {
        if(txtNewUserName.getText().isEmpty())
        {
            alert.showError("Enter New User Name");
            txtNewUserName.requestFocus();
            return;
        }
        if(txtNewPassword.getText().isEmpty())
        {
            alert.showError("Enter New Password");
            txtNewPassword.requestFocus();
            return;
        }
        if(txtNewRePassword.getText().isEmpty())
        {
            alert.showError("Enter Re-passwprd");
            txtNewRePassword.requestFocus();
            return;
        }
        if(!txtNewPassword.getText().equals(txtNewRePassword.getText()))
        {
            alert.showError("Both Passwprd are Not Same Please Re-Enter");
            txtNewPassword.requestFocus();
            return;
        }
        Login login = Login.builder()
                .password(txtNewPassword.getText())
                .username(txtNewUserName.getText())
                .build();
        if(loginService.saveLogin(login)==1)
        {
            alert.showSuccess("User Saved Success! Now You can Login");
            loginBox.setVisible(true);
            registerPane.setVisible(false);
            usernames.addAll(loginService.getAllUserNames());
            cmbUserName.getItems().clear();
            cmbUserName.getItems().addAll(usernames);

        }
    }

    private void login() {
        if(cmbUserName.getValue()==null)
        {
            alert.showError("Select UserName");
            cmbUserName.requestFocus();
            return;
        }
        if(txtPassword.getText().isEmpty())
        {
            alert.showError("Enter Password");
            txtPassword.requestFocus();
            return;

        }
        if(cmbUserName.getValue().equals("Admin") && txtPassword.getText().equals("123"))
        {
            alert.showSuccess("Login Success");
            stageManager.switchScene(FxmlView.HOME);
        }
        else{
            alert.showError("Enter Correct Password");
            txtPassword.requestFocus();
            txtPassword.setText("");
        }
    }
}
