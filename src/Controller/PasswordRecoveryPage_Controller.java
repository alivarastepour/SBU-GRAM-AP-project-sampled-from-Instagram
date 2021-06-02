package Controller;

import Model.PageLoader;
import Model.passwordRecoveryPageServer;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
/**
 <h1>PasswordRecoveryPage_Controller </h1>
 <p>this controller page is a connector between PasswordRecoveryPage FXML(ths stage)
 and PasswordRecoveryPageServer class </p>
 @author Ali Varaste Pour
 @version 1.0
 @since 5/22/2021
 @since 1/3/1400
 */
public class PasswordRecoveryPage_Controller {

    public JFXTextField username;
    public JFXTextField emailAddress;
    public JFXButton recoverPassword;
    public JFXTextField phoneNumber;
    public JFXToggleButton toggleAction;
    public boolean finalChoose = false;
    public boolean validInfo = false ;
    public Label notFound;

    public void actionChooser(){
        /*
        recovering password is available via email address or phone number
        this toggle button helps user to choose how they want to recover password
        if tha action chooser is true then the process will proceed using phone number
        otherwise process will rely on email address
        */
        boolean actionChooser = toggleAction.isSelected();
        if (actionChooser){
            phoneNumber.setDisable(false);
            emailAddress.setDisable(true);
        }
        else{
            emailAddress.setDisable(false);
            phoneNumber.setDisable(true);
        }
        finalChoose = actionChooser ;
    }

    public void recoverPassword(ActionEvent actionEvent) throws IOException {
        //on action , it sends data to PasswordRecoveryServer and base on the received data , performs proper action
        String username = this.username.getText();
        String email = "";
        String phoneNumber = "" ;//action chooser true
        if (finalChoose) {
            phoneNumber = this.phoneNumber.getText();
        }else
            email = this.emailAddress.getText();
        System.out.println("possiblw wrong 1" + username);
//        passwordRecoveryPageServer.username = username ;
        Model.passwordRecoveryPageServer.passwordRecoveryHandler(username , email , phoneNumber , finalChoose ) ;
        boolean RecoverValidity = passwordRecoveryPageServer.isValidInfo();
        if (RecoverValidity)
            new PageLoader().load("changePasswordPage");
        else
            notFound.setVisible(true);
    }

    public void backToLogin(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("loginPage");
    }

}
