package Model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
/**
 * <h1>rePostServer</h1>
 <p>this class starts a new client Socket and is a connector between postItemController and MainServer</p>
 @author Ali Varaste Pour
 @version 1.0
 @since 6/6/2021
 @since 16/3/1400
 */
public class rePostServer {
    /**
     * receives a post and a username and adds post to rhe given user's post list
     * @param username username
     * @param post post
     * @throws IOException using socket programming may cause IOException
     */
    public static void rePostHandler(String username , post post) throws IOException {
        Socket socket = new Socket("127.0.0.1", 9074);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        objectOutputStream.writeObject(post);
        objectOutputStream.flush();
        objectOutputStream.writeUTF(username);
        objectOutputStream.flush();
        socket.close();
        objectOutputStream.close();
        objectInputStream.close();
    }
}
