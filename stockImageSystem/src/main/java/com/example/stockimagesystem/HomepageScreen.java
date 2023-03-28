package com.example.stockimagesystem;

import Entities.Comment;
import Entities.Image;
import Entities.Like;
import Entities.Person;
import datamanager.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HomepageScreen implements Initializable {
    ImageManager imageManager = new ImageManager();
    PersonManager personManager = new PersonManager();
    CommentManager commentManager = new CommentManager();
    LikeManager likeManager = new LikeManager();
    CategoryManager categoryManager = new CategoryManager();
    BelongsToManager belongsToManager = new BelongsToManager();

    @javafx.fxml.FXML
    private TableColumn tblResolution = new TableColumn("First Name");
    @javafx.fxml.FXML
    private TableColumn tblPublishDate = new TableColumn("First Name");;
    @javafx.fxml.FXML
    private TableColumn tblCategory = new TableColumn("First Name");;
    @javafx.fxml.FXML
    private TableColumn tblLikes = new TableColumn("First Name");;
    @javafx.fxml.FXML
    private TableColumn tblId = new TableColumn("First Name");;
    @javafx.fxml.FXML
    private TableColumn tblPublishedBy = new TableColumn("First Name");;
    @javafx.fxml.FXML
    private TableColumn tblImageName = new TableColumn("First Name");;
    @javafx.fxml.FXML
    private TableColumn tblURL = new TableColumn("First Name");;
    @javafx.fxml.FXML
    private TableColumn tblComments = new TableColumn("First Name");;
    ArrayList<TableData> data =new ArrayList<>();
    ObservableList<TableData> odata;
    @javafx.fxml.FXML
    private TableView tblTable;
    @javafx.fxml.FXML
    private Button btnLike;
    @javafx.fxml.FXML
    private Button btnView;
    @javafx.fxml.FXML
    private Button btnComment;
    @javafx.fxml.FXML
    private TextField txtComment;
    @javafx.fxml.FXML
    private Label lblUserInfo;
    @javafx.fxml.FXML
    private Button btnMyProfile;

    @javafx.fxml.FXML
    public void view(ActionEvent actionEvent) throws IOException {
        TableData tableData = (TableData) tblTable.getSelectionModel().getSelectedItem();
        if (tableData==null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select an image!", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        LoginScreen.myImageId = Integer.parseInt(tableData.getId());

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("view-image-page.fxml"));
        Stage stage = (Stage) btnView.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 605, 605);
        stage.setScene(scene);
    }

    @javafx.fxml.FXML
    public void like(ActionEvent actionEvent) {
        TableData tableData = (TableData) tblTable.getSelectionModel().getSelectedItem();
        Like like = new Like(LoginScreen.myPersonId,Integer.parseInt(tableData.getId()));
        likeManager.insert(like);
        refreshTable();
    }

    @javafx.fxml.FXML
    public void comment(ActionEvent actionEvent) {
        TableData tableData = (TableData) tblTable.getSelectionModel().getSelectedItem();
        Comment comment = new Comment(LoginScreen.myPersonId,Integer.parseInt(tableData.getId()),txtComment.getText());
        commentManager.insert(comment);
        refreshTable();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refreshTable();
        lblUserInfo.setText(personManager.getById(LoginScreen.myPersonId).toString());
    }
    public void refreshTable(){
        data =new ArrayList<>();
        odata=null;
        tblTable.getItems().clear();
        for (Image image:imageManager.getData()){
            Person person = personManager.getById(image.getPersonId());

            TableData tableData = new TableData(
                    image.getImageName(),
                    image.getURL(),
                    String.format("%dx%d",image.getWidth(),image.getHeight()),
                    person.getUserName()+" "+person.getFirstName()+" "+person.getLastName(),
                    image.getPublishDate(),
                    categoryManager.getCategoryById(belongsToManager.getCategoriesByImageId(image.getImageId()).get(0).getCategoryId()).getCategoryName(),
                    commentManager.getNumberOfComments(image.getImageId()),
                    likeManager.getNumberOfLikes(image.getImageId()),
                    image.getImageId()
            );

            data.add(tableData);
        }

        odata = FXCollections.observableArrayList(data);

        tblImageName.setCellValueFactory(new PropertyValueFactory<TableData, String>("imageName"));
        tblURL.setCellValueFactory(new PropertyValueFactory<TableData, String>("URL"));
        tblResolution.setCellValueFactory(new PropertyValueFactory<TableData, String>("resolution"));
        tblPublishedBy.setCellValueFactory(new PropertyValueFactory<TableData, String>("publishedBy"));
        tblPublishDate.setCellValueFactory(new PropertyValueFactory<TableData, String>("publishDate"));
        tblCategory.setCellValueFactory(new PropertyValueFactory<TableData, String>("category"));
        tblComments.setCellValueFactory(new PropertyValueFactory<TableData, String>("comments"));
        tblLikes.setCellValueFactory(new PropertyValueFactory<TableData, String>("like"));
        tblId.setCellValueFactory(new PropertyValueFactory<TableData, String>("id"));

        tblTable.setItems(odata);
    }


    @javafx.fxml.FXML
    public void myProfile(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("myprofile.fxml"));
        Stage stage = (Stage) btnMyProfile.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 700, 600);
        stage.setScene(scene);
    }
}
