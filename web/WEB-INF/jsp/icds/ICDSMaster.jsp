<%-- 
    Document   : ICDSMaster
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
//                $('#dob').datepicker({
//                });
//                $('#doj').datepicker({
//                });

                $('#districtCode').change(function () {
                    if ($('#districtCode').val() != '') {
                        $.ajax({
                            type: "GET",
                            url: "getProjForDis.htm",
                            data: {distCode: $('#districtCode').val()},
                            success: function (data) {
                                var keys = Object.keys(data);
                                $('#selectedProjectId').empty();
                                var newOption = '<option value="select">Select</option>';
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
                            alert(JSON.stringify(data.hodName));
                            if (data.hrmsIdName == '') {
                                alert('HRMDID not Found.');
                                $("#hodName").val('');
                                $("#hodNameHindi").val('');
                            } else {
                                alert(data.hrmsIdName)
                                str = data.hrmsIdName.split("@");
                                alert(str[0]);
                                $("#hodName").val(str[0]);
                                alert(str[1]);
                                $("#hodNameHindi").val(str[1]);
                            }
                        }
                    });
                }
            }

            function submitForm() {
                $.validator.addMethod("validateOwner", function (value, element) {
                    return this.optional(element) || value != 'default';
                }, " Select Building Owner");
                $.validator.addMethod("validateEmail", function (value) {
                    var re = /\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/;
                    return value != '' && re.test(value);
                }, " Enter email");
                $.validator.addMethod("validateMobile", function (value) {
                    var re = /^\d{10}$/;
                    return value != '' && re.test(value);
                }, " Enter Mobile");
                $.validator.addMethod("validatePhone", function (value) {
                    var re = /^[0-9]+$/;
                    return value != '' && re.test(value);
                }, " Enter Phone");

                $.validator.addMethod("validateProject", function (value, element) {
                    return this.optional(element) || value != 'select';
                }, " Select Project");

                $.validator.addMethod("validateDistrict", function (value, element) {
                    return this.optional(element) || value != 'select';
                }, " Select District");

                $.validator.addMethod("validateLongitude", function (value) {
                    var re = /^(\+|-)?(?:[6][9]|[7][0-9])(?:(?:\.[0-9]{1,14})?)$/;
                    return value != '' && re.test(value);
                }, " Enter Valid Longitude");


                $.validator.addMethod("validateLatitude", function (value) {
                    var re = /^(\+|-)?(?:[2][3-9]|[3][0-1])(?:(?:\.[0-9]{1,14})?)$/;
                    return value != '' && re.test(value);
                }, " Enter Valid Latitude");

                var validator = $("#training_center").validate({
                    rules: {
                        officeName: "required",
                        officeNameHindi: "required",
                        officeAddress: "required",
                        longitude: {
                            validateLongitude: true
                        },
                        latitude: {
                            validateLatitude: true
                        },
                        selectedProjectId: {
                            validateProject: true
                        },
                        districtCode: {
                            validateDistrict: true
                        },
                        buildingOwner: {
                            validateOwner: true
                        },
                        hrmsIDHOD: "required",
                        hodName: "required",
                        hodNameHindi: "required",
                        hooChoice: "required",
                        phoneNo: {validatePhone: true},
                        mobileNo: {validateMobile: true},
                        email: {validateEmail: true}
                    },
                    errorElement: "span",
                    messages: {
                        officeName: "Enter Office Name",
                        officeNameHindi: "Enter Office Name in Hindi",
                        officeAddress: "Enter Address",
                        hrmsIDHOD: "Enter HRMS Id",
                        hodName: "Enter HOO Name",
                        hodNameHindi: "Enter HOO Name in Hindi",
                        hooChoice: "Required",
                        //phoneNo : "Enter Phone No" ,
                        // mobileNo : "Enter Mobile No" ,

                    }
                });
                if (validator.form()) { // validation perform
                    //alert($("#projectCode").val());
                    if ($("#projectCode").val() == '') {
                        $('form#training_center').attr('action', 'icdsProjectSave.htm');
                    } else {
                        $('form#training_center').attr('action', 'icdsProjectEditSave.htm');
                    }
                    $('form#training_center').submit();
                }
            }
            function cancelForm(level) {
                //alert(level);
                location.href = "icdsProjectList.htm?menuLevel=" + level;

            }


            function addZeroes(varID) {
                var num = Number($("#" + varID).val());
                if (isNaN(num)) {
                    $("#" + varID).val(0);
                } else {
                    $("#" + varID).val(num.toFixed(14));
                }

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
                                        <h2 class="text-white font-36"><spring:message code="label.mn.icds.projects"/></h2>
                                    <ol class="breadcrumb text-left mt-10 white">
                                        <li><a href="homepage.htm"><spring:message code="home"/></a></li>
                                        <li><a href="homepage.htm"><spring:message code="lable.icds.units"/></a></li>
                                        <li><a href="icdsProjectList.htm"><spring:message code="${MenuLevel}"/></a></li>
                                        <li class="active"><spring:message code="entry"/></li>
                                    </ol>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>

                <section>

                    <div class="container">
                        <form:form id="training_center" method="POST" commandName="command" >
                            <form:hidden path="substattivePostId" id="substattivePostId" value="${substattivePostId}"/>
                            <form:hidden path="projectCode" value="${projectCode}"/>

                            <form:hidden path="districtName" id="districtName" value="${districtName}" />
                            <form:hidden path="level" id="level" value="${menuLevel}" />
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
                                    <h4 class="mt-0 mb-30 line-bottom"><spring:message code="label.icds.office.details"/></h4>
                                    <!-- Contact Form -->
                                   

                                    <div class="row">
                                        <div class="col-sm-12">
                                            <c:if test="${MenuLevel ne 'DIST'}">
                                                <div class="form-group">

                                                    <label for="districtCode"><spring:message code="label.icds.district"/><small>*</small> &nbsp;:</label>

                                                    <form:select path="districtCode" class="form-control">
                                                        <form:option value="select" label="Select" itemValue="0"/>
                                                        <form:options items="${DistrictList}" />
                                                    </form:select>

                                                </div>
                                            </c:if>
                                            <c:if test="${MenuLevel eq 'DIST'}">
                                                <form:hidden path="districtCode" id="districtCode" value="${districtCode}"/>
                                            </c:if>
                                            <c:if test="${MenuLevel eq 'SECTOR'}">

                                                <div class="form-group">
                                                    <label for="selectedProjectId"><spring:message code="PROJECT"/></label>


                                                    <form:select id="selectedProjectId" path="selectedProjectId" class="form-control">
                                                        <form:option value="select" label='Select' itemValue="0"/>
                                                        <form:options items='${ProjectList}'/>
                                                    </form:select>

                                                </div>
                                            </c:if>
                                            <div class="form-group">
                                                <label for="officeName"><spring:message code="label.icds.district.office.name"/><small>*</small></label>
                                                <form:input path="officeName" id="officeName" class="form-control" data-parsley-trigger="change" placeholder="Enter Office Name" aria-required="true"/>
                                            </div>

                                            <div class="form-group">
                                                <label for="officeNameHindi"><spring:message code="label.icds.district.office.name.hindi"/><small>*</small></label>
                                                <form:input path="officeNameHindi" id="officeNameHindi" class="form-control" data-parsley-trigger="change" KeyboardLayout="HINDI" placeholder="Enter Office name in Hindi" required="" aria-required="true"/>
                                            </div>
                                            <div class="form-group">
                                                <label for="officeAddress"><spring:message code="label.icds.district.office.address"/><small>*</small></label>
                                                <form:textarea path="officeAddress" id="officeAddress" rows="5" cols="30" class="form-control" data-parsley-trigger="change" KeyboardLayout="HINDI" placeholder="Enter Address" required="" aria-required="true"/>
                                            </div>

                                            <div class="form-group">
                                                <label for="longitude"><spring:message code="label.icds.district.office.longitude"/><small>*</small></label>
                                                <form:input path="longitude" id="longitude" class="form-control" data-parsley-trigger="change" placeholder="Longitude" required="" aria-required="true" onchange="javascript:addZeroes('longitude');" />
                                            </div>

                                            <div class="form-group">
                                                <label for="latitude"><spring:message code="label.icds.district.office.latitude"/><small>*</small></label>
                                                <form:input path="latitude" id="latitude" class="form-control" data-parsley-trigger="change" placeholder="Latitude" required="" aria-required="true" onchange="javascript:addZeroes('latitude');" />
                                            </div>

                                            <div class="form-group">
                                                <label for="buildingOwner"><spring:message code="label.icds.district.office.building.owner"/><small>*</small></label>
                                                <form:select path="buildingOwner" class="form-control">
                                                    <form:option value="default" label="Select"/>
                                                    <form:options items="${BuildingOwner}"/>
                                                </form:select>
                                            </div>



                                        </div>
                                    </div>


                                </div>
                            </div>    
                            <div class="col-md-6">
                                <div class="border-1px bg-white clearfix p-20 pt-10 pb-20">

                                    <h4 class="mt-0 mb-30 line-bottom"><spring:message code="label.icds.head.of.office.details"/></h4>
                                    <!-- Contact Form -->

                                    <div class="row">
                                        <div class="col-sm-12">
                                            <div class="form-group">
                                                <label for="hooChoice" ><spring:message code="label.icds.head.of.office"/><small>*</small>&nbsp;&nbsp;</label>
                                                <form:radiobutton path="hooChoice" value="inPosition" id="inPosition"  data-parsley-trigger="change" placeholder="" required="" aria-required="true"/>
                                                <label for="hooChoice"><spring:message code="label.icds.hrms.head.of.office.inposition"/></label>
                                                <form:radiobutton path="hooChoice" value="additionalChargers" id="additionalChargers" data-parsley-trigger="change" placeholder="" required="" aria-required="true"/>
                                                <label for="hooChoice"><spring:message code="label.icds.hrms.head.of.office.additionalcharges"/></label>

                                            </div>

                                            <div class="form-group">
                                                <label for="hrmsIDHOD"><spring:message code="label.icds.hrms.head.of.office"/><small>*</small></label>
                                                <form:input path="hrmsIDHOD" id="hrmsIDHOD" class="form-control" data-parsley-trigger="change" placeholder="Enter HRMS id of Ofiice Head" required="" aria-required="true"/>
                                                <input type="button" value="<spring:message code="get.details"/>" name = "btn" class="btn btn-default btn-flat btn-theme-colored" onclick="getHRMSIDDetails()"/>

                                            </div>

                                            <div class="form-group">
                                                <label for="hodName"><spring:message code="label.icds.head.of.office.name"/><small>*</small></label>
                                                <form:input  path="hodName" id="hodName" class="form-control" data-parsley-trigger="change" placeholder="Enter HOD Name" required="" aria-required="true"/>

                                            </div>    

                                            <div class="form-group">
                                                <label for="hodNameHindi"><spring:message code="label.icds.head.of.office.name.hindi"/><small>*</small></label>
                                                <form:input path="hodNameHindi" id="hodNameHindi" class="form-control" data-parsley-trigger="change" placeholder="Enter HOD Name in Hindi" required="" aria-required="true"/>

                                            </div> 
                                            <div class="form-group">
                                                <label for="phoneNo"><spring:message code="label.icds.head.of.office.phone.no"/><small>*</small></label>
                                                <form:input pattern="[0-9.]+" path="phoneNo" id="phoneNo" class="form-control" data-parsley-trigger="change" placeholder="Enter Phone No" required=""  aria-required="true"/>

                                            </div> 
                                            <div class="form-group">
                                                <label for="mobileNo"><spring:message code="label.icds.head.of.office.mobile.no"/><small>*</small></label>
                                                <form:input path="mobileNo" id="mobileNo" class="form-control" data-parsley-trigger="change" placeholder="Enter Mobile No" required="" aria-required="true"/>

                                            </div> 
                                            <div class="form-group">
                                                <label for="email"><spring:message code="label.icds.head.of.office.email"/><small>*</small></label>
                                                <form:input path="email" id="email" class="form-control" data-parsley-trigger="change" placeholder="Enter Email"  required="" aria-required="true" />

                                            </div> 
                                        </div>
                                    </div>


                                </div>
                            </div> 

                            <div class="clearfix"></div>                    
                            <div class="text-center m-10">
                                <input type="submit" value="<spring:message code="save"/>" name = "icdsFormAction"   onclick="javascript:submitForm();" class="btn btn-dark btn-theme-colored btn-flat mr-5" data-loading-text="Please wait..."/>
                                <a href="javascript:cancelForm('${menuLevel}')" >
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
