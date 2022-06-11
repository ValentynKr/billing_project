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

    <title><fmt:message key="addCategoryOfActivity.button"/></title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/signin.css" rel="stylesheet">

    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>

</head>
<body>
<a href="${pageContext.request.contextPath}/jsp/welcome-admin.jsp" class="btn btn-secondary btn-lg active" role="button"
   aria-pressed="true"><fmt:message key="button.back"/></a>
<hr>
<div class="container">
    <div class="btn-group pull-left">
        <p class="h1"><fmt:message key="addCategoryOfActivity.button"/></p>
    </div>
</div>
<hr>

<div class="container-fluid">
    <form class="form-signin" action="<c:url value="/addCategoryAdmin"/>" method="post">
        <label for="ActivityCategoryStatus"><fmt:message key="userActivity.activityName"/></label>
        <select class="form-control" name="ActivityCategoryStatus" id="ActivityCategoryStatus">
            <option>OPENED</option>
            <option>CLOSED</option>
        </select>
        <label>
            <input type="text" class="form-control" name="newActivityCategoryNameEn"
                   placeholder="<fmt:message key="label.newCategoryName"/> en" required/>
        </label>
        <label>
            <input type="text" class="form-control" name="newActivityCategoryNameRu"
                   placeholder="<fmt:message key="label.newCategoryName"/> ru" required/>
        </label>
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
