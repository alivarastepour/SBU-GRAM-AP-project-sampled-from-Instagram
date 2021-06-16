package Controller;

import Model.PageLoader;
import Model.user;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
/**
 <h1>searchPage_Controller </h1>
 <p>this controller page is a connector between searchPage FXML(ths stage) and searchServer class </p>
 @author Ali Varaste Pour
 @version 1.0
 @since 6/3/2021
 @since 12/3/1400
 */
public class searchPage_Controller {
    public Label validUsername;
    public JFXTextField searchBox;
    static String username ;
    public static user User ;


    //searches for a user using given username
    public void search(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        String username = searchBox.getText();
        user user = Model.searchServer.searchHandler(username);
        if (user == null){
            validUsername.setVisible(true);
        }
        else{
            User = user ;
            PostItemController.trick(User);
            DirectItemController.user = User ;
            new PageLoader().load("othersProfilePage");
            searchPage_Controller.username = username ;
            validUsername.setVisible(false);

        }
    }
    
    public void returnToTimeLine(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("TimeLinePage");
    }

}
