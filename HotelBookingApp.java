import java.util.HashMap;
import java.util.Map;

/**
 * Book My Stay Application
 * Use Case 3: Centralized Room Inventory Management
 *
 * This program demonstrates how a HashMap can be used to manage
 * room availability in a centralized inventory system.
 *
 * Version: 3.1 (Refactored)
 *
 * @author Nithil
 */

/* Room Inventory Class */
class RoomInventory {

    private HashMap<String, Integer> inventory;

    /* Constructor initializes room availability */
    public RoomInventory() {
        inventory = new HashMap<>();

        inventory.put("Single Room", 10);
        inventory.put("Double Room", 5);
        inventory.put("Suite Room", 2);
    }

    /* Get availability for a room type */
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    /* Update availability */
    public void updateAvailability(String roomType, int count) {
        inventory.put(roomType, count);
    }

    /* Display complete inventory */
    public void displayInventory() {
        System.out.println("----- Current Room Inventory -----");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue() + " rooms available");
        }

        System.out.println("----------------------------------");
    }
}

/* Main Application Class */
public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("====================================");
        System.out.println("     Welcome to Book My Stay App    ");
        System.out.println("====================================");
        System.out.println("Version: 3.1\n");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Display inventory
        inventory.displayInventory();

        // Example: update availability
        System.out.println("\nUpdating inventory...");
        inventory.updateAvailability("Single Room", 8);

        // Display updated inventory
        inventory.displayInventory();

        System.out.println("Application terminated successfully.");
    }
}