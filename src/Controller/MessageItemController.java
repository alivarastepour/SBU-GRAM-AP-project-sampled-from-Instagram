package Controller;

import Model.PageLoader;
import Model.message;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
/**
 <h1>MessageItemController </h1>
 <p>this class sets data for each message in directPage</p>
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
    public JFXButton deleteButton ;
    public JFXTextField editMessageField ;
    public ImageView applyEditImage;
    public JFXButton applyEditButton ;
    public JFXButton editButton ;
    public Label time ;
    public JFXButton photoMessage;
    public static message tempMessage ;
    public ImageView play ;
    public ImageView pause ;
    public JFXButton playButton ;
    public JFXButton pauseButton ;
    String url ;
    Media media ;
    MediaPlayer mediaPlayer ;
    public MediaView player;

    

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
        edit.setVisible(message.getSender().getUserName().equals(logInPage_Controller.Username) && message.getPhoto() == null && message.getVoiceAddress() == null);
        delete.setVisible(message.getSender().getUserName().equals(logInPage_Controller.Username));
        deleteButton.setVisible(message.getSender().getUserName().equals(logInPage_Controller.Username));
        time.setText(message.getDate());
        photoMessage.setVisible(message.getPhoto() != null);
        play.setVisible(message.getVoiceAddress() != null);
        playButton.setVisible(message.getVoiceAddress() != null);
        pause.setVisible(message.getVoiceAddress() != null);
        pauseButton.setVisible(message.getVoiceAddress() != null);
        if (message.getVoiceAddress() != null){
            tempMessage = message ;
            url = MessageItemController.tempMessage.getVoiceAddress();
            media = new Media(new File(url).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            player = new MediaView();
            player.setMediaPlayer(mediaPlayer);
        }
        return root ;
    }
    
    /**
     * sends a deleteMessage to server containing message sender/receiver and message itself
     * @param actionEvent on mouse click
     * @throws IOException page loading and calling "messageHandler" may threw IOException
     */
    public void delete(ActionEvent actionEvent) throws IOException {
        Model.directsServer.MessageHandler(message.getMessage() , logInPage_Controller.Username , DirectItemController.user.getUserName() , "deleteMessage");
        new PageLoader().load("directPage");
    }
    
    /**
     * prepares proper fields for editing message
     * @param actionEvent on mouse click
     */
    public void edit(ActionEvent actionEvent) {
        messageLabel.setVisible(false);
        editButton.setVisible(false);
        edit.setVisible(false);
        applyEditImage.setVisible(true);
        applyEditButton.setVisible(true);
        editMessageField.setVisible(true);
    }
    
    /**
     * receives necessary data for editing a message
     * @param actionEvent on mouse click
     * @throws IOException page loading and "messageHandler" Method may threw IOException
     */
    public void applyEdit(ActionEvent actionEvent) throws IOException {
        String editedMessage ;
        if (!editMessageField.getText().isBlank()){
            editedMessage =  editMessageField.getText();
            String mergedMessage = message.getMessage() + "A___.___A" + editedMessage ;
            Model.directsServer.MessageHandler(mergedMessage , logInPage_Controller.Username , DirectItemController.user.getUserName() , "editMessage");
            new PageLoader().load("directPage");
        }
        editButton.setVisible(true);
        edit.setVisible(true);
        messageLabel.setVisible(true);
        applyEditImage.setVisible(false);
        applyEditButton.setVisible(false);
        editMessageField.setVisible(false);
    }

    /**
     * opens an image message
     * @param actionEvent on mouse click
     * @throws IOException loading new page may threw IOException
     */
    public void photoMessage(ActionEvent actionEvent) throws IOException {
        tempMessage = message ;
        new PageLoader().load("photo");
    }

    /**
     * plays a voiceMessage
     * @param actionEvent on mouseClick
     */
    public void play(ActionEvent actionEvent) {
        mediaPlayer.play();
    }

    /**
     * pauses a voiceMessage
     * @param actionEvent on mouseClick
     */
    public void pause(ActionEvent actionEvent){
        mediaPlayer.pause();
    }
}
