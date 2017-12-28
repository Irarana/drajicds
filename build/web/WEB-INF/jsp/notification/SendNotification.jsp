<%-- 
    Document   : ICDSProjectMaster
    Created on : Nov 10, 2017, 11:06:51 AM
    Author     : ekank
--%>

<%@ page contentType="text/html; charset=utf-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width,initial-scale=1.0"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="description" content="HelpingPro - Nonprofit, Crowdfunding & Charity HTML5 Template" />
        <meta name="keywords" content="charity,crowdfunding,nonprofit,orphan,Poor,funding,fundrising,ngo,children" />
        <meta name="author" content="ThemeMascot" />

        <!-- Page Title -->
        <title>Digital Raj - Integrated Child Development Services (ICDS) </title>

        <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css">
        <link href="css/jquery-ui.min.css" rel="stylesheet" type="text/css">

        <!--<link href="css/animate.css" rel="stylesheet" type="text/css">         very slow for this css -->

        <link href="css/css-plugin-collections.css" rel="stylesheet"/>

        <!-- CSS | menuzord megamenu skins -->
        <link id="menuzord-menu-skins" href="css/menuzord-skins/menuzord-rounded-boxed.css" rel="stylesheet"/>
        <!-- CSS | Main style file -->
        <link href="css/style-main.css" rel="stylesheet" type="text/css">
        <!-- CSS | Custom Margin Padding Collection -->
        <link href="css/custom-bootstrap-margin-padding.css" rel="stylesheet" type="text/css">
        <!-- CSS | Responsive media queries -->
        <link href="css/responsive.css" rel="stylesheet" type="text/css">
        <!-- CSS | Style css. This is the file where you can place your own custom css code. Just uncomment it and use it. -->
        <!-- <link href="css/style.css" rel="stylesheet" type="text/css"> -->

        <!-- Revolution Slider 5.x CSS settings -->
        <link  href="js/revolution-slider/css/settings.css" rel="stylesheet" type="text/css"/>
        <link  href="js/revolution-slider/css/layers.css" rel="stylesheet" type="text/css"/>
        <link  href="js/revolution-slider/css/navigation.css" rel="stylesheet" type="text/css"/>

        <!-- CSS | Theme Color -->
        <link href="css/colors/theme-skin-rose.css" rel="stylesheet" type="text/css">

        <!-- external javascripts -->
        <script src="js/jquery-2.2.4.min.js"></script>
        <script src="js/jquery-ui.min.js"></script>
        <script src="js/bootstrap.min.js"></script>

        <!-- JS | jquery plugin collection for this theme -->
        <script src="js/jquery-plugin-collection.js"></script>
        <link rel="stylesheet" type="text/css" href="js/datatable/datatable.css">
        <!--<link rel="stylesheet" type="text/css" href="js/datatable/dataTables.bootstrap.min.css">-->
        <script type="text/javascript" src="js/datatable/jquery.dataTables.min.js"></script>
        <script type="text/javascript" src="js/datatable/datatable-bootstrap.js"></script>
        <script type="text/javascript" src="js/datatable/dataTables.fixedColumns.min.js"></script>
        <script src="js/parsley.min.js" type="text/javascript"></script>
        <script type="text/javascript" src="https://www.google.com/jsapi"></script> 
        <!--<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>-->
        <script src="js/custom.js"></script>
        <script type="text/javascript">
            $(document).ready(function () {
                var table = $('#datatable').DataTable({
                    scrollY: "300px",
                    scrollCollapse: true,
                    paging: true,
                    responsive: true,
                    select: true,
                });

 $("#approver").prop("disabled", true);
 $("#detailsAbout").prop("disabled", true);
 

                $('#datatable tbody').on('click', 'tr', function () {
                    var rows = table.rows({'search': 'applied'}).nodes();
                    var districtFlag = false;
                    var projectFlag = false;
                    var sectorFlag = false;
                    var distCount = 0;
                    var projCount = 0;
                    var sectCount = 0;
                    for (var i = 0; i < rows.length; i++)
                    {

                        if ($('td:eq(1)', $(rows[i])).html() == 'District') {
                            if (!$('input[type="checkbox"]', $(rows[i])).is(':checked')) {
                                districtFlag = true;
                                //projectFlag = false;
                                //sectorFlag=false;
                            } else {
                                if (projCount > 0 || sectCount > 0) {
                                    $('input[type="checkbox"]', $(rows[i])).prop('checked', false);
                                } else {
                                    distCount++;
                                }
                            }
                        }
                        if ($('td:eq(1)', $(rows[i])).html() == 'Project') {
                            if (!$('input[type="checkbox"]', $(rows[i])).is(':checked')) {
                                projectFlag = true;
                                // districtFlag=false;
                                // sectorFlag=false;
                            } else {

                                if (distCount > 0 || sectCount > 0) {
                                    $('input[type="checkbox"]', $(rows[i])).prop('checked', false);
                                } else {
                                    projCount++;
                                }
                            }
                        }
                        if ($('td:eq(1)', $(rows[i])).html() == 'Sector') {
                            //alert($('td:eq(2)', $(rows[i])).html()+" Checked "+$('input[type="checkbox"]', $(rows[i])).is(':checked'));
                            if (!$('input[type="checkbox"]', $(rows[i])).is(':checked')) {
                                sectorFlag = true;

                            } else {
                                if (projCount > 0 || distCount > 0) {
                                    // alert("false");
                                    $('input[type="checkbox"]', $(rows[i])).prop('checked', false);
                                } else {
                                    sectCount++;
                                }
                                //  alert(sectCount)  ; 
                            }
                            // alert(sectorFlag+" : sectorFlag");
                        }

                    }
                    
                    if (distCount > 0) {
                        // alert(" distCount >> "+distCount);
                        // alert("allProject >> "+$('#allProject').is(":checked"));
                        // alert("allSector >> "+$('#allSector').is(":checked"));
                       
                        $('#allProject').prop(':checked', false);
                        $('#allSector').prop(':checked', false);
                        $('#selectedDistrict').html("");
                        $('#selectedDistrict').html("District(" + distCount + ")");
                        $('#selDistHid').val("District(" + distCount + ")");
                        //Approver List
                        $('#approver').empty();
                        newOption = $('<option value="">Select</option>');
                        $('#approver').append(newOption);
                        newOption = $('<option value="DIST">District</option>');
                        $('#approver').append(newOption);
                        //About List
                        $('#detailsAbout').empty();
                        var newOption = $('<option value="">Select</option>');
                        $('#detailsAbout').append(newOption);
                        newOption = $('<option value="DIST">District</option>');
                        $('#detailsAbout').append(newOption);
                        newOption = $('<option value="PROJECT">Project</option>');
                        $('#detailsAbout').append(newOption);
                        newOption = $('<option value="SECTOR">Sector</option>');
                        $('#detailsAbout').append(newOption);
                        newOption = $('<option value="AWC">AWC</option>');
                        $('#detailsAbout').append(newOption);
                    } else {
                        $('#selectedDistrict').html("");
                        $('#selDistHid').val("");
                    }
                    if (projCount > 0) {
                        // alert(" sectCount >> "+sectCount);
                        //alert("allDistrict >> "+$('#allDistrict').is(":checked"));
                        // alert("allSector >> "+$('#allSector').is(":checked"));
                        $('#allDistrict').prop('checked', false);
                        $('#allSector').prop('checked', false);
                        $('#selectedProject').html("");
                        $('#selectedProject').html("Project(" + projCount + ")");
                        $('#selProjHid').val("Project(" + projCount + ")");
                        //Approver List
                        $('#approver').empty();
                        var newOption = $('<option value="">Select</option>');
                        $('#approver').append(newOption);
                        newOption = $('<option value="DIST">District</option>');
                        $('#approver').append(newOption);
                        newOption = $('<option value="PROJECT">Project</option>');
                        $('#approver').append(newOption);
                        //About List
                        $('#detailsAbout').empty();
                        var newOption = $('<option value="">Select</option>');
                        $('#detailsAbout').append(newOption);

                        newOption = $('<option value="PROJECT">Project</option>');
                        $('#detailsAbout').append(newOption);
                        newOption = $('<option value="SECTOR">Sector</option>');
                        $('#detailsAbout').append(newOption);
                        newOption = $('<option value="AWC">AWC</option>');
                        $('#detailsAbout').append(newOption);
                    } else {
                        $('#selectedProject').html("");
                        $('#selProjHid').val("");
                    }
                    if (sectCount > 0) {
                        $('#allDistrict').prop('checked', false);
                        $('#allProject').prop('checked', false);
                        $('#selectedSector').html("");
                        $('#selectedSector').html("Sector(" + sectCount + ")");
                        $('#selSectHid').val("Sector(" + sectCount + ")");
                        //Approver List
                        $('#approver').empty();
                        var newOption = $('<option value="">Select</option>');
                        $('#approver').append(newOption);
                        newOption = $('<option value="DIST">District</option>');
                        $('#approver').append(newOption);
                        newOption = $('<option value="PROJECT">Project</option>');
                        $('#approver').append(newOption);
                        newOption = $('<option value="SECTOR">Sector</option>');
                        $('#approver').append(newOption);
                        //About List
                        $('#detailsAbout').empty();
                        var newOption = $('<option value="">Select</option>');
                        newOption = $('<option value="SECTOR">Sector</option>');
                        $('#detailsAbout').append(newOption);
                        newOption = $('<option value="AWC">AWC</option>');
                        $('#detailsAbout').append(newOption);

                    } else {
                        $('#selectedSector').html("");
                        $('#selSectHid').val("");
                    }
                    if($('#selDistHid').val()!='' ||$('#selProjHid').val()!='' ||$('#selSectHid').val()!=''){
                     $("#approver").prop("disabled", false);
                        $("#detailsAbout").prop("disabled", false);
                    }else{
                         $("#approver").prop("disabled", true);
                        $("#detailsAbout").prop("disabled", true);
                    }
                    if ($('#allDistrict').is(":checked")) {
                        if (districtFlag) {
                            $('#allDistrict').prop('checked', false);
                        }
                        if (distCount == 0) {
                            $('#allDistrict').prop('checked', false);
                        }
                    } else {
                        if (!districtFlag) {
                            $('#allDistrict').prop('checked', true);
                        }
                        if (distCount == 0) {
                            $('#allDistrict').prop('checked', false);
                        }
                    }
                    if ($('#allProject').is(":checked")) {
                        //  alert("if Checked "+$('#allProject').is(":checked"));
                        // alert("projectFlag "+projectFlag);
                        if (projectFlag) {
                            $('#allProject').prop('checked', false);
                        }
                        if (projCount == 0) {
                            $('#allProject').prop('checked', false);
                        }
                    } else {
                        if (!projectFlag) {
                            // alert("projectFlag "+projectFlag);
                            //alert("Checked "+$('#allProject').is(":checked"));
                            $('#allProject').prop('checked', true);
                        }
                        if (projCount == 0) {
                            $('#allProject').prop('checked', false);
                        }
                    }
                    if ($('#allSector').is(":checked")) {
                        if (sectorFlag) {
                            $('#allSector').prop('checked', false);
                        }
                        // alert(sectCount+" sectCount");
                        if (sectCount == 0) {
                            $('#allSector').prop('checked', false);
                        }
                    } else {
                        //alert(sectCount+" sectCount");
                        if (!sectorFlag) {
                            // alert(sectorFlag);
                            $('#allSector').prop('checked', true);
                        }
                        if (sectCount == 0) {
                            $('#allSector').prop('checked', false);
                        }
                    }
                });

                $('#allProject').change(function () {
                    // alert($('#allProject').val());
                    $('#allDistrict').prop('checked', false);
                    $('#allSector').prop('checked', false);
                    $('#selectedDistrict').html("");
                    $('#selDistHid').val("");
                    $('#selectedSector').html("");
                    $('#selSectHid').val("");
                    var tempVal = '';
                    if ($('#allProject').val()) {
                        var count = 0;
                        var rows = table.rows({'search': 'applied'}).nodes();
                        var cells = [];
                        for (var i = 0; i < rows.length; i++)
                        {
                            if ($('td:eq(1)', $(rows[i])).html() == 'Project') {
                                $('input[type="checkbox"]', $(rows[i])).prop('checked', this.checked);
                                count++;
                            } else {
                                $('input[type="checkbox"]', $(rows[i])).prop('checked', false);
                            }
                        }
                        if ($('#allProject').is(":checked")) {
                            tempVal = "Project(" + count + ")";
                        }
                        $('#selectedProject').html(tempVal);
                        $('#selProjHid').val(tempVal);
                        //Approver List
                        $('#approver').empty();
                        var newOption = $('<option value="">Select</option>');
                        $('#approver').append(newOption);
                        newOption = $('<option value="DIST">District</option>');
                        $('#approver').append(newOption);
                        newOption = $('<option value="PROJECT">Project</option>');
                        $('#approver').append(newOption);
                        //About List
                        $('#detailsAbout').empty();
                        var newOption = $('<option value="">Select</option>');
                        $('#detailsAbout').append(newOption);

                        newOption = $('<option value="PROJECT">Project</option>');
                        $('#detailsAbout').append(newOption);
                        newOption = $('<option value="SECTOR">Sector</option>');
                        $('#detailsAbout').append(newOption);
                        newOption = $('<option value="AWC">AWC</option>');
                        $('#detailsAbout').append(newOption);
                    }
                    if($('#selDistHid').val()!='' ||$('#selProjHid').val()!='' ||$('#selSectHid').val()!=''){
                     $("#approver").prop("disabled", false);
                        $("#detailsAbout").prop("disabled", false);
                    }else{
                         $("#approver").prop("disabled", true);
                        $("#detailsAbout").prop("disabled", true);
                    }
                });

                $('#allDistrict').change(function () {
                    $('#allProject').prop('checked', false);
                    $('#allSector').prop('checked', false);
                    $('#selectedProject').html("");
                    $('#selProjHid').val("");
                    $('#selectedSector').html("");
                    $('#selSectHid').val("");
                    // alert($('#allProject').val());
                    var tempVal = '';
                    if ($('#allDistrict').val()) {
                        var count = 0;
                        var rows = table.rows({'search': 'applied'}).nodes();
                        for (var i = 0; i < rows.length; i++)
                        {
                            if ($('td:eq(1)', $(rows[i])).html() == 'District') {
                                $('input[type="checkbox"]', $(rows[i])).prop('checked', this.checked);
                                count++;
                            } else {
                                $('input[type="checkbox"]', $(rows[i])).prop('checked', false);
                            }
                        }
                        if ($('#allDistrict').is(":checked")) {
                            tempVal = " District(" + count + ")";
                        }
                        $('#selectedDistrict').html(tempVal);
                        $('#selDistHid').val(tempVal);
//Approver List
                        $('#approver').empty();
                        newOption = $('<option value="">Select</option>');
                        $('#approver').append(newOption);
                        newOption = $('<option value="DIST">District</option>');
                        $('#approver').append(newOption);
                        //About List
                        $('#detailsAbout').empty();
                        var newOption = $('<option value="">Select</option>');
                        $('#detailsAbout').append(newOption);
                        newOption = $('<option value="DIST">District</option>');
                        $('#detailsAbout').append(newOption);
                        newOption = $('<option value="PROJECT">Project</option>');
                        $('#detailsAbout').append(newOption);
                        newOption = $('<option value="SECTOR">Sector</option>');
                        $('#detailsAbout').append(newOption);
                        newOption = $('<option value="AWC">AWC</option>');
                        $('#detailsAbout').append(newOption);
                    }
                    if($('#selDistHid').val()!='' ||$('#selProjHid').val()!='' ||$('#selSectHid').val()!=''){
                     $("#approver").prop("disabled", false);
                        $("#detailsAbout").prop("disabled", false);
                    }else{
                         $("#approver").prop("disabled", true);
                        $("#detailsAbout").prop("disabled", true);
                    }
                });
                $('#allSector').change(function () {
                    $('#allProject').prop('checked', false);
                    $('#allDistrict').prop('checked', false);
                    $('#selectedProject').html("");
                    $('#selProjHid').val("");
                    $('#selectedDistrict').html("");
                    $('#selDistHid').val("");
                    // alert($('#allProject').val());
                    var tempVal = '';
                    if ($('#allSector').val()) {
                        var count = 0;
                        var rows = table.rows({'search': 'applied'}).nodes();
                        for (var i = 0; i < rows.length; i++)
                        {
                            if ($('td:eq(1)', $(rows[i])).html() == 'Sector') {
                                $('input[type="checkbox"]', $(rows[i])).prop('checked', this.checked);
                                count++;
                            } else {
                                $('input[type="checkbox"]', $(rows[i])).prop('checked', false);
                            }
                        }
                        if ($('#allSector').is(":checked")) {
                            tempVal = " Sector(" + count + ")";
                        }
                        $('#selectedSector').html(tempVal);
                        $('#selSectHid').val(tempVal);
//Approver List
                        $('#approver').empty();
                        var newOption = $('<option value="">Select</option>');
                        $('#approver').append(newOption);
                        newOption = $('<option value="DIST">District</option>');
                        $('#approver').append(newOption);
                        newOption = $('<option value="PROJECT">Project</option>');
                        $('#approver').append(newOption);
                        newOption = $('<option value="SECTOR">Sector</option>');
                        $('#approver').append(newOption);
                        //About List
                        $('#detailsAbout').empty();
                        var newOption = $('<option value="">Select</option>');
                        newOption = $('<option value="SECTOR">Sector</option>');
                        $('#detailsAbout').append(newOption);
                        newOption = $('<option value="AWC">AWC</option>');
                        $('#detailsAbout').append(newOption);
                    }
                    if($('#selDistHid').val()!='' ||$('#selProjHid').val()!='' ||$('#selSectHid').val()!=''){
                     $("#approver").prop("disabled", false);
                        $("#detailsAbout").prop("disabled", false);
                    }else{
                         $("#approver").prop("disabled", true);
                        $("#detailsAbout").prop("disabled", true);
                    }
                });
//               

                $('#districtCode').change(function () {
                    if ($('#districtCode').val() != '') {
                        $.ajax({
                            type: "GET",
                            url: "getProjForDis.htm",
                            data: {distCode: $('#districtCode').val()},
                            success: function (data) {
                                var keys = Object.keys(data);
                                $('#selectedProjectId').empty();
                                var newOption = '<option value="select">Select</option>';
                                $('#selectedProjectId').append(newOption);
                                // alert(data);
                                for (var i = 0; i < keys.length; i++) {
                                    var key = keys[i];
                                    console.log(key, data[key]);
                                    var newOptiontemp = $('<option value="' + key + '">' + data[key] + '</option>');
                                    $('#selectedProjectId').append(newOptiontemp);
                                }

                            },
                            error: function () {
                                alert('Error occured');
                            }
                        });
                    }
                });

//               
            });
            google.load("elements", "1", {
                packages: "transliteration"
            });
            function onLoad() {
                var options = {
                    sourceLanguage:
                            google.elements.transliteration.LanguageCode.ENGLISH,
                    destinationLanguage:
                            [google.elements.transliteration.LanguageCode.HINDI],
                    transliterationEnabled: true
                };
                // Create an instance on TransliterationControl with the required
                // options.
                var control =
                        new google.elements.transliteration.TransliterationControl(options);
                // Enable transliteration in the textbox with id
                // 'transliterateTextarea'.
                //control.makeTransliteratable(['transliterateTextarea']);
                control.makeTransliteratable(['officeNameHindi']);
                control.makeTransliteratable(['officeAddress']);
                control.makeTransliteratable(['hodNameHindi']);
            }
            google.setOnLoadCallback(onLoad);
