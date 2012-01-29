/*    */ package iphonebuyer;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import networking.http.ShoppingCart;
/*    */ 
/*    */ class CartRequestSend extends Thread
/*    */ {
/*    */   CartRequestSender parent;
/*    */   Status status;
/*    */ 
/*    */   public CartRequestSend(CartRequestSender parent, Status status)
/*    */   {
/* 51 */     this.parent = parent;
/* 52 */     this.status = status;
/*    */   }
/*    */ 
/*    */   public void run()
/*    */   {
/* 57 */     String addCartUrl = "http://reserve.apple.com/WebObjects/ProductReservation.woa/wa/GianduiaAction/addToCart";
/* 58 */     int cartStatus = 3;
/* 59 */     int cartCount = 0;
/*    */ 
/* 61 */     while ((this.parent.getor.running) && (cartStatus != 1))
/*    */     {
/* 63 */       ShoppingCart cart = new ShoppingCart();
/* 64 */       cartStatus = cart.AddCart(this.parent.getor, addCartUrl, this.parent.ProductUrl, this.parent.ProductCookie, (String)MainJFrame.phoneModels.get(MainJFrame.instance.getSelectedPhone()));
//cartStatus = cart.AddCart(this.parent.getor, addCartUrl, this.parent.ProductUrl, this.parent.ProductCookie, "skuID=MD128CHA");
/*    */       // skuID=MD128CHA&planID=null&lang=zh&country=CN

/* 66 */       if (cartStatus == 2) {
/* 67 */         cartCount++; MainJFrame.instance.setResult(this.parent.getor, "会话超时(" + cartCount + ")");
/* 68 */         if (MainJFrame.instance.isRollback())
/*    */         {
/*    */           break;
/*    */         }
/*    */       }
/*    */     }
/* 74 */     if (cartStatus == 1) {
/* 75 */       this.status.finished = true;
/* 76 */       this.parent.getor.i_status += 1;
/* 77 */       String currentStatus = this.parent.getor.user.name + ":放入购物车第(" + this.parent.count++ + ")款....";
/* 78 */       MainJFrame.instance.setCurrentStatus(this.parent.getor, this.parent.getor.i_status, currentStatus);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Administrator\桌面\5-22\3\
 * Qualified Name:     iphonebuyer.CartRequestSend
 * JD-Core Version:    0.6.0
 */