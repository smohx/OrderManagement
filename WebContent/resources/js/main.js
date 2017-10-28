
$(document).ready(function(){
	if($('#sourcefile').length > 0 && $('#sourcefile').get(0) != undefined && $('#sourcefile').get(0).files != undefined && $('#sourcefile').get(0).files.length > 0)

	{
		var fileName1 = $('#sourcefile').get(0).files[0].name;
	}
	if($('#targetfile').length > 0 && $('#targetfile').get(0) != undefined && $('#targetfile').get(0).files != undefined && $('#targetfile').get(0).files.length > 0)
	{
		var fileName2 = $('#targetfile').get(0).files[0].name;
	}
	if (fileName1 != undefined){
		$('#sourcetext').text(fileName1);
	}
	if (fileName2 != undefined){
		$('#targettext').text(fileName2);
	}
	$('#sourcefile').change(function (e) {
		fileName1 = e.target.files[0].name;
		$('#sourcetext').text(fileName1);
	});
	$('#targetfile').change(function (e) {
		fileName2 = e.target.files[0].name;
		$('#targettext').text(fileName2);
	});
});
