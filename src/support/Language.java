package support;

public interface Language {
    //Login
    String loginTitle();
    String loginLogin();
    String loginReg();
    String loginExit();
    String loginGuest();

    //Menu
    String menuTitle();
    String menuWelcome(String username);
    String menuStartGame();
    String menuLevelSelect();
    String menuExit();
    String getLanguageTooltip();
    String getHelpTooltip();
    // 可根据需要继续扩展其他界面文本

    //Main
    String gameSave();
}