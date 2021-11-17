package com.ankush.controller.create;

import com.ankush.config.SpringFXMLLoader;
import com.ankush.view.StageManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class CreateMenuController implements Initializable {
    @Autowired @Lazy
    StageManager stageManager;
    @Autowired
    SpringFXMLLoader loader;

    @FXML private AnchorPane mainPane;
    @FXML private HBox menuAddCustomer;
    @FXML private HBox menuVIewCustomer;
    @FXML private HBox menuAddItem;
    @FXML private HBox menuViewItem;
    @FXML private HBox menuAddUser;
    @FXML private HBox menuEditUser;
    private Pane pane;
    private BorderPane rootPane;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        menuAddCustomer.setOnMouseClicked(e->{
            pane = loader.getPage("/fxml/create/AddCustomer.fxml");
            rootPane = (BorderPane) mainPane.getParent();
            rootPane.setCenter(pane);
        });
    }
}
