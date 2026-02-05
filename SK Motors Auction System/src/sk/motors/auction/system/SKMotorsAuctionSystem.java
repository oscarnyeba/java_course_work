package sk.motors.auction.system;
import java.util.Scanner;

/**
 * @authors
 * OGWANG GIFT GIDEON   VU-BCS-2503-0706-EVE
 * SUUBI DEBORAH 	VU-DIT-2503-1213-EVE
 * NYEBA OSCAR MATHEW	VU-BCS-2503-1204-EVE 
 * NALWOGA MADRINE      VU-BIT-2503-2460-EVE 
 * NAMAGAMBE PRECIOUS   VU-BSC-2503-0355-EVE
 * KINTU BRIAN          VU-DIT-2503-0306-EVE
 * 
 */

public class SKMotorsAuctionSystem {
    // CONSTANTS - Values that will not change
    // Number of bidders allowed in the auction
    private static final int NUM_BIDDERS = 3;
    
    // Minimum valid bid amount (in currency units) 
    private static final double MIN_BID = 0.01;
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Display welcome message
        displayWelcomeMessage();
        
        // Step 1: Get vehicle details from user
        Vehicle vehicle = getVehicleDetails(scanner);
        
        // Step 2: Display vehicle information to confirm
        displayVehicleInfo(vehicle);
        
        // Step 3: Collect bids from all bidders
        Bidder[] bidders = collectBids(scanner, NUM_BIDDERS);
        
        // Step 4: Find the highest bidder
        Bidder winner = findHighestBidder(bidders);
        
        // Step 5: Calculate financial outcome
        double profit = calculateProfit(vehicle, winner.getBidAmount());
        
        // Step 6: Display final results
        displayResults(vehicle, winner, profit);
        
