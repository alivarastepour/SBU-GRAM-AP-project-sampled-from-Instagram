package Controller;

import Model.PageLoader;
import Model.post;
import Model.user;
import com.jfoenix.controls.JFXButton;
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
    public ImageView comment ;
    public Rectangle postPhoto ;
    public post post;
    public static user PublisherUser ;
    public static user AuthorUser ;
    public ImageView like ;
    public ImageView liked ;
    public JFXButton addLike ;
    public JFXButton removeLike ;
    public Label numOfLikes ;

    //each list item will have its exclusive controller in runtime so we set the controller as we load the fxml
    public PostItemController(post post) throws IOException {
        new PageLoader().load("postItem", this);
        this.post = post;
    }

    //this anchor pane is returned to be set as the list view item
    public AnchorPane init() throws IOException {
        boolean condition = Model.likeServer.searchLikes(post , TimeLinePage_Controller.LoggedInUsername);
        like.setVisible(!condition);
        liked.setVisible(condition);
        addLike.setVisible(!condition);
        removeLike.setVisible(condition);
        numOfLikes.setText(String.valueOf(post.getLikes()));
        username.setText(post.getAuthorUser().getUserName());
        PostTitle.setText(post.getTitle());
        PostCaption.setText(post.getCaption());
        Image image = new Image(new ByteArrayInputStream(post.getPublisherUser().getProfilePhoto()));
        ProfilePhoto.setFill(new ImagePattern(image));
        Image image1 = new Image(new ByteArrayInputStream(post.getPhoto()));
        postPhoto.setFill(new ImagePattern(image1));
        return root;
    }
    //its important to know if the target profile belongs to the logged in user or others
    public void findUsername(ActionEvent actionEvent) throws IOException {
        PublisherUser = post.getPublisherUser();
        AuthorUser = post.getAuthorUser();
        System.out.println(AuthorUser.getUserName());
        if (TimeLinePage_Controller.LoggedInUsername.equals(PublisherUser.getUserName()))
            new PageLoader().load("selfProfilePage");
        else
            new PageLoader().load("othersProfilePage");
    }
    //adds a like to current post(this.post)
    public void addLike(ActionEvent actionEvent) throws IOException {
        Model.likeServer.addLike(post , TimeLinePage_Controller.LoggedInUsername);
        like.setVisible(false);
        liked.setVisible(true);
        addLike.setVisible(false);
        removeLike.setVisible(true);
    }
    //removes a like to current post(this.post)
    public void removeLike(ActionEvent actionEvent) throws IOException {
        Model.likeServer.removeLike(post , TimeLinePage_Controller.LoggedInUsername);
        like.setVisible(true);
        liked.setVisible(false);
        addLike.setVisible(true);
        removeLike.setVisible(false);
    }
}
