package model;

import model.Cell;

import java.io.*;
import java.util.ArrayList;

public class SaveManager {

    private static final String SAVE_FOLDER = "saves";//文件夹

    public SaveManager(){
        File folder = new File(SAVE_FOLDER);
        if(!folder.exists()){
            folder.mkdirs();//创建文件夹
        }
    }

    private String getPath(String userName){
        return SAVE_FOLDER + File.separator + userName + ".txt";
    }//获取存档路径

    public void save(String userName, GameState state){
        String path = getPath(userName);
        Cell[][] board = state.getBoard();;
        try(PrintWriter pw = new PrintWriter(new FileWriter(path))){
            pw.println(state.getHours() + ", " + state.getMinutes() + ", " + state.getSeconds() + "+" + state.getScore());
            //时间 分数
            for (int i = 0; i < board.length; i++) {
                for (int i1 = 0; i1 < board[i].length; i1++) {
                    pw.print(board[i][i1].getIconIndex() + " ");
                }
                pw.println();
            }
            //存棋盘
        }catch(IOException e){
            System.out.println("存档失败" + e.getMessage());
        }
    }

    public GameState load(String userName){
         String path = getPath(userName);
         File file = new File(path);
         if(!file.exists()) return null;

        ArrayList<String> lines = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            String line;//br.readline()要先被存起来
            while((line = br.readLine()) != null){
                line = line.trim();
                if(!line.isEmpty()){
                    lines.add(line);
                }
            }
        }catch(IOException e){
            System.out.println(e.getMessage());
            return null;
        }

        if(lines.size()<=1) return null;//棋盘都没有

        //读取第一行：时间
        String timeLine = lines.get(0);
        String[] timeArr = timeLine.split(", ");//save时时间用,分隔
        int hour = Integer.parseInt(timeArr[0]);
        int minute = Integer.parseInt(timeArr[1]);
        int  second = Integer.parseInt(timeArr[2]);
        int score = Integer.parseInt(timeArr[3]);

        //读棋盘部分 即lines.get(i) i>=1
        ArrayList<String> boradLines = new ArrayList<>();//虽然iconIndex是int 但是读取到file之后就变成String
        for(int  i=1;i<lines.size();i++){
            boradLines.add(lines.get(i));
        }

        //接下来计算棋盘的行列 该功能可能在棋盘大小随机生成的场景能用到
        int row = boradLines.size();
        int col = boradLines.get(0).split(" ").length;//save时cell用空格分隔
        Cell[][] board = new Cell[row][col];
        for (int i = 0; i < board.length; i++) {
            String[] inFo = boradLines.get(i).split(" ");
            for (int i1 = 0; i1 < board[i].length; i1++) {
                int iconIndex = Integer.parseInt(inFo[i]);
                boolean isEmpty = (iconIndex == 0);
                board[i][i1] =  new Cell(new Position(i,i1), isEmpty, iconIndex);
            }
        }

        //读完档return state
        GameState state = new GameState();
        state.setBoard(board);
        state.setScore(score);
        state.setSeconds(second);
        state.setMinutes(minute);
        state.setHours(hour);
        return state;
    }
}
