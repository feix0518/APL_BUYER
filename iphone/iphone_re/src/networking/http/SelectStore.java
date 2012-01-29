/*    */ package networking.http;
/*    */ 
/*    */ import iphonebuyer.Getor;
/*    */ import iphonebuyer.Log;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.IOException;
/*    */ import java.net.SocketTimeoutException;
/*    */ import java.net.UnknownHostException;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class SelectStore
/*    */   implements HttpResponseProcessor
/*    */ {
/*    */   public static final int SELECT_STORE_OK = 1;
/*    */   public static final int SELECT_STORE_SESSION_TIMEOUT = 2;
/*    */   public static final int SELECT_STORE_NET_ERROR = 3;
/* 27 */   private int status = 3;
/*    */   private Getor getr;
/* 29 */   private String nextUrl = null;
/*    */ 
/* 31 */   public int selectStore(Getor getr, String url, String cookie, String store) { this.getr = getr;
/* 32 */     HashMap headers = new HashMap(5);
///* 33 */     headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
/////* 34 */     headers.put("Referer", "http://reserve.apple.com/WebObjects/ProductReservation.woa/wa/reserveProduct?lang=zh&country=CN&prelaunch=MC605CH_A");
///* 34 */     headers.put("Referer", "http://reserve.apple.com/WebObjects/ProductReservation.woa/wa/reserveProduct?lang=zh&country=CN");
///* 35 */     headers.put("Accept-Language", "zh-cn;q=0.5");
//             headers.put("Host", "reserve.apple.com");
///* 36 */     headers.put("Accept-Encoding", "gzip, deflate");
///* 37 */     headers.put("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.13) Gecko/20101203 ZiiMeet v2.00.07 Firefox/3.6.13 ( .NET CLR 3.5.30729)"); headers.put("Content-Type", "application/x-www-form-urlencoded");
///* 38 */     headers.put("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
///* 39 */     headers.put("Connection", "Keep-Alive");
///* 40 */     headers.put("Keep-Alive", "300");
///* 40 */     headers.put("Proxy-Authorization", "Basic emhvdWhsOjEwMjgxMw==");
///* 40 */     headers.put("Proxy-Connection", "keep-alive");
///* 41 */     headers.put("Cookie", cookie);
				headers.put("Host","reserve.apple.com");
				headers.put("User-Agent","Mozilla/5.0 (Windows NT 5.1) AppleWebKit/534.51.22 (KHTML, like Gecko) Version/5.1.1 Safari/534.51.22");
				headers.put("Content-Length","182");
				headers.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
				headers.put("Origin","http://reserve.apple.com");
				headers.put("Content-Type","application/x-www-form-urlencoded");
				headers.put("Referer","http://reserve.apple.com/WebObjects/ProductReservation.woa/wa/reserveProduct?lang=zh&country=CN");
				headers.put("Accept-Language","ja-JP");
				headers.put("Accept-Encoding","gzip, deflate");
				headers.put("Pragma","no-cache");
				headers.put("Proxy-Authorization","Basic emhvdWhsOjEwMjgxMw==");
				headers.put("Connection","keep-alive");
				headers.put("Proxy-Connection","keep-alive");
/* 41 */     headers.put("Cookie", cookie);
//System.out.println(cookie);
/* 42 */     this.nextUrl = url;
/* 43 */     Log.println("store=" + store);
/* 44 */     HttpPost poster = new HttpPost(getr, url, headers, store, this);
/* 45 */     poster.doRequest(true);
/* 46 */     return this.status; }
/*    */ 
/*    */   public String getNextUrl()
/*    */   {
/* 50 */     return this.nextUrl;
/*    */   }
/*    */ 
/*    */   public void processRespose(Object body) {
/* 54 */     if (body != null) {
/* 55 */       HttpResponse res = (HttpResponse)body;
/* 56 */       StringBuffer sb = (StringBuffer)res.body;
/* 57 */       Log.println("------------------after select store--------------------------");
/*    */ //System.out.println(sb.toString());
/* 61 */       int index = sb.indexOf("cust.component.ShoppingCart");
/* 62 */       if (index > 0)
/*    */       {
/* 64 */         index = sb.indexOf("<img alt=\"iPhone\"");
/* 65 */         if (index > 0) {
/* 66 */           String subString = sb.substring(index - 120, index);
/* 67 */           String match = "/WebObjects/ProductReservation.woa/wo/";
/* 68 */           index = subString.indexOf(match);
/* 69 */           if (index > 0) {
/* 70 */             int lastIndex = subString.indexOf("\"", index + match.length());
/* 71 */             this.nextUrl = ("http://reserve.apple.com" + subString.substring(index, lastIndex));
/* 72 */             Log.println("nextUrl=" + this.nextUrl);
/*    */           }
/*    */ 
/*    */         }
/*    */ 
/* 77 */         Log.println("----------OK-----------");
/* 78 */         this.status = 1;
/* 79 */         return;
/*    */       }
/*    */ 
/* 83 */       if ((sb.indexOf("您的会话已超时") > 0) || (sb.indexOf("Unable to connect to server") >= 0) || (sb.indexOf("请再试一次") > 0))
/*    */       {
/* 86 */         Log.println("----------会话超时，线程从新开始-----------");
/* 87 */         this.status = 2;
/* 88 */         return;
/*    */       }
/*    */ 
/* 91 */       if (sb.indexOf("请选择您希望前往提取您所预定的产品") > 0)
/*    */       {
/* 93 */         String match = "action=\"/WebObjects/ProductReservation.woa/wo/";
/* 94 */         index = sb.indexOf(match);
/* 95 */         if (index > 0) {
/* 96 */           int lastIndex = sb.indexOf("\"", index + match.length());
/* 97 */           this.nextUrl = ("http://reserve.apple.com/WebObjects/ProductReservation.woa/wo/" + sb.substring(index + match.length(), lastIndex));
/* 98 */           Log.println("nextUrl=" + this.nextUrl);
/*    */         }
/*    */ 
/*    */       }
/*    */ 
/* 103 */       this.status = 3;
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
 * Qualified Name:     networking.http.SelectStore
 * JD-Core Version:    0.6.0
 */