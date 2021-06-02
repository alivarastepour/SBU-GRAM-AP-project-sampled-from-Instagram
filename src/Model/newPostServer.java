package Model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
/**
 * <h1>newPostServer</h1>
 <p>this class starts a new client Socket and is a connector between newPostController and MainServer</p>
 @author Ali Varaste Pour
 @version 1.0
 @since 5/31/2021
 @since 10/3/1400
 */
public class newPostServer {
    /**
     * creates a new post using
     * @param title title
     * @param Caption caption
     * @param PhotoAddress PhotoAddress
     * @param Author and Author
     * @throws IOException using socket programming may cause IOException
     */
    public static void newPostHandler(String title , String Caption , String PhotoAddress , user Author) throws IOException {
        post post = new post(title , Caption , PhotoAddress , Author , Author) ;
        Socket newPostSocket = new Socket("127.0.0.1" , 9087);
        ObjectOutputStream newPostObjectOutputStream = new ObjectOutputStream(newPostSocket.getOutputStream());
        ObjectInputStream newPostObjectInputStream = new ObjectInputStream(newPostSocket.getInputStream());
        newPostObjectOutputStream.writeObject(post);
        newPostObjectOutputStream.flush();
        newPostSocket.close();
        newPostObjectOutputStream.close();
        newPostObjectInputStream.close();
    }
}
