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
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import java.util.zip.GZIPInputStream;
/*     */ import javax.swing.JOptionPane;
import net.sf.jpd.netsettings.NetworkUtils;
import net.sf.jpd.netsettings.Proxy;
/*     */ 
/*     */ public class HttpGet
/*     */ {
/*     */   public static final int TIMEOUT = 30000;
/*     */   private String url;
/*  37 */   private int timeout = 30000;
/*     */   private HttpResponseProcessor httpResProcessor;
/*  39 */   private boolean cancelled = false;
/*     */   private HashMap<String, String> headers;
/*  41 */   private Getor getor = null;
/*     */ 
/*     */   public HttpGet(Getor getor, String url, HashMap<String, String> headers, HttpResponseProcessor httpResProcessor) {
/*  44 */     this.getor = getor;
/*  45 */     this.url = url;
/*  46 */     this.httpResProcessor = httpResProcessor;
/*  47 */     this.headers = headers;
/*     */   }
/*     */   public void setTimout(int timeout) {
/*  50 */     this.timeout = timeout;
/*     */   }
/*     */ 
/*     */   public void doCancel() {
/*  54 */     this.cancelled = true;
/*     */   }
/*     */ 
/*     */   public void doBinaryRequest() {
/*  58 */     OutputStream out = null;
/*  59 */     InputStream in = null;
/*  60 */     HttpURLConnection conn = null;
/*  61 */     boolean tryAgain = true;
/*  62 */     this.timeout = MainJFrame.instance.getTimeout();
/*     */ 
/*  64 */     for (int i = 0; (i < 1) && (tryAgain) && ((this.getor == null) || (this.getor.running)); i++) {
/*     */       try {
/*  66 */         conn = NetworkUtils.getConnection(this.url, null);
/*  67 */         if (this.headers != null)
/*     */         {
/*  69 */           Iterator it = this.headers.entrySet().iterator();
/*  70 */           while (it.hasNext()) {
/*  71 */             Map.Entry entry = (Map.Entry)it.next();
/*  72 */             String key = (String)entry.getKey();
/*  73 */             String value = (String)entry.getValue();
/*  74 */             conn.addRequestProperty(key, value);
/*     */           }
/*     */         }
/*     */ 
/*  78 */         conn.setConnectTimeout(this.timeout);
/*  79 */         conn.setReadTimeout(this.timeout);
/*     */ 
/*  81 */         in = conn.getInputStream();
/*     */ 
/*  83 */         Set entries = conn.getHeaderFields().entrySet();
/*     */ 
/*  85 */         String content_type = conn.getHeaderField("content-encoding");
/*  86 */         if ((content_type != null) && (content_type.startsWith("gzip"))) {
/*  87 */           in = new GZIPInputStream(in);
/*     */         }
/*     */ 
/*  95 */         this.httpResProcessor.processRespose(new HttpResponse(entries, in));
/*  96 */         in.close();
/*     */ 
/*  99 */         tryAgain = false;
/*     */       }
/*     */       catch (FileNotFoundException e) {
/* 102 */         Log.println("----------FileNotFoundException----------");
/* 103 */         tryAgain = true;
/* 104 */         if ((i == 2) && (!this.cancelled))
/*     */         {
/* 106 */           MainJFrame.instance.setNetStatus(this.getor, "<html><font color=\"#ff0000\" face=\"黑体\" size=\"4\">连接服务器失败，可能是服务器没有运行</font></html>");
/* 107 */           this.httpResProcessor.processFileNotFoundException(e);
/*     */         }
/*     */ 
/*     */       }
/*     */       catch (UnknownHostException e)
/*     */       {
/* 113 */         Log.println("----------UnknownHostException----------");
/* 114 */         if ((i == 2) && (!this.cancelled))
/*     */         {
/* 116 */           MainJFrame.instance.setNetStatus(this.getor, "<html><font color=\"#ff0000\" face=\"黑体\" size=\"4\">网络连接失败，可能网络断了</font></html>");
/* 117 */           this.httpResProcessor.processUnknownHostException(e);
/*     */         }
/*     */       }
/*     */       catch (SocketTimeoutException e)
/*     */       {
/* 122 */         Log.println("------" + this.getor.getUser().name + "----SocketTimeoutException----------" + e.getMessage());
/*     */ 
/* 126 */         if ((i == 2) && (!this.cancelled))
/*     */         {
/* 128 */           MainJFrame.instance.setNetStatus(this.getor, "<html><font color=\"#ff0000\" face=\"黑体\" size=\"4\">连接服务器一直超时，无法连接上</font></html>");
/* 129 */           MainJFrame.instance.timoutcount += 1;
/* 130 */           if ((MainJFrame.instance.timoutcount > 5) && (!WarningDlg.isShowed));
/* 133 */           this.httpResProcessor.processSocketTimeoutException(e);
/*     */         }
/*     */       }
/*     */       catch (IOException e)
/*     */       {
/* 138 */         MainJFrame.instance.timoutcount += 1;
/* 139 */         if ((MainJFrame.instance.timoutcount > 5) && (!WarningDlg.isShowed));
/* 142 */         Log.println("------" + this.getor.getUser().name + "----IOException----------" + e.getMessage());
/*     */ 
/* 144 */         if ((i == 2) && (!this.cancelled)) {
/* 145 */           if ((e != null) && (e.getMessage() != null)) {
/* 146 */             if (e.getMessage().indexOf("503") >= 0) {
/* 147 */               MainJFrame.instance.setNetStatus(this.getor, "<html><font color=\"#ff0000\" face=\"黑体\" size=\"4\">服务器P2P代理模块没有运行</font></html>");
/*     */             }
/* 149 */             else if (e.getMessage().equals("Connection refused: connect")) {
/* 150 */               MainJFrame.instance.setNetStatus(this.getor, "<html><font color=\"#ff0000\" face=\"黑体\">服务器连接失败，可能服务器没有运行</font></html>");
/*     */             }
/* 152 */             else if (e.getMessage().equals("Connection reset")) {
/* 153 */               MainJFrame.instance.setNetStatus(this.getor, "<html><font color=\"#ff0000\" face=\"黑体\">连接被重置，有可能服务器访问量太大，或者发送的文件太大，或者服务器已经崩溃</font></html>");
/* 154 */               MainJFrame.instance.timoutcount += 1;
/* 155 */               if ((MainJFrame.instance.timoutcount > 5) && (!WarningDlg.isShowed));
/* 158 */               JOptionPane.showMessageDialog(MainJFrame.instance, "<html><font color=\"#ff0000\" face=\"黑体\">连接被重置，有可能服务器访问量太大，或者发送的文件太大，或者服务器已经崩溃</font></html>", "连接重置", 0, null);
/*     */             } else {
/* 160 */               MainJFrame.instance.setNetStatus(this.getor, "<html><font color=\"#ff0000\" face=\"黑体\">服务器连接失败：" + e.getMessage() + "</font></html>");
/*     */             }
/*     */           }
/* 163 */           this.httpResProcessor.processIOException(e);
/*     */         }
/*     */ 
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 170 */         if ((i == 2) && (!this.cancelled)) {
/* 171 */           MainJFrame.instance.setNetStatus(this.getor, "<html><font color=\"#ff0000\" face=\"黑体\">服务器连接失败：" + e.getMessage() + "，<br/>如果异常信息为null,说明是网络返回后的处理出现了未捕捉到的异常<br/>" + this.url + "</font></html>");
/*     */ 
/* 173 */           this.httpResProcessor.processException(e);
/*     */         }
/* 175 */         Log.println("----------Exception----------");
/* 176 */         e.printStackTrace();
/*     */       } finally {
/*     */         try {
/* 179 */           if (out != null)
/* 180 */             out.close();
/* 181 */           if (in != null) {
/* 182 */             in.close();
/*     */           }
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/* 187 */           e.printStackTrace();
/*     */         }
/*     */       }
/* 190 */       if (!this.cancelled)
/* 191 */         this.httpResProcessor.processConnectionClosed();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void doRequest() {
/* 196 */     OutputStream out = null;
/* 197 */     InputStream in = null;
/* 198 */     HttpURLConnection conn = null;
/* 199 */     boolean tryAgain = true;
/*     */ 
/* 201 */     for (int i = 0; (i < 1) && (tryAgain) && ((this.getor == null) || (this.getor.running)); i++) {
/* 202 */       if (this.getor != null)
/* 203 */         //Log.println(this.getor.getUser().name + this.url);
/*     */       try
/*     */       {
	              Proxy proxy = new Proxy(Proxy.Type.HTTP, "proxy.sunjapan.com.cn", 8080, "zhouhl", "102813");
/* 206 */         conn = NetworkUtils.getConnection(this.url, proxy);
/* 207 */         if (this.headers != null)
/*     */         {
/* 209 */           Iterator it = this.headers.entrySet().iterator();
/* 210 */           while (it.hasNext()) {
/* 211 */             Map.Entry entry = (Map.Entry)it.next();
/* 212 */             String key = (String)entry.getKey();
/* 213 */             String value = (String)entry.getValue();
/* 214 */             conn.addRequestProperty(key, value);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 219 */         conn.setConnectTimeout(this.timeout);
/* 220 */         conn.setReadTimeout(this.timeout);
/* 221 */         in = conn.getInputStream();
/*     */ 
/* 223 */         Set entries = conn.getHeaderFields().entrySet();
/*     */ 
/* 225 */         String content_type = conn.getHeaderField("content-encoding");
/* 226 */         if ((content_type != null) && (content_type.startsWith("gzip"))) {
/* 227 */           in = new GZIPInputStream(in);
/*     */         }
/* 229 */         byte[] bytes = new byte[4096];
/* 230 */         StringBuffer sb = new StringBuffer(4096);
/* 231 */         int len = 0;
/* 232 */         while ((len = in.read(bytes)) > 0) {
/* 233 */           sb.append(new String(bytes, 0, len, "UTF-8").trim());
/*     */         }
/*     */ 
/* 236 */         this.httpResProcessor.processRespose(new HttpResponse(entries, sb));
/* 237 */         in.close();
/*     */ 
/* 239 */         tryAgain = false;
/*     */       }
/*     */       catch (FileNotFoundException e) {
	e.printStackTrace();
/* 242 */         Log.println("----------FileNotFoundException----------");
/* 243 */         tryAgain = true;
/* 244 */         if ((i == 2) && (!this.cancelled))
/*     */         {
/* 246 */           MainJFrame.instance.setNetStatus(this.getor, "<html><font color=\"#ff0000\" face=\"黑体\" size=\"4\">连接服务器失败，可能是服务器没有运行</font></html>");
/* 247 */           this.httpResProcessor.processFileNotFoundException(e);
/*     */         }
/*     */ 
/*     */       }
/*     */       catch (UnknownHostException e)
/*     */       {e.printStackTrace();
/* 253 */         Log.println("----------UnknownHostException----------");
/* 254 */         if ((i == 2) && (!this.cancelled)) {
/* 255 */           MainJFrame.instance.setNetStatus(this.getor, "<html><font color=\"#ff0000\" face=\"黑体\" size=\"4\">网络连接失败，可能网络断了</font></html>");
/*     */ 
/* 257 */           this.httpResProcessor.processUnknownHostException(e);
/*     */         }
/*     */ 
/*     */       }
/*     */       catch (SocketTimeoutException e)
/*     */       {e.printStackTrace();
/* 263 */         if (this.getor != null) {
/* 264 */           Log.println("------" + this.getor.getUser().name + "----SocketTimeoutException----------" + e.getMessage());
/*     */         }
/*     */ 
/* 267 */         if ((i == 2) && (!this.cancelled))
/*     */         {
/* 269 */           MainJFrame.instance.setNetStatus(this.getor, "<html><font color=\"#ff0000\" face=\"黑体\" size=\"4\">连接服务器一直超时，无法连接上</font></html>");
/* 270 */           MainJFrame.instance.timoutcount += 1;
/* 271 */           if ((MainJFrame.instance.timoutcount > 5) && (!WarningDlg.isShowed));
/* 274 */           this.httpResProcessor.processSocketTimeoutException(e);
/*     */         }
/*     */ 
/*     */       }
/*     */       catch (IOException e)
/*     */       {e.printStackTrace();
/* 280 */         Log.println("------" + this.getor.getUser().name + "----IOException----------" + e.getMessage());
/*     */ 
/* 282 */         if ((i == 2) && (!this.cancelled)) {
/* 283 */           if ((e != null) && (e.getMessage() != null)) {
/* 284 */             if (e.getMessage().indexOf("503") >= 0) {
/* 285 */               MainJFrame.instance.setNetStatus(this.getor, "<html><font color=\"#ff0000\" face=\"黑体\" size=\"4\">服务器P2P代理模块没有运行</font></html>");
/*     */             }
/* 287 */             else if (e.getMessage().equals("Connection refused: connect")) {
/* 288 */               MainJFrame.instance.setNetStatus(this.getor, "<html><font color=\"#ff0000\" face=\"黑体\">服务器连接失败，可能服务器没有运行</font></html>");
/*     */             }
/* 290 */             else if (e.getMessage().equals("Connection reset")) {
/* 291 */               MainJFrame.instance.timoutcount += 1;
/* 292 */               if ((MainJFrame.instance.timoutcount > 5) && (!WarningDlg.isShowed));
/* 295 */               MainJFrame.instance.setNetStatus(this.getor, "<html><font color=\"#ff0000\" face=\"黑体\">连接被重置，有可能服务器访问量太大，或者发送的文件太大，或者服务器已经崩溃</font></html>");
/*     */             }
/*     */             else {
/* 298 */               MainJFrame.instance.setNetStatus(this.getor, "<html><font color=\"#ff0000\" face=\"黑体\">服务器连接失败：" + e.getMessage() + "</font></html>");
/*     */             }
/*     */           }
/* 301 */           this.httpResProcessor.processIOException(e);
/*     */         }
/*     */ 
/*     */       }
/*     */       catch (Exception e)
/*     */       {e.printStackTrace();
/* 308 */         if ((i == 2) && (!this.cancelled)) {
/* 309 */           MainJFrame.instance.setNetStatus(this.getor, "<html><font color=\"#ff0000\" face=\"黑体\">服务器连接失败：" + e.getMessage() + "，<br/>如果异常信息为null,说明是网络返回后的处理出现了未捕捉到的异常<br/>" + this.url + "</font></html>");
/*     */ 
/* 311 */           this.httpResProcessor.processException(e);
/*     */         }
/* 313 */         Log.println("----------Exception----------");
/* 314 */         e.printStackTrace();
/*     */       } finally {
/*     */         try {
/* 317 */           if (out != null)
/* 318 */             out.close();
/* 319 */           if (in != null) {
/* 320 */             in.close();
/*     */           }
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/* 325 */           e.printStackTrace();
/*     */         }
/*     */       }
/* 328 */       if (!this.cancelled)
/* 329 */         this.httpResProcessor.processConnectionClosed();
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Administrator\桌面\5-22\3\
 * Qualified Name:     networking.http.HttpGet
 * JD-Core Version:    0.6.0
 */