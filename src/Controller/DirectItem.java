package Controller;

import Model.message;
import javafx.scene.control.ListCell;
import java.io.IOException;

/**
 * if i would have known what does this class do , i would have written it myself so ask description from author
 * @author niloufar MJ
 * @since 6/7/2021
 */
public class DirectItem extends ListCell<message> {

    @Override
    public void updateItem(message message, boolean empty) {
        super.updateItem(message, empty);
        if (message != null) {
            try {
                setGraphic(new DirectItemController(message).init());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
