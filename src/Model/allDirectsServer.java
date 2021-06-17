package Model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
/**
 * <h1>allDirectsServer</h1>
 <p>this class starts a new client Socket and returns a map containing messages</p>
 @author Ali Varaste Pour
 @version 1.0
 @since 6/12/2021
 @since 22/3/1400
 */
public class allDirectsServer {
    /**
     * starts a socket, sends data to server and then receives proper input
     * @param username a username whom messages are asked for
     * @param condition to set whether the messages are sent or received
     * @return a map containing users and list of their messages
     * @throws IOException using socket programming may cause IOException
     * @throws ClassNotFoundException casting from object to a specified type may threw CNFException
     */
    public static Map<user, List<message>> getMessages(String username , String condition) throws IOException, ClassNotFoundException {
        Socket allDirectsSocket = new Socket("127.0.0.1" , 9069);
        ObjectOutputStream allDirectsObjectOutputStream = new ObjectOutputStream(allDirectsSocket.getOutputStream());
        ObjectInputStream allDirectsObjectInputStream = new ObjectInputStream(allDirectsSocket.getInputStream());
        allDirectsObjectOutputStream.writeUTF(username);
        allDirectsObjectOutputStream.flush();
        allDirectsObjectOutputStream.writeUTF(condition);
        allDirectsObjectOutputStream.flush();
        Map<user, List<message>> messages = (Map<user, List<message>>) allDirectsObjectInputStream.readObject();
        allDirectsSocket.close();
        allDirectsObjectOutputStream.close();
        allDirectsObjectInputStream.close();
        return messages ;
    }
    
    /**
     * this method changes message state from unread to read
     * @param user1 first chatting user
     * @param user2 second chatting user
     * @throws IOException using socket programming may cause IOException
     */
    public static void read(String user1 , String user2) throws IOException {
        Socket readSocket = new Socket("127.0.0.1" , 9067);
        ObjectOutputStream readObjectOutputStream = new ObjectOutputStream(readSocket.getOutputStream());
        ObjectInputStream readObjectInputStream = new ObjectInputStream(readSocket.getInputStream());
        readObjectOutputStream.writeUTF(user1);
        readObjectOutputStream.flush();
        readObjectOutputStream.writeUTF(user2);
        readObjectOutputStream.flush();
        readSocket.close();
        readObjectOutputStream.close();
        readObjectInputStream.close();
    }
    
}
