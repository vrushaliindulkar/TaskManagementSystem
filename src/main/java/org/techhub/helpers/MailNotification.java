package org.techhub.helpers;

import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.techhub.model.Task;

public class MailNotification {

    public void sendTeamTaskEmails(Task task, List<String> teamEmails) {
        String from = "vrushali301998@gmail.com"; // Sender's email
        String host = "smtp.gmail.com"; // Gmail SMTP server

        // Setup mail server properties
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Get session
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // Use your app password here (if 2FA is enabled)
                return new PasswordAuthentication("vrushali301998@gmail.com", "xebh kxpa umzz czge");
            }
        });
        
        try {
        	String Priority="";
            if(task.getPriority().equals("1")) {
            	Priority="High";
            }
            else  if(task.getPriority().equals("2")) {
            	Priority="Medium";
            }
            else  if(task.getPriority().equals("3")) {
            	Priority="Low";
            }
            // Send emails to each recipient
            for (String to : teamEmails) {
                MimeMessage message = new MimeMessage(session);

                // Set From: header field
                message.setFrom(new InternetAddress(from));

                // Set To: header field
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

                // Set Subject: header field
                message.setSubject("New Team Task Assigned");
                
                // Set the actual message
                String body = "Dear Team Member,\n\n" +
                              "A new team task has been assigned:\n\n" +
                              "Task Name: " + task.getTaskName() + "\n" +
                              "Deadline: " + task.getDeadLine() + "\n" +
                              "Priority: " + Priority + "\n\n" +
                              "Please log in to the system to view and manage the task.\n\n" +
                              "Best Regards,\nTeam Management System";
                message.setText(body);

                // Send message
                Transport.send(message);
                System.out.println("Email sent successfully to: " + to);
            }
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
