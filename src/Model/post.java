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
    private List<comment> comments = new ArrayList<>();
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
    
    public void setReposts(int reposts) {
        this.reposts = reposts;
    }
    
    public List<comment> getComments() {
        return comments;
    }
    
    public void setComments(List<comment> comments) {
        this.comments = comments;
    }
    
    public void setLikeUsernames(List<String> likeUsernames) {
        this.likeUsernames = likeUsernames;
    }
    
    public List<String> getLikeUsernames() {
        return likeUsernames;
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
    
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    public void addLike(user user){
        this.likes++;
    }
    public void removeLike(user user){
        this.likes--;
    }
    public void setCaption(String caption) {
        this.caption = caption;
    }
    
    public void setLikes(int likes) {
        this.likes = likes;
    }
    
    public List<String> getLikeUsers() {
        return likeUsernames;
    }
    
    public void setLikeUsers(List<String> likeUsers) {
        this.likeUsernames = likeUsers;
    }
    
    public void setPhotoAddress(String photoAddress) {
        this.photoAddress = photoAddress;
    }
    
    public void setPhoto(byte[] photo) {
        this.photo = photo;
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
    public void addComment(comment comment){
        this.comments.add(comment);
    }
}
