package model;

public class User {
    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public boolean isGuest() {
        return isGuest;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGuest(boolean guest) {
        isGuest = guest;
    }

    private String userName;
    private String password;
    private boolean isGuest;

    //注册用户
    public User(String userName, String password){
        this.userName = userName;
        this.password = password;
        isGuest = false;
    }

    //游客登录
    public User(){
        isGuest = true;
        this.userName = "Guest";
        this.password = null;
    }

    public boolean canSaveGame(){
        return !isGuest;
    }
}


