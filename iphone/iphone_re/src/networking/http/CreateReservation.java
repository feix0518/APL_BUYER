/*    */ package networking.http;
/*    */ 
/*    */ import iphonebuyer.Getor;
/*    */ import iphonebuyer.Log;
/*    */ import iphonebuyer.User;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.IOException;
/*    */ import java.net.SocketTimeoutException;
/*    */ import java.net.UnknownHostException;
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
import java.util.Map;
/*    */ import java.util.Map.Entry;
import java.util.Set;
/*    */ 
/*    */ public class CreateReservation
/*    */   implements HttpResponseProcessor
/*    */ {
/*    */   private String reserveUrl;
/*    */   private Getor getr;
/*    */   private String url;
/*    */   private String referUrl;
/*    */   private String cookie;
/*    */   public static final int CREATE_RESERVATION_OK = 1;
/*    */   public static final int CREATE_RESERVATION_SESSION_TIMEOUT = 2;
/*    */   public static final int CREATE_RESERVATION_NET_ERROR = 3;
/* 42 */   private int status = 3;
/*    */ 
/*    */   public CreateReservation(Getor getr, String URL, String cookie, String referUrl) {
/* 45 */     this.getr = getr;
/* 46 */     this.url = URL;
/* 47 */     this.cookie = cookie;
/* 48 */     this.referUrl = referUrl;
/*    */   }
/*    */   public int createReservation() {
/* 51 */     HashMap headers = new HashMap(5);
/* 52 */     headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
/* 53 */     headers.put("Accept-Language", "zh-cn,zh;q=0.5");
/*    */ 
/* 55 */     headers.put("Accept-Encoding", "gzip, deflate");
/* 56 */     headers.put("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
/* 57 */     headers.put("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.13) Gecko/20101203 ZiiMeet v2.00.07 Firefox/3.6.13 ( .NET CLR 3.5.30729)"); headers.put("Connection", "Keep-Alive");
/* 58 */     headers.put("Cookie", this.cookie);
/* 59 */     HttpGet getor = new HttpGet(this.getr, this.url, headers, this);
/* 60 */     getor.doRequest();
/* 61 */     return this.status;
/*    */   }
/*    */   public String getLoginHttpsUrl() {
/* 64 */     return this.reserveUrl;
/*    */   }
/*    */ 
/*    */   public void processRespose(Object body) {
/* 68 */     if (body != null) {
/* 69 */       HttpResponse res = (HttpResponse)body;
/* 70 */       Log.println(this.getr.getUser().name + "-------after make reservation------");
/* 71 */       Log.println(res.body);
/* 72 */       if (res.body != null) {
/* 73 */         StringBuffer sb = (StringBuffer)res.body;
/* 74 */         if ((sb.indexOf("您的会话已超时") > 0) || (sb.indexOf("Unable to connect to server") >= 0) || (sb.indexOf("请再试一次") > 0))
/*    */         {
/* 76 */           this.status = 2;
/* 77 */           return;
/*    */         }
/*    */       }
/* 80 */       Set set = res.header;
/* 81 */       if (set != null) {
/* 82 */         Iterator it = set.iterator();
/* 83 */         while (it.hasNext()) {
/* 84 */           Map.Entry entry = (Map.Entry)it.next();
/* 85 */           Object key = entry.getKey();
/* 86 */           if (key != null) {
/* 87 */             Log.println(key);
/* 88 */             Log.println(entry.getValue());
/* 89 */             if (key.toString().toLowerCase().equals("location")) {
/* 90 */               String value = entry.getValue().toString();
/* 91 */               value = value.substring(1, value.length() - 1);
/* 92 */               this.reserveUrl = value;
/* 93 */               this.status = 1;
/* 94 */               break;
/*    */             }
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   public void processFileNotFoundException(FileNotFoundException e)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void processUnknownHostException(UnknownHostException e)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void processSocketTimeoutException(SocketTimeoutException e)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void processIOException(IOException e)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void processException(Exception e)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void processConnectionClosed()
/*    */   {
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Administrator\桌面\5-22\3\
 * Qualified Name:     networking.http.CreateReservation
 * JD-Core Version:    0.6.0
 */