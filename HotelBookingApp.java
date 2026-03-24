/**
 * Book My Stay Application
 * Use Case 2: Basic Room Types & Static Availability
 *
 * This program demonstrates object-oriented modeling
 * using abstraction and inheritance for different room types.
 * Room availability is represented using simple variables.
 *
 * Version: 2.1 (Refactored Version)
 *
 * @author Nithil
 */

abstract class Room {

    protected String roomType;
    protected int beds;
    protected double price;
    protected int size;

    public Room(String roomType, int beds, double price, int size) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
        this.size = size;
    }

    public void displayRoomDetails() {
        System.out.println("Room Type : " + roomType);
        System.out.println("Beds      : " + beds);
        System.out.println("Size      : " + size + " sq.ft");
        System.out.println("Price     : $" + price);
    }
}

/* Single Room */
class SingleRoom extends Room {

    public SingleRoom() {
        super("Single Room", 1, 100.0, 200);
    }
}

/* Double Room */
class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double Room", 2, 180.0, 350);
    }
}

/* Suite Room */
class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite Room", 3, 350.0, 600);
    }
}

/* Main Application Class */
public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("====================================");
        System.out.println("     Welcome to Book My Stay App    ");
        System.out.println("====================================");
        System.out.println("Version: 2.1\n");

        // Creating room objects (Polymorphism)
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        // Static availability variables
        int singleRoomAvailability = 10;
        int doubleRoomAvailability = 5;
        int suiteRoomAvailability = 2;

        // Display room details and availability
        System.out.println("----- Room Information -----");

        singleRoom.displayRoomDetails();
        System.out.println("Available : " + singleRoomAvailability);
        System.out.println("-----------------------------");

        doubleRoom.displayRoomDetails();
        System.out.println("Available : " + doubleRoomAvailability);
        System.out.println("-----------------------------");

        suiteRoom.displayRoomDetails();
        System.out.println("Available : " + suiteRoomAvailability);
        System.out.println("-----------------------------");

        System.out.println("Application terminated successfully.");
    }
}