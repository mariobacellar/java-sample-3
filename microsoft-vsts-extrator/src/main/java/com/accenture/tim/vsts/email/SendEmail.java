package com.accenture.tim.vsts.email;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * Spring’s Mail Sender
 * @author mario.bacellar
 *
 */
public class SendEmail {
	
	final static Logger log  = Logger.getLogger(SendEmail.class);

	public List<String> buildRecipients() throws Exception {
		log.debug("-> buildRecipients()");
		
		Properties 
		properties = new Properties();
		properties.load(SendEmail.class.getResourceAsStream("/send.email.properties"));
        		
		String app_mail_list=properties.getProperty("app.mail.list");
		
		String[] arryAccounts= app_mail_list.split(";");
		
		List<String> ret = Arrays.asList(arryAccounts);
		
		log.debug("-  buildRecipients - ret=["+ret+"]");
		return ret;
	}
	
	
    /**
     * Sends an email message with attachments.
     *
     * @param from        email address from which the message will be sent.
     * @param recipients  array of strings containing the recipients of the message.
     * @param subject     subject header field.
     * @param text        content of the message.
     * @param attachments attachments to be included with the message.
     * @param fileNames   file names for each attachment.
     * @param mimeTypes   mime types for each attachment.
     * 
     * @throws MessagingException
     * @throws IOException
     */
    public void send(	String				from				, 
    					Collection<String>	recipients			, 
    					String				subject				, 
    					String				text				,
    					List<InputStream>	attachments			, 
    					List<String>		fileNames			, 
    					List<String>		mimeTypes			) throws MessagingException, IOException {

		log.info("-> send");
        // check for null references
        Objects.requireNonNull(from);
        Objects.requireNonNull(recipients);

        
        log.info("-  Load email configuration from properties file");
        Properties 
        properties = new Properties();
        properties.load(SendEmail.class.getResourceAsStream("/send.email.properties"));
        
        // configure the connection to the SMTP server
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setJavaMailProperties(properties);

        MimeMessage message = mailSender.createMimeMessage();
        
        MimeMessageHelper 
        helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom		(from);
        helper.setSubject	(subject);
        helper.setText		(text, true);
        
        for (String recipient : recipients) 
            helper.addTo(recipient);

        if (attachments != null) {
            for (int i = 0; i < attachments.size(); i++) {
            	log.info("-  Create a data source to wrap the attachment and its mime type");
                ByteArrayDataSource dataSource = new ByteArrayDataSource(attachments.get(i), mimeTypes.get(i));
                log.info("-  Add the attachment=["+fileNames.get(i)+"]");
                helper.addAttachment(fileNames.get(i), dataSource);
            }
        }
        mailSender.send(message);
		log.info("<- send");
    }
	
	public static void main( String[] args ) {
		
		try {
			log.info("========================================================================");
			log.info("-> main");
			log.info("========================================================================");
	    	SendEmail sendEmail	= new SendEmail();

			String from			= "accenture@accenture.com";
			String subject		= null;
			String msg0			= "Hello Spring Email Sender.\n\n";
			String msg			= "";
			
			log.info("========================================================================");
			log.info("-> SendEmail - MULT destinatário");
			log.info("========================================================================");
			String 		fileName	= "REPOEPICDIFF_20180607.csv";
			Resource 	resource 	= new ClassPathResource("/diff/"+fileName);
			InputStream attachment	= resource.getInputStream();
			String 		mimeType	=  "application/txt";

			List<String>	
            multRecipients = new ArrayList<String>();
            multRecipients.add("mario.bacellar@accenture.com");
            multRecipients.add("mario.bacellar@gmail.com");
            subject	= "Subject - Testing - MULT Recipients";
			msg = msg0 + "\n\nTesting MULT recipient.\n\n";
            sendEmail.send(from, multRecipients, subject, msg, Arrays.asList(attachment), Arrays.asList(fileName), Arrays.asList(mimeType));
            log.info("========================================================================");
			log.info("<- SendEmail - MULT destinatário");
			log.info("========================================================================");
	    	

	    	log.info("========================================================================");
			log.info("<- main");
			log.info("========================================================================");

		} catch (Exception e) {
			e.printStackTrace();
			log.error("### ERROR ###",e);
		}
		
    	 
    }


}
