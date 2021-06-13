package Controller;

import Model.PageLoader;
import Model.message;
import Model.user;
import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
/**
 <h1>allDirectsPage_Controller </h1>
 <p>this controller page sets value for some fields related to all messages in s.one's direct
 and then sets data design in stage using other classes</p>
 @author Ali Varaste Pour
 @version 1.0
 @since 6/11/2021
 @since 21/3/1400
 */
public class allDirectsPage_Controller {
    public ListView<message> allDirects ;
    public Label guide ;
    public Label guide1 ;
    private Map<user, List<message>> receivedMessages = new HashMap<>();
    private Map<user, List<message>> sentMessages = new HashMap<>();

    /**
     * sets received and sent messages , sorts them and then visualizes it
     * @throws IOException since "getMessage" method throws CNFException , this method should also handle it
     * @throws ClassNotFoundException since "getMessage" method throws CNFException , this method should also handle it
     */
    public void initialize() throws IOException, ClassNotFoundException {
        receivedMessages = Model.allDirectsServer.getMessages(logInPage_Controller.Username , "receivedMessages");
        sentMessages = Model.allDirectsServer.getMessages(logInPage_Controller.Username , "sentMessages");
        List<message> messageList = new ArrayList<>();

        for (Map.Entry<user , List<message>> value: receivedMessages.entrySet()) {
            List<message> m = value.getValue();
            m = m.stream().sorted((a , b) -> (int) (b.getTime() - a.getTime())).collect(Collectors.toList());
            messageList.add(m.get(0));
        }

        for (Map.Entry<user , List<message>> value: sentMessages.entrySet()) {
            List<message> m = value.getValue();
            m = m.stream().sorted((a , b) -> (int) (b.getTime() - a.getTime())).collect(Collectors.toList());
            messageList.add(m.get(0));
        }

        for (int i = 0; i < messageList.size(); i++)
            for (int j = i; j < messageList.size(); j++)
                if (messageList.get(i).getReceiver().getUserName().equals(messageList.get(j).getSender().getUserName()) && messageList.get(j).getReceiver().getUserName().equals(messageList.get(i).getSender().getUserName()))
                    if (messageList.get(i).getTime() > messageList.get(j).getTime())
                        messageList.remove(messageList.get(j));
                    else
                        messageList.remove(messageList.get(i));

        guide.setVisible(messageList.size() == 0);
        guide1.setVisible(messageList.size() == 0);
        allDirects.setItems(FXCollections.observableArrayList(messageList));
        allDirects.setCellFactory(allDirects -> new DirectItem());

    }

    public void backToTimeLinePage(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("TimeLinePage");
    }
}
