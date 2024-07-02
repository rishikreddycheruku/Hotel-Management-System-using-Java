package HotelManagementSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class HotelManagementSystem {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Welcome to the Hotel Management System");

            Hotel hotel = new Hotel();

            while (true) {
                System.out.println("\n1. Display Available Rooms");
                System.out.println("2. Check-In");
                System.out.println("3. Check-Out by Registration ID");
                System.out.println("4. Display Registrations");
                System.out.println("5. Display Booked Rooms");
                System.out.println("6. Exit");
                System.out.print("\nEnter your choice: ");

                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        hotel.displayAvailableRooms();
                        break;
                    case 2:
                        hotel.checkIn();
                        break;
                    case 3:
                        hotel.checkOutByRegistrationId();
                        break;
                    case 4:
                        System.out.println("\n1. Display All Registrations");
                        System.out.println("2. Display Active Registrations");
                        System.out.println("3. Display Checked-Out Registrations");
                        System.out.print("\nEnter your choice: ");
                        int c = scanner.nextInt();
                        switch (c){
                            case 1:
                                hotel.displayAllRegistrations();
                                break;
                            case 2:
                                hotel.displayRegistrationsWithoutCheckOut();
                                break;
                            case 3:
                                hotel.displayCheckedOutRegistrations();
                                break;
                            default:
                                System.out.println("Invalid choice. Please enter a valid option.");
                                break;
                        }
                        break;
                    case 5:
                        hotel.displayBookedRooms();
                        break;
                    case 6:
                        System.out.println("Exiting the Hotel Management System. Goodbye!");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                        break;
                }
            }
        }
    }
}

// Guest class definition
class Guest {
    private String name;
    private long phoneNumber;
    private String checkInDate;
    private int registrationId;
    private int roomNumber;

    public Guest(String name, long phoneNumber, String checkInDate, int registrationId, int roomNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.checkInDate = checkInDate;
        this.registrationId = registrationId;
        this.roomNumber = roomNumber;
    }

