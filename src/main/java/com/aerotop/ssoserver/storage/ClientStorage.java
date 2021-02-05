package com.aerotop.ssoserver.storage;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @ClassName: ClientStorage
 * @Description: 存储客户端全部会话
 * @Author: gaosong
 * @Date 2021/2/2 13:47
 */
public enum ClientStorage {
    /**用来调用方法*/
    INSTANCE;
    /**局部会话存储集合,key代表token,value代表HttpSession对象*/
    private Map<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();

    public void set(String token, String url) {
        if (!map.containsKey(token)) {
            ArrayList<String> list = new ArrayList<>();
            list.add(url);
            map.put(token, list);
            return;
        }
        map.get(token).add(url);
    }

    public List<String> destroyClientToken(String token) {
        if (map.containsKey(token) && token!=null) {
            Iterator<Map.Entry<String, ArrayList<String>>> it = map.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry<String, ArrayList<String>> entry=it.next();
                String key=entry.getKey();
                if(token.equals(key)){
                    it.remove();
                    return entry.getValue();
                }
            }
        }
        return null;
    }
}
