/*     */ package iphonebuyer;
/*     */ 
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileReader;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.net.SocketTimeoutException;
/*     */ import java.net.URLEncoder;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.StringTokenizer;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.GroupLayout.Alignment;
/*     */ import javax.swing.GroupLayout.ParallelGroup;
/*     */ import javax.swing.GroupLayout.SequentialGroup;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPasswordField;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.JToggleButton;
import javax.swing.LayoutStyle;
/*     */ import javax.swing.LayoutStyle.ComponentPlacement;
/*     */ import networking.http.HttpPost;
/*     */ import networking.http.HttpResponse;
import networking.http.HttpResponseProcessor;
/*     */ 
/*     */ public class LoginDialog extends JDialog
/*     */   implements HttpResponseProcessor
/*     */ {
/*     */   public String token;
/*     */   public String[] stores;
/*     */   public String[] stores2;
/*     */   public String[] cookies;
/*     */   public String adminid;
/*     */   public String clientid;
/*     */   public String clientName;
/*     */   public String straccount;
/*     */   public String strpassword;
/*     */   public String qq;
/*     */   public String para1;
/*     */   public String para2;
/*     */   public String para3;
/*  51 */   public ArrayList<YellowCow> cows = new ArrayList(10);
/*     */ 
/*  54 */   private static LoginDialog instance = null;
/*     */   private JButton jButton1;
/*     */   private JLabel jLabel1;
/*     */   private JLabel jLabel2;
/*     */   private JPasswordField jPasswordField1;
/*     */   private JTextField jTextField1;
/*     */   private JToggleButton jToggleButton1;
/*     */ 
/*     */   public LoginDialog()
/*     */   {
/*  56 */     super(MainJFrame.instance, true);
/*  57 */     setTitle("登陆--" + MainJFrame.instance.getTitle());
/*     */     try {
/*  59 */       File file = new File("config.txt");
/*  60 */       if (!file.exists()) {
/*  61 */         file.createNewFile();
/*     */       } else {
/*  63 */         BufferedReader bi = new BufferedReader(new FileReader(file));
/*  64 */         String line = bi.readLine();
/*     */ 
/*  66 */         while (line != null) {
/*  67 */           if (!line.trim().equals("")) {
/*  68 */             int index = line.indexOf("&");
/*  69 */             if (index > 0) {
/*  70 */               this.straccount = line.substring(0, index);
/*  71 */               this.strpassword = line.substring(index + 1);
/*     */             }
/*     */           }
/*  74 */           line = bi.readLine();
/*     */         }
/*     */       }
/*     */     } catch (Exception e) {
/*  78 */       e.printStackTrace();
/*     */     }
/*  80 */     initComponents();
/*  81 */     if ((this.straccount != null) && (this.strpassword != null)) {
/*  82 */       this.jTextField1.setText(this.straccount);
/*  83 */       this.jPasswordField1.setText(this.strpassword);
/*     */     }
/*  85 */     instance = this;
/*     */   }
/*     */   public static LoginDialog getInstance() {
/*  88 */     return instance;
/*     */   }
/*     */   public void showUI() {
/*  91 */     Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
/*  92 */     int width = (d.width - 369) / 2;
/*  93 */     int height = (d.height - 187) / 2;
/*  94 */     setLocation(width, height);
/*  95 */     setVisible(true);
/*     */   }
/*     */ 
/*     */   private void initComponents()
/*     */   {
/* 119 */     this.jLabel1 = new JLabel();
/* 120 */     this.jTextField1 = new JTextField();
/* 121 */     this.jLabel2 = new JLabel();
/* 122 */     this.jPasswordField1 = new JPasswordField();
/* 123 */     this.jToggleButton1 = new JToggleButton();
/* 124 */     this.jButton1 = new JButton();
/*     */ 
/* 126 */     setDefaultCloseOperation(0);
/* 127 */     setAlwaysOnTop(true);
/*     */ 
/* 129 */     this.jLabel1.setText("账号：");
/*     */ 
/* 131 */     this.jLabel2.setText("密码：");
/*     */ 
/* 133 */     this.jToggleButton1.setText("登陆");
/* 134 */     this.jToggleButton1.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 136 */         LoginDialog.this.jToggleButton1ActionPerformed(evt);
/*     */       }
/*     */     });
/* 140 */     this.jButton1.setText("退出");
/* 141 */     this.jButton1.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 143 */         LoginDialog.this.jButton1ActionPerformed(evt);
/*     */       }
/*     */     });
/* 147 */     GroupLayout layout = new GroupLayout(getContentPane());
/* 148 */     getContentPane().setLayout(layout);
/* 149 */     layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(52, 52, 52).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jLabel2).addComponent(this.jLabel1)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jPasswordField1).addComponent(this.jTextField1, -1, 201, 32767)).addContainerGap(76, 32767)).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap(126, 32767).addComponent(this.jToggleButton1).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jButton1).addGap(119, 119, 119)));
/*     */ 
/* 168 */     layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(43, 43, 43).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.jTextField1, -2, 29, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.jPasswordField1, -2, 29, -2)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jToggleButton1).addComponent(this.jButton1)).addContainerGap(35, 32767)));
/*     */ 
/* 186 */     pack();
/*     */   }
/*     */ 
/*     */   private void jToggleButton1ActionPerformed(ActionEvent evt)
/*     */   {
/* 192 */     int account = 0;
/*     */     try {
/* 194 */       account = Integer.parseInt(this.jTextField1.getText());
/*     */     }
/*     */     catch (Exception e) {
/* 197 */       e.printStackTrace();
/* 198 */       JOptionPane.showMessageDialog(this, "账号格式错误", "错误", 0);
/* 199 */       return;
/*     */     }
/*     */ 
/* 204 */     HashMap headers = new HashMap(5);
/*     */ 
/* 206 */     headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
/* 207 */     headers.put("Accept-Language", "zh-cn,zh;q=0.5");
/* 208 */     headers.put("Accept-Encoding", "gzip, deflate, x-gzip, identity, *;q=0");
/* 209 */     headers.put("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
/* 210 */     headers.put("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1) ;  Embedded Web Browser from: http://bsalsa.com/; song2009 Embedded Web Browser from: http://bsalsa.com/; WebSaver; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.648; .NET CLR 3.5.21022; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; AskTB5.6; ZiiMeet v2.00.07)");
/* 211 */     headers.put("Content-Type", "application/x-www-form-urlencoded");
/* 212 */     headers.put("Connection", "Keep-Alive");
/* 213 */     headers.put("Keep-Alive", "300");
/* 214 */     headers.put("Pragma", "no-cache");
/* 215 */     headers.put("Cache-Control", "no-cache");
/*     */ 
/* 217 */     this.straccount = this.jTextField1.getText().trim();
/* 218 */     this.strpassword = this.jPasswordField1.getText().trim();
/* 219 */     String content = URLEncoder.encode(this.straccount + "&" + this.strpassword + "&ipad");
/*     */ 
/* 221 */     String url = "http://localhost:8080/QuerySun/Login";
/* 222 */     HttpPost getor = new HttpPost(null, url, headers, content, this);
/* 223 */     //getor.doRequest(true);
/* 224 */     //if (this.token != null)
processRespose1();
/* 225 */       dispose();
/*     */     try {
/* 227 */       FileWriter writer = new FileWriter(new File("config.txt"));
/* 228 */       writer.write(this.straccount + "&" + this.strpassword);
/* 229 */       writer.close();
/*     */     } catch (Exception e) {
/* 231 */       e.printStackTrace();
/*     */     }
/* 233 */     MainJFrame.instance.UpdateYelloCowList();
/*     */   }
/*     */ 
/*     */   private void jButton1ActionPerformed(ActionEvent evt)
/*     */   {
/* 239 */     System.exit(0);
/*     */   }
/*     */ 
public void processRespose(Object body) {}
/*     */   public void processRespose1() {
	StringBuffer sb = new StringBuffer(); 
	sb.append("200|");
	sb.append("e9bdf988-a8d8-4118-ad40-1292c9140bde|");
	sb.append("selectedStorePortURI=x-coredata%3A%2F%2Fcust%2FStore%2FewoJInByaW1hcnlLZXkiID0gewoJCSJzdG9yZUlEIiA9ICIxNDgxIjsKCX07CgkiZW50aXR5TmFtZSIgPSAiU3RvcmUiOwp9&selectedStatePortURI=&0.1.0.1.3.0.7.1.10.5.7=Y&productImage=http%3A%2F%2Freserve.apple.com%2Frprcustomer%2Fimages%2Fiphone4_choose_store_cn.jpg&=%5Bobject+Object%5D&0.1.0.1.3.0.7.1.10.5.61=true|");
	sb.append("selectedStorePortURI=x-coredata%3A%2F%2Fcust%2FStore%2FewoJInByaW1hcnlLZXkiID0gewoJCSJzdG9yZUlEIiA9ICIxMzY0IjsKCX07CgkiZW50aXR5TmFtZSIgPSAiU3RvcmUiOwp9&selectedStatePortURI=null&0.1.0.1.3.0.7.1.10.5.7=Y&productImage=http%3A%2F%2Freserve.apple.com%2Frprcustomer%2Fimages%2Fiphone4_choose_store_cn.jpg&=%5Bobject+Object%5D&0.1.0.1.3.0.7.1.10.5.59=true|");
	sb.append("selectedStorePortURI=x-coredata%3A%2F%2Fcust%2FStore%2FewoJInByaW1hcnlLZXkiID0gewoJCSJzdG9yZUlEIiA9ICI0MjEiOwoJfTsKCSJlbnRpdHlOYW1lIiA9ICJTdG9yZSI7Cn0%3D&selectedStatePortURI=null&0.1.0.1.3.0.7.1.10.5.7=Y&productImage=http%3A%2F%2Freserve.apple.com%2Frprcustomer%2Fimages%2Fiphone4_choose_store_cn.jpg&=%5Bobject+Object%5D&0.1.0.1.3.0.7.1.10.5.59=true|");
	sb.append("selectedStorePortURI=x-coredata%3A%2F%2Fcust%2FStore%2FewoJInByaW1hcnlLZXkiID0gewoJCSJzdG9yZUlEIiA9ICIxNDgyIjsKCX07CgkiZW50aXR5TmFtZSIgPSAiU3RvcmUiOwp9&selectedStatePortURI=null&0.1.0.1.3.0.7.1.10.5.7=Y&productImage=http%3A%2F%2Freserve.apple.com%2Frprcustomer%2Fimages%2Fiphone4_choose_store_cn.jpg&=%5Bobject+Object%5D&0.1.0.1.3.0.7.1.10.5.59=true|");
	sb.append("ewoJInByaW1hcnlLZXkiID0gewoJCSJzdG9yZUlEIiA9ICIxNDgxIjsKCX07CgkiZW50aXR5TmFtZSIgPSAiU3RvcmUiOwp9|");
	sb.append("ewoJInByaW1hcnlLZXkiID0gewoJCSJzdG9yZUlEIiA9ICIxMzY0IjsKCX07CgkiZW50aXR5TmFtZSIgPSAiU3RvcmUiOwp9|");
	sb.append("ewoJInByaW1hcnlLZXkiID0gewoJCSJzdG9yZUlEIiA9ICI0MjEiOwoJfTsKCSJlbnRpdHlOYW1lIiA9ICJTdG9yZSI7Cn0%3D|");
	sb.append("ewoJInByaW1hcnlLZXkiID0gewoJCSJzdG9yZUlEIiA9ICIxNDgyIjsKCX07CgkiZW50aXR5TmFtZSIgPSAiU3RvcmUiOwp9|");
	sb.append("RPRCookie=zh%7CCN; woinst=-1; s_pv=Reserve%20%26%20Wrap%20-%20Choose%20a%20store%20(CN); s_pathLength=retail%3D1%2C; |");
	sb.append("RPRCookie=zh%7CCN; woinst=-1; s_pv=Reserve%20%26%20Wrap%20-%20Product%20detail%20-%20iPhone%20(CN); s_pathLength=retail%3D2%2C; |");
	sb.append("RPRCookie=zh%7CCN; woinst=-1; s_pv=Reserve%20%26%20Wrap%20-%20Apple%20ID%20Sign-in%20(CN); s_pathLength=retail%3D3%2C; |");
	sb.append("RPRCookie=zh%7CCN; woinst=-1; s_pv=Reserve%20%26%20Wrap%20-%20Confirm%20Information%20(CN); s_pathLength=retail%3D4%2C; DefaultAppleID=synpp0216%2540sina.com; |");
	sb.append("RPRCookie=zh%7CCN; woinst=-1; s_pv=Reserve%20%26%20Wrap%20-%20Confirm%20Information%20(CN); s_pathLength=retail%3D4%2C; DefaultAppleID=z764502589%2540126.com; |");
	sb.append("01|01|费翔");
	sb.append("|745264559|");
	sb.append("0.1.0.1.3.0.7.1.10.1.7.20.0.5.1.1.1.4|");
	sb.append("0.1.0.1.3.0.7.1.10.1.7.20.0.5.1.1.1.20|");
	sb.append("0.1.0.1.3.0.7.1.10.1.7.20.0.5.1.1.1.30|");
	sb.append("1507;张佩瑜&888;鸿三里屯&937;永平2&1411;伟香港&1482;lian浦东&|");
	sb.append("白色/16G:skuID=MD128CHA-黑色/16G:skuID=MD128CHA-白色/32G:skuID=MD128CHA-黑色/32G:skuID=MD128CHA-白色/64G:skuID=MD128CHA-黑色/64G:skuID=MD128CHA");

/* 243 */     if (sb != null) {
/* 244 */       //HttpResponse res = (HttpResponse)body;
/* 245 */       //StringBuffer sb = (StringBuffer)res.body;
/* 246 */       String str = sb.toString();
/* 247 */       Log.println(str);

/* 248 */       if (str.startsWith("200")) {
/* 249 */         int index = str.indexOf("|");
/* 250 */         if (index > 0) {
/* 251 */           str = str.substring(index + 1);
/* 252 */           StringTokenizer tokenizer = new StringTokenizer(str, "|");
/* 253 */           int count = 0;
/* 254 */           while (tokenizer.hasMoreTokens()) {
/* 255 */             String tkn = tokenizer.nextToken();
/*     */ 
/* 257 */             switch (count) {
/*     */             case 0:
/* 259 */               this.token = tkn;
/* 260 */               break;
/*     */             case 1:
/* 262 */               this.stores = new String[4];
/* 263 */               this.stores[0] = tkn;
/* 264 */               break;
/*     */             case 2:
/* 266 */               this.stores[1] = tkn;
/* 267 */               break;
/*     */             case 3:
/* 269 */               this.stores[2] = tkn;
/* 270 */               break;
/*     */             case 4:
/* 272 */               this.stores[3] = tkn;
/* 273 */               break;
/*     */             case 5:
/* 275 */               this.stores2 = new String[4];
/* 276 */               this.stores2[0] = tkn;
/* 277 */               break;
/*     */             case 6:
/* 279 */               this.stores2[1] = tkn;
/* 280 */               break;
/*     */             case 7:
/* 282 */               this.stores2[2] = tkn;
/* 283 */               break;
/*     */             case 8:
/* 285 */               this.stores2[3] = tkn;
/* 286 */               break;
/*     */             case 9:
/* 288 */               this.cookies = new String[5];
/* 289 */               this.cookies[0] = tkn;
/* 290 */               break;
/*     */             case 10:
/* 292 */               this.cookies[1] = tkn;
/* 293 */               break;
/*     */             case 11:
/* 295 */               this.cookies[2] = tkn;
/* 296 */               break;
/*     */             case 12:
/* 298 */               this.cookies[3] = tkn;
/* 299 */               break;
/*     */             case 13:
/* 301 */               this.cookies[4] = tkn;
/* 302 */               break;
/*     */             case 14:
/* 304 */               this.adminid = tkn;
/* 305 */               break;
/*     */             case 15:
/* 307 */               this.clientid = tkn;
/* 308 */               break;
/*     */             case 16:
/* 310 */               this.clientName = tkn;
/* 311 */               break;
/*     */             case 17:
/* 313 */               this.qq = tkn;
/* 314 */               break;
/*     */             case 18:
/* 316 */               this.para1 = tkn;
/* 317 */               break;
/*     */             case 19:
/* 319 */               this.para2 = tkn;
/* 320 */               break;
/*     */             case 20:
/* 322 */               this.para3 = tkn;
/* 323 */               break;
/*     */             case 21:
/* 325 */               parseYellowCow(tkn);
/* 326 */               break;
/*     */             case 22:
/* 328 */               parseTypeList(tkn);
/*     */             }
/*     */ 
/* 332 */             count++;
/*     */           }
/* 334 */           if (!this.qq.startsWith(".")) {
/* 335 */             MainJFrame.instance.setTitle(MainJFrame.instance.getTitle() + " 代理QQ:" + this.qq);
/*     */           }
/*     */           else
/* 338 */             MainJFrame.instance.setTitle(MainJFrame.instance.getTitle() + " 作者QQ:309012359");
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 343 */         JOptionPane.showMessageDialog(instance, str, "登陆失败", 0);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void parseYellowCow(String str) {
/* 348 */     StringTokenizer tokenizer = new StringTokenizer(str, "&");
/* 349 */     while (tokenizer.hasMoreTokens()) {
/* 350 */       String tkn = tokenizer.nextToken();
/* 351 */       if (tkn != null) {
/* 352 */         int index = tkn.indexOf(";");
/* 353 */         YellowCow cow = new YellowCow();
/* 354 */         cow.id = tkn.substring(0, index);
/* 355 */         cow.name = tkn.substring(index + 1);
/* 356 */         this.cows.add(cow);
/* 357 */         Log.println(cow.id);
/* 358 */         Log.println(cow.name);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void parseTypeList(String str)
/*     */   {
/* 365 */     StringTokenizer tokenizer = new StringTokenizer(str, "-");
/* 366 */     MainJFrame.instance.getModelComboBox().removeAllItems();
/* 367 */     while (tokenizer.hasMoreTokens()) {
/* 368 */       String tkn = tokenizer.nextToken();
/* 369 */       if (tkn != null) {
/* 370 */         int index = tkn.indexOf(":");
/* 371 */         String type = tkn.substring(0, index);
/* 372 */         MainJFrame.instance.getModelComboBox().addItem(type);
/* 373 */         MainJFrame.phoneTypes.add(type);
/* 374 */         MainJFrame.phoneModels.add(tkn.substring(index + 1));
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
 * Qualified Name:     iphonebuyer.LoginDialog
 * JD-Core Version:    0.6.0
 */