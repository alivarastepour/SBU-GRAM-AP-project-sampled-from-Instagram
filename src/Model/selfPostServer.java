package Model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class selfPostServer {
    public static List<post> selfPostHandler(String username) throws IOException, ClassNotFoundException {
        Socket socket = new Socket("127.0.0.1" , 9071);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        objectOutputStream.writeUTF(username);
        objectOutputStream.flush();
        List<post> posts = (List<post>) objectInputStream.readObject();
        socket.close();
        objectOutputStream.close();
        objectInputStream.close();
        return posts ;
    }
}
