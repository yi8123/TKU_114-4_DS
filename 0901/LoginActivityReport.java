import java.util.*;

public class LoginActivityReport {
    public static class LogEntry {
        String account;
        String ip;

        public LogEntry(String account, String ip) {
            this.account = account;
            this.ip = ip;
        }
    }

    public static void analyzeLogins(List<LogEntry> logs, int suspiciousLoginThreshold, int suspiciousIpThreshold) {
        Map<String, Integer> loginCounts = new HashMap<>();
        Map<String, Set<String>> distinctIps = new HashMap<>();

        for (LogEntry log : logs) {
            loginCounts.put(log.account, loginCounts.getOrDefault(log.account, 0) + 1);
            distinctIps.computeIfAbsent(log.account, k -> new HashSet<>()).add(log.ip);
        }

        List<String> sortedAccounts = new ArrayList<>(loginCounts.keySet());
        Collections.sort(sortedAccounts);

        System.out.printf("%-15s %-12s %-12s %-10s%n", "Account", "Logins", "Unique IPs", "Status");
        System.out.println("---------------------------------------------------------");

        for (String account : sortedAccounts) {
            int count = loginCounts.get(account);
            int ips = distinctIps.get(account).size();
            boolean isSuspicious = (count >= suspiciousLoginThreshold) || (ips >= suspiciousIpThreshold);

            System.out.printf("%-15s %-12d %-12d %-10s%n",
                    account,
                    count,
                    ips,
                    isSuspicious ? "SUSPICIOUS" : "NORMAL");
        }
    }
}