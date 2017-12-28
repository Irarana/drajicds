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
                        },
                        {
                            targets: [1],
                            visible: false,
                            searchable: false
                        },
                        {
                            targets: [2],
                            visible: false,
                            searchable: false
                        },
                        {
                            targets: [7],
                            visible: false,
                            searchable: false
                        },
                        {
                            targets: [8],
                            visible: false,
                            searchable: false
                        },
                        {
                            targets: [9],
                            visible: false,
                            searchable: false
                        }

                    ]
                });



                $('#datatable tbody').on('click', 'tr', function () {
                    $("#joinIncumbency").prop("disabled", false);
                    $("#relieveIncumbency").prop("disabled", false);

                    var rowData = table.row(this).data();
                    $("#postName").val(rowData[3]);
                    $("#incumbencyId").val(rowData[0]);
                    $("#postCode").val(rowData[1]);
                    $("#trackID").val(rowData[2]);
                    $("#joiningDate").val(rowData[7]);
                    $("#relevingDate").val(rowData[8]);
                    $("#substattivePostId").val(rowData[9]);
                    if ($("#joiningDate").val() != '') {
                        $("#joinIncumbency").prop("disabled", true);
                        $("#relieveIncumbency").prop("disabled", false);
                    }

                    if (($("#relevingDate").val() == '' && $("#joiningDate").val() == '')) {
                        $("#relieveIncumbency").prop("disabled", true);
                    }


                    // alert("Hi" + rowData[7]);
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
            function sancEditPost() {
                var post = $("#postName").val();
                var incId = $("#incumbencyId").val();
                var postCode = $("#postCode").val();
                var projName = $("#projectName").val();
                var projCode = $("#projectCode").val();
                var trackId = $("#trackID").val();
                if (post != '') {
                    location.href = "icdsEditSanctionEntry.htm?incId=" + incId + "&postName=" + post + "&postCode=" + postCode + "&projectCode=" + projCode + "&projectName=" + projName + "&trackId=" + trackId;
                } else {
                    alert('Please select a row to View details.');
                }
            }


            function sancJoinIncumbency() {
                var post = $("#postName").val();
                var incId = $("#incumbencyId").val();
                var postCode = $("#postCode").val();
                var projName = $("#projectName").val();
                var projCode = $("#projectCode").val();
                var trackId = $("#trackID").val();
                var relevingDate = $("#relevingDate").val();
                var joinDate = $("#joiningDate").val();
                if (post != '') {
                    if (joinDate == '' && relevingDate == '') {
                        location.href = "icdsJoinIncumbency.htm?incId=" + incId + "&postName=" + post + "&postCode=" + postCode + "&projectCode=" + projCode + "&projectName=" + projName + "&trackId=" + trackId;
                    } else {
                        alert('Already have Incumbency.');
                        $("#joinIncumbency").prop("disabled", true);
                    }
                } else {
                    alert('Please select a row to View details.');
                }
            }
            function sancRelieveIncumbency() {
                var post = $("#postName").val();
                var incId = $("#incumbencyId").val();
                var postCode = $("#postCode").val();
                var projName = $("#projectName").val();
                var projCode = $("#projectCode").val();
                var joinDate = $("#joiningDate").val();
                //  alert(joinDate);

                //alert($("#projectCode").val());
                //var incumbencyId = $("#projectName").val();
                if (post != '') {

                    if ((incId != null || incId != '') && joinDate != '') {
                        location.href = "icdsRelieveIncumbency.htm?incId=" + incId + "&postName=" + post + "&postCode=" + postCode + "&projectCode=" + projCode + "&projectName=" + projName;
                    } else {
                        alert('Please Join first.');
                        //$("#joinIncumbency").prop("disabled",true);
                    }
                } else {
                    alert('Please select a row to View details.');
                }
            }

            function sancIncumbencyChart() {
                var post = $("#postName").val();
                var incId = $("#incumbencyId").val();
                var postCode = $("#postCode").val();
                var projName = $("#projectName").val();
                var projCode = $("#projectCode").val();
                var substattivePostId = $("#substattivePostId").val();


                if (post != '') {
                    location.href = "icdsIncumbencyChart.htm?incId=" + incId + "&postName=" + post + "&postCode=" + postCode + "&projectCode=" + projCode + "&projectName=" + projName + "&substattivePostId=" + substattivePostId;
                } else {
                    alert('Please select a row to View details.');
                }
            }

            function sancSave() {
                var post = $("#postName").val();
                var incId = $("#incumbencyId").val();
                var postCode = $("#postCode").val();
                var projName = $("#projectName").val();
                var projCode = $("#projectCode").val();

                location.href = "icdsSanSave.htm?incId=" + incId + "&postName=" + post + "&postCode=" + postCode + "&projectCode=" + projCode + "&projectName=" + projName;
            }

            function editmasterdata() {
                projectCode = $("#projectCode").val();
                if (projectCode != '') {
                    location.href = "icdsProjectEdit.htm?projCode=" + projectCode;
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
        <form:form id="ICDS_Form" method="POST" action="icdsSancPostSave.htm" commandName="command" >
            <form:hidden path="userlevel" id="userlevel"/>
            <form:hidden path="projectName" id="projectName" value="${projectName}"/>
            <form:hidden path="projectCode" id="projectCode" value="${projectCode}"/>
            <input type="hidden" id="postName" name="postName"/>
            <input type="hidden" id="incumbencyId" name="incumbencyId"/>
            <input type="hidden" id="postCode" name="postCode"/>
            <input type="hidden" id="trackID" name="trackID"/>
            <input type="hidden" id="joiningDate" name="joiningDate"/>
            <input type="hidden" id="relevingDate" name="relevingDate"/>
            <input type="hidden" id="substattivePostId" name="substattivePostId"/>



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
                                            <h2 class="text-white font-36"><spring:message code="label.icds.project.sanction.post.details"/></h2>
                                        <ol class="breadcrumb text-left mt-10 white">
                                            <li><a href="homepage.htm"><spring:message code="home"/></a></li>
                                            <li><a href="homepage.htm"><spring:message code="lable.icds.units"/></a></li>
                                            <li><a href="icdsProjectList.htm"><spring:message code="${MenuLevel}"/></a></li>
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
                                        <div class="col-md-12">
                                            <div class="form-group">
                                                <c:if test="${MenuLevel eq 'PROJECT'}">
                                                    <li><B><spring:message code="DIST"/> :</B> ${districtName}</li>
                                                    <li><B><spring:message code="PROJECT"/> :</B> ${projectName} (${projectCode})</li>
                                                        </c:if>
                                                        <c:if test="${MenuLevel eq 'DIST'}">
                                                    <li><B><spring:message code="DIST"/>  :</B> ${projectName} (${projectCode})</li>

                                                </c:if>
                                                <c:if test="${MenuLevel eq 'SECTOR'}">
                                                    <li><B><spring:message code="DIST"/>  :</B> ${districtName}</li>
                                                    <li><B><spring:message code="PROJECT"/> :</B> ${SelectedProjectName} (${SelectedProjectCode})</li>
                                                    <li><B><spring:message code="SECTOR"/> :</B> ${projectName} (${projectCode})</li>
                                                        </c:if>

                                            </div>
                                        </div> 


                                    </div>

                                </form>
                            </div>
                            <c:if test='${save}'>
                                <div class="pull-right btn-group btn-group-sm" >

                                    <!--                                <input type="submit" value="Save" name = "icdsPostSave"   class="btn btn-dark btn-theme-colored btn-flat mr-5" data-loading-text="Please wait..."/>-->

                                    <a href="javascript:sancSave()" >
                                        <button class="btn btn-colored btn-sm btn-theme-colored"  type="button">
                                            <spring:message code="save"/>
                                        </button>
                                    </a>
                                    <a href="icdsSanctionEntry.htm?projectName=${projectName}&projectCode=${projectCode}" >
                                        <button class="btn btn-colored btn-sm btn-theme-colored" disabled type="button">
                                            <spring:message code="new.sanction"/>
                                        </button>
                                    </a>
                                    <a href="javascript:sancJoinIncumbency()" >
                                        <button class="btn btn-colored btn-sm btn-theme-colored" disabled type="button">
                                            <spring:message code="join"/>
                                        </button>
                                    </a>
                                    <a href="javascript:sancRelieveIncumbency()" >
                                        <button class="btn btn-colored btn-sm btn-theme-colored" disabled type="button">
                                            <spring:message code="relieving"/>
                                        </button>
                                    </a>
                                    <a href="javascript:sancIncumbencyChart()" >
                                        <button class="btn btn-colored btn-sm btn-theme-colored" disabled   type="button">
                                            <spring:message code="incumbency.chart"/>
                                        </button>
                                    </a>


                                </div>
                            </c:if>
                            <c:if test='${other}'>
                                <div class="pull-right btn-group btn-group-sm" >

                                    <!--                                <input type="submit" value="Save" name = "icdsPostSave"   class="btn btn-dark btn-theme-colored btn-flat mr-5" data-loading-text="Please wait..."/>-->

                                    <a href="javascript:sancSave()" >
                                        <button class="btn btn-colored btn-sm btn-theme-colored" disabled  type="button">
                                            <spring:message code="save"/>
                                        </button>
                                    </a>
                                    <a href="icdsSanctionEntry.htm?projectName=${projectName}&projectCode=${projectCode}" >
                                        <button class="btn btn-colored btn-sm btn-theme-colored"  type="button">
                                            <spring:message code="new.sanction"/>
                                        </button>
                                    </a>
                                    <a href="javascript:sancJoinIncumbency()" >
                                        <button class="btn btn-colored btn-sm btn-theme-colored" id="joinIncumbency" type="button">
                                            <spring:message code="join"/>
                                        </button>
                                    </a>
                                    <a href="javascript:sancRelieveIncumbency()" >
                                        <button class="btn btn-colored btn-sm btn-theme-colored" id="relieveIncumbency" type="button">
                                            <spring:message code="relieving"/>
                                        </button>
                                    </a>
                                    <a href="javascript:sancIncumbencyChart()" >
                                        <button class="btn btn-colored btn-sm btn-theme-colored"   type="button">
                                            <spring:message code="incumbency.chart"/>
                                        </button>
                                    </a>


                                </div>
                            </c:if>

                            <div class="clearfix"></div>
                            <div id="divDatatable" class="col-md-12 " style="margin-top: 5px">  
                                <table id="datatable" class="stripetable row-border order-column table-condensed" >
                                    <thead>
                                        <tr>
                                            <th>Incumbency Id</th>
                                            <th>Post Code</th>
                                            <th>Track Id</th>
                                            <th><spring:message code="post"/></th>
                                            <th><spring:message code="sanction.order"/></th>
                                            <th><spring:message code="status"/></th>
                                            <th><spring:message code="staffing.in.position"/></th>
                                            <th>Joining date</th>
                                            <th>Releving date</th>
                                            <th>SubstattivePostId</th>
                                                <c:if test='${other}'>
                                                <th></th>
                                                </c:if>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="icdslist" items="${ICDSSanctionList}">
                                            <tr class="odd gradeX">
                                                <td><c:out value="${icdslist.incumbencyId}" /></td>
                                                <td><c:out value="${icdslist.sanctionPostCode}" /></td>
                                                <td><c:out value="${icdslist.trackID}" /></td>
                                                <td><c:out value="${icdslist.sanctionPostNameDis}" /></td>
                                                <td> <c:out value="${icdslist.sanctionOrderNo}"/></td>
                                                <td><spring:message code="${icdslist.sanctionPostOccupied}"/></td>
                                                <td><c:out value="${icdslist.staffingName}"/></td>
                                                <td><c:out value="${icdslist.joinedDate}"/></td>
                                                <td><c:out value="${icdslist.relevingDate}"/></td>
                                                <td><c:out value="${icdslist.substattivePostId}" /></td>
                                                <c:if test='${other}'>
                                                    <td><div class="pull-right btn-group btn-group-sm" style="margin-bottom: 5px">


                                                            <a href="javascript:sancEditPost()" >
                                                                <button class="btn btn-colored btn-sm btn-theme-colored" type="button">
                                                                    <spring:message code="edit"/> 
                                                                </button>
                                                            </a>


                                                        </div></td>
                                                    </c:if>
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
