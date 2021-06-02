package Model;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * <h1>selfProfileServer</h1>
 <p>this class starts a new client Socket and is a connector between selfProfileController and MainServer</p>
 @author Ali Varaste Pour
 @version 1.0
 @since 5/27/2021
 @since 6/3/1400
 */
public class selfProfileServer {
    private static String username ;

    /**
     *
     * @return the user which is using the app in a specific window
     * @throws IOException loggedInUserInfo.txt may not be found
     * @throws ClassNotFoundException readObject Method of selfProfileObjectInputStream may produce ClassNotFoundException
     */
    public static user selfProfileHandler() throws IOException, ClassNotFoundException {
        File file = new File("src/UsersInfo/loggedInUserInfo.txt");
        FileReader fileReader = new FileReader(file);
        Scanner scanner = new Scanner(fileReader);
        username = scanner.next();
        Socket selfProfileSocket = new Socket("127.0.0.1" , 9089);
        ObjectOutputStream selfProfileObjectOutputStream = new ObjectOutputStream(selfProfileSocket.getOutputStream());
        ObjectInputStream selfProfileObjectInputStream = new ObjectInputStream(selfProfileSocket.getInputStream());
        selfProfileObjectOutputStream.writeUTF(username);
        selfProfileObjectOutputStream.flush();
        user selfUser = (user) selfProfileObjectInputStream.readObject();
        selfProfileSocket.close();
        selfProfileObjectInputStream.close();
        selfProfileObjectOutputStream.close();
        return selfUser;
    }
    
    /**
     *
     * @param username to identify the user who is trying to change their profile photo
     * @param profilePhotoAddress new Address of user's profile photo
     * @throws IOException using socket programming may cause IOException
     */
    public static void ChangeProfilePhoto(String username , String profilePhotoAddress) throws IOException {
        Socket ChangeProfilePhotoSocket = new Socket("127.0.0.1" , 9090);
        DataOutputStream ChangePhotoDataOutputStream = new DataOutputStream(ChangeProfilePhotoSocket.getOutputStream());
        DataInputStream ChangePhotoDataInputStream = new DataInputStream(ChangeProfilePhotoSocket.getInputStream());
        ChangePhotoDataOutputStream.writeUTF(username);
        ChangePhotoDataOutputStream.flush();
        ChangePhotoDataOutputStream.writeUTF(profilePhotoAddress);
        ChangePhotoDataOutputStream.flush();
        ChangeProfilePhotoSocket.close();
        ChangePhotoDataOutputStream.close();
        ChangePhotoDataInputStream.close();
    }
}

