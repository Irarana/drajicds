<!DOCTYPE html>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
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
            <jsp:include page="header.jsp"></jsp:include>

                <!-- Start main-content -->
                <div class="main-content">
                    <!-- Section: inner-header -->
                    <section class="inner-header divider layer-overlay overlay-dark-8" data-bg-img="images/bg/bg2.jpg">
                        <div class="container pt-90 pb-40">
                            <!-- Section Content -->
                            <div class="section-content">
                                <div class="row"> 
                                    <div class="col-md-12">
                                        <h2 class="text-white font-36">Frontline Worker Profile </h2>
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
                <c:if test="${pageContext.response.locale!='hindi'}" >

                    <section>
                        <div class="container">

                            <div class="clearfix"></div>
                            <h3 class="line-bottom border-bottom mt-0 mt-sm-20"><spring:message code="label.fw.view.profilelbl"/></h3>
                            <div class="col-sm-10 p-0">
                                <div class="col-sm-2 pl-0">
                                    <a href="#" class="thumbnail mb-0"> <img class="img-fullwidth" src="https://placehold.it/200x200" alt="..."> </a>
                                </div>
                                <div class="col-sm-10 p-0">
                                    <h4 class="mb-5 pb-5">
                                        <c:out value="${command.workerName}"/> 
                                    </h4>
                                    <p class="mb-0">
                                        <strong>
                                            <spring:message code="label.fw.view.workerid"/>: <c:out value="${command.workerId}"/>
                                        </strong>, Since <c:out value="${command.doj}"/>
                                    </p>
                                    
                                    <h5 class="mb-0"><c:out value="${command.awwAddress}"/>,</h5>
                                    <h5 class="mb-0"><c:out value="${command.sectorName}"/> 
                                        under <c:out value="${command.projectName}"/> <spring:message code="label.fw.view.projecct"/> , <c:out value="${command.districtNameHindi}"/>
                                    </h5>



                                </div>
                            </div>    
                            <div class="separator mt-0 mb-0"></div>
                            <!-- Contact Form -->

                            <div class="clearfix"></div>    

                            <div class="row">
                                <div class="col-sm-7">
                                    <div class="icon-box left media  bg-deep bg-hover-theme-colored border-1px p-15 mb-20"> 
                                        <a class="media-left pull-left flip" href="#"><i class="pe-7s-id text-theme-colored mt-10"></i></a>
                                        <div class="media-body">
                                            <h4 class="font-15 text-uppercase"><spring:message code="label.fw.view.PersonalDetail"/></h4>
                                            <ul class="list check">
                                                <li><c:out value="${command.taggedRelation}"/> of <c:out value="${command.relationName}"/></li>
                                                <li>Born On ${command.dob}, ${command.category} <spring:message code="label.fw.view.category"/></li>
                                                <li><spring:message code="label.fw.view.aadhaar"/> : ${command.aadhaarNo} | <spring:message code="label.fw.view.bhamshah"/>: ${command.bhamshahNo}</li>
                                            </ul>
                                        </div>
                                    </div>
                                    <div class="icon-box left media bg-silver-light bg-hover-theme-colored border-1px p-15 mb-20"> 
                                        <a class="media-left pull-left flip" href="#"><i class="pe-7s-mail text-theme-colored mt-5"></i></a>
                                        <div class="media-body">
                                            <h4 class="font-15 text-uppercase"><spring:message code="label.fw.view.contact"/></h4>
                                            <ul class="list check">
                                                <li><c:out value="${command.address}"/></li>
                                                <li>${command.mobile}</li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-5">
                                    <div class="icon-box left media bg-silver-light bg-hover-theme-colored border-1px p-15 mb-20"> 
                                        <a class="media-left pull-left flip" href="#"><i class="pe-7s-study text-theme-colored mt-10"></i></a>
                                        <div class="media-body">
                                            <h4 class="font-15 text-uppercase"><spring:message code="label.fw.view.qual"/></h4>
                                            <ul class="list check">
                                                <li>${command.education}</li>
                                                <li>Undergone Job Training on ${command.jobTrainYear}</li>
                                                <li>Last Refresher Training on ${command.refreshTraining}</li>
                                            </ul>
                                        </div>
                                    </div>
                                    <div class="icon-box left media bg-deep bg-hover-theme-colored  border-1px p-15 mb-20"> 
                                        <a class="media-left pull-left flip" href="#"><i class="pe-7s-culture text-theme-colored mt-10"></i></a>
                                        <div class="media-body">
                                            <h4 class="font-15 text-uppercase"><spring:message code="label.fw.view.fi"/></h4>
                                            <ul class="list check">
                                                <li>A/c No.- ${command.bankAcct}</li>
                                                <li>IFSC Code - ${command.ifcsCode}</li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="clearfix"></div>  
                        </div> 
                    </section>  
                </c:if>
                <c:if test="${pageContext.response.locale=='hindi'}" >

                    <section>
                        <div class="container">

                            <div class="clearfix"></div>
                            <h3 class="line-bottom border-bottom mt-0 mt-sm-20"><spring:message code="label.fw.view.profilelbl"/></h3>
                            <div class="col-sm-10 p-0">
                                <div class="col-sm-2 pl-0">
                                    <a href="#" class="thumbnail mb-0"> <img class="img-fullwidth" src="https://placehold.it/200x200" alt="..."> </a>
                                </div>
                                <div class="col-sm-10 p-0">
                                    <h4 class="mb-5 pb-5">
                                        <c:out value="${command.workerNameHindi}"/>
                                    </h4>
                                    <p class="mb-0">
                                        <strong>
                                            <spring:message code="label.fw.view.workerid"/>: <c:out value="${command.workerId}"/>
                                        </strong>, Since <c:out value="${command.doj}"/>
                                    </p>
                                    <h5 class="mb-0"><c:out value="${command.awwAddress}"/>,</h5>
                                    <h5 class="mb-0"><c:out value="${command.sectorName}"/> 
                                        <c:out value="${command.projectName}"/> <spring:message code="label.fw.view.projecct"/> के अंतर्गत, <c:out value="${command.districtNameHindi}"/>
                                    </h5>
                                    

                                </div>
                            </div>    
                            <div class="separator mt-0 mb-0"></div>
                            <!-- Contact Form -->

                            <div class="clearfix"></div>    

                            <div class="row">
                                <div class="col-sm-7">
                                    <div class="icon-box left media  bg-deep bg-hover-theme-colored border-1px p-15 mb-20"> 
                                        <a class="media-left pull-left flip" href="#"><i class="pe-7s-id text-theme-colored mt-10"></i></a>
                                        <div class="media-body">
                                            <h4 class="font-15 text-uppercase"><spring:message code="label.fw.view.PersonalDetail"/></h4>
                                            <ul class="list check">
                                                <li>
                                                    <c:if test="${command.taggedRelation=='Husband'}" >
                                                        पति <c:out value="${command.relationName}"/></li>
                                                    </c:if>
                                                    <c:if test="${command.taggedRelation=='Father'}" >
                                                    पिता <c:out value="${command.relationName}"/></li>
                                                </c:if>

                                                <li>जन्म तिथि ${command.dob}, ${command.categoryHindi}   <spring:message code="label.fw.view.category"/></li>
                                                <li><spring:message code="label.fw.view.aadhaar"/> : ${command.aadhaarNo} | <spring:message code="label.fw.view.bhamshah"/>: ${command.bhamshahNo}</li>
                                            </ul>
                                        </div>
                                    </div>
                                    <div class="icon-box left media bg-silver-light bg-hover-theme-colored border-1px p-15 mb-20"> 
                                        <a class="media-left pull-left flip" href="#"><i class="pe-7s-mail text-theme-colored mt-5"></i></a>
                                        <div class="media-body">
                                            <h4 class="font-15 text-uppercase"><spring:message code="label.fw.view.contact"/></h4>
                                            <ul class="list check">
                                                <li><c:out value="${command.address}"/></li>
                                                <li>${command.mobile}</li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-5">
                                    <div class="icon-box left media bg-silver-light bg-hover-theme-colored border-1px p-15 mb-20"> 
                                        <a class="media-left pull-left flip" href="#"><i class="pe-7s-study text-theme-colored mt-10"></i></a>
                                        <div class="media-body">
                                            <h4 class="font-15 text-uppercase"><spring:message code="label.fw.view.qual"/></h4>
                                            <ul class="list check">
                                                <li>${command.educationHindi}</li>
                                                <li><spring:message code="label.fw.view.undergoneTraining"/> ${command.jobTrainYear}</li>
                                                <li><spring:message code="label.fw.view.lastTraining"/> ${command.refreshTraining}</li>
                                            </ul>
                                        </div>
                                    </div>
                                    <div class="icon-box left media bg-deep bg-hover-theme-colored  border-1px p-15 mb-20"> 
                                        <a class="media-left pull-left flip" href="#"><i class="pe-7s-culture text-theme-colored mt-10"></i></a>
                                        <div class="media-body">
                                            <h4 class="font-15 text-uppercase"><spring:message code="label.fw.view.fi"/></h4>
                                            <ul class="list check">
                                                <li><spring:message code="label.fw.view.account"/>: - ${command.bankAcct}</li>
                                                <li><spring:message code="label.fw.view.ifsc"/> - ${command.ifcsCode}</li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="clearfix"></div>  
                        </div> 
                    </section>  
                </c:if>
            </div>
            <!-- end main-content -->

            <!-- Footer -->
            <jsp:include page="footer.jsp"></jsp:include>
            <a class="scrollToTop" href="#"><i class="fa fa-angle-up"></i></a>
        </div>
        <!-- end wrapper -->

    </body>
</html>
