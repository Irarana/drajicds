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
                    //scrollY: "350px",
                    scrollCollapse: true,
                    paging: true,
                    responsive: true,
                    select: true,
                    columnDefs: [
                        {
                            targets: [2],
                            visible: false,
                            searchable: false
                        },
                        {
                            targets: [3],
                            visible: false,
                            searchable: false
                        }
                    ]
                });

                $('#datatable tbody').on('click', 'tr', function () {
                    $("#withdrawBtn").prop("disabled", false);
                    var rowData = table.row(this).data();
                    $("#selectedNoti").val(rowData[4]);
                    $("#selectedNotiId").val(rowData[2]);
                    $("#selectedNotiDate").val(rowData[1]);
                    $("#selectedIfWithdrawn").val(rowData[3]);

                    if ($('#selectedIfWithdrawn').val() == 'Y') {
                                $("#withdrawBtn").prop("disabled", true); 
                        //alert("Already Withdrawn.");
                        //        return false;
                            }
                    //alert($("#selectedNoti").val());
                    //alert($("#selectedNotiDate").val());
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
            
            function validateMoniter() {
                notiId = $('#selectedNoti').val();
               // alert(notiId);
                //alert(projSelVal);
                $('form#outBoxForm').submit(function (e) {
                    if ($('#selectedNoti').val() != '') {
                        //    alert(action + $("#selectedNoti").val());
                        return true;
                    } else {
                        alert("Please select row.");
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
                                        <h2 class="text-white font-36"><spring:message code="lable.outbox"/></h2>
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
                        <form:form id="outBoxForm" method="POST" modelAttribute="OutBoxBean" action="outboxAction.htm" >
                            <div class="panel-group toggle">
                                <div class="panel">
                                    <div class="panel-heading md-bg-pink-A200">
                                        <div class="panel-title"><h4 class="text-white "> &nbsp;<spring:message code="lable.outbox"/></h4> </div>
                                    </div>
                                    <div class="panel-body">
                                        <div class="row">
                                            <div class="col-sm-3">
                                                <div class="md-card md-bg-pink-A200 uk-text-contrast">
                                                    <div class="md-card-content">
                                                        <div class="uk-float-right uk-margin-top uk-margin-small-right"><h2><i class="fa fa-commenting-o  md-color-white"></i></h2></div>
                                                        <h3 class="md-color-grey-900 uk-margin-remove"><spring:message code="lable.totoal.notification"/></h3>
                                                        <h2 class="uk-margin-remove"><span class="countUpMe"><c:out value="${OutBoxBean.totalNotification}"/></span></h2>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-sm-3">
                                                <div class="md-card md-bg-lime-A700 uk-text-contrast">
                                                    <div class="md-card-content">
                                                        <div class="uk-float-right uk-margin-top uk-margin-small-right"><h2><i class="fa fa-commenting-o  md-color-white"></i></h2></div>
                                                        <h3 class="md-color-grey-900 uk-margin-remove"><spring:message code="lable.acknowledged"/></h3>
                                                        <h2 class="uk-margin-remove"><span class="countUpMe"><c:out value="${OutBoxBean.acknowledged}"/>/<c:out value="${OutBoxBean.totalAcknowledged}"/></span></h2>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-sm-3">
                                                <div class="md-card md-bg-amber-700 uk-text-contrast">
                                                    <div class="md-card-content">
                                                        <div class="uk-float-right uk-margin-top uk-margin-small-right"><h2><i class="fa fa-commenting-o  md-color-white"></i></h2></div>
                                                        <h3 class="md-color-grey-900 uk-margin-remove"><spring:message code="lable.replied"/></h3>
                                                        <h2 class="uk-margin-remove"><span class="countUpMe"><c:out value="${OutBoxBean.replied}"/>/<c:out value="${OutBoxBean.totalReplied}"/></span></h2>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-sm-3">
                                                <div class="md-card md-bg-teal-A400 uk-text-contrast">
                                                    <div class="md-card-content">
                                                        <div class="uk-float-right uk-margin-top uk-margin-small-right"><h2><i class="fa fa-commenting-o  md-color-white"></i></h2></div>
                                                        <h3 class="md-color-grey-900 uk-margin-remove"><spring:message code="lable.approved"/></h3>
                                                        <h2 class="uk-margin-remove"><span class="countUpMe"><c:out value="${OutBoxBean.approved}"/>/<c:out value="${OutBoxBean.totalApproved}"/></span></h2>
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

                                <input type="submit" id="withdrawBtn" value="<spring:message code='lable.withdraw'/>" name="_withdrawNoti"    class="btn btn-default btn-flat btn-theme-colored" >
                                <input type="submit" id="monitorBtn" value="<spring:message code='monitor'/>" name="_target1"  onclick="javascript:validateMoniter()" class="btn btn-default btn-flat btn-theme-colored" >

                            </div>
                            <div class="clearfix"></div>
                            <div id="divDatatable" class="col-md-12">  
                                <table id="datatable" class="stripetable row-border order-column table-condensed" >
                                    <thead>
                                        <tr>

                                            <th><spring:message code='sr.no'/></th>
                                            <th><spring:message code='lable.date'/></th>
                                            <th>notificationId</th>
                                            <th>If Withdraw</th>
                                            <th><spring:message code='lable.noti.notification'/></th>
                                            <th><spring:message code='lable.type'/></th>
                                            <th><spring:message code='lable.noti.respondent'/></th>
                                            <th><spring:message code='acknowledged'/></th>
                                            <th><spring:message code='replied'/></th>
                                            <th><spring:message code='approved'/></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="notilist" items="${OutBoxBean.notificationList}" varStatus="varCount">
                                            <c:if test = "${(notilist.ifWithdrawn =='Y') }"> 
                                                <tr class="odd gradeX text-danger">
                                                </c:if>
                                                <c:if test = "${(notilist.ifWithdrawn ==null || notilist.ifWithdrawn=='N') }"> 
                                                <tr class="odd gradeX">
                                                </c:if>   
                                                <td><c:out value="${varCount.count}"/></td>
                                                <td><c:out value="${notilist.notificationDate}"/></td>
                                                <td> <c:out value="${notilist.notificationId}"/></td>
                                                <td> <c:out value="${notilist.ifWithdrawn}"/></td>
                                                <td> <c:out value="${notilist.notification}"/></td>
                                                <td><spring:message code='${notilist.notType}'/></td>
                                                <td><c:out value="${notilist.respondents}(${notilist.taskCount})"/></td>
                                                <td><c:out value="${notilist.acknowledgedCount}"/></td>
                                                <td><c:out value="${notilist.repliedCount}"/></td>
                                                <td><c:out value="${notilist.approvedCount}"/></td>

                                            </tr>

                                        </c:forEach>


                                    </tbody>
                                </table>
                            </div>

                            <div class="pull-right btn-group btn-group-sm" style="margin-bottom: 5px">

                                <input type="submit" value="<spring:message code='lable.back'/>" name="_back0"   class="btn btn-default btn-flat btn-theme-colored" >

                            </div>
                            <input type="hidden" value="0" name="_page"/>

                            <form:hidden path="selectedNotification" id="selectedNoti"/>
                            <form:hidden path="selectedNotificationId" id="selectedNotiId"/>
                            <form:hidden path="selectedNotiDate" id="selectedNotiDate"/>
                            <form:hidden path="selectedIfWithdrawn" id="selectedIfWithdrawn"/>


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
