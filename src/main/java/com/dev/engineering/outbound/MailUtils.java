package com.dev.engineering.outbound;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailUtils {

	@Autowired
	private JavaMailSender javaMailSender;

	public void message(MailModel mail) {

		SimpleMailMessage message = new SimpleMailMessage();

		message.setSubject(mail.getSubject());
		message.setText(mail.getContent());
		message.setTo(mail.getTo());
		message.setFrom(mail.getFrom());


		javaMailSender.send(message);
	}
}
