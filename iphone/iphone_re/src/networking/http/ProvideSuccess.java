/*    */ package networking.http;
/*    */ 
/*    */ import iphonebuyer.Getor;
/*    */ import iphonebuyer.Log;
/*    */ import iphonebuyer.MainJFrame;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.IOException;
/*    */ import java.net.SocketTimeoutException;
/*    */ import java.net.UnknownHostException;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class ProvideSuccess
/*    */   implements HttpResponseProcessor
/*    */ {
/* 24 */   private boolean ret = false;
/*    */   private Getor getr;
/*    */ 
/*    */   public boolean post(Getor getr, String userid, String clientname, String token, String adminid, String name, String email, String password, String idcard, String store, String fetchtime, String type, int num, String color, String id, String usedtype, String cowid)
/*    */   {
/* 32 */     HashMap headers = new HashMap(5);
/*    */ 
/* 34 */     headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
/* 35 */     headers.put("Accept-Language", "zh-cn,zh;q=0.5");
/* 36 */     headers.put("Accept-Encoding", "gzip, deflate, x-gzip, identity, *;q=0");
/* 37 */     headers.put("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
/* 38 */     headers.put("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1) ;");
/* 39 */     headers.put("Connection", "Keep-Alive");
/* 40 */     headers.put("Keep-Alive", "300");
/* 41 */     headers.put("Pragma", "no-cache");
/* 42 */     headers.put("Cache-Control", "no-cache");
/*    */ 
/* 44 */     StringBuffer sb = new StringBuffer(1024);
/* 45 */     sb.append(userid);
/* 46 */     sb.append("&");
/* 47 */     sb.append(token);
/* 48 */     sb.append("&");
/* 49 */     sb.append(adminid);
/* 50 */     sb.append("&");
/* 51 */     sb.append(name);
/* 52 */     sb.append("&");
/* 53 */     sb.append(email);
/* 54 */     sb.append("&");
/* 55 */     sb.append(password);
/* 56 */     sb.append("&");
/* 57 */     sb.append(idcard);
/* 58 */     sb.append("&");
/* 59 */     sb.append(store);
/* 60 */     sb.append("&");
/* 61 */     if ((fetchtime == null) || (fetchtime.trim().equals("")))
/* 62 */       fetchtime = "----";
/* 63 */     sb.append(fetchtime);
/* 64 */     sb.append("&");
/* 65 */     sb.append(type);
/* 66 */     sb.append("&");
/* 67 */     sb.append(num);
/* 68 */     sb.append("&");
/* 69 */     sb.append(color);
/* 70 */     sb.append("&");
/* 71 */     sb.append(clientname);
/* 72 */     sb.append("&");
/* 73 */     sb.append(id);
/* 74 */     sb.append("&");
/* 75 */     sb.append(usedtype);
/* 76 */     sb.append("&");
/* 77 */     sb.append(cowid);
/* 78 */     Log.println(sb);
/* 79 */     String url = "http://localhost:8080/QuerySun/PostResult";
/* 80 */     HttpPost poster = new HttpPost(getr, url, headers, sb.toString(), this);
/* 81 */     poster.doRequest(true);
/* 82 */     return this.ret;
/*    */   }
/*    */ 
/*    */   public void processRespose(Object body)
/*    */   {
/* 87 */     HttpResponse res = (HttpResponse)body;
/* 88 */     StringBuffer sb = (StringBuffer)res.body;
/* 89 */     String strRet = sb.toString().trim();
/* 90 */     Log.println("----------------------提交:" + strRet + "------------------------");
/* 91 */     if (strRet.equals("OK"))
/* 92 */       this.ret = true;
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
 * Qualified Name:     networking.http.ProvideSuccess
 * JD-Core Version:    0.6.0
 */