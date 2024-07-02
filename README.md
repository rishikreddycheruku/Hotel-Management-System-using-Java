# Hotel-Management-System-using-Java
OOPS representation of Hotel Management System and Program in Java

Welcome to the Hotel Management System project repository. This Java application simulates a simple hotel management system where users can manage rooms, guest check-ins, and check-outs.

## Features

- **Display Available Rooms:** View rooms that are currently vacant.
- **Check-In:** Register a guest into an available room.
- **Check-Out by Registration ID:** Process check-out for a guest using their registration ID.
- **Display Registrations:**
  - Display all registrations.
  - Display active registrations (guests who haven't checked out).
  - Display checked-out registrations.
- **Display Booked Rooms:** View rooms that are currently occupied.
- **Exit:** Quit the application.

## Classes and Interfaces

### Classes
- **HotelManagementSystem:** Main class containing the application's entry point and menu-driven interface.
- **Guest:** Represents a hotel guest with attributes like name, phone number, check-in date, registration ID, and room number.
- **CheckOut:** Handles the check-out process, calculating bills and updating room status.
- **Room, SingleRoom, DoubleRoom, SuiteRoom:** Abstract and concrete classes representing different types of rooms with specific room rates.
- **HotelBilling:** Implements the `Billing` interface to calculate bills based on room rate and number of days.
- **Hotel:** Implements `CheckInOut` interface and manages hotel operations like check-in, check-out, room management, and guest lists.
- **CheckOutThread:** A thread class for performing check-out asynchronously.

### Interfaces
- **CheckInOut:** Defines methods for check-in and check-out operations.
- **Billing:** Defines method for calculating bills.

## Getting Started

1. **Clone the repository:**
   ```bash
   git clone https://github.com/rishikreddycheruku/Hotel-Management-System-using-Java.git
   cd Hotel-Management-System-using-Java/src
   ```

2. **Compile and Run:**
   ```bash
   javac HotelManagementSystem/HotelManagementSystem.java
   java HotelManagementSystem.HotelManagementSystem
   ```

3. **Usage:**
   - Follow the on-screen menu to interact with the application.
   - Enter numeric choices to navigate through different operations.

## Notes

- This application uses Java's `Scanner` for user input and basic console output for display.
- Room rates are predefined for different types of rooms (Single, Double, Suite).
- Date handling uses Java's `LocalDate` and `DateTimeFormatter` for processing check-in and check-out dates.
- In Case of any issues navigate to the file HotelManagementSystem.java and execute
   ```bash
   javac HotelManagementSystem.java
   ```
