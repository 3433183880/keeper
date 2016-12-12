package com.keeper.expansion;

import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.keeper.client.KeeperClient;
import com.keeper.client.exception.KeeperException;
import com.keeper.expansion.locks.KeeperLock;
import com.keeper.expansion.locks.KeeperMutexLock;

/**
 *@author huangdou
 *@at 2016年12月10日下午11:25:52
 *@version 0.0.1
 */
public class TestClientLock {

	static KeeperClient client ;
	@Before
	public void before(){
		client = new KeeperClient("127.0.0.1:2181");
	}
	
	@After
	public void after(){
		if (client != null){
			client.closeClient();
			client = null;
		}
	}
	
	class MyThread extends Thread{
		private KeeperLock lock ;
		
		public MyThread(KeeperLock lock,String name) {
			super();
			this.lock = lock;
			this.setName(name);
		}

		@Override
		public void run() {
			lock.lock();
			try {
				System.out.println(Thread.currentThread().getName()+"  locked ");
				Thread.sleep(5000);
			} catch (InterruptedException e) {
			}finally{
				System.out.println(Thread.currentThread().getName()+"  unlock ");
				lock.unlock();
			}
		}
		
	}
	@Test
	public void testLock() throws InterruptedException {
		final KeeperLock lock1 = new KeeperMutexLock("testlocka", client);
		final KeeperLock lock2 = new KeeperMutexLock("testlocka2", client);
		
		new MyThread(lock1,"lock1a").start();
		new MyThread(lock2,"lock2222222222a").start();
		new MyThread(lock1,"lock1b").start();
		
		new MyThread(lock2,"lock2222222222b").start();
		new MyThread(lock2,"lock2222222222c").start();
		new MyThread(lock2,"lock2222222222d").start();
		new MyThread(lock1,"lock1c").start();
		
		Thread.sleep(100000);
	}
	
	
	//Use 3 machines,2 threads per machine to perform simultaneous addition from 1 to 100,result 600
	@Test
	public void machine1() throws InterruptedException {
		final AtomicBoolean down1 = new AtomicBoolean();
		final AtomicBoolean down2 = new AtomicBoolean();
		final String additionPath = "/addtitionData5";
		final KeeperClient client = new KeeperClient("127.0.0.1:2181");
		final KeeperLock lock = new KeeperMutexLock("addtition", client);
		final int total = 100;
		new Thread(new Runnable() {
			@Override
			public void run() {
				int i = 0 ;
				while (i<total){
					i++;
					lock.lock();
					try{
						byte[] data = client.read(additionPath);
						
						int ret = Integer.parseInt(new String(data)) + 1;
						client.update(additionPath, (ret+"").getBytes());
					}catch(KeeperException e){
						System.out.println(e);
					}finally{
						lock.unlock();
					}
				}
				down1.compareAndSet(false, true);
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				int i = 0 ;
				while (i<total){
					i++;
					lock.lock();
					try{
						byte[] data = client.read(additionPath);
						
						int ret = Integer.parseInt(new String(data)) + 1;
						client.update(additionPath, (ret+"").getBytes());
					}catch(KeeperException e){
						System.out.println(e);
					}finally{
						lock.unlock();
					}
				}
				down2.compareAndSet(false, true);
			}
		}).start();
		
		while(!down1.get()){
			
		}
		while(!down2.get()){
			
		}
	}
	
	@Test
	public void machine2() throws InterruptedException {
		final AtomicBoolean down1 = new AtomicBoolean();
		final AtomicBoolean down2 = new AtomicBoolean();
		final String additionPath = "/addtitionData5";
		final KeeperClient client = new KeeperClient("127.0.0.1:2181");
		final KeeperLock lock = new KeeperMutexLock("addtition", client);
		final int total = 100;
		new Thread(new Runnable() {
			@Override
			public void run() {
				int i = 0 ;
				while (i<total){
					i++;
					lock.lock();
					try{
						byte[] data = client.read(additionPath);
						
						int ret = Integer.parseInt(new String(data)) + 1;
						client.update(additionPath, (ret+"").getBytes());
					}catch(KeeperException e){
						System.out.println(e);
					}finally{
						lock.unlock();
					}
				}
				down1.compareAndSet(false, true);
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				int i = 0 ;
				while (i<total){
					i++;
					lock.lock();
					try{
						byte[] data = client.read(additionPath);
						
						int ret = Integer.parseInt(new String(data)) + 1;
						client.update(additionPath, (ret+"").getBytes());
					}catch(KeeperException e){
						System.out.println(e);
					}finally{
						lock.unlock();
					}
				}
				down2.compareAndSet(false, true);
			}
		}).start();
		
		while(!down1.get()){
			
		}
		while(!down2.get()){
			
		}
	}
	
	@Test
	public void machine3() throws InterruptedException {
		final AtomicBoolean down1 = new AtomicBoolean();
		final AtomicBoolean down2 = new AtomicBoolean();
		final String additionPath = "/addtitionData5";
		final KeeperClient client = new KeeperClient("127.0.0.1:2181");
		final KeeperLock lock = new KeeperMutexLock("addtition", client);
		final int total = 100;
		new Thread(new Runnable() {
			@Override
			public void run() {
				int i = 0 ;
				while (i<total){
					i++;
					lock.lock();
					try{
						byte[] data = client.read(additionPath);
						
						int ret = Integer.parseInt(new String(data)) + 1;
						client.update(additionPath, (ret+"").getBytes());
					}catch(KeeperException e){
						System.out.println(e);
					}finally{
						lock.unlock();
					}
				}
				down1.compareAndSet(false, true);
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				int i = 0 ;
				while (i<total){
					i++;
					lock.lock();
					try{
						byte[] data = client.read(additionPath);
						
						int ret = Integer.parseInt(new String(data)) + 1;
						client.update(additionPath, (ret+"").getBytes());
					}catch(KeeperException e){
						System.out.println(e);
					}finally{
						lock.unlock();
					}
				}
				down2.compareAndSet(false, true);
			}
		}).start();
		
		while(!down1.get()){
			
		}
		while(!down2.get()){
			
		}
	}
}
