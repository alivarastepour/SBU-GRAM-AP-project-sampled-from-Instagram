package Model;

import Controller.logInPage_Controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
/**
 * <h1>searchServer</h1>
 <p>this class starts a new client Socket and is a connector between searchController and MainServer</p>
 @author Ali Varaste Pour
 @version 1.0
 @since 6/4/2021
 @since 13/3/1400
 */
public class searchServer {
    /**
     *
     * @param username searches if a user exists with the given username
     * @return and returns true in case of existence
     * @throws IOException using socket programming may cause IOException
     * @throws ClassNotFoundException casting object to specific class may threw CNFException
     */
    public static user searchHandler(String username) throws IOException, ClassNotFoundException {
        Socket searchSocket = new Socket("127.0.0.1",9079);
        ObjectOutputStream searchObjectOutputStream = new ObjectOutputStream(searchSocket.getOutputStream());
        ObjectInputStream searchObjectInputStream = new ObjectInputStream(searchSocket.getInputStream());
        searchObjectOutputStream.writeUTF(username);
        searchObjectOutputStream.flush();
        searchObjectOutputStream.writeUTF(logInPage_Controller.Username);
        searchObjectOutputStream.flush();
        user user= (Model.user) searchObjectInputStream.readObject();
        searchSocket.close();
        searchObjectOutputStream.close();
        searchObjectInputStream.close();
        return user ;
    }
}
