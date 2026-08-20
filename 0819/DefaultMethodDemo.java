interface AlertChannel {
    boolean send(String receiver, String message);

    default boolean isValid(String receiver, String message) {
        return receiver != null && !receiver.isBlank()
                && message != null && !message.isBlank();
    }

    default String preview(String message) {
        if (message == null) {
            return "";
        }
        if (message.length() > 10) {
            return message.substring(0, 10) + "...";
        }
        return message;
    }
}

class EmailAlert implements AlertChannel {
    @Override
    public boolean send(String receiver, String message) {
        if (!isValid(receiver, message) || !receiver.contains("@")) {
            return false;
        }
        System.out.println("EMAIL to " + receiver + ": " + message + " [Preview: " + preview(message) + "]");
        return true;
    }
}

class ConsoleAlert implements AlertChannel {
    @Override
    public boolean send(String receiver, String message) {
        if (!isValid(receiver, message)) {
            return false;
        }
        System.out.println("CONSOLE " + receiver + ": " + message + " [Preview: " + preview(message) + "]");
        return true;
    }
}

public class DefaultMethodDemo {
    public static void main(String[] args) {
        AlertChannel email = new EmailAlert();
        AlertChannel console = new ConsoleAlert();

        email.send("amy@example.com", "System maintenance starts at midnight");
        console.send("B113", "Hello!");
    }
}