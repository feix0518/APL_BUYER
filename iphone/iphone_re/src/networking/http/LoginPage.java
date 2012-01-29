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
/*    */ 
/*    */ public class LoginPage
/*    */   implements HttpResponseProcessor
/*    */ {
/*    */   public static final int LOGIN_PAGE_OK = 1;
/*    */   public static final int LOGIN_PAGE_SESSION_TIMEOUT = 2;
/*    */   public static final int LOGIN_PAGE_NET_ERROR = 3;
/* 31 */   private int status = 3;
/*    */   private Getor getr;
/*    */   private String postUrl;
/*    */   private String para1;
/*    */   private String para2;
/*    */   private String para3;
/*    */ 
/*    */   public int getPage(Getor getr, String httpsUrl, String cookie)
/*    */   {
/* 38 */     this.getr = getr;
/* 39 */     HashMap headers = new HashMap(5);
/* 40 */     headers.put("Accept", "*/*");
/*    */ 
///* 42 */     headers.put("Accept-Language", "zh-cn");
///* 43 */     headers.put("Accept-Encoding", "gzip, deflate");
///* 44 */     headers.put("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1) ;  Embedded Web Browser from: http://bsalsa.com/; song2009 Embedded Web Browser from: http://bsalsa.com/; WebSaver; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.648; .NET CLR 3.5.21022; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; AskTB5.6; ZiiMeet v2.00.07)");
///* 45 */     headers.put("Content-Type", "application/x-www-form-urlencoded");
///* 46 */     headers.put("Connection", "Keep-Alive");
///* 47 */     headers.put("Keep-Alive", "300");
///* 48 */     headers.put("Cache-Control", "no-cache");
headers.put("Host", "reserve.apple.com");
headers.put("User-Agent", "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/534.51.22 (KHTML, like Gecko) Version/5.1.1 Safari/534.51.22");
headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
headers.put("Accept-Encoding", "gzip, deflate");
headers.put("Accept-Language", "ja-JP");
headers.put("Referer", "http://reserve.apple.com/WebObjects/ProductReservation.woa/wo/17.");
headers.put("Connection", "keep-alive");
headers.put("Proxy-Connection", "keep-alive");
/* 49 */     headers.put("Cookie", cookie);
/* 50 */     HttpGet getor = new HttpGet(getr, httpsUrl, headers, this);
/* 51 */     getor.doRequest();
/* 52 */     return this.status;
/*    */   }
/*    */ 
/*    */   public String getPostUrl() {
/* 56 */     return this.postUrl;
/*    */   }
/*    */   public String getPara1() {
/* 59 */     return this.para1;
/*    */   }
/*    */   public String getPara2() {
/* 62 */     return this.para2;
/*    */   }
/*    */   public String getPara3() {
/* 65 */     return this.para3;
/*    */   }
/*    */ 
/*    */   public void processRespose(Object body) {
/* 69 */     if (body != null) {
/* 70 */       HttpResponse res = (HttpResponse)body;
/*    */ 
/* 72 */       StringBuffer sb = (StringBuffer)res.body;
/* 73 */       Log.println(this.getr.getUser().name + "------------get login page-------------");
/* 74 */       Log.println(sb);
               String html = sb.toString();
/* 75 */       int index = sb.indexOf("id=\"TheForm\"");
/* 76 */       if (index >= 0) {
/* 77 */         index = sb.indexOf("/WebObjects", index + 1);
/* 78 */         if (index >= 0) {
/* 79 */           int lastIndex = sb.indexOf("\"", index + 1);
/* 80 */           if (lastIndex > 0) {
/* 81 */             this.postUrl = sb.substring(index + 1, lastIndex);
/* 82 */             this.status = 1;
/*    */           }
/*    */         }
/*    */ 
/*    */       }
/*    */ 
/* 88 */       String match = "id=\"appleid\"";
/* 89 */       index = sb.indexOf(match);
/* 90 */       if (index > 0) {
/* 91 */         index = sb.indexOf("name", index + 1);
/* 92 */         if (index > 0) {
/* 93 */           index = sb.indexOf("\"", index + 1);
/* 94 */           int lastindex = sb.indexOf("\"", index + 1);
/* 95 */           this.para1 = sb.substring(index + 1, lastindex);
/*    */         }
/*    */       }
/*    */ 
/* 99 */       match = "id=\"password\"";
/* 100 */       index = sb.indexOf(match, index + 1);
/* 101 */       if (index > 0) {
/* 102 */         index = sb.indexOf("name", index + 1);
/* 103 */         if (index > 0) {
/* 104 */           index = sb.indexOf("\"", index + 1);
/* 105 */           int lastindex = sb.indexOf("\"", index + 1);
/* 106 */           this.para2 = sb.substring(index + 1, lastindex);
/*    */         }
/*    */       }
/*    */ 
/* 110 */       match = "type=\"submit\"";
/* 111 */       index = sb.indexOf(match, index + 1);
/* 112 */       if (index > 0) {
/* 113 */         index = sb.indexOf("name", index + 1);
/* 114 */         if (index > 0) {
/* 115 */           index = sb.indexOf("\"", index + 1);
/* 116 */           int lastindex = sb.indexOf("\"", index + 1);
/* 117 */           this.para3 = sb.substring(index + 1, lastindex);
/*    */         }
/*    */ 
/*    */       }
/*    */ 
/* 122 */       if ((sb.indexOf("您的会话已超时") >= 0) || (sb.indexOf("Unable to connect to server") >= 0) || (sb.indexOf("请再试一次") > 0))
/*    */       {
/* 125 */         Log.println(sb);
/* 126 */         this.status = 2;
/* 127 */         return;
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
 * Qualified Name:     networking.http.LoginPage
 * JD-Core Version:    0.6.0
 */