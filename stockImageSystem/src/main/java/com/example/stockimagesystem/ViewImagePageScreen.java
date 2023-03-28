package com.example.stockimagesystem;

import Entities.Category;
import Entities.Comment;
import Entities.Person;
import datamanager.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ViewImagePageScreen implements Initializable {
    ImageManager imageManager = new ImageManager();
    PersonManager personManager = new PersonManager();
    LikeManager likeManager = new LikeManager();
    CommentManager commentManager = new CommentManager();
    BelongsToManager belongsToManager = new BelongsToManager();
    CategoryManager categoryManager = new CategoryManager();

    public static Person publisher;

    @javafx.fxml.FXML
    private ImageView img;
    @javafx.fxml.FXML
    private Label lblnumberOfLikes;
    @javafx.fxml.FXML
    private Button btnGoBackHomepage;
    @javafx.fxml.FXML
    private ListView listComments;
    @javafx.fxml.FXML
    private Label lblpublishDate;
    @javafx.fxml.FXML
    private Label lblnumberOfComments;
    @javafx.fxml.FXML
    private Label lblpublisherNameSurname;
    @javafx.fxml.FXML
    private Button btnViewProfile;
    @javafx.fxml.FXML
    private Label lblCategory;
    @javafx.fxml.FXML
    private Label lblpublisherUsername;

    @javafx.fxml.FXML
    public void goBackHomepage(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("homepage.fxml"));
        Stage stage = (Stage) btnGoBackHomepage.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 1000, 605);
        stage.setScene(scene);
    }

    @javafx.fxml.FXML
    public void ViewProfile(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("profilepage.fxml"));
        Stage stage = (Stage) btnViewProfile.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 440, 140);
        stage.setScene(scene);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int imageId = LoginScreen.myImageId;

        publisher = personManager.getById(imageManager.getImageById(imageId).getPersonId());

        StringBuilder categories = new StringBuilder();
        categories.append("Categories:\n");

        belongsToManager.getCategoriesByImageId(imageId).forEach(x -> categories.append(categoryManager.getCategoryById(x.getCategoryId()).getCategoryName()).append("\n"));

        lblnumberOfComments.setText("Comments: " + commentManager.getNumberOfComments(imageId));
        lblnumberOfLikes.setText("Likes: " + likeManager.getNumberOfLikes(imageId));
        lblCategory.setText(String.valueOf(categories));
        lblpublishDate.setText(String.valueOf(imageManager.getImageById(imageId).getPublishDate()));
        lblpublisherNameSurname.setText(publisher.getFirstName() + " " + publisher.getLastName());
        lblpublisherUsername.setText(publisher.getUserName());

        ArrayList<String> comments = new ArrayList<>();
        commentManager.getCommentsByImageId(imageId).forEach(x->comments.add(x.getCommentText()));
        listComments.getItems().addAll(comments);

        String imageSource = imageManager.getImageById(imageId).getURL();

        double fitWidth = img.getFitWidth();
        double fitHeight = img.getFitHeight();
        img.setImage(new Image(imageSource, fitWidth, fitHeight, true, true, true));
    }
}
