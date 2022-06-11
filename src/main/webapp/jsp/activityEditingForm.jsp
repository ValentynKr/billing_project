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
    <title><fmt:message key="label.changeInfo"/></title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/signin.css" rel="stylesheet">

    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
</head>
<body>

<a href="${pageContext.request.contextPath}/jsp/editOrDeleteActivity.jsp" class="btn btn-secondary btn-lg active"
   role="button" aria-pressed="true"><fmt:message key="button.back"/></a>


<div class="container">
    <div class="btn-group pull-left">
        <p class="h1"><fmt:message key="title.changeUserInfo"/></p>
    </div>
</div>
<hr>

<div class="container-fluid">
    <form class="form-signin" action="<c:url value="/editActivityAdminForm"/>" method="post">
        <c:set var="localActivity" value="${sessionScope.activityToEdit}"/>

        <h2 class="form-signin-heading fade in text-center"><fmt:message key="title.changeUserInfo"/></h2>
        <label for="activityCategoryOfEditedActivity"><fmt:message key="placeholder.activityCategoryName"/></label>
        <select class="form-control" name="activityCategoryOfEditedActivity" id="activityCategoryOfEditedActivity">
            <c:forEach var="listOfAllActivityCategories" items="${sessionScope.listOfAllActivityCategories}">
                <option>${listOfAllActivityCategories.categoryName}</option>
            </c:forEach>
        </select>
        <label for="activityCategoryOfEditedActivity"><fmt:message key="placeholder.activityName"/></label>
        <input type="text" class="form-control" name="name" value="${localActivity.name}"/>
        <button class="btn btn-lg btn-default btn-block" type="submit"><fmt:message key="button.submit"/></button>
    </form>
    <c:if test="${not empty sessionScope.Alert}">
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <div class="alert alert-danger"><p class="text-center"><strong>${sessionScope.Alert}</strong></p></div>
                <c:remove var="Alert"/>
            </div>
        </div>
    </c:if>
</div>
</body>
</html>
