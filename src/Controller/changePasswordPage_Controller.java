package Controller;

import Model.PageLoader;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
/**
 <h1>changePasswordPage_Controller </h1>
 <p>this controller page is a connector between passwordChange FXML(ths stage) and passwordChangeServer class </p>
 @author Ali Varaste Pour
 @version 1.0
 @since 5/23/2021
 @since 2/3/1400
 */
public class changePasswordPage_Controller {

    public JFXTextField newPassword;
    public JFXTextField newPasswordRepeat;
    public Label passwordDoesntMatch;
    private static final String username = Model.passwordRecoveryPageServer.getUsername();
    public Label passwordValidation;

    public void backToPasswordRecovery(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("passwordRecoveryPage");//back arrow leads us to the previous page
    }

    public void changePassword(ActionEvent actionEvent) throws IOException {
        String newPassword = this.newPassword.getText() ;
        String newPasswordRepeat = this.newPasswordRepeat.getText() ;
        passwordDoesntMatch.setVisible(!newPassword.equals(newPasswordRepeat));
        boolean validPassword = Model.changePasswordServer.isValidPassword();
        passwordValidation.setVisible(!validPassword);
        if (newPassword.equals(newPasswordRepeat)){
            Model.changePasswordServer.changePasswordHandler(username , newPassword);
            new PageLoader().load("logInPage");
        }
    }
}
