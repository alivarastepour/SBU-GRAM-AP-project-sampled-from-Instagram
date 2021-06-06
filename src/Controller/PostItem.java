package Controller;

import Model.post;
import javafx.scene.control.ListCell;

import java.io.IOException;

/**
 * if i would have known what does this class do , i would have written it myself so ask description from author
 * @author niloufar MJ
 * @since 6/7/2021
 */
public class PostItem extends ListCell<post> {

    @Override
    public void updateItem(post post, boolean empty) {
        super.updateItem(post, empty);
        if (post != null) {
            try {
                setGraphic(new PostItemController(post).init());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
