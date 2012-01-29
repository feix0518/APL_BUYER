/*    */ package iphonebuyer;
/*    */ 
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.IOException;
/*    */ import java.net.URL;
/*    */ import sun.audio.AudioData;
/*    */ import sun.audio.AudioPlayer;
/*    */ import sun.audio.AudioStream;
/*    */ import sun.audio.ContinuousAudioDataStream;
/*    */ 
/*    */ public class MusicPlayer
/*    */ {
/*    */   private AudioStream as;
/*    */   ContinuousAudioDataStream cas;
/* 18 */   Thread thread = null;
/*    */ 
/*    */   public MusicPlayer(URL url) {
/*    */     try {
/* 22 */       this.as = new AudioStream(url.openStream());
/*    */     }
/*    */     catch (FileNotFoundException e) {
/* 25 */       e.printStackTrace();
/*    */     }
/*    */     catch (IOException e) {
/* 28 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ 
/*    */   public void start() {
/* 33 */     if (this.as == null) {
/* 34 */       Log.println("AudioStream object is not created!");
/* 35 */       return;
/*    */     }
/* 37 */     AudioPlayer.player.start(this.as);
/*    */   }
/*    */ 
/*    */   public void stop()
/*    */   {
/* 44 */     if (this.as == null) {
/* 45 */       Log.println("AudioStream object is not created!");
/* 46 */       return;
/*    */     }
/* 48 */     AudioPlayer.player.stop(this.as);
/*    */   }
/*    */ 
/*    */   public void continuousStart()
/*    */   {
/* 55 */     AudioData data = null;
/*    */     try {
/* 57 */       data = this.as.getData();
/*    */     }
/*    */     catch (IOException e) {
/* 60 */       e.printStackTrace();
/*    */     }
/*    */ 
/* 64 */     this.cas = new ContinuousAudioDataStream(data);
/*    */ 
/* 67 */     AudioPlayer.player.start(this.cas);
/*    */   }
/*    */ 
/*    */   public void continuousStop()
/*    */   {
/* 72 */     if (this.cas != null)
/* 73 */       AudioPlayer.player.stop(this.cas);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Administrator\桌面\5-22\3\
 * Qualified Name:     iphonebuyer.MusicPlayer
 * JD-Core Version:    0.6.0
 */