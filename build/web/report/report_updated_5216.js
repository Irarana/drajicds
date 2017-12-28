/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
setTimeout(function () {
    $.fn.dragDrop();
    $.fn.sort();
    
}, 0);
$.fn.dragDrop = function () {
    $(".draggable").draggable({
        revert: "invalid",
        drag: function (event, ui) {
            ui.helper.addClass("draggable");
        },
        stop: function (event, ui) {
            ui.helper.removeClass("draggable");
        },
    });
    $(".droppable").droppable({
        accept: ":not(.ui-sortable-helper)",
        drop: function (event, ui) {   
            ui.draggable.addClass("dropped");            
            $(this).append($(ui.draggable).clone());
            
            var chart_type= ui.draggable.find("img").attr("alt");
            var check = $(this).attr('id');
            var checkWidth = $('#' + check).width();

         //   console.log(check);
            
            var modal = "modal_" + check;
            var modalID = '#' + modal;
            $(".modal").attr("id", modal);
            $(modalID).modal();
          /*
            $(modalID).find('input[type="text"]').val("");
            var btnSubmit = $(modalID).find('.btnSubmit');
            var btnSubmit = "btn_" + check;
            var btnID = '#' + btnSubmit;
            $(".btnSubmit").attr("id", btnSubmit);

            var btnClose = $(modalID).find('.btnClose');
            var btnClose = "btnclose_" + check;
            var btnCloseID = '#' + btnClose;
            $(".btnClose").attr("id", btnClose);
            $(".btnClose").data("divid", check)

            var formDiv = $(modalID).find('.modal-content');
            var formDiv = "modalFormData_" + check;
            $(".modal-content").attr("id", formDiv);*/



            $('.modalForm').each(function( index ) {
               // $(this).find("input:hidden").

                if($(this).prop("id").length > 0)
                {


                    if($(this).data("ids")=='Query_Control_DataDomainidQuery_')
                    {




                        if ($(this).attr('data-info'))
                        {
                            $(this).data("info", check);
                        }
                        else
                        {
                            $(this).attr('data-info', check);
                        }


                    }


                    $newId   =$(this).data("ids")+check;
                    $(this).attr("id", $newId);


                    console.log($(this).data("ids"));

                    if($(this).hasClass('btn') && ($(this).text()=='Generate') )
                    {
                        $btnID='#'+$(this).prop("id");
                    }
                    if($(this).hasClass('btn') && ($(this).text()=='Close') ) {

                        $btnCloseID='#'+$(this).prop("id");

                        $($btnCloseID).data("divid", check);


                    }

                }
                else{

                }
            });




            // console.log($tt);

            
            $(document.body).on('click', $btnID, function () {

            	//area worked by Arijit Chakraborty
            	

            	var domainID = $(modalID).find('#Query_Control_DataDomainidQuery').val();
            	var dataModelID = $(modalID).find('#Query_Control_DataModelidQuery').val();
                var dataDimensionVal = $(modalID).find('#Query_Control_datadimensionquery').val();
            	var measureName = $(modalID).find("#Query_Control_Measures :input[type='checkbox']:checked").attr('id');
                var measureText = $(modalID).find('#'+measureName).text();
                var height = $(modalID).find('#height').val();
                var title = $(modalID).find('#title').val();

                var MeasurementBy = $(modalID).find('#Query_Control_MeasurementBy').text();
                var MeasurementByVal = $(modalID).find('#Query_Control_MeasurementBy').val();
                var MeasurementNameVal = $('.measurecheck:checked').map(function() {return this.value;}).get().join(',');

                var measurevall = MeasurementBy+':'+dataDimensionVal;
                var url = Routing.generate('DataQueryJson.do');

                if(domainID!='' && dataModelID!='' && dataDimensionVal!='' && MeasurementNameVal!='' && measureName!='' && MeasurementByVal!=''  ) {
                    $.ajax({
                        type: "POST",
                        url: Routing.generate('DataQueryJson.do'),
                        data: {
                            'dataDomainId': domainID,
                            'dataModelId': dataModelID,
                            'dataDimensionVal': dataDimensionVal,
                            'MeasurementNameVal': MeasurementNameVal,
                            'measureName': measureName,
                            'MeasurementByVal': MeasurementByVal
                        },
                        success: function (data) {
                            charts(data, chart_type + "-" + check, title, height, checkWidth, MeasurementBy, measurevall);
                            $(modalID).modal('hide');

                            $('#formDivContent_'+check).html($('#'+formDiv).clone());
                            $('#formDivContent_'+check).find('#'+formDiv).attr("id", 'clone_'+formDiv);


                            $('#'+formDiv+' select option:selected').each(function( index ) {

                                $('#clone_'+formDiv+' select').eq(index).val($( this ).val());

                            });
                            $('#formDivContent_'+check).hide();


                        }
                    });
                }
                else{
                    alert('Please fill up data correctly');
                }
                //ajax_data('GET', url, function (data)
                //{
                	//charts(data, chart_type + "-" + check, title, height, checkWidth);
                //});
            });

            $(document.body).on('click', $btnCloseID, function (){


                console.log($(this).data("divid"));
                $parentGraphDiv= $(this).data("divid");
                $(modalID).modal('hide');
                if( $('#formDivContent_'+check).text().length > 0) {

                }
                else{
                    $('#' + $parentGraphDiv).empty();
                    $('#' + $parentGraphDiv).next('.chartEdit').remove();
                }
            });

            $('#' + check).next('.chartEdit').remove();
            $('#' + check).parent('div').append('<div class="chartEdit" id="chartEdit_'+check+'"><div id="formDivContent_'+check+'" class="formDivContent_'+check+'"></div><i class="md md-edit m-r-15 md-24 editGraph"></i><i class="md md-delete md-24 deleteGraph"></i></div>');
        }
    });
};
//sort
$.fn.sort = function () {  
    $("#misTemp_custom").sortable({
        connectWith: '.temp_custom_row',
	handle: '.md-open-with',
        tolerance: 'pointer',
        revert: 'invalid',
        placeholder: 'placeholder',
        forceHelperSize: true,        
    }).disableSelection();    
};

