package Controller;

import Model.PageLoader;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import java.io.*;
/**
 <h1>signUpPage_Controller </h1>
 <p>this controller page is a connector between signUpPage FXML(ths stage)
 and signUpPageServer class </p>
 @author Ali Varaste Pour
 @version 1.0
 @since 5/22/2021
 @since 1/3/1400
 */
public class signUpPage_Controller {

    public JFXTextField userNameField;
    public JFXTextField passwordField;
    public JFXTextField passwordRepeatField;
    public JFXTextField phoneNumField;
    public JFXTextField emailField;
    public Label usernameNotAvailable;
    public Label passwordDoesntMatch;
    public Label passwordValidation;
    public Label emailValidation;
    public Label phoneNumberValidation;
    public JFXButton profilePhoto;
    public Label uploadedPhoto;
    public JFXTextField nameField;
    public JFXTextField cityField;
    public Label enterName;
    public Label enterCity;
    public Circle imageCircle;
    File profilePhotoAddress ;
    boolean fileUploaded = false ;
    public void initialize(){
        nameField.clear();
        userNameField.clear();passwordField.clear();passwordRepeatField.clear();emailField.clear();phoneNumField.clear();
        cityField.clear();
    }

    /**
     * receives data from user
     *  then it sends them to server
     *  then receives answer from from server and performs proper function
     */
    public void signUp() throws IOException {
        String name = nameField.getText();
        String userName = userNameField.getText();
        String password = passwordField.getText();
        String passwordRepeat = passwordRepeatField.getText();
        String email = emailField.getText();
        String phoneNumber = phoneNumField.getText();
        String city = cityField.getText();
        uploadedPhoto.setVisible(profilePhotoAddress == null);
        usernameNotAvailable.setVisible(userNameField.getText().isBlank());
        passwordValidation.setVisible(passwordField.getText().isBlank());
        emailValidation.setVisible(emailField.getText().isBlank());
        phoneNumberValidation.setVisible(phoneNumField.getText().isBlank());
        enterName.setVisible(nameField.getText().isBlank());
        enterCity.setVisible(cityField.getText().isBlank());
        if (profilePhotoAddress != null && !nameField.getText().isBlank()) {
            Model.signUpServer.signUpHandler(name, userName, password, phoneNumber, email, city, profilePhotoAddress.toString());
            passwordDoesntMatch.setVisible(!password.equals(passwordRepeat));
            usernameNotAvailable.setVisible(!Model.signUpServer.isValidUsername());
            passwordValidation.setVisible(!Model.signUpServer.isValidPasswordFormation());
            emailValidation.setVisible(!Model.signUpServer.isValidEmail());
            phoneNumberValidation.setVisible(!Model.signUpServer.isValidPhoneNumber());
            enterName.setVisible(nameField.getText().isEmpty());
            enterCity.setVisible(cityField.getText().isEmpty());
        }
        if (!nameField.getText().isEmpty() && password.equals(passwordRepeat) && Model.signUpServer.isValidUsername() && Model.signUpServer.isValidPasswordFormation() && Model.signUpServer.isValidEmail() && Model.signUpServer.isValidPhoneNumber() && fileUploaded && !cityField.getText().isEmpty()){
            new PageLoader().load("loginPage");
        }
    }

    public void backToLogin(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("loginPage");
    }

    public void setProfilePhoto(ActionEvent actionEvent) throws IOException {
        //opens a fileChooser Dialogue and allows user to choose a profile photo
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Popup());
        FileInputStream fileInputStream = new FileInputStream(file) ;
        profilePhotoAddress = file ;
        fileUploaded = true ;
        byte[] b = fileInputStream.readAllBytes();
        Image image = new Image(new ByteArrayInputStream(b));
        imageCircle.setFill(new ImagePattern(image));
        uploadedPhoto.setVisible(false);
    }
    public void finalize(){
        nameField.clear();
        userNameField.clear();passwordField.clear();passwordRepeatField.clear();emailField.clear();phoneNumField.clear();
        cityField.clear();
    }
}
