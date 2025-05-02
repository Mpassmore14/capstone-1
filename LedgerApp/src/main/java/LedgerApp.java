import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;


public class LedgerApp {
    static ArrayList<Transaction> ledger = new ArrayList<>();

    public static String fileName = "src/main/resources/Transactions.csv";

    public static void main(String[] args) {
        ledger = loadLedger();

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


    public static void addDeposit(Scanner myScanner) {
        System.out.print("Enter the deposit amount: ");
        String amountString = myScanner.nextLine();
        double amount = Double.parseDouble(amountString);

        System.out.print("Enter deposit date (yyyy-mm-dd): ");
        String dateString = myScanner.nextLine();
        LocalDate date = LocalDate.parse(dateString);

        System.out.println("Enter the time (hh:m): ");
        String timeString = myScanner.nextLine();
        LocalTime time = LocalTime.parse(timeString);

        System.out.print("Enter deposit description: ");
        String description = myScanner.nextLine();
        System.out.println("Enter vendor: ");
        String vendor = myScanner.nextLine();
        Transaction deposit;
        deposit = new Transaction(date, time, description, vendor, amount);
        ledger.add(deposit);
        saveToLedger();
    }

    public static void makePayment(Scanner myScanner) { //todo: figure out why makePayment print to transactions.csv and not addDeposit
        System.out.print("Enter the payment amount: ");
        String amountString = myScanner.nextLine();
        double amount = Double.parseDouble(amountString);

        System.out.print("Enter payment date (yyyy-mm-dd): ");
        String dateString = myScanner.nextLine();
        LocalDate date = LocalDate.parse(dateString);

        System.out.println("Enter the time (hh:m): ");
        String timeString = myScanner.nextLine();
        LocalTime time = LocalTime.parse(timeString);

        System.out.print("Enter payment description: ");
        String description = myScanner.nextLine();
        System.out.println("Enter vendor: ");
        String vendor = myScanner.nextLine();
        Transaction payment;
        payment = new Transaction(date, time, description, vendor, amount);
        ledger.add(payment);
        saveToLedger();
    }
    static ArrayList<Transaction> loadLedger() {
        ArrayList<Transaction> tempLedger = new ArrayList<>();
        try {
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
                tempLedger.add(newTransaction);
            }
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
        return tempLedger;
    }
    public static void displayLedger() {
        System.out.println("\n===Ledger===");
        System.out.println("Amount, Date, Description, Vendor, Type");
        for (Transaction t: ledger){
            System.out.println((t.getDate() + "|" + t.getTime() + "|" + t.getDescription() + "|" + t.getVendor() + "|" + t.getAmount()));
        }
    }

    public static void saveToLedger() {
        try (FileWriter writer = new FileWriter(fileName, false)) {
            for (Transaction t : ledger) {
                writer.write(t.getDate() + "|" + t.getTime() + "|" + t.getDescription() + "|" + t.getVendor() + "|" + t.getAmount() + "\n");
            }
            System.out.println("You did it!");
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
                    allTransactions(ledger);
                    break;
                case "D":
                    depositsOnly(ledger);
                    break;
                case "P":
                    paymentsOnly(ledger);
                    break;
                case "R":
                    displayReportsMenu(ledger);
                    break;
                case "X":
                    return;
                default:
                    System.out.println("Cant pick that one mate.");


            }
        }
    }

    private static void paymentsOnly(ArrayList<Transaction> transactions) {
        for (Transaction t : transactions) {
            if (t.getAmount() < 0) {
                System.out.println(t.getDate() + " " + t.getTime());
            }

        }
    }

    private static void depositsOnly(ArrayList<Transaction> transactions) {
        for (Transaction t : transactions) {
            if (t.getAmount() > 0)
                System.out.println(t.getDate() + " " + t.getTime());

        }

    }

    private static void allTransactions(ArrayList<Transaction> transactions) {
        for (Transaction t : transactions) {
            System.out.println(t.getDate() + " " + t.getTime() + " " + t.getDescription() + " " + t.getVendor() + " " + t.getAmount());

        }

    }


    public static void displayReportsMenu(ArrayList<Transaction> transactions) {
        Scanner myScanner = new Scanner(System.in);
        ArrayList<String> reportOptions = new ArrayList<>();
        reportOptions.add("1) Month to Date");
        reportOptions.add("2) Previous Month");
        reportOptions.add("3) Year to Date");
        reportOptions.add("4) Previous Year");
        reportOptions.add("5) Search by Vendor");
        reportOptions.add("0) Back");
        while (true) {
            System.out.println("\n=== Reports Menu===");
            for (String option : reportOptions) {
                System.out.println(option);

            }
            System.out.print("Select an option your grace: ");
            String choice = myScanner.nextLine().toUpperCase();
            switch (choice) {
                case "1":
                    reportMonthToDate(transactions);
                    break;
                case "2":
                    reportPreviousMonth(transactions);
                    break;
                case "3":
                    reportYeartoDate(transactions);
                    break;
                case "4":
                    reportPreviousYear(transactions);
                    break;
                case "5":
                    searchByVendor(transactions);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Cant pick that one. Try again darling.");
            }
        }


    }

    private static void searchByVendor(ArrayList<Transaction> transactions) {

    }

    private static void reportPreviousYear(ArrayList<Transaction> transactions) {
        LocalDate today = LocalDate.now();
        int year = today.getYear() - 1;

        System.out.println("\n---Previous Year---");
        for (Transaction t : transactions) {
            String actions = String.valueOf(t.getDate());
            LocalDate weee = LocalDate.parse(actions);
            int tYear = weee.getYear();
            if (year == tYear) {
                System.out.println(t);

            }

        }
    }

    private static void reportYeartoDate(ArrayList<Transaction> transactions) {
        LocalDate today = LocalDate.now();

        int year = today.getYear();

        System.out.println("\n---Year to Date---");
        for (Transaction t : transactions) {
            String actions = String.valueOf(t.getDate());
            LocalDate weee = LocalDate.parse(actions);
            int tYear = weee.getYear();
            if (year == tYear) {
                System.out.println(t);

            }

        }
    }


    private static void reportMonthToDate(ArrayList<Transaction> transactions) {
        LocalDate today = LocalDate.now();
        int month = today.getMonthValue();
        int year = today.getYear();

        System.out.println("\n---Month to Date---");
        for (Transaction t : transactions) {
            String actions = String.valueOf(t.getDate());
            LocalDate weee = LocalDate.parse(actions);
            int tMonth = weee.getMonthValue();
            int tYear = weee.getYear();
            if (month == tMonth && year == tYear) {
                System.out.println(t);

            }

        }


    }

    private static void reportPreviousMonth(ArrayList<Transaction> transactions) {
        LocalDate today = LocalDate.now();
        int month = today.getMonthValue() - 1;
        int year = today.getYear();

        System.out.println("\n---Previous Month---");
        for (Transaction t : transactions) {
            String actions = String.valueOf(t.getDate());
            LocalDate weee = LocalDate.parse(actions);
            int tMonth = weee.getMonthValue();
            int tYear = weee.getYear();
            if (month == 0) {
                month = 12;
                year = year - 1;
            }
            if (month == tMonth && year == tYear) {
                System.out.println(t);
            }

        }


    }

}

