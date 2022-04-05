package org.yibanPunchCard.service;

import org.springframework.stereotype.Component;
import org.yibanPunchCard.entity.Result;
import org.yibanPunchCard.utils.OpUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;

@Component
public class PunchService {
    private static final String URLGET = "http://api.xg.bjwlxy.cn/v1/yqsb/jkrb/getlist";
    private static final String URLADD = "http://api.xg.bjwlxy.cn/v1/yqsb/jkrb/add";
    private static final String URLSEND = "http://pushplus.hxtrip.com/send";
    public static Result QueryStudent(String xuehao) {
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        try {
            String urlNameString = URLGET + "?" + "xh="+xuehao;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            //Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            /*for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }*/
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return Result.resolve(result.toString());
    }
    public static Result PunchCard(String xuehao) {
        String json = "{\"id\":null,\"xh\":\""+xuehao+"\",\"sbdate\":\"当天\",\"tw\":0,\"sfyc\":\"正常\",\"sfks\":\"否\",\"sffl\":\"否\",\"qtzz\":\"\",\"sfjy\":\"否\",\"jydd\":\"\",\"zdjg\":\"\",\"zzsfgs\":\"\",\"czqk\":\"\",\"bz\":null,\"coordinate\":\""+ OpUtil.randomLocation()+"\",\"address\":\"陕西省 宝鸡市 渭滨区 宝光路 44号 靠近宝鸡文理学院 \",\"dqdm\":\"610302\"}";
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
//        System.out.println(json);
        try {
                URL realUrl = new URL(URLADD);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Connection", "keep-alive");
            conn.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Linux; Android 12; M2012K11AC Build/SKQ1.211006.001; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/95.0.4638.74 Mobile Safari/537.36 yiban_android/5.0.8");
            conn.setRequestProperty("X-Requested-With", "com.yiban.app");
            conn.setRequestProperty("X-Requested-With", "com.yiban.app");
            conn.setRequestProperty("Accept", "application/json, text/plain, */*");
            conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
            conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7");
            conn.setRequestProperty("Referer", "http://mobile.xg.bjwlxy.cn/");
            conn.setRequestProperty("Origin", "http://mobile.xg.bjwlxy.cn");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(json);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return Result.resolve(result.toString());
    }

    public static Result sendFallBack(String token,String xuehao,Result rs) {
        String json = "{\n" +
                "    \"token\": \""+token+"\",\n" +
                "    \"title\": \""+xuehao+"的打卡\",\n" +
                "    \"content\": \"您的打卡"+(rs.getCode()==200?"成功！":"失败！")+"原因："+rs.getMessage()+"\",\n" +
                "  }";
        System.out.println(rs);
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
//        System.out.println(json);
        try {
            URL realUrl = new URL(URLSEND);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Connection", "keep-alive");
            conn.setRequestProperty("Accept", "application/json, text/plain, */*");
            conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(json);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return Result.resolve(result.toString());
    }

    public static void punch(){
        Objects.requireNonNull(OpUtil.readJsonFile("config.json")).forEach(student -> {
            sendFallBack(student.getPushToken(), student.getXuehao(), PunchCard(student.getXuehao()));
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
