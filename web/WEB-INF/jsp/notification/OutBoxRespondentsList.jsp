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
                   // scrollY: "300px",
                    scrollCollapse: true,
                    paging: true,
                    responsive: true,
                    select: true,
                    destroy: true,
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
                        }
                    ]
                });

                $('#datatable tbody').on('click', 'tr', function () {
                    var rowData = table.row(this).data();
                    $("#selectedRespondentId").val(rowData[0]);
                    var allTaskCompleted = rowData[1];
                    // alert($("#selectedRespondentId").val());
                    if ($("#selectedRespondentId").val() > 0) {
                        //  alert("allTaskCompleted >> "+allTaskCompleted);
                        if (allTaskCompleted == 'false') {
                            $("#withdrawResp").prop("disabled", false);
                        } else {
                            //alert("This Request already in process so couldn't withdraw now.");
                            $("#withdrawResp").prop("disabled", true);
                        }
                    } else {
                        $("#withdrawResp").prop("disabled", true);
                    }

                    //alert($("#selectedNoti").val());
                    //alert($("#selectedNotiDate").val());
                });

                $('#selDistOption').change(function () {
                    if ($('#selDistOption').val() != '' && $('#aboutLevel').val() != 'PROJECT') {
                        $.ajax({
                            type: "GET",
                            url: "getOption.htm",
                            data: {code: $('#selDistOption').val()},
                            success: function (data) {

//                                if ($.fn.DataTable.isDataTable("#datatable")) {
//                                    $('#datatable').DataTable().clear().destroy();
//                                    $('#datatable').DataTable().api().ajax.reload();
//                                }
                                var keys = Object.keys(data);
                                $('#selProjOption').empty();
                                $('#selSactOption').empty();
                                // if ($('#userlevel').val() == 'STATE') { //
                                newOption = '<option value="">ALL</option>';
                                $('#selProjOption').append(newOption);
                                $('#selSactOption').append(newOption);

                                //  }
//                                 var newOption1 = '<option value="">ALL</option>';
//                                    $('#selProjOption').append(newOption1);
                                for (var i = 0; i < keys.length; i++) {
                                    var key = keys[i];
                                    console.log(key, data[key]);

                                    var newOption = $('<option value="' + key + '">' + data[key] + '</option>');
                                    $('#selProjOption').append(newOption);
                                }

                            },
                            error: function () {
                                alert('Error occured');
                            }
                        });
                    }
                });

                $('#selProjOption').change(function () {
                    if ($('#selProjOption').val() != '' && $('#aboutLevel').val() != 'SECTOR') {
                        $.ajax({
                            type: "GET",
                            url: "getSectorForProject.htm",
                            data: {projectCode: $('#projectCode').val()},
                            success: function (data) {
                                var keys = Object.keys(data);
                                $('#selSactOption').empty();
                                // if ($('#userlevel').val() == 'STATE' || $('#userlevel').val() == 'DIST') {
                                newOption = '<option value="">ALL</option>';
                                $('#selSactOption').append(newOption);
                                //}
                                var newOption1 = '<option value="">ALL</option>';
                                $('#selSactOption').append(newOption1);
                                for (var i = 0; i < keys.length; i++) {
                                    var key = keys[i];
                                    console.log(key, data[key]);
                                    var newOption = $('<option value="' + key + '">' + data[key] + '</option>');
                                    $('#selSactOption').append(newOption);
                                }

                            },
                            error: function () {
                                alert('Error occured');
                            }
                        });
                    }

                });

                $('#selSactOption').change(function () {
                    if ($('#selSactOption').val() != '') {
                        $.ajax({
                            type: "GET",
                            url: "getCenterForSector.htm",
                            data: {sectorCode: $('#sectorCode').val()},
                            success: function (data) {
                                var keys = Object.keys(data);

                                for (var i = 0; i < keys.length; i++) {
                                    var key = keys[i];
                                    console.log(key, data[key]);
                                    var newOption = $('<option value="' + key + '">' + data[key] + '</option>');
                                    $('#selSactOption').append(newOption);
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
            function validate() {
                distSelVal = $("#selDistHid").val();
                projSelVal = $("#selProjHid").val();
                //alert(distSelVal);
                //alert(projSelVal);

                $('form#outBoxRespondentsForm').submit(function (e) {

                    if ($("#selectedRespondentId").val() != '') {
                        // alert(action + $("#projectCode").val());
                        return true;
                    } else {
                        alert("Please select respondant.");
                        // e.preventDefault();
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
                                        <h2 class="text-white font-36"><spring:message code="lable.notification.details"/></h2>
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
                    <form:form id="outBoxRespondentsForm"  method="POST" modelAttribute="OutBoxBean" action="outboxAction.htm"  >
                        <form:hidden path="aboutLevel" id="aboutLevel" value="${OutBoxBean.aboutLevel}"/>
                        <div class="container">
                            <div class="row">
                                <div class="clearfix"></div>
                                <div class="col-md-12 " style="margin-top: 5px;margin-bottom: 5px">

                                    <div class="panel-body">
                                        <div class="row">

                                            <div class="col-md-6">
                                                <div class="form-group">

                                                    <li><B><spring:message code="lable.noti.notification"/>  :</B> <c:out value="${OutBoxBean.selectedNotification}"/></li>
                                                    <li><B><spring:message code="lable.date"/>:</B> <c:out value="${OutBoxBean.selectedNotiDate}"/></li>


                                                </div>
                                            </div> 
                                            <div class="col-md-6">
                                                <div class="form-group">


                                                    <li><B><spring:message code="lable.response.time"/>:</B> <c:out value="${OutBoxBean.selNotiResponseTime}"/></li>
                                                    <li><B><spring:message code="lable.approve.time"/>:</B> <c:out value="${OutBoxBean.selNotiApproveTime}"/></li>

                                                </div>
                                            </div> 



                                        </div>

                                    </div>
                                </div>
                                <div class="clearfix"></div>
                                <c:if test = "${(OutBoxBean.aboutLevel == 'SECTOR') || (OutBoxBean.aboutLevel == 'PROJECT') || (OutBoxBean.aboutLevel == 'AWC')}"> 

                                    <div class="icon-box left media bg-silver-light border-1px p-10 mb-5">
                                        <form>
                                            <div class="row">
                                                <c:if test = "${(OutBoxBean.aboutLevel == 'SECTOR') || (OutBoxBean.aboutLevel == 'PROJECT') || (OutBoxBean.aboutLevel == 'AWC')}"> 
                                                    <div class="col-md-3">
                                                        <div class="form-group">
                                                            <label class="col-md-4">District</label>
                                                            <div class="col-md-8">
                                                                <form:select id="selDistOption" path="selDistOption" class="form-control">
                                                                    <option value=""> ALL </option>
                                                                    <form:options items="${OutBoxBean.selectedDist}"/>
                                                                </form:select>
                                                            </div>
                                                            <div class="clearfix"></div>
                                                        </div>
                                                    </div> 
                                                </c:if>
                                                <c:if test = "${(OutBoxBean.aboutLevel == 'SECTOR') || (OutBoxBean.aboutLevel == 'AWC')}"> 
                                                    <div class="col-md-3">
                                                        <div class="form-group">
                                                            <label class="col-md-4">Project</label>
                                                            <div class="col-md-8">
                                                                <form:select id="selProjOption" path="selProjOption" class="form-control">
                                                                    <option value=""> ALL </option>
                                                                    <form:options items="${OutBoxBean.selectedProject}"/>
                                                                </form:select>
                                                            </div>
                                                            <div class="clearfix"></div>
                                                        </div>
                                                    </div>
                                                </c:if>
                                                <c:if test = "${(OutBoxBean.aboutLevel== 'AWC') }"> 
                                                    <div class="col-md-3">
                                                        <div class="form-group">
                                                            <label class="col-md-4">Sector</label>
                                                            <div class="col-md-8">
                                                                <form:select id="selSactOption" path="selSactOption" class="form-control">
                                                                    <option value=""> ALL </option>
                                                                    <form:options items="${OutBoxBean.selectedSector}"/>
                                                                </form:select>
                                                            </div>
                                                            <div class="clearfix"></div>
                                                        </div>
                                                    </div>
                                                </c:if>



                                                <div class="col-md-3 pull-right">
                                                    <input type="submit" value="<spring:message code='lable.filter'/>" name="_filter" class="btn btn-colored btn-sm btn-theme-colored pull-right" >

                                                </div>



                                            </div>

                                        </form>
                                    </div>
                                </c:if>  
                                <div class="clearfix"></div>
                                <div class="col-md-12 " style="margin-top: 15px">
                                    <b><spring:message code="lable.respondant.wise.status"/></b>
                                </div>
                                <div class="clearfix"></div>
                                <div id="divDatatable" class="col-md-12 " style="margin-top: 10px">  
                                    <table id="datatable" class="stripetable row-border order-column table-condensed" >
                                        <thead>
                                            <tr>
                                                <th>Respondent ID</th>
                                                <th>All Task Completed</th>
                                                <th><spring:message code="sr.no"/></th>
                                                <th><spring:message code="lable.recipient"/></th>
                                                <th><spring:message code="lable.acknowledged.on"/></th>
                                                <th><spring:message code="lable.replied.on"/></th>
                                                <th><spring:message code="lable.approve.on"/></th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="respondentlst" items="${OutBoxBean.selectedRespondentsList}" varStatus="status">

                                                <c:if test = "${(respondentlst.allTaskCompleted ==true) }"> 
                                                    <tr class="odd gradeX text-danger">
                                                    </c:if>
                                                    <c:if test = "${(respondentlst.allTaskCompleted ==null || respondentlst.allTaskCompleted==false) }"> 
                                                    <tr class="odd gradeX">
                                                    </c:if>  
                                                    <td><c:out value="${respondentlst.respondentId}" /></td>
                                                    <td><c:out value="${respondentlst.allTaskCompleted}" /></td>
                                                    <td><c:out value="${status.count}" /></td>
                                                    <td><c:out value="${respondentlst.respReportingDistName}" /></td>
                                                    <c:if test = "${(respondentlst.totalAcknowledged >0) }"> 
                                                        <td><c:out value="${respondentlst.acknowledged}" />/<c:out value="${respondentlst.totalAcknowledged}" /></td>
                                                        <td><c:out value="${respondentlst.replied}" />/<c:out value="${respondentlst.totalReplied}" /></td>
                                                        <td><c:out value="${respondentlst.approved}" />/<c:out value="${respondentlst.totalApproved}" /></td>
                                                    </c:if>
                                                    <c:if test = "${(respondentlst.totalAcknowledged ==0) }"> 
                                                        <td><c:out value="${respondentlst.acknowledgedOn}" /></td>
                                                        <td><c:out value="${respondentlst.repliedOn}" /></td>
                                                        <td><c:out value="${respondentlst.approvedOn}" /></td>
                                                    </c:if>
                                                </tr>

                                            </c:forEach>


                                        </tbody>
                                    </table>
                                </div>



                            </div>


                            <div class="clearfix"></div>                    
                            <div class="pull-right btn-group btn-group-sm" style="margin-bottom: 5px">
                                    <input type="submit" id="withdrawResp" value="<spring:message code='lable.withdraw'/>" name="_withdrawResp"  onclick="javascript:validate()"   class="btn btn-default btn-flat btn-theme-colored" >


                                <input type="submit" value="<spring:message code='lable.back'/>" name="_target0" class="btn btn-dark btn-theme-colored btn-flat mr-5" >

                                <input type="hidden" value="1" name="_page">
                                <form:hidden path="selectedRespondentId" id="selectedRespondentId"/>

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
