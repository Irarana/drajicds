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
                            } else {
                                distCount++;
                            }
                        }
                        if ($('td:eq(1)', $(rows[i])).html() == 'Project') {
                            if (!$('input[type="checkbox"]', $(rows[i])).is(':checked')) {
                                projectFlag = true;
                            } else {
                                projCount++;
                            }
                        }
                         if ($('td:eq(1)', $(rows[i])).html() == 'Sector') {
                            if (!$('input[type="checkbox"]', $(rows[i])).is(':checked')) {
                                sectorFlag = true;
                            } else {
                                sectCount++;
                            }
                        }
                    }
                    if (distCount > 0) {
                        $('#selectedDistrict').html("");
                        $('#selectedDistrict').html("District(" + distCount + ")");
                        $('#selDistHid').val("District(" + distCount + ")");
                    } else {
                        $('#selectedDistrict').html("");
                        $('#selDistHid').val("");
                    }
                    if (projCount > 0) {
                        $('#selectedProject').html("");
                        $('#selectedProject').html("Project(" + projCount + ")");
                        $('#selProjHid').val("Project(" + projCount + ")");
                    } else {
                        $('#selectedProject').html("");
                        $('#selProjHid').val("");
                    }
                    if (sectCount > 0) {
                        $('#selectedSector').html("");
                        $('#selectedSector').html("Sector(" + sectCount + ")");
                        $('#selSectHid').val("Sector(" + sectCount + ")");
                    } else {
                        $('#selectedSector').html("");
                        $('#selSectHid').val("");
                    }
                    if ($('#allDistrict').is(":checked")) {
                        if (districtFlag) {
                            $('#allDistrict').prop('checked', false);
                            ;
                        }
                    } else {
                        if (!districtFlag) {
                            $('#allDistrict').prop('checked', true);
                            ;
                        }
                    }
                    if ($('#allProject').is(":checked")) {
                        if (projectFlag) {
                            $('#allProject').prop('checked', false);
                            ;
                        }
                    } else {
                        if (!projectFlag) {
                            $('#allProject').prop('checked', true);
                            ;
                        }
                    }
                     if ($('#allSector').is(":checked")) {
                        if (sectorFlag) {
                            $('#allSector').prop('checked', false);
                            ;
                        }
                    } else {
                        if (!sectorFlag) {
                            $('#allSector').prop('checked', true);
                            ;
                        }
                    }
                });

                $('#allProject').change(function () {
                    // alert($('#allProject').val());
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
                            }
                        }
                        if ($('#allProject').is(":checked")) {
                            tempVal = "Project(" + count + ")";
                        }
                        $('#selectedProject').html(tempVal);
                        $('#selProjHid').val(tempVal);
                    }
                });

                $('#allDistrict').change(function () {
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
                            }
                        }
                        if ($('#allDistrict').is(":checked")) {
                            tempVal = " District(" + count + ")";
                        }
                        $('#selectedDistrict').html(tempVal);
                        $('#selDistHid').val(tempVal);

                    }
                });
                $('#allSector').change(function () {
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
                            }
                        }
                        if ($('#allSector').is(":checked")) {
                            tempVal = " Sector(" + count + ")";
                        }
                        $('#selectedSector').html(tempVal);
                        $('#selSectHid').val(tempVal);

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
            function validate() {
                distSelVal = $("#selDistHid").val();
                projSelVal = $("#selProjHid").val();
                sectSelVal = $("#selSectHid").val();
                //alert(distSelVal);
                //alert(projSelVal);

                $('form#respondantForm').submit(function (e) {

                    if ($("#selDistHid").val() != '' || $("#selProjHid").val() != '' || $("#selSectHid").val() != '') {
                        // alert(action + $("#projectCode").val());
                        return true;
                    } else {
                        alert("Please select respondant.");
                        return false;
                    }
                });

            }




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
                        <div class="row">
                            <div class="col-md-6 col-md-push-3">
                                <form:form id="respondantForm"  method="POST" modelAttribute="NotificationBean"  >
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

                                    <p><b id="selectedProject">${NotificationBean.selectedDistric}</b>
                                        <b id="selectedDistrict">${NotificationBean.selectedProject}</b>
                                        <b id="selectedSector">${NotificationBean.selectedSector}</b>
                                        <form:hidden id="selDistHid" path="selectedDistric"/>
                                        <form:hidden id="selProjHid" path="selectedProject"/>
                                        <form:hidden id="selSectHid" path="selectedSector"/>
                                    </p>
                                </div>
                            </div>


                            <div class="clearfix"></div>                    
                            <div class="text-center m-10">

                                <input type="submit" value="<spring:message code='previous'/>" name="_target0" onclick="javascript:validate();" class="btn btn-dark btn-theme-colored btn-flat mr-5" >

                                <input type="hidden" value="1" name="_page">

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
