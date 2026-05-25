package support;

public interface Language {
    String getTitle();
    String getWelcome(String username);
    String getStartGame();
    String getLevelSelect();
    String getExitGame();
    String getLanguageTooltip();
    String getHelpTooltip();
    // 可根据需要继续扩展其他界面文本
}