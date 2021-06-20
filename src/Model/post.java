package Model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * this class allows us o create objects with specified details defined as "post"
 */
public class post implements Serializable {
    @Serial
    private static final long serialVersionUID = 1231479614L;
    private String title ;
    private String caption ;
    private int likes ;
    private int reposts ;
    private final List<comment> comments = new ArrayList<>();
    public List<String> likeUsernames ;
    private user authorUser ;
    private user publisherUser ;
    private String photoAddress ;
    private byte[] photo ;
    private long time ;
    private String FormattedTime;
    
    public post(String title, String caption,String photoAddress ,  user authorUser, user publisherUser) throws IOException {
        this.title = title;
        this.caption = caption;
        this.photoAddress = photoAddress ;
        this.authorUser = authorUser;
        this.publisherUser = publisherUser;
        File file = new File(photoAddress);
        FileInputStream fileInputStream = new FileInputStream(file) ;
        photo = fileInputStream.readAllBytes();
        time = System.currentTimeMillis() ;
        likes = 0 ;
        likeUsernames = new ArrayList<>();
        FormattedTime = CurrentDateTime.time();
    }
    public post(){}
    
    public int getReposts() {
        return reposts;
    }
    
    public List<comment> getComments() {
        return comments;
    }
    
    public void addReposts() {
        this.reposts++;
    }
    
    public void setFormattedTime(String formattedTime) {
        FormattedTime = formattedTime;
    }
    
    public String getFormattedTime() {
        return FormattedTime;
    }
    
    public long getTime() {
        return time;
    }
    
    public void addLike(user user){
        this.likes++;
    }
    
    public void removeLike(user user){
        this.likes--;
    }
    
    public void setTime(long time) {
        this.time = time;
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
    
    public user getAuthorUser() {
        return authorUser;
    }
    
    public user getPublisherUser() {
        return publisherUser;
    }
    
    public byte[] getPhoto() {
        return photo;
    }
    
    public void setAuthorUser(user authorUser) {
        this.authorUser = authorUser;
    }
    
    public void setPublisherUser(user publisherUser) { this.publisherUser =      publisherUser; }
    public void addComment(comment comment){
        this.comments.add(comment);
    }
}
