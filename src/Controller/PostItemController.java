package Controller;

import Model.PageLoader;
import Model.post;
import Model.user;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class PostItemController {
    public AnchorPane root;
    public Circle ProfilePhoto;
    public Label username;
    public Label PostTitle;
    public Label PostCaption;
    public ImageView like ;
    public ImageView comment ;
    public Rectangle postPhoto ;
    public post post;
    public static user PublisherUser ;
    public static user AuthorUser ;

    //each list item will have its exclusive controller in runtime so we set the controller as we load the fxml
    public PostItemController(post post) throws IOException {
        new PageLoader().load("postItem", this);
        this.post = post;
    }

    //this anchor pane is returned to be set as the list view item
    public AnchorPane init() {
        username.setText(post.getAuthorUser().getUserName());
        PostTitle.setText(post.getTitle());
        PostCaption.setText(post.getCaption());
        Image image = new Image(new ByteArrayInputStream(post.getPublisherUser().getProfilePhoto()));
        ProfilePhoto.setFill(new ImagePattern(image));
        Image image1 = new Image(new ByteArrayInputStream(post.getPhoto()));
        postPhoto.setFill(new ImagePattern(image1));
        return root;
    }

    //you can show post's detail in new page with this method
    public void detail(ActionEvent actionEvent) {

    }
    public void findUsername(ActionEvent actionEvent) throws IOException {
        PublisherUser = post.getPublisherUser();
        AuthorUser = post.getAuthorUser();
        System.out.println(AuthorUser.getUserName());
        if (TimeLinePage_Controller.LoggedInUsername.equals(PublisherUser.getUserName()))
            new PageLoader().load("selfProfilePage");
        else
            new PageLoader().load("othersProfilePage");
    }
}