    public String getName() {
        return name;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public int getRegistrationId() {
        return registrationId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    @Override
    public String toString() {
        return "\nRegistration ID: " + registrationId + "\nGuest: " + name + "\nPhone: " + phoneNumber + "\nCheck-In Date: " + checkInDate +
                "\nRoom Number: " + roomNumber;
    }
}

// Class for processing check-out
class CheckOut {
    public static void processCheckOut(Hotel hotel, int registrationId, String checkOutDate) {
        List<Guest> guests = hotel.getGuests();
        List<Room> rooms = hotel.getRooms();
        List<Guest> checkedOutGuests = hotel.getCheckedOutGuests();

        Guest checkedInGuest = null;
        Room reservedRoom = null;

        for (Guest guest : guests) {
            if (guest.getRegistrationId() == registrationId) {
                checkedInGuest = guest;
                break;
            }
        }

        for (Room room : rooms) {
            if (hotel.isGuestInRoom(room, registrationId)) {
                reservedRoom = room;
                break;
            }
        }

        if (checkedInGuest != null && reservedRoom != null) {
            double roomRate = reservedRoom.getRoomRate();
            int numDays = hotel.calculateNumDays(checkedInGuest.getCheckInDate(), checkOutDate);

            double totalBill = roomRate * numDays;

            reservedRoom.vacateRoom();
            reservedRoom.setCheckOutDate(checkOutDate);

            System.out.println("Check-Out Details for Registration ID: " + checkedInGuest.getRegistrationId());
            System.out.println("Guest: " + checkedInGuest.getName());
            System.out.println("Phone: " + checkedInGuest.getPhoneNumber());
            System.out.println("Check-In Date: " + checkedInGuest.getCheckInDate());
            System.out.println("Check-Out Date: " + checkOutDate);
            System.out.println("Room Number: " + reservedRoom.getRoomNumber());
            System.out.println("Total Bill: Rs." + totalBill);

            guests.remove(checkedInGuest);
            checkedOutGuests.add(checkedInGuest);
        } else {
            System.out.println("Error: Guest or Room not found for Registration ID " + registrationId);
        }
    }
}

// Interface for CheckInOut
interface CheckInOut {
    void checkIn();
    void checkOutByRegistrationId();
}

// Abstract class for Room
abstract class Room {
    private int roomNumber;
    private boolean isOccupied;
    private double roomRate;
    private String checkOutDate;

    public Room(int roomNumber, double roomRate) {
        this.roomNumber = roomNumber;
        this.isOccupied = false;
        this.roomRate = roomRate;
        this.checkOutDate = "";
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void occupyRoom() {
        isOccupied = true;
    }

    public void vacateRoom() {
        isOccupied = false;
    }

    public double getRoomRate() {
        return roomRate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }
}

// Interface for Billing
interface Billing {
    double calculateBill(double roomRate, int numDays);
}

// Custom Exception
class RoomNotAvailableException extends Exception {
    public RoomNotAvailableException(String message) {
        super(message);
    }
}

class SingleRoom extends Room {
    public SingleRoom(int roomNumber) {
        super(roomNumber, 2000.0);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom(int roomNumber) {
        super(roomNumber, 3000.0);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom(int roomNumber) {
        super(roomNumber, 5000.0);
    }
}

// Billing implementation
class HotelBilling implements Billing {
    @Override
    public double calculateBill(double roomRate, int numDays) {
        return roomRate * numDays;
    }
}

class Hotel implements CheckInOut {
    private List<Room> rooms;
    private List<Guest> guests;
    private List<Guest> checkedOutGuests;
    private int registrationSequence = 10001;
    @SuppressWarnings("unused")
    private Billing billing;  // Interface implementation

    public Hotel() {
        rooms = new ArrayList<>();
        guests = new ArrayList<>();
        checkedOutGuests = new ArrayList<>();
        billing = new HotelBilling();  // Interface implementation

        for (int i = 101; i <= 115; i++) {
            rooms.add(new SingleRoom(i));
        }

        for (int i = 201; i <= 215; i++) {
            rooms.add(new DoubleRoom(i));
        }

        for (int i = 301; i <= 315; i++) {
            rooms.add(new SuiteRoom(i));
        }
    }

    public List<Guest> getGuests() {
        return guests;
    }

    public List<Guest> getCheckedOutGuests() {
        return checkedOutGuests;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public int calculateNumDays(String checkInDate, String checkOutDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(checkInDate, formatter);
        LocalDate endDate = LocalDate.parse(checkOutDate, formatter);
        return (int) ChronoUnit.DAYS.between(startDate, endDate);
    }

    private List<Room> getAvailableRoomsByType(String roomType) {
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (!room.isOccupied() && room.getClass().getSimpleName().equals(roomType)) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    private long getValidPhoneNumber(Scanner scanner) {
        long phoneNumber = 0;
        boolean validInput = false;

        while (!validInput) {
            System.out.print("Enter Guest Phone Number : ");
            try {
                phoneNumber = scanner.nextLong();
                if (String.valueOf(phoneNumber).length() == 10) {
                    validInput = true;
                } else {
                    System.out.println("Invalid phone number. Please enter exactly 10 digits.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next();
            }
        }
        return phoneNumber;
    }

    private int generateRegistrationId(String roomType) {
        int registrationNumber = registrationSequence++;
        return registrationNumber;
    }

    public void displayAvailableRooms() {
        System.out.println("Available Rooms:");
        for (Room room : rooms) {
            if (!room.isOccupied()) {
                System.out.println("Room " + room.getRoomNumber());
            }
        }
    }

    public void displayBookedRooms() {
        System.out.println("Booked Rooms:");
        for (Room room : rooms) {
            if (room.isOccupied()) {
                System.out.println("Room " + room.getRoomNumber());
            }
        }
    }

    public void displayRegistrationsWithoutCheckOut() {
        if (guests.isEmpty()) {
            System.out.println("No registrations available.");
        } else {
            System.out.println("\nRegistrations Active:");
            for (Guest guest : guests) {
                if (!isGuestCheckedOut(guest)) {
                    System.out.println(guest);
                }
            }
        }
    }

    public void displayCheckedOutRegistrations() {
        if (checkedOutGuests.isEmpty()) {
            System.out.println("No checked-out registrations available.");
        } else {
            System.out.println("\nChecked-Out Registrations:");
            for (Guest guest : checkedOutGuests) {
                System.out.println(guest);
            }
        }
    }

    private boolean isGuestCheckedOut(Guest guest) {
        for (Room room : rooms) {
            if (isGuestInRoom(room, guest.getRegistrationId()) && !room.isOccupied()) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unused")
    private String getCheckOutDate(Guest guest) {
        for (Room room : rooms) {
            if (isGuestInRoom(room, guest.getRegistrationId()) && !room.isOccupied()) {
                return room.getCheckOutDate();
            }
        }
        return "Not checked out";
    }

    public void checkIn() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Guest Name: ");
        String guestName = scanner.nextLine();

        long phoneNumber = getValidPhoneNumber(scanner);

        System.out.print("Enter check-in date (YYYY-MM-DD): ");
        String checkInDate = scanner.next();

        System.out.println("Select room type: ");
        System.out.println("1. Single Room");
        System.out.println("2. Double Room");
        System.out.println("3. Suite Room");
        System.out.print("Enter your choice: ");

        int roomTypeChoice = scanner.nextInt();
        String roomType;

        switch (roomTypeChoice) {
            case 1:
                roomType = "SingleRoom";
                break;
            case 2:
                roomType = "DoubleRoom";
                break;
            case 3:
                roomType = "SuiteRoom";
                break;
            default:
                System.out.println("Invalid choice. Please enter a valid option.");
                return;
        }

        List<Room> availableRooms = getAvailableRoomsByType(roomType);

        if (availableRooms.isEmpty()) {
            System.out.println("No available rooms of type " + roomType + ". Please choose a different type.");
            return;
        }

        System.out.println("Available " + roomType + "s:");
        for (Room room : availableRooms) {
            System.out.println("Room " + room.getRoomNumber());
        }

        boolean roomFound = false;

        while (!roomFound) {
            System.out.print("Enter room number: ");
            int roomNumber = scanner.nextInt();

            for (Room room : availableRooms) {
                if (room.getRoomNumber() == roomNumber && !room.isOccupied()) {
                    room.occupyRoom();
                    int registrationId = generateRegistrationId(roomType);

                    Guest checkedInGuest = new Guest(guestName, phoneNumber, checkInDate, registrationId, roomNumber);
                    guests.add(checkedInGuest);

                    System.out.println("Check-In Details:");
                    System.out.println("Registration ID: " + registrationId);
                    System.out.println("Guest: " + guestName);
                    System.out.println("Phone: " + phoneNumber);
                    System.out.println("Check-In Date: " + checkInDate);
                    System.out.println("Room Type: " + roomType);
                    System.out.println("Room Number: " + roomNumber);

                    roomFound = true;
                    break;
                } else if (room.isOccupied()) {
                    System.out.println("Room " + roomNumber + " is not available for reservation. Please enter a different room number.");
                    break;
                }
            }
        }
    }

    public void checkOutByRegistrationId() {
        if (guests.isEmpty()) {
            System.out.println("No registrations available for check-out.");
            return;
        }

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter Registration ID for check-out: ");
            int registrationId = scanner.nextInt();

            System.out.print("Enter check-out date (YYYY-MM-DD): ");
            String checkOutDate = scanner.next();

            CheckOut.processCheckOut(this, registrationId, checkOutDate);
        }
    }

    public void displayAllRegistrations() {
        if (guests.isEmpty() && checkedOutGuests.isEmpty()) {
            System.out.println("No registrations available.");
        } else {
            System.out.println("\nAll Registrations:");

            for (Guest guest : guests) {
                System.out.println(guest);
            }

            for (Guest checkedOutGuest : checkedOutGuests) {
                System.out.println(checkedOutGuest);
            }
        }
    }


    public boolean isGuestInRoom(Room room, int registrationId) {
        if (!room.isOccupied()) {
            return false;
        }

        for (Guest guest : guests) {
            if (guest.getRegistrationId() == registrationId && guest.getRoomNumber() == room.getRoomNumber()) {
                return true;
            }
        }
        return false;
    }
}

// Thread class for processing check-out asynchronously
class CheckOutThread extends Thread {
    private Hotel hotel;
    private int registrationId;
    private String checkOutDate;

    public CheckOutThread(Hotel hotel, int registrationId, String checkOutDate) {
        this.hotel = hotel;
        this.registrationId = registrationId;
        this.checkOutDate = checkOutDate;
    }

    @Override
    public void run() {
        CheckOut.processCheckOut(hotel, registrationId, checkOutDate);
    }
}
