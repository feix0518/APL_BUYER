/*     */ package networking.http;
/*     */ 
/*     */ import iphonebuyer.Getor;
/*     */ import iphonebuyer.Log;
/*     */ import iphonebuyer.MainJFrame;
/*     */ import iphonebuyer.User;
/*     */ import iphonebuyer.WarningDlg;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.SocketTimeoutException;
/*     */ import java.net.UnknownHostException;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import java.util.zip.GZIPInputStream;
/*     */ import javax.net.ssl.HostnameVerifier;
/*     */ import javax.net.ssl.HttpsURLConnection;
/*     */ import javax.net.ssl.SSLContext;
/*     */ import javax.net.ssl.SSLSession;
/*     */ import javax.net.ssl.TrustManager;
/*     */ import javax.net.ssl.X509TrustManager;
import net.sf.jpd.netsettings.NetworkUtils;
import net.sf.jpd.netsettings.Proxy;
/*     */ 
/*     */ public class HttpPost
/*     */ {
/*     */   public static final int TIMEOUT = 30000;
/*     */   private String url;
/*  40 */   private int timeout = 30000;
/*     */   private HttpResponseProcessor httpResProcessor;
/*  42 */   private boolean cancelled = false;
/*     */   private HashMap<String, String> headers;
/*     */   private byte[] body;
/*     */   private Getor getor;
/* 214 */   HostnameVerifier hv = new HostnameVerifier() {
/*     */     public boolean verify(String urlHostName, SSLSession session) {
/* 216 */       Log.println("Warning: URL Host: " + urlHostName + " vs. " + session.getPeerHost());
/*     */ 
/* 218 */       return true;
/*     */     }
/* 214 */   };
/*     */ 
/*     */   public HttpPost(Getor getor, String url, HashMap<String, String> headers, String body, HttpResponseProcessor httpResProcessor)
/*     */   {
/*  48 */     this.getor = getor;
/*  49 */     this.url = url;
/*  50 */     this.httpResProcessor = httpResProcessor;
/*  51 */     this.headers = headers;
/*     */     try {
/*  53 */       this.body = body.getBytes("UTF-8");
/*     */     } catch (Exception e) {
/*  55 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setTimout(int timeout) {
/*  59 */     this.timeout = timeout;
/*     */   }
/*     */ 
/*     */   public void doCancel() {
/*  63 */     this.cancelled = true;
/*     */   }
/*     */ 
/*     */   public void doRequest(boolean readBody) {
/*  67 */     OutputStream out = null;
/*  68 */     InputStream in = null;
/*  69 */     HttpURLConnection conn = null;
/*  70 */     boolean tryAgain = true;
/*  71 */     this.timeout = MainJFrame.instance.getTimeout();
/*     */ 
/*  73 */     for (int i = 0; (i < 1) && (tryAgain) && ((this.getor == null) || (this.getor.running)); i++) {
/*  74 */       if (this.getor != null)
/*  75 */         Log.println(this.getor.getUser().name + this.url);
/*     */       try {
/*  77 */         trustAllHttpsCertificates();
/*  78 */         HttpsURLConnection.setDefaultHostnameVerifier(this.hv);
                  Proxy proxy = new Proxy(Proxy.Type.HTTP, "proxy.sunjapan.com.cn", 8080, "zhouhl", "102813");
/*  79 */         conn = NetworkUtils.getConnection(this.url, proxy);
/*  80 */         conn.setRequestMethod("POST");
/*  81 */         if (this.headers != null)
/*     */         {
/*  83 */           Iterator it = this.headers.entrySet().iterator();
/*  84 */           while (it.hasNext()) {
/*  85 */             Map.Entry entry = (Map.Entry)it.next();
/*  86 */             String key = (String)entry.getKey();
/*  87 */             String value = (String)entry.getValue();
/*  88 */             conn.addRequestProperty(key, value);
/*     */           }
/*     */         }
/*  91 */         conn.setDoOutput(true);
/*     */ 
/*  93 */         conn.setConnectTimeout(this.timeout);
/*  94 */         conn.setReadTimeout(this.timeout);
/*  95 */         out = conn.getOutputStream();
/*     */ 
/*  97 */         out.write(this.body);
/*  98 */         out.flush();
/*  99 */         out.close();
/*     */ 
/* 102 */         Set entries = conn.getHeaderFields().entrySet();
/*     */ System.out.println(conn.getResponseCode());
/* 104 */         if (readBody)
/*     */         {
/* 106 */           in = conn.getInputStream();
/* 107 */           String content_type = conn.getHeaderField("content-encoding");

/* 108 */           if ((content_type != null) && (content_type.startsWith("gzip"))) {
/* 109 */             in = new GZIPInputStream(in);
/*     */           }
/* 111 */           byte[] bytes = new byte[4096];
/* 112 */           StringBuffer sb = new StringBuffer(4096);
/* 113 */           int len = 0;
/* 114 */           while ((len = in.read(bytes)) > 0) {
/* 115 */             sb.append(new String(bytes, 0, len, "UTF-8").trim());
/*     */           }
/*     */ 
/* 118 */           this.httpResProcessor.processRespose(new HttpResponse(entries, sb));
/*     */         } else {
/* 120 */           this.httpResProcessor.processRespose(new HttpResponse(entries, ""));
/*     */         }
/* 122 */         if (in != null) {
/* 123 */           in.close();
/*     */         }
/* 125 */         tryAgain = false;
/*     */       }
/*     */       catch (FileNotFoundException e) {
/* 128 */         Log.println(this.url + "----------FileNotFoundException----------");
/* 129 */         tryAgain = true;
/* 130 */         if ((i == 2) && (!this.cancelled))
/*     */         {
/* 132 */           MainJFrame.instance.setNetStatus(this.getor, "<html><font color=\"#ff0000\" face=\"黑体\" size=\"4\">连接服务器失败，可能是服务器没有运行</font></html>");
/* 133 */           this.httpResProcessor.processFileNotFoundException(e);
/*     */         }
/*     */ 
/*     */       }
/*     */       catch (UnknownHostException e)
/*     */       {
/* 139 */         Log.println(this.getor.getUser().name + this.url + "----------UnknownHostException----------");
/* 140 */         if ((i == 2) && (!this.cancelled)) {
/* 141 */           MainJFrame.instance.setNetStatus(this.getor, "<html><font color=\"#ff0000\" face=\"黑体\" size=\"4\">网络连接失败，可能网络断了</font></html>");
/*     */ 
/* 143 */           this.httpResProcessor.processUnknownHostException(e);
/*     */         }
/*     */ 
/*     */       }
/*     */       catch (SocketTimeoutException e)
/*     */       {
/* 149 */         Log.println(this.getor.getUser().name + this.url + "----------SocketException----------");
/* 150 */         Log.println(this.getor.getUser().name + this.url + "------" + this.getor.getUser().name + "----IOException----------" + e.getMessage());
/*     */ 
/* 153 */         if ((i == 2) && (!this.cancelled))
/*     */         {
/* 159 */           this.httpResProcessor.processSocketTimeoutException(e);
/*     */         }
/*     */       }
/*     */       catch (IOException e)
/*     */       {
/* 164 */         e.printStackTrace();
/* 165 */         if (this.getor != null) {
/* 166 */           Log.println(this.getor.getUser().name + this.url + "------" + this.getor.getUser().name + "----IOException----------" + e.getMessage());
/*     */         }
/* 168 */         if ((i == 2) && (!this.cancelled)) {
/* 169 */           if ((e != null) && (e.getMessage() != null)) {
/* 170 */             if (e.getMessage().indexOf("Connection refused: connect") > 0) { MainJFrame.instance.setNetStatus(this.getor, "<html><font color=\"#ff0000\" face=\"黑体\">服务器连接失败，可能服务器没有运行</font></html>");
/* 172 */               MainJFrame.instance.timoutcount += 1;
/* 173 */               if ((MainJFrame.instance.timoutcount <= 5) || (WarningDlg.isShowed));
/* 176 */             } else if (e.getMessage().equals("Connection reset")) {
/* 177 */               MainJFrame.instance.setNetStatus(this.getor, "<html><font color=\"#ff0000\" face=\"黑体\">连接被重置，有可能服务器访问量太大，或者发送的文件太大，或者服务器已经崩溃</font></html>");
/*     */             }
/*     */             else {
/* 180 */               MainJFrame.instance.setNetStatus(this.getor, "<html><font color=\"#ff0000\" face=\"黑体\">服务器连接失败：" + e.getMessage() + "</font></html>");
/*     */             }
/*     */           }
/* 183 */           this.httpResProcessor.processIOException(e);
/*     */         }
/*     */ 
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 190 */         if ((i == 2) && (!this.cancelled)) {
/* 191 */           MainJFrame.instance.setNetStatus(this.getor, "<html><font color=\"#ff0000\" face=\"黑体\">服务器连接失败：" + e.getMessage() + "，<br/>如果异常信息为null,说明是网络返回后的处理出现了未捕捉到的异常<br/>" + this.url + "</font></html>");
/*     */ 
/* 193 */           this.httpResProcessor.processException(e);
/*     */         }
/* 195 */         Log.println(this.getor.getUser().name + this.url + "----------Exception----------" + e.getMessage());
/* 196 */         e.printStackTrace();
/*     */       } finally {
/*     */         try {
/* 199 */           if (out != null)
/* 200 */             out.close();
/* 201 */           if (in != null) {
/* 202 */             in.close();
/*     */           }
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/* 207 */           e.printStackTrace();
/*     */         }
/*     */       }
/* 210 */       if (!this.cancelled)
/* 211 */         this.httpResProcessor.processConnectionClosed();
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void trustAllHttpsCertificates()
/*     */     throws Exception
/*     */   {
/* 223 */     TrustManager[] trustAllCerts = new TrustManager[1];
/* 224 */     TrustManager tm = new miTM();
/* 225 */     trustAllCerts[0] = tm;
/* 226 */     SSLContext sc = SSLContext.getInstance("SSL");
/*     */ 
/* 228 */     sc.init(null, trustAllCerts, null);
/* 229 */     HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
/*     */   }
/*     */ 
/*     */   static class miTM implements TrustManager, X509TrustManager
/*     */   {
/*     */     public X509Certificate[] getAcceptedIssuers()
/*     */     {
/* 236 */       return null;
/*     */     }
/*     */ 
/*     */     public boolean isServerTrusted(X509Certificate[] certs)
/*     */     {
/* 241 */       return true;
/*     */     }
/*     */ 
/*     */     public boolean isClientTrusted(X509Certificate[] certs)
/*     */     {
/* 246 */       return true;
/*     */     }
/*     */ 
/*     */     public void checkServerTrusted(X509Certificate[] certs, String authType)
/*     */       throws CertificateException
/*     */     {
/*     */     }
/*     */ 
/*     */     public void checkClientTrusted(X509Certificate[] certs, String authType)
/*     */       throws CertificateException
/*     */     {
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Administrator\桌面\5-22\3\
 * Qualified Name:     networking.http.HttpPost
 * JD-Core Version:    0.6.0
 */