<%-- 
    Document   : TrainingCenterList
    Created on : Oct 4, 2017, 11:12:58 PM
    Author     : surendra
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html dir="ltr" lang="en">

    <head>

        <!-- Meta Tags -->
        <meta name="viewport" content="width=device-width,initial-scale=1.0"/>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
        <meta name="description" content="HelpingPro - Nonprofit, Crowdfunding & Charity HTML5 Template" />
        <meta name="keywords" content="charity,crowdfunding,nonprofit,orphan,Poor,funding,fundrising,ngo,children" />
        <meta name="author" content="ThemeMascot" />

        <!-- Page Title -->
        <title>Digital Raj - Integrated Child Development Services (ICDS) </title>

        <!-- Stylesheet -->
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
        <link href="css/stylesheet.css" rel="stylesheet" type="text/css"/>
        <!-- CSS | Theme Color -->
        <link href="css/colors/theme-skin-rose.css" rel="stylesheet" type="text/css">
        <link href="css/uikit.almost-flat.min.css" rel="stylesheet" type="text/css"/>
        <!-- external javascripts -->
        <script src="js/jquery-2.2.4.min.js"></script>
        <script src="js/jquery-ui.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <!-- JS | jquery plugin collection for this theme -->
        <script src="js/jquery-plugin-collection.js"></script>
        <link rel="stylesheet" type="text/css" href="js/datatable/datatable.css">
        <link href="css/main.min.css" rel="stylesheet" type="text/css"/>
        <!--<link rel="stylesheet" type="text/css" href="js/datatable/dataTables.bootstrap.min.css">-->
        <script type="text/javascript" src="js/datatable/jquery.dataTables.min.js"></script>
        <script type="text/javascript" src="js/datatable/datatable-bootstrap.js"></script>
        <script type="text/javascript" src="js/datatable/dataTables.fixedColumns.min.js"></script>
        <script type="text/javascript" src="js/datatable/dataTables.select.min.js"></script>
        <script src="js/custom.js"></script>

        <script type="text/javascript">
            $(document).ready(function() {

                var table = $('#datatable').DataTable({
                    scrollY: "300px",
                    scrollCollapse: true,
                    paging: true,
                    responsive: true,
                    select: true

                });



                $('#datatable tbody').on('click', 'tr', function() {
                    var rowData = table.row(this).data();
                    $("#workerId").val(rowData[0]);
                });

                $('#districtCode').change(function() {
                    if ($('#districtCode').val() != '') {
                        $.ajax({
                            type: "GET",
                            url: "getProjectForDistrict.htm",
                            data: {distCode: $('#districtCode').val()},
                            success: function(data) {
                                var keys = Object.keys(data);
                                $('#projectCode').empty();
                                $('#sectorCode').empty();
                                $('#centerCode').empty();
                                if ($('#userlevel').val() == 'STATE') { //
                                    newOption = '<option value="">ALL</option>';
                                    $('#districtCode').append(newOption);
                                    $('#projectCode').append(newOption);
                                    $('#sectorCode').append(newOption);
                                    $('#centerCode').append(newOption);
                                }
                                for (var i = 0; i < keys.length; i++) {
                                    var key = keys[i];
                                    console.log(key, data[key]);
                                    var newOption = $('<option value="' + key + '">' + data[key] + '</option>');
                                    $('#projectCode').append(newOption);
                                }

                            },
                            error: function() {
                                alert('Error occured');
                            }
                        });
                    }
                });

                $('#projectCode').change(function() {
                    if ($('#projectCode').val() != '') {
                        $.ajax({
                            type: "GET",
                            url: "getSectorForProject.htm",
                            data: {projectCode: $('#projectCode').val()},
                            success: function(data) {
                                var keys = Object.keys(data);
                                $('#sectorCode').empty();
                                $('#centerCode').empty();
                                if ($('#userlevel').val() == 'STATE' || $('#userlevel').val() == 'DIST') {
                                    newOption = '<option value="">ALL</option>';
                                    $('#projectCode').append(newOption);
                                    $('#sectorCode').append(newOption);
                                    $('#centerCode').append(newOption);
                                }
                                for (var i = 0; i < keys.length; i++) {
                                    var key = keys[i];
                                    console.log(key, data[key]);
                                    var newOption = $('<option value="' + key + '">' + data[key] + '</option>');
                                    $('#sectorCode').append(newOption);
                                }

                            },
                            error: function() {
                                alert('Error occured');
                            }
                        });
                    }

                });

                $('#sectorCode').change(function() {
                    if ($('#projectCode').val() != '') {
                        $.ajax({
                            type: "GET",
                            url: "getCenterForSector.htm",
                            data: {sectorCode: $('#sectorCode').val()},
                            success: function(data) {
                                var keys = Object.keys(data);
                                $('#centerCode').empty();
                                if ($('#userlevel').val() == 'STATE' || $('#userlevel').val() == 'DIST' || $('#userlevel').val() == 'PROJECT') {
                                    newOption = '<option value="">ALL</option>';
                                    $('#centerCode').append(newOption);
                                }
                                for (var i = 0; i < keys.length; i++) {
                                    var key = keys[i];
                                    console.log(key, data[key]);
                                    var newOption = $('<option value="' + key + '">' + data[key] + '</option>');
                                    $('#centerCode').append(newOption);
                                }

                            },
                            error: function() {
                                alert('Error occured');
                            }
                        });
                    }
                });
                /*
                $('#centerCode').change(function() {
                    if ($('#sectorCode').val() == '') {
                        alert('Please Select Sector !');
                        $('#centerCode').empty();
                    }
                });
                */
            });


            function validate() {
                return true;
            }




            function editmasterdata() {
                workerId = $("#workerId").val();
                if (workerId != null) {
                    location.href = "frontlineWorkerControllerEdit.htm?workerId=" + workerId;
                } else {
                    alert('Please select a row to Edit details.');
                }
            }
            function viewmsterdata() {
                workerId = $("#workerId").val();
                if (workerId != '') {
                    location.href = "frontlineWorkerControllerView.htm?workerId=" + workerId;
                } else {
                    alert('Please select a row to View details.');
                }
            }


        </script>
        <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
          <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->

    </head>
    <body class="boxed-layout pt-40 pb-40 pt-sm-0" data-bg-img="images/pattern/p37.png">
        <form:form id="Frontline_Form" method="POST" action="frontlineWorkerList.htm" commandName="command" >
            <form:hidden path="userlevel" id="userlevel"/>
            <input type="hidden" id="workerId" name="workerId"/>
            <div id="wrapper" class="clearfix">
                <jsp:include page="header.jsp"></jsp:include>
                    <!-- Start main-content -->
                    <div class="main-content">
                        <!-- Section: inner-header -->
                        <section class="inner-header divider layer-overlay overlay-dark-8" data-bg-img="images/bg/bg2.jpg">
                            <div class="container pt-90 pb-40">
                                <!-- Section Content -->
                                <div class="section-content">
                                    <div class="row"> 
                                        <div class="col-md-6">
                                            <h2 class="text-white font-36"><spring:message code="label.mn.fw"/></h2>
                                            <ol class="breadcrumb text-left mt-10 white">
                                                <li><a href="index-mp-layout1.html">Home</a></li>
                                                <li><a href="#">Pages</a></li>
                                                <li class="active"><spring:message code="label.mn.fw"/></li>
                                            </ol>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </section>

                        <section>

                            <div class="container">
                                <div class="icon-box left media bg-silver-light border-1px p-15 mb-20">
                                    <form>
                                        <div class="row">
                                            <div class="col-md-3">
                                                <div class="form-group">
                                                    <label class="col-md-4">District</label>
                                                    <div class="col-md-8">
                                                    <form:select id="districtCode" path="districtCode" class="form-control">
                                                        <c:if test = "${command.userlevel == 'STATE'}"> 
                                                            <option value=""> ALL </option>
                                                        </c:if>
                                                        <form:options items="${DistrictList}"/>
                                                    </form:select>
                                                </div>
                                                <div class="clearfix"></div>
                                            </div>
                                        </div> 
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label class="col-md-4">Project</label>
                                                <div class="col-md-8">
                                                    <form:select id="projectCode" path="projectCode" class="form-control">
                                                        <c:if test = "${(command.userlevel == 'STATE') || (command.userlevel == 'DIST')}"> 
                                                            <option value=""> ALL </option>
                                                        </c:if>
                                                        <form:options items="${ProjectListDistrictWise}"/>
                                                    </form:select>
                                                </div>
                                                <div class="clearfix"></div>
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label class="col-md-4">Sector</label>
                                                <div class="col-md-8">
                                                    <form:select id="sectorCode" path="sectorCode" class="form-control">
                                                        <c:if test = "${(command.userlevel == 'STATE') || (command.userlevel == 'DIST') || (command.userlevel == 'PROJECT')}"> 
                                                            <option value=""> ALL </option>
                                                        </c:if>
                                                        <form:options items="${SectorListProjectWise}"/>
                                                    </form:select>
                                                </div>
                                                <div class="clearfix"></div>
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label class="col-md-4">Anganwadi Center</label>
                                                <div class="col-md-8">
                                                    <form:select id="centerCode" path="centerCode" class="form-control">
                                                        <c:if test = "${(command.userlevel == 'STATE') || (command.userlevel == 'DIST') || (command.userlevel == 'PROJECT') || (command.userlevel == 'SECTOR')}"> 
                                                            <option value=""> ALL </option>
                                                        </c:if>
                                                        <form:options items="${CenterListSectorWise}"/>
                                                    </form:select>
                                                </div>
                                                <div class="clearfix"></div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">

                                        <div class="col-md-3 pull-right">

                                            <button type="submit" class="btn btn-colored btn-sm btn-theme-colored pull-right">Submit</button>
                                        </div>
                                    </div>
                                </form>
                            </div>

                            <div class="panel-group toggle">
                                <div class="panel">
                                    <div class="panel-heading md-bg-pink-A200">
                                        <div class="panel-title"> <a data-parent="#accordion1" data-toggle="collapse" href="#toggle11" class="collapsed" aria-expanded="false"> <span class="open-sub pull-right"></span>Dashboard</a> </div>
                                    </div>
                                    <div class="panel-body">
                                        <div class="row">
                                            <div class="col-sm-3">
                                                <div class="md-card md-bg-pink-A200 uk-text-contrast">
                                                    <div class="md-card-content">
                                                        <div class="uk-float-right uk-margin-top uk-margin-small-right"><h2><i class="fa fa-commenting-o  md-color-white"></i></h2></div>
                                                        <h3 class="md-color-grey-900 uk-margin-remove">Anganwadi Worker</h3>
                                                        <h2 class="uk-margin-remove"><span class="countUpMe"><c:out value="${box1}"/></span></h2>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-sm-3">
                                                <div class="md-card md-bg-lime-A700 uk-text-contrast">
                                                    <div class="md-card-content">
                                                        <div class="uk-float-right uk-margin-top uk-margin-small-right"><h2><i class="fa fa-commenting-o  md-color-white"></i></h2></div>
                                                        <h3 class="md-color-grey-900 uk-margin-remove">Mini AWW</h3>
                                                        <h2 class="uk-margin-remove"><span class="countUpMe"><c:out value="${box2}"/></span></h2>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-sm-3">
                                                <div class="md-card md-bg-amber-700 uk-text-contrast">
                                                    <div class="md-card-content">
                                                        <div class="uk-float-right uk-margin-top uk-margin-small-right"><h2><i class="fa fa-commenting-o  md-color-white"></i></h2></div>
                                                        <h3 class="md-color-grey-900 uk-margin-remove">Helper</h3>
                                                        <h2 class="uk-margin-remove"><span class="countUpMe"><c:out value="${box3}"/></span></h2>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-sm-3">
                                                <div class="md-card md-bg-teal-A400 uk-text-contrast">
                                                    <div class="md-card-content">
                                                        <div class="uk-float-right uk-margin-top uk-margin-small-right"><h2><i class="fa fa-commenting-o  md-color-white"></i></h2></div>
                                                        <h3 class="md-color-grey-900 uk-margin-remove">Asha</h3>
                                                        <h2 class="uk-margin-remove"><span class="countUpMe"><c:out value="${box4}"/></span></h2>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                    </div>
                                </div>
                                <div id="toggle11" class="panel-collapse collapse">
                                    <div class="panel-body">
                                        <!--<p> Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson ad squid. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quia, quae, fuga!</p>-->
                                    </div>
                                </div>
                            </div>

                            <div class="pull-right btn-group btn-group-sm" style="margin-bottom: 5px">

                                <a href="frontLineWorkerEntry.htm">
                                    <button class="btn btn-colored btn-sm btn-theme-colored" type="button">
                                        Add 
                                    </button>
                                </a>
                                <a href="javascript:editmasterdata()" >
                                    <button class="btn btn-colored btn-sm btn-theme-colored" type="button">
                                        Edit 
                                    </button>
                                </a>

                                <a href="javascript:viewmsterdata()" >
                                    <button class="btn btn-colored btn-sm btn-theme-colored" type="button">
                                        View 
                                    </button>
                                </a>

                            </div>
                            <div class="clearfix"></div>
                            <div id="divDatatable" class="col-md-12">  
                                <table id="datatable" class="stripetable row-border order-column table-condensed" >
                                    <thead>
                                        <tr>
                                            <th>Worker Id</th>
                                            <th>Worker Name</th>
                                            <th>Worker Designation</th>
                                            <th>AWC Type</th>
                                            <th>%Filled</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="fwlist" items="${FrontlineWorkerList}">
                                            <tr class="odd gradeX">
                                                <td><c:out value="${fwlist.workerId}"/></td>
                                                <td> <c:out value="${fwlist.workerName}"/></td>
                                                <td><c:out value="${fwlist.workerDesig}"/></td>
                                                <td><c:out value="${fwlist.awcType}"/></td>
                                                <td>30%</td>
                                            </tr>

                                        </c:forEach>


                                    </tbody>
                                </table>
                            </div>
                        </div> 
                    </section>  
                </div>
                <!-- end main-content -->

                <!-- Footer -->
                <jsp:include page="footer.jsp"></jsp:include>
                    <a class="scrollToTop" href="#"><i class="fa fa-angle-up"></i></a>
                </div>
                <!-- end wrapper -->
        </form:form>
    </body>
</html>
