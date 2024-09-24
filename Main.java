import java.util.*;

class Car {
    private String carId;
    private String carBrand;
    private String carModel;
    private double pricePerDay;
    boolean isAvailable;

    public Car(String carId, String carBrand, String carModel, double pricePerDay) {
        this.carId = carId;
        this.carBrand = carBrand;
        this.carModel = carModel;
        this.pricePerDay = pricePerDay;
        isAvailable = true;
    }

    public String getcarId() {
        return carId;
    }

    public String getcarBrand() {
        return carBrand;
    }

    public String getcarModel() {
        return carModel;
    }

    public double CalculatePrice(int rentDays) {
        return pricePerDay * rentDays;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void rent() {
        isAvailable = false;
    }

    public void returnCar() {
        isAvailable = true;
    }
}

class Customer {
    private String cusId;
    private String cusName;

    public Customer(String cusId, String cusName) {
        this.cusId = cusId;
        this.cusName = cusName;
    }

    public String getcusId() {
        return cusId;
    }

    public String getcusName() {
        return cusName;
    }

}

class Rental {
    private Car car;
    private Customer customer;
    private int days;

    Rental(Car car, Customer customer, int days) {
        this.car = car;
        this.customer = customer;
        this.days = days;
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

}

class CarRentalSystem {
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    public CarRentalSystem() {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void addcusName(Customer customer) {
        customers.add(customer);
    }

    public void rentCar(Car car, Customer customer, int days) {
        if (car.isAvailable()) {
            car.rent();
            rentals.add(new Rental(car, customer, days));
        } else {
            System.out.println("Car is not Acailable for rent");
        }

    }

    public void returnCar(Car car) {
        car.returnCar();
        Rental rentToRemove = null;
        for (Rental rental : rentals) {
            if (rental.getCar() == car) {
                rentToRemove = rental;
                break;

            }
        }
        if (rentToRemove != null) {
            rentals.remove(rentToRemove);
        } else {
            System.out.println("car was not rented");
        }

    }

    public void menu(){
        Scanner sc=new Scanner(System.in);
        while(true){
            System.out.println("===RENTAL CAR SYSTEM===");
            System.out.println("1. RENT A CAR");
            System.out.println("RETURN A CAR");
            System.out.println("EXIT");
            System.out.print("Enter your choice:");
            int choice =sc.nextInt();
            sc.nextLine();

            if(choice==1)
            {
                System.out.println("Enter your name");
                String name=sc.nextLine();

                for(Car car:cars){
                    if(car.isAvailable()){
                    System.out.println(car.getcarId()+"  "+car.getcarBrand()+" "+car.getcarModel());
                    }
                }

                System.out.print("Enter a car id u want to rent:");
                String carId=sc.nextLine();

                System.out.println("Enter a number of days u want to rent:");

                int rentDays=sc.nextInt();


                sc.nextLine(); 

                Customer newCustomer=new Customer("CUS"+(customers.size()+1), name);
                addcusName(newCustomer);
                Car selectedCar=null;
                for(Car car:cars){
                    if(car.getcarId().equals(carId)){
                        selectedCar=car;
                        break;
                    }
                }
                if(selectedCar!=null){
                    Double totalPrice=selectedCar.CalculatePrice(rentDays);
                    System.out.println("Customer Information");
                    System.out.println("Customer ID: "+newCustomer.getcusId());
                    System.out.println("Customer Name: "+newCustomer.getcusName());
                    System.out.println("Car :"+selectedCar.getcarBrand()+selectedCar.getcarModel());
                    System.out.println("rental days:"+rentDays);
                    System.out.printf("Total Price: $%.2f%n", totalPrice);

                    System.out.print("\nConfirm rental (Y/N): ");
                    String confirm = sc.nextLine();


                    if (confirm.equalsIgnoreCase("Y")) {
                        rentCar(selectedCar, newCustomer, rentDays);
                        System.out.println("\nCar rented successfully.");
                    } else {
                        System.out.println("\nRental canceled.");
                    }
                } else {
                    System.out.println("\nInvalid car selection or car not available for rent.");
                }
            }


            
                else if (choice == 2) {
                    System.out.println("\n== Return a Car ==\n");
                    System.out.print("Enter the car ID you want to return: ");
                    String carId = sc.nextLine();
    
                    Car carToReturn = null;
                    for (Car car : cars) {
                        if (car.getcarId().equals(carId) && !car.isAvailable()) {
                            carToReturn = car;
                            break;
                        }
                    }
    
                    if (carToReturn != null) {
                        Customer customer = null;
                        for (Rental rental : rentals) {
                            if (rental.getCar() == carToReturn) {
                                customer = rental.getCustomer();
                                break;
                            }
                        }
    
                        if (customer != null) {
                            returnCar(carToReturn);
                            System.out.println("Car returned successfully by " + customer.getcusName());
                        } else {
                            System.out.println("Car was not rented or rental information is missing.");
                        }
                    } else {
                        System.out.println("Invalid car ID or car is not rented.");
                    }
                } else if (choice == 3) {
                    break;
                } else {
                    System.out.println("Invalid choice. Please enter a valid option.");
                }
            }
    
            System.out.println("\nThank you for using the Car Rental System!");
        }
    
    }

    


public class Main {
    public static void main(String[] args) {
        CarRentalSystem rentalSystem = new CarRentalSystem();
        rentalSystem.addCar(new Car("001", "Toyota", "fortuner", 150.0));
        rentalSystem.addCar(new Car("002", "Mahindra", "thar", 90.0));
        rentalSystem.addCar(new Car("003", "Suzuki", "Jimny", 100.0));
        rentalSystem.addCar(new Car("004", "Force", "Gurkha", 130.0));

        rentalSystem.menu();
    }
}