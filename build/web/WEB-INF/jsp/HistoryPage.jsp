<%-- 
    Document   : HistoryPage
    Created on : Oct 22, 2017, 8:44:13 PM
    Author     : Mana Jena
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Action Taken History</title>      
        <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="css/font-awesome.min.css">                
        <link rel="stylesheet" type="text/css" href="css/style.css">        
        <style>
            #dt{text-indent: -500px;height:25px; width:200px;}
            td {
                font-size: 11px;
            }
            tr:nth-child(even) {background-color: #f2f2f2}
        </style>
    </head>
    <body>
        <table border="1" height="95%" class="tariff_table">
            <tr>
                <td>
                    <strong>Project:</strong> ${project.projectDesc}
                </td>
            </tr>

            <tr>
                <td style="height:90%;vertical-align: top">
                    <table style="width:970px;" border=0 class="tariff_table">
                        <tr>
                            <td colspan="2">
                                <table border="1" style="width:970px;display: block;height: 380px;overflow-y: auto;overflow-x: hidden;">
                                    <thead>
                                        <tr>
                                            <th width="20px"><center><strong>Sl</strong></center></th>
                                            <th width="270px"><center><strong>Activity</strong></center></th>
                                            <th width="100px"><center><strong>Date</strong></center></th>
                                            <th width="100px"><center><strong>Decision</strong></center></th>
                                            <th width="125px"><center><strong>Observations</strong></center></th>
                                            <th width="125px"><center><strong>Compliance Note</strong></center></th>
                                            <th width="50px"><center><strong>Other Details</strong></center></th>
                                            <th width="150px"><center><strong>Attachments</strong></center></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${alHistory}" var="history" varStatus="cntStatus">
                                            <tr>
						<td>${cntStatus.index+1}</td>
                                                <td>${history.activity}</td>
                                                <td>${history.completionDate}</td>
                                                <td>${history.decision}</td>
                                                <td>&nbsp;</td>
                                                <td>&nbsp;</td>
                                                <td>&nbsp;</td>
                                                <td>
                                                    <c:forEach items="${alAttachments}" var="alAttachment" begin="${cntStatus}" end="${cntStatus}">
                                                        <c:forEach items="${alAttachment}" var="attachment" varStatus="cntA">
                                                            <a href="downloadaction.do?photopath=${alAttachment.strFILE_PATH}" target="_blank">${cntA.index+1}"."+${alAttachment.strFILE_NAME}</a></br>
                                                        </c:forEach>
                                                    </c:forEach>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </body>
</html>
