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
            $(document).ready(function () {

                var table = $('#datatable').DataTable({
                    scrollY: "300px",
                    scrollCollapse: true,
                    paging: true,
                    responsive: true,
                    select: true


                });



                $('#datatable tbody').on('click', 'tr', function () {
                    var rowData = table.row(this).data();
                    $("#postName").val(rowData[0]);
                    $("#projectName").val(rowData[1]);
                    var id = $("#table tr.selected td:first input").attr('value');
                    alert("Hi" + $("#postName").val());
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



            function sancJoinIncumbency() {
                var post = $("#postName").val();
                if (post != '') {


                    location.href = "icdsJoinIncumbency.htm?incId=null&postName=" + post;
                } else {
                    alert('Please select a row to View details.');
                }
            }
            function sancRelieveIncumbency() {
                var post = $("#postName").val();
                 projName = $("#projectName").val();
                 projCode = $("#projectCode").val();
                alert($("#projectName").val());
                alert(projName);
                
                //alert($("#projectCode").val());
                //var incumbencyId = $("#projectName").val();
                var incumbencyId = 5;
                if (post != '') {
                    location.href = "icdsRelieveIncumbency.htm?incId="+incumbencyId+"&postName="+post+"&projectName="+projName+"&projectCode="+projCode;
                } else {
                    alert('Please select a row to View details.');
                }
            }

            function editmasterdata() {
                projectCode = $("#projectCode").val();
                if (projectCode != '') {
                    location.href = "icdsProjectView.htm?projCode=" + projectCode;
                } else {
                    alert('Please select a row to View details.');
                }
            }
            function viewmsterdata() {
                projectCode = $("#projectCode").val();
                if (projectCode != '') {
                    location.href = "icdsProjectView.htm?projCode=" + projectCode;
                } else {
                    alert('Please select a row to View details.');
                }
            }
            function deleteProject() {
                projectCode = $("#projectCode").val();
                if (projectCode != '') {
                    var retVal = confirm("Do you want to delete this project?");
                    if (retVal == true) {
                        location.href = "deleteICDSProject.htm?projectCode=" + projectCode;
                        return true;
                    }

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
        <form:form id="ICDS_Form" method="POST" action="icdsProjectList.htm" commandName="command" >
            <form:hidden path="userlevel" id="userlevel"/>
            <form:hidden path="projectName" id="projectName" value="${projectName}"/>
            <form:hidden path="projectCode" id="projectCode" value="${projectCode}"/>
            
            <input type="hidden" id="postName" name="postName"/>

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
                                            <h2 class="text-white font-36"><spring:message code="label.mn.icds.projects"/></h2>
                                        <ol class="breadcrumb text-left mt-10 white">
                                        <li><a href="homepage.htm"><spring:message code="home"/></a></li>
                                        <li><a href="homepage.htm"><spring:message code="lable.icds.units"/></a></li>
                                        <li><a href="icdsProjectList.htm"><spring:message code="${MenuLevel}"/></a></li>
                                        <li><a href="javascript:SanPostList()"><spring:message code="label.icds.project.sanction.post"/></a></li>

                                        <li class="active"><spring:message code="list"/></li>
                                    </ol>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </section>

                    <section>

                        <div class="container">
                            <div class="icon-box left media  p-15 mb-20">
                                <form>
                                    <div class="row label-info">
                                        <div class="col-md-12">
                                            <div class="form-group">
                                                <label class="col-md-12">Sanction Post Details</label>

                                            </div>                                           
                                        </div>                          
                                    </div>
                                    <div class="row" style="margin-top: 5px">
                                        <div class="clearfix"></div>
                                        <div class="col-md-3">
                                            <div class="form-group">

                                                <li>District : ${DistrictName}</li>
                                                <li>Project : ${projectName} (${projectCode})</li>


                                            </div>
                                        </div> 


                                    </div>

                                </form>
                            </div>


                            <div class="pull-right btn-group btn-group-sm" >

                                <a href="frontLineWorkerEntry.htm">
                                    <button class="btn btn-colored btn-sm btn-theme-colored" type="button">
                                        Save
                                    </button>
                                </a>
                                <a href="icdsSanctionEntry.htm?ProjectName=${ProjectName} & ProjectCode=${ProjectCode}" >
                                    <button class="btn btn-colored btn-sm btn-theme-colored" type="button">
                                        New Sanction
                                    </button>
                                </a>
                                <a href="javascript:sancJoinIncumbency()" >
                                    <button class="btn btn-colored btn-sm btn-theme-colored" type="button">
                                        Join
                                    </button>
                                </a>
                                <a href="javascript:sancRelieveIncumbency()" >
                                    <button class="btn btn-colored btn-sm btn-theme-colored" type="button">
                                        Reliving
                                    </button>
                                </a>
                                <a href="icdsSancJoinIncumbency.htm" >
                                    <button class="btn btn-colored btn-sm btn-theme-colored" type="button">
                                        Incumbency Chart
                                    </button>
                                </a>


                            </div>
                            <div class="clearfix"></div>
                            <div id="divDatatable" class="col-md-12 " style="margin-top: 5px">  
                                <table id="datatable" class="stripetable row-border order-column table-condensed" >
                                    <thead>
                                        <tr>
                                            <th>Post</th>
                                            <th>Sanction Order</th>
                                            <th>Status</th>
                                            <th>Staffing in Position</th>

                                            <th></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="icdslist" items="${ICDSSanctionList}">
                                            <tr class="odd gradeX">
                                        <input name="id" value="${icdslist.incumbencyId}" hidden>
                                        <td>

                                            <c:out value="${icdslist.sanctionPostName}" /></td>
                                        <td> <c:out value="${icdslist.sanctionOrderNo}"/></td>
                                        <td><c:out value="${icdslist.sanctionPostOccupied}"/></td>
                                        <td><c:out value="${icdslist.staffingName}"/></td>

                                        <td><div class="pull-right btn-group btn-group-sm" style="margin-bottom: 5px">


                                                <a href="javascript:editmasterdata()" >
                                                    <button class="btn btn-colored btn-sm btn-theme-colored" type="button">
                                                        Edit 
                                                    </button>
                                                </a>


                                            </div></td>
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
                <jsp:include page="../footer.jsp"></jsp:include>
                    <a class="scrollToTop" href="#"><i class="fa fa-angle-up"></i></a>
                </div>
                <!-- end wrapper -->
        </form:form>
    </body>
</html>
