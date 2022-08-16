package com.xxxx.server.enmu;
public class MusicType {
    //后续了解到可以继续添加
    private static String[] TYPE={".wav",".mp3",".m4a"};
    public static boolean isContain(String type){
        for (String s:TYPE
             ) {
            if(s.equals(type))
                return true;
        }
        return false;
    }
}
