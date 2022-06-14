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
    <title><fmt:message key="label.userRequestAcceptationForm"/></title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/signin.css" rel="stylesheet">

    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
</head>
<body>

<a href="${pageContext.request.contextPath}/jsp/userRequestsAdmin.jsp" class="btn btn-secondary btn-lg active"
   role="button" aria-pressed="true"><fmt:message key="button.back"/></a>


<div class="container">
    <div class="btn-group pull-left">
        <p class="h1"><fmt:message key="label.AcceptOrDecline"/></p>
    </div>
</div>
<hr>
<div>
    <div class="container">
        <div class="row">
            <div class="col-sm-3"><p><strong><fmt:message key="userRequest.status"/></strong></p></div>
            <div class="col-sm-3">${sessionScope.userRequest.requestStatus}</div>
        </div>
        <div class="row">
            <div class="col-sm-3"><p><strong><fmt:message key="label.requestType"/></strong></p></div>
            <div class="col-sm-3">${sessionScope.userRequest.requestType}</div>
        </div>
        <div class="row">
            <div class="col-sm-3"><p><strong><fmt:message key="userRequest.creationDate"/></strong></p></div>
            <div class="col-sm-3">${sessionScope.userRequest.timestamp}</div>
        </div>
        <div class="row">
            <div class="col-sm-3"><p><strong><fmt:message key="placeholder.username"/></strong></p></div>
            <div class="col-sm-3">${sessionScope.userNameRequest.name}</div>
        </div>
        <div class="row">
            <div class="col-sm-3"><p><strong><fmt:message key="placeholder.comment"/></strong></p></div>
            <div class="col-sm-3">${sessionScope.userRequest.comment}</div>
        </div>
        <c:if test="${sessionScope.userRequestIsAcceptable}">
            <div class="row">
                <div class="col-sm-3">
                    <form class="form-group" action="${pageContext.request.contextPath}/userRequestAcceptServlet"
                          method="post">
                        <button class="btn btn-lg btn-success" type="submit"><fmt:message
                                key="button.accept"/></button>
                    </form>
                </div>
                <div class="col-sm-3">
                    <form class="form-group" action="${pageContext.request.contextPath}/userRequestDeclineServlet"
                          method="post">
                        <button class="btn btn-lg btn-danger" type="submit"><fmt:message
                                key="button.decline"/></button>
                    </form>
                </div>
            </div>
        </c:if>
    </div>
</div>
<hr>
<div>
    <div class="container">
        <div class="col-md-10">
            <table class="table">
                <thead>
                <tr>
                    <th><fmt:message key="userActivity.activityCategoryName"/></th>
                    <c:choose>
                        <c:when test="${(sessionScope.userRequest.requestType.toString()).equals('INVOLVE')}">
                            <th><fmt:message key="userActivity.activityName"/></th>
                        </c:when>
                        <c:when test="${(sessionScope.userRequest.requestType.toString()).equals('DELETE')}">
                            <th><fmt:message key="userActivity.activityName"/></th>
                            <th><fmt:message key="userActivity.durationOfActivity"/></th>
                        </c:when>
                        <c:when test="${(sessionScope.userRequest.requestType.toString()).equals('CREATE')}">
                            <th><fmt:message key="label.newActivityName"/></th>
                        </c:when>
                    </c:choose>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <c:choose>
                        <c:when test="${(sessionScope.userRequest.requestType.toString()).equals('INVOLVE')}">
                            <td>${sessionScope.activityCategoryDescription.categoryName}</td>
                            <td>${sessionScope.activityToInvolve.name}</td>
                        </c:when>
                        <c:when test="${(sessionScope.userRequest.requestType.toString()).equals('DELETE')}">
                            <td>${sessionScope.oldUserActivity.activityCategoryName}</td>
                            <td>${sessionScope.oldUserActivity.activityName}</td>
                            <td>${sessionScope.oldUserActivity.activityDuration}</td>
                        </c:when>
                        <c:when test="${(sessionScope.userRequest.requestType.toString()).equals('CREATE')}">
                            <td>${sessionScope.activityCategoryDescription.categoryName}</td>
                            <td>${sessionScope.userRequest.newActivityName}</td>
                        </c:when>
                    </c:choose>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
