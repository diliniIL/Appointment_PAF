
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="javax.servlet.*"%>
<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.Appointment"%>

<!DOCTYPE htmlt>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Appointment Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.4.0.min.js"></script>
<script src="Components/appointment.js"></script>
</head>
<body>

	<div class="container">
		<div class="row">
			<div class="col-6">


				<h1>Appointment Management </h1>

				<form id="formAppointment" name="formAppointment" method="post" action="appointment.jsp">
					Appointment Date : <input id="appointmentDate" name="appointmentDate" type="text"
						class="form-control form-control-sm"> <br> 
					Appointment Time : <input id="appointmentTime" name="appointmentTime" type="text"
						class="form-control form-control-sm"> <br> 
					Appointment Doctor : <input id="appointmentDoctor" name="appointmentDoctor" type="text"
						class="form-control form-control-sm"> <br> 
					Appointment Hospital : <input id="appointmentHospital" name="appointmentHospital" type="text"
						class="form-control form-control-sm"> <br>
					
					 <input id="btnSave" name="btnSave" type="button" value="Save"
						class="btn btn-primary"> <input type="hidden"
						id="hidAppointmentIDSave" name="hidAppointmentIDSave" value="">
				</form>

				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>
				<br>
				<div id="divAppointmentGrid">
					<%
						Appointment appointmentObj = new Appointment();
								out.print(appointmentObj.readAppointment());
					%>
				</div>
</body>
</html>