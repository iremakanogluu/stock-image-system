package com.example.stockimagesystem;

import Entities.BelongsTo;
import Entities.Image;
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
import java.sql.Date;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MyprofileScreen implements Initializable {
    ImageManager imageManager = new ImageManager();
    PersonManager personManager = new PersonManager();
    CommentManager commentManager = new CommentManager();
    LikeManager likeManager = new LikeManager();
    CategoryManager categoryManager = new CategoryManager();
    BelongsToManager belongsToManager = new BelongsToManager();
    FollowingEventManager followingEventManager = new FollowingEventManager();

    ArrayList<TableData> data =new ArrayList<>();
    ObservableList<TableData> odata;

    @javafx.fxml.FXML
    private Button btnGoBack;
    @javafx.fxml.FXML
    private Label lblNumberOfFollowers;
    @javafx.fxml.FXML
    private Label lblNumberOfFollowing;
    @javafx.fxml.FXML
    private Label lblPersonInfo;
    @javafx.fxml.FXML
    private Button btnDelete;
    @javafx.fxml.FXML
    private TextField txtImageName;
    @javafx.fxml.FXML
    private TextField txtWidth;
    @javafx.fxml.FXML
    private TextField txtHeight;
    @javafx.fxml.FXML
    private ChoiceBox chcCategories;
    @javafx.fxml.FXML
    private TableView tblMyImages;
    @javafx.fxml.FXML
    private TextField txtURL;
    @javafx.fxml.FXML
    private Button btnShare;
    @javafx.fxml.FXML
    private TableColumn tblPublishDate;
    @javafx.fxml.FXML
    private TableColumn tblCategory;
    @javafx.fxml.FXML
    private TableColumn tblLikes;
    @javafx.fxml.FXML
    private TableColumn tblImageName;
    @javafx.fxml.FXML
    private TableColumn tblURL;
    @javafx.fxml.FXML
    private TableColumn tblComments;
    @javafx.fxml.FXML
    private TableColumn tblResolution;
    @javafx.fxml.FXML
    private TableColumn tblImageId;

    @javafx.fxml.FXML
    public void goBack(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("homepage.fxml"));
        Stage stage = (Stage) btnGoBack.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 1000, 605);
        stage.setScene(scene);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Person me = personManager.getById(LoginScreen.myPersonId);
        lblPersonInfo.setText(me.toString());
        lblNumberOfFollowers.setText("Followers "+followingEventManager.getNumberOfFollowers(me.getPersonId()));
        lblNumberOfFollowing.setText("Following "+followingEventManager.getNumberOfFollowing(me.getPersonId()));

        ArrayList<String> categories = new ArrayList<>();

        categoryManager.getData().forEach(x->categories.add(x.getCategoryName()));
        chcCategories.getItems().addAll(categories);
        refreshTable();
    }

    @javafx.fxml.FXML
    public void share(ActionEvent actionEvent) {
        try{
            String imageName = txtImageName.getText();
            String URL = txtURL.getText();
            int width = Integer.parseInt(txtWidth.getText());
            int height = Integer.parseInt(txtHeight.getText());
            int newImageId = imageManager.getData().size()+1;
            Date sqlDate = new java.sql.Date(System.currentTimeMillis());
            Image myImage = new Image(newImageId,
                    imageName,
                    URL,
                    width,
                    height,
                    LoginScreen.myPersonId,
                    sqlDate
            );

            imageManager.insert(myImage);
            String selectedCategoryName = (String) chcCategories.getSelectionModel().getSelectedItem();
            int selectedCategoryId = categoryManager.getCategoryIdByCategoryName(selectedCategoryName);
            BelongsTo belongsTo = new BelongsTo(selectedCategoryId,newImageId);
            belongsToManager.insert(belongsTo);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter all entries!", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "The image shared!", ButtonType.OK);
        alert.showAndWait();

        refreshTable();
    }

    @javafx.fxml.FXML
    public void delete(ActionEvent actionEvent) {
        TableData tableData = (TableData) tblMyImages.getSelectionModel().getSelectedItem();
        if (tableData==null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select an image!", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure? Selected image will be deleted", ButtonType.YES,ButtonType.NO);
        alert.showAndWait();


        if (alert.getResult()==ButtonType.YES){
            int myImageId = Integer.parseInt(tableData.getId());
            imageManager.deleteById(myImageId);

            for (BelongsTo c:belongsToManager.getCategoriesByImageId(myImageId)){
                belongsToManager.deleteById(c);
            }

            refreshTable();
        }

    }

    public void refreshTable(){
        data =new ArrayList<>();
        odata=null;
        tblMyImages.getItems().clear();
        for (Image image:imageManager.getData()){
            Person person = personManager.getById(image.getPersonId());

            if (image.getPersonId()==LoginScreen.myPersonId){
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
        }

        odata = FXCollections.observableArrayList(data);

        tblImageName.setCellValueFactory(new PropertyValueFactory<TableData, String>("imageName"));
        tblURL.setCellValueFactory(new PropertyValueFactory<TableData, String>("URL"));
        tblResolution.setCellValueFactory(new PropertyValueFactory<TableData, String>("resolution"));
        tblPublishDate.setCellValueFactory(new PropertyValueFactory<TableData, String>("publishDate"));
        tblCategory.setCellValueFactory(new PropertyValueFactory<TableData, String>("category"));
        tblComments.setCellValueFactory(new PropertyValueFactory<TableData, String>("comments"));
        tblLikes.setCellValueFactory(new PropertyValueFactory<TableData, String>("like"));
        tblImageId.setCellValueFactory(new PropertyValueFactory<TableData, String>("id"));


        tblMyImages.setItems(odata);
    }
}
