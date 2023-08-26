import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Trade {
    private static int tradeCounter = 1;

    private int tradeNo;
    private String currencyPair;
    private String customerName;
    private double amount;
    private double rate;

    public Trade(String currencyPair, String customerName, double amount, double rate) {
        this.tradeNo = tradeCounter++;
        this.currencyPair = currencyPair;
        this.customerName = customerName;
        this.amount = amount;
        this.rate = rate;
    }

    public String getFormattedAmount() {
        DecimalFormat df = new DecimalFormat("###,###.00");
        return "INR" + df.format(amount);
    }

    public String toString() {
        return tradeNo + "\t" + currencyPair + "\t" + customerName + "\t" + getFormattedAmount() + "\t" + rate;
    }
}

class FXTradingApp {
    private static List<Trade> trades = new ArrayList<>();
    private static double usdToInrRate = 66.00;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Book Trade");
            System.out.println("2. Print Trades");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    bookTrade(scanner);
                    break;
                case 2:
                    printTrades();
                    break;
                case 3:
                    exitApp(scanner);
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
                    break;
            }
        }
    }

    private static void bookTrade(Scanner scanner) {
        scanner.nextLine(); // Consume newline
        System.out.print("Enter customer Name: ");
        String customerName = scanner.nextLine();

        System.out.print("Enter Currency Pair: ");
        String currencyPair = scanner.nextLine();

        if (!currencyPair.equalsIgnoreCase("USDINR")) {
            System.out.println("Invalid currency pair. Only USDINR is supported.");
            return;
        }

        System.out.print("Enter amount to transfer: ");
        double amount = scanner.nextDouble();

        System.out.print("Do you want to get Rate (Yes/No): ");
        scanner.nextLine(); // Consume newline
        String getRate = scanner.nextLine();

        double rate = usdToInrRate; // Default rate

        if (getRate.equalsIgnoreCase("Yes")) {
            rate = usdToInrRate; // You can fetch rate from an external source here
        }

        double inrAmount = amount * rate;

        System.out.println("You are transferring "
                + new Trade(currencyPair, customerName, inrAmount, rate).getFormattedAmount() + " to " + customerName);

        System.out.print("Book/Cancel this trade? (Book/Cancel): ");
        String action = scanner.nextLine();

        if (action.equalsIgnoreCase("Book")) {
            trades.add(new Trade(currencyPair, customerName, inrAmount, rate));
            System.out.println("Trade for " + currencyPair + " has been booked with rate " + rate + ", The amount of "
                    + new Trade(currencyPair, customerName, inrAmount, rate).getFormattedAmount()
                    + " will be transferred in 2 working days to " + customerName);
        } else if (action.equalsIgnoreCase("Cancel")) {
            System.out.println("Trade is Canceled.");
        }
    }

    private static void printTrades() {
        System.out.println("TradeNo\tCurrencyPair\tCustomerName\tAmount\tRate");
        for (Trade trade : trades) {
            System.out.println(trade);
        }
    }

    private static void exitApp(Scanner scanner) {
        System.out.print("Do you really want to exit (y/n): ");
        String confirm = scanner.next();

        if (confirm.equalsIgnoreCase("Y")) {
            System.out.println("Bye - have a good day");
            System.exit(0);
        }
    }
}
