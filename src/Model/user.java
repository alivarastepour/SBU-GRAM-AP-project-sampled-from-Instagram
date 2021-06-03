package Model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * this class is used to build Object so called " user " as individuals who use tha app
 */
public class user implements Serializable {
    @Serial
    private static final long serialVersionUID = 5893609486094L;
    private String name ;
    private String city ;
    private String userName ;
    private String password ;
    private String phoneNumber;
    private String email ;
    private int followings ;
    private int followers ;
    private String PhotoAddress ;
    private byte[] profilePhoto ;
    List<user> followersList = new ArrayList<>();
    List<user> followingsList = new ArrayList<>();
    List<user> mutedUsers = new ArrayList<>();
    public String getName() {
        return name;
    }

    public List<user> getMutedUsers() {
        return mutedUsers;
    }
    public void addMutedUser(user muted){
        mutedUsers.add(muted);
        System.out.println(this.getUserName() + " muted " + muted);
    }
    public void removeMutedUser(user muted){
        mutedUsers.remove(muted);
    }

    public byte[] getProfilePhoto() {
        return profilePhoto;
    }

    /**
     * class constructor
     */
    public user(String name ,String userName, String password, String phoneNumber, String email ,String city , String profilePhotoAddress , String followerNum , String FollowingNum) throws IOException {
        this.name = name ;
        this.userName = userName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.followings = Integer.parseInt(FollowingNum);
        this.followers = Integer.parseInt(followerNum);
        this.city = city ;
        PhotoAddress = profilePhotoAddress ;
        File file = new File(profilePhotoAddress);
        FileInputStream fileInputStream = new FileInputStream(file) ;
        profilePhoto = fileInputStream.readAllBytes();
    }
    
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setFollowings(int followings) {
        this.followings = followings;
    }
    
    public void setFollowers(int followers) {
        this.followers = followers;
    }
    
    public void setProfilePhoto(byte[] profilePhoto) {
        this.profilePhoto = profilePhoto;
    }
    
    public void setPhotoAddress(String photoAddress) {
        PhotoAddress = photoAddress;
    }

    public String getCity() {
        return city;
    }

    public String getPhotoAddress() {
        return PhotoAddress;
    }

    public user(byte[] profilePhoto){
        this.profilePhoto = profilePhoto ;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public String getPassword() {
        return password;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public String getEmail() {
        return email;
    }

    public int getFollowings() {
        return followings;
    }

    public int getFollowers() {
        return followers;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public void add_follower(){
        this.followers++;
    }
    public void add_following(){ this.followings++;}
    public void remove_follower(){
        this.followers--;
    }
    public void remove_following(){ this.followings--;}
    
    public List<user> getFollowersList() {
        return followersList;
    }
    
    public List<user> getFollowingsList() {
        return followingsList;
    }
}
