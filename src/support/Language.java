package support;

public interface Language {
    //Login
    String loginTitle0();   //界面标签
    String loginTitle1();
    String loginTitle2();
    String loginSubtitle();
    String loginLogin();
    String loginReg();
    String loginExit();
    String loginGuest();
    String loginUsernameLabel();
    String loginPasswordLabel();
    String loginFailTitle();
    String loginRegFailTitle();
    String loginRegSuccessTitle();
    String loginGuestMsgTitle();
    String loginEmptyFields();
    String loginSuccess(String username);
    String loginFail();
    String loginCommaError();
    String loginRegSuccess();
    String loginRegFail();
    String loginGuestMsg();

    //Menu
    String menuTitle();
    String menuWelcome(String username);
    String menuStartGame();
    String menuLevelSelect();
    String menuExit();
    String getLanguageTooltip();
    String getHelpTooltip();
    // 可根据需要继续扩展其他界面文本
    // Menu 界面文本
    String menuStartNewGame();          // 开始新游戏
    String menuContinueGame();          // 继续游戏
    String menuExitGame();              // 退出游戏
    //String menuWelcome(String username);
    String menuExitConfirmTitle();      // 退出确认标题
    String menuExitConfirmMessage();    // 确定要退出吗？
    String WarningTitle();   // 进度警告标题
    String menuNewGameWarningMessage(); // 之前进度可能丢失，确定开始新游戏？
    String menuSaveNotFound();          // 存档不存在
    //String menuSaveNotFoundTitle();     // 提示
    String chooseDifficulty();
    String difficultyTitle();
    //Main
    String gameSave();


    //General
    // 通用按钮文字
    String yes();  // 是 / Yes
    String no();   // 否 / No
    String easy();
    String hard();

}