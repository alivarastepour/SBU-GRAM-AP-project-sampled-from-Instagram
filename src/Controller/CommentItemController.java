package Controller;

import Model.PageLoader;
import Model.comment;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import java.io.IOException;

public class CommentItemController {
    Model.comment comment ;
    public AnchorPane root;
    public Circle profilePhoto ;
    public Label username ;
    public Label commentText ;
    public CommentItemController(comment comment) throws IOException {
        new PageLoader().load("CommentItem", this);
        this.comment = comment;
    }
    
    public AnchorPane init() throws IOException {
        username.setText(comment.getAuthor());
        commentText.setText(comment.getComment());
        return root;
    }
    
}
