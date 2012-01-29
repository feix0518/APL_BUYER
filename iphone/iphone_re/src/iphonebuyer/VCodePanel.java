/*     */ package iphonebuyer;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.GroupLayout.Alignment;
/*     */ import javax.swing.GroupLayout.ParallelGroup;
/*     */ import javax.swing.GroupLayout.SequentialGroup;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.LayoutStyle.ComponentPlacement;
/*     */ 
/*     */ public class VCodePanel extends JPanel
/*     */ {
/*     */   private JButton jButton3;
/*     */   private JButton jButton4;
/*     */   private JLabel jLabel3;
/*     */   private JPanel jPanel2;
/*     */   private JTextField jTextField1;
/*     */ 
/*     */   public VCodePanel()
/*     */   {
/*  24 */     initComponents();
/*     */   }
/*     */ 
/*     */   private void initComponents()
/*     */   {
/*  36 */     this.jPanel2 = new JPanel();
/*  37 */     this.jLabel3 = new PicLabel();
/*  38 */     this.jTextField1 = new JTextField();
/*  39 */     this.jButton3 = new JButton();
/*  40 */     this.jButton4 = new JButton();
/*     */ 
/*  42 */     this.jPanel2.setBorder(BorderFactory.createTitledBorder("请输入验证码(字母不分大小写)"));
/*  43 */     Dimension ps = new Dimension();
/*  44 */     ps.setSize(343.5D, 133.5D);
/*  45 */     this.jLabel3.setPreferredSize(ps);
/*     */ 
/*  47 */     Dimension s = new Dimension();
/*  48 */     s.setSize(343.5D, 133.5D);
/*  49 */     this.jLabel3.setSize(s);
/*     */ 
/*  51 */     Dimension ms = new Dimension();
/*  52 */     ms.setSize(343.5D, 133.5D);
/*  53 */     this.jLabel3.setMinimumSize(ms);
/*     */ 
/*  55 */     Dimension xs = new Dimension();
/*  56 */     xs.setSize(343.5D, 133.5D);
/*  57 */     this.jLabel3.setMaximumSize(xs);
/*     */ 
/*  59 */     this.jTextField1.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/*  61 */         VCodePanel.this.jTextField1ActionPerformed(evt);
/*     */       }
/*     */     });
/*  65 */     this.jButton3.setText("确定");
/*  66 */     this.jButton3.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/*  68 */         VCodePanel.this.jButton3ActionPerformed(evt);
/*     */       }
/*     */     });
/*  72 */     this.jButton4.setText("重刷");
/*  73 */     this.jButton4.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/*  75 */         VCodePanel.this.jButton4ActionPerformed(evt);
/*     */       }
/*     */     });
/*  79 */     GroupLayout jPanel2Layout = new GroupLayout(this.jPanel2);
/*  80 */     this.jPanel2.setLayout(jPanel2Layout);
/*  81 */     jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel3, -2, 344, -2).addGroup(jPanel2Layout.createSequentialGroup().addComponent(this.jTextField1, -1, 210, 32767).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jButton4, -2, 65, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jButton3))).addContainerGap()));
/*     */ 
/*  94 */     jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addContainerGap(-1, 32767).addComponent(this.jLabel3, -2, 140, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jTextField1, -2, -1, -2).addComponent(this.jButton4).addComponent(this.jButton3))));
/*     */ 
/* 106 */     GroupLayout layout = new GroupLayout(this);
/* 107 */     setLayout(layout);
/* 108 */     layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jPanel2, -2, 360, -2));
/*     */ 
/* 112 */     layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jPanel2, -2, -1, -2));
/*     */   }
/*     */ 
/*     */   private void jTextField1ActionPerformed(ActionEvent evt)
/*     */   {
/*     */   }
/*     */ 
/*     */   private void jButton3ActionPerformed(ActionEvent evt)
/*     */   {
/*     */   }
/*     */ 
/*     */   private void jButton4ActionPerformed(ActionEvent evt)
/*     */   {
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Administrator\桌面\5-22\3\
 * Qualified Name:     iphonebuyer.VCodePanel
 * JD-Core Version:    0.6.0
 */