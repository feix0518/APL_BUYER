/*    */ package networking.http;
/*    */ 
/*    */ import iphonebuyer.Getor;
/*    */ import iphonebuyer.Log;
/*    */ import java.io.File;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.FileOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.net.SocketTimeoutException;
/*    */ import java.net.UnknownHostException;
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
import java.util.Map;
/*    */ import java.util.Map.Entry;
import java.util.Set;
/*    */ 
/*    */ public class GetVerificationCode
/*    */   implements HttpResponseProcessor
/*    */ {
/*    */   String url;
/*    */   private String name;
/*    */   public static final int VC_OK = 1;
/*    */   public static final int VC_SESSION_TIMEOUT = 2;
/*    */   public static final int VC_NET_ERROR = 3;
/* 28 */   private int status = 3;
/*    */ 
/*    */   public int get(Getor getr, String URL, String referUrl, String cookie) {
/* 31 */     HashMap headers = new HashMap(5);
/* 32 */     this.url = URL;
/* 33 */     headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
/* 34 */     headers.put("Referer", referUrl);
/* 35 */     headers.put("Accept-Language", "zh-cn,zh;q=0.5");
/* 36 */     headers.put("Accept-Encoding", "gzip, deflate, x-gzip, identity, *;q=0");
/* 37 */     headers.put("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
/* 38 */     headers.put("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1) ;  Embedded Web Browser from: http://bsalsa.com/; song2009 Embedded Web Browser from: http://bsalsa.com/; WebSaver; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.648; .NET CLR 3.5.21022; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; AskTB5.6; ZiiMeet v2.00.07)");
/* 39 */     headers.put("Content-Type", "application/x-www-form-urlencoded");
/* 40 */     headers.put("Connection", "Keep-Alive");
/* 41 */     headers.put("Keep-Alive", "300");
/* 42 */     headers.put("Pragma", "no-cache");
/* 43 */     headers.put("Cache-Control", "no-cache");
/* 44 */     headers.put("Cookie", cookie);
/*    */ 
/* 46 */     HttpGet getor = new HttpGet(getr, URL, headers, this);
/* 47 */     getor.doBinaryRequest();
/* 48 */     return this.status;
/*    */   }
/*    */   public String getImagePath() {
/* 51 */     return this.name;
/*    */   }
/*    */ 
/*    */   public void processRespose(Object body) {
/* 55 */     if (body != null) {
/* 56 */       HttpResponse res = (HttpResponse)body;
/*    */ 
/* 58 */       Set set = res.header;
/* 59 */       if (set != null) {
/* 60 */         Iterator it = set.iterator();
/* 61 */         while (it.hasNext()) {
/* 62 */           Map.Entry entry = (Map.Entry)it.next();
/*    */ 
/* 64 */           Object key = entry.getKey();
/* 65 */           if ((key != null) && 
/* 66 */             (key.toString().toLowerCase().equals("content-type"))) {
/* 67 */             String value = entry.getValue().toString();
/* 68 */             value = value.substring(1, value.length() - 1);
/* 69 */             int index = value.indexOf("image/jpeg");
/* 70 */             if (index >= 0) {
/* 71 */               InputStream in = (InputStream)res.body;
/*    */               try {
/* 73 */                 this.name = (this.url.substring(this.url.indexOf("=") + 1) + ".jpg");
/* 74 */                 File file = new File(this.name);
/* 75 */                 FileOutputStream fout = new FileOutputStream(file);
/* 76 */                 byte[] bytes = new byte[4096];
/* 77 */                 int len = 0;
/* 78 */                 StringBuffer sb = new StringBuffer(8192);
/* 79 */                 while ((len = in.read(bytes)) > 0) {
/* 80 */                   fout.write(bytes, 0, len);
/* 81 */                   sb.append(new String(bytes, 0, len, "UTF-8").trim());
/*    */                 }
/* 83 */                 fout.close();
/* 84 */                 if ((sb.indexOf("您的会话已超时") >= 0) || (sb.indexOf("Unable to connect to server") >= 0) || (sb.toString().trim().equals("")) || (sb.indexOf("请再试一次") > 0))
/*    */                 {
/* 87 */                   Log.println(sb);
/* 88 */                   this.status = 2;
/*    */                 }
/* 90 */                 else if (file.length() > 1024L) {
/* 91 */                   this.status = 1;
/*    */                 } else {
/* 93 */                   this.status = 2;
/*    */                 }
/*    */ 
/* 96 */                 return;
/*    */               } catch (Exception e) {
/* 98 */                 e.printStackTrace();
/*    */               }
/*    */             }
/*    */           }
/*    */         }
/*    */       }
/*    */ 
/*    */       try
/*    */       {
/* 107 */         byte[] bytes = new byte[4096];
/* 108 */         int len = 0;
/* 109 */         InputStream in = (InputStream)res.body;
/* 110 */         StringBuffer sb = new StringBuffer(8192);
/* 111 */         while ((len = in.read(bytes)) > 0) {
/* 112 */           sb.append(new String(bytes, 0, len, "UTF-8").trim());
/*    */         }
/* 114 */         if ((sb.indexOf("您的会话已超时") >= 0) || (sb.indexOf("Unable to connect to server") >= 0) || (sb.toString().trim().equals("")) || (sb.indexOf("请再试一次") > 0))
/*    */         {
/* 117 */           Log.println(sb);
/* 118 */           this.status = 2;
/* 119 */           return;
/*    */         }
/*    */       } catch (Exception e) {
/* 122 */         e.printStackTrace();
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
 * Qualified Name:     networking.http.GetVerificationCode
 * JD-Core Version:    0.6.0
 */