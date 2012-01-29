/*     */ package com.heaton.threads;
/*     */ 
/*     */ import iphonebuyer.Log;
/*     */ 
/*     */ class WorkerThread extends Thread
/*     */ {
/*     */   public boolean busy;
/*     */   public ThreadPool owner;
/*     */ 
/*     */   WorkerThread(ThreadPool o)
/*     */   {
/* 160 */     this.owner = o;
/*     */   }
/*     */ 
/*     */   public void run()
/*     */   {
/* 169 */     Runnable target = null;
/*     */     do {
/* 171 */       target = this.owner.getAssignment();
/* 172 */       if (target != null) {
/* 173 */         target.run();
/* 174 */         this.owner.done.workerEnd();
/*     */       }
/*     */ 
/* 177 */       Log.println(toString());
/* 178 */     }while (target != null);
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Administrator\桌面\5-22\3\
 * Qualified Name:     com.heaton.threads.WorkerThread
 * JD-Core Version:    0.6.0
 */