package support;

public enum Chinese implements Language{
    INSTANCE;

    @Override
    public String loginTitle() {
        return "";
    }

    @Override
    public String loginLogin() {
        return "";
    }

    @Override
    public String loginReg() {
        return "";
    }

    @Override
    public String loginExit() {
        return "";
    }

    @Override
    public String loginGuest() {
        return "";
    }

    @Override public String menuTitle() { return "夏日大挑战 - 主菜单"; }
    @Override public String menuWelcome(String username) { return "欢迎，" + username; }
    @Override public String menuStartGame() { return "开始游戏"; }
    @Override public String menuLevelSelect() { return "关卡选择"; }
    @Override public String menuExit() { return "退出游戏"; }
    @Override public String getLanguageTooltip() { return "切换语言"; }
    @Override public String getHelpTooltip() { return "帮助"; }

    @Override
    public String gameSave() {
        return "";
    }


}
