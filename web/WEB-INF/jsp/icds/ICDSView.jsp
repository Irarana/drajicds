<!DOCTYPE html>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
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
        <link href="css/animate.css" rel="stylesheet" type="text/css">
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
        <script src="js/custom.js"></script>

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
                                        <h2 class="text-white font-36"><spring:message code="${MenuLevel}"/>&nbsp;<spring:message code="label.icds.details"/></h2>
                                    <ol class="breadcrumb text-left mt-10 white">
                                        <li><a href="homepage.htm"><spring:message code="home"/></a></li>
                                        <li><a href="#"><spring:message code="lable.icds.units"/></a></li>
                                        <li><a href="icdsProjectList.htm"><spring:message code="${MenuLevel}"/></a></li>
                                        <li class="active"><spring:message code="view"/></li>
                                    </ol>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>



                <section>
                    <div class="container">

                        <form:form id="training_center" method="Get" action="icdsProjectList.htm" commandName="command" > 
                            <form:hidden path="level" value="${MenuLevel}"/>
                            <div class="separator mt-0 mb-0"></div>
                            <!-- Contact Form -->

                            <div class="clearfix"></div>    

                            <div class="row">
                                <div class="col-sm-7">
                                    <div class="icon-box left media  bg-deep bg-hover-theme-colored border-1px p-15 mb-20"> 
                                        <a class="media-left pull-left flip" href="#"><i class="pe-7s-id text-theme-colored mt-10"></i></a>
                                        <div class="media-body">
                                            <h4 class="font-15 text-uppercase"><spring:message code="label.icds.office"/>&nbsp;<spring:message code="label.icds.details"/></h4>
                                            <ul class="list check">
                                                <c:if test="${MenuLevel eq 'PROJECT'}">
                                                    <li><spring:message code="DIST"/> : ${command.districtName}</li>
                                                    </c:if>
                                                    <c:if test="${MenuLevel eq 'SECTOR'}">
                                                    <li><spring:message code="DIST"/> : ${command.districtName}</li>
                                                    <li><spring:message code="PROJECT"/> : ${command.selectedProjectName}</li>
                                                    </c:if>

                                                <li><spring:message code="label.icds.district.office.name"/> : ${command.officeName} 
                                                </li>
                                                <li><spring:message code="label.icds.district.office.address"/> : ${command.officeAddress} 
                                                </li>
                                                <li><spring:message code="label.icds.district.office.longitude"/> : ${command.longitude} 
                                                </li>
                                                <li><spring:message code="label.icds.district.office.latitude"/> : ${command.latitude} 
                                                </li>
                                                <li><spring:message code="label.icds.district.office.building.owner"/> : ${command.buildingOwnerName} 
                                                </li>
                                                <br/>
                                            </ul>
                                        </div>
                                    </div>

                                </div>
                                <div class="col-sm-5">
                                    <div class="icon-box left media bg-silver-light bg-hover-theme-colored border-1px p-15 mb-20"> 
                                        <a class="media-left pull-left flip" href="#"><i class="pe-7s-study text-theme-colored mt-10"></i></a>
                                        <div class="media-body">
                                            <h4 class="font-15 text-uppercase"><spring:message code="label.icds.head.of.office.details"/></h4>
                                            <ul class="list check">
                                                <li>
                                                    <spring:message code="label.icds.head.of.office"/> 
                                                    <c:if test="${command.inPosition=='true'}" >
                                                        is <spring:message code="label.icds.hrms.head.of.office.inposition"/>
                                                    </c:if>
                                                    <c:if test="${command.additionalChargers=='true'}" >
                                                        with <spring:message code="label.icds.hrms.head.of.office.additionalcharges"/>. </li>
                                                    </c:if>
                                                </li>


                                                <li><spring:message code="label.icds.hrms.head.of.office"/> : ${command.hrmsIDHOD} 
                                                </li>
                                                <li><spring:message code="label.icds.head.of.office.name"/> : ${command.hodName} 
                                                </li>
                                                <li><spring:message code="label.icds.head.of.office.phone.no"/> : ${command.phoneNo} 
                                                </li>
                                                <li><spring:message code="label.icds.head.of.office.mobile.no"/> : ${command.mobileNo} 
                                                </li>
                                                <li><spring:message code="label.icds.head.of.office.email"/> : ${command.email} 
                                                </li> </ul>
                                        </div>
                                    </div>

                                </div>
                            </div>


                            <div class="separator mt-0 mb-0"></div>                 
                            <div class="text-center m-10">
                                <a href="icdsProjectList.htm?menuLevel=${MenuLevel}" >
                                    <button class="btn btn-dark btn-theme-colored btn-flat mr-5" type="button">
                                     <spring:message code="back"/>
                                    </button>
                                </a>
                                <!--                                <input type="submit" value="Back" name = "icdsFormAction" class="btn btn-default btn-flat btn-theme-colored"/>-->
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
