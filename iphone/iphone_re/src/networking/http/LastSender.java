/*     */ package networking.http;
/*     */ 
/*     */ import iphonebuyer.Getor;
/*     */ import iphonebuyer.Log;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.net.SocketTimeoutException;
/*     */ import java.net.URLEncoder;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ public class LastSender
/*     */   implements HttpResponseProcessor
/*     */ {
/*     */   public static final int SEND_OK = 1;
/*     */   public static final int SEND_SESSION_TIMEOUT = 2;
/*     */   public static final int SEND_NET_ERROR = 3;
/*     */   public static final int SEND_FAILED = 4;
/*     */   public static final int SEND_VC_ERROR = 5;
/*     */   public static final int SEND_MAX_EX = 6;
/*     */   public static final int SEND_REPEAT_ID = 7;
/*     */   public static final int SEND_LOST = 8;
/*  36 */   private int status = 3;
/*     */   private String url;
/*     */   private Getor getr;
/*     */ 
/*     */   public int send(Getor getr, String url, String referUrl, String cookie, String store,  String idcard, String pickupDate, String pickupTime)
/*     */   {
/*  40 */     this.getr = getr;
/*  41 */     HashMap headers = new HashMap(15);
///*  42 */     headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
///*  43 */     headers.put("Referer", referUrl);
///*  44 */     headers.put("Accept-Language", "zh-cn,zh;q=0.5");
///*  45 */     headers.put("Accept-Encoding", "gzip, deflate");
///*  46 */     headers.put("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1) ;  Embedded Web Browser from: http://bsalsa.com/; song2009 Embedded Web Browser from: http://bsalsa.com/; WebSaver; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.648; .NET CLR 3.5.21022; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; AskTB5.6; ZiiMeet v2.00.07)");
///*  47 */     headers.put("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
///*  48 */     headers.put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
///*  49 */     headers.put("Connection", "Keep-Alive");
///*  50 */     headers.put("Keep-Alive", "300");
headers.put("Host","reserve.apple.com");
headers.put("User-Agent","Mozilla/5.0 (Windows NT 5.1) AppleWebKit/534.51.22 (KHTML, like Gecko) Version/5.1.1 Safari/534.51.22");
headers.put("Content-Length","366");
headers.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
headers.put("Origin","https://reserve.apple.com");
headers.put("Content-Type","application/x-www-form-urlencoded");
headers.put("Accept-Language","ja-JP");
headers.put("Accept-Encoding","gzip, deflate");
headers.put("Connection","keep-alive");
headers.put("Proxy-Connection","keep-alive");
headers.put("Referer", referUrl);
headers.put("Cookie", cookie);
/*  52 */     Log.println(cookie);
/*     */     try {
///*  67 */       String value = "selectedDate=" + pickupDate 
//                             + "&selectedTime=" + URLEncoder.encode(pickupTime) 
//                             + "&selectedPickupStore=" + store 
//                             + "&showDates=Y&govtIDCheckEnabled=Y" 
//                             + "&selectedGovtID=" + idcard 
//                             + "&=%5Bobject+Object%5D&=%5Bobject+Object%5D&=" + idcard 
//                             + "&captchaTextEntered=" + code 
//                             + "&captchaNotifier=" 
//                             + "&userSelectedDateOne=" + pickupDate
//                             + "&userSelectedDateTwo=" + pickupDate 
//                             + "&1.1.0.1.3.0.7.1.10.1.53=true" 
//                             + "&1.1.0.1.3.0.7.1.10.1.51.1.7=" + code;
				 String value = "selectedDate="
				    + "&selectedTime=" 
				    + "&selectedPickupStore=" + store 
				    + "&showDates=Y&govtIDCheckEnabled=Y" 
				    + "&selectedGovtID=" + idcard 
				    + "&captchaTextEntered="
				    + "&userSelectedDate="
				    + "&userSelectedDateOne=" + pickupDate
				    + "&userSelectedDateTwo="
				    + "&1.1.0.1.3.0.7.1.10.1.53=true";


/*     */ 
/*  78 */       Log.println(value);
/*  79 */       HttpPost poster = new HttpPost(getr, url, headers, value, this);
/*  80 */       poster.doRequest(true);
/*     */     } catch (Exception e) {
/*  82 */       e.printStackTrace();
/*     */     }
/*     */ 
/*  85 */     return this.status;
/*     */   }
/*     */ 
/*     */   public void processRespose(Object body) {
/*  89 */     if (body != null) {
/*  90 */       HttpResponse res = (HttpResponse)body;
/*  91 */       StringBuffer sb = (StringBuffer)res.body;
/*  92 */       Log.println("------------------------------------------------------完成---------------------------------------------------------------------------");
	/*  93 */       Log.println(sb);
/*     */ 
/* 110 */       int index0 = sb.indexOf("confirm-final");
/* 111 */       if (index0 >= 0) {
/* 112 */         this.status = 1;
/* 113 */         return;
/*     */       }
/*     */ 
/* 116 */       int index = sb.indexOf("Error.Reservation.Create.Failed");
/* 117 */       if (index >= 0) {
/* 118 */         this.status = 8;
/* 119 */         return;
/*     */       }
/*     */ 
/* 122 */       index = sb.indexOf("查看其他Apple Store零售店是否还有其他可用预订");
/* 123 */       if (index >= 0) {
/* 124 */         this.status = 4;
/* 125 */         return;
/*     */       }
/*     */ 
/* 131 */       index = sb.indexOf("Please enter the characters you see or hear to continue");
/*     */ 
/* 133 */       if (index >= 0)
/*     */       {
/* 135 */         this.status = 5;
/* 136 */         return;
/*     */       }
/* 138 */       index = sb.indexOf("请输入您所看见或听见的词组以继续");
/* 139 */       if (index >= 0) {
/* 140 */         this.status = 5;
/* 141 */         return;
/*     */       }
/*     */ 
/* 144 */       index = sb.indexOf("Error.MaxQtyPerSku.Exceeded");
/* 145 */       if (index >= 0) {
/* 146 */         this.status = 6;
/* 147 */         return;
/*     */       }
/* 149 */       index = sb.indexOf("抱歉，您的预订限额已满。每个 Apple ID 仅限预订一件产品。");
/* 150 */       int index2 = sb.indexOf("The Maximum limit for LifeTime Reservations for passed in Government ID has been exceeded.");
/* 151 */       if ((index >= 0) || (index2 >= 0)) {
/* 152 */         this.status = 7;
/*     */ 
/* 155 */         return;
/*     */       }
/*     */ 
/* 158 */       if ((sb.indexOf("您的会话已超时") >= 0) || (sb.indexOf("Unable to connect to server") >= 0) || (sb.indexOf("请再试一次") > 0))
/*     */       {
/* 161 */         Log.println(sb);
/* 162 */         this.status = 2;
/* 163 */         return;
/*     */       }
/*     */ 
/* 166 */       index = sb.indexOf("wodata");
/* 167 */       if (index >= 0) {
/* 168 */         this.status = 4;
/* 169 */         index2 = sb.indexOf("\"", index + 1);
/* 170 */         this.url = sb.substring(index, index2);
/* 171 */         return;
/*     */       }
/*     */ 
/* 174 */       index = sb.indexOf("bigblue");
/* 175 */       if (index >= 0)
/*     */       {
/* 177 */         this.status = 4;
/* 178 */         this.url = null;
/* 179 */         return;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getUrl() {
/* 185 */     return this.url;
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
 * Qualified Name:     networking.http.LastSender
 * JD-Core Version:    0.6.0
 */