/*    */ package networking.http;
/*    */ 
/*    */ import iphonebuyer.Getor;
/*    */ import iphonebuyer.Log;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.IOException;
/*    */ import java.net.SocketTimeoutException;
/*    */ import java.net.UnknownHostException;
/*    */ import java.util.HashMap;
/*    */ import java.util.LinkedHashMap;
/*    */ 
/*    */ public class FetchTime
/*    */   implements HttpResponseProcessor
/*    */ {
/*    */   public static final int FETCH_TIME_OK = 1;
/*    */   public static final int FETCH_TIME_SESSION_TIMEOUT = 2;
/*    */   public static final int FETCH_TIME_NET_ERROR = 3;
/*    */   public static final int FETCH_TIME_NO_DATETIME = 4;
/*    */   public static final int FETCH_TIME_PARSE_ERROR = 5;
/* 29 */   private int status = 3;
/*    */   private String pickupDate;
/*    */   private String formattedPickuDate;
/* 33 */   private static LinkedHashMap<String, String> pickupTimeList = new LinkedHashMap(12);
/*    */   private Getor getr;
/* 35 */   LinkedHashMap<String, String> pickupTimes = new LinkedHashMap(10);
/*    */   private String referUrl;
/*    */   private String cookie;
/*    */   private String url;
/*    */ 
/*    */   public FetchTime(Getor getr, String url, String referUrl, String cookie)
/*    */   {
/* 40 */     this.getr = getr;
/* 41 */     this.referUrl = referUrl;
/* 42 */     this.cookie = cookie;
/* 43 */     this.url = url;
/*    */   }
/*    */   public int fetch() {
/* 46 */     HashMap headers = new HashMap(15);
///* 47 */     headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
///* 48 */     headers.put("Referer", this.referUrl);
///* 49 */     headers.put("Accept-Language", "zh-cn,zh;q=0.5");
///* 50 */     headers.put("Accept-Encoding", "gzip, deflate, x-gzip, identity, *;q=0");
///* 51 */     headers.put("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
///* 52 */     headers.put("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1) ;  Embedded Web Browser from: http://bsalsa.com/; song2009 Embedded Web Browser from: http://bsalsa.com/; WebSaver; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.648; .NET CLR 3.5.21022; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; AskTB5.6; ZiiMeet v2.00.07)");
///* 53 */     headers.put("Content-Type", "application/x-www-form-urlencoded");
///* 54 */     headers.put("Connection", "Keep-Alive");
///* 55 */     headers.put("Keep-Alive", "300");
///* 56 */     headers.put("Pragma", "no-cache");
///* 57 */     headers.put("Cache-Control", "no-cache");
headers.put("Host", " reserve.apple.com");
headers.put("User-Agent", " Mozilla/5.0 (Windows NT 5.1) AppleWebKit/534.51.22 (KHTML, like Gecko) Version/5.1.1 Safari/534.51.22");
headers.put("Content-Length", " 18");
headers.put("Accept", " */*");
headers.put("Origin", " https://reserve.apple.com");
headers.put("Content-Type", " application/x-www-form-urlencoded");
headers.put("Referer", " https://reserve.apple.com/WebObjects/ProductReservation.woa/wo/50.0.1.0.1.3.0.7.1.10.1");
headers.put("Accept-Language", " ja-JP");
headers.put("Accept-Encoding", " gzip, deflate");
headers.put("Connection", " keep-alive");
headers.put("Proxy-Connection", " keep-alive");
/* 58 */     headers.put("Cookie", this.cookie);
/*    */     try {
/* 60 */       HttpPost poster = new HttpPost(this.getr, this.url, headers, "lang=zh&country=CN", this);
/* 61 */       poster.doRequest(true);
/*    */     } catch (Exception e) {
/* 63 */       e.printStackTrace();
/*    */     }
/* 65 */     return this.status;
/*    */   }
/*    */ 
/*    */   public String getPickupDate() {
/* 69 */     return this.pickupDate;
/*    */   }
/*    */   public String getFormattedPickupDate() {
/* 72 */     return this.formattedPickuDate;
/*    */   }
/*    */ 
/*    */   public LinkedHashMap<String, String> getPickupTimeList() {
/* 76 */     return pickupTimeList;
/*    */   }
/*    */   public void setPickupTimeList(LinkedHashMap<String, String> list) {
	/* 76 */     this.pickupTimeList = list;
	/*    */   }
/*    */ 
/*    */   public void processRespose(Object body) {
/* 80 */     Log.println("-----------------------FetchPickupTimeDetail---------------------");
/* 81 */     if (body != null) {
/* 82 */       HttpResponse res = (HttpResponse)body;
/* 83 */       StringBuffer sb = (StringBuffer)res.body;
/* 84 */       Log.println(sb);
/* 85 */       int index = sb.indexOf("this.objectWithURIRepresentation");
/* 86 */       if (index >= 0) {
/* 87 */         this.status = 1;
/* 88 */         if ((index = sb.indexOf("//cust/PickupDay/", index + 1)) >= 0) {
/* 89 */           this.pickupDate = null;
/* 90 */           int lastIndex = sb.indexOf("'", index + 1);
/* 91 */           if (lastIndex > 0) {
/* 92 */             this.pickupDate = sb.substring(index + "//cust/PickupDay/".length(), lastIndex);
/* 93 */             Log.println("-------------------pickupDate=" + this.pickupDate + "----------------------------");
/* 94 */             index = sb.indexOf("name", index + 1);
/* 95 */             if (index >= 0) {
/* 97 */               index = sb.indexOf("'", index + 1);
/* 98 */               if (index >= 0) {
/* 99 */                 lastIndex = sb.indexOf("'", index + 1);
/* 100 */                 if (lastIndex >= 0) {
/* 101 */                   this.formattedPickuDate = sb.substring(index + 1, lastIndex);
/* 102 */                   Log.println("formattedPickuDate:" + this.formattedPickuDate);
/*    */                 }
/*    */               }
/*    */             }
/* 106 */             index = sb.indexOf("pickupTime: [");
/* 107 */             lastIndex = sb.indexOf("]", index + 1);
/* 108 */             String array = sb.substring(index + "pickupTime: [".length(), lastIndex);
/* 109 */             while (array != null) {
/* 110 */               index = array.indexOf("x-coredata://cust/TimeSlot/");
/* 111 */               if (index < 0) break;
/* 112 */               lastIndex = array.indexOf("'", index + 1);
/* 113 */               String timeslot = array.substring(index, lastIndex);
/* 114 */               Log.println(timeslot);
/* 115 */               index = array.indexOf("'", lastIndex + 1);
/* 116 */               String formattedTimeSlot = null;
/* 117 */               if (index >= 0)
/*    */               {
/* 119 */                 lastIndex = array.indexOf("'", index + 1);
/* 120 */                 formattedTimeSlot = array.substring(index + 1, lastIndex);
/* 121 */                 Log.println(formattedTimeSlot);
/*    */               }
/*    */ 
/* 124 */               pickupTimeList.put(timeslot, formattedTimeSlot);
/* 125 */               array = array.substring(lastIndex + 1);
/* 126 */               this.status = 1;
/*    */             }
/*    */ 
/*    */           }
/*    */ 
/*    */         }
/*    */ 
/* 134 */         return;
/*    */       }
/*    */ 
/* 137 */       if ((sb.indexOf("您的会话已超时") >= 0) || (sb.indexOf("Unable to connect to server") >= 0) || (sb.indexOf("请再试一次") > 0))
/*    */       {
/* 140 */         Log.println(sb);
/* 141 */         this.status = 2;
/* 142 */         return;
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
 * Qualified Name:     networking.http.FetchTime
 * JD-Core Version:    0.6.0
 */