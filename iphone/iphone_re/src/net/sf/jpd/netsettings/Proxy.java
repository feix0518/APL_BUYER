/*    */ package net.sf.jpd.netsettings;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.Serializable;
/*    */ import java.net.Proxy.Type;
/*    */ import java.net.Socket;
/*    */ import java.net.URL;
/*    */ import java.net.URLConnection;
/*    */ import java.net.UnknownHostException;
/*    */ import sun.misc.BASE64Encoder;
/*    */ 
/*    */ public class Proxy extends java.net.Proxy
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 7495084217081194366L;
/*    */   private String url;
/*    */   private int port;
/*    */   private String user;
/*    */   private String password;
/*    */ 
/*    */   public Proxy(Proxy.Type type, String url, int port, String user, String password)
/*    */     throws UnknownHostException, IOException
/*    */   {
/* 26 */     super(type, new Socket(url, port).getRemoteSocketAddress());
/* 27 */     this.url = url;
/* 28 */     this.port = port;
/* 29 */     this.user = user;
/* 30 */     this.password = password;
/*    */   }
/*    */ 
/*    */   public URLConnection getConnection(URL u) throws IOException {
	
/* 34 */     URLConnection con = u.openConnection(this);
/* 35 */     BASE64Encoder encoder = new BASE64Encoder();
/* 36 */     String encodedUserPwd = encoder.encode((this.user + ':' + this.password).getBytes());
/* 37 */     con.setRequestProperty("Proxy-Authorization", "Basic " + encodedUserPwd);
/* 38 */     return con;
/*    */   }
/*    */ 
/*    */   public String getPassword() {
/* 42 */     return this.password;
/*    */   }
/*    */ 
/*    */   public String getUrl() {
/* 46 */     return this.url;
/*    */   }
/*    */ 
/*    */   public int getPort() {
/* 50 */     return this.port;
/*    */   }
/*    */ 
/*    */   public String getUser() {
/* 54 */     return this.user;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Administrator\桌面\5-22\3\
 * Qualified Name:     net.sf.jpd.netsettings.Proxy
 * JD-Core Version:    0.6.0
 */