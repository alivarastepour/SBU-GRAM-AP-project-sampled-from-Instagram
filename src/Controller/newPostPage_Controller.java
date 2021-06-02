package Controller;

import Model.PageLoader;
import Model.newPostServer;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Popup;

import java.io.*;
/**
 <h1>newPostPage_Controller </h1>
 <p>this controller page is a connector between newPost FXML(ths stage) and newPostPageServer class </p>
 @author Ali Varaste Pour
 @version 1.0
 @since 5/31/2021
 @since 10/3/1400
 */
public class newPostPage_Controller {
    public JFXTextArea caption;
    public ImageView photo;
    public JFXTextField title;
    public Label photoNotUploaded;
    public Label captionNotEntered;
    public Label titleNotEntered;
    String photoAddress ;
    
    /**
     *this method saves a photo for your post
     * @param actionEvent
     * @throws IOException opening fileChooser and not selecting an item or selecting a file with wrong format will cause IOException
     */
    public void uploadPhoto(ActionEvent actionEvent) throws IOException {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Popup());
        photoAddress = file.toString();
        FileInputStream fileInputStream = new FileInputStream(file) ;
        byte[] b = fileInputStream.readAllBytes();
        Image image = new Image(new ByteArrayInputStream(b));
        photo.setImage(image);
    }
    
    /**
     * this method checks if all given data for new post are valid and if so, sends them to server
     * @param actionEvent
     * @throws IOException line 64 will produce IOException if url not found
     */
    public void UploadPost(ActionEvent actionEvent) throws IOException {
        String title = this.title.getText() ;
        String caption = this.caption.getText();
        boolean validTitle = !this.title.getText().isBlank();
        boolean validCaption = !this.caption.getText().isBlank();
        boolean validPhoto = this.photoAddress != null ;
        titleNotEntered.setVisible(!validTitle);
        captionNotEntered.setVisible(!validCaption);
        photoNotUploaded.setVisible(!validPhoto);
        if (validTitle && validCaption && validPhoto){
            newPostServer.newPostHandler(title , caption , photoAddress , changeProfileDetailsPage_Controller.user);
            new PageLoader().load("TimeLinePage");
        }
    }
    
    public void returnToTimeLine(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("TimeLinePage");
    }
}
