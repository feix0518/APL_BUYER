/*    */ package iphonebuyer;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ public class CartRequestSender
/*    */ {
/* 16 */   private ArrayList<Status> status = new ArrayList(5);
/*    */   public Getor getor;
/*    */   public String ProductUrl;
/*    */   public String ProductCookie;
/* 20 */   public int count = 1;
/*    */ 
/* 22 */   public CartRequestSender(Getor getor, String ProductUrl, String ProductCookie) { this.getor = getor;
/* 23 */     this.ProductCookie = ProductCookie;
/* 24 */     this.ProductUrl = ProductUrl; }
/*    */ 
/*    */   public void send()
/*    */   {
/* 28 */     for (int i = 0; i < 1; i++) {
/* 29 */       Status stat = new Status();
/* 30 */       this.status.add(stat);
/* 31 */       CartRequestSend thread = new CartRequestSend(this, stat);
/* 32 */       thread.start();
/*    */     }
/*    */   }
/*    */ 
/*    */   public boolean areAllFinished() {
/* 36 */     for (int i = 0; i < this.status.size(); i++) {
/* 37 */       if (!((Status)this.status.get(i)).finished)
/* 38 */         return false;
/*    */     }
/* 40 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Administrator\桌面\5-22\3\
 * Qualified Name:     iphonebuyer.CartRequestSender
 * JD-Core Version:    0.6.0
 */