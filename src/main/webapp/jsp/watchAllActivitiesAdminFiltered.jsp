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
    <title><fmt:message key="button.watchAllActivitiesFiltered"/></title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/signin.css" rel="stylesheet">

    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
</head>
<body>

<a href="${pageContext.request.contextPath}/jsp/watchAllActivitiesAdmin.jsp" class="btn btn-secondary btn-lg active"
   role="button" aria-pressed="true"><fmt:message key="button.back"/></a>

<div class="container">
    <div class="btn-group pull-left">
        <p class="h1"><fmt:message key="button.watchAllActivitiesFiltered"/> "${sessionScope.chosenCategoryToFilter}"</p>
    </div>
</div>
<hr>
<c:choose>
    <c:when test="${!sessionScope.filteredActivitiesAreExistingFlag}">
        <div class="container">
            <div class="btn-group pull-left">
                <p class="text-danger"><fmt:message key="label.nothingToExpose"/></p>
            </div>
        </div>
    </c:when>
    <c:otherwise>
        <div class="container">
            <div class="col-md-10">
                <table class="table">
                    <thead>
                    <tr>
                        <th><fmt:message key="userActivity.activityName"/></th>
                        <th><fmt:message key="userActivity.numberOfUsers"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="allActivityCountFiltered" items="${sessionScope.allActivityCountFiltered}">
                        <tr>
                            <td>${allActivityCountFiltered.activityName}</td>
                            <td>${allActivityCountFiltered.numberOfUserActivities}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </c:otherwise>
</c:choose>
</body>
</html>
