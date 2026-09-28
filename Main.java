package com.employee.management;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Employee Management System ---");
            System.out.println("1. Add Employee");
            System.out.println("2. Display Employees");
            System.out.println("3. Search Employee");
            System.out.println("4. Delete Employee");
            System.out.println("5. Sort by Salary");
            System.out.println("6. Update Employee");
            System.out.println("7. Export to .txt File");
            System.out.println("8. Exit");
            System.out.print("Choice enter kijiye: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    addEmployee(scanner);
                    break;
                case 2:
                    EmployeeDAO.displayEmployees();
                    break;
                case 3:
                    searchEmployee(scanner);
                    break;
                case 4:
                    deleteEmployee(scanner);
                    break;
                case 5:
                    EmployeeDAO.sortBySalary();
                    break;
                case 6:
                    updateEmployee(scanner);
                    break;
                case 7:
                    EmployeeDAO.exportEmployeesToFile();
                    break;
                case 8:
                    System.out.println("Program exited.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice! Dobara try kijiye.");
            }
        }
    }

    private static void addEmployee(Scanner scanner) {
        try {
            System.out.print("Enter ID: ");
            int id = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter Name: ");
            String name = scanner.nextLine();
            if (name.trim().isEmpty()) {
                System.out.println("Name cannot be empty.");
                return;
            }
            System.out.print("Enter Department: ");
            String department = scanner.nextLine();
            if (department.trim().isEmpty()) {
                System.out.println("Department cannot be empty.");
                return;
            }
            System.out.print("Enter Salary: ");
            double salary = Double.parseDouble(scanner.nextLine());
            if (salary < 0) {
                System.out.println("Salary cannot be negative.");
                return;
            }

            EmployeeDAO.addEmployee(id, name, department, salary);
        } catch (NumberFormatException e) {
            System.out.println("ID aur salary numeric hone chahiye.");
        }
    }

    private static void searchEmployee(Scanner scanner) {
        try {
            System.out.print("Search karne ke liye ID enter kijiye: ");
            int id = Integer.parseInt(scanner.nextLine());
            EmployeeDAO.searchEmployee(id);
        } catch (NumberFormatException e) {
            System.out.println("ID numeric honi chahiye.");
        }
    }

    private static void deleteEmployee(Scanner scanner) {
        try {
            System.out.print("Delete karne ke liye ID enter kijiye: ");
            int id = Integer.parseInt(scanner.nextLine());
            EmployeeDAO.deleteEmployee(id);
        } catch (NumberFormatException e) {
            System.out.println("ID numeric honi chahiye.");
        }
    }

    private static void updateEmployee(Scanner scanner) {
        try {
            System.out.print("Update karne ke liye ID enter kijiye: ");
            int id = Integer.parseInt(scanner.nextLine());
            System.out.print("New name enter kijiye: ");
            String name = scanner.nextLine();
            if (name.trim().isEmpty()) {
                System.out.println("Name empty nahi ho sakta.");
                return;
            }
            System.out.print("New department enter kijiye: ");
            String department = scanner.nextLine();
            if (department.trim().isEmpty()) {
                System.out.println("Department empty nahi ho sakta.");
                return;
            }
            System.out.print("New salary enter kijiye: ");
            double salary = Double.parseDouble(scanner.nextLine());
            if (salary < 0) {
                System.out.println("Salary negative nahi ho sakti.");
                return;
            }

            EmployeeDAO.updateEmployee(id, name, department, salary);
        } catch (NumberFormatException e) {
            System.out.println("ID aur salary numeric hone chahiye.");
        }
    }
}