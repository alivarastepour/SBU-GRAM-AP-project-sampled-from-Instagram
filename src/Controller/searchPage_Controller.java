package Controller;

import Model.PageLoader;
import Model.othersProfileServer;
import Model.user;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
/**
 <h1>searchPage_Controller </h1>
 <p>this controller page is a connector between searchPage FXML(ths stage) and searchServer class </p>
 @author Ali Varaste Pour
 @version 1.0
 @since 6/3/2021
 @since 12/3/1400
 */
public class searchPage_Controller {
    public Label validUsername;
    public JFXTextField searchBox;
    public JFXButton follow;
    public JFXButton unfollow;
    static String username ;
    public JFXButton mute;
    public JFXButton unmute;
    public JFXButton direct;
    public static String directUser ;
    public static user User ;
    public JFXButton block ;
    public JFXButton unblock ;

    //searches for a user using given username
    public void search(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        String username = searchBox.getText();
        user user = Model.searchServer.searchHandler(username);
        if (user == null){
            validUsername.setVisible(true);
            follow.setVisible(false);
            unfollow.setVisible(false);
            mute.setVisible(false);
            unmute.setVisible(false);
            direct.setVisible(false);
            block.setVisible(false);
            unblock.setVisible(false);
        }
        else{
            User = user ;
            searchPage_Controller.username = username ;
            boolean condition = othersProfileServer.followValidity(searchPage_Controller.username , TimeLinePage_Controller.LoggedInUsername);
            boolean condition1 = othersProfileServer.muteValidity(searchPage_Controller.username , TimeLinePage_Controller.LoggedInUsername);
            othersProfileServer.blockAction(searchPage_Controller.username , TimeLinePage_Controller.LoggedInUsername , "checkIfBlocked");
            boolean condition2 = othersProfileServer.alreadyBlocked;
            block.setVisible(!condition2);
            unblock.setVisible(condition2);
            validUsername.setVisible(false);
            direct.setVisible(true);
            follow.setVisible(true);
            unfollow.setVisible(true);
            mute.setVisible(true);
            unmute.setVisible(true);
            follow.setDisable(condition);
            unfollow.setDisable(!condition);
            mute.setDisable(!condition1 && unfollow.isDisable());
            unmute.setDisable(!condition1 && unfollow.isDisable());
        }
    }
    //starts a process of following
    public void follow(ActionEvent actionEvent) throws IOException {
        Model.othersProfileServer.FollowHandler(searchPage_Controller.username,TimeLinePage_Controller.LoggedInUsername);
        follow.setDisable(false);
        unfollow.setDisable(true);
    }
    //ends a process of following
    public void unfollow(ActionEvent actionEvent) throws IOException {
        Model.othersProfileServer.unFollowHandler(searchPage_Controller.username,TimeLinePage_Controller.LoggedInUsername);
        unfollow.setDisable(false);
        follow.setDisable(true);
    }
    //starts a process of muting
    public void mute(ActionEvent actionEvent) throws IOException {
        Model.othersProfileServer.muteHandler(searchPage_Controller.username,TimeLinePage_Controller.LoggedInUsername);
        mute.setDisable(false);
        unmute.setDisable(true);
    }
    //ends a process of muting
    public void unmute(ActionEvent actionEvent) throws IOException {
        Model.othersProfileServer.unMuteHandler(searchPage_Controller.username,TimeLinePage_Controller.LoggedInUsername);
        unmute.setDisable(false);
        mute.setDisable(true);
    }
    public void returnToTimeLine(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("TimeLinePage");
    }
    //opens directPage with user
    public void direct(ActionEvent actionEvent) throws IOException {
        directUser = searchBox.getText();
        DirectItemController.user = User ;
        new PageLoader().load("directPage");
    }
    //starts process of blocking
    public void block(ActionEvent actionEvent) throws IOException {
        othersProfileServer.blockAction(searchPage_Controller.username,TimeLinePage_Controller.LoggedInUsername , "block");
        block.setDisable(true);
        unblock.setDisable(false);
    }
    //starts process of unblocking
    public void unblock(ActionEvent actionEvent) throws IOException {
        othersProfileServer.blockAction(searchPage_Controller.username,TimeLinePage_Controller.LoggedInUsername , "unblock");
        unblock.setDisable(true);
        block.setDisable(false);
    }
}
