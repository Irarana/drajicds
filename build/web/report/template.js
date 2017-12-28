/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
$("#tabCharts").load("report/charts.txt");
var row_count = 0;
var col_count = 0;
           
            //template load
            $('body').on('click', '#mistemp1_icon', function () {
                $("#rowIcon").css('display','none');
                $("#misTemp").load("report/mistemp1.txt");
            });
            $('body').on('click', '#mistemp2_icon', function () {
                $("#rowIcon").css('display','none');
                $("#misTemp").load("report/mistemp2.txt");
            });
            $('body').on('click', '#mistemp3_icon', function () {
                $("#rowIcon").css('display','none');
                $("#misTemp").load("report/mistemp3.txt");
            });
            $('body').on('click', '#mistemp_custom_icon', function () {                
                $("#rowIcon").css('display','block');
                $("#misTemp").load("report/mistemp_custom.txt");                                
            });
            //add row type
            $('body').on('click', '#rowtype1_icon', function () {                
                $('#misTemp_custom').append('<div id="TempCustomRowtype1_'+ row_count +'" class="row mt10 mb5 temp_custom_row"><div class="rowEdit"><i class="fa fa-arrows mr10 "></i><i class="fa fa-plus-circle mr10 addRow"></i><i class="fa fa-trash deleteRow"></i></div><div class="col-xs-12"><div class="misTemp droppable"></div></div></div>');
                $('.misTemp').each(function (index, value) {
                    $(value).attr('id', 'TempCustomRowtype1Col_' + index);
                });
                $('#divAction').css('display','block');
                $('#divSave').css('display','block');
                $.fn.dragDrop();
                $.fn.sort();
                $.fn.sortInner();
                row_count++;                  
            });
            
            $('body').on('click', '#rowtype2_icon', function () {                
                $('#misTemp_custom').append('<div id="TempCustomRowtype2_'+ row_count +'" class="row mt10 mb5 temp_custom_row"><div class="rowEdit"><i class="fa fa-arrows mr10 "></i><i class="fa fa-plus-circle mr10 addRow"></i><i class="fa fa-trash deleteRow"></i></div><div class="col-xs-6"><div class="misTemp droppable"></div></div><div class="col-xs-6 "><div class="misTemp droppable"></div></div></div>');
                $('.misTemp').each(function (index, value) {
                    $(value).attr('id', 'TempCustomRowtype2Col_' + index);
                });
                $('#divAction').css('display','block');
                $('#divSave').css('display','block');
                $.fn.dragDrop();
                $.fn.sort();
                $.fn.sortInner();
                row_count++;
            });
            
            $('body').on('click', '#rowtype3_icon', function () {                
                $('#misTemp_custom').append('<div id="TempCustomRowtype3_'+ row_count +'" class="row mt10 mb5 temp_custom_row"><div class="rowEdit"><i class="fa fa-arrows mr10 "></i><i class="fa fa-plus-circle mr10 addRow"></i><i class="fa fa-trash deleteRow"></i></div><div class="col-xs-4"><div class="misTemp droppable"></div></div><div class="col-xs-4"><div class="misTemp droppable"></div></div><div class="col-xs-4"><div  class="misTemp droppable"></div></div></div>');
                $('.misTemp').each(function (index, value) {
                    $(value).attr('id', 'TempCustomRowtype3Col_' + index);
                });
                $('#divAction').css('display','block');
                $('#divSave').css('display','block');
                $.fn.dragDrop();
                $.fn.sort();
                $.fn.sortInner();
                row_count++;
            });
            
            $('body').on('click', '#rowtype4_icon', function () {                
                $('#misTemp_custom').append('<div id="TempCustomRowtype4_'+ row_count +'" class="row mt10 mb5 temp_custom_row"><div class="rowEdit"><i class="fa fa-arrows mr10 "></i><i class="fa fa-plus-circle mr10 addRow"></i><i class="fa fa-trash deleteRow"></i></div><div class="col-xs-8"><div class="misTemp droppable"></div></div><div class="col-xs-4"><div class="misTemp droppable"></div></div></div>');
               $('.misTemp').each(function (index, value) {
                    $(value).attr('id', 'TempCustomRowtype4Col_' + index);
               }); 
               $('#divAction').css('display','block');
               $('#divSave').css('display','block');
               $.fn.dragDrop();
               $.fn.sort();
               $.fn.sortInner();
               row_count++;               
            });
//            $('body').on('click', '#rowtype5_icon', function () {                
//                $('#misTemp_custom').append('<div id="TempCustomRowtype5_'+ row_count +'" class="row mt10 mb5 temp_custom_row"><div class="rowEdit"><i class="md md-open-with m-r-10 "></i> <i class="md  md-add-circle m-r-10 addRow"></i><i class="md md-delete deleteRow"></i></div><div class="col-xs-4"><div class="misTemp droppable"></div></div><div class="col-xs-8"><div class="misTemp droppable"></div></div></div>');
//               $('.misTemp').each(function (index, value) {
//                    $(value).attr('id', 'TempCustomRowtype5Col_' + index);
//               }); 
//               $('#divAction').css('display','block');
//               $('#divSave').css('display','block'); 
//               $.fn.dragDrop();
//               $.fn.sort();
//               $.fn.sortInner();
//               row_count++;               
//            });
   
    
//custom template 
// add row
$('body').on('click', '.addRow', function () {    
    var currentrow = $(this).parents('.temp_custom_row');
    var currentrowID = $(this).parents('.temp_custom_row').attr('id').split('_')[0];
    var newRow = currentrow.clone().insertAfter(currentrow);

    $(newRow).attr('id', ''+ currentrowID +'_'+ row_count +'');
    $(newRow).find('.misTemp').empty();
    $(newRow).find('.misTemp').next('.chartEdit').remove();

    $(newRow).find('.misTemp').each(function (index, value) {
        $(value).attr('id', '' + currentrowID + '_' + row_count + '' + index);
    });
    
    $.fn.dragDrop();
    $.fn.sortInner();
    row_count++;
    col_count++;
});

//delete row
$('body').on('click', '.deleteRow', function () {
    var currentrow = $(this).parents('.temp_custom_row');   
    currentrow.slideUp(function () {
        currentrow.remove();
    });
    var rowLength = $('.temp_custom_row').length;
    
    if(rowLength==1){
        $('#divAction').css('display','none');
        $('#divSave').css('display','none');
    }  
});

});
  