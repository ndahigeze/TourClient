/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UIControl;

import UIControl.Inits.ClientSignInit;
import Domain.Location;
import Domain.LocationServicePackage;
import Domain.Privilege;
import Domain.Users;
import Services.Converter;
import Services.LocationService;
import Services.ServicePackage;
import Services.UserService;
import UIControl.Inits.MainUI;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ndahigeze
 */
public class ClientSignupController implements Initializable {

    @FXML
    private TextField sunTf;
    @FXML
    private PasswordField spTf;
    @FXML
    private TextField fTf;
    @FXML
    private TextField lnTf;
    @FXML
    private TextField unTf;
    @FXML
    private TextField eTf;
    @FXML
    private PasswordField pTf;
    @FXML
    private PasswordField cpTf;
     @FXML
    private JFXButton signUp;
         @FXML
    private JFXButton signInBTN;
      
    /**
     * Initializes the controller class.
     */
         UserService us ;
          UserService s;
          Converter c;
          public Stage st;
    @FXML
    private JFXComboBox<String> gComb;
    @FXML
    private Label infoLabel;
    @FXML
    private Label LocationName;
    @FXML
    private Label totalAmountLabel;
    @FXML
    private TableView<LocationServicePackage> serviceList;
    @FXML
    private TableColumn<LocationServicePackage, String> serviceName;
    @FXML
    private TableColumn<LocationServicePackage, Number> serviceAmouunt;
    @FXML
    private TableView<Location> locationList;
    @FXML
    private TableColumn<Location, String> locationListCol;
    @FXML
    private Label loginConf;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        combElement();
        viewLocation();
    } 
    
    public void createAccount(){
        String responce="";
        us=new UserService();
        Privilege p=new Privilege();
        p.setCode(1);
        Converter c=new Converter();
        Users u=new Users();
      if(!checkEmpty()){
         u.setFirstName(fTf.getText());
         u.setLastName( lnTf.getText());
         u.setEmainl( eTf.getText());
         u.setUserName( unTf.getText());
         u.setPassword(c.md5(pTf.getText()));
         u.setRegDate(new Date());
         u.setGender(gComb.getValue());
         u.setPrivilege(p);
          infoLabel.setText(us.createUser(u));
         cleanField();
      }else{
          infoLabel.setText(" !All field Are Required"); 
      }
    }
   
    @FXML
    void signIn(ActionEvent event) throws Exception {
     s=new UserService();
     c=new Converter();
     if(!sunTf.getText().isEmpty()&&!spTf.getText().isEmpty()){
               List<Users> l=s.login(sunTf.getText(),spTf.getText());
             if(!l.isEmpty()){
                 int id=l.get(0).getCode();
                   signup(id);
             }else{
             loginConf.setText("Username or Passwor is incorect try again");
             }
     }else{
         loginConf.setText("Username or Passwor is incorect try again");
     }
     cleanField();
    }

    @FXML
    void signUp(ActionEvent event) {
        
           String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);         
        Matcher m = pattern.matcher(eTf.getText()); 
       if(match()){
           if(m.matches()){
             createAccount();
           }else{
              infoLabel.setText("!Put Valid Email");
           }
       }else{
         infoLabel.setText("!Password not Match");
       }
    }
    
     public boolean checkEmpty(){
      return 
              fTf.getText().isEmpty()||
              lnTf.getText().isEmpty()||
              unTf.getText().isEmpty()||
              eTf.getText().isEmpty()||
              pTf.getText().isEmpty()||
              cpTf.getText().isEmpty()||
              gComb.getValue().isEmpty();
    }
    
   public boolean match(){
    c=new Converter();
    return c.md5(cpTf.getText()).equals(c.md5(pTf.getText()));
   }
       public void combElement(){
       
           gComb.getItems().addAll("Male","Female");
     }
       
       public String checkSave(String username){
         s=new UserService();
          List<Users>  l=s.findByUsername(username);
         if(l.isEmpty()){
             return "Account Created Successfull";
         }else{
             return "Username is Already Taken, try to find Different One";
         }
       }

       
    void viewLocation(){
       LocationService ls=new LocationService();
       ObservableList list=FXCollections.observableArrayList(ls.viewLocation());
       locationListCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
       locationList.setItems(list);
    }  
       
    @FXML
    private void locationListClick(MouseEvent event) {
     int id=locationList.getSelectionModel().getSelectedItem().getCode();
       ServicePackage lp=new ServicePackage();
       List<LocationServicePackage> list0=lp.findByLocation(id);
       ObservableList list=FXCollections.observableArrayList(list0);
        serviceName.setCellValueFactory(new PropertyValueFactory<>("ServiceName"));
        serviceAmouunt.setCellValueFactory(new PropertyValueFactory<>("Amount"));
        serviceAmouunt.setCellFactory(col -> 
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
         serviceList.setItems(list);
         double amount=0.0;
       for(LocationServicePackage l: list0){
         amount=amount+l.getAmount();
       }
       totalAmountLabel.setText("Total Amount Per Trip: RWF"+NumberFormat.getInstance(Locale.US).format(amount));
    }
    
      public void cleanField(){
             fTf.setText("");
              lnTf.setText("");
              unTf.setText("");
              eTf.setText("");
              pTf.setText("");
              cpTf.setText("");
              gComb.setValue("");
              cpTf.setText("");
              pTf.setText("");
      }
      
    void signup(int id) throws IOException, Exception{
      /*  Stage stage =new Stage();
         FXMLLoader loader=new FXMLLoader();
         Parent root = loader.load(getClass().getResource("/UIFxml/ClientMainUI.fxml").openStream());
          ClientMainUIController co=(ClientMainUIController)loader.getController();
          co.checkId(username,names,id);
           Scene scene = new Scene(root);
           stage.setScene(scene);
           stage.show(); 
           st=stage;
           */
         
         File f=new File("C://KWAN//UserLoger.txt");
          f.createNewFile();
          PrintWriter pr=new PrintWriter(f);
          pr.println(id);
          pr.flush();
          pr.close();
           new MainUI().start(new Stage());
           ClientSignInit.close();
                  
    }
     
     
}
