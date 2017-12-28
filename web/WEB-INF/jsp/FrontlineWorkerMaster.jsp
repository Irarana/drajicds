<%-- 
    Document   : FrontlineWorkerMaster
    Created on : Oct 8, 2017, 11:06:51 AM
    Author     : surendra
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
            $(document).ready(function () {
                $('#dob').datepicker({
                });

                $('#doj').datepicker({
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
                control.makeTransliteratable(['workerNameHindi']);
                control.makeTransliteratable(['address']);

            }
            google.setOnLoadCallback(onLoad);

            function getAWCName() {
                aWCCode = $("#aWCCode").val();
                if (aWCCode == '') {
                    alert('Please Enter Anganwadi Center Code');
                } else {


                    $.ajax({
                        'url': 'getAwcNameController.htm?awcCode=' + aWCCode,
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
                            if (data.awcName == '') {
                                alert('Awc Name not Found.');
                                $("#awcName").val('');
                                $("#awcType").val('');
                            } else {
                                str = data.awcName.split("@");
                                $("#awcName").val(str[0]);
                                $("#awcType").val(str[1]);

                            }
                        }
                    });
                }
            }



        </script>

    </head>
    <body class="boxed-layout pt-40 pb-40 pt-sm-0" data-bg-img="images/pattern/p37.png">
        <div id="wrapper" class="clearfix">
            <jsp:include page="header.jsp"></jsp:include>
                <!-- Start main-content -->
                <div class="main-content">
                    <!-- Section: inner-header -->
                    <section class="inner-header divider layer-overlay overlay-dark-8" data-bg-img="images/bg/bg2.jpg">
                        <div class="container pt-90 pb-40">
                            <!-- Section Content -->
                            <div class="section-content">
                                <div class="row"> 
                                    <div class="col-md-6">
                                        <h2 class="text-white font-36"><spring:message code="label.fw.title"/></h2>
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
                        <form:form id="training_center" method="POST" action="frontlineWorkerControllerSave.htm" commandName="command" >
                            <div class="col-md-6  ">
                                <div class="border-1px bg-white clearfix p-20 pt-10 pb-20">
                                    <h4 class="mt-0 mb-30 line-bottom"><spring:message code="label.fw.section1"/></h4>
                                    <!-- Contact Form -->
                                    

                                        <div class="row">
                                            <div class="col-sm-12">
                                                <div class="form-group">
                                                    <form:hidden path="workerId"/>
                                                    <label for="form_name"><spring:message code="label.fw.workerName"/><small>*</small></label>
                                                    <form:input path="workerName" id="workerName" class="form-control" data-parsley-trigger="change" placeholder="Enter Worker Name" aria-required="true"/>
                                                </div>

                                                <div class="form-group">
                                                    <label for="form_name"><spring:message code="label.fw.workernameHindi"/><small>*</small></label>
                                                    <form:input path="workerNameHindi" id="workerNameHindi" class="form-control" data-parsley-trigger="change" KeyboardLayout="HINDI" placeholder="Enter Worker name in Hindi" required="" aria-required="true"/>
                                                </div>
                                                <div class="form-group">
                                                    <label for="form_name"><spring:message code="label.fw.relation"/><small>*</small></label>
                                                    <form:select path="taggedRelation" class="form-control">
                                                        <form:option value="" label="Select"/>
                                                        <form:options items="${relationList}"/>
                                                    </form:select>
                                                </div>

                                                <div class="form-group">
                                                    <label for="form_name"><spring:message code="label.fw.relname"/><small>*</small></label>
                                                    <form:input path="relationName" id="relationName" class="form-control" data-parsley-trigger="change" placeholder="Enter Relation Name" required="" aria-required="true"/>
                                                </div>

                                                <div class="form-group">
                                                    <label for="form_name"><spring:message code="label.fw.dob"/><small>*</small></label>
                                                    <form:input path="dob" id="dob" style="width: 120px" class="form-control" data-parsley-trigger="change" required="" readonly="true" aria-required="true" data-date-format="dd-M-yyyy"/>
                                                </div>

                                                <div class="form-group">
                                                    <label for="form_name"><spring:message code="label.fw.category"/><small>*</small></label>
                                                    <form:select path="category" class="form-control">
                                                        <form:option value="" label="Select"/>
                                                        <form:options items="${categoryList}"/>
                                                    </form:select>
                                                </div>

                                                <div class="form-group">
                                                    <label for="form_name"><spring:message code="label.fw.aadhaar"/><small>*</small></label>
                                                    <form:input path="aadhaarNo" id="aadhaarNo" class="form-control" data-parsley-trigger="change" placeholder="Enter 12 digit Aadhaar Number" required="" aria-required="true"/>
                                                </div>

                                                <div class="form-group">
                                                    <label for="form_name"><spring:message code="label.fw.bhamshah"/><small>*</small></label>
                                                    <form:input path="bhamshahNo" id="bhamshahNo" class="form-control" data-parsley-trigger="change" placeholder="Enter Bhamshah Number" required="" aria-required="true"/>
                                                </div>


                                            </div>
                                        </div>

                                    
                                </div>
                            </div>    
                            <div class="col-md-6">
                                <div class="border-1px bg-white clearfix p-20 pt-10 pb-20">

                                    <h4 class="mt-0 mb-30 line-bottom"><spring:message code="label.fw.section2"/></h4>
                                    <!-- Contact Form -->
                                    

                                        <div class="row">
                                            <div class="col-sm-12">
                                                <div class="form-group">
                                                    <label for="form_name"><spring:message code="label.fw.address"/><small>*</small></label>
                                                    <form:textarea path="address" id="address" rows="5" cols="30" class="form-control" data-parsley-trigger="change" KeyboardLayout="HINDI" placeholder="Enter Address" required="" aria-required="true"/>
                                                </div>

                                                <div class="form-group">
                                                    <label for="form_name"><spring:message code="label.fw.mobile"/><small>*</small></label>
                                                    <form:input path="mobile" id="mobile" class="form-control" data-parsley-trigger="change" placeholder="Enter Mobile Number" required="" aria-required="true"/>
                                                </div>
                                            </div>
                                        </div>
                                

                                </div>
                            </div> 
                            <div class="clearfix"></div>
                            <div class="col-md-6  ">
                                <div class="border-1px bg-white clearfix p-20 pt-10 pb-20">
                                    <h4 class="mt-0 mb-30 line-bottom"><spring:message code="label.fw.section3"/></h4>
                                    <!-- Contact Form -->
                                    

                                        <div class="row">
                                            <div class="col-sm-12">
                                                <div class="form-group">
                                                    <label for="form_name"><spring:message code="label.fw.bankacct"/><small>*</small></label>
                                                    <form:input path="bankAcct" id="bankAcct" class="form-control" data-parsley-trigger="change" placeholder="Enter Bank Account Number" required="" aria-required="true"/>
                                                </div>

                                                <div class="form-group">
                                                    <label for="form_name"><spring:message code="label.fw.ifsc"/><small>*</small></label>
                                                    <form:input path="ifcsCode" id="ifcsCode" class="form-control" data-parsley-trigger="change" placeholder="Enter IFSC Code" required="" aria-required="true"/>
                                                </div>    



                                            </div>
                                        </div>

                                   
                                </div>
                            </div>    
                            <div class="col-md-6">
                                <div class="border-1px bg-white clearfix p-20 pt-10 pb-20">

                                    <h4 class="mt-0 mb-30 line-bottom"><spring:message code="label.fw.section4"/></h4>
                                    <!-- Contact Form -->
                                    

                                        <div class="row">
                                            <div class="col-sm-12">
                                                <div class="form-group">
                                                    <label for="form_name"><spring:message code="label.fw.doj"/><small>*</small></label>
                                                    <form:input path="doj" id="doj" style="width: 120px" class="form-control doj" data-parsley-trigger="change" required="" readonly="true" aria-required="true" data-date-format="dd-M-yyyy"/>
                                                </div>

                                                <div class="form-group">
                                                    <label for="form_name"><spring:message code="label.fw.awcCode"/><small>*</small></label>
                                                    <form:input path="aWCCode" id="aWCCode" class="form-control" data-parsley-trigger="change" placeholder="Enter AWC Code" required="" aria-required="true"/>
                                                    <input type="button" value="Fetch Name" name = "btn" class="btn btn-default btn-flat btn-theme-colored" onclick="getAWCName()"/>
                                                </div>

                                                <div class="form-group">
                                                    <label for="form_name">AWC Name<small>*</small></label>
                                                    <form:input path="awcName" id="awcName" class="form-control" data-parsley-trigger="change" KeyboardLayout="HINDI" readonly="true" required="" aria-required="true"/>
                                                </div>

                                                <div class="form-group">
                                                    <label for="form_name">AWC Type<small>*</small></label>
                                                    <form:input path="awcType" id="awcType" class="form-control" data-parsley-trigger="change" KeyboardLayout="HINDI" readonly="true" required="" aria-required="true"/>
                                                </div>

                                                <div class="form-group">
                                                    <label for="form_name"><spring:message code="label.fw.desig"/><small>*</small></label>
                                                    <form:select path="workerDesig" class="form-control">
                                                        <form:option value="" label="Select"/>
                                                        <form:options items="${desigList}"/>
                                                    </form:select>
                                                </div>
                                            </div>
                                        </div>
                                  

                                </div>
                            </div> 
                            <div class="clearfix"></div>
                            <div class="col-md-6">
                                <div class="border-1px bg-white clearfix p-20 pt-10 pb-20">

                                    <h4 class="mt-0 mb-30 line-bottom"><spring:message code="label.fw.section5"/></h4>
                                    <!-- Contact Form -->
                                    

                                        <div class="row">
                                            <div class="col-sm-12">
                                                <div class="form-group">
                                                    <label for="form_name"><spring:message code="label.fw.education"/><small>*</small></label>
                                                    <form:select path="education" class="form-control">
                                                        <form:option value="" label="Select"/>
                                                        <form:options items="${educationList}"/>
                                                    </form:select>
                                                </div>

                                                <div class="form-group">
                                                    <label for="form_name"><spring:message code="label.fw.jobtraing"/><small>*</small></label>
                                                    <form:select path="ifJobTraining" class="form-control">
                                                        <form:option value="" label="Select"/>
                                                        <form:option value="Y" label="Yes"/>
                                                        <form:option value="N" label="No"/>
                                                    </form:select>
                                                </div>

                                                <div class="form-group">
                                                    <label for="form_name"><spring:message code="label.fw.year"/><small>*</small></label>
                                                    <form:input path="jobTrainYear" id="jobTrainYear" class="form-control" data-parsley-trigger="change" placeholder="Job Trainiing Year" required="" aria-required="true"/>
                                                </div>    

                                                <div class="form-group">
                                                    <label for="form_name"><spring:message code="label.fw.refresh"/><small>*</small></label>
                                                    <form:input path="refreshTraining" id="refreshTraining" class="form-control" data-parsley-trigger="change" placeholder="Refresh Training" required="" aria-required="true"/>
                                                </div> 
                                            </div>
                                        </div>
                                    

                                </div>
                            </div> 
                            <div class="clearfix"></div>                    
                            <div class="text-center m-10">
                                <input type="submit" value="Save" name = "frontline" class="btn btn-dark btn-theme-colored btn-flat mr-5" data-loading-text="Please wait..."/>
                                <c:if test='${not empty "${command.workerId}"}'>
                                    <input type="submit" value="Delete" name = "frontline" class="btn btn-dark btn-theme-colored btn-flat mr-5" data-loading-text="Please wait..."/>
                                </c:if>
                                <input type="submit" value="Cancel" name = "frontline" class="btn btn-default btn-flat btn-theme-colored"/>
                            </div>
                        </form:form>         
                    </div> 
                </section>  
            </div>
            <!-- end main-content -->

            <!-- Footer -->
            <jsp:include page="footer.jsp"></jsp:include>
            <a class="scrollToTop" href="#"><i class="fa fa-angle-up"></i></a>
        </div>
        <!-- end wrapper -->

    </body>
</html>
