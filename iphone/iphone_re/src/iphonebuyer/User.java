/*    */ package iphonebuyer;
/*    */ 
/*    */ public class User
/*    */ {
/*    */   public String id;
/*    */   public String adminid;
/*    */   public String name;
/*    */   public String appleid;
/*    */   public String password;
/*    */   public String idcode;
/*    */   public boolean used;
/*    */ 
/*    */   public User(String id, String adminid, String name, String appleid, String password, String idcode, boolean used)
/*    */   {
/* 22 */     this.name = name;
/* 23 */     this.appleid = appleid;
/* 24 */     this.password = password;
/* 25 */     this.idcode = idcode;
/* 26 */     this.used = used;
/* 27 */     this.id = id;
/* 28 */     this.adminid = adminid;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Administrator\桌面\5-22\3\
 * Qualified Name:     iphonebuyer.User
 * JD-Core Version:    0.6.0
 */