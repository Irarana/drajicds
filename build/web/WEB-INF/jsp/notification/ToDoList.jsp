<!DOCTYPE html>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
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
                            targets: [1],
                            visible: false,
                            searchable: false
                        },
                        {
                            targets: [6],
                            visible: false,
                            searchable: false
                        },
                        {
                            targets: [7],
                            visible: false,
                            searchable: false
                        }
                    ]
                });

                $("#approver").val('');

                $('#datatable tbody').on('click', 'tr', function () {
                    $("#approverBtn").prop("disabled", true);
                    var rowData = table.row(this).data();
                    $("#selectedNoti").val(rowData[3]);
                    $("#selectedNotiDate").val(rowData[2]);
                    $("#selectedNotiId").val(rowData[1]);
                    $("#approver").val(rowData[6]);
                    $("#selApproveRespoId").val(rowData[7]);
                    if ($("#approver").val() == 'true') {
                        $("#approverBtn").prop("disabled", false);
                    }

                   // alert($("#approver").val());

                    //alert(rowData[7]);
                    //alert($("#selApproveRespoId").val());
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

                $('form#respondantForm').submit(function (e) {

                    if ($("#selDistHid").val() != '' || $("#selProjHid").val() != '') {
                        // alert(action + $("#projectCode").val());
                        return true;
                    } else {
                        alert("Please select respondant.");
                        return false;
                    }
                });

            }

            function approve() {
                approver = $("#approver").val();
                projSelVal = $("#selProjHid").val();
                alert(approver);
                //alert(projSelVal);

                $('form#inBoxForm').submit(function (e) {

                    if (approver != '') {
                        if (approver == 'true') {
                            // alert(action + $("#projectCode").val());
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        alert("Please select respondant.");
                        return false;
                    }
                });

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
        <div id="wrapper" class="clearfix">

            <!-- Header -->
            <jsp:include page="../header.jsp"></jsp:include>

                <!-- Start main-content -->
                <div class="main-content">
                    <!-- Section: inner-header -->
                    <section class="inner-header divider layer-overlay overlay-dark-8" data-bg-img="images/bg/bg2.jpg">
                        <div class="container pt-90 pb-40">
                            <!-- Section Content -->
                            <div class="section-content">
                                <div class="row"> 
                                    <div class="col-md-12">
                                        <h2 class="text-white font-36"><spring:message code="lable.to.do.list"/></h2>
                                    <ol class="breadcrumb text-left mt-10 white">
                                        <li><a href="index-mp-layout1.html">Home</a></li>
                                        <li><a href="#">Pages</a></li>
                                        <li class="active">Entry</li>
                                    </ol>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>

                <section>
                    <div class="container">
                        <form:form id="inBoxForm" method="POST" modelAttribute="InBoxBean" action="inboxAction.htm" >
                            <div class="panel-group toggle">
                                <div class="panel">
                                    <div class="panel-heading md-bg-pink-A200">
                                        <div class="panel-title"><h4 class="text-white "> &nbsp;<spring:message code="lable.to.do.list"/></h4> </div>
                                    </div>
                                    <div class="panel-body">
                                        <div class="row">
                                            <div class="col-sm-3">
                                                <div class="md-card md-bg-pink-A200 uk-text-contrast">
                                                    <div class="md-card-content">
                                                        <div class="uk-float-right uk-margin-top uk-margin-small-right"><h2><i class="fa fa-commenting-o  md-color-white"></i></h2></div>
                                                        <h3 class="md-color-grey-900 uk-margin-remove"><spring:message code="lable.totoal.notification"/></h3>
                                                        <h2 class="uk-margin-remove"><span class="countUpMe"><c:out value="${InBoxBean.totalNotification}"/></span></h2>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-sm-3">
                                                <div class="md-card md-bg-lime-A700 uk-text-contrast">
                                                    <div class="md-card-content">
                                                        <div class="uk-float-right uk-margin-top uk-margin-small-right"><h2><i class="fa fa-commenting-o  md-color-white"></i></h2></div>
                                                        <h3 class="md-color-grey-900 uk-margin-remove"><spring:message code="lable.acknowledged"/></h3>
                                                        <h2 class="uk-margin-remove"><span class="countUpMe"><c:out value="${InBoxBean.acknowledged}"/>/<c:out value="${InBoxBean.totalAcknowledged}"/></span></h2>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-sm-3">
                                                <div class="md-card md-bg-amber-700 uk-text-contrast">
                                                    <div class="md-card-content">
                                                        <div class="uk-float-right uk-margin-top uk-margin-small-right"><h2><i class="fa fa-commenting-o  md-color-white"></i></h2></div>
                                                        <h3 class="md-color-grey-900 uk-margin-remove"><spring:message code="lable.replied"/></h3>
                                                        <h2 class="uk-margin-remove"><span class="countUpMe"><c:out value="${InBoxBean.replied}"/>/<c:out value="${InBoxBean.totalReplied}"/></span></h2>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-sm-3">
                                                <div class="md-card md-bg-teal-A400 uk-text-contrast">
                                                    <div class="md-card-content">
                                                        <div class="uk-float-right uk-margin-top uk-margin-small-right"><h2><i class="fa fa-commenting-o  md-color-white"></i></h2></div>
                                                        <h3 class="md-color-grey-900 uk-margin-remove"><spring:message code="lable.approved"/></h3>
                                                        <h2 class="uk-margin-remove"><span class="countUpMe"><c:out value="${InBoxBean.approved}"/>/<c:out value="${InBoxBean.totalApproved}"/></span></h2>
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

                                <input type="submit" id="approverBtn" value="<spring:message code='lable.approved'/>" name="_approve" onclick="javascript:approve();" class="btn btn-default btn-flat btn-theme-colored" >

                            </div>
                            <div class="clearfix"></div>
                            <div id="divDatatable" class="col-md-12">  
                                <table id="datatable" class="stripetable row-border order-column table-condensed" >
                                    <thead>
                                        <tr>
                                            <th><spring:message code='sr.no'/></th>
                                            <th><spring:message code='lable.notification.id'/></th>
                                            <th><spring:message code='lable.date'/></th>                                         
                                            <th><spring:message code='lable.noti.notification'/></th>
                                            <th><spring:message code='lable.submitted.by'/></th>
                                            <th><spring:message code='lable.status'/></th>
                                            <th>Is Approver</th>
                                            <th>Respondent Id</th>
                                            <th><spring:message code='lable.action'/></th>

                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="notilist" items="${InBoxBean.notificationList}" varStatus="varCount">
                                            <tr class="odd gradeX">
                                                <td><c:out value="${varCount.count}"/></td>
                                                <td><c:out value="${notilist.notificationId}"/></td>
                                                <td><c:out value="${notilist.notificationDate}"/></td>
                                                <td> <c:out value="${notilist.notification}"/></td>
                                                <td><c:out value="${notilist.submittedBy}"/> (<spring:message code='${notilist.notType}'/>)</td>
                                                <td><spring:message code='${notilist.status}'/></td>
                                                <td><c:out value='${notilist.isApprover}'/></td>
                                                <td><c:out value='${notilist.respondentId}'/></td>
                                                <td><input type="submit" value="<spring:message code='lable.tasklist'/>" name="_target1"   class="btn btn-default btn-flat btn-theme-colored" ></td>

                                            </tr>

                                        </c:forEach>


                                    </tbody>
                                </table>
                            </div>
                            <input type="hidden" value="0" name="_page"/>
                            <form:hidden path="selectedNotificationId" id="selectedNotiId"/>
                            <form:hidden path="selectedNotification" id="selectedNoti"/>
                            <form:hidden path="selectedNotiDate" id="selectedNotiDate"/>
                            <form:hidden path="approver" id="approver"/>
                            <form:hidden path="selApproveRespoId" id="selApproveRespoId"/>

                        </div> 
                    </form:form>
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
