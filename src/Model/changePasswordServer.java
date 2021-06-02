package Model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
/**
 * <h1>changePasswordServer</h1>
 <p>this class starts a new client Socket and is a connector between changePasswordController and MainServer</p>
 @author Ali Varaste Pour
 @version 1.0
 @since 5/27/2021
 @since 6/3/1400
 */
public class changePasswordServer {
    private static boolean validPassword  = false;

    public static boolean isValidPassword() {
        return validPassword;
    }

    /**
     *
     * @param username is used to identify which user is trying to change password
     * @param Password is the new password for the mentioned username
     * @throws IOException using socket programming may cause IOException
     */
    public static void changePasswordHandler(String username , String Password) throws IOException {
        Socket changePasswordSocket = new Socket("127.0.0.1" , 9091);
        DataInputStream changePasswordInputStream = new DataInputStream(changePasswordSocket.getInputStream());
        DataOutputStream changePasswordOutputStream = new DataOutputStream(changePasswordSocket.getOutputStream());
        changePasswordOutputStream.writeUTF(username);
        changePasswordOutputStream.flush();
        changePasswordOutputStream.writeUTF(Password);
        changePasswordOutputStream.flush();
        validPassword = changePasswordInputStream.readBoolean();
        changePasswordSocket.close();
        changePasswordInputStream.close();
        changePasswordOutputStream.close();

    }
}
