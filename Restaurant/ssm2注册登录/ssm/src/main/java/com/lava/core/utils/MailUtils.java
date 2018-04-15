package com.lava.core.utils;

import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import com.cn.hnust.been.Person;
import com.cn.hnust.controller.LoginController;
import com.sun.mail.util.MailSSLSocketFactory;



public class MailUtils {
	private static Logger logger = Logger.getLogger(MailUtils.class);
	//--------------����---------------------
    public static final String FROM = "1617116339@qq.com";//�����˵�email
    public static final String PWD = "nojcmftvipstbhhb";//����������--��������
    public static final String URL = "http://localhost:8080/ssm/login";//��Ŀ��ҳ
    public static final int TIMELIMIT = 1000*60*60*24; //�����ʼ�����ʱ��24Сʱ
    public static final String TITLE = "�����ʼ�";
 
//---------------�Զ��庯��-----------------
    public static Person sendMail(Person u) throws AddressException, MessagingException, NoSuchAlgorithmException {
        //ע������
        String to  = u.getMail();
        //��ǰʱ���
        Long curTime = System.currentTimeMillis();
        //�������Чʱ��
        Long activateTime = curTime+TIMELIMIT;
        //������--���ڼ��������˺�       
        u.setActiCode(UUIDUtils.getUUID()+curTime);     
        String actiCode = u.getActiCode();
        //����ʱ��       
        u.setToken_exptime(String.valueOf(activateTime));
        //���͵���������
        String content = "����������֤�룺"
                +"<br><a href='"+URL+"/activatemail/?actiCode="+actiCode+"&email="+to+"'>"
                +URL+"/activatemail/?actiCode="+actiCode+"&email="+to+"</a></p>";
        //���÷����������
        sendMailEntity(to, TITLE, content);
        return u;
    }

	
	 public static void sendMailEntity(String to,String title,String content) throws AddressException, MessagingException {

	        Properties props = new Properties(); //���Լ���һ�������ļ�      
	        // �����ʼ�������������
	        props.setProperty("mail.host", "smtp.qq.com");//ָ���ʼ���������Ĭ�϶˿� 25
	        // ���ͷ�������Ҫ�����֤
	        props.setProperty("mail.smtp.auth", "true");//Ҫ����ָ���û�������ķ�ʽȥ��֤
	        // �����ʼ�Э������
	        props.setProperty("mail.transport.protocol", "smtp");

	     // ����SSL���ܣ������ʧ��
	        MailSSLSocketFactory sf = null;
			try {
				sf = new MailSSLSocketFactory();
			} catch (GeneralSecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        sf.setTrustAllHosts(true);
	        props.put("mail.smtp.ssl.enable", "true");
	        props.put("mail.smtp.ssl.socketFactory", sf);

	        //���������½�һ���ʼ��Ự  
	        Session session = Session.getInstance(props,new Authenticator() {
	        	@Override
	        	protected PasswordAuthentication getPasswordAuthentication() {
	        		// TODO Auto-generated method stub
	        		return new PasswordAuthentication(FROM, PWD);
	        	}
			});
	        session.setDebug(true); //�������ӡһЩ������Ϣ��  
	        MimeMessage message = new MimeMessage(session);//���ʼ��Ự�½�һ����Ϣ����  
	        message.setFrom(new InternetAddress(FROM));//���÷����˵ĵ�ַ  
	        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));//�����ռ���,���������������ΪTO   	       
	        message.setSubject(title,"UTF-8");//���ñ���  
	        //�����ż�����  
	        //message.setText(content,"UTF-8"); //���� ���ı� �ʼ� todo  
	        message.setContent(content, "text/html;charset=utf-8"); //����HTML�ʼ���������ʽ�ȽϷḻ  
	        message.setSentDate(new Date());//���÷���ʱ��  
	        //message.saveChanges();//�洢�ʼ���Ϣ  
	        //�����ʼ�  
	        Transport.send(message);     
	        logger.debug("Transport.send"); 
	        /*//�����ʼ� (��)
	        Transport transport = session.getTransport(SMTP);  
	        //Transport transport = session.getTransport();  
	        transport.connect(FROM, PWD);
	        transport.sendMessage(message, message.getAllRecipients());//�����ʼ�,���еڶ�����������������õ��ռ��˵�ַ  
	        transport.close(); */ 
	    }

}
