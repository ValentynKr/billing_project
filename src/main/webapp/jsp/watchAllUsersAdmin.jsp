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
    <title><fmt:message key="label.chooseUserToWatchUserActivities"/></title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/signin.css" rel="stylesheet">

    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
</head>
<body>

<a href="${pageContext.request.contextPath}/jsp/welcome-admin.jsp" class="btn btn-secondary btn-lg active"
   role="button" aria-pressed="true"><fmt:message key="button.back"/></a>


<div class="container">
    <div class="btn-group pull-left">
        <p class="h1"><fmt:message key="label.chooseUserToWatchUserActivities"/></p>
    </div>
</div>
<hr>

<div class="container">
    <div class="col-md-10">
        <table class="table">
            <thead>
            <tr>
                <th><fmt:message key="placeholder.userId"/></th>
                <th><fmt:message key="placeholder.username"/></th>
                <th><fmt:message key="placeholder.email"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="user" items="${sessionScope.listOfAllUsers}">
                <tr>
                    <td>${user.userId}</td>
                    <td>${user.name}</td>
                    <td>${user.email}</td>
                    <td>
                        <form class="form-group" action="${pageContext.request.contextPath}/userActivityInfoAdmin"
                              method="post">
                            <input type="hidden" name="userIdForUserActivities" value="${user.userId}">
                            <button class="btn btn-xs btn-default" type="submit"><fmt:message
                                    key="button.watchRequest"/></button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
