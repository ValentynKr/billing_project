<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="resources"/>
<!DOCTYPE html>
<html>
<head>
    <title>Registration form</title>
</head>
<body>
<form action="<c:url value="/registration"/>" method="post">
    <br/>
    Name: <label>
    <br/>
    <input type="text" name="name">
</label>
    <br/>
    Email: <label>
    <br/>
    <input type="text" name="email"/>
</label>
    <br/>
    Pass: <label>
    <br/>
    <input type="text" name="password"/>
</label>
    <br/>
    <input type="submit" value="Submit"/>
</form>
</body>
</html>