//            function getHRMSIDDetails() {
//                hrmsID = $("#hrmsIDHOD").val();
//                if (hrmsIDHOD == '') {
//                    alert('Please Enter HRMS ID');
//                } else {
//
//
//                    $.ajax({
//                        'url': 'getHRMSIDController.htm?hrmsID=' + hrmsID,
//                        dataType: 'json',
//                        contentType: 'application/json',
//                        'type': 'GET',
//                        beforeSend: function () {
//                            //alert(1);
//                        },
//                        error: function () {
//                            alert('Error');
//                        },
//                        'success': function (data) {
//                            //alert(data);
//                            if (data.hrmsIdName == '') {
//                                alert('HRMDID not Found.');
//                                $("#hodName").val('');
//                                $("#hodNameHindi").val('');
//                            } else {
//                                // alert(data.hrmsIdName)
//                                str = data.hrmsIdName.split("@");
//                                // alert(str);
//                                $("#hodName").val(str[0]);
//                                // alert(str[1]);
//                                $("#hodNameHindi").val(str[1]);
//                            }
//                        }
//                    });
//                }
//            }

            function submitForm() {
                $.validator.addMethod("validateApprover", function (value, element) {
                    return  value != '';
                }, " Select Approver");
                $.validator.addMethod("validateNotification", function (value) {
                    return value != '' && (value.length <= 100);
                }, " Enter Notification and length should be less then 100 character.");

                $.validator.addMethod("validateDetailsAbout", function (value, element) {
                    return  value != '';
                }, " Select About");

                $.validator.addMethod("validateNeedApprovalBefore", function (value, element) {
                    return  value > 0;
                }, " Select Approver With In");

                $.validator.addMethod("validateNeedReplyBefore", function (value, element) {
                    return  value > 0;
                }, " Select Reply With In");


                $.validator.addMethod("validateFormId", function (value, element) {
                    return  value > 0;
                }, " Select Form Id");

                $.validator.addMethod("validateDist", function () {
                    alert("Hi");
                    return  false;
                }, " Select respondents");

                var validator = $("#notificationForm").validate({
                    rules: {
                        notification: {
                            validateNotification: true
                        },
                        approver: {
                            validateApprover: true
                        },
                        detailsAbout: {
                            validateDetailsAbout: true
                        },
                        needApprovalBefore: {
                            validateNeedApprovalBefore: true
                        },
                        needReplyBefore: {
                            validateNeedReplyBefore: true
                        },
                        form_Id: {
                            validateFormId: true
                        },
                        respondents: {
                            validateDist: true
                        },
                    },
                    errorElement: "span",
                    messages: {
                        //respondents: "Enter respondents ",
                    }
                });
                var distVal = $('b:first').text(function text(index, value) {
                    // alert(value);
                    if (value == '' && $('b:odd').text() == '' && $('b:last').text() == '') {
                        // alert(">>"+$('b:odd').text());
                        // alert($('b:last').text());
                        $('b:first').html('<strong style="color: red">Please select respondent.</strong>')
                        //return 
                    }
                });
//                if($('b:first').text()==''){
//                    alert(">>>"+$('b:first').text());
//                    return false;
//                }else 
                if (validator.form()) { // validation perform
                    // return true;
                    $('form#notificationForm').submit(function (e) {

                        if (($('b:first').text() != '' && $('b:first').text() != 'Please select respondent.') || $('b:odd').text() != '' || $('b:last').text() != '') {
                            // alert(action + $("#projectCode").val());
                            return true;
                        } else {
                            //alert(">>>"+distVal);
                            //alert("Please select respondant.");
                            return false;
                        }
                    });
                }

                //$('form#notificationForm').submit();

            }
            function cancelForm(level) {
                //alert(level);
                location.href = "icdsProjectList.htm?menuLevel=" + level;

            }

