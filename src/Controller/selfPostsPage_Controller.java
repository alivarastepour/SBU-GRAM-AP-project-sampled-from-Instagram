package Controller;

import Model.PageLoader;
import Model.post;
import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 <h1>selfPostsPage_Controller </h1>
 <p>this controller page is a connector between selfPostsPage FXML(ths stage) and selfPostServer class </p>
 @author Ali Varaste Pour
 @version 1.0
 @since 6/7/2021
 @since 17/3/1400
 */
public class selfPostsPage_Controller {
    static boolean trick = false ;
    public Label greet ;
    public Label greet1 ;
    public ListView<post> SelfTimeLine;
    private List<post> selfPosts = new ArrayList<>();
    public void initialize() throws IOException, ClassNotFoundException {
        if (!trick)
            selfPosts = Model.selfPostServer.selfPostHandler(logInPage_Controller.Username);
        else
            selfPosts = Model.selfPostServer.selfPostHandler(PostItemController.PublisherUser.getUserName());
        greet.setVisible(selfPosts.size() == 0);
        greet1.setVisible(selfPosts.size() == 0);
        SelfTimeLine.setItems(FXCollections.observableArrayList(selfPosts));
        SelfTimeLine.setCellFactory(TimeLine -> new PostItem());
        trick = false ;
    }


    public void backToProfilePage(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("TimeLinePage");
    }
    public static void trick(){
        trick = true ;
    }
}
