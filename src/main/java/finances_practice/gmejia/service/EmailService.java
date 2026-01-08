package finances_practice.gmejia.service;

public interface EmailService {
    void sendVerificationEmail(String toEmailSend, String code);
}
