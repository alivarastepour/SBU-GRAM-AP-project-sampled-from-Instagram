package Model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
/**
 * <h1>likeServer</h1>
 <p>this class starts a new client Socket and is a connector between likePage_Controller and MainServer</p>
 @author Ali Varaste Pour
 @version 1.0
 @since 6/4/2021
 @since 13/3/1400
 */
public class likeServer {
    /**
     * this method searches if a
     * @param post specified post
     * @param username is liked by specified username
     * @return and returns true if so.
     * @throws IOException using socket programming may cause IOException
     */
    public static boolean searchLikes(post post , String username) throws IOException {
        Socket searchLikesSocket = new Socket("127.0.0.1" , 9078);
        ObjectOutputStream searchLikesObjectOutputStream = new ObjectOutputStream(searchLikesSocket.getOutputStream());
        ObjectInputStream searchLikesObjectInputStream = new ObjectInputStream(searchLikesSocket.getInputStream());
        searchLikesObjectOutputStream.writeObject(post);
        searchLikesObjectOutputStream.flush();
        searchLikesObjectOutputStream.writeUTF(username);
        searchLikesObjectOutputStream.flush();
        boolean condition = searchLikesObjectInputStream.readBoolean();
        searchLikesSocket.close();
        searchLikesObjectOutputStream.close();
        searchLikesObjectInputStream.close();
        return condition;
    }
    
    /**
     * this method adds a like to a
     * @param post specified post
     * @param username by specified username.
     * @throws IOException using socket programming may cause IOException
     */
    public static void addLike(post post , String username) throws IOException {
        Socket addLikeSocket = new Socket("127.0.0.1" , 9077);
        ObjectOutputStream addLikeObjectOutputStream = new ObjectOutputStream(addLikeSocket.getOutputStream());
        ObjectInputStream addLikeObjectInputStream = new ObjectInputStream(addLikeSocket.getInputStream());
        addLikeObjectOutputStream.writeObject(post);
        addLikeObjectOutputStream.flush();
        addLikeObjectOutputStream.writeUTF(username);
        addLikeObjectOutputStream.flush();
        addLikeSocket.close();
        addLikeObjectOutputStream.close();
        addLikeObjectInputStream.close();
    }
    /**
     * this method removes a like from a
     * @param post specified post
     * @param username by specified username.
     * @throws IOException using socket programming may cause IOException
     */
    public static void removeLike(post post , String username) throws IOException {
        Socket removeLikeSocket = new Socket("127.0.0.1" , 9076);
        ObjectOutputStream removeLikeObjectOutputStream = new ObjectOutputStream(removeLikeSocket.getOutputStream());
        ObjectInputStream removeLikeObjectInputStream = new ObjectInputStream(removeLikeSocket.getInputStream());
        removeLikeObjectOutputStream.writeObject(post);
        removeLikeObjectOutputStream.flush();
        removeLikeObjectOutputStream.writeUTF(username);
        removeLikeObjectOutputStream.flush();
        removeLikeSocket.close();
        removeLikeObjectOutputStream.close();
        removeLikeObjectInputStream.close();
    }
}
