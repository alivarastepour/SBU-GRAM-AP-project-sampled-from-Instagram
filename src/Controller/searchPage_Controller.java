package Controller;

import Model.PageLoader;
import Model.user;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;

import java.io.IOException;

public class searchPage_Controller {
    public Label validUsername;
    public JFXTextField searchBox;

    public void search(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        String username = searchBox.getText();
        user user = Model.searchServer.searchHandler(username);
        if (user == null)
            validUsername.setVisible(true);
        else
            validUsername.setVisible(false);
//            new PageLoader().load("othersProfilePage");
    }
}
