/*    */ package networking.http;
/*    */ 
/*    */ import iphonebuyer.Getor;
/*    */ import iphonebuyer.Log;
/*    */ import iphonebuyer.MainJFrame;
/*    */ import iphonebuyer.User;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.IOException;
/*    */ import java.net.SocketTimeoutException;
/*    */ import java.net.UnknownHostException;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class DoRealLogin
/*    */   implements HttpResponseProcessor
/*    */ {
/*    */   public static final int LOGIN_OK = 1;
/*    */   public static final int LOGIN_SESSION_TIMEOUT = 2;
/*    */   public static final int LOGIN_NET_ERROR = 3;
/*    */   public static final int LOGIN_BAD_ACCOUNT = 4;
/* 32 */   private int status = 3;
/*    */   private Getor getr;
/*    */   private String url;
/*    */   private String reUrl;
/*    */   private String lastUrl;
/* 41 */   private int count = 0;
/*    */ 
/*    */   public int login(Getor getr, String url, String referUrl, String cookie) {
/* 44 */     this.getr = getr;
/* 45 */     HashMap headers = new HashMap(5);
/* 46 */     headers.put("Accept", "*/*");
/* 47 */     headers.put("Referer", referUrl);
/* 48 */     headers.put("Accept-Language", "zh-cn");
/* 49 */     headers.put("Accept-Encoding", "gzip, deflate, x-gzip, identity, *;q=0");
/* 50 */     headers.put("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
/* 51 */     headers.put("Content-Type", "application/x-www-form-urlencoded");
/* 52 */     headers.put("Connection", "Keep-Alive");
/* 53 */     headers.put("Keep-Alive", "300");
/* 54 */     headers.put("Cookie", cookie);
/* 55 */     Log.println(cookie);
/*    */     try
/*    */     {
/* 66 */       HttpGet poster = new HttpGet(getr, url, headers, this);
/* 67 */       poster.doRequest();
/*    */     } catch (Exception e) {
/* 69 */       e.printStackTrace();
/*    */     }
/*    */ 
/* 72 */     return this.status;
/*    */   }
/*    */ 
/*    */   public String getCodeImageUrl()
/*    */   {
/* 78 */     return this.url;
/*    */   }
/*    */   public String getReCodeImageUrl() {
/* 81 */     return this.reUrl;
/*    */   }
/*    */   public String getLastUrl() {
/* 84 */     return this.lastUrl;
/*    */   }
/*    */   public int getPhoneNum() {
/* 87 */     return this.count;
/*    */   }
/*    */ 
/*    */   public void processRespose(Object body)
/*    */   {
/* 92 */     if (body != null)
/*    */     {
/* 94 */       HttpResponse res = (HttpResponse)body;
/* 95 */       StringBuffer sb = (StringBuffer)res.body;
/* 96 */       Log.println(this.getr.getUser().name + "---------after do login--------");
/* 97 */       Log.println(">>>>" + sb + "<<<");
/*    */ 
/* 99 */       int index = sb.indexOf("actionName");
/* 100 */       if (index > 0)
/*    */       {
/* 102 */         index = sb.indexOf("\"", index + 1);
/* 103 */         int lastIndex = sb.indexOf("\"", index + 1);
/* 104 */         if (lastIndex > 0) {
/* 105 */           this.reUrl = sb.substring(index + 1, lastIndex);
/* 106 */           Log.println(this.reUrl);
/*    */         }
/*    */ 
/* 110 */         index = sb.indexOf("wodata=");
/* 111 */         if (index > 0) {
/* 112 */           lastIndex = sb.indexOf("\"", index + 1);
/* 113 */           if (lastIndex > 0) {
/* 114 */             this.url = sb.substring(index, lastIndex);
/* 115 */             Log.println("-------------doreallogin---------" + this.url);
/*    */           }
/*    */         }
/* 118 */         index = sb.indexOf("item-block");
/* 119 */         while (index > 0) {
/* 120 */           this.count += 1;
/* 121 */           index = sb.indexOf("item-block", index + 1);
/*    */         }
/* 123 */         MainJFrame.instance.setPoneNum(this.getr, this.count);
/* 124 */         Log.println("-----------------------phone num=" + this.count + "----------------");
/*    */ 
/* 126 */         index = sb.indexOf("id=\"TheForm\"");
/* 127 */         if (index >= 0) {
/* 128 */           index = sb.indexOf("/WebObjects", index + 1);
/* 129 */           if (index >= 0) {
/* 130 */             lastIndex = sb.indexOf("\"", index + 1);
/* 131 */             if (lastIndex > 0) {
/* 132 */               this.lastUrl = sb.substring(index + 1, lastIndex);
/* 133 */               Log.println("-----get the last url----" + this.lastUrl);
/*    */             }
/*    */ 
/*    */           }
/*    */ 
/*    */         }
/*    */ 
/* 162 */         this.status = 1;
/* 163 */         return;
/*    */       }
/*    */ 
/* 167 */       if ((sb.indexOf("您的会话已超时") >= 0) || (sb.indexOf("Unable to connect to server") >= 0) || (sb.indexOf("请再试一次") > 0))
/*    */       {
/* 169 */         Log.println(sb);
/* 170 */         this.status = 2;
/* 171 */         return;
/*    */       }
/*    */ 
/* 174 */       if (sb.toString().trim().equals("")) {
/* 175 */         this.status = 4;
/* 176 */         return;
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
 * Qualified Name:     networking.http.DoRealLogin
 * JD-Core Version:    0.6.0
 */