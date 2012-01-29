/*     */ package com.heaton.threads;
/*     */ 
/*     */ public class Done
/*     */ {
/*  30 */   private int _activeThreads = 0;
/*     */ 
/*  40 */   private boolean _started = false;
/*     */ 
/*     */   public synchronized void waitDone()
/*     */   {
/*     */     try
/*     */     {
/*  50 */       while (this._activeThreads > 0)
/*  51 */         wait();
/*     */     }
/*     */     catch (InterruptedException e)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public synchronized void waitBegin()
/*     */   {
/*     */     try
/*     */     {
/*  65 */       while (!this._started)
/*  66 */         wait();
/*     */     }
/*     */     catch (InterruptedException e)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public synchronized void workerBegin()
/*     */   {
/*  80 */     this._activeThreads += 1;
/*  81 */     this._started = true;
/*  82 */     notify();
/*     */   }
/*     */ 
/*     */   public synchronized void workerEnd()
/*     */   {
/*  92 */     this._activeThreads -= 1;
/*  93 */     notify();
/*     */   }
/*     */ 
/*     */   public synchronized void reset()
/*     */   {
/* 102 */     this._activeThreads = 0;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Administrator\桌面\5-22\3\
 * Qualified Name:     com.heaton.threads.Done
 * JD-Core Version:    0.6.0
 */