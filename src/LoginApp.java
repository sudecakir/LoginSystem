import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class LoginApp {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Login System ---\n");
            System.out.print("1-Register\n2-Login\n3-Exit\n4-Delete a user\n ");
            System.out.println("Enter your choice: ");
            int choice = input.nextInt();
            input.nextLine();

            if (choice == 1) {
                System.out.println("Enter a username: ");
                String username = input.nextLine();
                System.out.println("Enter a password: ");
                String password = input.nextLine();
                if(password.length() < 6|| !password.matches(".*[a-zA-Z].*") || !password.matches(".*\\d.*")) {
                    System.out.println("Password must be at lesast 6 characters and long and contain at least one letter and one number.");
                    continue;
                }
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
            }else if (choice == 4) {
                System.out.println("Enter the username to delete: ");
                String usernameToDelete = input.nextLine();
                boolean userFound=false;
                ArrayList<String> updatedUsers = new ArrayList<>();
                try {
                    BufferedReader reader = new BufferedReader(new FileReader("users.txt"));
                    String line;
                    while ((line= reader.readLine()) != null){
                        String[] parts = line.split(",");
                        if(parts.length >= 1 && parts[0].trim().equalsIgnoreCase(usernameToDelete.trim())) {
                            userFound = true;
                            continue;
                        }
                        updatedUsers.add(line);
                    }
                    reader.close();

                }catch (IOException e) {
                    System.out.println("Could not read user file: " + e.getMessage());
                    return;
                }
                if(userFound) {
                    try {
                        FileWriter writer=new FileWriter("users.txt",false);
                        for (String user : updatedUsers) {
                            writer.write(user+"\n");
                        }
                        writer.close();
                        System.out.println("User successfully deleted!");
                    }catch (IOException e) {
                        System.out.println("Error writing to users file:  " + e.getMessage());
                    }
                }else{
                    System.out.println("User not found!");
                }
            }



            else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
