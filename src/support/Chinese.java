package support;

public enum Chinese implements Language{
    INSTANCE;

    @Override public String getTitle() { return "夏日大挑战 - 主菜单"; }
    @Override public String getWelcome(String username) { return "欢迎，" + username; }
    @Override public String getStartGame() { return "开始游戏"; }
    @Override public String getLevelSelect() { return "关卡选择"; }
    @Override public String getExitGame() { return "退出游戏"; }
    @Override public String getLanguageTooltip() { return "切换语言"; }
    @Override public String getHelpTooltip() { return "帮助"; }

}
