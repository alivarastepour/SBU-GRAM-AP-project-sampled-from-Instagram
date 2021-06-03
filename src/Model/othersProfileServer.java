package Model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
/**
 * <h1>othersProfileServer</h1>
 <p>this class starts a new client Socket and is a connector between othersProfileController and MainServer</p>
 @author Ali Varaste Pour
 @version 1.0
 @since 6/2/2021
 @since 11/3/1400
 */
public class othersProfileServer {
    public static void FollowHandler(String whoIsFollowed , String whoIsFollowing) throws IOException {
        Socket followSocket = new Socket("127.0.0.1" , 9085);
        DataOutputStream followDataOutputStream = new DataOutputStream(followSocket.getOutputStream());
        DataInputStream followDataInputStream = new DataInputStream(followSocket.getInputStream());
        followDataOutputStream.writeUTF(whoIsFollowed);
        followDataOutputStream.flush();
        followDataOutputStream.writeUTF(whoIsFollowing);
        followDataOutputStream.flush();
        followSocket.close();
        followDataOutputStream.close();
        followDataInputStream.close();
    }
    public static boolean followValidity(String y , String x) throws IOException {
        Socket followSocket = new Socket("127.0.0.1" , 9084);
        DataOutputStream followDataOutputStream = new DataOutputStream(followSocket.getOutputStream());
        DataInputStream followDataInputStream = new DataInputStream(followSocket.getInputStream());
        followDataOutputStream.writeUTF(y);
        followDataOutputStream.flush();
        followDataOutputStream.writeUTF(x);
        followDataOutputStream.flush();
        boolean condition =  followDataInputStream.readBoolean();
        followSocket.close();
        followDataOutputStream.close();
        followDataInputStream.close();
        return condition ;
    }
    public static void unFollowHandler(String y , String x) throws IOException {
        Socket unFollowSocket = new Socket("127.0.0.1" , 9083);
        DataOutputStream unFollowDataOutputStream = new DataOutputStream(unFollowSocket.getOutputStream());
        DataInputStream unFollowDataInputStream = new DataInputStream(unFollowSocket.getInputStream());
        unFollowDataOutputStream.writeUTF(y);
        unFollowDataOutputStream.flush();
        unFollowDataOutputStream.writeUTF(x);
        unFollowDataOutputStream.flush();
        unFollowSocket.close();
        unFollowDataOutputStream.close();
        unFollowDataInputStream.close();
    }
    public static void muteHandler(String muted , String muter) throws IOException {
        Socket muteSocket = new Socket("127.0.0.1" , 9082);
        DataOutputStream muteDataOutputStream = new DataOutputStream(muteSocket.getOutputStream());
        DataInputStream muteDataInputStream = new DataInputStream(muteSocket.getInputStream());
        muteDataOutputStream.writeUTF(muter);
        muteDataOutputStream.flush();
        muteDataOutputStream.writeUTF(muted);
        muteDataOutputStream.flush();
        muteSocket.close();
        muteDataOutputStream.close();
        muteDataInputStream.close();
    }
    public static boolean muteValidity(String muted , String muter) throws IOException {
        Socket muteSocket = new Socket("127.0.0.1" , 45554);
        DataOutputStream muteDataOutputStream = new DataOutputStream(muteSocket.getOutputStream());
        DataInputStream muteDataInputStream = new DataInputStream(muteSocket.getInputStream());
        muteDataOutputStream.writeUTF(muted);
        muteDataOutputStream.flush();
        muteDataOutputStream.writeUTF(muter);
        muteDataOutputStream.flush();
        boolean condition = muteDataInputStream.readBoolean();
        muteSocket.close();
        muteDataOutputStream.close();
        muteDataInputStream.close();
        return condition ;
    }
    public static void unMuteHandler(String unmuted , String unmuter) throws IOException {
        Socket muteSocket = new Socket("127.0.0.1" , 9080);
        DataOutputStream muteDataOutputStream = new DataOutputStream(muteSocket.getOutputStream());
        DataInputStream muteDataInputStream = new DataInputStream(muteSocket.getInputStream());
        muteDataOutputStream.writeUTF(unmuter);
        muteDataOutputStream.flush();
        muteDataOutputStream.writeUTF(unmuted);
        muteDataOutputStream.flush();
        muteSocket.close();
        muteDataOutputStream.close();
        muteDataInputStream.close();
    }
}
