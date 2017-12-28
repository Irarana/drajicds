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
                    select: true,
                    columnDefs: [
                        {
                            targets: [0],
                            visible: false,
                            searchable: false
                        }
                    ]
                });



                $('#datatable tbody').on('click', 'tr', function () {
                    var rowData = table.row(this).data();
                    $("#projectCode").val(rowData[1]);
                    $("#projectName").val(rowData[2]);
                    $("#reportingOffice").val(rowData[0]);
                    // $("#substattivePostId").val(rowData[0]);

                    // alert("Hi" + $("#reportingOffice").val());
                    // alert("Hi" + $("#projectCode").val());
                });

                $('#districtCode').change(function () {
                    if ($('#districtCode').val() != '') {
                        $.ajax({
                            type: "GET",
                            url: "getProjForDis.htm",
                            data: {distCode: $('#districtCode').val()},
                            success: function (data) {
                                var keys = Object.keys(data);
                                $('#selectedProjectId').empty();
                                var newOption = '<option value="">ALL</option>';
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


            function formSubmit(action) {
                // alert("outside >> " +  $("#projectCode").val());
                if (action == 'Edit') {
                    $('form#ICDS_Form').attr('action', 'icdsProjectEdit.htm');
                } else if (action == 'View') {
                    $('form#ICDS_Form').attr('action', 'icdsProjectView.htm');
                }
                else if (action == 'Delete') {
                    $('form#ICDS_Form').attr('action', 'deleteICDSProject.htm');

                } else if (action == 'SanctionPost') {
                    //alert(action + $("#projectCode").val());
                    if ($("#projectCode").val() == '') {
                        alert("Please select row.");
                    }
                    $('form#ICDS_Form').attr('action', 'icdsSanctionPostList.htm');
                    $('form#ICDS_Form').submit(function (e) {

                        if ($("#projectCode").val() != '') {
                            // alert(action + $("#projectCode").val());
                            return true;
                        } else {

                            return false;
                        }
                    });

                } else if (action == 'CreateProject') {
                    $('form#ICDS_Form').attr('action', 'icdsProjectEntry.htm');
                    $('form#ICDS_Form').submit();
                    return true;
                }
                // alert("before submit");
                $('form#ICDS_Form').submit();


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
        <form:form id="ICDS_Form" method="POST"  commandName="command" >
            <form:hidden path="userlevel" id="userlevel"/>
            <form:hidden path="districtName" id="districtName" value="${districtName}" />
            <form:hidden path="projectName" id="projectName"  />
            <form:hidden path="projectCode" id="projectCode"  />
            <form:hidden path="level" id="level" value="${MenuLevel}" />
            <form:hidden path="reportingOffice" id="reportingOffice"  />

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
                                            <h2 class="text-white font-36"><spring:message code="label.mn.icds"/>&nbsp;<spring:message code="${MenuLevel}"/> </h2>
                                        <ol class="breadcrumb text-left mt-10 white">
                                            <li><a href="homepage.htm"><spring:message code="home"/></a></li>
                                            <li><a href="#"><spring:message code="lable.icds.units"/></a></li>
                                            <li class="active"><spring:message code="label.icds.project.sanction.post.List"/></li>
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
                                    <div>
                                        <h4 class="text-danger">
                                        <c:if test="${not empty message}">
                                                <spring:message code="${message}"/>
                                        </c:if>
                                         <c:if test="${not empty extention}">
                                               &nbsp;${extention}
                                         </c:if>
                                        </h4>
                                    </div>
                                    <div class="clearfix"></div>
                                    <div class="row" style="margin-top: 5px">
                                        <div class="clearfix"></div>
                                        <c:if test="${MenuLevel ne 'DIST'}">
                                            <div class="col-md-3">
                                                <div class="form-group">
                                                    <label class="col-md-4"><spring:message code="DIST"/></label>

                                                    <div class="col-md-8">
                                                        <form:select id="districtCode" path="districtCode" class="form-control">

                                                            <form:option value='ALL' label='All' itemValue="0"/>
                                                            <form:options items="${DistrictList}"/>
                                                        </form:select>
                                                    </div>


                                                </div>
                                            </div> 
                                        </c:if>
                                        <c:if test="${MenuLevel eq 'DIST'}">
                                            <form:hidden path="districtCode" id="districtCode" value="${districtCode}"/>
                                        </c:if>

                                        <c:if test="${MenuLevel eq 'SECTOR'}">

                                            <div class="col-md-3">
                                                <div class="form-group">
                                                    <label class="col-md-4"><spring:message code="PROJECT"/></label>

                                                    <div class="col-md-8">
                                                        <form:select id="selectedProjectId" path="selectedProjectId" class="form-control">
                                                            <c:if test="${ProjectList.size()>0}">
                                                                <form:option value='All' label='All' itemValue="0"/>
                                                            </c:if>
                                                            <c:if test="${ProjectList.size()<=0}">
                                                                <form:option value='' label='Select' itemValue="0"/>
                                                            </c:if>
                                                            <form:options items='${ProjectList}'/>
                                                        </form:select>
                                                    </div>


                                                </div>
                                            </div> 
                                        </c:if>
                                        <div class="col-md-1" >
                                            <c:if test="${MenuLevel ne 'DIST'}">
                                                <div class="form-group">
                                                    <button type="submit" class="btn btn-colored btn-sm btn-theme-colored pull-right">OK</button>
                                                    <div class="clearfix"></div>
                                                </div>
                                            </c:if>
                                        </div>

                                    </div>

                                </form>
                            </div>

                            <div class="pull-right btn-group btn-group-sm" >
                                <input type="submit" value="<spring:message code="sanction.post"/>" name = "icdsFormAction"   onclick="javascript:formSubmit('SanctionPost');" class="btn btn-dark btn-theme-colored btn-flat mr-5" data-loading-text="Please wait..."/>
                                <input type="submit" value="<spring:message code="create"/> <spring:message code="${MenuLevel}"/>" name = "icdsFormAction"   onclick="javascript:formSubmit('CreateProject');" class="btn btn-dark btn-theme-colored btn-flat mr-5" data-loading-text="Please wait..."/>




                            </div>
                            <div class="clearfix"></div>
                            <div id="divDatatable" class="col-md-12 " style="margin-top: 5px">  
                                <table id="datatable" class="stripetable row-border order-column table-condensed" >
                                    <thead>
                                        <tr>
                                            <th>Reporting Office ID</th>
                                            <th><spring:message code="lable.code"/></th>
                                            <th><spring:message code="${MenuLevel}"/></th>
                                            <th><spring:message code="head.of.office"/></th>
                                            <th></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="icdslist" items="${ICDSPorjectList}">
                                            <tr class="odd gradeX">
                                                <td><c:out value="${icdslist.reportingOffice}"/></td>
                                                <td><c:out value="${icdslist.projectCode}"/></td>
                                                <td> <c:out value="${icdslist.projectName}"/></td>
                                                <td><c:out value="${icdslist.headOfOffice}"/></td>
                                                <td><div class="pull-right btn-group btn-group-sm" style="margin-bottom: 5px">

                                                        <input type="submit" value=" <spring:message code="edit"/> " name = "icdsFormAction"   onclick="javascript:formSubmit('Edit');" class="btn btn-dark btn-theme-colored btn-flat mr-5" data-loading-text="Please wait..."/>
                                                        <input type="submit" value=" <spring:message code="delete"/> " name = "icdsFormAction"   onclick="javascript:formSubmit('Delete');" class="btn btn-dark btn-theme-colored btn-flat mr-5" data-loading-text="Please wait..."/>
                                                        <input type="submit" value=" <spring:message code="view"/> " name = "icdsFormAction"   onclick="javascript
                                                                :formSubmit('View');" class="btn btn-dark btn-theme-colored btn-flat mr-5" data-loading-text="Please wait..."/>
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
