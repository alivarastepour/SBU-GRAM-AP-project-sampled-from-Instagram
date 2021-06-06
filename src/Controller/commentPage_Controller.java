package Controller;

import Model.PageLoader;
import Model.comment;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class commentPage_Controller {
    public JFXListView<Model.comment> commentsListView;
    public Label username;
    public JFXTextArea comment;
    public Label no_c;
    public Label no_c1;
    public List<Model.comment> commentList = new ArrayList<>();
    public void initialize() throws IOException, ClassNotFoundException {
        commentList = Model.commentServer.commentsHandler(PostItemController.currentPost);
        no_c.setVisible(commentList.size() == 0);
        no_c1.setVisible(commentList.size() == 0);
        username.setText(logInPage_Controller.Username);
        commentsListView.setItems(FXCollections.observableArrayList(commentList));
        commentsListView.setCellFactory(TimeLine -> new CommentItem());
    }
    public void addComment(ActionEvent actionEvent) throws IOException {
        String commentText = comment.getText();
        String user = username.getText();
        if (!comment.getText().isBlank())
            Model.commentServer.addComment(new comment(user , commentText) , PostItemController.currentPost);
        new PageLoader().load("TimeLinePage");
    
    }



}
