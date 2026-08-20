abstract class Transport {
    private final String routeName;

    Transport(String routeName) {
        this.routeName = (routeName == null || routeName.isBlank()) ? "Unknown Route" : routeName;
    }

    public String getRouteName() {
        return routeName;
    }

    abstract int calculateFare(int distance);
}

class Bus extends Transport {
    Bus(String routeName) {
        super(routeName);
    }

    @Override
    int calculateFare(int distance) {
        int validDistance = Math.max(0, distance);
        return 15 + (validDistance / 10) * 15;
    }
}

class Taxi extends Transport {
    Taxi(String routeName) {
        super(routeName);
    }

    @Override
    int calculateFare(int distance) {
        int validDistance = Math.max(0, distance);
        return 85 + validDistance * 20;
    }
}

public class TransportFareSystem {
    public static void main(String[] args) {
        Transport[] transports = {
            new Bus("Bus 307 (Taipei - Banqiao)"),
            new Bus("Bus 299 (Xinzhuang - Taipei)"),
            new Taxi("Taxi City Ride A"),
            new Taxi("Taxi Airport Express B")
        };

        int testDistance = 12;

        for (Transport transport : transports) {
            System.out.printf("[%s] Distance: %d km -> Fare: $%d%n",
                    transport.getRouteName(), testDistance, transport.calculateFare(testDistance));
        }
    }
}