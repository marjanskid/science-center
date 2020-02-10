package root.sciencecenter.services;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import root.sciencecenter.entities.User;
import root.sciencecenter.repositories.UserRepository;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class AuthorCorrectionTimeoutMail implements JavaDelegate {

    @Autowired
    IdentityService identityService;

    @Autowired
    UserRepository userRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("------------------------------------------");
        System.out.println("AuthorCorrectionTimeoutMail");

        String processUser = (String) delegateExecution.getVariable("processUser");

        System.out.println(processUser);
        System.out.println("------------------------------------------");

        User foundProcessUser = userRepository.findByUsername(processUser);
        if (foundProcessUser != null) {
            String processUserEmail = foundProcessUser.getEmail();

            String from = "marjanskid6196@gmail.com";
            String password = "delijesever89";
            String emailSubject = "New article correction timeout";
            String emailText = String.format("New article that has been posted form author '%s'" +
                    " has to be corrected, but the time for correction has expired.", processUser);

            sendEmail(from, password, processUserEmail, emailSubject, emailText);
        }
    }

    public void sendEmail(String from, String password, String to, String emailSubject, String emailText){
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from,password);
                    }
                });

        try {
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(emailSubject);
            message.setText(emailText);

            Transport.send(message);
            System.out.println("Email sent successfully");
        } catch (MessagingException e) {
            System.out.println("Email hasn't sent successfully");
            throw new RuntimeException(e);
        }
    }
}
