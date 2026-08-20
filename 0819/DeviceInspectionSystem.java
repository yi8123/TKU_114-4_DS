abstract class Device {
    private final String deviceName;

    Device(String deviceName) {
        this.deviceName = (deviceName == null || deviceName.isBlank()) ? "Generic Device" : deviceName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    abstract void runDiagnostic();
}

class Laptop extends Device {
    Laptop(String deviceName) {
        super(deviceName);
    }

    @Override
    void runDiagnostic() {
        System.out.println("[Laptop: " + getDeviceName() + "] Checking CPU temperature and battery health... OK.");
    }
}

class Printer extends Device {
    Printer(String deviceName) {
        super(deviceName);
    }

    @Override
    void runDiagnostic() {
        System.out.println("[Printer: " + getDeviceName() + "] Checking paper tray and ink levels... OK.");
    }

    public void cleanPrintHead() {
        System.out.println("[Printer: " + getDeviceName() + "] -> Cleaning print head nozzles... Done.");
    }
}

class Router extends Device {
    Router(String deviceName) {
        super(deviceName);
    }

    @Override
    void runDiagnostic() {
        System.out.println("[Router: " + getDeviceName() + "] Checking WAN/LAN link status and packet loss... OK.");
    }
}

public class DeviceInspectionSystem {
    public static void main(String[] args) {
        Device[] devices = {
            new Laptop("MacBook Pro"),
            new Printer("Epson L3210"),
            new Router("ASUS RT-AX86U"),
            new Printer("HP LaserJet Pro")
        };

        for (Device device : devices) {
            device.runDiagnostic();

            if (device instanceof Printer printer) {
                printer.cleanPrintHead();
            }
        }
    }
}