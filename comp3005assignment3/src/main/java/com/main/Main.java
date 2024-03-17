package com.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Scanner;

public class Main {

    static final String url = "jdbc:postgresql://localhost:5432/COMP3005_Assignment3-Q1";
    static final String user = "postgres";
    static final String password = "student";

    static Connection connection;

    /*
     * Get all students
     */
    public static void getAllStudents(){

        try {

            java.sql.Statement statement = connection.createStatement(); // Create a statement
            statement.executeQuery("SELECT * FROM students"); // Execute the query
            ResultSet result = statement.getResultSet(); // Get the result set
            while (result.next()) { // Iterate through the result set
                System.out.print(result.getInt("student_id") + "\t");
                System.out.print(result.getString("first_name") + "\t");
                System.out.print(result.getString("last_name") + "\t");
                System.out.print(result.getString("email") + "\t");
                System.out.print(result.getDate("enrollment_date") + "\n");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * Add a student
     */
    public static void addStudent(String fName, String lName, String email, String date) {
        try {
            java.sql.Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES ('" + fName + "', '" + lName + "', '" + email + "', '" + date + "')");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    /*
     * Helper function to add a student
     */
    public static void addStudentHelper(){
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Enter the student's first name: ");
        String fName = scanner.nextLine(); // Get the student's first name
        System.out.println("Enter the student's last name: ");
        String lName = scanner.nextLine(); // Get the student's last name
        System.out.println("Enter the student's email: ");
        String email = scanner.nextLine(); // Get the student's email
        System.out.println("Enter the student's enrollment date (YYYY-MM-DD): ");
        String date = scanner.nextLine(); // Get the student's enrollment date
        
        addStudent(fName, lName, email, date); // Add the student
    }

    /*
     * Update a student's email
     */
    public static void updateStudentEmail(int studentID, String newEmail){
        try {
            java.sql.Statement statement = connection.createStatement(); // Create a statement
            statement.executeUpdate("UPDATE students SET email = '" + newEmail + "' WHERE student_id = " + studentID); // Execute the update query
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * Helper function to update a student's email
     */
    public static void updateStudentEmailHelper(){
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Enter the student's ID: ");
        int studentID = scanner.nextInt(); // Get the student ID
        scanner.nextLine();
        System.out.println("Enter the student's new email: ");
        String newEmail = scanner.nextLine(); // Get the new email
        
        updateStudentEmail(studentID, newEmail); // Update the student's email
    }

    /*
     * Delete a student
     */
    public static void deleteStudent(int studentID){
        try {
            java.sql.Statement statement = connection.createStatement(); // Create a statement
            statement.executeUpdate("DELETE FROM students WHERE student_id = " + studentID); // Execute the delete query
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * Helper function to delete a student
     */
    public static void deleteStudentHelper(){ 
        Scanner  scanner = new Scanner(System.in);
        
        System.out.println("Enter the student's ID: ");
        int studentID = scanner.nextInt(); // Get the student ID
        
        deleteStudent(studentID); // Delete the student
    }


    /*
     * Run the main program loop
     */
    public static void run(){
        int choice = 0;
        Scanner scanner = new Scanner(System.in);
        while (choice != 5) {
            System.out.println("\n");

            System.out.println("    1. Get all students");
            System.out.println("    2. Add a student");
            System.out.println("    3. Update a student's email");
            System.out.println("    4. Delete a student");
            System.out.println("    5. Exit");
            System.out.println("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            System.out.println("\n");

            switch (choice) {
                case 1:
                    getAllStudents();
                    break;
                case 2:
                    addStudentHelper();
                    break;
                case 3:
                    updateStudentEmailHelper();
                    break;
                case 4:
                    deleteStudentHelper();
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    public static void main(String[] args) {
        try {
            connection = DriverManager.getConnection(url, user, password); // Connect to the database
            Class.forName("org.postgresql.Driver");

            if (connection != null) { // If the connection is successful
                System.out.println("Connected to PostgreSQL successfully!\n\n");

                run(); // Run the main program
            }
            else {
                System.out.println("Failed to establish connection.");
            }

            connection.close(); // Close the connection
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
