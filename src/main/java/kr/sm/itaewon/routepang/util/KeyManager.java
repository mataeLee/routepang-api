package kr.sm.itaewon.routepang.util;

import java.util.Arrays;

public class KeyManager {

    private static final String chat = "dm";

    public String chatKeyEncrypt(long[] ids){

        String key = chat + ":";
        Arrays.sort(ids);
        for(long id : ids){
            key += id;
            key += ".";
        }
        return "";
    }

    public long[] chatKeyDecrypt(String box){
        String[] text = box.split(":");
        if(text[0].equals("dm")){
            String[] id = text[1].split(".");
            long[] ids = new long[id.length];
            for(int i=0;i<id.length;i++){
                ids[i] = Long.parseLong(id[i]);
            }
            return ids;
        }
        else return null;
    }
}