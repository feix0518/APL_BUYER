/*     */ package iphonebuyer;
/*     */ 
/*     */ import java.awt.Container;
/*     */ import java.awt.Point;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.GroupLayout.Alignment;
/*     */ import javax.swing.GroupLayout.ParallelGroup;
/*     */ import javax.swing.GroupLayout.SequentialGroup;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JLabel;
import javax.swing.LayoutStyle;
import javax.swing.LayoutStyle.ComponentPlacement;
/*     */ 
/*     */ public class WarningDlg extends JDialog
/*     */ {
/*  19 */   public static boolean isShowed = false;
/*  20 */   public static WarningDlg instance = null;
/*     */   private JButton jButton1;
/*     */   private JLabel jLabel1;
/*     */ 
/*     */   public WarningDlg()
/*     */   {
/*  23 */     super(MainJFrame.instance, true);
/*  24 */     initComponents();
/*     */   }
/*     */   public void showUI() {
/*  27 */     isShowed = true;
/*  28 */     if (!isVisible())
/*     */     {
/*  30 */       int width = MainJFrame.instance.getLocation().x + (MainJFrame.instance.getWidth() - getWidth()) / 2;
/*  31 */       int height = MainJFrame.instance.getLocation().y + (MainJFrame.instance.getHeight() - getHeight()) / 2;
/*  32 */       setLocation(width, height);
/*  33 */       setVisible(true);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static WarningDlg getInstance() {
/*  37 */     if (instance == null)
/*  38 */       instance = new WarningDlg();
/*  39 */     return instance;
/*     */   }
/*     */ 
/*     */   private void initComponents()
/*     */   {
/*  51 */     this.jLabel1 = new JLabel();
/*  52 */     this.jButton1 = new JButton();
/*     */ 
/*  54 */     setDefaultCloseOperation(0);
/*  55 */     addWindowListener(new WindowAdapter() {
/*     */       public void windowClosing(WindowEvent evt) {
/*  57 */         WarningDlg.this.formWindowClosing(evt);
/*     */       }
/*     */     });
/*  61 */     this.jLabel1.setHorizontalAlignment(0);
/*  62 */     this.jLabel1.setText("<html><font size=22 color=red>网络被封了哦！</font></html>");
/*     */ 
/*  64 */     this.jButton1.setText("继续");
/*  65 */     this.jButton1.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/*  67 */         WarningDlg.this.jButton1ActionPerformed(evt);
/*     */       }
/*     */     });
/*  71 */     GroupLayout layout = new GroupLayout(getContentPane());
/*  72 */     getContentPane().setLayout(layout);
/*  73 */     layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1, -1, 323, 32767).addGroup(layout.createSequentialGroup().addGap(127, 127, 127).addComponent(this.jButton1).addContainerGap(139, 32767)));
/*     */ 
/*  81 */     layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jLabel1, -2, 94, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 18, 32767).addComponent(this.jButton1)));
/*     */ 
/*  89 */     pack();
/*     */   }
/*     */ 
/*     */   private void jButton1ActionPerformed(ActionEvent evt)
/*     */   {
/*  94 */     setVisible(false);
/*  95 */     isShowed = false;
/*  96 */     MainJFrame.instance.timoutcount = 0;
/*     */   }
/*     */ 
/*     */   private void formWindowClosing(WindowEvent evt)
/*     */   {
/* 101 */     setVisible(false);
/* 102 */     isShowed = false;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Administrator\桌面\5-22\3\
 * Qualified Name:     iphonebuyer.WarningDlg
 * JD-Core Version:    0.6.0
 */