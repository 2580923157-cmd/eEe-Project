package support;

public enum English implements Language {
    INSTANCE;

    @Override public String getTitle() { return "Summer Challenge - Main Menu"; }
    @Override public String getWelcome(String username) { return "Welcome, " + username; }
    @Override public String getStartGame() { return "Start Game"; }
    @Override public String getLevelSelect() { return "Level Select"; }
    @Override public String getExitGame() { return "Exit"; }
    @Override public String getLanguageTooltip() { return "Change Language"; }
    @Override public String getHelpTooltip() { return "Help"; }
}