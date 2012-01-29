/*    */ package net.sf.jpd.netsettings;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.net.HttpURLConnection;
/*    */ import java.net.URL;
/*    */ 
/*    */ public class NetworkUtils
/*    */ {
/*    */   public static HttpURLConnection getConnection(String urlString, Proxy proxy)
/*    */     throws IOException
/*    */   {
/* 18 */     HttpURLConnection.setFollowRedirects(false);
/* 19 */     URL url = new URL(urlString);
/*    */     HttpURLConnection connection;
/* 22 */     if (proxy == null)
/*    */     {
/* 24 */       connection = (HttpURLConnection)url.openConnection();
/*    */     }
/*    */     else
/*    */     {
/* 29 */       connection = (HttpURLConnection)proxy.getConnection(url);
/*    */     }
/*    */ 
/* 32 */     return connection;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Administrator\桌面\5-22\3\
 * Qualified Name:     net.sf.jpd.netsettings.NetworkUtils
 * JD-Core Version:    0.6.0
 */