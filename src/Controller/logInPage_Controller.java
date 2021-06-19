package Controller;

import Model.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.io.IOException;

/**
 <h1>logInPage_Controller </h1>
 <p>this controller page is a connector between logInPage FXML(ths stage) and logInPageServer class </p>
 @author Ali Varaste Pour
 @version 1.0
 @since 5/22/2021
 @since 1/3/1400
 */
public class logInPage_Controller {

    @FXML
    public JFXTextField username;
    public JFXPasswordField password;
    public JFXButton signIn;
    public Label wrongPass;
    public JFXButton accCreate;
    public JFXButton forgotPass;
    public Label wrongUsername;
    public JFXToggleButton togglePass;
    public JFXTextField password_show;
    public static String Username ;

    public void login(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        //on action , it sends data to logInServer and base on the received data , performs proper action
        String userName = username.getText() ;
        String passWord ;
        if (password.isVisible()) {
            passWord = password.getText();
        }
        else {
            passWord = password_show.getText();
        }
        if (!username.getText().isBlank() && !password.getText().isBlank())
            Model.logInServer.logInHandler(userName , passWord);
        boolean validUsername = Model.logInServer.isValidUsername();
        boolean validPassword = Model.logInServer.isValidPassword();
        if (validUsername && validPassword ){
            wrongPass.setVisible(false);
            wrongUsername.setVisible(false);
            Username = userName ;
            new PageLoader().load("TimeLinePage");
        }else if (validUsername && !validPassword){
            wrongUsername.setVisible(false);
            wrongPass.setVisible(true);
        }else if (!validUsername){
            wrongPass.setVisible(false);
            wrongUsername.setVisible(true);
        }
        validPassword = false;
        validUsername = false ;
    }

    public void showPassword(ActionEvent actionEvent) {
        //controller for the toggle button which hides or reveals password
        if (!password_show.isVisible()){
            password_show.setVisible(true);
            password.setVisible(false);
            password_show.setText(password.getText());
        }
        else{
            password_show.setVisible(false);
            password.setVisible(true);
            password.setText(password_show.getText());
        }
    }
    
    public void toSignUpPage(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("signUpPage");
    }
    
    public void passwordRecovery(ActionEvent actionEvent)throws IOException {
        new PageLoader().load("passwordRecoveryPage");
    }

}
