package Controller;

import Model.PageLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import java.io.ByteArrayInputStream;
import java.io.IOException;
/**
 <h1>MessageItemController </h1>
 <p>this class se</p>
 @author Ali Varaste Pour
 @version 1.0
 @since 6/11/2021
 @since 21/3/1400
 */
public class MessageItemController {
    Model.message message ;
    public AnchorPane root ;
    public Circle profilePhoto ;
    public Label messageLabel;
    public ImageView edit ;
    public ImageView delete;

    /**
     * class constructor; it sets a specified message to be visualized
     * @param message a specific message
     * @throws IOException loading other pages may threw IOException
     */
    public MessageItemController(Model.message message) throws IOException {
        new PageLoader().load("MessageItem", this);
        this.message = message;
    }

    /**
     * initializes and sets value for each messageItem
     * @return current root
     */
    public AnchorPane init(){
        profilePhoto.setFill(new ImagePattern(new Image(new ByteArrayInputStream(message.getSender().getProfilePhoto()))));
        messageLabel.setText(message.getMessage());
        edit.setVisible(message.getSender().getUserName().equals(logInPage_Controller.Username));
        delete.setVisible(message.getSender().getUserName().equals(logInPage_Controller.Username));
        return root ;
    }

}
