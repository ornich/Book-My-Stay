import java.util.*;

/**
 * Book My Stay Application – Use Case 8: Booking History & Reporting. This program introduces
 * historical tracking of confirmed reservations to provide operational visibility and support
 * administrative reporting. Confirmed bookings are stored in insertion order using a List data
 * structure, creating a chronological audit trail of system activity. A separate reporting service
 * retrieves stored reservation data to generate summaries without modifying original records,
 * demonstrating separation of concerns and a persistence-oriented design mindset.
 * @author Nithil
 * @version 8.0
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

    public void display() {
        System.out.println("Reservation ID : " + reservationId);
        System.out.println("Guest Name     : " + guestName);
        System.out.println("Room Type      : " + roomType);
        System.out.println("-----------------------------");
    }
}

class BookingHistory {

    private List<Reservation> history = new ArrayList<>();

    public void addReservation(Reservation r) {
        history.add(r);
        System.out.println("Reservation stored in history → " + r.getReservationId());
    }

    public List<Reservation> getHistory() {
        return history;
    }
}

class BookingReportService {

    public void showAllBookings(List<Reservation> list) {

        System.out.println("\n===== Booking History Report =====");

        if (list.isEmpty()) {
            System.out.println("No bookings available.");
            return;
        }

        for (Reservation r : list) {
            r.display();
        }
    }

    public void showSummary(List<Reservation> list) {

        System.out.println("\n===== Booking Summary =====");
        System.out.println("Total Confirmed Bookings : " + list.size());

        Map<String, Integer> roomCount = new HashMap<>();

        for (Reservation r : list) {
            roomCount.put(r.getRoomType(),
                    roomCount.getOrDefault(r.getRoomType(), 0) + 1);
        }

        for (String type : roomCount.keySet()) {
            System.out.println(type + " Bookings : " + roomCount.get(type));
        }
    }
}

public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("====================================");
        System.out.println("      Welcome to Book My Stay       ");
        System.out.println("====================================");
        System.out.println("Version: 8.0\n");

        BookingHistory history = new BookingHistory();
        BookingReportService report = new BookingReportService();

        history.addReservation(new Reservation("RES201", "Alice", "Single Room"));
        history.addReservation(new Reservation("RES202", "Bob", "Double Room"));
        history.addReservation(new Reservation("RES203", "Charlie", "Suite Room"));
        history.addReservation(new Reservation("RES204", "David", "Single Room"));

        report.showAllBookings(history.getHistory());
        report.showSummary(history.getHistory());
    }
}