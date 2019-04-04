$(document).ready(function() {

	$("#a_add_role").click(function(event) {
		event.preventDefault();

		$("#add_role_modal").modal();
	
	});
	
	$("#add_role_modal #modalOkButton").click(function(event) {
		event.preventDefault();

		// get selected option
		var role = $('#selectrole_id').find(":selected").val();
		var roleText = $('#selectrole_id').find(":selected").text();
		
		
		//get user role
		var found = 0;
		var userRoles = [];
		$('#roleList span span span').each(function(){
		    var $span = $(this);
		    var roleFound = $span.text()
		    userRoles.push(roleFound);
		    if(roleFound==roleText){
		    	found++;
		    }
		    
		});
		if(found > 0){
			alert("This role already added. Please choose another one.");
		}else{
			// update input new_roles
			//var newRoles = $("#new_roles_id").val();
			//$("#new_roles_id").val(newRoles+""+role+",");
			
			
			
			$( "#roleList" ).append( "<span><span class=\"role_body btn btn-default\" > <span class=\"role_value\" in_role_value=\""+role+"\">"+roleText+"</span> <a class=\"delete_role btn btn-danger btn-xs\" href=\"#\"><span class=\"glyphicon glyphicon-remove-sign\">Delete</span></a></span></span>" );
			
			updateInputRoles();
			
			
			$('#add_role_modal').modal('toggle');
		}
		
	
	});
	
	
	
	$('#roleList').on('click',"a.delete_role",function(event) {
		event.preventDefault();
		
	//	var deletedRole = $("#deleted_role_id").val();
		
		//console.log($(this).parent(".role_body").children("span").text()+"...");
	
		//$("#deleted_role_id").val(deletedRole+""+$(this).parent(".role_body").children("span").text()+",");

		
		
		$(this).parent(".role_body").remove();
		
		updateInputRoles();
		
		
    });
	
	function updateInputRoles(){
		// update roles input
		var userRoles = [];
		$('#roleList span span span.role_value').each(function(){
		    var $span = $(this);
		    var roleFound = $span.attr("in_role_value");
		    userRoles.push(roleFound);
		    
		});
		
		$("#roles_id").val(userRoles.join(","));
		console.log($("#roles_id").val());
		// end update roles input
	}
});