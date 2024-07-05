package HotelManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem {
    private static final String url = "jdbc:mysql://localhost:3306/hospital_management";

    private static final String username = "root";

    private static final String password = "Vish@1234";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Patient patient = new Patient(connection, scanner);
            Doctor doctor = new Doctor(connection);
            while (true) {
                System.out.println("HOSPITAL MANAGEMENT SYSTEM");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patient");
                System.out.println("3. View Doctor");
                System.out.println("4. Book Appointment");
                System.out.println("5. Exit");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        //Add patient
                        patient.addPatient();
                        System.out.println();
                        break;
                    case 2:
                        //View Patient
                        patient.viewPatient();
                        System.out.println();
                        break;
                    case 3:
                        //View Doctor
                        doctor.viewDoctor();
                        System.out.println();
                        break;
                    case 4:
                        //book Appointment
                        bookAppointment(patient,doctor,connection,scanner);
                        System.out.println();
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Enter valid choice");
                        break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void bookAppointment(Patient patient, Doctor doctor, Connection connection, Scanner scanner) {
        System.out.println("Enter Patient ID : ");
        int patientId = scanner.nextInt();
        System.out.println("Enter Doctor ID : ");
        int docId = scanner.nextInt();
        System.out.println("Enter appointment date (yyyy-mm-dd) : ");
        String appDate = scanner.next();
        if (patient.getPatientById(patientId) && doctor.getDoctorById(docId)) {
            if (checkDocAvailability(docId, appDate, connection)) {
                String appointmentQuery = "INSERT INTO appointment( patId, docId, appDate ) VALUES( ?, ?, ? )";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
                    preparedStatement.setInt(1, patientId);
                    preparedStatement.setInt(2, docId);
                    preparedStatement.setString(3, appDate);
                    int rowAffected = preparedStatement.executeUpdate();
                    if (rowAffected > 0) {
                        System.out.println("Appointment booked");
                    } else {
                        System.out.println("Failed to book Appointment ");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Doctor is not available");
            }
        } else {
            System.out.println("Either doctor or patients id is not valid");
        }
    }

     public static boolean checkDocAvailability(int docId, String appDate, Connection connection)
    {
        String query = "SELECT COUNT(*) FROM appointment WHERE docId = ? AND appDate = ? ";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,docId);
            preparedStatement.setString(2,appDate);
            ResultSet resultset = preparedStatement.executeQuery();
            if(resultset.next()){
                int count = resultset.getInt(1);
                if(count == 0){
                    return true;
                }else{
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}