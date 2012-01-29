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
/*    */ public class S_VIGenerator
/*    */   implements HttpResponseProcessor
/*    */ {
/*    */   private static final String URL = "/b/ss/applecnglobal/1/H.22.1/s9340292037905?AQB=1&ndh=1&t=13%2F4%2F2011%2018%3A35%3A34%205%20-480&pageName=retail%20store%20-%20index%20(cn)&g=http%3A%2F%2Fwww.apple.com.cn%2Fretail%2F&r=http%3A%2F%2Fwww.apple.com.cn%2Fretail%2Fiphone4%2F&cc=USD&vvp=DFA%231513429%3Av46%3D%5B%5B%22DFA-%22%2Blis%2B%22-%22%2Blip%2B%22-%22%2Blastimp%2B%22-%22%2Blastimptime%2B%22-%22%2Blcs%2B%22-%22%2Blcp%2B%22-%22%2Blastclk%2B%22-%22%2Blastclktime%5D%5D&ch=www.cn.retailstore&c4=http%3A%2F%2Fwww.apple.com.cn%2Fretail%2F&c5=win32&c6=%3A%20retail%20store%20-%20index%20(cn)&c9=windows&c15=no%20zip&c18=quicktime%207.x&c19=flash%2010&c20=non-store%20kiosk&c44=applecnglobal&c48=1&c49=D%3Ds_vi&c50=retailstore%3D1&s=1280x800&c=24&j=1.7&v=Y&k=Y&bw=1280&bh=591&p=Cooliris%20embedded%20in%20a%20tab%3BMicrosoft%20Office%202003%3BAlipay%20security%20control%3BQuickTime%20Plug-in%207.6.6%3BJava%20Deployment%20Toolkit%206.0.210.6%3Bnpruntime%20scriptable%20example%20plugin%3BAliWangWang%20Plug-In%20For%20Firefox%20and%20Netscape%3BMozilla%20Default%20Plug-in%3BAdobe%20Acrobat%3BGoogle%20Update%3BShockwave%20Flash%3BiTunes%20Application%20Detector%3BJava(TM)%20Platform%20SE%206%20U21%3BSilverlight%20Plug-In%3BWindows%20Presentation%20Foundation%3BRealPlayer%20Version%20Plugin%3BRealPlayer(tm)%20G2%20LiveConnect-Enabled%20Plug-In%20(32-bit)%20%3BVLC%20Multimedia%20Plug-in%3BThunder%20DapCtrl%20Plugin%3BWindows%20Media%20Player%20Plug-in%20Dynamic%20Link";
/*    */   private String s_vi;
/*    */   private Getor getr;
/*    */ 
/*    */   public String getS_VI(Getor getr, String cookie)
/*    */   {
/* 29 */     this.getr = getr;
/* 30 */     HashMap headers = new HashMap(5);
/* 31 */     headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
/* 32 */     headers.put("Referer", "http://www.apple.com.cn/");
/* 33 */     headers.put("Accept-Language", "zh-cn;q=0.5");
/* 34 */     headers.put("Accept-Encoding", "gzip, deflate");
/* 35 */     headers.put("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.13) Gecko/20101203 ZiiMeet v2.00.07 Firefox/3.6.13 ( .NET CLR 3.5.30729)"); headers.put("Content-Type", "application/x-www-form-urlencoded");
/* 36 */     headers.put("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
/* 37 */     headers.put("Connection", "Keep-Alive");
/* 38 */     headers.put("Keep-Alive", "300");
/* 39 */     headers.put("Cookie", cookie);
/*    */ 
/* 41 */     HttpGet getor = new HttpGet(getr, "/b/ss/applecnglobal/1/H.22.1/s9340292037905?AQB=1&ndh=1&t=13%2F4%2F2011%2018%3A35%3A34%205%20-480&pageName=retail%20store%20-%20index%20(cn)&g=http%3A%2F%2Fwww.apple.com.cn%2Fretail%2F&r=http%3A%2F%2Fwww.apple.com.cn%2Fretail%2Fiphone4%2F&cc=USD&vvp=DFA%231513429%3Av46%3D%5B%5B%22DFA-%22%2Blis%2B%22-%22%2Blip%2B%22-%22%2Blastimp%2B%22-%22%2Blastimptime%2B%22-%22%2Blcs%2B%22-%22%2Blcp%2B%22-%22%2Blastclk%2B%22-%22%2Blastclktime%5D%5D&ch=www.cn.retailstore&c4=http%3A%2F%2Fwww.apple.com.cn%2Fretail%2F&c5=win32&c6=%3A%20retail%20store%20-%20index%20(cn)&c9=windows&c15=no%20zip&c18=quicktime%207.x&c19=flash%2010&c20=non-store%20kiosk&c44=applecnglobal&c48=1&c49=D%3Ds_vi&c50=retailstore%3D1&s=1280x800&c=24&j=1.7&v=Y&k=Y&bw=1280&bh=591&p=Cooliris%20embedded%20in%20a%20tab%3BMicrosoft%20Office%202003%3BAlipay%20security%20control%3BQuickTime%20Plug-in%207.6.6%3BJava%20Deployment%20Toolkit%206.0.210.6%3Bnpruntime%20scriptable%20example%20plugin%3BAliWangWang%20Plug-In%20For%20Firefox%20and%20Netscape%3BMozilla%20Default%20Plug-in%3BAdobe%20Acrobat%3BGoogle%20Update%3BShockwave%20Flash%3BiTunes%20Application%20Detector%3BJava(TM)%20Platform%20SE%206%20U21%3BSilverlight%20Plug-In%3BWindows%20Presentation%20Foundation%3BRealPlayer%20Version%20Plugin%3BRealPlayer(tm)%20G2%20LiveConnect-Enabled%20Plug-In%20(32-bit)%20%3BVLC%20Multimedia%20Plug-in%3BThunder%20DapCtrl%20Plugin%3BWindows%20Media%20Player%20Plug-in%20Dynamic%20Link", headers, this);
/* 42 */     getor.doRequest();
/* 43 */     return this.s_vi;
/*    */   }
/*    */ 
/*    */   public void processRespose(Object body)
/*    */   {
/* 48 */     if (body != null) {
/* 49 */       HttpResponse res = (HttpResponse)body;
/* 50 */       Set set = res.header;
/* 51 */       if (set != null) {
/* 52 */         Iterator it = set.iterator();
/* 53 */         while (it.hasNext()) {
/* 54 */           Map.Entry entry = (Map.Entry)it.next();
/*    */ 
/* 56 */           Object key = entry.getKey();
/*    */ 
/* 58 */           if ((key != null) && 
/* 59 */             (key.toString().toLowerCase().equals("set-cookie"))) {
/* 60 */             String value = entry.getValue().toString();
/* 61 */             value = value.substring(1, value.length() - 1);
/* 62 */             Log.println(value);
/* 63 */             int index = value.indexOf("s_vi");
/* 64 */             if (index >= 0) {
/* 65 */               value = value.substring(index, value.indexOf(";", index + 1));
/* 66 */               this.s_vi = value;
/* 67 */               break;
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
 * Qualified Name:     networking.http.S_VIGenerator
 * JD-Core Version:    0.6.0
 */