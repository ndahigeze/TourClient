/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UIControl;

import Domain.Application;
import Domain.Location;
import Domain.LocationServicePackage;
import Domain.Users;
import Services.ApplicationService;
import Services.LocationService;
import Services.ServicePackage;
import Services.UserService;
import UIControl.Inits.ClientSignInit;
import UIControl.Inits.MainUI;
import com.google.gson.Gson;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ndahigeze
 */
public class ClientMainUIController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private Label username;
    @FXML
    private Label usernameLabel;
    @FXML
    private JFXComboBox<Location> lTf;
    @FXML
    private JFXTextField tripNameTf;
    @FXML
    private JFXButton SearchTripBTN;
    @FXML
    private JFXButton refreshTripBTN;
    @FXML
    private TableView<Location> tripView;
    @FXML
    private TableColumn<Location, String> tripNameCol;
    @FXML
    private TableColumn<Location, String> tripDesCol;
    @FXML
    private TableView<LocationServicePackage> serviceView;
    @FXML
    private TableColumn<LocationServicePackage, String> stripNameCol;
    @FXML
    private TableColumn<LocationServicePackage, String> serviceNameCol;
    @FXML
    private TableColumn<LocationServicePackage, Number> amountCol;
    LocationService ls;
    ServicePackage lp;
    int id;
    @FXML
    private JFXButton requestTripBTN;
    @FXML
    private Label searchLabel;
    @FXML
    private Label totalLabel;
    @FXML
    private Label totalTl;
    private JFXTextField unTf;
    @FXML
    private JFXDatePicker dateChoser;
    @FXML
    private Label requireL;
    @FXML
    private Label tripConfirmation;
    @FXML
    private Label locationName;
    @FXML
    private JFXListView<LocationServicePackage> listView;
    @FXML
    private Label tripAmount;
    @FXML
    private JFXButton logoutBTN;
    @FXML
    private TableView<Application> tripDone;
    @FXML
    private TableColumn<Application, Date> tdate;
    @FXML
    private TableColumn<Application, Location> ylocationCol;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
         viewLocation();
          viewService();
          populateComb();
        try {
            userinfo();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ClientMainUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
    
    @FXML
    private void searchTrip(ActionEvent event) {
        ls=new LocationService();
        if(tripNameTf.getText().isEmpty()){
          searchLabel.setText("Searched Nothing");  
        }else{
          List<Location> li=ls.viewByName(tripNameTf.getText());
         ObservableList list=FXCollections.observableArrayList(li);
      if(list.isEmpty()){
       searchLabel.setText("No Location record Found !");
      }else{
      tripNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
      tripDesCol.setCellValueFactory(new PropertyValueFactory<>("Description"));
       tripView.setItems(list);
       int idi=0;
       for(Location l: li){
         idi=l.getCode();
       }
       ServiceviewByLocation(idi);
      }
        }
    }

    @FXML
    private void refreshTripTableBTN(ActionEvent event) {
         viewLocation();
          viewService();
          populateComb();
    }
    
    
    void viewLocation(){
         ls=new LocationService();
      ObservableList list=FXCollections.observableArrayList(ls.viewLocation());
      tripNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
      tripDesCol.setCellValueFactory(new PropertyValueFactory<>("Description"));
      tripView.setItems(list);
    }
    void viewService(){
         lp=new ServicePackage();
       ObservableList list=FXCollections.observableArrayList(lp.viewTripService());
        stripNameCol.setCellValueFactory(new PropertyValueFactory<>("Location"));
        serviceNameCol.setCellValueFactory(new PropertyValueFactory<>("ServiceName"));
        amountCol.setCellValueFactory(new PropertyValueFactory<>("Amount"));
         amountCol.setCellFactory(col -> 
                new TableCell<LocationServicePackage, Number>() { 
               @Override
               public void updateItem(Number Amount, boolean empty) {
                   super.updateItem(Amount, empty);
                   if (empty) {
                       setText(null);
                   } else {
                      setText("RWF "+NumberFormat.getInstance(Locale.US).format(Amount));
                   }
               }
           });
        serviceView.setItems(list);
    }
    
    void ServiceviewByLocation(int id){
       lp=new ServicePackage();
       List<LocationServicePackage> list0=lp.findByLocation(id);
       ObservableList list=FXCollections.observableArrayList(list0);
        stripNameCol.setCellValueFactory(new PropertyValueFactory<>("Location"));
        serviceNameCol.setCellValueFactory(new PropertyValueFactory<>("ServiceName"));
        amountCol.setCellValueFactory(new PropertyValueFactory<>("Amount"));
        amountCol.setCellFactory(col -> 
                new TableCell<LocationServicePackage, Number>() {
               //@Override 
               @Override
               public void updateItem(Number Amount, boolean empty) {
                   super.updateItem(Amount, empty);
                   if (empty) {
                       setText(null);
                   } else {
                      setText("RWF "+NumberFormat.getInstance(Locale.US).format(Amount));
                   }
               }
           });
        serviceView.setItems(list);
        double amount=0;
        for(LocationServicePackage l: list0){
            amount=amount+l.getAmount();
        }
       totalLabel.setText("RWF "+NumberFormat.getInstance(Locale.US).format(amount));
       totalTl.setText("Total Price");
    } 

    @FXML
    private void requestTrip(ActionEvent event) {
        Application p=new Application();
        ApplicationService ps=new ApplicationService();
        if(checkEmpty()){
          requireL.setText("All field Are Required");
        }else{
            Users u=new Users();
            u.setCode(id);
            p.setClient(u);
            p.setLocation(lTf.getValue());
            p.setBookDate(convertToDate(dateChoser.getValue()));
            p.setStatus(0);
            requireL.setText(ps.createRequest(p));
            showTripRequested(lTf.getValue().getCode());
        }
    }
    boolean checkEmpty(){
     return lTf.getValue()==null||
             dateChoser.getValue() ==null;
    }

    @FXML
    private void tableClick(MouseEvent event) {
      int code=tripView.getSelectionModel().getSelectedItem().getCode();
       ServiceviewByLocation(code);
    }
    public void populateComb(){
        ls=new LocationService();
        List<Location> list=ls.viewLocation();
        list.forEach((l) -> {
            lTf.getItems().add(l);
        });
    } 
     public Date convertToDate(LocalDate dateToConvert) {
            return java.util.Date.from(dateToConvert.atStartOfDay()
            .atZone(ZoneId.systemDefault())
            .toInstant());
    }
     
     void showTripRequested(int locId){
         ApplicationService ps=new ApplicationService();
        List<Application> list=ps.tripDetails(id,locId);
        if(list.isEmpty()){
        }else{
          locationName.setText(list.get(0).getLocation().getName());
          serviceDetails(list.get(0).getLocation().getCode());
        }
        
        
        
     }
     void serviceDetails(int id){
          lp=new ServicePackage();
          List<LocationServicePackage> list0=lp.findByLocation(id);
          ObservableList list=FXCollections.observableArrayList(list0);
          tripConfirmation.setText("Details of your Trip");
          listView.getItems().addAll(list);
          double amount=0;
          for(LocationServicePackage p: list0){
              amount=amount+p.getAmount();
          }
          tripAmount.setText("RWF "+NumberFormat.getInstance(Locale.US).format(amount));
        
     }


    @FXML
    private void logout(ActionEvent event) throws Exception {
         MainUI.close();
        try {
            new ClientSignInit().start(new Stage());
        } catch (Exception ex) {
            Logger.getLogger(ClientMainUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    void userinfo() throws FileNotFoundException{
       File f=new File("C://KWAN//UserLoger.txt");
        try (Scanner ini = new Scanner(f)) {
            String txt="";
            while(ini.hasNext()){
                txt +=ini.nextLine();
            }
            id=Integer.parseInt(txt);
            findById(id);
        }
    }
    void findById(int id){
       UserService us=new UserService();
       Users u=us.findById(id);
        usernameLabel.setText("First Name: "+u.getFirstName()+" LastName: "+u.getLastName()+" "+"   Username: "+u.getUserName());
         viewdone();
    }
    void viewdone(){
         ApplicationService ap=new ApplicationService();
         ObservableList list=FXCollections.observableArrayList(ap.findByDoneBy(id));
         tdate.setCellValueFactory(new PropertyValueFactory<>("BookDate"));
         ylocationCol.setCellValueFactory(new PropertyValueFactory<>("Location"));
         tripDone.setItems(list);
        
    }

}
