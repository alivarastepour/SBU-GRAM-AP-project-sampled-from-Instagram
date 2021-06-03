package Controller;

import Model.PageLoader;
import Model.othersProfileServer;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import java.io.ByteArrayInputStream;
import java.io.IOException;
/**
 <h1>othersProfilePage_Controller </h1>
 <p>this controller page is a connector between othersProfile FXML(ths stage) and othersProfileServer class </p>
 @author Ali Varaste Pour
 @version 1.0
 @since 6/1/2021
 @since 10/3/1400
 */
public class othersProfilePage_Controller {
    public Label username;
    public Label name;
    public Label city;
    public Label following;
    public Label follower;
    public Circle profilePhoto;
    public JFXButton mute;
    public JFXButton unmute;
    public JFXButton unfollow;
    public JFXButton follow;
    boolean alreadyFollowed ;
    boolean alreadyMuted ;
    /**
     * since most fields in this page are variables and differs from one page to another they should be initialized by the time page opens
     */
    public void initialize() throws IOException {
        username.setText(PostItemController.PublisherUser.getUserName());
        name.setText(PostItemController.PublisherUser.getName());
        city.setText(PostItemController.PublisherUser.getCity());
        following.setText(String.valueOf(PostItemController.PublisherUser.getFollowings()));
        follower.setText(String.valueOf(PostItemController.PublisherUser.getFollowers()));
        profilePhoto.setFill(
                new ImagePattern(
                        new Image(
                                new ByteArrayInputStream(
                                        PostItemController.PublisherUser.getProfilePhoto()))));
        alreadyFollowed = othersProfileServer.followValidity(PostItemController.PublisherUser.getUserName(),TimeLinePage_Controller.LoggedInUsername);
        alreadyMuted = othersProfileServer.muteValidity(PostItemController.PublisherUser.getUserName(),TimeLinePage_Controller.LoggedInUsername);
        follow.setVisible(!alreadyFollowed);
        unfollow.setVisible(alreadyFollowed);
        mute.setVisible(alreadyFollowed && !alreadyMuted);
        unmute.setVisible(alreadyFollowed && alreadyMuted);
    }
    public void backToTimeLine(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("TimeLinePage");
    }
    //to be completed
    public void follow(ActionEvent actionEvent) throws IOException {
        follow.setVisible(false);
        unfollow.setVisible(true);
        Model.othersProfileServer.FollowHandler(PostItemController.PublisherUser.getUserName(),TimeLinePage_Controller.LoggedInUsername);
    }
    //to be completed
    
    public void unfollow(ActionEvent actionEvent) throws IOException {
        follow.setVisible(true);
        unfollow.setVisible(false);
        Model.othersProfileServer.unFollowHandler(PostItemController.PublisherUser.getUserName(),TimeLinePage_Controller.LoggedInUsername);
    }
    //to be completed
    public void mute(ActionEvent actionEvent) throws IOException {
        mute.setVisible(false);
        unmute.setVisible(true);
        Model.othersProfileServer.muteHandler(PostItemController.PublisherUser.getUserName(),TimeLinePage_Controller.LoggedInUsername);
    }
    //to be completed
    public void unmute(ActionEvent actionEvent) throws IOException {
        mute.setVisible(true);
        unmute.setVisible(false);
        Model.othersProfileServer.unMuteHandler(PostItemController.PublisherUser.getUserName(),TimeLinePage_Controller.LoggedInUsername);
    }
}
