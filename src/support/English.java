package support;

public enum English implements Language {
    /*INSTANCE;

    //
    @Override
    public String loginTitle0() {
        return "Cool Summer - Login";
    }

    @Override
    public String loginTitle() {
        return "Welcome to Cool Summer";
    }

    @Override
    public String loginSubtitle() {
        return "Please login or register";
    }

    @Override
    public String loginLogin() {
        return "Login";
    }

    @Override
    public String loginReg() {
        return "Register";
    }

    @Override
    public String loginExit() {
        return "Exit";
    }

    @Override
    public String loginGuest() {
        return "Guest";
    }


    @Override public String menuTitle() { return "Cool Summer - Main Menu"; }
    @Override public String menuWelcome(String username) { return "Welcome, " + username; }
    @Override public String menuStartGame() { return "Start Game"; }
    @Override public String menuLevelSelect() { return "Level Select"; }
    @Override public String menuExit() { return "Exit"; }
    @Override public String getLanguageTooltip() { return "Change Language"; }
    @Override public String getHelpTooltip() { return "Help"; }

    @Override
    public String gameSave() {
        return "Save Game";
    }

    */

    INSTANCE;
    //login
    @Override public String loginTitle0(){ return "Summer Challenge - Login"; }
    @Override public String loginTitle1()    { return "Welcome to"; }
    @Override public String loginTitle2() {return "Summer Challenge";    }
    @Override public String loginSubtitle()   { return "Please login or register"; }
    @Override public String loginLogin(){ return "Login"; }
    @Override public String loginReg() { return "Register"; }
    @Override public String loginExit(){ return "Exit"; }
    @Override public String loginGuest(){ return "Play as Guest"; }
    @Override public String loginUsernameLabel() { return "Username:"; }
    @Override public String loginPasswordLabel() { return "Password:"; }
    @Override public String loginFailTitle(){ return "Login Failed"; }
    @Override public String loginRegFailTitle(){ return "Registration Failed"; }
    @Override public String loginRegSuccessTitle(){ return "Register"; }
    @Override public String loginGuestMsgTitle() { return "Info"; }
    @Override public String loginEmptyFields()   { return "Username and password cannot be empty!"; }
    @Override public String loginSuccess(String u){ return "Login succeessfully! Welcome to the Game, " + u; }
    @Override public String loginFail(){ return "Wrong username or password!"; }
    @Override public String loginCommaError(){ return "Username cannot contain comma!"; }
    @Override public String loginRegSuccess(){ return "Registration successful! Please login."; }
    @Override public String loginRegFail(){ return "Username already exists, please change!"; }
    @Override public String loginGuestMsg(){ return "Entered as guest."; }

    //menu
    @Override public String menuTitle(){ return "Summer Challenge - Menu"; }
    @Override public String menuWelcome(String u){ return "Welcome, " + u; }
    @Override public String menuStartGame(){ return "Start Game"; }
    @Override public String menuLevelSelect(){ return "Level Select"; }
    @Override public String menuExit(){ return "Exit"; }
    @Override public String getLanguageTooltip() { return "Switch Language"; }
    @Override public String getHelpTooltip(){ return "Help"; }
    @Override public String menuStartNewGame() { return "New Game"; }
    @Override public String menuContinueGame()  { return "Continue"; }
    @Override public String menuExitGame()      { return "Exit"; }
    //@Override public String menuWelcome(String u) { return "Welcome, " + u; }
    @Override public String menuExitConfirmTitle()   { return "Exit"; }
    @Override public String menuExitConfirmMessage() { return "Are you sure you want to exit?"; }
    @Override public String WarningTitle()   { return "Warning"; }
    @Override public String menuNewGameWarningMessage() { return "Previous progress may be lost!\nStart a new game anyway?"; }
    @Override public String menuSaveNotFound()       { return "Save not found!"; }
    //@Override public String menuSaveNotFoundTitle()  { return "Info"; }
    @Override public String chooseDifficulty() { return "Please choose difficulty"; }
    @Override public String difficultyTitle() { return "Difficulty Choice"; }

    //Main
    @Override public String gameSave()           { return "Save Game"; }
    @Override public String gameTitle(){return "Summer Challenge";}
    @Override public String fail() {
        return "Time's up! You lose.\nWhat's your next action?";
    }
    @Override public String failTitle(){return "Fail";}
    @Override public String win() {
        return "🎉 You win!🎉\nWhat's your next action?";
    }
    @Override public String winTitle(){return "Win";}
    @Override public String ready(){return "Ready...";}
    @Override public String score(int sc){
        return "Score: "+Integer.toString(sc);
    }

    @Override public String yes() { return "Yes"; }
    @Override public String no()  { return "No"; }
    @Override public String easy() { return "Easy"; }
    @Override public String hard() { return "Hard"; }
    @Override public String restart(){return "Retry";};
    @Override public String new_game(){return "Start new game";};
    @Override public String end(){return "Exit";};
    @Override public String start(){return "START";}
}