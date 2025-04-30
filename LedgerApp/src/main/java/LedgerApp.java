import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


public class LedgerApp {  //fixme: not even sure how this came about
    static ArrayList<Transaction> ledger = new ArrayList<>();

    public static String fileName = "src/main/resources/Transactions.csv";

    public static void main(String[] args) throws IOException {
        ledger = loadLedger(fileName);

        Scanner myScanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println("\n===Home Screen===");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment");
            System.out.println("L) Display Ledger");
            System.out.println("M) Display full Ledger Menu");
            System.out.println("X) Exit");
            System.out.print("Please make a selection: ");
            String choice = myScanner.nextLine().trim().toUpperCase();

            switch (choice) {
                case "D":
                    addDeposit(myScanner);
                    break;
                case "P":
                    makePayment(myScanner);
                    break;
                case "L":
                    displayLedger();
                    break;
                case "M":
                    displayFullLedgerMenu();
                    break;
                case "X":
                    running = false;
                    System.out.println("There's the door. Catch you on the flip side");
                    break;
                default:
                    System.out.println("Sorry you don't have access to that . ");
            }
        }
        myScanner.close();


    }
    // create methods for switch cases

    public static void addDeposit(Scanner myScanner) {
        System.out.print("Insert how much you're depositing: ");
        String userEntryString = myScanner.nextLine();
        double amount = Double.parseDouble(userEntryString);

        System.out.print("Enter date of deposit (yyyy-mm-dd): ");
        String dateEntry = myScanner.nextLine();
        LocalDate date = LocalDate.parse(dateEntry);
////        System.out.print("Enter time of deposit (hh-mm-ss): ");
//        String timeEntry = myScanner.nextLine();
//        String dateTimeString = dateEntry + "T" + timeEntry;
//        LocalDateTime dateTime = LocalDateTime.parse(dateTimeString);
//        LocalDateTime today = LocalDateTime.now();
//        System.out.println("time" + today);
//
//        System.out.print("Enter Details for deposit: ");
//        String description = myScanner.nextLine();
//
//        System.out.print("Enter vendor for deposit: ");
//        String vendor = myScanner.nextLine();
//
//        Transaction deposit = new Transaction(dateEntry, timeEntry, description, vendor, amount);
//
//        ledger.add(deposit);


    }

    public static void makePayment(Scanner myScanner) { //todo: figure out why makePayment print to transactions.csv and not addDeposit
        System.out.print("Enter the payment amount: ");
        String amount = myScanner.nextLine();
        System.out.print("Enter payment date (yyyy-mm-dd): ");
        String date = myScanner.nextLine();
        System.out.print("Enter payment description: ");
        String description = myScanner.nextLine();
        System.out.println("Enter vendor: ");
        String vendor = myScanner.nextLine();
//        saveToLedger(date,"-" + amount,description,vendor, "Payment");

    }

//    private static void saveToLedger(String date, String s, String description, String vendor, String payment) { // fixme: adjust savetoLedger to provide correct info
//    }
    static ArrayList<Transaction> loadLedger(String filePath ){
        ArrayList<Transaction> tempLedger = new ArrayList<>();
        try  {
            FileReader myFileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(myFileReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {

                String[] parts = line.split("\\|");
                LocalDate date = LocalDate.parse(parts[0]);
                LocalTime time = LocalTime.parse(parts[1]);
                String description = parts[2];
                String vendor = parts[3];
                double amount = Double.parseDouble(parts[4]);

                // TODO: handle header
                Transaction newTransaction = new Transaction(date, time, description, vendor, amount);
                ledger.add(newTransaction);
            }
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
        return tempLedger;
    }

    public static void displayLedger() throws IOException {
        System.out.println("\n===Ledger===");

        System.out.printf("Amount", "Date", "Description", "Vendor", "Type");

    }

    public static void saveToLedger(String amount, String date, String description, String vendor, String type) { //fixme: how to call on this method
        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.append(amount)
                    .append(",")
                    .append(date)
                    .append(",")
                    .append(description)
                    .append(",")
                    .append(vendor)
                    .append(",")
                    .append(type)
                    .append("\n");
            System.out.println(type + "Has been saved successfully!");
        } catch (IOException e) {
            System.out.println("Uh oh.. slight problem mate" + e.getMessage());

        }

    }

    public static void displayFullLedgerMenu() {
        Scanner myScanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n===Ledger Menu===");
            System.out.println("A) All Transactions");
            System.out.println("D) Deposits Only");
            System.out.println("P) Payments only");
            System.out.println("R) Reports");
            System.out.println("X) Exit Ledger");
            System.out.println("Please select one: ");
            String choice = myScanner.nextLine().toUpperCase();

            switch (choice) {
                case "A":

                    break;
                case "D":
//                   fixme: is ledger the right variable? wouldn't it be transactions instead?
//                    displayLedger(ledger, "Deposit");
                    break;
                case "P":
//                    displayLedger(ledger, "Payment");
                    break;
                case "R":
//                    displayReportsMenu(ledger);
                    break;
                case "X":
                    return;
                default:
                    System.out.println("Cant pick that one mate.");


            }
        }
    }

//    private static void displayReportsMenu(ArrayList<Transaction> ledger) { Todo: understand which to use arrayList or hashmap
//        Scanner myScanner = new Scanner(System.in);
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        while (true){
//            System.out.println("\n===Reports Menu===");
//            System.out.println("1) Month to Date");
//            System.out.println("2) Previous Month");
//            System.out.println("3)Year to Date");
//            System.out.println("4) Previous Year");
//            System.out.println("5) Search by Vendor");
//            System.out.println("0) Back to Ledger Menu");
//            System.out.println("H) Home (Main Menu)");
//            String userChoice = myScanner.nextLine().toUpperCase();
//            switch-cases
public static void displayReportsMenu(List<Transaction> transactions) {  //fixme : finish hashmap with while/ for loop and if switch-case
    Scanner myScanner = new Scanner(System.in);
    HashMap<String, String> reportOptions = new HashMap<>();
    reportOptions.put("1", "Month to Date");
    reportOptions.put("2", "Previous Month");
    reportOptions.put("3", "Year to Date");
    reportOptions.put("4", "Previous Year");
    reportOptions.put("5", "Search by Vendor");
    reportOptions.put("0", "Back");
    reportOptions.put("H", "Home page");


}


//    }

//    private static void displayLedger(ArrayList<Transaction> ledger, String deposit) {
//    }

}


