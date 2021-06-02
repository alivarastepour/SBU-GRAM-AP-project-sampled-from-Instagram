package Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * this class allows us o create objects with specified details defined as "post"
 */
public class post implements Serializable {
    private static final long SerialVersionUID = 1231479614l;
    private String title ;
    private String caption ;
    private int likes ;
    private List<String > commentList = new ArrayList<>();
    private user authorUser ;
    private user publisherUser ;
    private String photoAddress ;
    private byte[] photo ;
    
    public post(String title, String caption,String photoAddress ,  user authorUser, user publisherUser) throws IOException {
        this.title = title;
        this.caption = caption;
        this.photoAddress = photoAddress ;
        this.authorUser = authorUser;
        this.publisherUser = publisherUser;
        File file = new File(photoAddress);
        FileInputStream fileInputStream = new FileInputStream(file) ;
        photo = fileInputStream.readAllBytes();
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getCaption() {
        return caption;
    }
    
    public int getLikes() {
        return likes;
    }
    
    public List<String> getCommentList() {
        return commentList;
    }
    
    public user getAuthorUser() {
        return authorUser;
    }
    
    public user getPublisherUser() {
        return publisherUser;
    }
    
    public String getPhotoAddress() {
        return photoAddress;
    }
    
    public byte[] getPhoto() {
        return photo;
    }
    
    public void setAuthorUser(user authorUser) {
        this.authorUser = authorUser;
    }
    
    public void setPublisherUser(user publisherUser) {
        this.publisherUser = publisherUser;
    }

}
