package Model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * this class allows us to create specified objects defined as message
 */
public class message implements Serializable {
    @Serial
    private static final long serialVersionUID = 574864964023L;
    private String message ;
    private user sender ;
    private user receiver ;
    private final long time ;
    private boolean read  ;
    private final String date ;

    public message(String message, user sender, user receiver) {
        this.message = message;
        this.sender = sender;
        this.receiver = receiver;
        time = System.currentTimeMillis() ;
        read = false ;
        date = CurrentDateTime.time1();
    }
    
    public String getDate() {
        return date;
    }
    
    public boolean isRead() {
        return read;
    }
    
    public void setRead(boolean read) {
        this.read = read;
    }
    
    public long getTime() {
        return time;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public user getSender() {
        return sender;
    }

    public void setSender(user sender) {
        this.sender = sender;
    }

    public user getReceiver() {
        return receiver;
    }

    public void setReceiver(user receiver) {
        this.receiver = receiver;
    }
}
