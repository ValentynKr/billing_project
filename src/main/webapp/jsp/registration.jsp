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
<script type="text/javascript">
    let Msg = '<%=session.getAttribute("Alert")%>';
    if (Msg == "1") {
            alert("User with such email is already registered");
    }
    else if (Msg == "2") {
        alert("Registration was accomplished. Thank you!")
    }
    else if (Msg == "5") {
        alert("Chosen email is invalid. It should`n contain special symbols or cyrillic letters. Example - ggg@gmail.com")
    }
</script>
<hr>
<a href="<c:url value="/login.jsp"/>">Back to Login page</a>
</body>
</html>
