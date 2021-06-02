package Model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
/**
 * <h1>passwordRecoveryPageServer</h1>
 <p>this class starts a new client Socket and is a connector between passwordRecoveryController and MainServer</p>
 @author Ali Varaste Pour
 @version 1.0
 @since 5/27/2021
 @since 6/3/1400
 */
public class passwordRecoveryPageServer {
    private static  boolean validInfo = false ;
    private static String username ;

    public static String getUsername() {
        return username;
    }
    public static boolean isValidInfo() {
        return validInfo;
    }

    /**
     * this method sends
     * @param username username
     * @param email email
     * @param phoneNumber phoneNumber
     * @param action and a action which represents the way that password is gonna be recovered
     * to mainServer
     * @throws IOException using socket programming may cause IOException
     */
    public static void passwordRecoveryHandler(String username , String email , String phoneNumber , boolean action) throws IOException {
        passwordRecoveryPageServer.username = username ;
        Socket passwordRecoverySocket = new Socket("127.0.0.1" , 9092) ;
        DataInputStream passwordRecoveryInputStream = new DataInputStream(passwordRecoverySocket.getInputStream());
        DataOutputStream passwordRecoveryOutputStream = new DataOutputStream(passwordRecoverySocket.getOutputStream());
        passwordRecoveryOutputStream.writeUTF(passwordRecoveryPageServer.username);
        passwordRecoveryOutputStream.flush();
        if (action){
            passwordRecoveryOutputStream.writeUTF(phoneNumber);
        }else{
            passwordRecoveryOutputStream.writeUTF(email);
        }
        passwordRecoveryOutputStream.flush();
        passwordRecoveryOutputStream.writeBoolean(action);
        validInfo = passwordRecoveryInputStream.readBoolean();
        passwordRecoverySocket.close();
        passwordRecoveryInputStream.close();
        passwordRecoveryOutputStream.close();
    }
}
