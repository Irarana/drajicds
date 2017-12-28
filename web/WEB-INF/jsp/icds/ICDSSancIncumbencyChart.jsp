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
                            targets: [1],
                            visible: false,
                            searchable: false
                        },]
                });
                $('#datatable tbody').on('click', 'tr', function () {
                     $("#joinIncumbency").prop("disabled", false);
                     $("#relieveIncumbency").prop("disabled", false);
                    var rowData = table.row(this).data();
                    $("#incumbencyId").val(rowData[1]);
                    $("#fromDate").val(rowData[3]);
                    $("#toDate").val(rowData[4]);
                    //alert("Hi" + rowData[1]);
                    if ($("#fromDate").val() != '') {
                        $("#joinIncumbency").prop("disabled", true);
                        $("#relieveIncumbency").prop("disabled", false);
                    } 
                    
                    if ($("#toDate").val() != '' ) {
                        $("#relieveIncumbency").prop("disabled", true);
                    }
                });




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
                //control.makeTransliteratable(['workerNameHindi']);
                //control.makeTransliteratable(['address']);

            }
            google.setOnLoadCallback(onLoad);


            function sancJoinIncumbency() {
                var post = $("#postName").val();
                var incId = $("#incumbencyId").val();
                var postCode = $("#postCode").val();
                var projName = $("#projectName").val();
                var projCode = $("#projectCode").val();
                var fromDate = $("#fromDate").val();
                //alert(fromDate);
                if (fromDate == '') {
                    if (post != '') {
                        location.href = "icdsJoinIncumbency.htm?incId=" + incId + "&postName=" + post + "&postCode=" + postCode + "&projectCode=" + projCode + "&projectName=" + projName;
                    } else {
                        alert('Please select a row to View details.');
                    }
                }
                else {
                    alert('Incumbent has joining date.');
                }
            }
            function sancRelieveIncumbency() {
                var post = $("#postName").val();
                var incId = $("#incumbencyId").val();
                var postCode = $("#postCode").val();
                var projName = $("#projectName").val();
                var projCode = $("#projectCode").val();
                var toDate = $("#toDate").val();
                //alert(toDate);

                //alert($("#projectCode").val());
                //var incumbencyId = $("#projectName").val();
                if (toDate == '') {
                    if (post != '') {

                        if (incId != null || incId != '') {
                            location.href = "icdsRelieveIncumbency.htm?incId=" + incId + "&postName=" + post + "&postCode=" + postCode + "&projectCode=" + projCode + "&projectName=" + projName;
                        } else {
                            alert('Please Join first.');
                        }
                    } else {
                        alert('Please select a row to View details.');
                    }
                } else {
                    alert('Incumbent has relieve date.');
                }
            }

            function SanPostList() {
                $('form#training_center').attr('action', 'icdsSanctionPostList.htm');
                $('form#training_center').submit();
            }
            //icdsSanctionPostList.htm?projectName=" + icdsm.getProjectName() + "&projectCode=" + icdsm.getProjectCode()
            function cancel() {
                $('form#training_center').attr('action', 'icdsSanctionPostList.htm');

                $('form#training_center').submit();
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
                                        <h2 class="text-white font-36"><spring:message code="label.icds.project.sanction.incumbency.chart"/></h2>
                                    <ol class="breadcrumb text-left mt-10 white">
                                        <li><a href="homepage.htm"><spring:message code="home"/></a></li>
                                        <li><a href="homepage.htm"><spring:message code="lable.icds.units"/></a></li>
                                        <li><a href="icdsProjectList.htm"><spring:message code="${MenuLevel}"/></a></li>
                                        <li><a href="javascript:cancel()"><spring:message code="label.icds.project.sanction.post"/></a></li>
                                        <li class="active"><spring:message code="list"/></li>
                                    </ol>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>

                <section>

                    <div class="container">
                        <form:form id="training_center" method="POST" commandName="command" >

                            <form:hidden path="userlevel" id="userlevel"/>
                            <form:hidden path="projectName" id="projectName" value="${projectName}"/>
                            <form:hidden path="projectCode" id="projectCode" value="${projectCode}"/>
                            <input type="hidden" id="postName" name="postName" value="${postName}"/>
                            <input type="hidden" id="incumbencyId" name="incumbencyId"/>
                            <input type="hidden" id="postCode" name="postCode" value="${postCode}"/>
                            <input type="hidden" id="fromDate" name="fromDate" />
                            <input type="hidden" id="toDate" name="toDate" />
                            <h4 class="mt-0 mb-30 line-bottom">
                                <spring:message code="label.icds.project.sanction.join.incumbency.post"/> :${postName} ,${projectName}( <spring:message code="${MenuLevel}"/>)</h4>

                            <div id="divDatatable" class="col-md-12 " style="margin-top: 10px">  
                                <table id="datatable" class="stripetable row-border order-column table-condensed" >
                                    <thead>
                                        <tr>
                                            <th><spring:message code="sr.no"/></th>
                                            <th><spring:message code="incumbency.id"/></th>
                                            <th><spring:message code="name"/></th>
                                            <th><spring:message code="from"/></th>
                                            <th><spring:message code="till"/></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="icdslist" items="${IncumbencyChartList}" >
                                            <tr class="odd gradeX">
                                                <td><c:out value="${icdslist.trackID}" /></td>
                                                <td><c:out value="${icdslist.incumbencyId}" /></td>
                                                <td><c:out value="${icdslist.staffingName}" /></td>
                                                <td> <c:out value="${icdslist.joinedDate}"/></td>
                                                <td><c:out value="${icdslist.relevingDate}"/></td>

                                            </tr>

                                        </c:forEach>


                                    </tbody>
                                </table>
                            </div>


                            <div class="clearfix"></div>                    
                            <div class="text-center m-10">
                                <a href="javascript:cancel()" >
                                    <button class="btn btn-dark btn-theme-colored btn-flat mr-5" type="button">
                                        <spring:message code="cancel"/>
                                    </button>
                                </a>
                                <a href="javascript:sancJoinIncumbency()" >
                                    <button class="btn btn-dark btn-theme-colored btn-flat mr-5" id="joinIncumbency" type="button">
                                        <spring:message code="join"/>
                                    </button>
                                </a>
                                <a href="javascript:sancRelieveIncumbency()" >
                                    <button class="btn btn-dark btn-theme-colored btn-flat mr-5" id="relieveIncumbency" type="button">
                                        <spring:message code="relieving"/>
                                    </button>
                                </a>
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
