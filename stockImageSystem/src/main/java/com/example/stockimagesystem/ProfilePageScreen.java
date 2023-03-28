package com.example.stockimagesystem;

import Entities.FollowingEvent;
import Entities.Person;
import datamanager.FollowingEventManager;
import datamanager.PersonManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfilePageScreen implements Initializable {
    FollowingEventManager followingEventManager =new FollowingEventManager();
    PersonManager personManager = new PersonManager();
    @javafx.fxml.FXML
    private Button btnGoBack;
    @javafx.fxml.FXML
    private Label lblNumberOfFollowers;
    @javafx.fxml.FXML
    private Label lblNumberOfFollowing;
    @javafx.fxml.FXML
    private Button btnFollowEvent;
    @javafx.fxml.FXML
    private Label lblPersonInfo;

    @javafx.fxml.FXML
    public void goBack(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("view-image-page.fxml"));
        Stage stage = (Stage) btnGoBack.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 605, 605);
        stage.setScene(scene);
    }

    @javafx.fxml.FXML
    public void followEvent(ActionEvent actionEvent) {
        Person publisher = ViewImagePageScreen.publisher;
        if (btnFollowEvent.getText().equals("Follow")){
            followingEventManager.insert(new FollowingEvent(publisher.getPersonId(),LoginScreen.myPersonId));
            btnFollowEvent.setText("Unfollow");
        }
        else{
            followingEventManager.deleteById(new FollowingEvent(publisher.getPersonId(),LoginScreen.myPersonId));
            btnFollowEvent.setText("Follow");
        }
        lblNumberOfFollowers.setText("Followers "+followingEventManager.getNumberOfFollowers(publisher.getPersonId()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Person publisher = ViewImagePageScreen.publisher;
        lblPersonInfo.setText(publisher.toString());
        lblNumberOfFollowers.setText("Followers "+followingEventManager.getNumberOfFollowers(publisher.getPersonId()));
        lblNumberOfFollowing.setText("Following "+followingEventManager.getNumberOfFollowing(publisher.getPersonId()));

        if (followingEventManager.isFollowing(publisher.getPersonId(),LoginScreen.myPersonId)){
            btnFollowEvent.setText("Unfollow");
        }
    }
}
