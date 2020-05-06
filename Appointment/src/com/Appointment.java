package com;

import java.sql.Statement;

import javax.servlet.http.HttpServlet;

//import java.sql.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Appointment extends HttpServlet {

	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/health_care", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertAppointment(String date, String time, String doctor, String hospital) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into appointment(`appointmentID`,`appointmentDate`,`appointmentTime`,`appointmentDoctor`,`appointmentHospital`)"
					+ " values (?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, date);
			preparedStmt.setString(3, time);
			preparedStmt.setString(4, doctor);
			preparedStmt.setString(5, hospital);
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newAppointment = readAppointment();
			 output = "{\"status\":\"success\", \"data\": \"" +newAppointment + "\"}"; 
			 
			//output = "Inserted successfully";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while inserting the appointment.\"}";
			//output = "Error while inserting the appointment.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readAppointment() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Appointment Date</th><th>Appointment Time</th><th>Appointment Doctor</th><th>Appointment Hospital</th><th>Update</th><th>Remove</th></tr>";
			String query = "select * from appointment";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String appointmentID = Integer.toString(rs.getInt("appointmentID"));
				String appointmentDate = rs.getString("appointmentDate");
				String appointmentTime = rs.getString("appointmentTime");
				String appointmentDoctor = rs.getString("appointmentDoctor");
				String appointmentHospital = rs.getString("appointmentHospital");
				// Add into the html table
				output += "<tr><td><input id='hidAppointmentIDUpdate' name='hidAppointmentIDUpdate' type='hidden' value='" + appointmentID + "'>" + appointmentDate + "</td>";
				
				/*output += "<tr><td><input id=\"hidAppointmentIDUpdate\"name=\"hidAppointmentIDUpdate\"type=\"hidden\" value=\""
						+ appointmentID + "\">" + appointmentDate + "</td>";*/
				output += "<td>" + appointmentTime + "</td>";
				output += "<td>" + appointmentDoctor + "</td>";
				output += "<td>" + appointmentHospital + "</td>";
				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td><td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-appointmentid='"
						 + appointmentID + "'>" + "</td></tr>";
				
				
				
				
				/*output += "<td><input name=\"btnUpdate\"type=\"button\" value=\"Update\"class=\" btnUpdate btn btn-secondary\"></td>"
						+ "<td><form method=\"post\" action=\"appointment.jsp\"><input name=\"btnRemove\" type=\"submit\"value=\"Remove\" class=\"btn btn-danger\"><input name=\"hidAppointmentIDDelete\" type=\"hidden\"value=\""
						+ appointmentID + "\">" + "</form></td></tr>";*/
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the appointment.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateAppointment(String ID, String date, String time, String doctor, String hospital) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE appointment SET appointmentDate=?,appointmentTime=?,appointmentDoctor=?,appointmentHospital=?WHERE appointmentID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, date);
			preparedStmt.setString(2, time);
			preparedStmt.setString(3, doctor);
			preparedStmt.setString(4, hospital);
			preparedStmt.setInt(5, Integer.parseInt(ID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			//output = "Updated successfully";
			String newAppointment = readAppointment();
			 output = "{\"status\":\"success\", \"data\": \"" +newAppointment + "\"}";
			
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while updating the appointment.\"}";
			//output = "Error while updating the appointment.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deleteAppointment(String appointmentID) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from appointment where appointmentID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(appointmentID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			//output = "Deleted successfully";
			String newAppointment = readAppointment();
			 output = "{\"status\":\"success\", \"data\": \"" +newAppointment + "\"}";
			
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while deleting the appointment.\"}";
			//output = "Error while deleting the item.";
			System.err.println(e.getMessage());
		}
		return output;
	}
}
