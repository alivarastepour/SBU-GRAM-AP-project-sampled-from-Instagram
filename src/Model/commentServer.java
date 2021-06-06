package Model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
/**
 * <h1>commentServer</h1>
 <p>this class starts a new client Socket and is a connector between commentsPageController and MainServer</p>
 @author Ali Varaste Pour
 @version 1.0
 @since 6/7/2021
 @since 17/3/1400
 */
public class commentServer {
    /**
     * this method connects to server using a port and searches for a list of comments
     * @param post we're looking for this post's comments
     * @return a list of comments
     * @throws IOException using socket programming may cause IOException
     * @throws ClassNotFoundException casting list of objects to list of comments may threw CNFException
     */
    public static List<comment> commentsHandler(post post) throws IOException, ClassNotFoundException {
        Socket commentSocket = new Socket("127.0.0.1" , 9073);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(commentSocket.getOutputStream());
        ObjectInputStream objectInputStream = new ObjectInputStream(commentSocket.getInputStream());
        objectOutputStream.writeObject(post);
        objectOutputStream.flush();
        List<comment> comments = (List<comment>) objectInputStream.readObject();
        commentSocket.close();
        objectOutputStream.close();
        objectInputStream.close();
        return comments ;
    }
    
    /**
     * adds a comment to specific post using a port
     * @param comment comment
     * @param post post
     * @throws IOException using socket programming may cause IOException
     */
    public static void addComment(comment comment , post post) throws IOException {
        Socket socket = new Socket("127.0.0.1", 9072);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        objectOutputStream.writeObject(comment);
        objectOutputStream.flush();
        objectOutputStream.writeObject(post);
        objectOutputStream.flush();
    }
}
