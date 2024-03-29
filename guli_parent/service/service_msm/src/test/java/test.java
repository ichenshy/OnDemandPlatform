import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

/**
 * @author Chenchenx
 * @version v1.0
 * @date 2021/12/1220:08
 */
  public class test {
          public static void main(String[] args) {
              String host = "https://fesms.market.alicloudapi.com";// 【1】请求地址 支持http 和 https 及 WEBSOCKET
              String path = "/sms/";// 【2】后缀
              String appcode = "d2168814d9054786b3b336552b40beda"; // 【3】开通服务后 买家中心-查看AppCode
              String code = "123456"; // 【4】请求参数，详见文档描述
              String phone = "18364821270"; // 【4】请求参数，详见文档描述
              String sign = "1"; // 【4】请求参数，详见文档描述
              String skin = "1"; // 【4】请求参数，详见文档描述
              String urlSend = host + path + "?code=" + code + "&phone=" + phone + "&sign=" + sign + "&skin=" + skin ; // 【5】拼接请求链接
              try {
                  URL url = new URL(urlSend);
                  HttpURLConnection httpURLCon = (HttpURLConnection) url.openConnection();
                  httpURLCon.setRequestProperty("Authorization", "APPCODE " + appcode);// 格式Authorization:APPCODE
                  // (中间是英文空格)
                  int httpCode = httpURLCon.getResponseCode();
                  if (httpCode == 200) {
                      String json = read(httpURLCon.getInputStream());
                  } else {
                      Map<String, List<String>> map = httpURLCon.getHeaderFields();
                      String error = map.get("X-Ca-Error-Message").get(0);
                      if (httpCode == 400 && error.equals("Invalid AppCode `not exists`")) {
                          System.out.println("AppCode错误 ");
                      } else if (httpCode == 400 && error.equals("Invalid Url")) {
                          System.out.println("请求的 Method、Path 或者环境错误");
                      } else if (httpCode == 400 && error.equals("Invalid Param Location")) {
                          System.out.println("参数错误");
                      } else if (httpCode == 403 && error.equals("Unauthorized")) {
                          System.out.println("服务未被授权（或URL和Path不正确）");
                      } else if (httpCode == 403 && error.equals("Quota Exhausted")) {
                          System.out.println("套餐包次数用完 ");
                      } else {
                          System.out.println("参数名错误 或 其他错误");
                          System.out.println(error);
                      }
                  }
              } catch (MalformedURLException e) {
                  System.out.println("URL格式错误");
              } catch (UnknownHostException e) {
                  System.out.println("URL地址错误");
              } catch (Exception e) {
                  // 打开注释查看详细报错异常信息
                   e.printStackTrace();
              }

          }
          /*
           * 读取返回结果
           */
          private static String read(InputStream is) throws IOException {
              StringBuffer sb = new StringBuffer();
              BufferedReader br = new BufferedReader(new InputStreamReader(is));
              String line = null;
              while ((line = br.readLine()) != null) {
                  line = new String(line.getBytes(), "utf-8");
                  sb.append(line);
              }
              br.close();
              return sb.toString();
          }
      }
