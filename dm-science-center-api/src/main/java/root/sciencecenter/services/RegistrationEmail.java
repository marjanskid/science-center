package root.sciencecenter.services;

import com.google.common.hash.Hashing;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import root.sciencecenter.constants.ApiConstants;
import root.sciencecenter.dtos.FormSubmissionDto;
import root.sciencecenter.entities.RegistrationHashCode;
import root.sciencecenter.repositories.RegistrationHashCodeRepository;
import root.sciencecenter.repositories.UserRepository;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;

@Service
public class RegistrationEmail implements JavaDelegate {

    @Autowired
    IdentityService identityService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RegistrationHashCodeRepository registrationHashCodeRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<FormSubmissionDto> registration = (List<FormSubmissionDto>) delegateExecution.getVariable("Process_17a6cmn");

        String username = registration.get(7).getFieldValue();
        String emailAddress = registration.get(5).getFieldValue();

        String hashCode = Hashing.sha256().hashString(username, StandardCharsets.UTF_8).toString();
        System.out.println("admin hashed: " + Hashing.sha256().hashString("admin", StandardCharsets.UTF_8).toString());
        registrationHashCodeRepository.save(new RegistrationHashCode(hashCode, username));

        String from = "marjanskid6196@gmail.com";
        String password = "delijesever89";
        String emailSubject = "Account activation";
        String emailText = "To activate account click on this link: " +
                "\n " + ApiConstants.API_BASE_ADDRESS + "/registration/activateUserAccount/" + username  + "/" + hashCode;

        // sendEmail(from, password, emailAddress, emailSubject, emailText);
    }

    public void sendEmail(String from, String password, String to, String emailSubject, String emailText){
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
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
