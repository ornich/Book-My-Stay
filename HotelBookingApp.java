import java.io.*;
import java.util.*;

/**
 * Book My Stay Application – Use Case 12: Data Persistence & System Recovery. This program
 * demonstrates how critical booking and inventory state can survive application restarts by
 * using serialization-based file persistence. System state is saved to a file during shutdown
 * and restored during startup through deserialization, ensuring continuity and failure tolerance.
 * The design introduces durable state management, inventory snapshot recovery, and graceful
 * handling of missing or corrupted persistence data to support realistic production behavior.
 * @author Nithil
 * @version 12.0
 */

class Reservation implements Serializable {

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public void display() {
        System.out.println(reservationId + " | " + guestName + " | " + roomType);
    }
}

class SystemState implements Serializable {

    Map<String, Integer> inventory;
    List<Reservation> bookings;

    public SystemState(Map<String, Integer> inventory,
                       List<Reservation> bookings) {
        this.inventory = inventory;
        this.bookings = bookings;
    }
}

class PersistenceService {

    private static final String FILE_NAME = "hotel_state.dat";

    public void save(SystemState state) {

        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(state);
            System.out.println("\nSystem State Saved Successfully.");

        } catch (IOException e) {
            System.out.println("Error saving state: " + e.getMessage());
        }
    }

    public SystemState load() {

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            System.out.println("Persisted State Loaded.");
            return (SystemState) ois.readObject();

        } catch (FileNotFoundException e) {

            System.out.println("No persistence file found. Starting fresh.");
            return null;

        } catch (Exception e) {

            System.out.println("Corrupted persistence file. Starting fresh.");
            return null;
        }
    }
}

public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("====================================");
        System.out.println(" Data Persistence & Recovery Demo ");
        System.out.println("====================================\n");

        PersistenceService persistence = new PersistenceService();

        SystemState state = persistence.load();

        Map<String, Integer> inventory;
        List<Reservation> bookings;

        if (state == null) {

            inventory = new HashMap<>();
            inventory.put("Single Room", 2);
            inventory.put("Double Room", 1);
            inventory.put("Suite Room", 1);

            bookings = new ArrayList<>();

            System.out.println("Initialized New System State.");

        } else {

            inventory = state.inventory;
            bookings = state.bookings;

            System.out.println("Recovered Previous System State.");
        }

        // Simulate new bookings
        Reservation r1 = new Reservation("RES401", "Alice", "Single Room");
        Reservation r2 = new Reservation("RES402", "Bob", "Double Room");

        bookings.add(r1);
        bookings.add(r2);

        inventory.put("Single Room", inventory.get("Single Room") - 1);
        inventory.put("Double Room", inventory.get("Double Room") - 1);

        System.out.println("\nCurrent Bookings:");
        for (Reservation r : bookings) {
            r.display();
        }

        System.out.println("\nCurrent Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " : " + inventory.get(type));
        }

        // Save state before shutdown
        persistence.save(new SystemState(inventory, bookings));

        System.out.println("\nSystem can restart with recovered state.");
    }
}