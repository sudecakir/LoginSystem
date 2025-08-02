import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class LoginApp {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Login System ---\n");
            System.out.print("1-Register\n2-Login\n3-Exit ");
            System.out.println("Enter your choice: ");
            int choice = input.nextInt();
            input.nextLine();

            if (choice == 1) {
                System.out.println("Enter a username: ");
                String username = input.nextLine();
                System.out.println("Enter a password: ");
                String password = input.nextLine();
                boolean userExists=false;
                try (Scanner fileScanner = new Scanner(new java.io.File("users.txt"))) {
                    while (fileScanner.hasNextLine()) {
                        String line = fileScanner.nextLine();
                        String[] parts = line.split(",");
                        if(parts[0].equals(username)) {
                            userExists = true;
                            break;
                        }
                    }

                }catch (IOException e) {
                    System.out.println("Could not read user file: " + e.getMessage());
                }
                if(userExists) {
                    System.out.println("This user already taken. Try another.");
                }else{
                    try (FileWriter writer = new FileWriter("users.txt",true)){
                        writer.write(username + ","+ password + "\n");
                        System.out.println("Registration succesful!!");

                    }catch (IOException e ){
                        System.out.println("Error saving user:  " + e.getMessage());

                    }
                }

            }else if (choice == 2) {
                System.out.println("Enter your username: ");
                String username = input.nextLine();
                System.out.println("Enter your password: ");
                String password = input.nextLine();
                boolean loginSuccess=false;
                try (Scanner fileScanner = new Scanner(new java.io.File("users.txt"))){
                    while (fileScanner.hasNextLine()) {
                        String line = fileScanner.nextLine();
                        String[] parts = line.split(",");
                        if(parts.length == 2 && parts[0].equals(username) && parts[1].equals(password)) {
                            loginSuccess = true;
                            break;
                        }
                    }

                }catch (IOException e) {
                    System.out.println("Could not read user file: " + e.getMessage());
                }
                if(loginSuccess) {
                    System.out.println("Login successful! Welcome, "+ username + " !");
                }else {
                    System.out.println("Login failed! Incorrect username or password.");
                }
            }else if (choice == 3) {
                System.out.println("Exiting...");
                break;
            }else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
