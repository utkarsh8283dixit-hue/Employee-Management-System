package com.employee.management;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EmployeeDAO {

    // 1. Add Employee Method
    public static void addEmployee(int id, String name, String department, double salary) {
        String query = "INSERT INTO employees (id, name, department, salary) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.setString(2, name);
            statement.setString(3, department);
            statement.setDouble(4, salary);
            statement.executeUpdate();
            System.out.println("Employee successfully add ho gaya!");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // 2. Display Employees Method
    public static void displayEmployees() {
        String query = "SELECT * FROM employees";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            System.out.println("\n--- Employee List ---");
            boolean hasData = false;
            while (resultSet.next()) {
                hasData = true;
                System.out.println("ID: " + resultSet.getInt("id") +
                        ", Name: " + resultSet.getString("name") +
                        ", Department: " + resultSet.getString("department") +
                        ", Salary: " + resultSet.getDouble("salary"));
            }
            if (!hasData) {
                System.out.println("Koi employee nahi mila.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // 3. Search Employee Method
    public static void searchEmployee(int id) {
        String query = "SELECT * FROM employees WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                System.out.println("\n--- Employee Found ---");
                System.out.println("ID: " + resultSet.getInt("id"));
                System.out.println("Name: " + resultSet.getString("name"));
                System.out.println("Department: " + resultSet.getString("department"));
                System.out.println("Salary: " + resultSet.getDouble("salary"));
            } else {
                System.out.println("Is ID ka koi employee nahi mila.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // 4. Delete Employee Method
    public static void deleteEmployee(int id) {
        String query = "DELETE FROM employees WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Employee delete ho gaya.");
            } else {
                System.out.println("Is ID ka employee nahi mila.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // 5. Update Employee Method
    public static void updateEmployee(int id, String name, String department, double salary) {
        String query = """
                UPDATE employees 
                SET name = ?, department = ?, salary = ? 
                WHERE id = ?
                """;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.setString(2, department);
            statement.setDouble(3, salary);
            statement.setInt(4, id);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Employee update ho gaya.");
            } else {
                System.out.println("Employee nahi mila.");
            }
        } catch (SQLException e) {
            System.out.println("Update error: " + e.getMessage());
        }
    }

    // 6. Sort by Salary Method (Using ArrayList)
    public static void sortBySalary() {
        String query = "SELECT * FROM employees";
        List<Employee> employeeList = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                employeeList.add(new Employee(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("department"),
                        resultSet.getDouble("salary")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        if (employeeList.isEmpty()) {
            System.out.println("Sort karne ke liye koi employee data available nahi hai.");
            return;
        }

        employeeList.sort(Comparator.comparingDouble(Employee::getSalary));

        System.out.println("\n--- Employees Sorted by Salary ---");
        for (Employee emp : employeeList) {
            System.out.println("ID: " + emp.getId() +
                    ", Name: " + emp.getName() +
                    ", Department: " + emp.getDepartment() +
                    ", Salary: " + emp.getSalary());
        }
    }

    // 7. Export Employees to .txt File Method
    public static void exportEmployeesToFile() {
        String query = "SELECT * FROM employees";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
             java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.File("employees_backup.txt"))) {

            writer.println("--- Employee Management System Backup ---");
            writer.println("ID\tName\tDepartment\tSalary");
            writer.println("----------------------------------------");

            boolean hasData = false;
            while (resultSet.next()) {
                hasData = true;
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String department = resultSet.getString("department");
                double salary = resultSet.getDouble("salary");

                writer.println(id + "\t" + name + "\t" + department + "\t" + salary);
            }

            if (hasData) {
                System.out.println("Data successfully 'employees_backup.txt' file me save ho gaya hai!");
            } else {
                System.out.println("File me save karne ke liye koi data available nahi hai.");
            }

        } catch (Exception e) {
            System.out.println("File export error: " + e.getMessage());
        }
    }
}