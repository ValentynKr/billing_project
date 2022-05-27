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
<header>
    <table>
        <c:forEach items="${sessionScope.User}" var="user">
            <tr>
                <td><c:out value="${user.userId}"/></td>
                <td><c:out value="${user.name}"/></td>
                <td><c:out value="${user.admin}"/></td>
                <td><c:out value="${user.email}"/></td>
                <td><c:out value="${user.password}"/></td>
            </tr>
        </c:forEach>
    </table>
</header>
</body>
</html>
