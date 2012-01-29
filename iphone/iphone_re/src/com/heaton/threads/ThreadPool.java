/*     */ package com.heaton.threads;
/*     */ 
/*     */ import iphonebuyer.MainJFrame;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class ThreadPool
/*     */ {
/*  26 */   private Vector<WorkerThread> threads = null;
/*     */ 
/*  31 */   ArrayList assignments = new ArrayList(3);
/*     */ 
/*  37 */   protected Done done = new Done();
/*     */   private static ThreadPool instance;
/*     */ 
/*     */   public ThreadPool(int size)
/*     */   {
/*  46 */     this.threads = new Vector(size);
/*  47 */     for (int i = 0; i < size; i++) {
/*  48 */       WorkerThread thread = new WorkerThread(this);
/*  49 */       this.threads.add(thread);
/*  50 */       thread.start();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static ThreadPool getIntance() {
/*  54 */     if (instance == null)
/*     */     {
/*  58 */       instance = new ThreadPool(MainJFrame.instance.getThreadNum());
/*     */     }
/*  60 */     return instance;
/*     */   }
/*     */   public void addThread(int count) {
/*  63 */     for (int i = 0; i < count; i++) {
/*  64 */       WorkerThread thread = new WorkerThread(this);
/*  65 */       this.threads.add(thread);
/*  66 */       thread.start();
/*     */     }
/*     */   }
/*     */ 
/*     */   public synchronized void assign(Runnable r)
/*     */   {
/*  79 */     this.done.workerBegin();
/*  80 */     this.assignments.add(r);
/*  81 */     notify();
/*     */   }
/*     */ 
/*     */   public synchronized void assign(Runnable r, int index)
/*     */   {
/*  87 */     this.done.workerBegin();
/*  88 */     this.assignments.add(index, r);
/*  89 */     notify();
/*     */   }
/*     */ 
/*     */   public synchronized Runnable getAssignment()
/*     */   {
/*     */     try
/*     */     {
/* 101 */       while (!this.assignments.iterator().hasNext()) {
/* 102 */         wait();
/*     */       }
/* 104 */       Runnable r = (Runnable)this.assignments.iterator().next();
/* 105 */       this.assignments.remove(r);
/* 106 */       return r;
/*     */     } catch (InterruptedException e) {
/* 108 */       this.done.workerEnd();
/* 109 */     }return null;
/*     */   }
/*     */ 
/*     */   public void complete()
/*     */   {
/* 119 */     this.done.waitBegin();
/* 120 */     this.done.waitDone();
/*     */   }
/*     */ 
/*     */   protected void finalize()
/*     */   {
/* 127 */     this.done.reset();
/* 128 */     for (int i = 0; i < this.threads.size(); i++) {
/* 129 */       ((WorkerThread)this.threads.get(i)).interrupt();
/* 130 */       this.done.workerBegin();
/* 131 */       ((WorkerThread)this.threads.get(i)).destroy();
/*     */     }
/* 133 */     this.done.waitDone();
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Administrator\桌面\5-22\3\
 * Qualified Name:     com.heaton.threads.ThreadPool
 * JD-Core Version:    0.6.0
 */