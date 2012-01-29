/*     */ package networking.http;
/*     */ 
/*     */ import iphonebuyer.Getor;
/*     */ import iphonebuyer.Log;
/*     */ import iphonebuyer.User;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.net.SocketTimeoutException;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ public class ShoppingCart
/*     */   implements HttpResponseProcessor
/*     */ {
/* 128 */   private int maxCartLimit = 1;
/*     */   private Getor getr;
/*     */   public static final int CART_OK = 1;
/*     */   public static final int CART_SESSION_TIMEOUT = 2;
/*     */   public static final int CART_NET_ERROR = 3;
/* 134 */   private int status = 3;
/*     */ 
/*     */   public int RenderCart(Getor getor, String URL, String cookie) {
/* 137 */     this.getr = getor;
/*     */ 
/* 139 */     HashMap headers = new HashMap(15);
/* 140 */     headers.put("Accept", "*/*");
/* 141 */     headers.put("Accept-Language", "zh-cn,zh;q=0.5");
/* 142 */     headers.put("Accept-Encoding", "gzip, deflate");
/* 143 */     headers.put("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
/* 144 */     headers.put("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.13)"); headers.put("Connection", "Keep-Alive");
/* 145 */     headers.put("Keep-Alive", "300");
/* 146 */     headers.put("Pragma", "no-cache");
/* 147 */     headers.put("Cache-Control", "no-cache");
/* 148 */     headers.put("Cookie", cookie);
/* 149 */     HttpPost poster = new HttpPost(this.getr, URL, headers, "", this);
/* 150 */     poster.doRequest(true);
/*     */ 
/* 155 */     return this.status;
/*     */   }
/*     */ 
/*     */   public int AddCart(Getor getr, String URL, String referUrl, String cookie, String content) {
/* 159 */     this.getr = getr;
/* 160 */     HashMap headers = new HashMap(15);
/* 161 */     headers.put("Accept", "*/*");
/*     */ 
/* 163 */     headers.put("Accept-Language", "zh-cn,zh;q=0.5");
/* 164 */     headers.put("Accept-Encoding", "gzip, deflate");
/* 165 */     headers.put("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
/* 166 */     headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
/* 167 */     headers.put("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.13)");
/* 168 */     headers.put("Connection", "Keep-Alive");
/* 169 */     headers.put("Keep-Alive", "300");
/* 170 */     headers.put("Cache-Control", "no-cache");
/* 171 */     headers.put("Cookie", cookie);
/*     */ 
/* 173 */     HttpPost poster = new HttpPost(getr, URL, headers, content, this);
/* 174 */     poster.doRequest(true);
/*     */ 
/* 178 */     return this.status;
/*     */   }
/*     */ 
/*     */   public int getMaxCartLimit() {
/* 182 */     return this.maxCartLimit;
/*     */   }
/*     */ 
/*     */   public void processRespose(Object body) {
/* 186 */     if (body != null) {
/* 187 */       HttpResponse res = (HttpResponse)body;
/* 188 */       StringBuffer sb = (StringBuffer)res.body;
/* 189 */       Log.println("--------after render/add cart--------------" + this.getr.getUser().name + "-----------------------");
/* 190 */       Log.println(sb);
/* 191 */       if (sb.indexOf("this.objectWithURIRepresentation") >= 0) {
/* 192 */         this.status = 1;
/* 193 */         int index = sb.indexOf("maxCartLimit :");
/* 194 */         if (index > 0) {
/* 195 */           int firstIndex = sb.indexOf("'", index + 1);
/* 196 */           int lastIndex = sb.indexOf("'", firstIndex + 1);
/* 197 */           if ((firstIndex > 0) && (lastIndex > 0) && (firstIndex + 1 < lastIndex)) {
/* 198 */             String num = sb.substring(firstIndex + 1, lastIndex);
/* 199 */             Log.println("----------------------maxCartLimit=(" + num + ")------------------------");
/* 200 */             num = num.trim();
/*     */             try {
/* 202 */               this.maxCartLimit = Integer.parseInt(num);
/*     */             } catch (Exception e) {
/* 204 */               e.printStackTrace();
/*     */             }
/*     */           }
/*     */         }
/* 208 */         return;
/*     */       }
/*     */ 
/* 211 */       if ((sb.indexOf("您的会话已超时") >= 0) || (sb.indexOf("Unable to connect to server") >= 0) || (sb.indexOf("请再试一次") > 0))
/*     */       {
/* 214 */         Log.println(sb);
/* 215 */         this.status = 2;
/* 216 */         return;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void processFileNotFoundException(FileNotFoundException e)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void processUnknownHostException(UnknownHostException e)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void processSocketTimeoutException(SocketTimeoutException e)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void processIOException(IOException e)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void processException(Exception e)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void processConnectionClosed()
/*     */   {
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Administrator\桌面\5-22\3\
 * Qualified Name:     networking.http.ShoppingCart
 * JD-Core Version:    0.6.0
 */