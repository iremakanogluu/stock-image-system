package com.example.stockimagesystem;

import datamanager.LoginManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginScreen {
    LoginManager loginManager = new LoginManager();
    public static int myPersonId;
    public static int myImageId;

    @javafx.fxml.FXML
    private PasswordField txtpassword;
    @javafx.fxml.FXML
    private TextField txtemail;
    @javafx.fxml.FXML
    private Button btnlogin;

    @javafx.fxml.FXML
    public void login(ActionEvent actionEvent) throws IOException {
        if (loginManager.isSucceed(txtemail.getText(),txtpassword.getText())){
            myPersonId = loginManager.getLoginByEmail(txtemail.getText()).getPersonId();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Login successful!", ButtonType.OK);
            alert.showAndWait();
            loginManager.closeAll();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("homepage.fxml"));
            Stage stage = (Stage) btnlogin.getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load(), 1000, 605);
            stage.setScene(scene);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "You entered the wrong password or email!", ButtonType.OK);
            alert.showAndWait();
        }


    }
}
