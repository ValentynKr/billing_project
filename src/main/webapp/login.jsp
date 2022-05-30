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
    <title>Login page</title>
</head>

<body>
<form action="<c:url value="/login"/>" method="get">
    <br/>
    <header>Hi, please, enter your login and password below</header>
    <br/>
    Email (login):
    <br/>
    <input type="text" name="login">
    <br/>
    <br/>
    Password: <br/>
    <input type="text" name="password" />
    <br/>
    <br/>
    <input type="submit" value="Submit" />
</form>
<br/>
<a1 type="text">If you are not registered, click to register <a href="${pageContext.request.contextPath}/jsp/registration.jsp">here</a></a1>

<script type="text/javascript">
    let Msg = '<%=session.getAttribute("Alert")%>';
    if (Msg == "3") {
        alert("Wrong password!");
    }
    else if (Msg == "4") {
        alert("User with such email is not registered. Please, register!")
    }


</script>

</body>
</html>
