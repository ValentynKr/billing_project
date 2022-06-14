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
    <title><fmt:message key="button.editActivity"/></title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/signin.css" rel="stylesheet">

    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
</head>
<body>

<a href="${pageContext.request.contextPath}/jsp/welcome.jsp" class="btn btn-secondary btn-lg active" role="button"
   aria-pressed="true"><fmt:message
        key="button.back"/></a>

<div class="container">
    <div class="btn-group pull-left">
        <p class="h1"><fmt:message key="button.editActivity"/></p>
    </div>
</div>
<hr>
<div class="container">
    <div class="col-md-10">
        <table class="table">
            <thead>
            <tr>
                <th><fmt:message key="userActivity.activityName"/></th>
                <th><fmt:message key="userActivity.durationOfActivity"/></th>
                <th><fmt:message key="button.edit"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="userActivity" items="${sessionScope.userActivities}">
                <tr>
                    <td>${userActivity.activityName}</td>
                    <td>${userActivity.activityDuration}</td>
                    <td>
                        <form class="form-group-sm" action="${pageContext.request.contextPath}/userRequestEditServlet"
                              method="post">

                                <input type="text" class="form-group-sm" name="userActivityNewName"
                                       placeholder="<fmt:message key="label.editActivityName"/>">


                                <input type="text" class="form-group-sm" name="userActivityNewDuration"
                                       placeholder="<fmt:message key="label.editActivityDuration"/>">


                                <input type="text" class="form-group-sm" name="commentForAdmin"
                                       placeholder="<fmt:message key="label.comment"/>" required>

                            <input type="hidden" name="userActivityName" value="${userActivity.activityName}">
                            <button class="btn btn-xs btn-default" type="submit"><fmt:message
                                    key="button.submit"/></button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<c:if test="${not empty sessionScope.Alert}">
    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <div class="alert alert-danger"><p class="text-center"><strong>${sessionScope.Alert}</strong></p></div>
            <c:remove var="Alert"/>
        </div>
    </div>
</c:if>
</body>
</html>
