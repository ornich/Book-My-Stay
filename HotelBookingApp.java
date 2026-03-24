import java.util.*;

/**
 * Book My Stay Application – Use Case 11: Concurrent Booking Simulation (Thread Safety). This
 * program demonstrates how multiple booking requests occurring simultaneously can lead to race
 * conditions when shared resources such as booking queues and inventory are accessed without
 * control. A synchronized booking processor ensures that critical sections involving request
 * retrieval, room allocation, and inventory updates are executed safely by one thread at a time.
 * This simulation highlights the importance of thread safety in maintaining consistent system
 * state and preventing double allocation in multi-user environments.
 * @author Nithil
 * @version 11.0
 */

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

class BookingQueue {

    private Queue<Reservation> queue = new LinkedList<>();

    public synchronized void addRequest(Reservation r) {
        queue.add(r);
        System.out.println(Thread.currentThread().getName()
                + " added booking for " + r.getGuestName());
    }

    public synchronized Reservation getRequest() {
        return queue.poll();
    }

    public synchronized boolean hasRequests() {
        return !queue.isEmpty();
    }
}

class InventoryService {

    private Map<String, Integer> inventory = new HashMap<>();
    private int roomCounter = 100;

    public InventoryService() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public synchronized void allocateRoom(Reservation r) {

        String type = r.getRoomType();

        if (inventory.getOrDefault(type, 0) <= 0) {
            System.out.println(Thread.currentThread().getName()
                    + " → No availability for " + r.getGuestName());
            return;
        }

        inventory.put(type, inventory.get(type) - 1);
        String roomId = type.substring(0, 2).toUpperCase() + roomCounter++;

        System.out.println(Thread.currentThread().getName()
                + " → Booking Confirmed for " + r.getGuestName()
                + " | Room ID: " + roomId);
    }

    public void showInventory() {
        System.out.println("\nFinal Inventory State:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " : " + inventory.get(type));
        }
    }
}

class BookingProcessor extends Thread {

    private BookingQueue queue;
    private InventoryService inventory;

    public BookingProcessor(String name,
                            BookingQueue queue,
                            InventoryService inventory) {
        super(name);
        this.queue = queue;
        this.inventory = inventory;
    }

    public void run() {

        while (true) {

            Reservation r;

            synchronized (queue) {
                if (!queue.hasRequests())
                    break;
                r = queue.getRequest();
            }

            if (r != null) {
                inventory.allocateRoom(r);
            }

            try {
                Thread.sleep(200); // simulate processing delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("====================================");
        System.out.println(" Concurrent Booking Simulation ");
        System.out.println("====================================\n");

        BookingQueue queue = new BookingQueue();
        InventoryService inventory = new InventoryService();

        // Simulating concurrent guest requests
        queue.addRequest(new Reservation("Alice", "Single Room"));
        queue.addRequest(new Reservation("Bob", "Double Room"));
        queue.addRequest(new Reservation("Charlie", "Suite Room"));
        queue.addRequest(new Reservation("David", "Single Room"));
        queue.addRequest(new Reservation("Eva", "Single Room"));

        // Multiple booking processors (threads)
        BookingProcessor t1 =
                new BookingProcessor("Processor-1", queue, inventory);
        BookingProcessor t2 =
                new BookingProcessor("Processor-2", queue, inventory);
        BookingProcessor t3 =
                new BookingProcessor("Processor-3", queue, inventory);

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        inventory.showInventory();

        System.out.println("\nSystem maintained consistent state under concurrency.");
    }
}