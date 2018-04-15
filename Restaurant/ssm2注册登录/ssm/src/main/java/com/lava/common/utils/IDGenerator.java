package com.lava.common.utils;   

import java.util.Random;

/**
 * ����һ�������������Ψһ��־����־��Ϊ4����ɲ���
 * ϵͳʱ��+����ID+�߳�ID+��ˮ��
 * 
 * @author Shengle.Liao
 */
public class IDGenerator { 
	
	private static final ThreadLocal<IDGenerator> LOCAL = new ThreadLocal<IDGenerator>();

	private final static long twepoch = 1288834974657L;
	//����ID��ռ�Ķ����Ƴ���
	private final static long procIdBits = 6L;
	//�߳�ID��ռ�Ķ����Ƴ���
	private final static long threadIdBits = 9L; 
	//����ID���ֵ
	private final static long maxProcId = -1L ^ (-1L << procIdBits);  
	//�߳�ID���ֵ
	private final static long maxThreadId = -1L ^ (-1L << threadIdBits);
	//����������λ����
	private final static long sequenceBits = 12L;
	//����IDƫ����12λ
	private final static long procIdShift = sequenceBits;
	//�߳�ID����17λ
	private final static long threadIdShift = sequenceBits + procIdBits;
	//ʱ���������22λ
	private final static long timestampLeftShift = sequenceBits + procIdBits + threadIdBits;
	private final static long sequenceMask = -1L ^ (-1L << sequenceBits);

	//�����������
	private static Random r = new Random();
	
	private final long createThreadId;
	
	//���һ������IDʱ��ĺ���ֵ
	private long lastTimestamp = -1L;
	//��ǰ�������Ѿ����ɵ�����
	private long sequence = 0L;
	//����ID
	private long procId = 1L;
	//�߳�ID
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
        	throw new RuntimeException("����IDGeneratorʵ������");
        }
        return generator;
    }
	
	private IDGenerator() {
		//����id���̺߳� 
		this.createThreadId = Thread.currentThread().getId();
		//Ĭ�ϵĹ��췽���У�ʹ���������ID
		this.procId = r.nextInt((int)(maxProcId+1));
		// ʹ�õ�ǰ�߳�id��Ϊ�̱߳�־����ֹ����̼߳�������ͬ�����к�
		this.threadId = createThreadId;  
		checkParameters();
	}

	/**
	 * ��ֹ����ָ����procID��threadID����Ҫ������     
	 * �����������ʹ���������������
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
	 * ����Long�����к�
	 * @return
	 */
	public synchronized long generateLong() {
		if(this.createThreadId != Thread.currentThread().getId()){ 
			throw new RuntimeException("����UniqueID������̺߳��ǣ�" + this.createThreadId 
					+ ",��������id�������̺߳��ǣ�" + Thread.currentThread().getId() 
					+ ",������ʹ�������ķ�ʽ�������к�"); 
		}
		//��ǰ������
		long timestamp = timeGen();
		if (timestamp < lastTimestamp) {
			try { 
				throw new Exception("�����߼�����.���̻߳����²���������: "
								+ (lastTimestamp - timestamp) + " milliseconds");  
			} catch (Exception e) {
				throw new RuntimeException(e); 
			}
		}

		if (lastTimestamp == timestamp) {
			// ��ǰ�����ڣ���+1
			sequence = ((sequence + 1) & sequenceMask);
			if (sequence == 0) {
				// ��ǰ�����ڼ������ˣ���ȴ���һ�� 
				timestamp = tilNextMillis(lastTimestamp);
			} 
		} else {
			sequence = 0;
		}
		lastTimestamp = timestamp;
		// IDƫ������������յ�ID��������ID
		long nextId = ((timestamp - twepoch) << timestampLeftShift)
				| (threadId << threadIdShift)
				| (procId << procIdShift) | sequence; 
		return nextId; 
	}
	
	/**
	 * �����ַ����͵����к�
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
