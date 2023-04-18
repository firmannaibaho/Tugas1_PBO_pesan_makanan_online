import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static ArrayList<Restaurant> restaurants = new ArrayList<>();
    static ArrayList<Order> orders = new ArrayList<>();
    static User currentUser;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initRestaurants();
        login();
    }

    public static void initRestaurants() {
        ArrayList<MenuItem> menu1 = new ArrayList<>();
        menu1.add(new MenuItem("Nasi Goreng", 15000));
        menu1.add(new MenuItem("Ayam Goreng", 20000));
        menu1.add(new MenuItem("Es Teh", 5000));
        restaurants.add(new Restaurant("Warung Makan A", "Jl. Merdeka No.1", menu1));

        ArrayList<MenuItem> menu2 = new ArrayList<>();
        menu2.add(new MenuItem("Mie Ayam", 12000));
        menu2.add(new MenuItem("Sate Ayam", 15000));
        menu2.add(new MenuItem("Es Jeruk", 6000));
        restaurants.add(new Restaurant("Warung Makan B", "Jl. Veteran No.2", menu2));
    }

    public static void login() {
        while (true) {
            System.out.print("Username: ");
            String username = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();

            if (username.equals("admin") && password.equals("admin")) {
                currentUser = new Admin();
                adminMenu();
            } else if (username.equals("customer") && password.equals("customer")) {
                currentUser = new Customer();
                customerMenu();
            } else {
                System.out.println("Invalid username or password.");
            }
        }
    }

    public static void adminMenu() {
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Lihat Restoran");
            System.out.println("2. Tambah Restoran");
            System.out.println("3. Hapus Restoran");
            System.out.println("4. Kembali ke Login\n >>");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    printRestaurants();
                    break;
                case 2:
                    addRestaurant();
                    break;
                case 3:
                    removeRestaurant();
                    break;
                case 4:
                    login();
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }
    }

   public static void printRestaurants() {
    System.out.println("\nRestaurants:");
    for (int i = 0; i < restaurants.size(); i++) {
        Restaurant restaurant = restaurants.get(i);
        System.out.println((i + 1) + ". " + restaurant.getName());
        System.out.println("   " + restaurant.getAddress());
        System.out.println("   Menu:");
        for (int j = 0; j < restaurant.getMenu().size(); j++) {
            MenuItem menuItem = restaurant.getMenu().get(j);
            System.out.println("   " + (j + 1) + ". " + menuItem.getName() + " - " + menuItem.getPrice());
        }
        System.out.println();
    }
}


public static void addRestaurant() {
    System.out.print("\nName: ");
    String name = scanner.nextLine();
    System.out.print("Address: ");
    String address = scanner.nextLine();

    ArrayList<MenuItem> menu = new ArrayList<>();
    boolean addMore = true; 
    while (addMore) { 
        System.out.print("Menu item name (empty to finish): ");
        String itemName = scanner.nextLine();
        if (itemName.isEmpty()) {
            addMore = false; 
        } else {
            System.out.print("Menu item price: ");
            int itemPrice = Integer.parseInt(scanner.nextLine());
            menu.add(new MenuItem(itemName, itemPrice));
        }
    }

    restaurants.add(new Restaurant(name, address, menu));
    System.out.println("Restaurant added.");
}

    public static void removeRestaurant() {
        System.out.print("\nEnter the number of the restaurant to remove: ");
        int restaurantNumber = Integer.parseInt(scanner.nextLine()) - 1;
        if (restaurantNumber < 0 || restaurantNumber >= restaurants.size()) {
            System.out.println("Invalid restaurant number.");
        } else {
            restaurants.remove(restaurantNumber);
            System.out.println("Restaurant removed.");
        }
    }

    public static void customerMenu() {
        while (true) {
            System.out.println("\nCustomer Menu:");
            System.out.println("1. Data Restoran");
            System.out.println("2. Buat Pesanan");
            System.out.println("3. Lihat Pesanan");
            System.out.println("4. Kembali ke Login\n >>");

            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    printRestaurants();
                    break;
                case 2:
                    createOrder();
                    break;
                case 3:
                    printOrders();
                    break;
                case 4:
                    login();
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }
    }