        // Close system scanner
        scanner.close();
    }
    
    
    private static Vehicle getVehicleDetails(Scanner scanner) {
        System.out.println("\n=== VEHICLE REGISTRATION ===");
        
        // Get registration number
        System.out.print("Enter vehicle registration number: ");
        String registrationNumber = scanner.nextLine().trim(); // trim() removes trailing white spaces around characters
        
        // Get vehicle cost with validation
        double cost = getValidDouble(scanner, "Enter vehicle cost: ", 0);
        
        // Get current balance with validation
        double balance = getValidDouble(scanner, "Enter current balance on vehicle: ", 0);
        
        // Create and return vehicle object
        return new Vehicle(registrationNumber, cost, balance);
    }
    
    private static void displayVehicleInfo(Vehicle vehicle) {
        // Uses formated strings for printing dynamic data
        // %s prints strings
        // %.2f prints a float to 2 decimal places
        // %n moves to a new line
        System.out.println("\n=== VEHICLE DETAILS ===");
        System.out.printf("Registration Number: %s%n", vehicle.getRegistrationNumber());
        System.out.printf("Vehicle Cost:        shs %.2f%n", vehicle.getCost());
        System.out.printf("Current Balance:     shs %.2f%n", vehicle.getBalance());
        System.out.printf("Total Expenses:      shs %.2f%n", vehicle.getTotalExpenses());
    }
    

    // BIDDING METHODS
    private static Bidder[] collectBids(Scanner scanner, int numBidders) {
        Bidder[] bidders = new Bidder[numBidders]; // Bidder array
        
        System.out.println("\n=== BIDDING PROCESS ===");
        
        // Collect each bid
        for (int i = 0; i < numBidders; i++) {
            System.out.printf("%n--- Bidder %d ---%n", i + 1);
            
            // Get bidder name
            System.out.print("Enter bidder name: ");
            String name = scanner.nextLine().trim();
            
            // Get bid amount with validation
            double bidAmount = getValidDouble(scanner, "Enter bid amount: ", MIN_BID);
            
            // Create bidder object
            bidders[i] = new Bidder(name, bidAmount);
        }
       
        return bidders;
    }
    
    private static Bidder findHighestBidder(Bidder[] bidders) {
        // Start with first bidder as highest
        Bidder highest = bidders[0];
        
        // Compare with remaining bidders
        for (int i = 1; i < bidders.length; i++) {
            if (bidders[i].getBidAmount() > highest.getBidAmount()) {
                highest = bidders[i];
            }
        }
        
        return highest;
    }
    
    
    // FINANCIAL CALCULATION METHODS
    private static double calculateProfit(Vehicle vehicle, double winningBid) {
        // Deposits = winning bid + remaining balance
        double totalDeposits = winningBid + vehicle.getBalance();
        
        // Expenses = vehicle cost (what we paid for it)
        double totalExpenses = vehicle.getTotalExpenses();
        
        // Profit/Loss = Deposits - Expenses
        return totalDeposits - totalExpenses;
    }
    
   
    // DISPLAY METHODS
    private static void displayWelcomeMessage() {
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║     SK MOTORS VEHICLE AUCTION SYSTEM       ║");
        System.out.println("║   Highest Bidder Wins - Transparent Deals  ║");
    }
    
    private static void displayResults(Vehicle vehicle, Bidder winner, double profit) {
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║            AUCTION RESULTS                 ║");
        System.out.println("╚════════════════════════════════════════════╝");
        
        // Winner information
        System.out.printf("%nWinning Bidder: %s%n", winner.getName());
        System.out.printf("Winning Bid:    shs %.2f%n", winner.getBidAmount());
        
        // Financial breakdown
        System.out.println("\n--- Financial Summary ---");
        System.out.printf("Total Deposits:  shs %.2f (Bid: shs %.2f + Balance: shs %.2f)%n", 
            winner.getBidAmount() + vehicle.getBalance(),
            winner.getBidAmount(),
            vehicle.getBalance());
        System.out.printf("Total Expenses:  shs %.2f%n", vehicle.getTotalExpenses());
        
        // Profit or Loss
        if (profit > 0) {
            System.out.printf("PROFIT:          shs %.2f ✓%n", profit);
        } else {
            // Math.abs() returns the absolute value incase of a negative
            System.out.printf("LOSS:            shs %.2f ✗%n", Math.abs(profit));
        }
    }
    
    
    // INPUT VALIDATION HELPERS
    private static double getValidDouble(Scanner scanner, String prompt, double minValue) {
        while (true) {
            System.out.print(prompt);
            
            // Check if input is a number
            if (!scanner.hasNextDouble()) {
                System.out.printf("Invalid input. Please enter a number.%n");
                scanner.next(); // Clear invalid input
                continue;
            }
            
            double value = scanner.nextDouble();
            scanner.nextLine(); // Consume newline
            
            // Check if value meets minimum requirement
            if (value >= minValue) {
                return value;
            }
            
            System.out.printf("Value must be at least %.2f%n", minValue);
        }
    }
}


// VEHICLE CLASS - Represents a vehicle in the auction
class Vehicle {
    // Instance variables - each Vehicle object has its own copy
    private String registrationNumber;
    private double cost;           // What we paid for the vehicle
    private double balance;        // Money still owed on the vehicle

    public Vehicle(String registrationNumber, double cost, double balance) {
        this.registrationNumber = registrationNumber;
        this.cost = cost;
        this.balance = balance;
    }
    
    // Getter methods - allow access to private variables
    public String getRegistrationNumber() {
        return registrationNumber;
    }
    
    public double getCost() {
        return cost;
    }
    
    public double getBalance() {
        return balance;
    }
    
    public double getTotalExpenses() {
        return cost;
    }
}


// BIDDER CLASS - Represents a person bidding on a vehicle
class Bidder {
    private String name;
    private double bidAmount;
    
    public Bidder(String name, double bidAmount) {
        this.name = name;
        this.bidAmount = bidAmount;
    }
    
    // Getter methods
    public String getName() {
        return name;
    }
    
    public double getBidAmount() {
        return bidAmount;
    }
}