package Model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * this class loads a specific stage details and avoid repeating them
 */
public class PageLoader {
    static Stage stage ;
    static Scene scene ;
    public static void initStage(Stage primaryStage){
        stage = primaryStage ;
        stage.setTitle("SBU Gram");
        stage.setWidth(900);
        stage.setHeight(550);
        stage.getIcons().add(new Image(String.valueOf(Paths.get("View/Icons/icon.png"))));
    }
    
    public void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }
    
    public static Stage getPrimaryStage() {
        return stage;
    }
    
    public Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/View/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }
    
    public void load(String url) throws IOException {
        scene = new Scene(new PageLoader().loadFXML(url));
        stage.setScene(scene);
        stage.show();
    }
    
    public void load(String fxml, Object controller) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/View/" + fxml + ".fxml"));
        fxmlLoader.setController(controller);
        fxmlLoader.load();
    }
}
