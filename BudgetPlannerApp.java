import java.util.ArrayList;
import java.util.Scanner;
class User {
    private String userId;
    private String name;
    private String email;
    private String password;
    private double totalBudget;
    public User(String userId, String name, String email, String password, double totalBudget) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.totalBudget = totalBudget;
    }

    public String getUserId() { return userId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public double getTotalBudget() { return totalBudget; }

   
    public boolean validatePassword(String password) {
        return this.password.equals(password);
    }
}

class Income {
    private String incomeId;
    private String source;
    private double amount;

  
    public Income(String incomeId, String source, double amount) {
        this.incomeId = incomeId;
        this.source = source;
        this.amount = amount;
    }


    public String getSource() { return source; }
    public double getAmount() { return amount; }
}


class Expense {
    private String expenseId;
    private String category;
    private double amount;

    
    public Expense(String expenseId, String category, double amount) {
        this.expenseId = expenseId;
        this.category = category;
        this.amount = amount;
    }

    
    public String getCategory() { return category; }
    public double getAmount() { return amount; }
}


class BudgetPlanner {
    private User user;
    private ArrayList<Income> incomes;
    private ArrayList<Expense> expenses;

   
    public BudgetPlanner(User user) {
        this.user = user;
        this.incomes = new ArrayList<>();
        this.expenses = new ArrayList<>();
    }

    
    public void addIncome(String source, double amount) {
        String incomeId = "INC-" + (incomes.size() + 1);
        incomes.add(new Income(incomeId, source, amount));
        System.out.println("Income added successfully!");
    }

    
    public void addExpense(String category, double amount) {
        String expenseId = "EXP-" + (expenses.size() + 1);
        expenses.add(new Expense(expenseId, category, amount));
        System.out.println("Expense added successfully!");
    }

    
    public double calculateTotalIncome() {
        return incomes.stream().mapToDouble(Income::getAmount).sum();
    }

    
    public double calculateTotalExpenses() {
        return expenses.stream().mapToDouble(Expense::getAmount).sum();
    }

    
    public double calculateSavings() {
        return calculateTotalIncome() - calculateTotalExpenses();
    }

    
    public void generateReport() {
        System.out.println("\n--- Budget Report ---");
        System.out.println("Total Income: $" + calculateTotalIncome());
        System.out.println("Total Expenses: $" + calculateTotalExpenses());
        System.out.println("Savings: $" + calculateSavings());
        System.out.println("---------------------");
    }
}


public class BudgetPlannerApp {
    private static ArrayList<User> users = new ArrayList<>();
    private static BudgetPlanner currentPlanner = null;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n--- Budget Planner ---");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    loginUser();
                    break;
                case 3:
                    System.out.println("Exiting... Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }


    private static void registerUser() {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        System.out.print("Enter a password: ");
        String password = scanner.nextLine();
        System.out.print("Enter your total budget: ");
        double totalBudget = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        String userId = "USER-" + (users.size() + 1);
        users.add(new User(userId, name, email, password, totalBudget));
        System.out.println("Registration successful! You can now log in.");
    }

   
    private static void loginUser() {
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        User user = users.stream().filter(u -> u.getEmail().equals(email)).findFirst().orElse(null);
        if (user != null && user.validatePassword(password)) {
            System.out.println("Login successful! Welcome, " + user.getName() + "!");
            currentPlanner = new BudgetPlanner(user);
            userDashboard();
        } else {
            System.out.println("Invalid email or password. Please try again.");
        }
    }

    private static void userDashboard() {
        while (true) {
            System.out.println("\n--- User Dashboard ---");
            System.out.println("1. Add Income");
            System.out.println("2. Add Expense");
            System.out.println("3. View Report");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    System.out.print("Enter income source: ");
                    String source = scanner.nextLine();
                    System.out.print("Enter income amount: ");
                    double incomeAmount = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
                    currentPlanner.addIncome(source, incomeAmount);
                    break;
                case 2:
                    System.out.print("Enter expense category: ");
                    String category = scanner.nextLine();
                    System.out.print("Enter expense amount: ");
                    double expenseAmount = scanner.nextDouble();
                    scanner.nextLine(); 
                    currentPlanner.addExpense(category, expenseAmount);
                    break;
                case 3:
                    currentPlanner.generateReport();
                    break;
                case 4:
                    System.out.println("Logged out successfully!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
