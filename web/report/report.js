/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


setTimeout(function () {
    $.fn.dragDrop();
    $.fn.sort();
    $.fn.sortInner();
}, 0);


$.fn.dragDrop = function () {
    $(".draggable").draggable({
        revert: "invalid",
        drag: function (event, ui) {
            ui.helper.addClass("draggable");
        },
        stop: function (event, ui) {
            ui.helper.removeClass("draggable");
        }
    });
    $(".droppable").droppable({
        accept: ":not(.ui-sortable-helper)",
        drop: function (event, ui) {
            ui.draggable.addClass("dropped");
            $(this).append($(ui.draggable).clone());

            var chart_type = ui.draggable.find("img").attr("alt");
            var check = $(this).attr('id');
            var checkWidth = $('#' + check).width();

            var modal = "modal_" + check;
            var modalID = '#' + modal;
            $(".modal").attr("id", modal);
            $(modalID).modal();

            $(modalID).find('input[type="text"]').val("");
            var btn = $(modalID).find('.btnSubmit');
            var btn = "btn_" + check;
            var btnID = '#' + btn;
            $(".btnSubmit").attr("id", btn);
            //$(btnID).unbind().click( function () {
            $('body').one('click', btnID, function () { 
                var height = $(modalID).find('#height').val();
                var title = $(modalID).find('#title').val();
                url = 'report/world-population-density.json';
                ajax_data('GET', url, function (data)
                {
                    charts(data, chart_type + "-" + check, title, height, checkWidth);
                });               
            });
            
            $('#' + check).next('.chartEdit').remove();
            $('#' + check).parent('div').append('<div class="chartEdit"><i class="fa fa-pencil mr15 md-24 editGraph"></i><i class="fa fa-trash deleteGraph"></i></div>');            
        }
    });

};

//sort
$.fn.sort = function () {  
    $("#misTemp_custom").sortable({
        connectWith: '.template',
	handle: '.fa-arrows',
        tolerance: 'pointer',
        revert: 'invalid',
        placeholder: 'placeholder',
        forceHelperSize: true,        
    }).disableSelection();    
};
$.fn.sortInner = function () {  
    $(".temp_custom_row").sortable({
        cancel: ".rowEdit, .col-xs-12",
        connectWith: '.misTemp',
	//handle: '.fa-arrows',
        tolerance: 'pointer',
        revert: 'invalid',
        //placeholder: 'placeholder',
        forceHelperSize: true,  
    }).disableSelection();
};
//edit    
$('body').on('click', '.editGraph', function () {
    var parentGraphDiv = $(this).parent('div').prev().attr('id');
    var editmodal = "modal_" + parentGraphDiv;
    var editmodalID = '#' + editmodal;
    $(".modal").attr("id", editmodal);
    $(editmodalID).modal();
    $(editmodalID).find('input[type="text"]').val("");
    var height = $(editmodalID).find('#height').val();
    var title = $(editmodalID).find('#title').val();

    var btnE = $(editmodalID).find('.btnSubmit');
    var btnE = "btn_" + parentGraphDiv;
    var btnEID = '#' + btnE;
    $(".btnSubmit").attr("id", btnE);
    $('body').on('click', btnEID, function () {
        url = 'report/world-population-density.json';
        ajax_data('GET', url, function (data)
        {
            charts(data, chart_type + "-" + check, title, height, checkWidth);
        });
        
    });
});

//delete
 
$('body').on('click', '.deleteGraph', function () {
    var parentGraphDiv = $(this).parent('div').prev().attr('id');
    $('#' + parentGraphDiv).empty();
    $('#' + parentGraphDiv).next('.chartEdit').remove();
    
});

//preview
$('body').on('click', '#preview', function () {    
    var currentrowID = $(this).parents('.card-body').find('.template').attr('id');
    
    if (currentrowID == "misTemp_custom") {    
        $(this).parents('.card-body').find('.template').find('.misTemp').css('border', '1px solid #EFEFEF');
        $('.chartEdit').css('display', 'none');
        $('.draggable').draggable('disable');
        $(".droppable").droppable('disable');
        //$( ".temp_custom_row" ).sortable('disable');
        $( ".temp_custom_row" ).sortable({ 
            cancel: ".disable-sort" 
        });
        $(this).parents('.card-body').find('.template').find('.row').removeClass('temp_custom_row');
        $('.rowEdit').css('display', 'none');
        //$(".temp_custom_row").sortable('disable');
    }
    else{       
        $(this).parents('.template').find('.misTemp').css('border', '1px solid #EFEFEF');
        $('.chartEdit').css('display', 'none');
        $('.draggable').draggable('disable');
        $('.droppable').droppable('disable');
    }
});
//code
$('body').on('click', '#code', function () {    
    var currentrowID = $(this).parents('.card-body').find('.template').attr('id');

    if (currentrowID == "misTemp_custom") {
        $(this).parents('.card-body').find('.template').find('.misTemp').css('border', '1px dashed #c2c2c2');
        $('.chartEdit').css('display', 'block');
        $('.draggable').draggable('enable');
        $('.droppable').droppable('enable');        
        $(this).parents('.card-body').find('.template').find('.row').addClass('temp_custom_row');
        $(".temp_custom_row").sortable('enable');
        $('.rowEdit').css('display', 'block');
    }
    else{
        $(this).parents('.template').find('.misTemp').css('border', '1px dashed #c2c2c2');
        $('.chartEdit').css('display', 'block');
        $('.draggable').draggable('enable');
        $('.droppable').droppable('enable');
    }
});


