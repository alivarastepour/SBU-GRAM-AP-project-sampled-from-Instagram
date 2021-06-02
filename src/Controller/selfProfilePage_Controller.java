package Controller;

import Model.PageLoader;
import Model.selfProfileServer;
import Model.user;
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
 <h1>selfProfilePage_Controller </h1>
 <p>this controller page is a connector between selfProfilePage FXML(ths stage)
 and selfProfilePageServer class </p>
 @author Ali Varaste Pour
 @version 1.0
 @since 5/25/2021
 @since 4/3/1400
 */
public class selfProfilePage_Controller {

    public Label username;
    public Label city;
    public Label name;
    public Label following;
    public Label follower;
    public Circle profilePhoto;
    public ImageView backArrow;
    
    /**
     * since most fields in this page are variables and differs from one page to another they should be initialized by the time page opens
     * @throws IOException
     * @throws ClassNotFoundException line 42 calls a method from another class, that method reads an object an tries to cast it
     *                                to another object ; hence there is a likelihood of ClassNotFoundException to be threw
     */
    public void initialize() throws IOException, ClassNotFoundException {
        user selfUser = selfProfileServer.selfProfileHandler();
        name.setText(selfUser.getName());
        username.setText(selfUser.getUserName());
        city.setText(selfUser.getCity());
        following.setText(String.valueOf(selfUser.getFollowings()));
        follower.setText(String.valueOf(selfUser.getFollowers()));
        byte[] b = selfUser.getProfilePhoto();
        Image image = new Image(new ByteArrayInputStream(b));
        profilePhoto.setFill(new ImagePattern(image));

    }

    public void backToTimeLine(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("TimeLinePage");
    }

    public void changeProfilePhoto(MouseEvent mouseEvent) throws IOException {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Popup());
        FileInputStream fileInputStream = new FileInputStream(file) ;
        byte[] b = fileInputStream.readAllBytes();
        Image image = new Image(new ByteArrayInputStream(b));
        profilePhoto.setFill(new ImagePattern(image));
        selfProfileServer.ChangeProfilePhoto(username.getText() , file.toString());
    }
    
    public void changeProfileDetails(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("changeProfileDetailsPage");
    }
}
