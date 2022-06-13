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
    <title><fmt:message key="welcomeAdmin.title"/></title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/signin.css" rel="stylesheet">

    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
</head>
<body>

<a href="${pageContext.request.contextPath}/logout" class="btn btn-secondary btn-lg active" role="button" aria-pressed="true"><fmt:message key="button.logout"/></a>
<a href="${pageContext.request.contextPath}/jsp/changeUserInfo.jsp" class="btn btn-secondary btn-lg active" role="button" aria-pressed="true"><fmt:message key="title.changeUserInfo"/></a>
<a href="${pageContext.request.contextPath}/jsp/addUserAdmin.jsp" class="btn btn-warning" role="button" aria-pressed="true"><fmt:message key="addUser.button"/></a>
<a href="${pageContext.request.contextPath}/jsp/editOrDeleteUser.jsp" class="btn btn-warning" role="button" aria-pressed="true"><fmt:message key="button.editOrDeleteUser"/></a>
<a href="${pageContext.request.contextPath}/jsp/addActivityAdmin.jsp" class="btn btn-primary" role="button" aria-pressed="true"><fmt:message key="addActivity.button"/></a>
<a href="${pageContext.request.contextPath}/jsp/editOrDeleteActivity.jsp" class="btn btn-primary" role="button" aria-pressed="true"><fmt:message key="button.editOrDeleteActivity"/></a>
<a href="${pageContext.request.contextPath}/jsp/addCategoryAdmin.jsp" class="btn btn-info" role="button" aria-pressed="true"><fmt:message key="addCategoryOfActivity.button"/></a>
<a href="${pageContext.request.contextPath}/jsp/editOrDeleteCategory.jsp" class="btn btn-info" role="button" aria-pressed="true"><fmt:message key="button.editOrDeleteCategory"/></a>
<a href="${pageContext.request.contextPath}/jsp/userRequestsAdmin.jsp" class="btn btn-danger" role="button" aria-pressed="true"><fmt:message key="button.userRequests"/></a>


<div class="container">
    <div class="btn-group pull-left">
        <p class="h1"><fmt:message key="placeholder.adminCabinet"/></p>
    </div>
</div>
<hr>
<div class="container">
    <div class="row">
        <div class="col-sm-2"><p><strong><fmt:message key="placeholder.username"/></strong></p></div>
        <div class="col-sm-2">${sessionScope.user.name}</div>
    </div>
    <div class="row">
        <div class="col-sm-2"><p><strong><fmt:message key="placeholder.email"/></strong></p></div>
        <div class="col-sm-2">${sessionScope.user.email}</div>
    </div>
    <div class="row">
        <div class="col-sm-2"><p><strong><fmt:message key="placeholder.status"/></strong></p></div>
        <c:if test="${sessionScope.user.admin}">
            <div class="col-sm-2"><fmt:message key="placeholder.adminstatus"/></div>
        </c:if>
        <c:if test="${!sessionScope.user.admin}">
            <div class="col-sm-2"><fmt:message key="placeholder.userstatus"/></div>
        </c:if>
    </div>

</div>
<hr>
<div class="container">
    <div class="col-md-10">
        <table class="table">
            <thead>
            <tr>
                <th><fmt:message key="placeholder.username"/></th>
                <th><fmt:message key="userActivity.activityCategoryName"/></th>
                <th><fmt:message key="userActivity.activityName"/></th>
                <th><fmt:message key="userActivity.durationOfActivity"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="userActivity" items="${sessionScope.userActivities}">
                <tr>
                    <td>${userActivity.userName}</td>
                    <td>${userActivity.activityCategoryName}</td>
                    <td>${userActivity.activityName}</td>
                    <td>${userActivity.activityDuration}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