public static void createOrder() {
    while (true) {
        System.out.println("\nAvailable restaurants:");
        for (int i = 0; i < restaurants.size(); i++) {
            System.out.println((i+1) + ". " + restaurants.get(i).getName());
        }
        System.out.print("\nEnter the number of the restaurant (0 to finish): ");
        int restaurantNumber = Integer.parseInt(scanner.nextLine()) - 1;
        if (restaurantNumber == -1) {
            break;
        } else if (restaurantNumber < 0 || restaurantNumber >= restaurants.size()) {
            System.out.println("Invalid restaurant number.");
            continue;
        }
        Restaurant restaurant = restaurants.get(restaurantNumber);
        System.out.println("\nMenu for " + restaurant.getName() + ":");

        ArrayList<MenuItem> menu = restaurant.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            MenuItem menuItem = menu.get(i);
            System.out.println((i+1) + ". " + menuItem.getName() + " - Rp" + menuItem.getPrice());
        }

        ArrayList<OrderItem> orderItems = new ArrayList<>();
        while (true) {
            System.out.print("\nEnter the number of the menu item (0 to finish): ");
            int menuItemNumber = Integer.parseInt(scanner.nextLine()) - 1;
            if (menuItemNumber == -1) {
                break;
            } else if (menuItemNumber < -1 || menuItemNumber >= restaurant.getMenu().size()) {
                System.out.println("Invalid menu item number.");
            } else {
                MenuItem menuItem = restaurant.getMenu().get(menuItemNumber);
                System.out.print("Enter the quantity: ");
                int quantity = Integer.parseInt(scanner.nextLine());
                orderItems.add(new OrderItem(menuItem, quantity));
            }
        }

        System.out.print("Enter the distance (in km) to your location: ");
        double distance = Double.parseDouble(scanner.nextLine());

        int total = 0;
        for (OrderItem orderItem : orderItems) {
            total += orderItem.getMenuItem().getPrice() * orderItem.getQuantity();
        }
        int deliveryFee = (int) Math.ceil(distance) * 5000;
        int grandTotal = total + deliveryFee;

        Order order = new Order(restaurantNumber, orderItems, distance, grandTotal);
        orders.add(order);
        System.out.println("\nOrder created.");
    }
}

    public static void printOrders() {
        System.out.println("\nOrders:");
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            Restaurant restaurant = restaurants.get(order.getRestaurantNumber());
            System.out.println((i + 1) + ". " + restaurant.getName());
            for (OrderItem orderItem : order.getOrderItems()) {
                System.out.println("   " + orderItem.getMenuItem().getName() + " x " + orderItem.getQuantity());
            }
            System.out.println("   Delivery distance: " + order.getDistance() + " km");
            System.out.println("   Total: " + order.getGrandTotal());
        }
    }
}

class User {
}

class Admin extends User {
}

class Customer extends User {
}

class Restaurant {
    private String name;
    private String address;
    private ArrayList<MenuItem> menu;
    public Restaurant(String name, String address, ArrayList<MenuItem> menu) {
        this.name = name;
        this.address = address;
        this.menu = menu;
    }
    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public ArrayList<MenuItem> getMenu() {
        return menu;
    }
}

class MenuItem {
    private String name;
    private int price;
    public MenuItem(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}



class OrderItem {
    private MenuItem menuItem;
    private int quantity;
    public OrderItem(MenuItem menuItem, int quantity) {
        this.menuItem = menuItem;
        this.quantity = quantity;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public int getQuantity() {
        return quantity;
    }
}

class Order {
    private int restaurantNumber;
    private ArrayList<OrderItem> orderItems;
    private double distance;
    private int grandTotal;
    public Order(int restaurantNumber, ArrayList<OrderItem> orderItems, double distance, int grandTotal) {
        this.restaurantNumber = restaurantNumber;
        this.orderItems = orderItems;
        this.distance = distance;
        this.grandTotal = grandTotal;
    }

    public int getRestaurantNumber() {
        return restaurantNumber;
    }

    public ArrayList<OrderItem> getOrderItems() {
        return orderItems;
    }

    public double getDistance() {
        return distance;
    }

    public int getGrandTotal() {
        return grandTotal;
    }
}

        
