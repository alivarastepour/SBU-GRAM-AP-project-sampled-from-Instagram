package Model;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class searchServer {
    public static user searchHandler(String username) throws IOException, ClassNotFoundException {
        Socket searchSocket = new Socket("127.0.0.1",9079);
        ObjectOutputStream searchObjectOutputStream = new ObjectOutputStream(searchSocket.getOutputStream());
        ObjectInputStream searchObjectInputStream = new ObjectInputStream(searchSocket.getInputStream());
        searchObjectOutputStream.writeUTF(username);
        searchObjectOutputStream.flush();
        user user= (Model.user) searchObjectInputStream.readObject();
        searchSocket.close();
        searchObjectOutputStream.close();
        searchObjectInputStream.close();
        return user ;
    }
}
