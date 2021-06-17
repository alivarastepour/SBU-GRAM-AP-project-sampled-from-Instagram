package Controller;

import Model.*;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 <h1>TimeLinePage_Controller </h1>
 <p>this controller page is a connector between TimeLinePage FXML(ths stage) and TimeLinePageServer class </p>
 @author Ali Varaste Pour
 @version 1.0
 @since 5/25/2021
 @since 4/3/1400
 */
public class TimeLinePage_Controller {
    public static String LoggedInUsername ;
    public ListView<post> TimeLine;
    public Label greet;
    public Label greet1;
    private List<post> posts = new ArrayList<>();
    public Label unreadMessages ;
    public void initialize() throws IOException, ClassNotFoundException {
        LoggedInUsername = logInPage_Controller.Username;
        posts = TimeLineServer.TimeLineHandler(LoggedInUsername);
        TimeLine.setItems(FXCollections.observableArrayList(posts));
        TimeLine.setCellFactory(TimeLine -> new PostItem());
        greet.setVisible(posts.size() == 0);
        greet1.setVisible(posts.size() == 0);
        Map<user, List<message>> receivedMessages = Model.allDirectsServer.getMessages(logInPage_Controller.Username , "receivedMessages");
        Map<user, List<message>> sentMessages = Model.allDirectsServer.getMessages(logInPage_Controller.Username , "sentMessages");
        List<message> messageList  = allDirectsPage_Controller.messageAssitant(receivedMessages , sentMessages);
        int unreadCount = (int) messageList.stream().filter(a -> !a.getSender().getUserName().equals(logInPage_Controller.Username)).filter(a -> !a.isRead()).count();
        if (unreadCount == 0)
            unreadMessages.setVisible(false);
        else
            unreadMessages.setText(String.valueOf(unreadCount));
    }
    
    public void Profile(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("selfProfilePage");
    }

    public void SignOut(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("loginPage");
    }
    
    public void newPost(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("newPostPage");
    }
    
    public void refresh(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("TimeLinePage");
    }

    public void search(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("searchPage");
    }

    public void directs(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("allDirectsPage");
    }
}
