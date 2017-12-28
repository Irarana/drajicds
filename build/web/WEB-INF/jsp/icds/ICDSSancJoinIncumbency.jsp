<%-- 
    Document   : ICDSProjectMaster
    Created on : Nov 10, 2017, 11:06:51 AM
    Author     : ekank
--%>

<%@ page contentType="text/html; charset=utf-8" language="java" %>
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
        <script type="text/javascript" src="https://www.google.com/jsapi"></script> 
        <!--<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>-->
        <script src="js/custom.js"></script>
        <script type="text/javascript">

            var lastDate = '';
            $(document).ready(function () {
                $('#joinedDate').datepicker({
                });
                lastDate = $("#lastIncumJoinDt").val();

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
                control.makeTransliteratable(['staffingNameHindi']);

            }
            google.setOnLoadCallback(onLoad);

            function getHRMSIDDetails() {
                hrmsID = $("#hrmsIDHOD").val();
                if (hrmsIDHOD == '') {
                    alert('Please Enter HRMS ID');
                } else {


                    $.ajax({
                        'url': 'getHRMSIDController.htm?hrmsID=' + hrmsID,
                        dataType: 'json',
                        contentType: 'application/json',
                        'type': 'GET',
                        beforeSend: function () {
                            //alert(1);
                        },
                        error: function () {
                            alert('Error');
                        },
                        'success': function (data) {
                            //alert(data);
                            if (data.hrmsIdName == '') {
                                alert('HRMDID not Found.');
                                $("#staffingName").val('');
                                $("#staffingNameHindi").val('');
                            } else {
                                // alert(data.hrmsIdName)
                                str = data.hrmsIdName.split("@");
                                // alert(str);
                                $("#staffingName").val(str[0]);
                                // alert(str[1]);
                                $("#staffingNameHindi").val(str[1]);
                            }
                        }
                    });
                }
            }

            function submitForm() {
                $.validator.addMethod("validateTime", function (value, element) {
                    return this.optional(element) || value != 'default';
                }, " Select Join Time");

                $.validator.addMethod("validateDate", function (value) {
                    var flag = true;
                    lastDate = $("#lastIncumJoinDt").val();
                    var currentJoinDate = new Date($('#joinedDate').datepicker('getDate')).getTime();
                    if (lastDate != '') {
                        var lastJoinDate = new Date(lastDate).getTime();
                        //  alert(lastJoinDate + " joining date  " + currentJoinDate);
                        if (lastJoinDate > currentJoinDate) {
                            flag = false;
                        }
                    }
                    //alert(flag);
                    //alert(lastDate);
                    return (value != '' && flag);
                }, " Select Join Date After " + lastDate);

                var validator = $("#training_center").validate({
                    rules: {
                        joiningTime: {
                            validateTime: true
                        },
                        joinedDate: {
                            validateDate: true
                        },
                        hrmsIDHOD: "required",
                        staffingName: "required",
                        staffingNameHindi: "required",
                        //joinedDate: "required",
                    },
                    errorElement: "span",
                    messages: {
                        hrmsIDHOD: "Enter HRMS Id",
                        staffingName: "Enter  Name",
                        staffingNameHindi: "Enter  Name in Hindi",
                        // joinedDate: "Enter Joining Date ",
                        // mobileNo : "Enter Mobile No" ,

                    }
                });
                if (validator.form()) { // validation perform
                    //$('form#training_center').submit();
                    return true;
                }

            }
            function SanPostList() {
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
                                    <div class="col-md-10">
                                        <h2 class="text-white font-36"><spring:message code="label.icds.project.sanction.join.incumbency"/></h2>
                                    <ol class="breadcrumb text-left mt-10 white">
                                        <li><a href="homepage.htm"><spring:message code="home"/></a></li>
                                        <li><a href="homepage.htm"><spring:message code="lable.icds.units"/></a></li>
                                        <li><a href="icdsProjectList.htm"><spring:message code="${MenuLevel}"/></a></li>
                                        <li><a href="javascript:SanPostList()"><spring:message code="label.icds.project.sanction.post"/></a></li>
                                        <li class="active"><spring:message code="entry"/></li>
                                    </ol>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>

                <section>

                    <div class="container">
                        <form:form id="training_center" method="POST" action="joinSave.htm" commandName="command" >
                            <form:hidden path="projectName" id="projectName" value="${projectName}"/>
                            <form:hidden path="projectCode" id="projectCode" value="${projectCode}"/>
                            <form:hidden id="sanctionPostName" path="sanctionPostName" value="${postName}"/>
                            <form:hidden id="incumbencyId" path="incumbencyId" value="${incId}"/>
                            <form:hidden id="sanctionPostCode" path="sanctionPostCode" value="${postCode}"/>
                            <form:hidden id="trackID" path="trackID" value="${trackId}"/>
                            <form:hidden id="lastIncumJoinDt" path="lastIncumJoinDt" value="${lastRelevingDate}"/>
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
                            <div class="col-md-6  ">
                                <div class="border-1px bg-white clearfix p-20 pt-10 pb-20">
                                    <h4 class="mt-0 mb-30 line-bottom">
                                        <spring:message code="label.icds.project.sanction.join.incumbency.post"/> :${postName} </h4>
                                    <!-- Contact Form -->


                                    <div class="row">
                                        <div class="col-sm-12">

                                            <div class="form-group">
                                                <label for="hrmsIDHOD"><spring:message code="label.icds.hrms.head.of.office"/><small>*</small></label>
                                                <form:input path="hrmsIDHOD" id="hrmsIDHOD" class="form-control" data-parsley-trigger="change" placeholder="Enter HRMS id " required="" aria-required="true"/>
                                                <input type="button" value="<spring:message code="get.details"/>" name = "btn" class="btn btn-default btn-flat btn-theme-colored" onclick="getHRMSIDDetails()"/>

                                            </div>

                                            <div class="form-group">
                                                <label for="staffingName"><spring:message code="label.icds.project.sanction.join.post.name"/><small>*</small></label>
                                                <form:input path="staffingName" id="staffingName" class="form-control" data-parsley-trigger="change" placeholder="Enter  Name" required="" aria-required="true"/>

                                            </div>    

                                            <div class="form-group">
                                                <label for="staffingNameHindi"><spring:message code="label.icds.project.sanction.join.post.name.hindi"/><small>*</small></label>
                                                <form:input path="staffingNameHindi" id="staffingNameHindi" class="form-control" data-parsley-trigger="change" placeholder="Enter  Name in Hindi" required="" aria-required="true"/>

                                            </div> 
                                            <div class="form-group">
                                                <label for="joinedDate"><spring:message code="label.icds.project.sanction.join.date"/><small>*</small></label>
                                                <form:input path="joinedDate" id="joinedDate" class="form-control" data-parsley-trigger="change" placeholder="Enter Joining Date" required="" aria-required="true" data-date-format="dd-M-yyyy"/>
                                                &nbsp;
                                                <form:select path="joiningTime" class="form-control">
                                                    <form:option value="default" label="Select" itemValue="0"/>
                                                    <form:options items="${TimeList}" />
                                                </form:select>
                                            </div>

                                        </div>
                                    </div>


                                </div>
                            </div>    

                            <div class="clearfix"></div>                    
                            <div class="text-center m-10">
                                <input type="submit" value="<spring:message code="save"/>" name = "icdsFormAction" onclick="javascript:submitForm();" class="btn btn-dark btn-theme-colored btn-flat mr-5" data-loading-text="Please wait..."/>
                                <a href="javascript:SanPostList()" >
                                    <button class="btn btn-dark btn-theme-colored btn-flat mr-5" type="button">
                                        <spring:message code="cancel"/>
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
