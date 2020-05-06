$(document).ready(function() {
	$("#alertSuccess").hide(); 
	$("#alertError").hide();
});


// SAVE ============================================
$(document).on("click", "#btnSave", function(event) {
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();

	// Form validation-------------------
	var status = validateAppointmentForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	// If valid------------------------
	var type = ($("#hidAppointmentIDSave").val() == "") ? "POST" : "PUT";

	$("#alertSuccess").text("Successfully saved.");
	$("#alertSuccess").show();	
	
	$.ajax({
		url : "AppointmentAPI",
		type : type,
		data : $("#formAppointment").serialize(),
		dataType : "text",
		complete : function(response, status) {
			onAppointmentSaveComplete(response.responseText, status);
			
		}
	
	});
	
});

function onAppointmentSaveComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divAppointmentsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
	$("#hidAppointmentIDSave").val("");
	$("#formAppointment")[0].reset();
}

//Remove
$(document).on("click", ".btnRemove", function(event) {
	$.ajax({
		url : "AppointmentAPI",
		type : "DELETE",
		data : "appointmentID=" + $(this).data("appointmentid"),
		dataType : "text",
		complete : function(response, status) {
			onAppointmentDeleteComplete(response.responseText, status);
		}
	});
	/*$("#alertSuccess").text("Successfully deleted.");
	$("#alertSuccess").show();*/
});

//DELETE==========================================
function onAppointmentDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divAppointmentsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
		
	}
}

// UPDATE==========================================
$(document).on(
		"click",
		".btnUpdate",
		function(event) {
			$("#hidAppointmentIDSave").val(
					$(this).closest("tr").find('#hidAppointmentIDUpdate').val());
			$("#appointmentDate").val($(this).closest("tr").find('td:eq(0)').text());
			$("#appointmentTime").val($(this).closest("tr").find('td:eq(1)').text());
			$("#appointmentDoctor").val($(this).closest("tr").find('td:eq(2)').text());
			$("#appointmentHospital").val($(this).closest("tr").find('td:eq(3)').text());
			
		}
		
			
);



// CLIENTMODEL=========================================================================
function validateAppointmentForm() {
	// Date
	if ($("#appointmentDate").val().trim() == "") {
		return "Insert Appointment Date.";
	}
	// Time
	if ($("#appointmentTime").val().trim() == "") {
		return "Insert Appointment Time.";
	}
	// Doctor-------------------------------
	if ($("#appointmentDoctor").val().trim() == "") {
		return "Insert Appointment Doctor.";
	}
	/*// is numerical value
	var tmpPrice = $("#itemPrice").val().trim();
	if (!$.isNumeric(tmpPrice)) {
		return "Insert a numerical value for Item Price.";
	}
	// convert to decimal price
	$("#itemPrice").val(parseFloat(tmpPrice).toFixed(2));*/
	
	// Hospital------------------------
	if ($("#appointmentHospital").val().trim() == "") {
		return "Insert Appointment Hospital.";
	}
	return true;
}
