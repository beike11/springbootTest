package com.stevenw.demo.base;

import com.stevenw.demo.util.SslUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 无符号右移
 * @author stevenw
 * @date 2019/4/9
 */
public class URShift {
    public final static String POST = "POST";

    public static void main(String[] args) throws Exception {
       /* int a = 1000;
        System.out.println(Integer.toBinaryString(a));
        a >>= 2;
        System.out.println("a：" + a);
        System.out.println(Integer.toBinaryString(a));
        a >>= 1;
        System.out.println("a：" + a);
        System.out.println(Integer.toBinaryString(a));*/
//        System.out.println(Integer.toBinaryString(3));
//        System.out.println(Integer.toBinaryString(9));
//        System.out.println(Integer.toBinaryString(11));
//        System.out.println(3 | 1);
        PrintWriter writer = null;
        HttpURLConnection conn = null;
        BufferedReader in = null;
        URL url = new URL("https://localhost:443/tpgerp/sys/saveorghistoryforsync");
        if("https".equalsIgnoreCase(url.getProtocol())){
            SslUtils.ignoreSsl();
        }
        conn = (HttpURLConnection)url.openConnection();
        setHttpUrlConnection(conn,POST);

        conn.connect();
        writer = new PrintWriter(conn.getOutputStream());
        writer.print("");
        writer.flush();
        String result =  readResponseContent(conn.getInputStream());
        System.err.println(result);
    }

    /**
     * 设置请求通用信息
     * @param conn
     * @param requestMethod
     * @throws ProtocolException
     */
    private static void setHttpUrlConnection(HttpURLConnection conn, String requestMethod) throws ProtocolException {
        conn.setRequestMethod(requestMethod);
        conn.setRequestProperty("accept", "*/*");
        conn.setRequestProperty("Accept-Language", "zh-CN");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)");
        conn.setRequestProperty("Proxy-Connection", "Keep-Alive");
        if (null != requestMethod && POST.equals(requestMethod)) {
            conn.setDoOutput(true);
            conn.setDoInput(true);
        }
    }

    /**
     * 读取response内容
     * @param in
     * @return
     * @throws IOException
     */
    private static String readResponseContent(InputStream in) throws IOException {
        Reader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            reader = new InputStreamReader(in);
            char[] buffer = new char[1024];
            int head = 0;
            while ((head = reader.read(buffer)) > 0) {
                content.append(new String(buffer, 0, head));
            }
            return content.toString();
        } finally {
            if (null != in) {
                in.close();
            }
            if (null != reader) {
                reader.close();
            }
        }
    }

    /**
     * 算法，根据结果找到集合中的两数（两数和）
     * @param arr
     * @param target
     * @return
     */
    public int[] getTowSum(int [] arr, int target){
        Map<Integer,Integer> tempMap = new HashMap();
        for (int i = 0; i < arr.length; i++) {
            int temoInt = target - arr[i];
            if(tempMap.containsKey(temoInt)){
                return new int[]{i,tempMap.get(temoInt)};
            }
            tempMap.put(arr[i],i);
        }
        return  null;
    }
}
