package Controller;

import Model.comment;
import Model.post;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class CommentItem extends ListCell<comment> {
    @Override
    public void updateItem(comment comment, boolean empty) {
        super.updateItem(comment, empty);
        if (comment != null) {
            try {
                setGraphic(new CommentItemController(comment).init());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
