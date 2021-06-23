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
     */
    public static List<message> messageAssitant(Map<user, List<message>> receivedMessages , Map<user, List<message>> sentMessages){
        List<message> messageList = new ArrayList<>();
        List<message> m = new ArrayList<>();
        for (Map.Entry<user , List<message>> value: receivedMessages.entrySet()) {
            m.addAll(value.getValue());
            m = m.stream().sorted((a , b) -> (int) (b.getTime() - a.getTime())).collect(Collectors.toList());
            if (m.size() != 0)
                messageList.add(m.get(0));
            m.clear();
        }
        for (Map.Entry<user , List<message>> value: sentMessages.entrySet()) {
            m.addAll(value.getValue());
            m = m.stream().sorted((a , b) -> Math.toIntExact((b.getTime() - a.getTime()))).collect(Collectors.toList());
            if (m.size() != 0)
                messageList.add(m.get(0));
            m.clear();
        }
        List<message> removeList = new ArrayList<>();
        for (message m1: messageList)
            for (message m2:messageList)
                if (m2.getReceiver().getUserName().equals(m1.getSender().getUserName() )
                        &&
                        m1.getReceiver().getUserName().equals(m2.getSender().getUserName())){
                    if (m2.getTime() >= m1.getTime())
                        removeList.add(m1);
                    else
                        removeList.add(m2);
                }
        messageList.removeAll(removeList);
        messageList = messageList.stream().sorted((a,b) -> Math.toIntExact(b.getTime() - a.getTime())).collect(Collectors.toList());
        return messageList ;
    }
    public void initialize() throws IOException, ClassNotFoundException {
        receivedMessages = Model.allDirectsServer.getMessages(logInPage_Controller.Username , "receivedMessages");
        sentMessages = Model.allDirectsServer.getMessages(logInPage_Controller.Username , "sentMessages");
        List<message> messageList  = messageAssitant(receivedMessages , sentMessages);
        guide.setVisible(messageList.size() == 0);
        guide1.setVisible(messageList.size() == 0);
        allDirects.setItems(FXCollections.observableArrayList(messageList));
        allDirects.setCellFactory(allDirects -> new DirectItem());
    }
    
    public void backToTimeLinePage(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("TimeLinePage");
    }
}
