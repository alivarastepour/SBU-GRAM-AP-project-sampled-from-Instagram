package Model;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * <h1>MainServer</h1>
 <p>this class is our main server class and keeps data available and changeable till its closed</p>
 @author Ali Varaste Pour
 @version 1.0
 @since 5/27/2021
 @since 6/3/1400
 */
public class MainServer {

    private static List<user> users = new Vector<>();//list of users
    private static List<String> usernames = new Vector<>();//list of usernames
    private static final Map<String , String> userPass = new ConcurrentHashMap<>();//map from users to passwords
    private static final Map<String , String> userEmail = new ConcurrentHashMap<>();//map from users to emails
    private static final Map<String , String> userPhone = new ConcurrentHashMap<>();//map from users to phone numbers
    private static List<post> posts = new Vector<>();//keeps all posts

    /**
     * the following method reads all data from a txt file and adds them to users List
     */
    private static void getUsersInformation(){
        users.clear();
        usernames.clear();
        File file = new File("src/UsersInfo/users_information.txt");
        File file1 = new File("src/posts/posts.txt");
        try {
            FileInputStream fileInputStream1 = new FileInputStream(file);
            ObjectInputStream objectInputStream1 = new ObjectInputStream(fileInputStream1);
            users = (List<user>) objectInputStream1.readObject();
            for (Model.user user : users) {
                MainServer.usernames.add(user.getUserName());
                MainServer.userPass.put(user.getUserName(), user.getPassword());
                MainServer.userPhone.put(user.getUserName(), user.getPhoneNumber());
                MainServer.userEmail.put(user.getUserName(), user.getEmail());
            }
            FileInputStream fileInputStream = new FileInputStream(file1);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            posts = (List<post>) objectInputStream.readObject();
        } catch ( ClassNotFoundException e) {
            e.printStackTrace();
        } catch ( EOFException e) {
        } catch ( IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param userName user's input (username) validity base on the defined regex
     * @return true if username does not exist in usernames List and matches the given pattern
     */
    private static boolean validUsername(String userName){
        final String validUsernamePattern = "^[A-Za-z]\\w{5,29}$";
        final Pattern usernamePattern = Pattern.compile(validUsernamePattern);
        Matcher usernameMatcher = usernamePattern.matcher(userName);
        return !MainServer.usernames.contains(userName) && usernameMatcher.matches();
    }

    /**
     *
     * @param password user's input (password) validity base on the defined regex
     * @return true if the password matches the defined regex
     */
    private static boolean validPasswordFormation(String password){
        final String validPasswordPattern = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=\\S+$).{8,20}$";
        final Pattern PasswordPattern = Pattern.compile(validPasswordPattern);
        Matcher PasswordMatcher = PasswordPattern.matcher(password) ;
        return PasswordMatcher.matches();
    }

    /**
     *
     * @param email user's input (email) validity base on the defined regex
     * @return true if the email matches the defined regex
     */
    private static boolean validEmail(String email){
        final String validEmailPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+"
                + "(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+"
                + "(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        final Pattern EmailPattern =  Pattern.compile(validEmailPattern) ;
        Matcher EmailMatcher = EmailPattern.matcher(email);
        return EmailMatcher.matches();
    }

    /**
     *
     * @param phoneNumber user's input (phone Number) validity base on the defined regex
     * @return true if phone Number matches the defined regex
     */
    private static boolean validPhoneNumber(String phoneNumber){
        final String validPhoneNumberPattern = "^\\d{8,11}$" ;
        final Pattern phoneNumberPattern = Pattern.compile(validPhoneNumberPattern);
        Matcher phoneNumberMatcher = phoneNumberPattern.matcher(phoneNumber) ;
        return phoneNumberMatcher.matches();
    }

    /**
     * checks if
     * @param name user's name,
     * @param userName username,
     * @param password password,
     * @param phoneNumber phone number,
     * @param email email   ,
     * @param city city,
     * @param profilePhotoAddress profile Photo Address
     * are valid
     * @throws IOException profile photo address may be wrong
     */
    private static void validFields(String name ,String userName , String password  , String  phoneNumber ,
                                   String  email ,String city , String profilePhotoAddress)
            throws IOException {
        boolean validName = name != null && !name.contains(" ");
        boolean validUsername = validUsername(userName);
        boolean validPasswordFormation = validPasswordFormation(password) ;
        boolean validEmail = validEmail(email);
        boolean validPhoneNumber = validPhoneNumber(phoneNumber);
        boolean validCity = city != null && !city.contains(" ");
        if (validName && validUsername && validPasswordFormation
                && validEmail && validPhoneNumber && validCity){
            add_user(name , userName , password , phoneNumber , email ,city , profilePhotoAddress);
        }
    }

    /**
     * adds user to users list using their
     * @param name name
     * @param userName username
     * @param password password
     * @param phoneNumber phoneNumber
     * @param email email
     * @param city city
     * @param profilePhotoAddress profilePhoto address
     *amd then adds them to our simple dataBase(the txt file)
     * @throws IOException the txt file which is used as a simple dataBase may not be found
     */
    private static void add_user(String name ,String userName ,String  password ,String  phoneNumber
            ,String  email ,String city , String profilePhotoAddress) throws IOException {
        user tempUser = new user(name , userName , password , phoneNumber , email ,city, profilePhotoAddress , String.valueOf(0) , String.valueOf(0));
        MainServer.users.add(tempUser) ;
        MainServer.usernames.add(userName) ;
        MainServer.userPass.put(userName , password) ;
        MainServer.userEmail.put(userName , email) ;
        MainServer.userPhone.put(userName , phoneNumber) ;
        File file = new File("src/UsersInfo/users_information.txt");
        FileOutputStream fileOutputStream = new FileOutputStream(file , false) ;
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream) ;
        objectOutputStream.writeObject(users);
        objectOutputStream.flush();
        System.out.println(userName + " joined SBU Gram at : " + CurrentDateTime.time());
    }

    /**
     *
     * @param username is the username which is trying to recover their password
     * @param email is the defined user's email address
     * @return true if the user exists and the email address is theirs
     */
    private static boolean userEmailExistence(String username , String email){
        return userEmail.containsKey(username) && userEmail.get(username).equals(email);
    }

    /**
     *
     * @param username is the username which is trying to recover their password
     * @param phone is the defined user's phoneNumber
     * @return true if the user exists and the phoneNumber is theirs
     */
    private static boolean userPhoneExistence(String username , String phone){
        return userPhone.containsKey(username) && userPhone.get(username).equals(phone);
    }

    /**
     * changes a user' password and apply changes to our simple database
     * @param username is the username which is trying to change their password
     * @param password is the defined user's new password
     */
    private static void changePassword(String username , String password) throws IOException {
        for (Model.user user : users)
            if (user.getUserName().equals(username))
                user.setPassword(password);
        userPass.put(username , password);
        applyChanges(username);
    }

    /**
     *
     * applies changes to database
     */
    private static void applyChanges(String username) throws IOException {
        File file = new File("src/UsersInfo/users_information.txt");
        FileOutputStream fileOutputStream = new FileOutputStream(file , false);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(users);
    }

