package com.example.stockimagesystem;

import Entities.Login;
import Entities.Person;
import datamanager.LoginManager;
import datamanager.PersonManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.time.ZoneId;

public class SignupScreen {
    PersonManager personManager = new PersonManager();
    LoginManager loginManager = new LoginManager();

    @javafx.fxml.FXML
    private TextField firstName;
    @javafx.fxml.FXML
    private TextField lastName;
    @javafx.fxml.FXML
    private Button goLoginPage;
    @javafx.fxml.FXML
    private PasswordField password;
    @javafx.fxml.FXML
    private DatePicker birthdate;
    @javafx.fxml.FXML
    private TextField gender;
    @javafx.fxml.FXML
    private Label welcomeText;
    @javafx.fxml.FXML
    private Button signup;
    @javafx.fxml.FXML
    private TextField email;
    @javafx.fxml.FXML
    private TextField username;

    @javafx.fxml.FXML
    public void goLoginPage(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Stage stage = (Stage) goLoginPage.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 505, 605);
        stage.setScene(scene);
    }

    @javafx.fxml.FXML
    public void btnSignUp(ActionEvent actionEvent) {
        int personId = personManager.getData().size() + 1;
        Person person = new Person(
                personId,
                firstName.getText(),
                lastName.getText(),
                username.getText(),
                gender.getText(),
                Date.valueOf(birthdate.getValue())
        );
        personManager.insert(person);
        personManager.closeAll();

        Login login = new Login(
                loginManager.getData().size()+1,
                email.getText(),
                password.getText(),
                personId
        );

        loginManager.insert(login);
        loginManager.closeAll();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Sign up successful!", ButtonType.OK);
        alert.showAndWait();
    }
}
