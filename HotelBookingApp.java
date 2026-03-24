import java.util.*;

/**
 * Book My Stay Application – Use Case 10: Booking Cancellation & Inventory Rollback. This program
 * enables safe cancellation of confirmed bookings by reversing previously completed state changes.
 * A Stack data structure is used to track recently allocated room IDs and perform LIFO rollback
 * operations. The system validates cancellation requests, restores inventory counts immediately,
 * updates booking records, and prevents duplicate or invalid cancellations, thereby maintaining
 * consistent and predictable system behavior across the booking lifecycle.
 * @author Nithil
 * @version 10.0
 */

class Reservation {

    private String reservationId;
    private String roomType;
    private String roomId;
    private boolean cancelled = false;

    public Reservation(String reservationId, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void cancel() {
        cancelled = true;
    }
}

class InventoryService {

    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("Single Room", 1);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public void increment(String roomType) {
        inventory.put(roomType, inventory.get(roomType) + 1);
    }

    public void display() {
        System.out.println("\nCurrent Inventory State:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " : " + inventory.get(type));
        }
    }
}

class BookingHistory {

    private Map<String, Reservation> history = new HashMap<>();

    public void add(Reservation r) {
        history.put(r.getReservationId(), r);
    }

    public Reservation get(String id) {
        return history.get(id);
    }

    public void showStatus() {
        System.out.println("\nBooking Status Report:");
        for (Reservation r : history.values()) {
            System.out.println(r.getReservationId() +
                    " → " + (r.isCancelled() ? "Cancelled" : "Active"));
        }
    }
}

class CancellationService {

    private Stack<String> rollbackStack = new Stack<>();

    public void cancelBooking(String reservationId,
                              BookingHistory history,
                              InventoryService inventory) {

        Reservation r = history.get(reservationId);

        if (r == null) {
            System.out.println("Cancellation Failed → Reservation does not exist");
            return;
        }

        if (r.isCancelled()) {
            System.out.println("Cancellation Failed → Already Cancelled");
            return;
        }

        rollbackStack.push(r.getRoomId());
        inventory.increment(r.getRoomType());
        r.cancel();

        System.out.println("Cancellation Successful → Room Released: "
                + rollbackStack.peek());
    }

    public void showRollbackStack() {
        System.out.println("\nRollback Stack (Recent Releases): " + rollbackStack);
    }
}

public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("====================================");
        System.out.println("      Welcome to Book My Stay       ");
        System.out.println("====================================");
        System.out.println("Version: 10.0\n");

        InventoryService inventory = new InventoryService();
        BookingHistory history = new BookingHistory();
        CancellationService cancelService = new CancellationService();

        // Simulated confirmed bookings
        history.add(new Reservation("RES301", "Single Room", "SR101"));
        history.add(new Reservation("RES302", "Double Room", "DR201"));
        history.add(new Reservation("RES303", "Suite Room", "SU301"));

        // Cancellation requests
        cancelService.cancelBooking("RES302", history, inventory);
        cancelService.cancelBooking("RES999", history, inventory); // Invalid
        cancelService.cancelBooking("RES302", history, inventory); // Duplicate
        cancelService.cancelBooking("RES301", history, inventory);

        cancelService.showRollbackStack();
        history.showStatus();
        inventory.display();
    }
}