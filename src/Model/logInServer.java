package Model;

import Controller.changeProfileDetailsPage_Controller;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.Socket;
/**
 * <h1>logInServer</h1>
 <p>this class starts a new client Socket and is a connector between logInController and MainServer</p>
 @author Ali Varaste Pour
 @version 1.0
 @since 5/27/2021
 @since 6/3/1400
 */
public class logInServer {
    private static boolean validUsername = false ;
    private static boolean validPassword = false ;
    
    public static boolean isValidUsername() {
        return validUsername;
    }
    
    public static boolean isValidPassword() {
        return validPassword;
    }

    /**
     *
     * @param userName identifies which user is trying to login
     * @param Password is the entered password by the user in login page
     * @throws IOException using socket programming may cause IOException
     */
    public static void logInHandler(String userName , String Password) throws IOException, ClassNotFoundException {
        Socket logInSocket = new Socket("127.0.0.1" , 9094) ;
        ObjectOutputStream logInObjectOutputStream = new ObjectOutputStream(logInSocket.getOutputStream());
        ObjectInputStream logInObjectInputStream = new ObjectInputStream(logInSocket.getInputStream()) ;
        logInObjectOutputStream.writeUTF(userName);
        logInObjectOutputStream.flush();
        logInObjectOutputStream.writeUTF(Password);
        logInObjectOutputStream.flush();
        String encryptedUserPassValidity = logInObjectInputStream.readUTF();
        String[] decryptedUserPassValidityArray = encryptedUserPassValidity.split("~~.~~") ;
        if (decryptedUserPassValidityArray[0].equals("true"))
            validUsername = true ;
        if (decryptedUserPassValidityArray[1].equals("true"))
            validPassword = true ;
        user user = (Model.user) logInObjectInputStream.readObject();
        logInSocket.close();
        logInObjectInputStream.close();
        logInObjectOutputStream.close();
        changeProfileDetailsPage_Controller.username = userName ;
        changeProfileDetailsPage_Controller.user = user ;
//        System.out.println(user.getCity());
    }
}
