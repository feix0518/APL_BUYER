/*    */ package networking.http;
/*    */ 
/*    */ import java.util.Set;
/*    */ 
/*    */ public class HttpResponse
/*    */ {
/*    */   public Set header;
/*    */   public Object body;
/*    */ 
/*    */   public HttpResponse(Set header, Object body)
/*    */   {
/* 18 */     this.header = header;
/* 19 */     this.body = body;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Administrator\桌面\5-22\3\
 * Qualified Name:     networking.http.HttpResponse
 * JD-Core Version:    0.6.0
 */