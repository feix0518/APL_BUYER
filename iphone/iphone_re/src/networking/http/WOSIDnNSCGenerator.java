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
/*    */ public class WOSIDnNSCGenerator
/*    */   implements HttpResponseProcessor
/*    */ {
/*    */   private String wosid;
/*    */   private String nsc_;
/*    */   private String productSelectUrl;
/*    */   private String para1;
/*    */   private String para2;
/*    */   private Getor getr;
/*    */   private String cookie;
/*    */   private String url;
/*    */ 
/*    */   public WOSIDnNSCGenerator(Getor getr, String Url, String cookie)
/*    */   {
/* 41 */     this.getr = getr;
/* 42 */     this.url = Url;
/* 43 */     this.cookie = cookie;
/*    */   }
/*    */   public String[] getWOSIDnNSC() {
/* 46 */     HashMap headers = new HashMap(15);
///* 47 */     headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
///* 48 */     headers.put("Referer", "http://www.apple.com.cn/retail/iphone4/");
///* 49 */     headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
///* 50 */     headers.put("Accept-Language", "zh-cn;q=0.5");
///* 51 */     headers.put("Accept-Encoding", "gzip, deflate");
///* 52 */     headers.put("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.13) Gecko/20101203 ZiiMeet v2.00.07 Firefox/3.6.13 ( .NET CLR 3.5.30729)"); headers.put("Content-Type", "application/x-www-form-urlencoded");
///* 53 */     headers.put("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
///* 54 */     headers.put("Connection", "Keep-Alive");
///* 55 */     headers.put("Keep-Alive", "300");
///* 56 */     headers.put("Cookie", "RPRCookie=zh|CN; s_orientationHeight=675; s_ppv=start%2520-%2520index%2520%2528JP%2529%2C44%2C44%2C675%2C; dfa_cookie=applejpglobal%2Capplejpstartpage%2Cappleglobal%2Cappleretail%2Capplecnglobal; s_cc=true; s_orientation=%5B%5BB%5D%5D; s_pathLength=startpage%3D3%2Cretail%3D6%2C; s_pv=start%20-%20index%20(JP); s_ria=Flash%20Not%20Detected%7C; s_sq=%5B%5BB%5D%5D; ccl=Z5FQgXHLJB2WcxK4ft+nCQ==; geo=CN; s_invisit_n_us=93%2C; s_vnum_n_us=%2C93%7C1%2C19%7C1%2C3%7C1%2C0%7C1%2C93%7C1%2C93%7C1%2C93%7C1%2C93%7C1%2C3%7C1%2C8%7C1%2C93%7C1%2C93%7C1%2C93%7C1%2C93%7C1%2C93%7C1%2C93%7C1%2C93%7C1%2C93%7C1%2C93%7C1%2C93%7C1; s_invisit_n_jp=78%2C; s_vnum_n_jp=%2C78%7C1%2C78%7C1%2C78%7C1%2C8%7C1%2C78%7C1%2C78%7C1%2C78%7C1%2C78%7C1%2C78%7C1; ds01=A2967FA47A76861DD52C0F2A1DA4C5108BD5D8EF4EA544161F400E548000BD63; DefaultAppleID=zhouhlcn; s_membership=1%3Aaid; POD=cn~zh; dssid2=5ee37ae2-ab7f-4b53-a725-de39f3d2d32d; s_vi=[CS]v1|276A3D5C8515B78A-400001A7C02B3DF4[CE]");
///*    */ 
headers.put("Host","reserve.apple.com");
headers.put("User-Agent","Mozilla/5.0 (Windows NT 5.1) AppleWebKit/534.51.22 (KHTML, like Gecko) Version/5.1.1 Safari/534.51.22");
headers.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
headers.put("Accept-Language","ja-JP");
headers.put("Accept-Encoding","gzip, deflate");
headers.put("Proxy-Authorization","Basic emhvdWhsOjEwMjgxMw==");
headers.put("Connection","keep-alive");
headers.put("Proxy-Connection","keep-alive");
headers.put("Cookie", "RPRCookie=zh|CN; s_orientationHeight=675; s_ppv=start%2520-%2520index%2520%2528JP%2529%2C44%2C44%2C675%2C; dfa_cookie=applejpglobal%2Capplejpstartpage%2Cappleglobal%2Cappleretail%2Capplecnglobal; s_cc=true; s_orientation=%5B%5BB%5D%5D; s_pathLength=startpage%3D3%2Cretail%3D6%2C; s_pv=start%20-%20index%20(JP); s_ria=Flash%20Not%20Detected%7C; s_sq=%5B%5BB%5D%5D; ccl=Z5FQgXHLJB2WcxK4ft+nCQ==; geo=CN; s_invisit_n_us=93%2C; s_vnum_n_us=%2C93%7C1%2C19%7C1%2C3%7C1%2C0%7C1%2C93%7C1%2C93%7C1%2C93%7C1%2C93%7C1%2C3%7C1%2C8%7C1%2C93%7C1%2C93%7C1%2C93%7C1%2C93%7C1%2C93%7C1%2C93%7C1%2C93%7C1%2C93%7C1%2C93%7C1%2C93%7C1; s_invisit_n_jp=78%2C; s_vnum_n_jp=%2C78%7C1%2C78%7C1%2C78%7C1%2C8%7C1%2C78%7C1%2C78%7C1%2C78%7C1%2C78%7C1%2C78%7C1; ds01=A2967FA47A76861DD52C0F2A1DA4C5108BD5D8EF4EA544161F400E548000BD63; DefaultAppleID=zhouhlcn; s_membership=1%3Aaid; POD=cn~zh; dssid2=5ee37ae2-ab7f-4b53-a725-de39f3d2d32d; s_vi=[CS]v1|276A3D5C8515B78A-400001A7C02B3DF4[CE]");
/* 58 */     HttpGet getor = new HttpGet(this.getr, this.url, headers, this);
/* 59 */     getor.doRequest();
/*    */ 
/* 62 */     String[] values = new String[5];
/* 63 */     values[0] = this.wosid;
/* 64 */     values[1] = this.nsc_;
/* 65 */     values[2] = this.productSelectUrl;
/* 66 */     values[3] = this.para1;
/* 67 */     values[4] = this.para2;
/* 68 */     return values;
/*    */   }
/*    */ 
/*    */   public void processRespose(Object body)
/*    */   {
/* 73 */     if (body != null) {
/* 74 */       HttpResponse res = (HttpResponse)body;
/* 75 */       StringBuffer sb = (StringBuffer)res.body;
//System.out.println(sb.toString());
/* 76 */       String match = "action=\"/WebObjects/ProductReservation.woa/wo/";
/* 77 */       int index = sb.indexOf(match);
/* 78 */       if (index > 0) {
/* 79 */         int lastIndex = sb.indexOf("\"", index + match.length());
/* 80 */         this.productSelectUrl = ("http://reserve.apple.com/WebObjects/ProductReservation.woa/wo/" + sb.substring(index + match.length(), lastIndex));
/* 81 */         Log.println("productSelectUrl=" + this.productSelectUrl);
/*    */       }
/*    */ 
/* 84 */       match = "id=\"isCartEmpty\"";
/* 85 */       index = sb.indexOf(match);
/* 86 */       if (index > 0) {
/* 87 */         index = sb.indexOf("name", index + 1);
/* 88 */         if (index > 0) {
/* 89 */           index = sb.indexOf("\"", index + 1);
/* 90 */           int lastindex = sb.indexOf("\"", index + 1);
/* 91 */           this.para1 = sb.substring(index + 1, lastindex);
/*    */         }
/*    */       }
/*    */ 
/* 95 */       match = "type=\"submit\"";
/* 96 */       index = sb.indexOf(match, index + 1);
/* 97 */       if (index > 0) {
/* 98 */         index = sb.indexOf("name", index + 1);
/* 99 */         if (index > 0) {
/* 100 */           index = sb.indexOf("\"", index + 1);
/* 101 */           int lastindex = sb.indexOf("\"", index + 1);
/* 102 */           this.para2 = sb.substring(index + 1, lastindex);
/*    */         }
/*    */ 
/*    */       }
/*    */ 
/* 107 */       Set set = res.header;
/* 108 */       if (set != null) {
/* 109 */         Iterator it = set.iterator();
/* 110 */         while (it.hasNext()) {
/* 111 */           Map.Entry entry = (Map.Entry)it.next();
/* 112 */           Object key = entry.getKey();
/*    */ 
/* 114 */           if (key != null)
/*    */           {
/* 116 */             if (key.toString().toLowerCase().equals("set-cookie")) {
/* 117 */               String value = entry.getValue().toString();
/* 118 */               value = value.substring(1, value.length() - 1);
/*    */ 
/* 120 */               index = value.indexOf("wosid");
/* 121 */               if (index >= 0) {
/* 122 */                 String value_wosid = value.substring(index, value.indexOf(";", index + 1));
/* 123 */                 this.wosid = value_wosid;
/*    */               }
///*    */               else
///*    */               {
/* 127 */                 index = value.indexOf("NSC_");
/* 128 */                 if (index >= 0) {
/* 129 */                   String value_nsc = value.substring(index, value.indexOf(";", index + 1));
/* 130 */                   this.nsc_ = value_nsc;
/*    */                 }
///*    */               }
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
 * Qualified Name:     networking.http.WOSIDnNSCGenerator
 * JD-Core Version:    0.6.0
 */