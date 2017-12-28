<%-- 
    Document   : header
    Created on : Oct 16, 2017, 9:26:06 PM
    Author     : surendra
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!-- Header -->
<header class="header">
    <div class="header-top bg-theme-colored sm-text-center p-0">
        <div class="container">
            <div class="row">
                <div class="col-md-4">
                    <div class="widget no-border m-0 mt-10">
                        <ul class="list-inline sm-text-center">
                            <li>
                                <h4 class="text-white p-0 m-0">Digital Raj - ICDS</h4>
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="col-md-4 text-center">
                    <ul class="styled-icons icon-theme-colored icon-dark icon-circled icon-sm  sm-pull-none sm-text-center mt-5 mt-sm-15">
                        <li><a href="#" data-bg-color="#EF3E56" style="background: rgb(239, 62, 86) !important;"><i class="fa fa-user-circle text-white" ></i> </a><p class="text-white mb-0 mt-5 line-height-1em pull-left" >${SessionAttr.userid} </p></li>
                    </ul>
                </div>
                <div class="col-md-4">
                    <div class="widget no-border m-0">

                        <ul class="styled-icons icon-theme-colored icon-dark icon-circled icon-sm pull-right sm-pull-none sm-text-center mt-5 mt-sm-15">
                            <li><a href="#"><i class="fa fa-facebook text-white" data-toggle="tooltip" data-placement="top" title="Facebook"></i></a></li>
                            <li><a href="#"><i class="fa fa-twitter text-white" data-toggle="tooltip" data-placement="top" title="Twitter"></i></a></li>
                            <li><a href="?lang=en"><i class="fa fa-language text-white fa-lg" data-toggle="tooltip" data-placement="top" title="English"></i></a></li>
                            <li><a href="?lang=hindi"><i class="fa fa-language text-white fa-lg" data-toggle="tooltip" data-placement="top" title="Hindi"></i></a></li>
                            <li><a href="login.html"><i class="fa fa-sign-in text-white fa-lg" data-toggle="tooltip" data-placement="top" title="Sign In"></i></a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="header-nav">
        <div class="header-nav-wrapper navbar-scrolltofixed bg-white">
            <div class="container">
                <nav id="menuzord-right" class="menuzord default">
                    <a class="menuzord-brand pull-left flip xs-pull-center" href="index.html">
                        <img src="images/logo.png" alt="">
                    </a>
                    <ul class="menuzord-menu">
                        <li class="active"><a href="homepage.htm"><spring:message code="home"/></a></li>
                            <c:if test="${SessionAttr.level =='STATE'}">
                            <!--                            <li><a href="#">ICDS Units </a></li>
                                                        <li><a href="entitylist.htm?selqid=1">Anganwadi Center </a></li>
                                                        <li><a href="entitylist.htm?selqid=2">Frontline Worker </a></li>
                                                        <li><a href="entitylist.htm?selqid=3">Training Center</a></li>
                                                        <li><a href="entitylist.htm?selqid=4">Nandghar Sponsor</a></li>
                                                        <li><a href="#">Programme</a>
                                                            <ul class="dropdown">
                                                                <li><a href="projectList.htm?projectId=1">Model Pre-School</a></li>
                                                            </ul>-->
                        </c:if>

                        <li><a href="#"><spring:message code="lable.icds.units"/> </a>
                            <ul class="dropdown">
                                <li><a href="icdsProjectList.htm?menuLevel=DIST"><spring:message code="DIST"/></a></li>
                                <li><a href="icdsProjectList.htm?menuLevel=PROJECT"><spring:message code="PROJECT"/></a></li>
                                <li><a href="icdsProjectList.htm?menuLevel=SECTOR"><spring:message code="SECTOR"/></a></li>
                                <li><a href="sendNoti.htm"><spring:message code="lable.noti.header"/></a></li>
                                <li><a href="inboxNoti.htm"><spring:message code="lable.to.do.list"/></a></li>
                                <li><a href="outboxNoti.htm"><spring:message code="lable.outbox"/></a></li>
                            </ul>
                        </li>
                        <li><a href="awclist.htm">Anganwadi Center </a></li>
                        <li><a href="frontlineWorkerList.htm">Frontline Worker </a></li>
                        <li><a href="trainingCenterList.htm">Training Center</a></li>
                        <li><a href="nandgharlist.htm">Nandghar Sponsor</a></li>


                    </ul>
                </nav>
            </div>
        </div>
    </div>  
</header>
