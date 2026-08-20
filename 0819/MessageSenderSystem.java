interface MessageSender {
    boolean send(String receiver, String message);

    default boolean isValid(String receiver, String message) {
        return receiver != null && !receiver.isBlank() && message != null && !message.isBlank();
    }
}

class EmailSender implements MessageSender {
    @Override
    public boolean send(String receiver, String message) {
        if (!isValid(receiver, message) || !receiver.contains("@")) {
            return false;
        }
        System.out.println("[EMAIL] To: " + receiver + " | Content: " + message);
        return true;
    }
}

class SmsSender implements MessageSender {
    @Override
    public boolean send(String receiver, String message) {
        if (!isValid(receiver, message) || !receiver.matches("^09\\d{8}$")) {
            return false;
        }
        System.out.println("[SMS] To: " + receiver + " | Content: " + message);
        return true;
    }
}

class ConsoleSender implements MessageSender {
    @Override
    public boolean send(String receiver, String message) {
        if (!isValid(receiver, message)) {
            return false;
        }
        System.out.println("[CONSOLE] Terminal Target: " + receiver + " -> " + message);
        return true;
    }
}

public class MessageSenderSystem {
    public static boolean notifyUser(MessageSender sender, String receiver, String message) {
        if (sender == null) {
            return false;
        }
        return sender.send(receiver, message);
    }

    public static void main(String[] args) {
        MessageSender email = new EmailSender();
        MessageSender sms = new SmsSender();
        MessageSender console = new ConsoleSender();

        notifyUser(email, "user@test.com", "Your verification code is 1234");
        notifyUser(sms, "0912345678", "Package arriving today");
        notifyUser(console, "STDOUT", "Server heartbeat OK");

        System.out.println("Invalid Email result: " + notifyUser(email, "invalid-email", "Hello"));
        System.out.println("Blank Message result: " + notifyUser(sms, "0912345678", "   "));
    }
}