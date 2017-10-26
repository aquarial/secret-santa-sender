import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.config.TransportStrategy;

/**
 * Demo app that shows how to construct and send an RFC822
 * (singlepart) message.
 * <p>
 * XXX - allow more than one recipient on the command line
 *
 * @author Max Spivak
 * @author Bill Shannon
 */

public class Sender {
    private String from;
    private String from_name;
    private Mailer mailer;


    Sender(String username, String password) {
        this.from = username + "@gmail.com";
        from_name = "Santa";
        mailer = new Mailer("smtp.gmail.com", 465, username, password, TransportStrategy.SMTP_SSL);
    }


    public void sendEmail(String to, String gift_target_name) {
        String to_name = to.split("@")[0];

        String text = "Hi!\n\n" + from_name + " has signed you up for a Secret Santa Gift exchange. " +
                "You will be bringing a gift for... " + gift_target_name + ". ";
        String subject = "Testing";

        Email mail = new EmailBuilder()
                .from(from_name, from)
                .to(to_name, to)
                .subject(subject)
                .text(text)
                .build();
        mailer.sendMail(mail);
    }

}
