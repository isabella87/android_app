package com.banhuitong.async;

import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
/**
 * 线程池
 * @author Administrator
 *
 */
public class DefaultThreadPool {
	// BaseRequest任务队列
	private static ArrayBlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<Runnable>(
			15);
	// 线程池
	public static AbstractExecutorService pool = new ThreadPoolExecutor(10, 20,
			15L, TimeUnit.SECONDS, blockingQueue,
			new ThreadPoolExecutor.DiscardOldestPolicy());
	
	private static DefaultThreadPool instance=null;
	
	public static DefaultThreadPool getInstance(){
		if(instance==null){
			instance=new DefaultThreadPool();
		}
		return instance;
	}
	public void execute(Runnable r){
		pool.execute(r);
	}
	
	public static void shutDown(){
		if(pool!=null){
			pool.shutdown();
		}
	}
	
	public static void shutDownRightNow(){
		if(pool!=null){
//			List<Runnable> tasks=pool.shutdownNow();
			pool.shutdownNow();
			try {
				pool.awaitTermination(1, TimeUnit.MICROSECONDS);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void removeTaskFromQueue(){
	}
}