import java.util.*;

/**
 * Book My Stay Application – Use Case 9: Error Handling & Validation. This program strengthens
 * system reliability by introducing structured input validation and domain-specific error handling.
 * Booking inputs such as guest name, room type, and inventory constraints are validated before
 * processing to prevent inconsistent system states. Custom exceptions enable fail-fast detection
 * of invalid booking scenarios, while graceful handling ensures meaningful error messages are
 * displayed and the application continues running safely without data corruption.
 * @author Nithil
 * @version 9.0
 */

class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

class InventoryService {

    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public void validateRoomType(String roomType) throws InvalidBookingException {
        if (!inventory.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid Room Type: " + roomType);
        }
    }

    public void validateAvailability(String roomType) throws InvalidBookingException {
        if (inventory.get(roomType) <= 0) {
            throw new InvalidBookingException("No rooms available for " + roomType);
        }
    }

    public void allocateRoom(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " : " + inventory.get(type));
        }
    }
}

class BookingValidator {

    public void validateReservation(Reservation r, InventoryService inventory)
            throws InvalidBookingException {

        if (r.getGuestName() == null || r.getGuestName().trim().isEmpty()) {
            throw new InvalidBookingException("Guest Name cannot be empty");
        }

        inventory.validateRoomType(r.getRoomType());
        inventory.validateAvailability(r.getRoomType());
    }
}

public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("====================================");
        System.out.println("      Welcome to Book My Stay       ");
        System.out.println("====================================");
        System.out.println("Version: 9.0\n");

        InventoryService inventory = new InventoryService();
        BookingValidator validator = new BookingValidator();

        List<Reservation> requests = new ArrayList<>();

        requests.add(new Reservation("Alice", "Single Room"));
        requests.add(new Reservation("", "Double Room"));           // Invalid Name
        requests.add(new Reservation("Charlie", "Luxury Room"));     // Invalid Room Type
        requests.add(new Reservation("David", "Suite Room"));
        requests.add(new Reservation("Eva", "Suite Room"));          // No Availability

        for (Reservation r : requests) {

            System.out.println("\nProcessing booking for: " + r.getGuestName());

            try {

                validator.validateReservation(r, inventory);
                inventory.allocateRoom(r.getRoomType());

                System.out.println("Booking Confirmed for " + r.getGuestName());

            } catch (InvalidBookingException e) {

                System.out.println("Booking Failed → " + e.getMessage());

            }
        }

        inventory.displayInventory();

        System.out.println("\nSystem continues running safely after handling errors.");
    }
}