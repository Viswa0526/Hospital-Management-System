package HotelManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Doctor {

        private Connection connection;

        public Doctor(Connection connection) {
            this.connection = connection;
        }

        public void viewDoctor(){
            String query = "SELECT * FROM doctor";
            try{
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
                System.out.println("Doctors : ");
                System.out.println("+-------------+-------------------+---------------+");
                System.out.println("| Doctor's ID | Name              | Department    |");
                System.out.println("+-------------+-------------------+---------------+");
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getNString("doc_name");
                    String department = resultSet.getString("department");
                    System.out.printf("| %-11s | %-17s | %-13s |\n", id, name, department);
                    System.out.println("+-------------+-------------------+---------------+");
                }
            }catch(SQLException e){
                e.getStackTrace();
            }
        }

        public boolean getDoctorById(int id){
            String query = "SELECT * FROM doctor WHERE id = ?";
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


