package Model;

import java.io.*;
import java.net.Socket;
import java.util.Map;

/**
 * <h1>changeProfileDetailsServer</h1>
 <p>this class starts a new client Socket and is a connector between changeProfileDetailsController and MainServer</p>
 @author Ali Varaste Pour
 @version 1.0
 @since 5/29/2021
 @since 8/3/1400
 */
public class changeProfileDetailsServer {
    public static boolean validPassword = false ;
    public static boolean validEmail = false ;
    public static boolean validPhoneNumber = false ;
    public static boolean validCity = false ;
    public static boolean validName = false ;

    /**
     *
     * @param map contains all changeable profile details and their new values(if not null)
     * @throws IOException using socket programming may cause IOException
     */
    public static void changeProfileDetailsHandler(Map<String , String> map) throws IOException {
        Socket changeProfileDetailsSocket = new Socket("127.0.0.1" , 9088);
        ObjectOutputStream changeProfileDetailsObjectOutputStream = new ObjectOutputStream(changeProfileDetailsSocket.getOutputStream());
        ObjectInputStream changeProfileDetailsObjectInputStream = new ObjectInputStream(changeProfileDetailsSocket.getInputStream());
        changeProfileDetailsObjectOutputStream.writeObject(map);
        changeProfileDetailsObjectOutputStream.flush();
        changeProfileDetailsServer.validPassword = changeProfileDetailsObjectInputStream.readBoolean();
        changeProfileDetailsServer.validEmail = changeProfileDetailsObjectInputStream.readBoolean();
        changeProfileDetailsServer.validPhoneNumber = changeProfileDetailsObjectInputStream.readBoolean();
        changeProfileDetailsServer.validCity = changeProfileDetailsObjectInputStream.readBoolean();
        changeProfileDetailsServer.validName = changeProfileDetailsObjectInputStream.readBoolean();
        changeProfileDetailsSocket.close();
        changeProfileDetailsObjectOutputStream.close();
        changeProfileDetailsObjectInputStream.close();
    }
}
