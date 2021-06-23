package Controller;

import Model.PageLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * <h1>photoMessage_Controller</h1>
 * <p>this class handles image messages</p>
 */
public class photoMessage_Controller {
    public ImageView photo;
    public Label username;
    public void initialize(){
        photo.setImage(new Image(new ByteArrayInputStream(MessageItemController.tempMessage.getPhoto())));
        username.setText(MessageItemController.tempMessage.getSender().getUserName());
    }

    public void backToDirectPage(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("directPage");
    }
}
