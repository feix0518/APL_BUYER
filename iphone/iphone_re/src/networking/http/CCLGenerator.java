/*    */ package networking.http;
/*    */ 
/*    */ import iphonebuyer.Getor;
/*    */ import iphonebuyer.Log;
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
/*    */ public class CCLGenerator
/*    */   implements HttpResponseProcessor
/*    */ {
/*    */   private static final String URL = "http://images.apple.com/global/nav/images/globalheader.png";
/*    */   private String cclValue;
/* 27 */   private Getor getr = null;
/*    */ 
/* 29 */   public String getCCL(Getor getr) { this.getr = getr;
/* 30 */     HashMap headers = new HashMap(5);
/* 31 */     headers.put("Accept", "*/*");
/* 32 */     headers.put("Accept-Language", "zh-cn;q=0.5");
/* 33 */     headers.put("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
/* 34 */     headers.put("Accept-Encoding", "gzip, deflate");
/* 35 */     headers.put("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.13) Gecko/20101203 ZiiMeet v2.00.07 Firefox/3.6.13 ( .NET CLR 3.5.30729)"); headers.put("Content-Type", "application/x-www-form-urlencoded");
/* 36 */     headers.put("Connection", "Keep-Alive");
/* 37 */     headers.put("Keep-Alive", "300");
/*    */ 
/* 40 */     HttpGet getor = new HttpGet(getr, "http://images.apple.com/global/nav/images/globalheader.png", headers, this);
/* 41 */     getor.doRequest();
/* 42 */     return this.cclValue;
/*    */   }
/*    */ 
/*    */   public void processRespose(Object body)
/*    */   {
/* 47 */     if (body != null) {
/* 48 */       HttpResponse res = (HttpResponse)body;
/* 49 */       Set set = res.header;
/* 50 */       if (set != null) {
/* 51 */         Iterator it = set.iterator();
/* 52 */         while (it.hasNext()) {
/* 53 */           Map.Entry entry = (Map.Entry)it.next();
/*    */ 
/* 55 */           Object key = entry.getKey();
/*    */ 
/* 57 */           if ((key != null) && 
/* 58 */             (key.toString().toLowerCase().equals("set-cookie"))) {
/* 59 */             String value = entry.getValue().toString();
/* 60 */             value = value.substring(1, value.length() - 1);
/* 61 */             Log.println(value);
/* 62 */             int index = value.indexOf("ccl");
/* 63 */             if (index >= 0) {
/* 64 */               value = value.substring(index, value.indexOf(";", index + 1));
/* 65 */               this.cclValue = value;
/* 66 */               break;
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
 * Qualified Name:     networking.http.CCLGenerator
 * JD-Core Version:    0.6.0
 */