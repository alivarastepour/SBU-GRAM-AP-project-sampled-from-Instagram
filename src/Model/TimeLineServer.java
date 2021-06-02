package Model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
/**
 * <h1>TimeLineServer</h1>
 <p>this class starts a new client Socket and is a connector between TimeLineController and MainServer</p>
 @author Ali Varaste Pour
 @version 1.0
 @since 5/30/2021
 @since 9/3/1400
 */
public class TimeLineServer {
    public static List<post> TimeLineHandler() throws IOException, ClassNotFoundException {
        Socket TimeLineSocket = new Socket("127.0.0.1" , 9086);
        ObjectOutputStream TimeLineObjectOutputStream = new ObjectOutputStream(TimeLineSocket.getOutputStream());
        ObjectInputStream TimeLineObjectInputStream = new ObjectInputStream(TimeLineSocket.getInputStream());
        List<post> posts = (List<post>) TimeLineObjectInputStream.readObject();
        TimeLineSocket.close();
        TimeLineObjectOutputStream.close();
        TimeLineObjectInputStream.close();
        return posts ;
    }
    

}

