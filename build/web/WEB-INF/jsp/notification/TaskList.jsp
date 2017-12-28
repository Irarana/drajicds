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
<!--        <link rel="stylesheet" type="text/css" href="js/datatable/jquery.dataTables.min.css">-->
        <link href="css/main.min.css" rel="stylesheet" type="text/css"/>
        <!--<link rel="stylesheet" type="text/css" href="js/datatable/dataTables.bootstrap.min.css">-->
        <script type="text/javascript" src="js/datatable/jquery.dataTables.min.js"></script>
        <script type="text/javascript" src="js/datatable/datatable-bootstrap.js"></script>
        <script type="text/javascript" src="js/datatable/dataTables.fixedColumns.min.js"></script>
        <script type="text/javascript" src="js/datatable/dataTables.select.min.js"></script>
        <script src="js/custom.js"></script>
        <style type="text/css">
    	td.details-control {
    background: url('images/details_open.png') no-repeat center center;
    cursor: pointer;
}
tr.details td.details-control {
    background: url('images/details_close.png') no-repeat center center;
}
    </style>
        <script type="text/javascript">


            /* Formatting function for row details - modify as you need */
            function format(respondent,status) {
                // `d` is the original data object for the row
                return '<table class="stripetable row-border order-column table-condensed"  border="1" style="padding-left:10px;">' +
                        '<tr><td colspan="4"> Reassign Details' +
                        '</td></tr><tr>'+
                        '<td>Respondent:</td>' +
                        '<td><b>' + respondent + '</b></td> &nbsp;' +
                        '<td>Status:</td>' +
                        '<td><b>' + (status=='A'?'Not Completed':'Reassign') + '</b></td>' +
                        '</tr>' +
                        '</table>';
            }
            $(document).ready(function () {
                //$("#repliedBtn").prop("disabled", false);
                //$("#approverBtn").prop("disabled", false);
                if($('#selectedIsApproved').val()=='true'){
                  //  alert($('#selectedIsApproved').val());
                     $("#approverBtn").prop("disabled", false);
                }else{
                    $("#approverBtn").prop("disabled", true);
                }
                 if($('#selectedIsReplied').val()=='true'){
                    //alert($('#selectedIsReplied').val());
                     $("#repliedBtn").prop("disabled", false);
                }else{
                     $("#repliedBtn").prop("disabled", true);
                }
                var table = $('#datatable').DataTable({
                    scrollY: "300px",
                    scrollX: true,
                    scrollCollapse: true,
                    paging: true,
                    responsive: true,
                    select: true,
                    columnDefs: [
//                        {
//                            targets: [0],
//                            //class: 'details-control',
//                            orderable: false,
//                            //data: null,
//                            //defaultContent: "",
//                            searchable: false
//                        },
                        {
                            targets: [1],
                            visible: false,
                            searchable: false
                        },
                        {
                            targets: [2],
                            visible: false,
                            searchable: false
                        }
                        ,
                        {
                            targets: [15],
                            visible: false,
                            searchable: false
                        },
                        {
                            targets: [16],
                            visible: false,
                            searchable: false
                        }
                    ]
                });
// Array to track the ids of the details displayed rows
                var detailRows = [];
                $('#datatable tbody').on('click', 'tr td.details-control', function () {
                   // alert("Hi");
                    var tr = $(this).closest('tr');
                    var row = table.row(tr);
                    var idx = $.inArray(tr.attr('id'), detailRows);
                   // alert(row.child.isShown());
                    //alert("Bye"+row.data()[15]);
                    if (row.child.isShown()) {
                        tr.removeClass('details');
                        row.child.hide();

                        // Remove from the 'open' array
                        detailRows.splice(idx, 1);
                    }
                    else if(row.data()[15]!=''){
                        tr.addClass('details');
                        
                        row.child(format(row.data()[15],row.data()[16])).show();

                        // Add to the 'open' array
                        //alert(idx);
                        if (idx == -1) {
                            detailRows.push(tr.attr('id'));
                        }
                    }
                });

//                // On each draw, loop over the `detailRows` array and show any child rows
//                table.on('draw', function () {
//                    alert(detailRows);
//                    $.each(detailRows, function (i, id) {
//                       // alert("id"+id);
//                        $('#' + id + 'td.details-control').trigger('click');
//                    });
//                });
                
                
                var about = '';
                var respondent = '';
                var mark = '';
                $('#datatable tbody').on('click', 'tr', function () {
                    $("#reassignBtn").prop("disabled", false);
                    var rowData = table.row(this).data();
                    mark = rowData[1];
                    $("#selectedMarkCompleted").val(rowData[1]);
                    $("#selectedTaskID").val(rowData[2]);
                    about = rowData[4];
                    respondent = rowData[5];
                    // alert(about+"=="+respondent);
                    // alert($("#selectedMarkCompleted").val());
                    if ((about == respondent) || (about == 'PROJECT' && respondent == 'DIST') ||
                            (about == 'SECTOR' && respondent == 'PROJECT')) {
                        $("#reassignBtn").prop("disabled", true);
                    }

                });


                $('#reassignBtn').on('click', function () {
                     //alert("Hell");
                     // alert("about" + about);
                   //  alert("respondent" + respondent);
                   // alert("mark" + mark);
                    if (mark != '') {
                        // alert("inside "+mark);
                        if (mark == 'true') {
                            $('#currentRespondent').empty()
                            if (respondent == "DIST") {
                                if (about == "SECTOR") {
                                    $('#currentRespondent').append('<option value="PROJECT">Project</option>');
                                    $("#myModal").modal("show");
                                }
                                if (about == "AWC") {
                                    $('#currentRespondent').append('<option value="PROJECT">Project</option>');
                                    $('#currentRespondent').append('<option value="SECTOR">Sector</option>');
                                    $("#myModal").modal("show");

                                }
//                        else {
//                            $('#currentRespondent').append('<option value="SECTOR">Sector</option>');
//                            $("#myModal").modal("show");
//                        }

                            } else if (respondent == "PROJECT") {
                                if (about == "AWC") {
                                    $('#currentRespondent').append('<option value="SECTOR">Sector</option>');
                                    $("#myModal").modal("show");
                                }
                            }
                        }

                    } else {
                        alert("Please select row to reassign");
                    }
                });
                $('#datatable tbody tr td').dblclick(function () {
                    about = $(this).closest('tr').children()[2].textContent;
                    respondent = $(this).closest('tr').children()[3].textContent;
                    mark = $(this).closest('tr').children()[4].textContent;
                    // alert("about" + about);
                    // alert("respondent" + respondent);
                    // alert("mark" + $("#selectedMarkCompleted").val());
                    if ($("#selectedMarkCompleted").val() == 'true') {
                        $('#currentRespondent').empty()
                        if (respondent == "DIST") {
                            if (about == "SECTOR") {
                                $('#currentRespondent').append('<option value="PROJECT">Project</option>');
                                $("#myModal").modal("show");
                            }
                            if (about == "AWC") {
                                $('#currentRespondent').append('<option value="PROJECT">Project</option>');
                                $('#currentRespondent').append('<option value="SECTOR">Sector</option>');
                                $("#myModal").modal("show");

                            }
//                        else {
//                            $('#currentRespondent').append('<option value="SECTOR">Sector</option>');
//                            $("#myModal").modal("show");
//                        }

                        } else if (respondent == "PROJECT") {
                            if (about == "AWC") {
                                $('#currentRespondent').append('<option value="SECTOR">Sector</option>');
                                $("#myModal").modal("show");
                            }
                        }
                    }

                    // $("#txtfname").val($(this).closest('tr').children()[2].textContent);
                    //$("#txtlname").val($(this).closest('tr').children()[1].textContent);
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
                                        <h2 class="text-white font-36"><spring:message code="lable.tasklist"/></h2>
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
                    <form:form id="taskListForm"  method="POST" modelAttribute="InBoxBean" action="inboxAction.htm"  >

                        <div class="container">

                                <div class="panel">

                                    <div class="panel-body">
                                        <div class="row">
                                            <div class="col-md-12">
                                            <div class="form-group">
                                                
                                                    <li><B><spring:message code="lable.noti.notification"/>  :</B> <c:out value="${InBoxBean.selectedNotification}"/></li>
                                                    <li><B><spring:message code="lable.date"/>:</B> <c:out value="${InBoxBean.selectedNotiDate}"/></li>
                                                   

                                            </div>
                                        </div> 
                                          
                                            
                                        </div>

                                    </div>
                                </div>
                               
                            <div class="pull-right btn-group btn-group-sm" style="margin-bottom: 5px">

                                <button type="button" id="reassignBtn" class="btn btn-default btn-theme-colored" data-dismiss="modal">Re Assign</button>

                            </div>
                            <div class="clearfix"></div>
                            <div id="divDatatable" class="col-md-12 " style="margin-top: 10px">  
                                <table id="datatable" class="stripetable row-border order-column table-condensed" >
                                    <thead>
                                        <tr>
                                            <th></th>
                                            <th>Mark Completed</th>
                                            <th>Task ID</th>
                                            <th><spring:message code="sr.no"/></th>
                                            <th><spring:message code="lable.detail.about"/></th>
                                            <th><spring:message code="lable.noti.respondent"/></th>
                                            <th><spring:message code="lable.if.completed"/></th>
                                            <th><spring:message code="lable.completed.on"/></th>
                                            <th><spring:message code="lable.completed.by"/></th>
                                            <th><spring:message code="lable.if.replied"/></th>
                                            <th><spring:message code="lable.replied.on"/></th>
                                            <th><spring:message code="lable.replied.by"/></th>
                                            <th><spring:message code="lable.if.approve"/></th>
                                            <th><spring:message code="lable.approve.on"/></th>
                                            <th><spring:message code="lable.approve.by"/></th>
                                            <th>Reassign Respondent</th>
                                            <th>Reassign Status</th>
                                            
                                           

                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="tasklst" items="${InBoxBean.selectedTaskList}" varStatus="status" >
                                            <tr class="odd gradeX">
                                                <c:if test = "${not empty tasklst.relatedTaskList}"> 
                                                    <td class="details-control"></td>
                                                    </c:if>
                                                    <c:if test = "${empty tasklst.relatedTaskList}"> 
                                                        <td ></td>
                                                    </c:if>
                                                <td><c:out value="${tasklst.markCompleted}" /></td>
                                                <td><c:out value="${tasklst.taskId}" /></td>
                                                <td><c:out value="${status.count}" /></td>
                                                <td><c:out value="${tasklst.detailsAbout}" /></td>
                                                <td><c:out value="${tasklst.respondents.respondentType}" /></td>
                                                <td>
                                                    <c:if test = "${(tasklst.if_Complied=='Y') }"> 
                                                        <spring:message code="lable.yes"/>
                                                    </c:if>
                                                    <c:if test = "${tasklst.markCompleted}"> 
                                                        <input type="submit" value="<spring:message code='lable.mark.completed'/>" name="_markCompleted" class="btn btn-default btn-theme-colored" />
                                                    </c:if>
                                                    <c:if test = "${(tasklst.status=='R')}"> 
                                                        <spring:message code="lable.reassign"/>
                                                    </c:if>
                                                </td>
                                                <td><c:out value="${tasklst.completedOn}" /></td>
                                                <td><c:out value="${tasklst.completedByUserId}" /></td>
                                                <td><c:out value="${tasklst.respondents.if_Replied}" /></td>
                                                <td><c:out value="${tasklst.respondents.repliedOn}" /></td>
                                                <td><c:out value="${tasklst.respondents.repliedBy}" /></td>
                                                <td><c:out value="${tasklst.respondents.if_Approve}" /></td>
                                                <td><c:out value="${tasklst.respondents.approvedOn}" /></td>
                                                <td><c:out value="${tasklst.respondents.approvedBy}" /></td>
                                                <td>${tasklst.relatedTaskList[0].respondentType}</td>
                                                <td>${tasklst.relatedTaskList[0].status}</td>
                                               
                                            </tr>

                                        </c:forEach>


                                    </tbody>
                                </table>
                            </div>



                            <div class="clearfix"></div>                    
                            <div class="pull-right btn-group btn-group-sm" style="margin-bottom: 5px">

                                <input type="submit" value="<spring:message code='lable.back'/>" name="_target0" class="btn btn-dark btn-theme-colored btn-flat mr-5" />
                                <input type="submit" id="repliedBtn" value="<spring:message code='lable.submit'/>" name="_submit" class="btn btn-dark btn-theme-colored btn-flat mr-5" />
                                <input type="submit" id="approverBtn" value="<spring:message code='lable.approved'/>" name="_approve" class="btn btn-default btn-flat btn-theme-colored" />

                                <input type="hidden" value="1" name="_page">
                                <form:hidden path="selectedTaskID" id="selectedTaskID"/>

                                <form:hidden path="selectedMarkCompleted" id="selectedMarkCompleted"/>
                                 <form:hidden path="selectedIsApproved" id="selectedIsApproved" />
                                  <form:hidden path="selectedIsReplied" id="selectedIsReplied"/>
                            </div>



                        </div> 
                    </section>  
                </div>
                <!-- end main-content -->
                <div class="modal fade" id="myModal">
                    <div class="modal-dialog">
                        <div class="modal-content">


                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title">Re-Assign Task</h4>
                            </div>
                            <div class="modal-body">

                                <label for="currentRespondent"><spring:message code="lable.noti.respondent"/></label>
                                <form:select id="currentRespondent" path="currentRespondent" class="form-control">

                                </form:select>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default btn-theme-colored" data-dismiss="modal">Close</button>

                                <input type="submit" value="Save changes" name="_reAssign" class="btn btn-default btn-theme-colored" />
                                <input type="hidden" value="1" name="_page">

                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
            <!-- Footer -->
            <jsp:include page="../footer.jsp"></jsp:include>
            <a class="scrollToTop" href="#"><i class="fa fa-angle-up"></i></a>
        </div>
        <!-- end wrapper -->


    </body>
</html>
