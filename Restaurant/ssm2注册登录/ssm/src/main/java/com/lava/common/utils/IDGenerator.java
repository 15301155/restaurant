package com.lava.common.utils;   

import java.util.Random;

/**
 * 生成一个有序的整数型唯一标志，标志分为4个组成部分
 * 系统时间+进程ID+线程ID+流水号
 * 
 * @author Shengle.Liao
 */
public class IDGenerator { 
	
	private static final ThreadLocal<IDGenerator> LOCAL = new ThreadLocal<IDGenerator>();

	private final static long twepoch = 1288834974657L;
	//进程ID所占的二进制长度
	private final static long procIdBits = 6L;
	//线程ID所占的二进制长度
	private final static long threadIdBits = 9L; 
	//进程ID最大值
	private final static long maxProcId = -1L ^ (-1L << procIdBits);  
	//线程ID最大值
	private final static long maxThreadId = -1L ^ (-1L << threadIdBits);
	//毫秒内自增位长度
	private final static long sequenceBits = 12L;
	//进程ID偏左移12位
	private final static long procIdShift = sequenceBits;
	//线程ID左移17位
	private final static long threadIdShift = sequenceBits + procIdBits;
	//时间毫秒左移22位
	private final static long timestampLeftShift = sequenceBits + procIdBits + threadIdBits;
	private final static long sequenceMask = -1L ^ (-1L << sequenceBits);

	//随机数生成器
	private static Random r = new Random();
	
	private final long createThreadId;
	
	//最后一次生成ID时间的毫秒值
	private long lastTimestamp = -1L;
	//当前毫秒内已经生成的数量
	private long sequence = 0L;
	//进程ID
	private long procId = 1L;
	//线程ID
	private long threadId = 1L;

    public static IDGenerator getInstance() {
    	IDGenerator generator = LOCAL.get();
        try{
            if(generator == null){
                generator = new IDGenerator();
                LOCAL.set(generator);
            }
        }catch(Exception ex){
        	ex.printStackTrace();
        	throw new RuntimeException("生成IDGenerator实例出错！");
        }
        return generator;
    }
	
	private IDGenerator() {
		//创建id的线程号 
		this.createThreadId = Thread.currentThread().getId();
		//默认的构造方法中，使用随机进程ID
		this.procId = r.nextInt((int)(maxProcId+1));
		// 使用当前线程id作为线程标志，防止多个线程间生成相同的序列号
		this.threadId = createThreadId;  
		checkParameters();
	}

	/**
	 * 防止出现指定的procID和threadID超出要求的情况     
	 * 如果超出，则使用随机数字来代表
	 */
	private void checkParameters() {
		if (procId > maxProcId || procId < 0) {
			procId = r.nextInt((int) (maxProcId + 1)); 
		}
		if (threadId > maxThreadId || threadId < 0) {
			threadId = r.nextInt((int) (maxThreadId + 1));
		}
	}

	private IDGenerator(long procId){  
		this.createThreadId = Thread.currentThread().getId();
		this.procId = procId;
		this.threadId = createThreadId;
		checkParameters(); 
	}
	
	/**
	 * 生成Long型序列号
	 * @return
	 */
	public synchronized long generateLong() {
		if(this.createThreadId != Thread.currentThread().getId()){ 
			throw new RuntimeException("创建UniqueID对象的线程号是：" + this.createThreadId 
					+ ",调用生成id方法的线程号是：" + Thread.currentThread().getId() 
					+ ",不允许使用这样的方式生成序列号"); 
		}
		//当前毫秒数
		long timestamp = timeGen();
		if (timestamp < lastTimestamp) {
			try { 
				throw new Exception("发生逻辑错误.多线程环境下产生的问题: "
								+ (lastTimestamp - timestamp) + " milliseconds");  
			} catch (Exception e) {
				throw new RuntimeException(e); 
			}
		}

		if (lastTimestamp == timestamp) {
			// 当前毫秒内，则+1
			sequence = ((sequence + 1) & sequenceMask);
			if (sequence == 0) {
				// 当前毫秒内计数满了，则等待下一秒 
				timestamp = tilNextMillis(lastTimestamp);
			} 
		} else {
			sequence = 0;
		}
		lastTimestamp = timestamp;
		// ID偏移组合生成最终的ID，并返回ID
		long nextId = ((timestamp - twepoch) << timestampLeftShift)
				| (threadId << threadIdShift)
				| (procId << procIdShift) | sequence; 
		return nextId; 
	}
	
	/**
	 * 生成字符串型的序列号
	 */
	public synchronized String generate() {
		return String.valueOf(this.generateLong());
	}

	private synchronized long tilNextMillis(final long lastTimestamp) {
		long timestamp = this.timeGen();
		while (timestamp <= lastTimestamp) {
			timestamp = this.timeGen();
		}
		return timestamp;
	}

	private synchronized long timeGen() {
		return System.currentTimeMillis();
	}
	
}
