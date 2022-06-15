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
    <title><fmt:message key="button.watchAllActivities"/></title>
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
        <p class="h1"><fmt:message key="button.watchAllActivities"/></p>
    </div>
</div>
<hr>

<div class="container">
    <div class="btn-group pull-left">
        <p class="h6"><fmt:message key="label.filterByCategory"/></p>
        <c:forEach var="allCategoriesFilter" items="${sessionScope.listOfAllActivityCategories}">
            <a href="${pageContext.request.contextPath}/watchAllActivitiesServlet?filter=${allCategoriesFilter.categoryId}"
               class="btn btn-xs btn-default"
               role="button">${allCategoriesFilter.categoryName}</a>
        </c:forEach>
    </div>
</div>
<hr>
<div class="container">
    <div class="col-md-10">
        <table class="table">
            <thead>
            <tr>
                <th><a href="${pageContext.request.contextPath}/watchAllActivitiesServlet?sort=1"><fmt:message
                        key="userActivity.activityCategoryName"/></a></th>
                <th><a href="${pageContext.request.contextPath}/watchAllActivitiesServlet?sort=2"><fmt:message
                        key="userActivity.activityName"/></a></th>
                <th><a href="${pageContext.request.contextPath}/watchAllActivitiesServlet?sort=3"><fmt:message
                        key="userActivity.numberOfUsers"/></a></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="allActivityCount" items="${sessionScope.allActivityCount}">
                <tr>
                    <td>${allActivityCount.activityCategoryName}</td>
                    <td>${allActivityCount.activityName}</td>
                    <td>${allActivityCount.numberOfUserActivities}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
