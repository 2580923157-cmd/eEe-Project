package support;

public enum TraditionalChinese implements Language{
    INSTANCE;

    //Login
    @Override public String loginTitle0() {return "夏日大挑戰 - 登錄";}
    @Override public String loginTitle1() {return "歡迎來到"; }
    @Override public String loginTitle2() {return "夏 日 大 挑 戰"; }
    @Override public String loginSubtitle() {return "請先登錄或註冊";}
    @Override public String loginLogin() { return "登錄"; }
    @Override public String loginReg() {return "註冊"; }
    @Override public String loginExit() {return "退出遊戲";}
    @Override public String loginGuest(){return "遊客登錄";}
    @Override public String loginUsernameLabel() { return "用戶名："; }
    @Override public String loginPasswordLabel() { return "密　碼："; }
    @Override public String loginFailTitle(){ return "登錄失敗"; }
    @Override public String loginRegFailTitle(){ return "註冊失敗"; }
    @Override public String loginRegSuccessTitle(){ return "註冊"; }
    @Override public String loginGuestMsgTitle() { return "提示"; }
    @Override public String loginEmptyFields(){ return "用戶名和密碼不能爲空！"; }
    @Override public String loginSuccess(String user){ return "登錄成功！歡迎您， " + user; }
    @Override public String loginFail(){ return "用戶名或密碼錯誤！"; }
    @Override public String loginCommaError(){ return "用戶名不能包含逗號！"; }
    @Override public String loginRegSuccess(){ return "註冊成功！請登錄"; }
    @Override public String loginRegFail(){ return "用戶名已存在，請更換！"; }
    @Override public String loginGuestMsg(){ return "以遊客身份進入遊戲"; }


    //Menu
    @Override public String menuTitle() { return "夏日大挑戰 - 主菜單"; }
    @Override public String menuWelcome(String username) { return "歡迎您，" + username; }
    @Override public String menuStartGame() { return "開始遊戲"; }
    @Override public String menuLevelSelect() { return "關卡選擇"; }
    @Override public String menuExit() { return "退出遊戲"; }
    @Override public String getLanguageTooltip() { return "切換語言"; }
    @Override public String getHelpTooltip() { return "幫助"; }
    @Override public String menuStartNewGame() { return "開始新遊戲"; }
    @Override public String menuContinueGame() { return "繼續遊戲"; }
    @Override public String menuExitGame() { return "退出遊戲"; }
    //@Override public String menuWelcome(String u) { return "歡迎，" + u; }
    @Override public String menuExitConfirmTitle() { return "退出"; }
    @Override public String menuExitConfirmMessage() { return "確定要退出嗎？"; }
    @Override public String WarningTitle() { return "警告"; }
    @Override public String menuNewGameWarningMessage() { return "之前的進度可能丟失！\n確定要開始新遊戲嗎？"; }
    @Override public String menuSaveNotFound() { return "存檔不存在！"; }
    //@Override public String menuSaveNotFoundTitle() { return "提示"; }
    @Override public String chooseDifficulty() { return "請選擇難度"; }
    @Override public String difficultyTitle() { return "難度選擇"; }

    //Main
    @Override public String gameSave() {
        return "保存遊戲";
    }
    @Override public String gameTitle(){return "夏日大挑戰";}
    @Override public String fail() {
        return "時間用完了！遊戲結束。\n下一步操作是？";
    }
    @Override public String failTitle(){return "失敗";}
    @Override public String win() {
        return "🎉 恭喜通關！🎉\n下一步操作是？";
    }
    @Override public String winTitle(){return "勝利";}
    @Override public String ready(){return "準備...";}
    @Override public String score(int sc){
        return "分數："+Integer.toString(sc);
    }

    @Override public String audioOnTooltip(){ return "播放音樂"; }
    @Override public String audioOffTooltip() { return "停止音樂"; }
    @Override public String pauseTooltip(){ return "暫停"; }
    @Override public String continueTooltip() { return "繼續"; }
    @Override public String retryTooltip(){ return "重新開始"; }
    @Override public String undoTooltip(){ return "回到上一步"; }
    @Override public String saveTooltip(){ return "保存"; }
    @Override public String exitTooltip(){ return "退出"; }


    @Override public String yes() { return "是"; }
    @Override public String no() { return "否"; }
    @Override public String easy() { return "簡單"; }
    @Override public String hard() { return "困難"; }
    @Override public String restart(){return "重新開始";};
    @Override public String new_game(){return "開始新一關";};
    @Override public String end(){return "退出";};
    @Override public String start(){return "開始";}
}
