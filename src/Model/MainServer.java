package Model;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private static Map<String , String> userPass = new ConcurrentHashMap<>();//map from users to passwords
    private static Map<String , String> userEmail = new ConcurrentHashMap<>();//map from users to emails
    private static Map<String , String> userPhone = new ConcurrentHashMap<>();//map from users to phone numbers
    private static List<post> posts = new Vector<>();//keeps all posts

    /**
     * the following method reads all data from a txt file and adds them to users List
     */
    private static void getUsersInformation(){
        users.clear();
        File file = new File("src/UsersInfo/users_information.txt");
        File file1 = new File("src/posts/posts.txt");
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()){
                String tempString = scanner.nextLine();
                String[] tempArray = tempString.split(" ");
                user tempUser = new user(tempArray[0] , tempArray[1], tempArray[2] , tempArray[3] , tempArray[4] , tempArray[5] , tempArray[6] , tempArray[7] , tempArray[8]);
                //                          name            username        pass            phone       email           city            photo
                MainServer.users.add(tempUser) ;
                MainServer.usernames.add(tempArray[1]) ;
                MainServer.userPass.put(tempArray[1] , tempArray[2]) ;
                MainServer.userPhone.put(tempArray[1] , tempArray[3]) ;
                MainServer.userEmail.put(tempArray[1] , tempArray[4]) ;
            }
            FileInputStream fileInputStream = new FileInputStream(file1);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            posts = (List<post>) objectInputStream.readObject();
        } catch ( ClassNotFoundException e) {
            e.printStackTrace();
        } catch ( EOFException e) {
            System.out.println("post file is empty and cant be read");
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
        FileWriter fileWriter = new FileWriter(file , true) ;
        Formatter formatter = new Formatter(fileWriter ) ;
        formatter.format("%s %s %s %s %s %s %s %s %s %s" ,name , userName , password , phoneNumber , email,city
                ,tempUser.getPhotoAddress() , tempUser.getFollowers() , tempUser.getFollowings() ,'\n');
        formatter.flush();
        System.out.println(userName + " joined SBU Geram at : " + CurrentDateTime.time());
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
    private static void changePassword(String username , String password){
        for (Model.user user : users)
            if (user.getUserName().equals(username)){
                user.setPassword(password);
//                break;
            }
        userPass.put(username , password);
        applyChanges(username);
    }

    /**
     *
     * applies changes to database
     */
    private static void applyChanges(String username){
        File file = new File("src/UsersInfo/users_information.txt");
        try {
            FileWriter fileWriter = new FileWriter(file , false) ;
            Formatter formatter = new Formatter(fileWriter);
            for (Model.user user : users) {
                formatter.format("%s %s %s %s %s %s %s %s %s %s",user.getName(), user.getUserName(),
                        user.getPassword(), user.getPhoneNumber(), user.getEmail() ,user.getCity(),
                        user.getPhotoAddress() , user.getFollowers(), user.getFollowings(), '\n');
                formatter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
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
    private static user findUserByUsername(String username){
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
    private static void changeProfileDetails(Map<String , String> map){
        String username = map.get("username");
        if (map.get("email") != null)
            for (Model.user user : users)
                if (user.getUserName().equals(username) && validEmail(map.get("email"))){
                    user.setEmail(map.get("email"));
                    userEmail.put(map.get("username") , map.get("email"));
                }
        if (map.get("phoneNumber") != null)
            for (Model.user user : users)
                if (user.getUserName().equals(username) && validPhoneNumber(map.get("phoneNumber"))){
                    user.setPhoneNumber(map.get("phoneNumber"));
                    userPhone.put(map.get("username") , map.get("phoneNumber"));
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
        if (posts.size() != 0)
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
    private static void followProgress(String x , String y){
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserName().equals(y)){
                users.get(i).add_following();
                users.get(i).followingsList.add(findUserByUsername(y));
            }
            if (users.get(i).getUserName().equals(x)){
                users.get(i).add_follower();
                users.get(i).followersList.add(findUserByUsername(x));
            }
        }
        applyChanges(x);
        applyChanges(y);
    }
    /**
     * this is our running server which starts several Threads
     * each Thread does a particular job
     * @param args java default parameters for p-s-v main
     */
    public static void main(String[] args) {
         new Thread(new Runnable() {
             //this Thread handles login actions
            @Override
            public void run() {
                while (true){
                    try {
                        getUsersInformation();
                        ServerSocket ServerLogInSocket = new ServerSocket(9094);
                        Socket logInServerSocket = ServerLogInSocket.accept();
                        ObjectOutputStream ServerLogInObjectOutputStream = new ObjectOutputStream(logInServerSocket.getOutputStream());
                        ObjectInputStream ServeLogInObjectInputStream = new ObjectInputStream(logInServerSocket.getInputStream());
                        String username = ServeLogInObjectInputStream.readUTF();
                        String password = ServeLogInObjectInputStream.readUTF();
                        boolean validUser = usernames.contains(username) ;
                        boolean validPassword = false ;
                        if (usernames.contains(username))
                            validPassword = userPass.get(username).equals(password) ;
                        if (validUser && validPassword){
                            System.out.println("user [ " + username + " ] logged in at : " +  CurrentDateTime.time());
                            setLoggedInUser(username);
                        }
                        ServerLogInObjectOutputStream.writeUTF(validUser + "~~.~~" + validPassword);
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
            }
        }).start();
         
         new Thread(new Runnable() {
             //this Thread handles signUp actions
             @Override
             public void run() {
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
             }
         }).start();

         new Thread(new Runnable() {
             //this Thread Handles passwordRecovery actions
             @Override
             public void run() {
                 while (true){
                     try {
                         getUsersInformation();
                         ServerSocket ServerPasswordRecoverySocket = new ServerSocket(9092);
                         Socket PasswordRecoveryServerSocket = ServerPasswordRecoverySocket.accept();
                         DataInputStream serverPasswordRecoveryDataInputStream = new DataInputStream(PasswordRecoveryServerSocket.getInputStream());
                         DataOutputStream serverPasswordRecoveryDataOutputStream = new DataOutputStream(PasswordRecoveryServerSocket.getOutputStream());
                         String username = serverPasswordRecoveryDataInputStream.readUTF();
                         System.out.println("password recovery user : " + username);
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
             }
         }).start();
         new Thread(new Runnable() {
             //this Thread handles changePassword actions
             @Override
             public void run() {
                 while (true){
                     try {
                         getUsersInformation();
                         ServerSocket ServerChangePasswordSocket = new ServerSocket(9091);
                         Socket ChangePasswordServerSocket = ServerChangePasswordSocket.accept();
                         DataInputStream serverPasswordRecoveryDataInputStream = new DataInputStream(ChangePasswordServerSocket.getInputStream());
                         DataOutputStream serverPasswordRecoveryDataOutputStream = new DataOutputStream(ChangePasswordServerSocket.getOutputStream());
                         String username = serverPasswordRecoveryDataInputStream.readUTF();
                         System.out.println("change password user name "+username);
                         String password = serverPasswordRecoveryDataInputStream.readUTF();
                         serverPasswordRecoveryDataOutputStream.writeBoolean(validPasswordFormation(password));
                         serverPasswordRecoveryDataOutputStream.flush();
                         if (validPasswordFormation(password))
                            changePassword(username , password);
                         ServerChangePasswordSocket.close();
                         ChangePasswordServerSocket.close();
                         serverPasswordRecoveryDataInputStream.close();
                         serverPasswordRecoveryDataOutputStream.close();
                     } catch (IOException e) {
                         e.printStackTrace();
                     }
                 }
             }
         }).start();
         new Thread(new Runnable() {
             @Override
             public void run() {
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
                         changeProfilePhotoSocket.close();
                         changeProfilePhotoServerSocket.close();
                         changePhotoDataOutputStream.close();
                         changePhotoDataInputStream.close();
                     } catch (IOException e) {
                         e.printStackTrace();
                     }

                 }
             }
         }).start();
         new Thread(new Runnable() {
             //this Thread handles ..
             @Override
             public void run() {
                 while (true){
                     try {
                         ServerSocket ServerSelfProfileSocket = new ServerSocket(9089);
                         Socket SelfProfileServerSocket = ServerSelfProfileSocket.accept();
                         ObjectOutputStream ServerSelfProfileObjectOutputStream = new ObjectOutputStream(SelfProfileServerSocket.getOutputStream());
                         ObjectInputStream ServerSelfProfileObjectInputStream = new ObjectInputStream(SelfProfileServerSocket.getInputStream());
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
             }
         }).start();
         new Thread(new Runnable() {
             //this thread handles anyChange in profile Details
             @Override
             public void run() {
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
             }
         }).start();
         
         new Thread(new Runnable() {
             @Override
             public void run() {
                    while (true){
                        try {
                            ServerSocket newPostSocket = new ServerSocket(9087);
                            Socket newPostServerSocket = newPostSocket.accept();
                            ObjectOutputStream newPosObjectOutputStream = new ObjectOutputStream(newPostServerSocket.getOutputStream());
                            ObjectInputStream newPostObjectInputStream = new ObjectInputStream(newPostServerSocket.getInputStream());
                            post post = (Model.post) newPostObjectInputStream.readObject();
                            posts.add(post);
                            add_post(post);
                            newPostSocket.close();
                            newPostServerSocket.close();
                            newPosObjectOutputStream.close();
                            newPostObjectInputStream.close();
                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
             }
         }).start();
         
         new Thread(new Runnable() {
             @Override
             public void run() {
                while (true){
                    try {
                        ServerSocket TimeLineSocket = new ServerSocket(9086);
                        Socket TimeLineSocketServerSocket = TimeLineSocket.accept();
                        ObjectOutputStream TimeLineObjectOutputStream = new ObjectOutputStream(TimeLineSocketServerSocket.getOutputStream());
                        ObjectInputStream TimeLineObjectInputStream = new ObjectInputStream(TimeLineSocketServerSocket.getInputStream());
                        read_change();
                        TimeLineObjectOutputStream.writeObject(posts);
                        TimeLineObjectOutputStream.flush();
                        TimeLineSocket.close();
                        TimeLineSocketServerSocket.close();
                        TimeLineObjectOutputStream.close();
                        TimeLineObjectInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
             }
         }).start();
         
         new Thread(new Runnable() {
             @Override
             public void run() {
                while (true){
                    try {
                        ServerSocket FollowSocket = new ServerSocket(9085);
                        Socket FollowServerSocket = FollowSocket.accept();
                        DataOutputStream FollowDataOutputStream = new DataOutputStream(FollowServerSocket.getOutputStream());
                        DataInputStream FollowDataInputStream = new DataInputStream(FollowServerSocket.getInputStream());
                        String whoIsFollowed = FollowDataInputStream.readUTF();
                        String whoIsFollowing = FollowDataInputStream.readUTF();
                        followProgress(whoIsFollowed , whoIsFollowing) ;
                        FollowSocket.close();
                        FollowServerSocket.close();
                        FollowDataOutputStream.close();
                        FollowDataInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
             }
         }).start();
    }
}