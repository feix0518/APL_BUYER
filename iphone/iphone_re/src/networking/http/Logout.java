/*    */ package networking.http;
/*    */ 
/*    */ import iphonebuyer.Getor;
/*    */ import iphonebuyer.Log;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.IOException;
/*    */ import java.net.SocketTimeoutException;
/*    */ import java.net.URLEncoder;
/*    */ import java.net.UnknownHostException;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class Logout
/*    */   implements HttpResponseProcessor
/*    */ {
/*    */   private Getor getr;
/*    */ 
/*    */   public void out(String diskId)
/*    */   {
/* 26 */     HashMap headers = new HashMap(5);
/* 27 */     headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
/* 28 */     headers.put("Accept-Language", "zh-cn;q=0.5");
/* 29 */     headers.put("Accept-Encoding", "gzip, deflate");
/* 30 */     headers.put("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.13) Gecko/20101203 ZiiMeet v2.00.07 Firefox/3.6.13 ( .NET CLR 3.5.30729)"); headers.put("Content-Type", "application/x-www-form-urlencoded");
/* 31 */     headers.put("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
/* 32 */     headers.put("Connection", "Keep-Alive");
/* 33 */     String path = URLEncoder.encode(diskId);
/*    */   }
/*    */ 
/*    */   public void processRespose(Object body)
/*    */   {
/* 40 */     HttpResponse res = (HttpResponse)body;
/* 41 */     Log.println(res.body);
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
 * Qualified Name:     networking.http.Logout
 * JD-Core Version:    0.6.0
 */