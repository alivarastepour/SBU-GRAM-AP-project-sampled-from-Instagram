package Model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
/**
 * <h1>directsServer</h1>
 <p>this class starts a new client Socket and adds a pv message to repository</p>
 @author Ali Varaste Pour
 @version 1.0
 @since 6/11/2021
 @since 21/3/1400
 */
public class directsServer {
    /**
     * receives all necessary fields for creating an object from message class
     * @param message message itself
     * @param username sender of message
     * @param directUser receiver of message
     * @throws IOException using socket programming may cause IOException
     */
    public static void sendMessageHandler(String message , String username , String directUser) throws IOException {
        Socket directsSocket = new Socket("127.0.0.1" , 9070);
        ObjectOutputStream directsObjectOutputStream = new ObjectOutputStream(directsSocket.getOutputStream());
        ObjectInputStream directsObjectInputStream = new ObjectInputStream(directsSocket.getInputStream());
        directsObjectOutputStream.writeUTF(message);
        directsObjectOutputStream.flush();
        directsObjectOutputStream.writeUTF(username);
        directsObjectOutputStream.flush();
        directsObjectOutputStream.writeUTF(directUser);
        directsObjectOutputStream.flush();
        directsSocket.close();
        directsObjectOutputStream.close();
        directsObjectInputStream.close();
    }
}