    /**
     *
     * @param username is the user who has last signed in
     * @throws IOException loggedInUserInfo.txt may not be found
     */
    private static void setLoggedInUser(String username) throws IOException {
        File file = new File("src/UsersInfo/loggedInUserInfo.txt");
        FileWriter fileWriter = new FileWriter(file , false);
        Formatter formatter = new Formatter(fileWriter);
        formatter.format("%s" , username);
        formatter.flush();
    }

    /**
     *
     * @param username this method tries to find a user by it's 'username'
     * @return defined user
     */
    public static user findUserByUsername(String username){
        user tempUser = null ;
        for (Model.user user : users)
            if (user.getUserName().equals(username))
                tempUser = user;
        return tempUser;
    }

    
    /**
     *
     * @param username to identify the user who is trying to change their profile photo
     * @param Address new Address of user's profile photo
     * @throws IOException called method 'applyChanges' may throw IOException
     */
    private static void apply_profilePhotoChange(String username , String Address) throws IOException {
        for (Model.user user : users) {
            if (user.getUserName().equals(username)) {
                user.setPhotoAddress(Address);
            }
            applyChanges(username);
        }

    }

    /**
     *
     * @param map contains all possible changes that can be done in changeProfileDetailsPage
     */
    private static void changeProfileDetails(Map<String , String> map) throws IOException {
        String username = map.get("username");
        if (map.get("email") != null)
            for (Model.user user : users)
                if (user.getUserName().equals(username) && validEmail(map.get("email"))){
                    user.setEmail(map.get("email"));
                    userEmail.put(map.get("username") , map.get("email"));
                    System.out.println("user [ " + username + " ] changed their Email Address at " + CurrentDateTime.time());
                }
        if (map.get("phoneNumber") != null)
            for (Model.user user : users)
                if (user.getUserName().equals(username) && validPhoneNumber(map.get("phoneNumber"))){
                    user.setPhoneNumber(map.get("phoneNumber"));
                    userPhone.put(map.get("username") , map.get("phoneNumber"));
                    System.out.println("user [ " + username + " ] changed their Phone Number at " + CurrentDateTime.time());
                }
        if (map.get("city") != null)
            for (Model.user user : users)
                if (user.getUserName().equals(username) && !map.get("city").contains(" "))
                    user.setCity(map.get("city"));
        if (map.get("name") != null)
            for (Model.user user : users)
                if (user.getUserName().equals(username) && !map.get("name").contains(" ") )
                    user.setName(map.get("name"));
        applyChanges(username);
        if (map.get("password") != null && validPasswordFormation(map.get("password"))){
            changePassword(username ,map.get("password"));
        }
    }
    
