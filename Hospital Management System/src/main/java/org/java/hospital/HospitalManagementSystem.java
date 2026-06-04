package org.java.hospital;

import java.sql.*;
import java.util.Scanner;

//import static java.lang.Class.forName;

public class HospitalManagementSystem {
    private static final String url = "jdbc:mysql://localhost:3306/hospital";

    private static final String userName = "root";
    private static final String password = "Pari143#";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        try{
            Connection connection = DriverManager.getConnection(url,userName,password);
            Patient patient = new Patient(connection, scanner);
            Doctor doctor = new Doctor(connection );
//            int choice=0;
            while(true){
                System.out.println("HOSPITAL MANAGEMENT SYSTEM");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patients");
                System.out.println("3. View Doctors");
                System.out.println("4. View Book Appointment");
                System.out.println("5. Exit");
                System.out.println("Enter your choice:");
//                String input = scanner.nextLine().trim();
//                int choice =0;
//                if(!input.isEmpty()){
//                    try{
//                        choice = Integer.parseInt(input);
//                        break;
//                    }catch(NumberFormatException e){
//                        System.out.println("Please enter a valid choice");
//                    }
//                }
                int choice =Integer.parseInt( scanner.nextLine());
//                scanner.nextLine();
                switch (choice){
                    case 1 :
//                        Add Patient
                        patient.addPatient();
                        System.out.println();
                        break;
                      case 2:
//                          View Patients
                          patient.viewPatients();
                          System.out.println();
                          break;


                        case 3:
//                            View Doctors
                            doctor.viewDoctors();
                            System.out.println();
                            break;

                            case 4:
//                                View Book Appointment
                                bookAppointment(patient , doctor , connection , scanner);
                                System.out.println();
                                break;

                                case 5:
                                    return;
                                    default:
                                        System.out.println("Invalid choice");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public static void bookAppointment(Patient patient , Doctor doctor ,Connection connection , Scanner scanner){
        System.out.println("Enter Patient  Id");
        int patient_Id = scanner.nextInt();
        System.out.println("Enter Doctor Id");
        int doctor_Id = scanner.nextInt();
        System.out.println("Enter Appointment Date (yyyy-mm-dd):");
        String appointmentDate = scanner.next();
        if(patient.getPatientById(patient_Id) && doctor.getDoctorById(doctor_Id) ){
            if(checkDoctorAvailability(doctor_Id ,appointmentDate , connection)){
                String appointmentquery = "INSERT INTO appointments(patient_id, doctor_id , appointment_date )VALUES (?,?,?)";
                try{
                    PreparedStatement preparedStatement = connection.prepareStatement(appointmentquery);
                     preparedStatement.setInt(1,patient_Id);
                     preparedStatement.setInt(2,doctor_Id);
                     preparedStatement.setString(3,appointmentDate);
                     int rowsAffected = preparedStatement.executeUpdate();
                     if(rowsAffected>0){
                         System.out.println("Appointment has been added successfully");

                     }else{
                         System.out.println("Failed to add appointment");
                     }

                }catch (SQLException e){
                    e.printStackTrace();

                }

            }else {
                System.out.println("Doctor is not  on this Date");
            }
            }else {

                    System.out.println("Enter doctor or patient doesnt exist !!");
            }


    }
    public static boolean checkDoctorAvailability(int doctorId , String appointmentDate , Connection connection){
        String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date = ?";
        try{PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,doctorId);
            preparedStatement.setString(2,appointmentDate);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                int count = resultSet.getInt(1);
                return count == 0;

            }


        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
