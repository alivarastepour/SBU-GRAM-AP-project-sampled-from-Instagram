package Controller;

import Model.PageLoader;
import Model.changeProfileDetailsServer;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
/**
 <h1>changeProfileDetailsPage_Controller </h1>
 <p>this controller page is a connector between changeProfileDetails FXML(ths stage) and changeProfileDetailsServer class </p>
 @author Ali Varaste Pour
 @version 1.0
 @since 5/30/2021
 @since 9/3/1400
 */
public class changeProfileDetailsPage_Controller {
    public JFXTextField emailAddress;
    public JFXTextField phoneNumber;
    public JFXTextField city;
    public JFXTextField name;
    public JFXTextField password;
    public static String username ;
    public static Model.user user ;
    public Label unvalidPassword;
    public Label enterName;
    public Label enterCity;
    public Label phoneNumberValidation;
    public Label emailValidation;
    
    /**
     * this method fills a map containing changes of a account
     * @param actionEvent
     * @throws IOException static method which throws Exception from another class is called here
     */
    public void changeDetails(ActionEvent actionEvent) throws IOException {
        String EmailAddress = null;
        String PhoneNumber = null ;
        String City = null;
        String Name = null;
        String Password = null;
        if (!emailAddress.getText().isBlank())
            EmailAddress = emailAddress.getText();
        if (!phoneNumber.getText().isBlank())
            PhoneNumber = phoneNumber.getText();
        if (!city.getText().isBlank())
            City = city.getText();
        if (!name.getText().isBlank())
            Name = name.getText();
        if (!password.getText().isBlank())
            Password = password.getText();
        Map<String , String > map = new HashMap<>();
        map.put("username" , username) ;
        map.put("email" , EmailAddress);
        map.put("phoneNumber" , PhoneNumber);
        map.put("city" , City);
        map.put("name" , Name) ;
        map.put("password" , Password);
        changeProfileDetailsServer.changeProfileDetailsHandler(map);
        boolean validPassword = !changeProfileDetailsServer.validPassword && !password.getText().isBlank() ;
        boolean validName = !changeProfileDetailsServer.validName && !name.getText().isBlank() ;
        boolean validCity = !changeProfileDetailsServer.validCity && !city.getText().isBlank() ;
        boolean validPhoneNumber = !changeProfileDetailsServer.validPhoneNumber && !phoneNumber.getText().isBlank();
        boolean validEmail = !changeProfileDetailsServer.validEmail && !emailAddress.getText().isBlank() ;
        unvalidPassword.setVisible(validPassword);
        enterName.setVisible(validName);
        enterCity.setVisible(validCity);
        phoneNumberValidation.setVisible(validPhoneNumber);
        emailValidation.setVisible(validEmail);
        if (!unvalidPassword.isVisible() && !enterName.isVisible() && !enterCity.isVisible() && !phoneNumberValidation.isVisible() && !emailValidation.isVisible())
            new PageLoader().load("selfProfilePage");
        
    }
    
    public void backToProfilePage(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("selfProfilePage");
    }
}
