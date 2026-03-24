import java.util.*;

/**
 * Book My Stay Application
 * Use Case 7: Add-On Service Selection
 *
 * This program extends the Hotel Booking Management System by introducing
 * optional add-on services that can be attached to already confirmed
 * reservations. The objective of this use case is to demonstrate how
 * real-world business features can be added to an existing system without
 * modifying or disturbing the core booking workflow such as request
 * processing, room allocation, or inventory synchronization.
 *
 * In practical hotel systems, guests often enhance their stay by selecting
 * additional services like breakfast, airport pickup, spa access, or extra
 * bedding. These services must be handled independently from the main
 * reservation logic to maintain system stability and scalability.
 *
 * The design uses a one-to-many relationship model where a single reservation
 * can have multiple associated services. This relationship is implemented
 * using a Map that links a reservation ID to a List of selected services.
 * Lists preserve insertion order and allow multiple service selections,
 * while the Map enables efficient lookup and management.
 *
 * This approach follows the principle of composition over inheritance,
 * ensuring flexible feature expansion without rigid class dependencies.
 * Cost aggregation for add-on services is performed separately, allowing
 * modular pricing calculations and simplified future enhancements.
 *
 * Core booking confirmation and inventory state remain unchanged,
 * illustrating clean separation of optional features from critical
 * reservation operations.
 *
 * @author Nithil
 * @version 7.0
 */

class Reservation {

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

class AddOnService {

    private String serviceName;
    private double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }
}

class AddOnServiceManager {

    private Map<String, List<AddOnService>> serviceMap = new HashMap<>();

    public void addService(String reservationId, AddOnService service) {

        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);

        System.out.println(service.getServiceName() +
                " added to Reservation " + reservationId);
    }

    public double calculateTotalCost(String reservationId) {

        double total = 0;

        List<AddOnService> services = serviceMap.get(reservationId);

        if (services != null) {
            for (AddOnService s : services) {
                total += s.getCost();
            }
        }

        return total;
    }

    public void displayServices(String reservationId) {

        System.out.println("\nServices for Reservation " + reservationId);

        List<AddOnService> services = serviceMap.get(reservationId);

        if (services == null) {
            System.out.println("No Add-On Services Selected");
            return;
        }

        for (AddOnService s : services) {
            System.out.println(s.getServiceName() + " : ₹" + s.getCost());
        }

        System.out.println("Total Add-On Cost : ₹" +
                calculateTotalCost(reservationId));
    }
}

public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("====================================");
        System.out.println("      Welcome to Book My Stay       ");
        System.out.println("====================================");
        System.out.println("Version: 7.0\n");

        // Simulated confirmed reservations
        Reservation r1 = new Reservation("RES101", "Alice", "Single Room");
        Reservation r2 = new Reservation("RES102", "Bob", "Double Room");

        AddOnServiceManager manager = new AddOnServiceManager();

        // Guest selecting services
        manager.addService("RES101", new AddOnService("Breakfast", 250));
        manager.addService("RES101", new AddOnService("Airport Pickup", 800));
        manager.addService("RES101", new AddOnService("Spa Access", 1200));
        manager.addService("RES102", new AddOnService("Extra Bed", 500));

        // Display services
        manager.displayServices("RES101");
        manager.displayServices("RES102");

        System.out.println("\nCore booking and inventory state remain unchanged.");
    }
}