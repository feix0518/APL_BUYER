/*     */ package iphonebuyer;
/*     */ 
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Point;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.GroupLayout.Alignment;
/*     */ import javax.swing.GroupLayout.ParallelGroup;
/*     */ import javax.swing.GroupLayout.SequentialGroup;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.LayoutStyle.ComponentPlacement;
/*     */ 
/*     */ public class CodeInputDlg extends JDialog
/*     */ {
/*     */   Getor getor;
/*     */   private String code;
/*  24 */   public static boolean showed = false;
/*     */   private JLabel jLabel3;
/*     */   private JPanel jPanel2;
/*     */   private JTextField jTextField1;
/*     */ 
/*     */   public CodeInputDlg(Getor getor)
/*     */   {
/*  28 */     super(MainJFrame.instance, true);
/*  29 */     this.getor = getor;
/*  30 */     setTitle(getor.getUser().name);
/*  31 */     initComponents();
/*     */   }
/*     */ 
/*     */   public void setImage(String imageFile) {
/*  35 */     ImageIcon icon = new ImageIcon(imageFile);
/*  36 */     this.jLabel3.setIcon(icon);
/*     */   }
/*     */ 
/*     */   public void showUI() {
/*  40 */     showed = true;
/*  41 */     int width = MainJFrame.instance.getLocation().x + (MainJFrame.instance.getWidth() - getWidth()) / 2;
/*  42 */     int height = MainJFrame.instance.getLocation().y + (MainJFrame.instance.getHeight() - getHeight()) / 2;
/*     */ 
/*  47 */     setLocation(width, height);
/*  48 */     setVisible(true);
/*     */   }
/*     */ 
/*     */   private void initComponents()
/*     */   {
/*  61 */     this.jPanel2 = new JPanel();
/*  62 */     this.jLabel3 = new PicLabel();
/*  63 */     this.jTextField1 = new JTextField();
/*     */ 
/*  65 */     setDefaultCloseOperation(2);
/*  66 */     setAlwaysOnTop(true);
/*     */ 
/*  68 */     this.jPanel2.setBorder(BorderFactory.createTitledBorder("请输入验证码(字母不分大小写)"));
/*  69 */     Dimension ps = new Dimension();
/*  70 */     ps.setSize(343.5D, 133.5D);
/*  71 */     this.jLabel3.setPreferredSize(ps);
/*     */ 
/*  73 */     Dimension s = new Dimension();
/*  74 */     s.setSize(343.5D, 133.5D);
/*  75 */     this.jLabel3.setSize(s);
/*     */ 
/*  77 */     Dimension ms = new Dimension();
/*  78 */     ms.setSize(343.5D, 133.5D);
/*  79 */     this.jLabel3.setMinimumSize(ms);
/*     */ 
/*  81 */     Dimension xs = new Dimension();
/*  82 */     xs.setSize(343.5D, 133.5D);
/*  83 */     this.jLabel3.setMaximumSize(xs);
/*     */ 
/*  85 */     this.jTextField1.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/*  87 */         CodeInputDlg.this.jTextField1ActionPerformed(evt);
/*     */       }
/*     */     });
/*  91 */     GroupLayout jPanel2Layout = new GroupLayout(this.jPanel2);
/*  92 */     this.jPanel2.setLayout(jPanel2Layout);
/*  93 */     jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel3, -1, 344, 32767).addComponent(this.jTextField1, -1, 344, 32767));
/*     */ 
/*  98 */     jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addComponent(this.jLabel3, -2, 140, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jTextField1, -1, 35, 32767)));
/*     */ 
/* 106 */     GroupLayout layout = new GroupLayout(getContentPane());
/* 107 */     getContentPane().setLayout(layout);
/* 108 */     layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jPanel2, -1, -1, 32767));
/*     */ 
/* 112 */     layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jPanel2, -1, -1, 32767));
/*     */ 
/* 117 */     pack();
/*     */   }
/*     */ 
/*     */   private void jTextField1ActionPerformed(ActionEvent evt)
/*     */   {
/* 122 */     Log.println("----------input:" + this.jTextField1.getText() + "-------------");
/* 123 */     this.code = this.jTextField1.getText().trim();
/*     */ 
/* 125 */     dispose();
/*     */   }
/*     */ 
/*     */   public String getCode() {
/* 129 */     return this.code;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Administrator\桌面\5-22\3\
 * Qualified Name:     iphonebuyer.CodeInputDlg
 * JD-Core Version:    0.6.0
 */