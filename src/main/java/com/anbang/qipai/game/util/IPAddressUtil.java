package com.anbang.qipai.game.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

public class IPAddressUtil {
    public static String getIPAddress1(String loginIP){
        String host = "http://iploc.market.alicloudapi.com";
        String path = "/v3/ip";
        String method = "GET";
        String appcode = "f00ea85a042c4404acaa87c121fc0079";
        Map<String, String> headers = new HashMap<String, String>();
        // 最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("ip", loginIP);

        try {
            HttpResponse response = HttpUtil.doGet(host, path, method, headers, querys);
            String entity = EntityUtils.toString(response.getEntity());
            Map map = JSON.parseObject(entity, Map.class);
            String status = (String) map.get("status");
            String info = (String) map.get("info");
            String infocode = (String) map.get("infocode");
            String province = (String) map.get("province");
            String adcode = (String) map.get("adcode");
            String city = (String) map.get("city");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getIPAddress2 (String loginIP) {
        String host = "http://ipquery.market.alicloudapi.com";
        String path = "/query";
        String method = "GET";
        String appcode = "47582084c54449c3b4ee5351a087aafe";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("ip", loginIP);

        try {
            HttpResponse response = HttpUtil.doGet(host, path, method, headers, querys);
            String entity = EntityUtils.toString(response.getEntity());
            Map map = JSON.parseObject(entity, Map.class);
            JSONObject data = (JSONObject) map.get("data");
            String prov = (String)data.get("prov");
            String city = (String)data.get("city");
            String isp = (String)data.get("isp");
            return prov + city + isp;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
