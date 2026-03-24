import java.util.*;

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
}

/* Booking Request Queue */
class BookingRequestQueue {

    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        queue.add(reservation);
        System.out.println("Booking request added for " + reservation.getGuestName());
    }

    public Reservation getNextRequest() {
        return queue.poll();   // FIFO removal
    }

    public boolean hasRequests() {
        return !queue.isEmpty();
    }
}

/* Inventory Service */
class InventoryService {

    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public boolean isAvailable(String roomType) {
        return inventory.getOrDefault(roomType, 0) > 0;
    }

    public void decrement(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void displayInventory() {
        System.out.println("\nInventory Status:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " : " + inventory.get(type));
        }
    }
}

/* Room Allocation Service */
class RoomAllocationService {

    private Set<String> allocatedRooms = new HashSet<>();
    private Map<String, Set<String>> roomTypeMap = new HashMap<>();
    private int counter = 100;

    public String allocateRoom(String roomType) {

        String roomId;

        do {
            roomId = roomType.substring(0, 2).toUpperCase() + counter++;
        } while (allocatedRooms.contains(roomId));

        allocatedRooms.add(roomId);

        roomTypeMap.putIfAbsent(roomType, new HashSet<>());
        roomTypeMap.get(roomType).add(roomId);

        return roomId;
    }

    public void displayAllocatedRooms() {
        System.out.println("\nAllocated Rooms Summary:");
        for (String type : roomTypeMap.keySet()) {
            System.out.println(type + " -> " + roomTypeMap.get(type));
        }
    }
}

/* Main Application */
public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("====================================");
        System.out.println("      Welcome to Book My Stay       ");
        System.out.println("====================================");
        System.out.println("Version: 6.0\n");

        BookingRequestQueue bookingQueue = new BookingRequestQueue();
        InventoryService inventory = new InventoryService();
        RoomAllocationService allocator = new RoomAllocationService();

        // Booking Requests
        bookingQueue.addRequest(new Reservation("Alice", "Single Room"));
        bookingQueue.addRequest(new Reservation("Bob", "Double Room"));
        bookingQueue.addRequest(new Reservation("Charlie", "Suite Room"));
        bookingQueue.addRequest(new Reservation("David", "Single Room"));
        bookingQueue.addRequest(new Reservation("Eva", "Single Room")); // should fail

        System.out.println("\n--- Processing Booking Requests ---");

        while (bookingQueue.hasRequests()) {

            Reservation r = bookingQueue.getNextRequest();

            System.out.println("\nProcessing request for " + r.getGuestName());

            if (inventory.isAvailable(r.getRoomType())) {

                String roomId = allocator.allocateRoom(r.getRoomType());
                inventory.decrement(r.getRoomType());

                System.out.println("Reservation Confirmed");
                System.out.println("Allocated Room ID : " + roomId);

            } else {
                System.out.println("Reservation Failed — No Rooms Available");
            }
        }

        allocator.displayAllocatedRooms();
        inventory.displayInventory();
    }
}