//save
//$('body').on('click','#save', function (){ 
//    var currentrowID = $(this).parents('.card-body').find('.template').attr('id');
//    
//    if (currentrowID == "misTemp_custom") {    
//        $(this).parents('.card-body').find('.template').find('.misTemp').css('border', '1px solid #EFEFEF');
//        $('.chartEdit').css('display', 'none');
//        $('.draggable').draggable('disable');
//        $(".droppable").droppable('disable');
//        $( ".temp_custom_row" ).sortable('disable');
//        $(this).parents('.card-body').find('.template').find('.row').removeClass('temp_custom_row');
//        $('.rowEdit').css('display', 'none');
//        //$(".temp_custom_row").sortable('disable');
//        
//        var textToWrite = $(this).parents('.card-body').find('.template').html();
//	var textFileAsBlob = new Blob([textToWrite], {type:'text/plain'});
//	var fileNameToSaveAs = 'template.txt';
//
//	var downloadLink = document.createElement("a");
//	downloadLink.download = fileNameToSaveAs;
//	downloadLink.innerHTML = "Download File";
//	if (window.webkitURL != null)
//	{
//		// Chrome allows the link to be clicked
//		// without actually adding it to the DOM.
//		downloadLink.href = window.webkitURL.createObjectURL(textFileAsBlob);
//	}
//	else
//	{
//		// Firefox requires the link to be added to the DOM
//		// before it can be clicked.
//		downloadLink.href = window.URL.createObjectURL(textFileAsBlob);
//		//downloadLink.onclick = destroyClickedElement;
//		downloadLink.style.display = "none";
//		document.body.appendChild(downloadLink);
//	}
//
//	downloadLink.click();    
//    }
//    else{       
//        $(this).parents('.template').find('.misTemp').css('border', '1px solid #EFEFEF');
//        $('.chartEdit').css('display', 'none');
//        $('.draggable').draggable('disable');
//        $('.droppable').droppable('disable');
//        var textToWrite = $(this).parents('.card-body').find('.template').html();
//	var textFileAsBlob = new Blob([textToWrite], {type:'text/plain'});
//	var fileNameToSaveAs = 'template.txt';
//
//	var downloadLink = document.createElement("a");
//	downloadLink.download = fileNameToSaveAs;
//	downloadLink.innerHTML = "Download File";
//	if (window.webkitURL != null)
//	{
//		// Chrome allows the link to be clicked
//		// without actually adding it to the DOM.
//		downloadLink.href = window.webkitURL.createObjectURL(textFileAsBlob);
//	}
//	else
//	{
//		// Firefox requires the link to be added to the DOM
//		// before it can be clicked.
//		downloadLink.href = window.URL.createObjectURL(textFileAsBlob);
//		//downloadLink.onclick = destroyClickedElement;
//		downloadLink.style.display = "none";
//		document.body.appendChild(downloadLink);
//	}
//
//	downloadLink.click(); 
//    }
//});




//chart
function charts(data, ChartType, title, height, checkWidth)
{
    var c = ChartType;
    var jsonData = data;


    google.load("visualization", "1", {packages: ['corechart','table'], callback: drawVisualization});
    function drawVisualization()
    {
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Country');
        data.addColumn('number', 'Population Density');
        $.each(jsonData, function (i, jsonData)
        {
            var value = jsonData.value;
            var name = jsonData.name;
            data.addRows([[name, value]]);
        });

        var res = c.split("-");

        if (res[0] == "DonutChart") {
            var options = {
                height: height,
                width: checkWidth,
                title: title,
                colorAxis: {colors: ['#54C492', '#cc0000']},
                datalessRegionColor: '#dedede',
                defaultColor: '#dedede',
                pieHole: 0.4
            };
        }
        else if (res[0] == "StackChart") {
            var data = google.visualization.arrayToDataTable([
                ['Genre', 'Fantasy & Sci Fi', 'Romance', 'Mystery/Crime', 'General',
                 'Western', 'Literature', { role: 'annotation' } ],
                ['2010', 10, 24, 20, 32, 18, 5, ''],
                ['2020', 16, 22, 23, 30, 16, 9, ''],
                ['2030', 28, 19, 29, 30, 12, 13, '']
              ]);
            var options = {                
                height: height,
                width: checkWidth,
                title: title,
                colorAxis: {colors: ['#54C492', '#cc0000']},
                datalessRegionColor: '#dedede',
                defaultColor: '#dedede',
                legend: { position: 'top', maxLines: 2 },
                bar: { groupWidth: '75%' },
                isStacked: true,                
            };
        }
        
        else {
            var options = {
                height: height,
                width: checkWidth,
                title: title,
                colorAxis: {colors: ['#54C492', '#cc0000']},
                datalessRegionColor: '#dedede',
                defaultColor: '#dedede'
            };
        }

        var chart;

        if (res[0] == "ColumnChart")
            chart = new google.visualization.ColumnChart(document.getElementById(res[1]));
        else if (res[0] == "PieChart")
            chart = new google.visualization.PieChart(document.getElementById(res[1]));
        else if (res[0] == "BarChart")
            chart = new google.visualization.BarChart(document.getElementById(res[1]));
        else if (res[0] == "DonutChart")
            chart = new google.visualization.PieChart(document.getElementById(res[1]));
        else if (res[0] == "StackChart")
            chart = new google.visualization.ColumnChart(document.getElementById(res[1]));
        else if (res[0] == "TableChart")
            chart = new google.visualization.Table(document.getElementById(res[1]));
        else if (res[0] == "LineChart")
            chart = new google.visualization.LineChart(document.getElementById(res[1])); 
        chart.draw(data, options);                
    }
}