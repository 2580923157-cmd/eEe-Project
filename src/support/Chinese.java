package support;

public enum Chinese implements Language{
    INSTANCE;

    //Login
    @Override public String loginTitle0() {return "夏日大挑战 - 登录";}
    @Override public String loginTitle1() {return "欢迎来到";    }
    @Override public String loginTitle2() {return "夏 日 大 挑 战";    }
    @Override public String loginSubtitle() {return "请先登录或注册";}
    @Override public String loginLogin() { return "登录";    }
    @Override public String loginReg() {return "注册";    }
    @Override public String loginExit() {return "退出游戏";}
    @Override public String loginGuest(){return "游客登录";}
    @Override public String loginUsernameLabel()  { return "用户名："; }
    @Override public String loginPasswordLabel()  { return "密　码："; }
    @Override public String loginFailTitle(){ return "登录失败"; }
    @Override public String loginRegFailTitle(){ return "注册失败"; }
    @Override public String loginRegSuccessTitle(){ return "注册"; }
    @Override public String loginGuestMsgTitle() { return "提示"; }
    @Override public String loginEmptyFields(){ return "用户名和密码不能为空！"; }
    @Override public String loginSuccess(String user){ return "登录成功！欢迎您， " + user; }
    @Override public String loginFail(){ return "用户名或密码错误！"; }
    @Override public String loginCommaError(){ return "用户名不能包含逗号！"; }
    @Override public String loginRegSuccess(){ return "注册成功！请登录"; }
    @Override public String loginRegFail(){ return "用户名已存在，请更换！"; }
    @Override public String loginGuestMsg(){ return "以游客身份进入游戏"; }


    //Menu
    @Override public String menuTitle() { return "夏日大挑战 - 主菜单"; }
    @Override public String menuWelcome(String username) { return "欢迎您，" + username; }
    @Override public String menuStartGame() { return "开始游戏"; }
    @Override public String menuLevelSelect() { return "关卡选择"; }
    @Override public String menuExit() { return "退出游戏"; }
    @Override public String getLanguageTooltip() { return "切换语言"; }
    @Override public String getHelpTooltip() { return "帮助"; }
    @Override public String menuStartNewGame() { return "开始新游戏"; }
    @Override public String menuContinueGame()  { return "继续游戏"; }
    @Override public String menuExitGame()      { return "退出游戏"; }
    //@Override public String menuWelcome(String u) { return "欢迎，" + u; }
    @Override public String menuExitConfirmTitle()   { return "退出"; }
    @Override public String menuExitConfirmMessage() { return "确定要退出吗？"; }
    @Override public String WarningTitle()   { return "警告"; }
    @Override public String menuNewGameWarningMessage() { return "之前的进度可能丢失！\n确定要开始新游戏吗？"; }
    @Override public String menuSaveNotFound()       { return "存档不存在！"; }
    //@Override public String menuSaveNotFoundTitle()  { return "提示"; }
    @Override public String chooseDifficulty() { return "请选择难度"; }
    @Override public String difficultyTitle() { return "难度选择"; }

    //Main
    @Override public String gameSave() {
        return "保存游戏";
    }
    @Override public String gameTitle(){return "夏日大挑战";}
    @Override public String fail() {
        return "时间用完了！游戏结束。\n下一步操作是？";
    }
    @Override public String failTitle(){return "失败";}
    @Override public String win() {
        return "🎉 恭喜通关！🎉\n下一步操作是？";
    }
    @Override public String winTitle(){return "胜利";}
    @Override public String ready(){return "准备...";}
    @Override public String score(int sc){
        return "分数："+Integer.toString(sc);
    }

    @Override public String audioOnTooltip(){ return "播放音乐"; }
    @Override public String audioOffTooltip() { return "停止音乐"; }
    @Override public String pauseTooltip(){ return "暂停"; }
    @Override public String continueTooltip() { return "继续"; }
    @Override public String retryTooltip(){ return "重新开始"; }
    @Override public String undoTooltip(){ return "回到上一步"; }
    @Override public String saveTooltip(){ return "保存"; }
    @Override public String exitTooltip(){ return "退出"; }


    @Override public String yes() { return "是"; }
    @Override public String no()  { return "否"; }
    @Override public String easy() { return "简单"; }
    @Override public String hard() { return "困难"; }
    @Override public String restart(){return "重新开始";};
    @Override public String new_game(){return "开始新一关";};
    @Override public String end(){return "退出";};
    @Override public String start(){return "开始";}
}
