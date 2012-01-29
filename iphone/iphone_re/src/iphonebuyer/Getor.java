package iphonebuyer;

import java.awt.Desktop;
import java.net.URI;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Random;

import javax.swing.JOptionPane;

import networking.http.CopyOfSelectStore;
import networking.http.CreateReservation;
import networking.http.DoLogin;
import networking.http.DoRealLogin;
import networking.http.FetchTime;
import networking.http.GetReservationPage;
import networking.http.GetVerificationCode;
import networking.http.GetVerificationCodeUrl;
import networking.http.LastSender;
import networking.http.LoginPage;
import networking.http.SelectStore;
import networking.http.ShoppingCart;
import networking.http.WOSIDnNSCGenerator;

public class Getor
  implements Runnable
{
  private String postUrl;
  private String FetchPickupTimeCookie;
  private String pickupDate = "";
  private String pickupTime = "";
  private String formattedTime = "";
  private String[] stores = { "x-coredata%3A%2F%2Fcust%2FStore%2FewoJInByaW1hcnlLZXkiID0gewoJCSJzdG9yZUlEIiA9ICIxNDgxIjsKCX07CgkiZW50aXR5TmFtZSIgPSAiU3RvcmUiOwp9", "x-coredata%3A%2F%2Fcust%2FStore%2FewoJInByaW1hcnlLZXkiID0gewoJCSJzdG9yZUlEIiA9ICIxMzY0IjsKCX07CgkiZW50aXR5TmFtZSIgPSAiU3RvcmUiOwp9", "x-coredata%3A%2F%2Fcust%2FStore%2FewoJInByaW1hcnlLZXkiID0gewoJCSJzdG9yZUlEIiA9ICI0MjEiOwoJfTsKCSJlbnRpdHlOYW1lIiA9ICJTdG9yZSI7Cn0%3D", "x-coredata%3A%2F%2Fcust%2FStore%2FewoJInByaW1hcnlLZXkiID0gewoJCSJzdG9yZUlEIiA9ICIxNDgyIjsKCX07CgkiZW50aXR5TmFtZSIgPSAiU3RvcmUiOwp9" };
  public User user;
  private String imagFile;
  private String code;
  private String currentStatus;
  public boolean running = true;
  public int i_status = 0;

  public Getor(User user) { this.user = user; }

  public void stop() {
    this.running = false;
  }
  public String getFormattedTime() {
    return this.formattedTime;
  }
  public boolean isRunning() {
    return this.running;
  }
  public User getUser() {
    return this.user;
  }
  public String getVerificationImage() {
    return this.imagFile;
  }
  public String getStatus() {
    return this.currentStatus;
  }


  public void run() {
	  System.out.println(this.getUser().name);
    String ds01 = null;
    String[] values = null;
    String StoreUrl = "http://reserve.apple.com/WebObjects/ProductReservation.woa/wa/reserveProduct?lang=zh&country=CN";
    String ProductUrl = null;
    MainJFrame.instance.setRunningIcon(this, 0);
    
    while (this.running) {
      values = null;
      ProductUrl = null;
      //状态
      this.i_status = 0;
      // 设定状态
      this.currentStatus = (this.user.name + ":初始化请求...");
      MainJFrame.instance.setResult(this, "");
      
      this.i_status += 1;
      // values: sessionID(wosID) values[0]: NSC_
      while ((this.running) && ((values == null) || (values[0] == null))) {
        MainJFrame.instance.setCurrentStatus(this, this.i_status, this.currentStatus);
        Log.println("-----------WOSIDnNSCGenerator------------" + getUser().name + "-----------------");
        WOSIDnNSCGenerator woisnscGen = new WOSIDnNSCGenerator(this, StoreUrl, "");
        values = woisnscGen.getWOSIDnNSC();
      }

      // 选择零售店后提交的URL
      ProductUrl = values[2];

       Log.println("values[1]=" + values[1]);
       Log.println("values[2]=" + values[2]);
       Log.println("values[2]=" + values[3]);
       Log.println("values[2]=" + values[4]);

       // 设定Cookie
       String StroreCookie = "RPRCookie=zh|CN; woinst=-1;" + values[0] + "; " + values[1];
       StroreCookie = StroreCookie + "; s_orientationHeight=8; s_ppv=Reserve%2520%2526%2520Wrap%2520-%2520Choose%2520a%2520store%2520%2528CN%2529%2C100%2C100%2C8%2C; dfa_cookie=applejpglobal%2Capplejpstartpage%2Capplecnglobal%2Cappleglobal%2Cappleretail; s_cc=true; s_orientation=%5B%5BB%5D%5D; s_pathLength=startpage%3D3%2Cretail%3D33%2C; s_pv=Reserve%20%26%20Wrap%20-%20Choose%20a%20store%20(CN); s_sq=%5B%5BB%5D%5D; s_invisit_n_us=93%2C; s_vnum_n_us=%2C93%7C1%2C19%7C1%2C3%7C1%2C0%7C1%2C93%7C1%2C93%7C1%2C93%7C1%2C93%7C1%2C3%7C1%2C8%7C1%2C93%7C1%2C93%7C1%2C93%7C1%2C93%7C1%2C93%7C1; s_ria=Flash%20Not%20Detected%7C; ccl=4lNUblhnLHOrHMw1FWb4Gg==; geo=CN; s_vnum_n_jp=%2C78%7C1%2C78%7C1%2C78%7C1%2C8%7C1%2C78%7C1; ds01=A2967FA47A76861DD52C0F2A1DA4C5108BD5D8EF4EA544161F400E548000BD63; DefaultAppleID=zhouhlcn; s_membership=1%3Aaid; POD=cn~zh; dssid2=5ee37ae2-ab7f-4b53-a725-de39f3d2d32d; s_vi=[CS]v1|276A3D5C8515B78A-400001A7C02B3DF4[CE]";

       Log.println("-------------StroreCookie--------------\n" + StroreCookie);

       
       // *************************   选择零售店   *************************
       // 选择零售店后提交
       this.currentStatus = (this.user.name + ":选择零售店....");
       this.i_status += 1;
       MainJFrame.instance.setCurrentStatus(this, this.i_status, this.currentStatus);
       
       // 零售店选择
       SelectStore selectStore = new SelectStore();
       // 返回状态：1：正常  2：会话超时  3：
       int selectStoreRes = 3;
       int selectstoreCount = 0;

       // 提交零售店
       while ((this.running) && (selectStoreRes != 1)) {

         selectStoreRes = selectStore.selectStore(this, ProductUrl, StroreCookie, 
                "0.1.0.1.3.0.7.1.10.9.11=Y&productImage=&defaultStoreImage=http%3A%2F%2Freserve.apple.com%2Frprcustomer%2F8256%2Fimages%2Freserve_pu_store_landing_feature_default.jpg&storeNumber=R359");

         ProductUrl = selectStore.getNextUrl();
         
         Log.println(getUser().name + "selectStoreRes=" + selectStoreRes);
         // 会话超时,继续提交
         if (selectStoreRes == 2) {
             selectstoreCount++; MainJFrame.instance.setResult(this, "会话超时(" + selectstoreCount + ")");
           
             // 画面上选择:超时重来
             if (!MainJFrame.instance.isRollback()) {
                 continue;
             }
         }

      }
       // 会话超时:重新开始
       if (selectStoreRes == 2) {
         this.i_status = 0;
         values = null;
         continue;
       }

       MainJFrame.instance.setResult(this, "");
       // *************************   选择零售店结束 -> 选择预订商品画面  *************************


       // *************************  选择预订商品画面 -> 选择商品详细画面  *************************
       // 设定选择预订商品画面的cookie
       String ProductCookie = LoginDialog.getInstance().cookies[1] + values[0] + "; " + values[1];
       Log.println("-------------ProductCookie--------------\n" + ProductCookie);

       GetReservationPage reservpage = new GetReservationPage();
       // http://reserve.apple.com/WebObjects/ProductReservation.woa/wo/2.0.1.0.1.3.0.7.9.10.1.15.0.1.1.1.1
       reservpage.GetPage(this, selectStore.getNextUrl(), "", StroreCookie);
       
    	   
    	   
       // ************************* 选择商品详细画面  -> 获取购物车 *************************
       this.currentStatus = (this.user.name + ":获取购物车....");
       this.i_status += 1;
       MainJFrame.instance.setCurrentStatus(this, this.i_status, this.currentStatus);
       // 获取购物车的URL
       String renderCartUrl = "http://reserve.apple.com/WebObjects/ProductReservation.woa/wa/GianduiaAction/renderCart?lang=zh&country=CN";

       // 1:正常 2：会话超时
       int cartStatus = 3;
       int cartCount = 0;

       while ((this.running) && (cartStatus != 1)) {
         ShoppingCart cart = new ShoppingCart();
         cartStatus = cart.RenderCart(this, renderCartUrl, ProductCookie);
         if ((cartStatus == 2) && 
           (MainJFrame.instance.isRollback())) {
           cartCount++; MainJFrame.instance.setResult(this, "会话超时(" + cartCount + ")");
           break;
        }
      }

       if (cartStatus == 2) {
         this.i_status = 0;
         values = null;
         continue;
      }
       MainJFrame.instance.setResult(this, "");

       
       // ************************* 获取购物车  -> 放入购物车 *************************
       this.currentStatus = (this.user.name + ":放入购物车....");
       this.i_status += 1;
       MainJFrame.instance.setCurrentStatus(this, this.i_status, this.currentStatus);

       CartRequestSender cardAdder = new CartRequestSender(this, ProductUrl, ProductCookie);
       cardAdder.send();

       // 指定个数的商品全部放入购物车
       while (!cardAdder.areAllFinished()) {
        try {
           Thread.sleep(10L);
        } catch (Exception e) {
           e.printStackTrace();
        }
      }
       MainJFrame.instance.setResult(this, "");

       // ************************* 放入购物车  -> 创建预定 *************************
       this.currentStatus = (this.user.name + ":创建预定....");
       this.i_status += 1;
       MainJFrame.instance.setCurrentStatus(this, this.i_status, this.currentStatus);
       String loginHttpsUrl = null;
       String CreateReserveUrl = "http://reserve.apple.com/WebObjects/ProductReservation.woa/wa/GianduiaAction/createReservation?lang=zh&country=CN";
       int createReservationStatus = 3;
       int createreservationcount = 0;
       CreateReservation createReservation = new CreateReservation(this, CreateReserveUrl, ProductCookie, ProductUrl);

       while ((this.running) && ((createReservationStatus != 1) || (loginHttpsUrl == null))) {
        try {
           createReservationStatus = createReservation.createReservation();
        } catch (Exception e) {
           e.printStackTrace();
        }
         if (createReservationStatus == 1)
           loginHttpsUrl = createReservation.getLoginHttpsUrl();
         if (createReservationStatus == 2) {
           createreservationcount++; MainJFrame.instance.setResult(this, "会话超时(" + createreservationcount + ")");
           if (!MainJFrame.instance.isRollback()) {
            continue;
          }
        }
      }

       if (createReservationStatus == 2) {
         this.i_status = 0;
         values = null;
         continue;
      }
       MainJFrame.instance.setResult(this, "");

       
       // ************************* 取得登陆页面 *************************
       Log.println("-------------loginUrl=" + loginHttpsUrl + "-------------" + (createReservationStatus == 1));
       LoginPage loginPage = new LoginPage();
       int loginPageStatus = 3;

       this.currentStatus = (this.user.name + ":获取登陆页面....");
       this.i_status += 1;
       MainJFrame.instance.setCurrentStatus(this, this.i_status, this.currentStatus);
       int loginCount = 0;

       while ((this.running) && (loginPageStatus != 1)) {
         loginPageStatus = loginPage.getPage(this, loginHttpsUrl, ProductCookie);
         if (loginPageStatus == 1)
           this.postUrl = loginPage.getPostUrl();
         if (loginPageStatus != 2)
          continue;
         loginCount++; MainJFrame.instance.setResult(this, "会话超时(" + loginCount + ")");
         if (!MainJFrame.instance.isRollback())
        {
          continue;
        }

      }

       if (loginPageStatus == 2) {
         this.i_status = 0;
         values = null;
         continue;
      }
       MainJFrame.instance.setResult(this, "");

       
       // ************************* 用户登陆 *************************
       String LoginCookie = LoginDialog.getInstance().cookies[2] + values[0] + "; " + values[1];
       Log.println("-------------LoginCookie--------------\n" + LoginCookie);

       this.currentStatus = (this.user.name + ":登陆....");
       this.i_status += 1;
       MainJFrame.instance.setCurrentStatus(this, this.i_status, this.currentStatus);
       DoLogin loginPoster = new DoLogin();
       int loginStatus = 3;
       loginCount = 0;
       String httpgetloginUrl = null;

       //while ((this.running) && ((loginStatus != 1) || (httpgetloginUrl == null))) {
       while ((this.running) && (loginStatus != 1)) {
    	  // 用户登陆操作
          try {
              loginStatus = loginPoster.login(this, "https://reserve.apple.com/" + this.postUrl, loginHttpsUrl, LoginCookie, this.user.appleid, this.user.password, loginPage.getPara1(), loginPage.getPara2(), loginPage.getPara3());
          } catch (Exception e) {
              e.printStackTrace();
          }

          // 正常登陆
          if (loginStatus == 1) {
              httpgetloginUrl = loginPoster.getLoaction();
          }
          // 会话超时
          if (loginStatus == 2) {
              loginCount++; MainJFrame.instance.setResult(this, "会话超时(" + loginCount + ")");
              if (MainJFrame.instance.isRollback()) {
                  break;
              }
          } else if (loginStatus == 4) {
              break;
          }
       }
       
       ds01 = loginPoster.getToken();
       Log.println("httpgetloginUrl=" + httpgetloginUrl);
       MainJFrame.instance.setResult(this, "");
       if (loginStatus == 2) {
         this.i_status = 0;
         values = null;
         continue;
       }
       if (loginStatus == 4) {
         MainJFrame.instance.setResult(this, "账号有问题");
         MainJFrame.instance.setRunningIcon(this, 1);
         return;
      }

       // ************************* 用户登陆成功 -> 身份证输入页面 *************************
//       this.currentStatus = (this.user.name + ":身份证输入页面....");
//       this.i_status += 1;
//       MainJFrame.instance.setCurrentStatus(this, this.i_status, this.currentStatus);
//       DoRealLogin rloginPoster = new DoRealLogin();
//       loginStatus = 3;
//       loginCount = 0;
//
//       while ((this.running) && (loginStatus != 1)) {
//         loginStatus = rloginPoster.login(this, httpgetloginUrl, loginHttpsUrl, LoginCookie);
//
//         if (loginStatus == 2) {
//           loginCount++; MainJFrame.instance.setResult(this, "会话超时(" + loginCount + ")");
//           if (MainJFrame.instance.isRollback())
//          {
//            break;
//          }
//        }
//         else if (loginStatus == 4) {
//           break;
//        }
//      }
//       MainJFrame.instance.setResult(this, "");
//       if (loginStatus == 2) {
//         this.i_status = 0;
//         values = null;
//         continue;
//       }if (loginStatus == 4) {
//         MainJFrame.instance.setResult(this, "账号有问题");
//         MainJFrame.instance.setRunningIcon(this, 1);
//         return;
//      }
//
//       Log.println("getCodeImageUrl=" + rloginPoster.getCodeImageUrl());
//       Log.println("getLastUrl=" + rloginPoster.getLastUrl());
//       Log.println("getReCodeImageUrl=" + rloginPoster.getReCodeImageUrl());

       
       // ************************* 身份证输入页面中获取拿货时间 *************************
       this.FetchPickupTimeCookie = "woinst=-1; " + values[0] + "; " + "RPRCookie=zh|CN; "  + values[1] + "; ";
       this.FetchPickupTimeCookie = this.FetchPickupTimeCookie + "s_orientationHeight=8; s_ppv=Reserve%2520%2526%2520Wrap%2520-%2520Confirm%2520Information%2520%2528CN%2529%2C100%2C100%2C8%2C; dfa_cookie=applecnglobal; s_cc=true; s_orientation=%5B%5BB%5D%5D; s_pathLength=retail%3D17%2C; s_pv=Reserve%20%26%20Wrap%20-%20Confirm%20Information%20(CN); s_sq=%5B%5BB%5D%5D; " + ds01 + "; " + "s_invisit_n_us=93%2C; s_vnum_n_us=%2C93%7C1%2C19%7C1%2C3%7C1%2C0%7C1%2C93%7C1%2C93%7C1%2C93%7C1%2C93%7C1%2C3%7C1%2C8%7C1%2C93%7C1%2C93%7C1%2C93%7C1%2C93%7C1%2C93%7C1%2C93%7C1%2C93%7C1%2C93%7C1%2C93%7C1%2C93%7C1%2C93%7C1%2C93%7C1%2C93%7C1%2C93%7C1%2C93%7C1%2C93%7C1%2C93%7C1%2C93%7C1%2C93%7C1%2C93%7C1%2C93%7C1; s_ria=Flash%20Not%20Detected%7C; ccl=wvo1I3cpyOHXYUw7KmRgdw==; geo=CN; s_vnum_n_jp=%2C78%7C1%2C78%7C1%2C78%7C1%2C8%7C1%2C78%7C1%2C78%7C1%2C78%7C1%2C78%7C1%2C78%7C1%2C78%7C1%2C78%7C1%2C78%7C1%2C78%7C1%2C78%7C1; DefaultAppleID=zhouhlcn; s_membership=1%3Aaid; POD=cn~zh; dssid2=5ee37ae2-ab7f-4b53-a725-de39f3d2d32d; s_vi=[CS]v1|276A3D5C8515B78A-400001A7C02B3DF4[CE]";
       Log.println("-------------FetchPickupTimeCookie--------------\n" + this.FetchPickupTimeCookie);

       this.currentStatus = (this.user.name + ":获取拿货时间....");
       this.i_status += 1;
       MainJFrame.instance.setCurrentStatus(this, this.i_status, this.currentStatus);
       String FecthPicupTimeUrl = "http://reserve.apple.com/WebObjects/ProductReservation.woa/wa/GianduiaAction/fetchPickupDateTimeDetail";
       FetchTime fetcher = null;
       int fetchTimeStatus = 3;
       int totalCount = 0;
       this.i_status += 1;
       while ((this.running) && (fetchTimeStatus != 1)) {
         fetcher = new FetchTime(this, FecthPicupTimeUrl, "", this.FetchPickupTimeCookie);
         fetchTimeStatus = fetcher.fetch();
         if (fetchTimeStatus == 4) {
           totalCount++;
           Log.println("-------------------还没开放---------------------");
           this.currentStatus = (this.user.name + ":还没开放(" + totalCount + ")....");
           MainJFrame.instance.setCurrentStatus(this, this.i_status, this.currentStatus);
           MainJFrame.instance.setResult(this, "<html><font color=gray>没开放(" + totalCount + ")</font></html>");
           if (MainJFrame.instance.getSelectedMode() == 1) break;
          try {
             Thread.sleep(10000L);
          } catch (Exception e) {
             e.printStackTrace();
          }

        }

      }

       MainJFrame.instance.setResult(this, "");
       
       if (fetchTimeStatus == 1) {
         if (MainJFrame.instance.isPlaySound()) {
           MainJFrame.instance.PlaySound();
         }
         MainJFrame.instance.setResult(this, "<html><font color=red>!!!已经开放!!!!</font></html>");
         this.pickupDate = fetcher.getPickupDate();
//         int size = fetcher.getPickupTimeList().size();
//         Random random = new Random();
//         int s = random.nextInt(size) % (size + 1);
//         if (s == 0) {
//           s = 1;
//        }
//         Iterator it = fetcher.getPickupTimeList().keySet().iterator();
//         int cc = 0;
//         while (it.hasNext()) {
//           String key = (String)it.next();
//           if (cc == s) {
//             this.pickupTime = key;
//             this.formattedTime = ((String)fetcher.getPickupTimeList().get(key));
//             break;
//          }
//           cc++;
//        }
         Log.println("-----------pickupDate=" + this.pickupDate + "-------------");
         Log.println("-----------pickupTime=" + this.pickupTime + "-------------");
      }
//
//       this.imagFile = null;
//       this.currentStatus = (this.user.name + ":获取验证码链接....");
//       this.i_status += 1;
//       MainJFrame.instance.setCurrentStatus(this, this.i_status, this.currentStatus);
//       String VCUrl = "";//rloginPoster.getCodeImageUrl();
//       if (VCUrl == null) {
//         String reCodeImageUrl = "";// rloginPoster.getReCodeImageUrl();
//         String getCodeUrl = "http://reserve.apple.com/";
//         if (reCodeImageUrl == null) {
//           getCodeUrl = getCodeUrl + "/WebObjects/ProductReservation.woa/wo/13.1.1.0.1.3.0.7.1.10.1.45.1.19.1";
//         } else getCodeUrl = getCodeUrl + reCodeImageUrl;
//
//         GetVerificationCodeUrl VCodeUrlGetor = new GetVerificationCodeUrl();
//         while ((this.running) && (VCUrl == null)) {
//           VCUrl = VCodeUrlGetor.get(this, getCodeUrl, "", this.FetchPickupTimeCookie);
//        }
//      }
//
//       this.currentStatus = (this.user.name + ":获取验证码....");
//       String urlOfCodeImage = "http://reserve.apple.com/WebObjects/ProductReservation.woa/wr?" + VCUrl;
//       this.i_status += 1;
//       MainJFrame.instance.setCurrentStatus(this, this.i_status, this.currentStatus);
//       Log.println(">>>>>2>>>>>" + urlOfCodeImage);
//       GetVerificationCode verificationCode = new GetVerificationCode();
//
//       int getVcStatus = 3;
//       int vcCount = 0;
//
//       while ((this.running) && (getVcStatus != 1)) {
//         getVcStatus = verificationCode.get(this, urlOfCodeImage, "", this.FetchPickupTimeCookie);
//
//         if (getVcStatus == 2) {
//           vcCount++; MainJFrame.instance.setResult(this, "会话超时(" + vcCount + ")");
//           if (!MainJFrame.instance.isRollback())
//          {
//            continue;
//          }
//        }
//      }
//
//       MainJFrame.instance.setResult(this, "");
//       if (getVcStatus == 1) {
//         this.imagFile = verificationCode.getImagePath();
//        try {
//           if (this.imagFile == null) {
//             this.currentStatus = (this.user.name + ":获取验证码失败，从头来吧....");
//             this.i_status += 1;
//             MainJFrame.instance.setCurrentStatus(this, this.i_status, this.currentStatus);
//             this.i_status = 0;
//             values = null;
//             continue;
//          }
//           CodeInputDlg dlg = new CodeInputDlg(this);
//           dlg.setImage(this.imagFile);
//           MainJFrame.instance.addCodeInputDlg(dlg);
//           CodeInputDlg dlgToShow = MainJFrame.instance.getCodeInputDlg(this);
//           while (dlgToShow == null) {
//            try {
//               Thread.sleep(10L);
//            } catch (Exception e) {
//               e.printStackTrace();
//            }
//             dlgToShow = MainJFrame.instance.getCodeInputDlg(this);
//          }
//           dlgToShow.showUI();
//           MainJFrame.instance.removeFirstDlg();
//           this.code = dlgToShow.getCode();
//           Log.println("------------------------code" + this.code + "-------------------------");
//           this.currentStatus = (this.user.name + ":请输入验证码....");
//           this.i_status += 1;
//           MainJFrame.instance.setCurrentStatus(this, this.i_status, this.currentStatus);
//        }
//        catch (Exception e) {
//           e.printStackTrace();
//        }
//      }
//
//       if (getVcStatus == 2) {
//         this.i_status = 0;
//         values = null;
//         continue;
//      }
//       MainJFrame.instance.setResult(this, "");

       String lastUrl = null;
//       if (rloginPoster.getLastUrl() == null)
//         lastUrl = "http://reserve.apple.com/WebObjects/ProductReservation.woa/wo/10.1.1.0.1.3.0.7.1.10.1";
//      else {
         lastUrl = "http://reserve.apple.com/" + loginPoster.getLastUrl();
//      }

       String SendCookie = LoginDialog.getInstance().cookies[4] + ds01 + "; " + values[0] + "; " + values[1];
       Log.println("-------------SendCookie--------------\n" + SendCookie);
       int sendStatus = 3;
       this.currentStatus = (this.user.name + ":已停止");
       this.i_status += 1;
       MainJFrame.instance.setCurrentStatus(this, this.i_status, this.currentStatus);
       MainJFrame.instance.setResult(this, "已停止");

       while ((this.running) && (sendStatus == 3)) {
         this.currentStatus = (this.user.name + ":最后的提交");
         MainJFrame.instance.setCurrentStatus(this, this.i_status, this.currentStatus);
         MainJFrame.instance.setResult(this, "提交中...");
         Log.println("code=" + this.code);
         Log.println("user.idcode=" + this.user.idcode);
         Log.println("pickupDate=" + this.pickupDate);
         Log.println("pickupTime=" + this.pickupTime);
         
         
         
        // this.pickupDate = "1326110400000";

         
         
         LastSender sender = new LastSender();
         //sendStatus = sender.send(this, lastUrl, "", SendCookie, LoginDialog.getInstance().stores2[MainJFrame.instance.getSelectedStore()], this.code.trim(), this.user.idcode, this.pickupDate, this.pickupTime);
         sendStatus = sender.send(this, lastUrl, 
        		 loginPoster.getLoaction(), 
        		 SendCookie, 
        		 "ewoJInByaW1hcnlLZXkiID0gewoJCSJzdG9yZUlEIiA9ICIxNTQyIjsKCX07CgkiZW50aXR5TmFtZSIgPSAiU3RvcmUiOwp9", 
        		 this.user.idcode, // 身份证
        		 "1326456000000", 
        		 this.pickupTime);
         
      }

       this.i_status += 1;
       if (sendStatus == 2) {
           this.currentStatus = (this.user.name + ":会话已超时，提交作废....");

           MainJFrame.instance.setCurrentStatus(this, this.i_status, this.currentStatus);
           MainJFrame.instance.setResult(this, "会话超时");
           try {
               Thread.sleep(3000L);
           } catch (Exception e) {
               e.printStackTrace();
           }
      } else if (sendStatus == 6) {
         this.currentStatus = (this.user.name + ":提交的手机太多....");
         MainJFrame.instance.setCurrentStatus(this, this.i_status, this.currentStatus);
         MainJFrame.instance.setResult(this, "超过两个");
         MainJFrame.instance.setRunningIcon(this, 1);
       } else if (sendStatus == 7) {
         this.currentStatus = (this.user.name + ":身份证已经拿过货....");
         MainJFrame.instance.setCurrentStatus(this, this.i_status, this.currentStatus);
         MainJFrame.instance.setResult(this, "身份证重复");
         MainJFrame.instance.setRunningIcon(this, 1);
       } else if (sendStatus == 8) {
         this.currentStatus = (this.user.name + ":没抢到，可能停止发货了....");
         MainJFrame.instance.setCurrentStatus(this, this.i_status, this.currentStatus);
         MainJFrame.instance.setResult(this, "没抢到");
         MainJFrame.instance.setRunningIcon(this, 1);
       } else if (sendStatus == 5) {
         this.currentStatus = (this.user.name + ":验证码错误....");
         MainJFrame.instance.setCurrentStatus(this, this.i_status, this.currentStatus);
         MainJFrame.instance.setResult(this, "验证码错误");
         MainJFrame.instance.setRunningIcon(this, 1);
       } else if (sendStatus == 4) {
         this.currentStatus = (this.user.name + ":抢货失败，可能没货了....");
         MainJFrame.instance.setCurrentStatus(this, this.i_status, this.currentStatus);
         MainJFrame.instance.setResult(this, "没货了");
         MainJFrame.instance.setRunningIcon(this, 1); } else {
         if (sendStatus != 1) {
          break;
        }
         String storeName = null;
         switch (MainJFrame.instance.getSelectedStore()) {
        case 0:
           storeName = "浦西";
           break;
        case 1:
           storeName = "浦东";
           break;
        case 2:
           storeName = "三里屯";
           break;
        case 3:
           storeName = "西单";
        }

         boolean r = false;
         int l = 0;
		
       //  while (!r)
       // {
       //    String type = "iPad " + (String)MainJFrame.phoneTypes.get(MainJFrame.instance.getSelectedPhone());

       //    r = MainJFrame.instance.getProvider().post(this, LoginDialog.getInstance().clientid, LoginDialog.getInstance().clientName, LoginDialog.getInstance().token, LoginDialog.getInstance().adminid, this.user.name, this.user.appleid, this.user.password, this.user.idcode, storeName, this.formattedTime, type, rloginPoster.getPhoneNum(), "颜色", this.user.id, "ipad", ((YellowCow)LoginDialog.getInstance().cows.get(MainJFrame.instance.getSelectedCowIndex())).id);

       //    l++;
       //    if (l >= 3)
       //     break;
       // }
		//l =3;
         if (l >= 3) {
           MainJFrame.instance.setRunningIcon(this, 1);
           this.currentStatus = (this.user.name + ":正版请加QQ：309012359，手机：15018006185 黄家大少！");
           MainJFrame.instance.setCurrentStatus(this, this.i_status, this.currentStatus);
           MainJFrame.instance.setResult(this, "正版请加QQ:309012359！");
           JOptionPane.showMessageDialog(MainJFrame.instance, "你使用的版本未得到授权，请联系作者免费获取正版授权 QQ：309012359 手机：15018006185 黄家大少！", "失败", 0);
          try {
             Desktop.getDesktop().browse(new URI("http://www.qiimii.com"));
          } catch (Exception e) {
             e.printStackTrace();
          }

           System.exit(0);
        } else {
           MainJFrame.instance.setRunningIcon(this, 2);
           this.currentStatus = (this.user.name + ":成功拿货");
           MainJFrame.instance.setCurrentStatus(this, this.i_status, this.currentStatus);
           MainJFrame.instance.setResult(this, "<html><font color=red>成功拿货</font></html>");
        }
      }
    }
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\5-22\3\
 * Qualified Name:     iphonebuyer.Getor
 * JD-Core Version:    0.6.0
 */