package model;//文件读写

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserDAO {
    private static final String USER_HOME = System.getProperty("user.home");
    //系统用户主目录 C:\Users\用户名
    private static final String APP_DIR = USER_HOME + File.separator + "myGames";
    private static final String USER_FILE = APP_DIR + File.separator + "users.txt";
    private Map<String, String> userMap = new HashMap<>();//HashMap是Map接口最常用的实现类
    /*HashMap put(key, value) 把键值对存进Map 若key已经存在 会覆盖原来的value
              contains(key) 判断Map中有没有该key 返回true或false
              get(key) 根据key取出对应的value key不存在则返回null
     */

    public UserDAO(){
        File dir = new File(APP_DIR);
        if(!dir.exists()){
            dir.mkdir();
        }
        loadUsersFromFile();
    }

    private void loadUsersFromFile(){
        File file = new File(USER_FILE);
        if(!file.exists()){
            try{
                file.createNewFile();//若USER_FILE文件路径不存在就创建一个
            }catch(IOException e){
                System.err.println("创建用户失败" + e.getMessage());
            }
            return;
        }
        /*BufferedReader读文件 readLine()读一行
          BufferedWriter写文件 write()写文本 newLine()换行
         */
        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            String line;
            while((line = br.readLine() )!= null){//处理每一行数据
                line = line.trim();
                if(line.isEmpty()){
                    continue;
                }
                String[] parts = line.split(":",2);
                //username和password都在一个文本file里 读取时需要split
                if(parts.length == 2){
                    userMap.put(parts[0], parts[1]);
                }
            }
        }catch(IOException e){
            System.out.println("读取用户文件失败" + e.getMessage());
        }
    }
    /*try catch结构专门用于处理文件流
     IOException若出现输入输出异常   e.getMessage错误原因
     */

    public boolean registerUser(String userName, String password){
        //用户名已存在则不能注册
        if (userMap.containsKey(userName)) {
            return false;
        }

        userMap.put(userName, password);
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(USER_FILE,true))){
            //FileWriter中true表示追加模式 不覆盖原来文件内容只在文件末尾加新行
            bw.write(userName + ":" + password);
            bw.newLine();
            return true;
        }catch(IOException e){
            System.err.println("写入文件失败"  + e.getMessage());
            return false;
        }
    }

    //登录验证
    public boolean validateUser(String userName, String password){
        return userMap.containsKey(userName) && userMap.get(userName).equals(password);
    }

    //检查用户名是否存在
    public boolean isUserNameExists(String userName){
        return userMap.containsKey(userName);
    }

    public static String getAppDir(){
        return APP_DIR;
    }

}
