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
/*    */ public class GetShop
/*    */   implements HttpResponseProcessor
/*    */ {
/*    */   private Getor getr;
/*    */   public static final int CART_OK = 1;
/*    */   public static final int CART_SESSION_TIMEOUT = 2;
/*    */   public static final int CART_NET_ERROR = 3;
/* 35 */   private int status = 3;
/*    */ 
/*    */   public int GetShop(Getor getor, String URL, String referUrl, String cookie) {
/* 38 */     this.getr = getor;
/*    */ 
/* 40 */     HashMap headers = new HashMap(15);
/* 41 */     headers.put("Accept", "*/*");
/* 42 */     headers.put("Accept-Language", "zh-cn,zh;q=0.5");
/* 43 */     headers.put("Accept-Encoding", "gzip, deflate");
/* 44 */     headers.put("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
/* 45 */     headers.put("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.13)"); headers.put("Connection", "Keep-Alive");
/* 46 */     headers.put("Keep-Alive", "300");
/* 47 */     headers.put("Pragma", "no-cache");
/* 48 */     headers.put("Cache-Control", "no-cache");
/* 49 */     headers.put("Cookie", cookie);
/* 50 */     HttpPost poster = new HttpPost(this.getr, URL, headers, "", this);
/* 51 */     poster.doRequest(true);
/* 52 */     return this.status;
/*    */   }
/*    */ 
/*    */   public void processRespose(Object body)
/*    */   {
/* 58 */     if (body != null) {
/* 59 */       HttpResponse res = (HttpResponse)body;
/* 60 */       StringBuffer sb = (StringBuffer)res.body;
/* 61 */       Log.println("--------after render/add cart--------------" + this.getr.getUser().name + "-----------------------");
/* 62 */       Log.println(sb);
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
 * Qualified Name:     networking.http.GetShop
 * JD-Core Version:    0.6.0
 */