/*    */ package networking.http;
/*    */ 
/*    */ import iphonebuyer.Getor;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.IOException;
/*    */ import java.net.SocketTimeoutException;
/*    */ import java.net.UnknownHostException;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class GetVerificationCodeUrl
/*    */   implements HttpResponseProcessor
/*    */ {
/*    */   private String url;
/*    */ 
/*    */   public String get(Getor getr, String URL, String referUrl, String cookie)
/*    */   {
/* 24 */     HashMap headers = new HashMap(15);
/*    */ 
/* 26 */     headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
/* 27 */     headers.put("Referer", referUrl);
/* 28 */     headers.put("Accept-Language", "zh-cn,zh;q=0.5");
/* 29 */     headers.put("Accept-Encoding", "gzip, deflate, x-gzip, identity, *;q=0");
/* 30 */     headers.put("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
/* 31 */     headers.put("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1) ;  Embedded Web Browser from: http://bsalsa.com/; song2009 Embedded Web Browser from: http://bsalsa.com/; WebSaver; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.648; .NET CLR 3.5.21022; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; AskTB5.6; ZiiMeet v2.00.07)");
/* 32 */     headers.put("Content-Type", "application/x-www-form-urlencoded");
/* 33 */     headers.put("Connection", "Keep-Alive");
/* 34 */     headers.put("Keep-Alive", "300");
/* 35 */     headers.put("Pragma", "no-cache");
/* 36 */     headers.put("Cache-Control", "no-cache");
/* 37 */     headers.put("Cookie", cookie);
/*    */ 
/* 39 */     HttpGet getor = new HttpGet(getr, URL, headers, this);
/* 40 */     getor.doRequest();
/*    */ 
/* 42 */     return this.url;
/*    */   }
/*    */ 
/*    */   public void processRespose(Object body)
/*    */   {
/* 47 */     if (body != null) {
/* 48 */       HttpResponse res = (HttpResponse)body;
/* 49 */       StringBuffer sb = (StringBuffer)res.body;
/* 50 */       int index = sb.indexOf("wodata");
/* 51 */       if (index > 0)
/*    */       {
/* 53 */         if (index > 0) {
/* 54 */           int lastIndex = sb.indexOf("\"", index + 1);
/* 55 */           this.url = sb.substring(index, lastIndex);
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
 * Qualified Name:     networking.http.GetVerificationCodeUrl
 * JD-Core Version:    0.6.0
 */