import java.util.LinkedList;
import java.util.Queue;

/**
 * Book My Stay Application
 * Use Case 5: Booking Request (First-Come-First-Served)
 *
 * Demonstrates how booking requests are stored in a queue
 * to preserve arrival order using FIFO principle.
 *
 * Version: 5.1 (Refactored)
 *
 * @author Nithil
 */

/* Reservation Class */
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

    public void displayReservation() {
        System.out.println("Guest Name : " + guestName);
        System.out.println("Room Type  : " + roomType);
        System.out.println("----------------------------");
    }
}

/* Booking Request Queue */
class BookingRequestQueue {

    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    /* Add booking request */
    public void addRequest(Reservation reservation) {
        queue.add(reservation);
        System.out.println("Booking request added for " + reservation.getGuestName());
    }

    /* Display all queued requests */
    public void displayQueue() {

        System.out.println("\n----- Booking Request Queue -----");

        for (Reservation r : queue) {
            r.displayReservation();
        }

        System.out.println("Total Requests in Queue: " + queue.size());
    }
}

/* Main Application */
public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("====================================");
        System.out.println("      Welcome to Book My Stay       ");
        System.out.println("====================================");
        System.out.println("Version: 5.1\n");

        // Initialize booking queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Guests submitting booking requests
        Reservation r1 = new Reservation("Alice", "Single Room");
        Reservation r2 = new Reservation("Bob", "Double Room");
        Reservation r3 = new Reservation("Charlie", "Suite Room");

        // Add requests to queue
        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        // Display queued requests
        bookingQueue.displayQueue();

        System.out.println("\nRequests stored in FIFO order.");
        System.out.println("No room allocation performed yet.");
    }
}