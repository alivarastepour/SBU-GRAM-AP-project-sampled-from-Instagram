package Model;

import java.io.*;
import java.net.Socket;

/**
 * <h1>signUpServer</h1>
 <p>this class starts a new client Socket and is a connector between signUpController and MainServer</p>
 @author Ali Varaste Pour
 @version 1.0
 @since 5/27/2021
 @since 6/3/1400
 */
public class signUpServer {
    private static boolean validUsername  ;
    private static boolean validPasswordFormation  ;
    private static boolean validEmail  ;
    private static boolean validPhoneNumber  ;

    /**
     * this method starts a new client socket and sends :
     * @param name name
     * @param userName username
     * @param password password
     * @param phoneNumber phoneNumber
     * @param email email
     * @param city city
     * @param profilePhotoAddress profilePhotoAddress
     *to server and creates a new account
     * @throws IOException using socket programming may cause IOException
     */
    public static void signUpHandler(String name ,String userName ,String password
             ,String phoneNumber ,String email ,String city , String profilePhotoAddress) throws IOException {
        Socket signUpSocket = new Socket("127.0.0.1" , 9093);
        DataInputStream signUpDataInputStream = new DataInputStream(signUpSocket.getInputStream());
        DataOutputStream signUpDataOutputStream = new DataOutputStream(signUpSocket.getOutputStream());
        signUpDataOutputStream.writeUTF(name);
        signUpDataOutputStream.flush();
        signUpDataOutputStream.writeUTF(userName);
        signUpDataOutputStream.flush();
        signUpDataOutputStream.writeUTF(password);
        signUpDataOutputStream.flush();
        signUpDataOutputStream.writeUTF(phoneNumber);
        signUpDataOutputStream.flush();
        signUpDataOutputStream.writeUTF(email);
        signUpDataOutputStream.flush();
        signUpDataOutputStream.writeUTF(city);
        signUpDataOutputStream.flush();
        signUpDataOutputStream.writeUTF(profilePhotoAddress);
        signUpDataOutputStream.flush();
        validUsername = signUpDataInputStream.readBoolean();
        validPasswordFormation = signUpDataInputStream.readBoolean();
        validEmail = signUpDataInputStream.readBoolean();
        validPhoneNumber = signUpDataInputStream.readBoolean();
    }
    public static boolean isValidUsername() {
        return validUsername;
    }

    public static boolean isValidPasswordFormation() {
        return validPasswordFormation;
    }

    public static boolean isValidEmail() {
        return validEmail;
    }

    public static boolean isValidPhoneNumber() {
        return validPhoneNumber;
    }
}
