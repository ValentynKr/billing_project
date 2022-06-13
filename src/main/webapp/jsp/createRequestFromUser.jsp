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
    <title><fmt:message key="button.createRequest"/></title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/signin.css" rel="stylesheet">

    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
</head>
<body>

<a href="${pageContext.request.contextPath}/logout" class="btn btn-secondary btn-lg active" role="button"
   aria-pressed="true"><fmt:message key="button.logout"/></a>
<a href="${pageContext.request.contextPath}/jsp/welcome.jsp" class="btn btn-secondary btn-lg active" role="button"
   aria-pressed="true"><fmt:message
        key="button.back"/></a>

<div class="container">
    <div class="btn-group pull-left">
        <p class="h1"><fmt:message key="title.requestPage"/></p>
    </div>
</div>
<hr>
<div class="container">
    <div class="col-md-7">
        <form class="form-signin" action="${pageContext.request.contextPath}/userRequestHandlingServlet" method="post">

            <label for="typeOfRequest"><fmt:message key="label.requestType"/></label>
            <select class="form-control" name="typeOfRequest" id="typeOfRequest">
                <option value="CREATE">CREATE</option>
                <option value="INVOLVE">INVOLVE</option>
                <option value="DELETE">DELETE</option>
                <option value="EDIT">EDIT</option>
            </select>
            <button class="btn btn-lg btn-default btn-block" type="submit"><fmt:message
                    key="button.submit"/></button>
        </form>
    </div>
</div>
</body>
</html>