    /**
     * re-writes posts.txt to save all data in post's list
     * @param post -_-
     * @throws IOException file posts.txt may not be found
     */
    private static void add_post(post post) throws IOException {
        File file = new File("src/posts/posts.txt");
        FileOutputStream fileOutputStream = new FileOutputStream(file , false) ;
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream) ;
        objectOutputStream.writeObject(posts);
        objectOutputStream.flush();
    }
    
    /**
     * if any change is applied to user's details, it is necessary to apply it to post's details as well
     */
    private static void read_change(){
        for (Model.post post : posts)
            for (Model.user user : users) {
                if (post.getPublisherUser().getUserName().equals(user.getUserName()))
                    post.setPublisherUser(user);
                if (post.getAuthorUser().getUserName().equals(user.getUserName()))
                    post.setAuthorUser(user);
            }
    }
    
    /**
     * this method handles number of followings and followers of each user
     * @param x following
     * @param y follower
     */
    private static void followProgress(String x , String y) throws IOException {
        for (Model.user user : users) {
            if (user.getUserName().equals(y)) {
                user.add_following();
                user.followingsList.add(findUserByUsername(x));
            }
            if (user.getUserName().equals(x)) {
                user.add_follower();
                user.followersList.add(findUserByUsername(y));
            }
        }
        applyChanges(x);
        applyChanges(y);
    }

    /**
     * returns true if
     * @param x x follows
     * @param y y
     * @return :)
     */
    private static boolean xFollowsY(String y , String x){
        return findUserByUsername(y).followersList.contains(findUserByUsername(x));
    }

    /**
     *
     * @param x the one who is unfollowed
     * @param y the one who unfollows
     * @throws IOException apply change method is called;that may threw IOException
     */
    private static void unFollowProgress(String x ,String y) throws IOException {
        for (Model.user user : users) {
            if (user.getUserName().equals(y)) {
                user.remove_following();
                user.followingsList.remove(findUserByUsername(x));
            }
            if (user.getUserName().equals(x)) {
                user.remove_follower();
                user.followersList.remove(findUserByUsername(y));
            }
            applyChanges(x);
            applyChanges(y);
        }
    }

    /**
     * in this method a user Mutes another user
     * @param muter Muter
     * @param muted Muted
     * @throws IOException method "apply changes" may threw IOException
     */
    private static void muteProgress(String muter , String muted) throws IOException {
        for (Model.user user : users)
            if (user.getUserName().equals(muter))
                user.addMutedUser(findUserByUsername(muted));
        applyChanges(muted);
        applyChanges(muter);
    }

    /**
     * if
     * @param muter muter
     * @param muted has already muted this user
     * @return returns true
     */
    private static boolean xMutesY(String muter , String muted){
        return findUserByUsername(muter).mutedUsers.contains(findUserByUsername(muted));
    }

    /**
     * in this method a user unMutes another user
     * @param muter unMuter
     * @param muted unMuted
     * @throws IOException method "apply changes" may threw IOException
     */
    private static void unmuteProgress(String muter , String muted) throws IOException {
        for (Model.user user : users)
            if (user.getUserName().equals(muter))
                user.removeMutedUser(findUserByUsername(muted));
        applyChanges(muted);
        applyChanges(muter);
    }

    /**
     * receives a
     * @param username username
     * @return and returns all post that should be in their TimeLine
     */
    private static List<post> findPostsForUsername(String username){
        List<post> postsForUsername = new ArrayList<>();
        for (Model.post post : posts)
            if (!findUserByUsername(username).getMutedUsers().contains(post.getPublisherUser()) && (post.getPublisherUser().getFollowersList().contains(findUserByUsername(username)) || post.getPublisherUser().getUserName().equals(username) ))
                postsForUsername.add(post);
        postsForUsername = postsForUsername.stream().sorted((a , b) -> Math.toIntExact(b.getTime() - a.getTime())).collect(Collectors.toList());
        return postsForUsername ;
    }
    
    /**
     * searches if
     * @param post this post
     * @param username is liked by this username.
     * @return and returns true if so
     */
    private static boolean searchLikes(post post , String username){
        read_change();
        if (post.likeUsernames != null)
            return post.likeUsernames.contains(username);
        else
            return false;
    }
    
    /**
     *
     * @param username this user adds a
     * @param post like to this post
     * @throws IOException add_post method may threw IOException
     */
    private static void addLike(post post , String username) throws IOException {
        for (Model.post value : posts)
            if (post.getCaption().equals(value.getCaption())) {
                value.likeUsernames.add(username);
                value.addLike(findUserByUsername(username));
            }
        add_post(post);
    }
    
    /**
     *
     * @param username this user removes a
     * @param post like from this post
     * @throws IOException add_post method may threw IOException
     */
    private static void removeLike(post post , String username) throws IOException {
        for (Model.post value : posts)
            if (post.getCaption().equals(value.getCaption())) {
                value.likeUsernames.remove(username);
                value.removeLike(findUserByUsername(username));
            }
        add_post(post);
    }
    
    /**
     * receives a username as parameter and removes it's user from all lists
     * @param username user who is trying to delete account
     * @throws IOException line 483, 491, 494 may threw IOException
     */
    private static void delete_account(String username) throws IOException {
        for (Model.user user : users) {
            if (!user.getUserName().equals(username)) {
                user.followersList.remove(findUserByUsername(username));
                user.followingsList.remove(findUserByUsername(username));
                user.mutedUsers.remove(findUserByUsername(username));
                user.blockedUsers.remove(findUserByUsername(username));
                user.sentMessages.remove(findUserByUsername(username));
                user.receivedMessages.remove(findUserByUsername(username));
            }
        }
        for (int i = 0; i < posts.size(); i++)
            if (posts.get(i).getAuthorUser().getUserName().equals(username) || posts.get(i).getPublisherUser().getUserName().equals(username))
                posts.remove(i);
        usernames = usernames.stream().filter(a -> !a.equals(username)).collect(Collectors.toList());
        userPass.remove(username);
        userPhone.remove(username);
        userEmail.remove(username);
        users.remove(findUserByUsername(username));
        applyChanges(username);
        add_post(new post());
    }
    
    /**
     * returns list of comments for particular post
     * @param post particular post
     * @return list of comments
     */
    private static List<comment> findCommentsForPost(post post){
        List<comment> list = new ArrayList<>();
        for (Model.post value : posts)
            if (value.getCaption().equals(post.getCaption()))
                list = value.getComments();
        return list ;
    }
    
    /**
     * adds a particular comment to particular post
     * @param comment particular comment
     * @param post particular post
     * @throws IOException add_post method may threw IOException inside
     */
    private static void addCommentToPost(comment comment , post post) throws IOException {
        for (Model.post value : posts)
            if (value.getCaption().equals(post.getCaption()))
                value.addComment(comment);
        add_post(post);
    }

    /**
     * creates an object from message class and adds it to user's sent or received messages
     * @param message message itself
     * @param sender sender of message
     * @param receiver receiver of message
     * @throws IOException since apply change method is called, the possibility of IOException is not zero
     */
    private static void sendTextMessage(String message , user sender , user receiver) throws IOException {
        message textMessage = new message(message , sender , receiver, null , null);
        addMessage(sender, receiver, textMessage);
    }

    /**
     * returns a map holding users as key and list of user's messages as value
     * @param user the user who is trying to receive their inbox Messages
     * @return a map of users to a list of messages
     */
    private static Map<user, List<message>> findReceivedMessages(user user){
        user tempUser = null ;
        for (Model.user value : users)
            if (value.getUserName().equals(user.getUserName()))
                tempUser = value;
        return tempUser.receivedMessages;
    }
    /**
     * returns a map holding users as key and list of user's messages as value
     * @param user the user who is trying to receive their sent Messages
     * @return a map of users to a list of messages
     */
    private static Map<user, List<message>> findSentMessages(user user){
        user tempUser = null ;
        for (Model.user value : users)
            if (value.getUserName().equals(user.getUserName()))
                tempUser = value;
        return tempUser.sentMessages;
    }
    
    /**
     * receives all necessary data for removing a message
     * @param message message itself
     * @param userSender sender of message
     * @param userReceiver receiver of message
     * @throws IOException calling "applyChange" may threw IOException
     */
    private static void deleteMessage(String message , user userSender , user userReceiver) throws IOException {
        for (Model.user user : users) {
            if (userSender.getUserName().equals(user.getUserName())) {
                List<message> x = user.sentMessages.get(userReceiver).stream().filter(a -> !a.getMessage().equals(message)).collect(Collectors.toList());
                user.sentMessages.put(userReceiver, x);
            }
            if (userReceiver.getUserName().equals(user.getUserName())) {
                List<message> x = user.receivedMessages.get(userSender).stream().filter(a -> !a.getMessage().equals(message)).collect(Collectors.toList());
                user.receivedMessages.put(userSender, x);
            }
        }
        applyChanges(userSender.getUserName());
        applyChanges(userReceiver.getUserName());
    }
    
    /**
     * edits a specified message
     * @param message a merged String concatenated by "A___.___A" ; 1st part is primitiveMessage and 2nd part is secondaryMessage
     * @param userSender sender of the message
     * @param userReceiver receiver of the message
     * @throws IOException "applyChange" method may threw IOException
     */
    private static void editMessage(String message , user userSender , user userReceiver) throws IOException {
        String[] tempArray = message.split("A___.___A");
        String primitiveMessage = tempArray[0];
        String secondaryMessage = tempArray[1];
        for (Model.user user : users){
            if (user.getUserName().equals(userSender.getUserName()))
                for (Map.Entry<user, List<message>> v : user.sentMessages.entrySet())
                    for (int j = 0; j < v.getValue().size(); j++)
                        if (v.getValue().get(j).getMessage().equals(primitiveMessage))
                            v.getValue().get(j).setMessage(secondaryMessage);
            if (user.getUserName().equals(userReceiver.getUserName()))
                for (Map.Entry<user, List<message>> v : user.receivedMessages.entrySet())
                    for (int j = 0; j < v.getValue().size(); j++)
                        if (v.getValue().get(j).getMessage().equals(primitiveMessage))
                            v.getValue().get(j).setMessage(secondaryMessage);
        }
        System.out.println(userSender.getUserName() + " edited a message from chat with  " + userReceiver.getUserName() + " at " + CurrentDateTime.time()+ " original message : " + primitiveMessage + ". edited message : " + secondaryMessage);
        applyChanges(userSender.getUserName());
        applyChanges(userReceiver.getUserName());
    }
    
    /**
     * checks if user2 already blocks user1
     * @param user1 user1
     * @param user2 user2
     * @return true if user2 follow user1
     */
    private static boolean alreadyBlocked(String user1 , String user2){
        boolean alreadyBlocked = false ;
        for (Model.user user : users)
            if (user.getUserName().equals(user2))
                alreadyBlocked = user.blockedUsers.contains(findUserByUsername(user1));
        return alreadyBlocked ;
    }
    
    /**
     * adds user1 to user2's blockList
     * @param user1 user1
     * @param user2 user2
     * @throws IOException "applyChange" method may threw IOException
     */
    private static void xBlocksY(String user1 , String user2) throws IOException {
        for (Model.user user : users)
            if (user.getUserName().equals(user2)){
                user.blockedUsers.add(findUserByUsername(user1));
                user.receivedMessages.remove(findUserByUsername(user1));
                user.sentMessages.remove(findUserByUsername(user1));
                findUserByUsername(user1).receivedMessages.remove(user);
                findUserByUsername(user1).sentMessages.remove(user);
                if (xFollowsY(user.getUserName() , user1))
                    unFollowProgress(user.getUserName() , user1);
                if (xFollowsY(user1 , user.getUserName()))
                    unFollowProgress(user1 , user.getUserName());
            }
        applyChanges(user1);
        applyChanges(user2);
    }
    /**
     * removes user1 from user2's blockList
     * @param user1 user1
     * @param user2 user2
     * @throws IOException "applyChange" method may threw IOException
     */
    private static void xunBlocksY(String user1 , String user2) throws IOException {
        for (Model.user user : users) {
            if (user.getUserName().equals(user2))
                user.blockedUsers.remove(findUserByUsername(user1));
        }
        applyChanges(user1);
        applyChanges(user2);
    }
    
    /**
     * marks all messaaes between two chatting users as read
     * @param user1 first chatting user
     * @param user2 second chatting user
     * @throws IOException "applyChange" method may threw IOException
     */
    private static void markAsRead(user user1 , user user2) throws IOException {
        for (Model.user user : users) {
            if (user.getUserName().equals(user2.getUserName())) {
                for (int j = 0; j < user.sentMessages.size(); j++)
                    if (user.sentMessages.containsKey(user1) && user.sentMessages.get(user1) != null)
                        for (int k = 0; k < user.sentMessages.get(user1).size(); k++)
                            user.sentMessages.get(user1).get(k).setRead(true);
            }
            if (user.getUserName().equals(user1.getUserName())) {
                for (int j = 0; j < user.receivedMessages.size(); j++)
                    if (user.receivedMessages.containsKey(user2) && user.receivedMessages.get(user2) != null)
                        for (int k = 0; k < user.receivedMessages.get(user2).size(); k++)
                            user.receivedMessages.get(user2).get(k).setRead(true);
            }
        }
        applyChanges(user1.getUserName());
        applyChanges(user2.getUserName());
    }

    /**
     * creates an object from message class (image message) using images' address , sender and receiver
     * @param photoAddress address of the image
     * @param userSender sender of the image
     * @param userReceiver receiver of the image
     * @throws IOException fileInputStream,readAllBytes and "addMessage" method may threw IOException
     */
    private static void sendPhotoMessage(String photoAddress , user userSender , user userReceiver) throws IOException {
        File file = new File(photoAddress);
        FileInputStream fileInputStream = new FileInputStream(file) ;
        byte[] b = fileInputStream.readAllBytes();
        message PhotoMessage = new message("view image" , userSender , userReceiver , b , null);
        addMessage(userSender, userReceiver, PhotoMessage);
    }

    /**
     * adds a messages to it's owner's repository
     * @param userSender message sender
     * @param userReceiver message receiver
     * @param message message itself
     * @throws IOException "applyChanges" method may threw IOException
     */
    private static void addMessage(user userSender, user userReceiver, message message) throws IOException {
        for (Model.user user : users) {
            if (user.getUserName().equals(userSender.getUserName()))
                if (user.sentMessages.get(userReceiver) == null) {
                    List<message> list = new ArrayList<>();
                    list.add(message);
                    user.sentMessages.put(userReceiver, list);
                } else
                    user.sentMessages.get(userReceiver).add(message);
            if (user.getUserName().equals(userReceiver.getUserName()))
                if (user.receivedMessages.get(userSender) == null) {
                    List<message> list = new ArrayList<>();
                    list.add(message);
                    user.receivedMessages.put(userSender, list);
                } else
                    user.receivedMessages.get(userSender).add(message);
        }
        applyChanges(userSender.getUserName());
        applyChanges(userReceiver.getUserName());
    }

    /**
     * creates an object from message class (voice message) using voice' address , sender and receiver
     * @param message address of voice
     * @param userSender sender
     * @param userReceiver receiver
     * @throws IOException "addMessage" method may threw IOException
     */
    private static void sendVoiceMessage(String message, user userSender, user userReceiver) throws IOException {
        message voiceMessage = new message("voice Message" , userSender , userReceiver , null , message);
        addMessage(userSender, userReceiver, voiceMessage);
    }

    /**
     * this is our running server which starts several Threads
     * each Thread does a particular job
     * @param args java default parameters for p-s-v main
     */
    public static void main(String[] args) {
        //this Thread handles login actions
        new Thread(() -> {
           while (true){
               try {
                   getUsersInformation();
                   ServerSocket ServerLogInSocket = new ServerSocket(9094);
                   Socket logInServerSocket = ServerLogInSocket.accept();
                   ObjectOutputStream ServerLogInObjectOutputStream = new ObjectOutputStream(logInServerSocket.getOutputStream());
                   ObjectInputStream ServeLogInObjectInputStream = new ObjectInputStream(logInServerSocket.getInputStream());
                   String username = ServeLogInObjectInputStream.readUTF();
                   String password = ServeLogInObjectInputStream.readUTF();
                   boolean validUser = false ;
                   for (user value : users)
                       if (value.getUserName().equals(username)) {
                           validUser = true;
                           break;
                       }
                   System.out.println("valid user " + validUser);
                   boolean validPassword = false ;
                   if (usernames.contains(username))
                       validPassword = userPass.get(username).equals(password) ;
                   System.out.println("valid Password " + validPassword);
                   if (validUser && validPassword){
                       System.out.println("user [ " + username + " ] logged in at : " +  CurrentDateTime.time());
                       setLoggedInUser(username);
                   }
                   ServerLogInObjectOutputStream.writeBoolean(validUser);
                   ServerLogInObjectOutputStream.flush();
                   ServerLogInObjectOutputStream.writeBoolean(validPassword);
                   ServerLogInObjectOutputStream.flush();
                   user user = findUserByUsername(username);
                   ServerLogInObjectOutputStream.writeObject(user);
                   ServerLogInObjectOutputStream.flush();
                   ServerLogInSocket.close();
                   logInServerSocket.close();
                   ServeLogInObjectInputStream.close();
                   ServerLogInObjectOutputStream.close();

               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
       }).start();

        //this Thread handles signUp actions
        new Thread(() -> {
           while (true){
               try {
                   ServerSocket serverSignUpSocket = new ServerSocket(9093);
                   Socket signUpServerSocket = serverSignUpSocket.accept();
                   DataInputStream serverSignUpDataInputStream = new DataInputStream(signUpServerSocket.getInputStream());
                   DataOutputStream serverSignUpDataOutputStream = new DataOutputStream(signUpServerSocket.getOutputStream());
                   String name = serverSignUpDataInputStream.readUTF();
                   String username = serverSignUpDataInputStream.readUTF();
                   String password = serverSignUpDataInputStream.readUTF();
                   String phoneNumber = serverSignUpDataInputStream.readUTF();
                   String email = serverSignUpDataInputStream.readUTF();
                   String city = serverSignUpDataInputStream.readUTF();
                   String profilePhotoAddress = serverSignUpDataInputStream.readUTF();
                   boolean validUsername = validUsername(username);
                   validFields(name , username,password,phoneNumber,email, city , profilePhotoAddress);
                   boolean validPasswordFormation = validPasswordFormation(password);
                   boolean validEmail = validEmail(email);
                   boolean validPhoneNumber = validPhoneNumber(phoneNumber);
                   serverSignUpDataOutputStream.writeBoolean(validUsername);
                   serverSignUpDataOutputStream.flush();
                   serverSignUpDataOutputStream.writeBoolean(validPasswordFormation);
                   serverSignUpDataOutputStream.flush();
                   serverSignUpDataOutputStream.writeBoolean(validEmail);
                   serverSignUpDataOutputStream.flush();
                   serverSignUpDataOutputStream.writeBoolean(validPhoneNumber);
                   serverSignUpDataOutputStream.flush();
                   serverSignUpSocket.close();
                   signUpServerSocket.close();
                   serverSignUpDataInputStream.close();
                   serverSignUpDataOutputStream.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
        }).start();

        //this Thread Handles passwordRecovery actions
        new Thread(() -> {
            while (true){
                try {
                    getUsersInformation();
                    ServerSocket ServerPasswordRecoverySocket = new ServerSocket(9092);
                    Socket PasswordRecoveryServerSocket = ServerPasswordRecoverySocket.accept();
                    DataInputStream serverPasswordRecoveryDataInputStream = new DataInputStream(PasswordRecoveryServerSocket.getInputStream());
                    DataOutputStream serverPasswordRecoveryDataOutputStream = new DataOutputStream(PasswordRecoveryServerSocket.getOutputStream());
                    String username = serverPasswordRecoveryDataInputStream.readUTF();
                    String PorE = serverPasswordRecoveryDataInputStream.readUTF();
                    boolean action = serverPasswordRecoveryDataInputStream.readBoolean();
                    boolean userPhoneExistence = false;
                    boolean userEmailExistence = false;
                    if (action){
                        userPhoneExistence = userPhoneExistence(username , PorE) ;
                        serverPasswordRecoveryDataOutputStream.writeBoolean(userPhoneExistence);
                    }else{
                        userEmailExistence = userEmailExistence(username , PorE) ;
                        serverPasswordRecoveryDataOutputStream.writeBoolean(userEmailExistence);
                    }
                    serverPasswordRecoveryDataOutputStream.flush();
                    ServerPasswordRecoverySocket.close();
                    PasswordRecoveryServerSocket.close();
                    serverPasswordRecoveryDataInputStream.close();
                    serverPasswordRecoveryDataOutputStream.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        //this Thread handles changePassword actions
        new Thread(() -> {
            while (true){
                try {
                    getUsersInformation();
                    ServerSocket ServerChangePasswordSocket = new ServerSocket(9091);
                    Socket ChangePasswordServerSocket = ServerChangePasswordSocket.accept();
                    DataInputStream serverPasswordRecoveryDataInputStream = new DataInputStream(ChangePasswordServerSocket.getInputStream());
                    DataOutputStream serverPasswordRecoveryDataOutputStream = new DataOutputStream(ChangePasswordServerSocket.getOutputStream());
                    String username = serverPasswordRecoveryDataInputStream.readUTF();
                    String password = serverPasswordRecoveryDataInputStream.readUTF();
                    serverPasswordRecoveryDataOutputStream.writeBoolean(validPasswordFormation(password));
                    serverPasswordRecoveryDataOutputStream.flush();
                    if (validPasswordFormation(password)){
                        changePassword(username , password);
                        System.out.println("user [ " + username + " ] changed password at " + CurrentDateTime.time());
                    }
                    ServerChangePasswordSocket.close();
                    ChangePasswordServerSocket.close();
                    serverPasswordRecoveryDataInputStream.close();
                    serverPasswordRecoveryDataOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
         new Thread(() -> {
             while (true){
                 try {
                     getUsersInformation();
                     ServerSocket changeProfilePhotoSocket = new ServerSocket(9090);
                     Socket changeProfilePhotoServerSocket = changeProfilePhotoSocket.accept();
                     DataOutputStream changePhotoDataOutputStream = new DataOutputStream(changeProfilePhotoServerSocket.getOutputStream());
                     DataInputStream changePhotoDataInputStream = new DataInputStream(changeProfilePhotoServerSocket.getInputStream());
                     String username = changePhotoDataInputStream.readUTF();
                     String Address = changePhotoDataInputStream.readUTF();
                     apply_profilePhotoChange(username , Address);
                     System.out.println("user [ " + username + " ] changed profilePhoto at " + CurrentDateTime.time());
                     changeProfilePhotoSocket.close();
                     changeProfilePhotoServerSocket.close();
                     changePhotoDataOutputStream.close();
                     changePhotoDataInputStream.close();
                 } catch (IOException e) {
                     e.printStackTrace();
                 }

             }
         }).start();
        //this Thread handles ..
        new Thread(() -> {
            while (true){
                try {
                    ServerSocket ServerSelfProfileSocket = new ServerSocket(9089);
                    Socket SelfProfileServerSocket = ServerSelfProfileSocket.accept();
                    ObjectOutputStream ServerSelfProfileObjectOutputStream = new ObjectOutputStream(SelfProfileServerSocket.getOutputStream());
                    ObjectInputStream ServerSelfProfileObjectInputStream = new ObjectInputStream(SelfProfileServerSocket.getInputStream());
                    read_change();
                    String username = ServerSelfProfileObjectInputStream.readUTF();
                    user selfUser = findUserByUsername(username);
                    ServerSelfProfileObjectOutputStream.writeObject(selfUser);
                    ServerSelfProfileObjectOutputStream.flush();
                    ServerSelfProfileSocket.close();
                    SelfProfileServerSocket.close();
                    ServerSelfProfileObjectInputStream.close();
                    ServerSelfProfileObjectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        //this thread handles anyChange in profile Details
        new Thread(() -> {
           while (true){
               try {
                   ServerSocket ServerChangeProfileDetailsSocket = new ServerSocket(9088);
                   Socket ChangeProfileDetailsServerSocket = ServerChangeProfileDetailsSocket.accept();
                   ObjectOutputStream ChangeProfileDetailsObjectOutputStream = new ObjectOutputStream(ChangeProfileDetailsServerSocket.getOutputStream());
                   ObjectInputStream ChangeProfileDetailsObjectInputStream = new ObjectInputStream(ChangeProfileDetailsServerSocket.getInputStream());
                   Map<String , String > map = (Map<String, String>) ChangeProfileDetailsObjectInputStream.readObject();
                   changeProfileDetails(map);
                   boolean validPassword  = false;
                   if (map.get("password") != null)
                        validPassword = validPasswordFormation(map.get("password"));
                   boolean validEmail = false ;
                   if (map.get("email") != null)
                       validEmail = validEmail(map.get("email"));
                   boolean validPhoneNumber = false ;
                   if (map.get("phoneNumber") != null)
                       validPhoneNumber = validPhoneNumber(map.get("phoneNumber"));
                   boolean validCity = false ;
                   if (map.get("city") != null)
                       validCity = !map.get("city").contains(" ");
                   boolean validName = false ;
                   if (map.get("name") != null)
                       validName = !map.get("name").contains(" ");
                   ChangeProfileDetailsObjectOutputStream.writeBoolean(validPassword);
                   ChangeProfileDetailsObjectOutputStream.flush();
                   ChangeProfileDetailsObjectOutputStream.writeBoolean(validEmail);
                   ChangeProfileDetailsObjectOutputStream.flush();
                   ChangeProfileDetailsObjectOutputStream.writeBoolean(validPhoneNumber);
                   ChangeProfileDetailsObjectOutputStream.flush();
                   ChangeProfileDetailsObjectOutputStream.writeBoolean(validCity);
                   ChangeProfileDetailsObjectOutputStream.flush();
                   ChangeProfileDetailsObjectOutputStream.writeBoolean(validName);
                   ChangeProfileDetailsObjectOutputStream.flush();
                   ServerChangeProfileDetailsSocket.close();
                   ChangeProfileDetailsServerSocket.close();
                   ChangeProfileDetailsObjectOutputStream.close();
                   ChangeProfileDetailsObjectInputStream.close();
               } catch (IOException | ClassNotFoundException e) {
                   e.printStackTrace();
               }
           }
        }).start();

        //this Thread handles new post Actions
        new Thread(() -> {
               while (true){
                   try {
                       ServerSocket newPostSocket = new ServerSocket(9087);
                       Socket newPostServerSocket = newPostSocket.accept();
                       ObjectOutputStream newPosObjectOutputStream = new ObjectOutputStream(newPostServerSocket.getOutputStream());
                       ObjectInputStream newPostObjectInputStream = new ObjectInputStream(newPostServerSocket.getInputStream());
                       post post = (Model.post) newPostObjectInputStream.readObject();
                       posts.add(post);
                       System.out.println("user [ " + post.getPublisherUser().getUserName() + " ] added a new post " + CurrentDateTime.time());
                       add_post(post);
                       newPostSocket.close();
                       newPostServerSocket.close();
                       newPosObjectOutputStream.close();
                       newPostObjectInputStream.close();
                   } catch (IOException | ClassNotFoundException e) {
                       e.printStackTrace();
                   }
               }
        }).start();

        //this Thread returns a list of posts
        new Thread(() -> {
           while (true){
               try {
                   ServerSocket TimeLineSocket = new ServerSocket(9086);
                   Socket TimeLineSocketServerSocket = TimeLineSocket.accept();
                   ObjectOutputStream TimeLineObjectOutputStream = new ObjectOutputStream(TimeLineSocketServerSocket.getOutputStream());
                   ObjectInputStream TimeLineObjectInputStream = new ObjectInputStream(TimeLineSocketServerSocket.getInputStream());
                   read_change();
                   String username = TimeLineObjectInputStream.readUTF();
                   List<post> postsForUsername = findPostsForUsername(username);
                   TimeLineObjectOutputStream.writeObject(postsForUsername);
                   TimeLineObjectOutputStream.flush();
                   TimeLineSocket.close();
                   TimeLineSocketServerSocket.close();
                   TimeLineObjectOutputStream.close();
                   TimeLineObjectInputStream.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
        }).start();

        //this Thread handles a follow process
        new Thread(() -> {
           while (true){
               try {
                   ServerSocket FollowSocket = new ServerSocket(9085);
                   Socket FollowServerSocket = FollowSocket.accept();
                   DataOutputStream FollowDataOutputStream = new DataOutputStream(FollowServerSocket.getOutputStream());
                   DataInputStream FollowDataInputStream = new DataInputStream(FollowServerSocket.getInputStream());
                   String whoIsFollowed = FollowDataInputStream.readUTF();
                   String whoIsFollowing = FollowDataInputStream.readUTF();
                   if (!xFollowsY(whoIsFollowed,whoIsFollowing)){
                       followProgress(whoIsFollowed , whoIsFollowing) ;
                       System.out.println(whoIsFollowing + " started following " + whoIsFollowed);
                   }
                   FollowSocket.close();
                   FollowServerSocket.close();
                   FollowDataOutputStream.close();
                   FollowDataInputStream.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
        }).start();

        //this Thread Checks if a user is already followed
        new Thread(() -> {
            while (true){
                try {
                    ServerSocket FollowSearchSocket = new ServerSocket(9084);
                    Socket FollowSearchServerSocket = FollowSearchSocket.accept();
                    DataOutputStream FollowSearchDataOutputStream = new DataOutputStream(FollowSearchServerSocket.getOutputStream());
                    DataInputStream FollowSearchDataInputStream = new DataInputStream(FollowSearchServerSocket.getInputStream());
                    String y = FollowSearchDataInputStream.readUTF();
                    String x = FollowSearchDataInputStream.readUTF();
                    FollowSearchDataOutputStream.writeBoolean(xFollowsY(y,x));
                    FollowSearchDataOutputStream.flush();
                    FollowSearchSocket.close();
                    FollowSearchServerSocket.close();
                    FollowSearchDataOutputStream.close();
                    FollowSearchDataInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        //this Thread Handles unFollowing Progress
        new Thread(() -> {
            while (true){
                try {
                    ServerSocket unFollowSocket = new ServerSocket(9083);
                    Socket unFollowServerSocket = unFollowSocket.accept();
                    DataOutputStream unFollowDataOutputStream = new DataOutputStream(unFollowServerSocket.getOutputStream());
                    DataInputStream unFollowDataInputStream = new DataInputStream(unFollowServerSocket.getInputStream());
                    String y = unFollowDataInputStream.readUTF();
                    String x = unFollowDataInputStream.readUTF();
                    unFollowProgress(y,x);
                    System.out.println(x + " unfollowed " + y + " at " + CurrentDateTime.time());
                    unFollowSocket.close();
                    unFollowServerSocket.close();
                    unFollowDataOutputStream.close();
                    unFollowDataInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        //this Thread handles muting Process
        new Thread(() -> {
            while (true){
                try {
                    ServerSocket muteSocket = new ServerSocket(9082);
                    Socket muteServerSocket = muteSocket.accept();
                    DataOutputStream muteDataOutputStream = new DataOutputStream(muteServerSocket.getOutputStream());
                    DataInputStream muteDataInputStream = new DataInputStream(muteServerSocket.getInputStream());
                    String muter = muteDataInputStream.readUTF();
                    String muted = muteDataInputStream.readUTF();
                    muteProgress(muter , muted);
                    System.out.println(muter + " muted " + muted + " at " + CurrentDateTime.time());
                    muteSocket.close();
                    muteServerSocket.close();
                    muteDataOutputStream.close();
                    muteDataInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        //this Thread checks if a user is already muted
        new Thread(() -> {
            while(true){
                try {
                    ServerSocket muteSearchSocket = new ServerSocket(45554);
                    Socket muteSearchServerSocket = muteSearchSocket.accept();
                    DataOutputStream muteSearchDataOutputStream = new DataOutputStream(muteSearchServerSocket.getOutputStream());
                    DataInputStream muteSearchDataInputStream = new DataInputStream(muteSearchServerSocket.getInputStream());
                    String muted = muteSearchDataInputStream.readUTF();
                    String muter = muteSearchDataInputStream.readUTF();
                    muteSearchDataOutputStream.writeBoolean(xMutesY(muter , muted));
                    muteSearchDataOutputStream.flush();
                    muteSearchSocket.close();
                    muteSearchServerSocket.close();
                    muteSearchDataOutputStream.close();
                    muteSearchDataInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }).start();

        //this Thread handles unMuting Progress
        new Thread(() -> {
            while (true){
                try {
                    ServerSocket unmuteSocket = new ServerSocket(9080);
                    Socket unmuteServerSocket = unmuteSocket.accept();
                    DataOutputStream unmuteDataOutputStream = new DataOutputStream(unmuteServerSocket.getOutputStream());
                    DataInputStream unmuteDataInputStream = new DataInputStream(unmuteServerSocket.getInputStream());
                    String unmuter = unmuteDataInputStream.readUTF();
                    String unmuted = unmuteDataInputStream.readUTF();
                    unmuteProgress(unmuter , unmuted);
                    System.out.println(unmuter + " unmuted " + unmuted + " at " + CurrentDateTime.time());
                    unmuteSocket.close();
                    unmuteServerSocket.close();
                    unmuteDataOutputStream.close();
                    unmuteDataInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        //this Thread Handles search actions
        new Thread(() -> {
            while (true){
                try {
                    ServerSocket searchSocket = new ServerSocket(9079);
                    Socket searchServerSocket = searchSocket.accept();
                    ObjectOutputStream searchObjectOutputStream = new ObjectOutputStream(searchServerSocket.getOutputStream());
                    ObjectInputStream searchObjectInputStream = new ObjectInputStream(searchServerSocket.getInputStream());
                    String username = searchObjectInputStream.readUTF();
                    String loggedInUserName = searchObjectInputStream.readUTF();
                    if (findUserByUsername(username) != null && !findUserByUsername(username).blockedUsers.contains(findUserByUsername(loggedInUserName)))
                       searchObjectOutputStream.writeObject(findUserByUsername(username));
                    else
                        searchObjectOutputStream.writeObject(null);
                    searchObjectOutputStream.flush();
                    searchSocket.close();
                    searchServerSocket.close();
                    searchObjectOutputStream.close();
                    searchObjectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        //this Thread searches for post like's
        new Thread(() -> {
            while (true){
                try {
                    ServerSocket searchLikesSocket = new ServerSocket(9078);
                    Socket searchLikesServerSocket = searchLikesSocket.accept();
                    ObjectOutputStream searchLikesObjectOutputStream = new ObjectOutputStream(searchLikesServerSocket.getOutputStream());
                    ObjectInputStream searchLikesObjectInputStream = new ObjectInputStream(searchLikesServerSocket.getInputStream());
                    post post = (Model.post) searchLikesObjectInputStream.readObject();
                    String username = searchLikesObjectInputStream.readUTF();
                    read_change();
                    boolean condition = searchLikes(post , username);
                    searchLikesObjectOutputStream.writeBoolean(condition);
                    searchLikesObjectOutputStream.flush();
                    searchLikesSocket.close();
                    searchLikesServerSocket.close();
                    searchLikesObjectOutputStream.close();
                    searchLikesObjectInputStream.close();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        //this Thread handles adding like progress
        new Thread(() -> {
           while (true){
               try {
                   ServerSocket addLikeSocket = new ServerSocket(9077);
                   Socket addLikeServerSocket = addLikeSocket.accept();
                   ObjectOutputStream searchLikesObjectOutputStream = new ObjectOutputStream(addLikeServerSocket.getOutputStream());
                   ObjectInputStream searchLikesObjectInputStream = new ObjectInputStream(addLikeServerSocket.getInputStream());
                   post post = (Model.post) searchLikesObjectInputStream.readObject();
                   String username = searchLikesObjectInputStream.readUTF();
                   addLike(post , username);
                   System.out.println(username + " liked a post by "  + post.getPublisherUser().getUserName() + " at " + CurrentDateTime.time());
                   addLikeSocket.close();
                   addLikeServerSocket.close();
                   searchLikesObjectOutputStream.close();
                   searchLikesObjectInputStream.close();
               } catch (IOException | ClassNotFoundException e) {
                   e.printStackTrace();
               }
           }
        }).start();

        //this thread handles removing like progress
        new Thread(() -> {
            while (true){
                try {
                    ServerSocket removeLikeSocket = new ServerSocket(9076);
                    Socket removeLikeServerSocket = removeLikeSocket.accept();
                    ObjectOutputStream searchLikesObjectOutputStream = new ObjectOutputStream(removeLikeServerSocket.getOutputStream());
                    ObjectInputStream searchLikesObjectInputStream = new ObjectInputStream(removeLikeServerSocket.getInputStream());
                    post post = (Model.post) searchLikesObjectInputStream.readObject();
                    String username = searchLikesObjectInputStream.readUTF();
                    removeLike(post , username);
                    System.out.println(username + " removed a like from post by "  + post.getPublisherUser().getUserName() + " at " + CurrentDateTime.time());
                    removeLikeSocket.close();
                    removeLikeServerSocket.close();
                    searchLikesObjectOutputStream.close();
                    searchLikesObjectInputStream.close();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        //this Thread removes a user's acc
        new Thread(() -> {
            while (true){
                try {
                    ServerSocket deleteAccSocket = new ServerSocket(9075);
                    Socket deleteAccServerSocket = deleteAccSocket.accept();
                    DataOutputStream deleteAccDataOutputStream = new DataOutputStream(deleteAccServerSocket.getOutputStream());
                    DataInputStream deleteAccDataInputStream = new DataInputStream(deleteAccServerSocket.getInputStream());
                    String username = deleteAccDataInputStream.readUTF();
                    delete_account(username);
                    System.out.println(username + " deleted their account at " + CurrentDateTime.time());
                    deleteAccSocket.close();
                    deleteAccServerSocket.close();
                    deleteAccDataOutputStream.close();
                    deleteAccDataInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        //this Thread handles reposting actions
        new Thread(() -> {
            while (true){
                try {
                    ServerSocket rePostSocket = new ServerSocket(9074);
                    Socket rePostServerSocket = rePostSocket.accept();
                    ObjectOutputStream rePostObjectOutputStream = new ObjectOutputStream(rePostServerSocket.getOutputStream());
                    ObjectInputStream rePostObjectInputStream = new ObjectInputStream(rePostServerSocket.getInputStream());
                    post post = (Model.post) rePostObjectInputStream.readObject();
                    user user = findUserByUsername(rePostObjectInputStream.readUTF());
                    post.setPublisherUser(user);
                    post.setTime(System.currentTimeMillis());
                    post.setFormattedTime(CurrentDateTime.time());
                    for (Model.post value : posts)
                        if (value.getAuthorUser().getUserName().equals(post.getAuthorUser().getUserName()))
                            value.addReposts();
                    posts.add(post);
                    add_post(post);
                    System.out.println(user.getUserName() + " reposted a post from " + post.getAuthorUser().getUserName() + " at :" +CurrentDateTime.time());
                    rePostSocket.close();
                    rePostServerSocket.close();
                    rePostObjectOutputStream.close();
                    rePostObjectInputStream.close();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        //this Thread loads previous comments
        new Thread(() -> {
            while (true){
                try {
                    ServerSocket commentsSocket = new ServerSocket(9073);
                    Socket commentsServerSocket = commentsSocket.accept();
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(commentsServerSocket.getOutputStream());
                    ObjectInputStream objectInputStream = new ObjectInputStream(commentsServerSocket.getInputStream());
                    post post = (Model.post) objectInputStream.readObject();
                    List<comment> commentList = findCommentsForPost(post);
                    objectOutputStream.writeObject(commentList);
                    objectOutputStream.flush();
                    commentsServerSocket.close();
                    objectOutputStream.close();
                    commentsSocket.close();
                    objectInputStream.close();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        //this Thread handles comment adding progress
        new Thread(() -> {
            while (true){
                try {
                    ServerSocket commentsSocket = new ServerSocket(9072);
                    Socket commentsServerSocket = commentsSocket.accept();
                    ObjectOutputStream commentsObjectOutputStream = new ObjectOutputStream(commentsServerSocket.getOutputStream());
                    ObjectInputStream commentsObjectInputStream = new ObjectInputStream(commentsServerSocket.getInputStream());
                    comment comment = (Model.comment) commentsObjectInputStream.readObject();
                    post post = (Model.post) commentsObjectInputStream.readObject();
                    addCommentToPost(comment , post) ;
                    System.out.println(comment.getAuthor() + " added a new comment to " + post.getAuthorUser().getUserName() + "'s post : " + comment.getComment() + " at " + CurrentDateTime.time());
                    commentsSocket.close();
                    commentsServerSocket.close();
                    commentsObjectOutputStream.close();
                    commentsObjectInputStream.close();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        //this Thread searches for self user posts(to show in profilePage)
        new Thread(() -> {
            while (true){
                try {
                    ServerSocket selfPostsSocket = new ServerSocket(9071);
                    Socket selfPostsServerSocket = selfPostsSocket.accept();
                    ObjectOutputStream selfPostsObjectOutputStream = new ObjectOutputStream(selfPostsServerSocket.getOutputStream());
                    ObjectInputStream selfPostsObjectInputStream = new ObjectInputStream(selfPostsServerSocket.getInputStream());
                    String username = selfPostsObjectInputStream.readUTF();
                    List<post> postList = posts.stream().filter(a->a.getPublisherUser().getUserName().equals(username)).collect(Collectors.toList());
                    selfPostsObjectOutputStream.writeObject(postList);
                    selfPostsObjectOutputStream.flush();
                    selfPostsSocket.close();
                    selfPostsServerSocket.close();
                    selfPostsObjectOutputStream.close();
                    selfPostsObjectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        //this Thread receives data and creates a new object from message class or removes one or edits one using the described data
        new Thread(() -> {
            while (true){
                try {
                    ServerSocket addMessageSocket = new ServerSocket(9070);
                    Socket addMessageServerSocket = addMessageSocket.accept();
                    ObjectOutputStream addMessageObjectOutputStream = new ObjectOutputStream(addMessageServerSocket.getOutputStream());
                    ObjectInputStream addMessageObjectInputStream = new ObjectInputStream(addMessageServerSocket.getInputStream());
                    String condition = addMessageObjectInputStream.readUTF();
                    String message = addMessageObjectInputStream.readUTF();
                    user userSender = findUserByUsername(addMessageObjectInputStream.readUTF());
                    user userReceiver = findUserByUsername(addMessageObjectInputStream.readUTF());
                    if (condition.equals("newMessage")){
                        sendTextMessage(message , userSender , userReceiver);
                        System.out.println(userSender.getUserName() + " sent a message to " + userReceiver.getUserName() + " at " + CurrentDateTime.time() + " message : " + message);
                    }
                    if (condition.equals("deleteMessage")){
                        deleteMessage(message , userSender , userReceiver);
                        System.out.println(userSender.getUserName() + " removed a message from chat with  " + userReceiver.getUserName() + " at " + CurrentDateTime.time()+ " message : " + message);
                    }
                    if (condition.equals("editMessage"))
                        editMessage(message , userSender , userReceiver);
                    if (condition.equals("newPhotoMessage"))
                        sendPhotoMessage(message , userSender , userReceiver);
                    if (condition.equals("newVoiceMessage"))
                        sendVoiceMessage(message , userSender , userReceiver);
                    addMessageSocket.close();
                    addMessageServerSocket.close();
                    addMessageObjectOutputStream.close();
                    addMessageObjectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        //this Thread updates all sent and received messages
        new Thread(() -> {
            while (true){
                try {
                    ServerSocket messagesSocket = new ServerSocket(9069);
                    Socket messagesServerSocket = messagesSocket.accept();
                    ObjectOutputStream messagesObjectOutputStream = new ObjectOutputStream(messagesServerSocket.getOutputStream());
                    ObjectInputStream messagesObjectInputStream = new ObjectInputStream(messagesServerSocket.getInputStream());
                    user user = findUserByUsername(messagesObjectInputStream.readUTF());
                    String condition = messagesObjectInputStream.readUTF();
                    Map<user, List<message>> messages = new HashMap<>();
                    if (condition.equals("receivedMessages"))
                        messages = findReceivedMessages(user);
                    else if (condition.equals("sentMessages"))
                        messages = findSentMessages(user);
                    messagesObjectOutputStream.writeObject(messages);
                    messagesObjectOutputStream.flush();
                    messagesSocket.close();
                    messagesServerSocket.close();
                    messagesObjectOutputStream.close();
                    messagesObjectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        
        //this Thread Handles all blocking actions
        new Thread(() -> {
            while (true){
                try {
                    ServerSocket blockSocket = new ServerSocket(9068);
                    Socket blockServerSocket = blockSocket.accept();
                    DataOutputStream blockDataOutputStream = new DataOutputStream(blockServerSocket.getOutputStream());
                    DataInputStream blockDataInputStream = new DataInputStream(blockServerSocket.getInputStream());
                    String user1 = blockDataInputStream.readUTF();
                    String user2 = blockDataInputStream.readUTF();
                    String condition = blockDataInputStream.readUTF();
                    boolean alreadyBlocked = false ;
                    if (condition.equals("checkIfBlocked")){
                        alreadyBlocked = alreadyBlocked(user1 , user2) ;
                        blockDataOutputStream.writeBoolean(alreadyBlocked);
                        blockDataOutputStream.flush();
                    }
                    if (condition.equals("block"))
                        xBlocksY(user1 , user2);
                    if (condition.equals("unblock"))
                        xunBlocksY(user1 , user2);
                    blockSocket.close();
                    blockServerSocket.close();
                    blockDataOutputStream.close();
                    blockDataInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        
        //this Thread marks messages as read
        new Thread(() -> {
            while (true){
                try {
                    ServerSocket readSocket = new ServerSocket(9067);
                    Socket readServerSocket = readSocket.accept();
                    ObjectOutputStream readObjectOutputStream = new ObjectOutputStream(readServerSocket.getOutputStream());
                    ObjectInputStream readObjectInputStream = new ObjectInputStream(readServerSocket.getInputStream());
                    String user1 = readObjectInputStream.readUTF();
                    String user2 = readObjectInputStream.readUTF();
                    markAsRead(findUserByUsername(user1) , findUserByUsername(user2));
                    readSocket.close();
                    readServerSocket.close();
                    readObjectOutputStream.close();
                    readObjectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        
    }


}