package br.com.mcb.sandgrid;

//using SendGrid's Java Library
//https://github.com/sendgrid/sendgrid-java
import com.sendgrid.*;
import java.io.IOException;

public class Example {
	
	public static void main(String[] args) throws IOException {
	
		Email	from	= new Email("test@example.com");
		String	subject = "xSending with SendGrid is Fun";
		Email	to		= new Email("mario.bacellar@gmail.com");
		Content content = new Content("text/plain", "and easy to do anywhere, even with Java");
		Mail	mail 	= new Mail(from, subject, to, content);

		SendGrid sg		= new SendGrid("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");
		Request request = new Request();
		try {
			
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			
			Response response = sg.api(request);
			
			System.out.println(response.getStatusCode());
			System.out.println(response.getBody());
			System.out.println(response.getHeaders());
			
		} catch (IOException ex) {
			throw ex;
		}
	}
}