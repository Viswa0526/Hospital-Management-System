package HotelManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {

    private Connection connection;
    private Scanner scanner;

    public Patient(Connection connection, Scanner scanner){
        this.connection = connection;
        this.scanner = scanner;
    }

    public void addPatient(){
        System.out.print("Enter patients name : ");
        String name = scanner.next();
        System.out.print("Enter patients age : ");
        int age = scanner.nextInt();
        System.out.print("Enter Patient gender : ");
        String gender = scanner.next();

        try {
            String query = " INSERT INTO patient( pat_name, age, gender ) VALUES ( ? ,? ,? ) ";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,name);
            preparedStatement.setInt(2,age);
            preparedStatement.setString(3,gender);
            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows > 0){
                System.out.println("Patient added successfully");
            }
            else {
                System.out.println("Failed to add patient");
            }
        }
        catch (SQLException e){
            e.getStackTrace();
        }
    }

    public void viewPatient(){
        String query = " SELECT * FROM patient ";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Pateints : ");
            System.out.println("+-------------+-------------------+--------+------------+");
            System.out.println("| Patients ID | Name              |  Age   | Gender     |");
            System.out.println("+-------------+-------------------+--------+------------+");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("pat_name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                System.out.printf("| %-12s | %-16s | %-6s | %-10s |\n", id, name, age, gender);
                System.out.println("+-------------+-------------------+--------+------------+");
            }
        }catch(SQLException e){
            e.getStackTrace();
        }
    }

    public boolean getPatientById(int id){
        String query = " SELECT * FROM patient WHERE id = ? ";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return true;
            }else {
                return false;
            }
        }catch(SQLException e){
            e.getStackTrace();
        }
        return  false;
    }

}
