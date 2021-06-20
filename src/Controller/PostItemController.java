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
import java.util.List;

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
    public Label time ;
    public Label numOfRePost ;
    public Label repostLabel ;
    public Label repostname ;
    public Label numOfComments ;
    public JFXButton repostButton ;
    public static post currentPost ;
    public static String user;

    //each list item will have its exclusive controller in runtime so we set the controller as we load the fxml
    public PostItemController(post post) throws IOException {
        new PageLoader().load("postItem", this);
        this.post = post;
    }
    public static void trick(user user){
        PublisherUser = user ;
    }
    //this anchor pane is returned to be set as the list view item
    public AnchorPane init() throws IOException, ClassNotFoundException {
        boolean condition = Model.likeServer.searchLikes(post , TimeLinePage_Controller.LoggedInUsername);
        List<Model.comment> list = Model.commentServer.commentsHandler(post);
        like.setVisible(!condition);
        liked.setVisible(condition);
        addLike.setVisible(!condition);
        removeLike.setVisible(condition);
        time.setText(post.getFormattedTime());
        numOfComments.setText(String.valueOf(list.size()));
        numOfLikes.setText(String.valueOf(post.getLikes()));
        username.setText(post.getPublisherUser().getUserName());
        user = post.getPublisherUser().getUserName();
        PostTitle.setText(post.getTitle());
        PostCaption.setText(post.getCaption());
        Image image = new Image(new ByteArrayInputStream(post.getPublisherUser().getProfilePhoto()));
        ProfilePhoto.setFill(new ImagePattern(image));
        Image image1 = new Image(new ByteArrayInputStream(post.getPhoto()));
        postPhoto.setFill(new ImagePattern(image1));
        repostLabel.setVisible(!post.getPublisherUser().getUserName().equals(post.getAuthorUser().getUserName()));
        if (repostLabel.isVisible())
            repostname.setText(post.getAuthorUser().getUserName());
        repostButton.setVisible(!TimeLinePage_Controller.LoggedInUsername.equals(post.getPublisherUser().getUserName())&&
                !TimeLinePage_Controller.LoggedInUsername.equals(post.getAuthorUser().getUserName()));
        numOfRePost.setText(String.valueOf(post.getReposts()));
        return root;
    }
    //its important to know if the target profile belongs to the logged in user or others
    public void findUsername(ActionEvent actionEvent) throws IOException {
        PublisherUser = post.getPublisherUser();
        AuthorUser = post.getAuthorUser();
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
    //reposts a post
    public void repost(ActionEvent actionEvent) throws IOException {
        Model.rePostServer.rePostHandler(TimeLinePage_Controller.LoggedInUsername , post);
    }
    //adds a comment
    public void comment(ActionEvent actionEvent) throws IOException {
        currentPost = post ;
        new PageLoader().load("commentPage");
    }
}
