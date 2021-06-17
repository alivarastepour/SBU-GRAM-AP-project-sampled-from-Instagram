package Controller;

import Model.PageLoader;
import Model.message;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import java.io.ByteArrayInputStream;
import java.io.IOException;
/**
 <h1>DirectItemController </h1>
 <p>this controller page sets value for each directItem in allDirectsPage</p>
 @author Ali Varaste Pour
 @version 1.0
 @since 6/12/2021
 @since 22/3/1400
 */
public class DirectItemController {
    Model.message message ;
    public AnchorPane root;
    public Circle profilePhoto ;
    public Circle unread ;
    public Label username ;
    public Label lastMessage ;
    public static Model.user user ;

    /**
     * class controller
     * sets a specified message
     * @param message message
     * @throws IOException loading other pages may threw IOException
     */
    public DirectItemController(message message) throws IOException {
        new PageLoader().load("DirectItem", this);
        this.message = message;
    }

    /**
     * initializes each direct item using its user details
     * @return the updated anchorPane
     */
    public AnchorPane init(){

        if (message.getSender().getUserName().equals(logInPage_Controller.Username)){
            username.setText(message.getReceiver().getUserName());
            profilePhoto.setFill(new ImagePattern(new Image(new ByteArrayInputStream(message.getReceiver().getProfilePhoto()))));
        }else{
            username.setText(message.getSender().getUserName());
            profilePhoto.setFill(new ImagePattern(new Image(new ByteArrayInputStream(message.getSender().getProfilePhoto()))));
        }
        lastMessage.setText(message.getMessage());
        unread.setVisible(!message.isRead() && !message.getSender().getUserName().equals(logInPage_Controller.Username));
        return root ;
    }

    /**
     * this method opens the direct page with the current user
     * @param actionEvent on mouse click
     * @throws IOException loading other pages may threw IOException
     */
    public void gotoMessages(ActionEvent actionEvent) throws IOException {
        if (message.getSender().getUserName().equals(logInPage_Controller.Username)){
            user = message.getReceiver();
            Model.allDirectsServer.read(logInPage_Controller.Username , user.getUserName());
        }
        else{
            user = message.getSender();
            Model.allDirectsServer.read(logInPage_Controller.Username , user.getUserName());
        }
        new PageLoader().load("directPage");
    }
}
