package Model;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * <h1>Main</h1>
 <p>this class runs the client side</p>
 @author Ali Varaste Pour
 @version 1.0
 @since 5/27/2021
 @since 6/3/1400
 */
public class Main extends Application {
    /**
     *
     * @param primaryStage is the main stage(login page)
     * @throws Exception since client side starts  from here , every kind of exception may be threw
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        PageLoader.initStage(primaryStage);
            new PageLoader().load("loginPage");
    }
    
    /**
     *
     * @param args java default parameter for p-s-v-main
     */
    public static void main(String[] args) { launch(args); }
}
