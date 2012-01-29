/*    */ package iphonebuyer;
/*    */ 
/*    */ import java.awt.Graphics;
/*    */ import javax.swing.ImageIcon;
/*    */ import javax.swing.JLabel;
/*    */ 
/*    */ public class PicLabel extends JLabel
/*    */ {
/*    */   public void paintComponent(Graphics g)
/*    */   {
/* 21 */     ImageIcon icon = (ImageIcon)getIcon();
/* 22 */     if (icon != null)
/* 23 */       g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), null);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Administrator\桌面\5-22\3\
 * Qualified Name:     iphonebuyer.PicLabel
 * JD-Core Version:    0.6.0
 */