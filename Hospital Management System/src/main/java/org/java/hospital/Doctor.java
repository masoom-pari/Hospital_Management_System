package org.java.hospital;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.util.Scanner;

public class Doctor {

    private final Connection conection;


    public  Doctor(Connection conection ){
        this.conection = conection;

    }

//    public void addPatient(){
//        System.out.println("Enter Patient Name");
//        String name = scanner.nextLine();
//        System.out.println("Enter Patient Age");
//        int age = scanner.nextInt();
//        System.out.println("Enter Patient Gender");
//        String gender = scanner.next();
//
//        try{
//            String query ="INSERT INTO patients(name, age , gender) VALUES (? , ? ,?)";
//            PreparedStatement preparedStatement = this.conection.prepareStatement(query);
//            preparedStatement.setString(1,name);
//            preparedStatement.setInt(2,age);
//            preparedStatement.setString(3,gender);
//            int affectedRows = preparedStatement.executeUpdate();
//            if(affectedRows > 0){
//                System.out.println("Patient has been added succesfully");
//
//            }else {
//                System.out.println("Patient has been added failed");
//            }
//        }catch(SQLException e){
//            e.printStackTrace();
//        }
//    }
    public void viewDoctors(){


        String query = "SELECT * FROM doctors";
        try {
            PreparedStatement preparedStatement = this.conection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("doctors:");

            System.out.println("+------------+---------------------+------------------------+");
            System.out.println("| doctor Id  |Name                 | specialization         |");
            System.out.println("+------------+---------------------+------------------------+");
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String specialization = resultSet.getString("specialization");
                System.out.printf("| %-13s| %22s| %25s |\n "  ,id,name,specialization);
                System.out.println("+------------+---------------------+------------------------+");


            }

        }catch(SQLException e){
            e.printStackTrace();
        }



    }
    public boolean getDoctorById(int id){
        String query = "SELECT * FROM doctors WHERE id = ?";
        try {
            PreparedStatement preparedStatement = this.conection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) return true;
            else return false;
        }catch(SQLException e){
                e.printStackTrace();
            }
            return false;

    }
}
