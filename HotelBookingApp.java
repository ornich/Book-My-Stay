/**
 * Book My Stay Application
 * Use Case 1: Application Entry & Welcome Message
 *
 * This class represents the entry point of the Hotel Booking
 * Management System. When the program starts, it prints
 * a welcome message along with the application name
 * and version information.
 *
 * @author Nithil
 * @version 1.0
 */

public class HotelBookingApp {

    /**
     * Main method - Entry point of the Java application.
     * The JVM starts execution from this method.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {

        // Display welcome message
        System.out.println("====================================");
        System.out.println("     Welcome to Book My Stay App    ");
        System.out.println("====================================");

        // Display application details
        System.out.println("Application Name : Hotel Booking Management System");
        System.out.println("Version          : v1.0");

        // Closing message
        System.out.println("------------------------------------");
        System.out.println("Application started successfully!");
        System.out.println("------------------------------------");
    }
}