//edit    
$('body').on('click','.editGraph', function (){   
    var parentGraphDiv=$(this).parent('div').prev().attr('id');     
    var editmodal="modal_" + parentGraphDiv;
    var editmodalID= '#'+editmodal;
    $(".modal").attr("id", editmodal);
    $(editmodalID).modal();
    var height = $(editmodalID).find('#height').val(); 
    var title = $(editmodalID).find('#title').val();
    
    var btnE=$(editmodalID).find('.btnSubmit');
    var btnE="btn_" + parentGraphDiv;
    var btnEID= '#'+btnE;
    $(".btnSubmit").attr("id", btnE);
    console.log(editmodalID);
    /*
    $('body').on('click',btnEID, function (){
        url= ''+Routing.generate('root_homepage')+'report/world-population-density.json';
        ajax_data('GET',url, function(data)
        {                      
            charts(data,chart_type+"-"+check,title,height,checkWidth);  
        });
    });*/
});
//delete
$('body').on('click','.deleteGraph', function (){   
    var parentGraphDiv=$(this).parent('div').prev().attr('id'); 
    $('#'+parentGraphDiv).empty();
    $('#'+parentGraphDiv).next('.chartEdit').remove();    
});






//preview
$('body').on('click', '#preview', function () {
    var currentrowID = $(this).parents('.card-body').find('.template').attr('id');

    if (currentrowID == "misTemp_custom") {
        $(this).parents('.card-body').find('.template').find('.misTemp').css('border', '1px solid #EFEFEF');
        $('.chartEdit').css('display', 'none');
        $('.draggable').draggable('disable');
        $(".droppable").droppable('disable');
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
//    $(this).parents('.template').find('.misTemp').css('border','1px solid #EFEFEF');
//    $('.chartEdit').remove();
//    //console.log($('#temp_action').remove())
//    console.log($(this).parents('.card-body').html());    
//});

//chart
function charts(data,ChartType,title,height, checkWidth, MeasurementBy, measureName)
{

var c=ChartType;
var jsonData=data;
//var div_id=divId;
google.load("visualization", "1", {packages:['corechart','table'], callback: drawVisualization});
function drawVisualization() 
{
var data = new google.visualization.DataTable();
data.addColumn('string', 'Measure Type');
data.addColumn('number', measureName);
$.each(jsonData, function(i,jsonData)
{
//console.log(jsonData);
var value=jsonData.value;
var name=jsonData.name;
//console.log(value+ " " + name);
data.addRows([ [name, value]]);
});

var res = c.split("-"); 

if(res[0]=="DonutChart"){
    var options = {
    height: height,
    width:checkWidth,
    title :title,
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
        isStacked: true
    };
}
else{
    var options = {
    height: height,
    width:checkWidth,           
    title : title,
    colorAxis: {colors: ['#54C492', '#cc0000']},
    datalessRegionColor: '#dedede',
    defaultColor: '#dedede'
    };
}

var chart;

if(res[0]=="ColumnChart")
chart = new google.visualization.ColumnChart(document.getElementById(res[1]));
else if(res[0]=="PieChart")
chart = new google.visualization.PieChart(document.getElementById(res[1]));
else if(res[0]=="BarChart")
chart = new google.visualization.BarChart(document.getElementById(res[1]));
else if(res[0]=="DonutChart")
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

//new function added, Arijit Chakraborty
$('#Query_Control_DataDomainidQuery').on("change", function(){
    var val = $(this).val();
    $.ajax({
        type: "POST",
        url: Routing.generate('DataModelByDataDomain.do') ,
        data: {'dataDomainId' : $(this).val()},
        success: function(data) {
        	$('#dataModelSection').html('');
        	$('#Query_Control_DimensionSection').html('');
        	$('#Query_Control_MeasurementBy').html('');
        	$('#Query_Control_Measures').html('');
        	
        	var dimenHtml = '<div class="col-md-12 m-b-10">' +
    		'<label class="col-md-3">Data Model</label>' +
    		'<div class="col-md-9">' +
    		'<div class="fg-line">' +
            '<select id="Query_Control_DataModelidQuery" class="form-control tag-select dimension">' +
            '<option value="" default>--Select Data Model--</option>';
        	
            $.each(data, function(key, value) {
            	dimenHtml += '<option value="' + key +'">' + value + '</option>';
            });
            
            dimenHtml += '</select></div>' +
                		'</div>' +
                		'</div>';
            	
            $('#dataModelSection').append(dimenHtml);
        }
    });

    return false;
});

//new function added, Arijit Chakraborty
$(document.body).on('change', '#Query_Control_DataModelidQuery' ,function() {

    var val = $(this).val();


    if (val!='') {



    $.ajax({
        type: "POST",
        url: Routing.generate('DataDimensionElementsByDataModel.do'),
        data: {'dataModelId': val},
        success: function (data) {

            //console.log(data);

            $('#Query_Control_DimensionSection').html('');
            $('#Query_Control_MeasurementBy').html('');
            $('#measureID').show();
            $('#dimesionID').show();
            //$('#Query_Control_MeasurementBy').append('<option value="resultset">Resultset</option>');
            $('#Query_Control_Measures').html('');

            $.each(data, function (key, value) {
                //console.log(key);
                //console.log(value);
                if (key == "measures") {
                    $.each(value, function (k, v) {
                        $('#Query_Control_Measures').append('<input name="measurecheck" class="measurecheck" value="' + k + '" type="checkbox" id="' + k + '">&nbsp;' + v + '</input><br />');
                    });
                }
                var dimenHtml = '';
                var measuresHtml = '';
                dimenHtml = '<div class="row">' +
                    '<div class="col-md-12 m-b-10">' +
                    '<label class="col-md-3">' + value.name + '</label>' +
                    '<div class="col-md-9">' +
                    '<div class="fg-line">' +
                    '<select id="Query_Control_datadimensionquery" class="form-control tag-select dimension">' +
                    '<option value="">--Select ' + value.name + '--</option>' +
                    '<option value="all">All</option>';

                measuresHtml += '<option value="' + value.alias + '">' + value.name + '</option>';

                //var elemObj = JSON.parse(value.elements);
                $.each(value.elements, function (elemKey, elemValue) {
                    dimenHtml += '<option value=' + elemValue + '>' + elemValue + '</option>';
                });

                dimenHtml += '</select></div>' +
                    '</div>' +
                    '</div>' +
                    '</div>';

                $('#Query_Control_DimensionSection').append(dimenHtml);
                $('#Query_Control_MeasurementBy').append(measuresHtml);
            });
        }
    });
}
    return false;

});


function getHtml(saveType){
    var hml = $("#misTemp").html();
    var  reportstatus   = saveType;
    var reporttitle= $.trim($('#reporttitle').val());
    //console.log(hml);
    if(reporttitle!='') {
        $.ajax({
            type: "POST",
            url: Routing.generate('Reports.Addition.do'),
            data: {reportHtml: hml, reportstatus: reportstatus, reporttitle: reporttitle},
            // dataType:"json",
            // restful:true,
            // contentType: 'application/json',
            cache: false,
            // timeout:20000,
            //async:true,

            success: function (data) {
                alert(data);
                window.location.href = Routing.generate('ReportList.html');

            },
            error: function (data) {
                alert("Error In Connecting");
            }
        });
    }
    else
    {
        alert('Please enter Report Title');
    }
    // post_ajax_data(url, hml, success);
}


function changePagination(pageId)
{


    $('html,body').animate({
        scrollTop: $('.queryControl').offset().top
    }, 500);

    $("#display-model-list").css("opacity","0.4");
    $('#paginate-loader').html('<img src="'+Routing.generate('root_homepage')+'/bundles/root/img/loading.gif" height="30px" width="30px" id="paginateLoaderImage">').fadeIn();

    var postData = {

        "pageId": pageId ,
        "formData" : $('#searchForm').serialize()
    }


    setTimeout(function(){

        $.ajax({
            type: "POST",
            url: Routing.generate('ReportListing.html'),
            data: postData,
            cache: false,
            success: function(result){

                $('#paginate-loader').html('').fadeOut();

                $("#reportListing").css("opacity","inherit").html(result);
                $('html,body').animate({scrollTop: $(window).scrollTop() + 200 },'slow');


            }
        });

    },1000)

}

function dataReportEdit(reportId)
{

    $url= Routing.generate('ReportEdit.html', {'id': reportId})
    window.location.href = $url;


}


//

function domainSearch(domain)
{
  console.log($(domain).val());

    $check = $(domain).data("info");
    var val = domain.value;
    if (val!='') {
        $.ajax({
            type: "POST",
            url: Routing.generate('DataModelByDataDomain.do'),
            data: {'dataDomainId': domain.value},
            success: function (data) {

                $('#dataModelSection_' + $check).html('');
                $('#dataModelSection_' + $check).show();
                $('#Query_Control_DimensionSection_' + $check).html('');
                $('#Query_Control_MeasurementBy_' + $check).html('');
                $('#Query_Control_Measures_' + $check).html('');

                var dimenHtml = '<div class="col-md-12 m-b-10">' +
                    '<label class="col-md-3">Data Model</label>' +
                    '<div class="col-md-9">' +
                    '<div class="fg-line">' +
                    '<select id="Query_Control_DataModelidQuery_' + $check + '" onchange="modelSearch(this);" data-modelinfo="' + $check + '" class="form-control tag-select dimension">' +
                    '<option value="" default>--Select Data Model--</option>';

                $.each(data, function (key, value) {
                    dimenHtml += '<option value="' + key + '">' + value + '</option>';
                });

                dimenHtml += '</select></div>' +
                    '</div>' +
                    '</div>';

                $('#dataModelSection_' + $check).append(dimenHtml);
            }
        });
    }
    else
    {
        $('#dataModelSection_' + $check).html('');
        $('#Query_Control_DimensionSection_' + $check).html('');
        $('#dataModelSection_' + $check).hide();
        $('#Query_Control_MeasurementBy_' + $check).html('');
        $('#Query_Control_Measures_' + $check).html('');

        $('#Query_Control_DimensionSection_'+$check).hide('');
        $('#Query_Control_MeasurementBy_'+$check).hide('');

        if($('#measureID_' + $check).length>0) {
         //   $('#measureID_' + $check).html('');
            $('#measureID_' + $check).hide();
        }
        if($('#dimesionID_' + $check).length>0) {
           // $('#dimesionID_' + $check).html('');
            $('#dimesionID_' + $check).hide();
        }
    }
    return false;
}


function modelSearch(model)
{
    var val = $(model).val();
    $check = $(model).data("modelinfo");

    if (val!='') {



        $.ajax({
            type: "POST",
            url: Routing.generate('DataDimensionElementsByDataModel.do'),
            data: {'dataModelId': val},
            success: function (data) {

                //console.log(data);

                $('#Query_Control_DimensionSection_'+$check).html('');
                $('#Query_Control_DimensionSection_'+$check).show();
                $('#Query_Control_MeasurementBy_'+$check).html('');
                $('#Query_Control_MeasurementBy_'+$check).show();
                $('#measureID_'+$check).show();
                $('#dimesionID_'+$check).show();
                //$('#Query_Control_MeasurementBy').append('<option value="resultset">Resultset</option>');
                $('#Query_Control_Measures_'+$check).html('');
                $('#Query_Control_Measures_'+$check).show();

                $.each(data, function (key, value) {
                    //console.log(key);
                    //console.log(value);
                    if (key == "measures") {
                        $.each(value, function (k, v) {
                            $('#Query_Control_Measures_'+$check).append('<input name="measurecheck" class="measurecheck" value="' + k + '" type="checkbox" id="' + k + '">&nbsp;' + v + '</input><br />');
                        });
                    }
                    var dimenHtml = '';
                    var measuresHtml = '';
                    dimenHtml = '<div class="row">' +
                        '<div class="col-md-12 m-b-10">' +
                        '<label class="col-md-3">' + value.name + '</label>' +
                        '<div class="col-md-9">' +
                        '<div class="fg-line">' +
                        '<select id="Query_Control_datadimensionquery_'+$check+'" class="form-control tag-select dimension">' +
                        '<option value="">--Select ' + value.name + '--</option>' +
                        '<option value="all">All</option>';

                    measuresHtml += '<option value="' + value.alias + '">' + value.name + '</option>';

                    //var elemObj = JSON.parse(value.elements);
                    $.each(value.elements, function (elemKey, elemValue) {
                        dimenHtml += '<option value=' + elemValue + '>' + elemValue + '</option>';
                    });

                    dimenHtml += '</select></div>' +
                        '</div>' +
                        '</div>' +
                        '</div>';

                    $('#Query_Control_DimensionSection_'+$check).append(dimenHtml);
                    $('#Query_Control_MeasurementBy_'+$check).append(measuresHtml);
                });
            }
        });
    }
    else
    {

        $('#Query_Control_DimensionSection_'+$check).html('');
        $('#Query_Control_MeasurementBy_'+$check).html('');
        $('#measureID_'+$check).hide();
        $('#dimesionID_'+$check).hide();
    }
    return false;
}