//            function validate() {
//                distSelVal = $("#selDistHid").val();
//                projSelVal = $("#selProjHid").val();
//                sectSelVal = $("#selSectHid").val();
//                //alert(distSelVal);
//                //alert(projSelVal);
//
//                $('form#notificationForm').submit(function (e) {
//
//                    if ($("#selDistHid").val() != '' || $("#selProjHid").val() != '' || $("#selSectHid").val() != '') {
//                        // alert(action + $("#projectCode").val());
//                        return true;
//                    } else {
//                        alert("Please select respondant.");
//                        return false;
//                    }
//                });
//
//            }


        </script>

    </head>
    <body class="boxed-layout pt-40 pb-40 pt-sm-0" data-bg-img="images/pattern/p37.png">
        <div id="wrapper" class="clearfix">
            <jsp:include page="../header.jsp"></jsp:include>
                <!-- Start main-content -->
                <div class="main-content">
                    <!-- Section: inner-header -->
                    <section class="inner-header divider layer-overlay overlay-dark-8" data-bg-img="images/bg/bg2.jpg">
                        <div class="container pt-90 pb-40">
                            <!-- Section Content -->
                            <div class="section-content">
                                <div class="row"> 
                                    <div class="col-md-6">
                                        <h2 class="text-white font-36"><spring:message code="lable.noti.header"/></h2>
                                    <ol class="breadcrumb text-left mt-10 white">
                                        <li><a href="homepage.htm"><spring:message code="home"/></a></li>
                                        <li><a href="homepage.htm"><spring:message code="lable.icds.units"/></a></li>

                                        <li class="active"><spring:message code="entry"/></li>
                                    </ol>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>

                <section>

                    <div class="container">
                        <form:form id="notificationForm" method="POST" modelAttribute="NotificationBean"  >
                            <div class="col-md-6  ">
                                <div class="border-1px bg-white clearfix p-20 pt-10 pb-20">

                                    <div class="form-group">
                                        <label for="notification"><spring:message code="lable.noti.notification"/><small>*</small></label>
                                        <form:textarea path="notification" id="notification" rows="5" cols="30" class="form-control" data-parsley-trigger="change" KeyboardLayout="HINDI" placeholder="Enter Address" required="" aria-required="true"/>
                                    </div>
                                    <div class="form-group">
                                        <label for="attachment"><spring:message code="lable.noti.attachment"/><small>*</small></label>
                                    </div>

                                    <div class="form-group">
                                        <label for="needReply"><spring:message code="lable.noti.need.reply"/><small>*</small></label>

                                        <form:checkbox path="needReply" id="needReply"  data-parsley-trigger="change"  required="" aria-required="true"/>

                                    </div>
                                    <div class="form-group">
                                        <label for="form_Id"><spring:message code="lable.noti.reply.data.form"/></label>
                                        <form:select id="form_Id" path="form_Id" class="form-control">
                                            <form:options items="${ListOfDays}"/>
                                        </form:select>
                                    </div>

                                    <div class="form-group">
                                        <label for="needReplyBefore"><spring:message code="lable.noti.reply.with.in"/></label>
                                        <form:select id="needReplyBefore" path="needReplyBefore" class="form-control">
                                            <form:options items="${ListOfDays}"/>
                                        </form:select>

                                    </div>

                                    <div class="form-group">
                                        <label for="needApprovalBefore"><spring:message code="lable.noti.approver.with.in"/></label>
                                        <form:select id="needApprovalBefore" path="needApprovalBefore" class="form-control">
                                            <form:options items="${ListOfDays}"/>
                                        </form:select>

                                    </div>
                                </div>

                            </div>    

                            <div class="col-md-6">
                                <div class="border-1px bg-white clearfix p-20 pt-10 pb-20">
                                    <div class="form-group">
                                        <label for="respondents"><spring:message code="lable.noti.respondent"/><small>*</small>&nbsp;:

                                            <b id="selectedDistrict">${NotificationBean.selectedDistric}</b>
                                            <b id="selectedProject">${NotificationBean.selectedProject}</b>
                                            <b id="selectedSector">${NotificationBean.selectedSector}</b>
                                            <form:hidden id="selDistHid" path="selectedDistric"/>
                                            <form:hidden id="selProjHid" path="selectedProject"/>
                                            <form:hidden id="selSectHid" path="selectedSector"/>

                                        </label>


                                    </div>
                                    <h4 class="mt-0 mb-30 line-bottom-center">
                                        <form:checkbox path="allDistrict" id="allDistrict"/> :
                                        <spring:message code="lable.noti.all.district"/> 
                                        <form:checkbox path="allProject" id="allProject"/>:
                                        <spring:message code="lable.noti.all.project"/>
                                        <form:checkbox path="allSector" id="allSector"/>:
                                        <spring:message code="lable.noti.all.sector"/>
                                    </h4>

                                    <div id="divDatatable" class="col-md-12 " style="margin-top: 5px">  
                                        <table id="datatable" class="stripetable row-border order-column table-condensed" >
                                            <thead>
                                                <tr>
                                                    <th>Select</th>
                                                    <th><spring:message code="lable.noti.respondent"/></th>
                                                    <th><spring:message code="name"/></th>

                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="respondantlst" items="${NotificationBean.repondantList}" varStatus="status">
                                                    <tr class="odd gradeX">
                                                        <td><form:checkbox path="repondantList[${status.index}].checked" value="${respondantlst.checked}"/></td>
                                                        <td><spring:message code="${respondantlst.officeLevel}"/></td>
                                                        <td><c:out value="${respondantlst.officeName}" /></td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                    <div class="form-group">
                                        <label for="approver"><spring:message code="lable.noti.approver"/></label>
                                        <form:select id="approver" path="approver" class="form-control">
                                            <form:option value="" label='Select' itemValue="0"/>
                                            <form:options items="${ApproverList}"/>

                                        </form:select>

                                    </div>
                                    <div class="form-group">
                                        <label for="detailsAbout"><spring:message code="lable.noti.about"/></label>
                                        <form:select id="detailsAbout" path="detailsAbout" class="form-control">
                                            <form:option value='' label='Select' itemValue="0"/> 
                                            <form:options items="${ApproverList}"/>

                                        </form:select>
                                    </div>

                                </div>
                            </div>
                            <div class="clearfix"></div>                    
                            <div class="text-center m-10">


                                <input type="submit" value="<spring:message code='save'/>" name="_save" onclick="javascript:return submitForm();" class="btn btn-dark btn-theme-colored btn-flat mr-5">
                                <input type="submit" value="<spring:message code='cancel'/>" name="_cancel" class="btn btn-dark btn-theme-colored btn-flat mr-5">
                                <input type="hidden" value="0" name="_page">

                            </div>
                        </form:form>         
                    </div> 
                </section>  
            </div>
            <!-- end main-content -->

            <!-- Footer -->
            <jsp:include page="../footer.jsp"></jsp:include>
            <a class="scrollToTop" href="#"><i class="fa fa-angle-up"></i></a>
        </div>
        <!-- end wrapper -->

    </body>
</html>
