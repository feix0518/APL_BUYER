 package networking.http;
 
 import iphonebuyer.Getor;
 import iphonebuyer.Log;
 import iphonebuyer.User;
 import java.io.FileNotFoundException;
 import java.io.IOException;
 import java.net.SocketTimeoutException;
 import java.net.UnknownHostException;
 import java.util.HashMap;
 import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
 
 public class DoLogin
   implements HttpResponseProcessor
 {
   public static final int LOGIN_OK = 1;
   public static final int LOGIN_SESSION_TIMEOUT = 2;
   public static final int LOGIN_NET_ERROR = 3;
   public static final int LOGIN_BAD_ACCOUNT = 4;
   private int status = 3;
   private String ds01;
   private Getor getr;
   private String url;
   private String reUrl;
   private String lastUrl;
   private int count = 0;
   private String locationUrl;
 
   public int login(Getor getr, String url, String referUrl, String cookie, String appleid, String password, String para1, String para2, String para3)
   {
     this.getr = getr;
     HashMap headers = new HashMap(5);
     headers.put("Accept", "*/*");
     headers.put("Referer", referUrl);
     headers.put("Accept-Language", "zh-cn");
     headers.put("Accept-Encoding", "gzip, deflate, x-gzip, identity, *;q=0");
   headers.put("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
   headers.put("Content-Type", "application/x-www-form-urlencoded");
   headers.put("Connection", "Keep-Alive");
   headers.put("Keep-Alive", "300");
   headers.put("Cookie", cookie);
   Log.println(cookie);
   try
   {
     String content = para1 + "=" + appleid + "&" + para2 + "=" + password + "&" + para3 + "=true";
     
     System.out.println(content);
     HttpPost poster = new HttpPost(getr, url, headers, content, this);
     poster.doRequest(true);
   } catch (Exception e) {
     e.printStackTrace();
   }

   return this.status;
 }
 public String getToken() {
   return this.ds01;
 }
 public String getCodeImageUrl() {
   return this.url;
 }
 public String getReCodeImageUrl() {
   return this.reUrl;
 }
 public String getLastUrl() {
   return this.lastUrl;
 }
 public int getPhoneNum() {
   return this.count;
 }
 public String getLoaction() {
   return this.locationUrl;
 }

 public void processRespose(Object body) {
   if (body != null)
   {
     int index = -1;
     HttpResponse res = (HttpResponse)body;
     Log.println(this.getr.getUser().name + "-------after do prelogin------");
     Log.println(res.body);
     Log.println(res.header);
     String match = "action=\"/WebObjects/ProductReservation.woa/wo/";

      if (res.body != null) {
        StringBuffer sb = (StringBuffer)res.body;
        String sbHtml = sb.toString();
        //System.out.println(sbHtml);
       index = sb.indexOf(match);
       if (index > 0) {
         int lastIndex = sb.indexOf("\"", index + match.length());
         String referUrl = sb.substring(index + match.length(), lastIndex);
         int referIndex = Integer.parseInt(referUrl.substring(0, 2));
         referUrl = (referIndex - 1) + ".0" + referUrl.substring(4);
         this.locationUrl = "https://reserve.apple.com/WebObjects/ProductReservation.woa/wo/" + referUrl;
         this.lastUrl = ("WebObjects/ProductReservation.woa/wo/" + sb.substring(index + match.length(), lastIndex));
         Log.println("productSelectUrl=" + this.lastUrl);
       }
         if ((sb.indexOf("您的会话已超时") > 0) || (sb.indexOf("Unable to connect to server") >= 0) || (sb.indexOf("请再试一次") > 0)) {
           this.status = 2;
           return;
        }
      }
       Set set = res.header;
       if (set != null) {
         Iterator it = set.iterator();
         while (it.hasNext()) {
           Map.Entry entry = (Map.Entry)it.next();
           Object key = entry.getKey();
           if (key != null) {

             if (key.toString().toLowerCase().equals("location")) {
               String value = entry.getValue().toString();
               value = value.substring(1, value.length() - 1);
               this.locationUrl = value;
               if (this.ds01 != null) {
                 this.status = 1;
                 break;
              }
             } else if (key.toString().toLowerCase().equals("set-cookie")) {
               String value = entry.getValue().toString();
               value = value.substring(1, value.length() - 1);

               index = value.indexOf("ds01");
               if (index >= 0) {
                 value = value.substring(index, value.indexOf(";", index + 1));
                 this.ds01 = value;
                 //if (this.locationUrl != null) {
                   this.status = 1;
                   break;
                //}
              }
            }
          }
        }
      }
      //if (this.locationUrl == null)
      //   this.status = 4;
    }
  }

  public void processFileNotFoundException(FileNotFoundException e)
  {
  }

  public void processUnknownHostException(UnknownHostException e)
  {
  }

  public void processSocketTimeoutException(SocketTimeoutException e)
  {
  }

  public void processIOException(IOException e)
  {
  }

  public void processException(Exception e)
  {
  }

  public void processConnectionClosed()
  {
  }
}
