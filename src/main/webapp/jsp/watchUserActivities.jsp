<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.session.getValue(language)}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="resources"/>
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="label.userActivities"/></title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/signin.css" rel="stylesheet">

    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
</head>
<body>

<a href="${pageContext.request.contextPath}/jsp/watchAllUsersAdmin.jsp" class="btn btn-secondary btn-lg active"
   role="button" aria-pressed="true"><fmt:message key="button.back"/></a>

<div class="container">
    <div class="btn-group pull-left">
        <p class="h1"><fmt:message key="label.userActivities"/> "${sessionScope.nameOfUserForUserActivities}"</p>
    </div>
</div>
<hr>

<div class="container">
    <div class="col-md-10">
        <table class="table">
            <thead>
            <tr>
                <th><fmt:message key="userActivity.activityCategoryName"/></th>
                <th><fmt:message key="userActivity.activityName"/></th>
                <th><fmt:message key="userActivity.durationOfActivity"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="userActivity" items="${sessionScope.userActivities}">
                <tr>
                    <td>${userActivity.activityCategoryName}</td>
                    <td>${userActivity.activityName}</td>
                    <td>${userActivity.activityDuration}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<hr>
<div class="container">
    <div class="btn-group pull-left">
        <p class="h4"><fmt:message key="label.totalTimeSpent"/>: ${sessionScope.totalTimeSpent}</p>
    </div>
</div>
<div>
    <a href="${pageContext.request.contextPath}/downloadReport" class="btn btn-secondary btn-lg active" role="button" aria-pressed="true"><fmt:message
            key="button.formAndDownloadReport"/></a>
</div>

</body>
</html>
