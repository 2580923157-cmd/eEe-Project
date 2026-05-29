package support;

public enum English implements Language {
    INSTANCE;

    @Override public String menuTitle() { return "Summer Challenge - Main Menu"; }
    @Override public String menuWelcome(String username) { return "Welcome, " + username; }
    @Override public String menuStartGame() { return "Start Game"; }
    @Override public String menuLevelSelect() { return "Level Select"; }
    @Override public String menuExit() { return "Exit"; }
    @Override public String getLanguageTooltip() { return "Change Language"; }
    @Override public String getHelpTooltip() { return "Help"; }
}