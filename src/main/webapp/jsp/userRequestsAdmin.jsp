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
    <title><fmt:message key="button.userRequests"/></title>
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
        <p class="h1"><fmt:message key="button.userRequests"/></p>
    </div>
</div>
<hr>

<div class="container">
    <div class="col-md-11">
        <table class="table">
            <thead>
            <tr>
                <th><fmt:message key="userRequest.creationDate"/></th>
                <th><fmt:message key="userRequest.status"/></th>
                <th><fmt:message key="userRequest.type"/></th>
                <th><fmt:message key="placeholder.username"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="listOfUserRequests" items="${requestScope.requestListToDemo}">
                <tr>
                    <td>${listOfUserRequests.timestamp}</td>
                    <td>${listOfUserRequests.requestStatus}</td>
                    <td>${listOfUserRequests.requestType}</td>
                    <td>${listOfUserRequests.userName}</td>
                    <td>
                        <form class="form-group" action="${pageContext.request.contextPath}/userRequestInfoServlet"
                              method="post">
                            <input type="hidden" name="userRequestId" value="${listOfUserRequests.requestId}">
                            <button class="btn btn-xs btn-default" type="submit"><fmt:message
                                    key="button.watchRequest"/></button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div>
            <nav aria-label="Page navigation example">
                <ul class="pagination justify-content-center">
                    <c:choose>
                        <c:when test="${requestScope.currentPage != 1}">
                            <li class="page-item"><a class="page-link"
                                                     href="${pageContext.request.contextPath}/watchAllUserRequestsServlet?page=${requestScope.currentPage - 1}">Previous</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item disabled">
                                <a class="page-link" href="#" tabindex="-1">Previous</a>
                            </li>
                        </c:otherwise>
                    </c:choose>

                    <c:forEach begin="1" end="${requestScope.noOfPages}" var="i">
                        <c:choose>
                            <c:when test="${requestScope.currentPage eq i}">
                                <li class="page-item disabled">
                                    <a class="page-link" href="#">${i}</a></li>
                            </c:when>
                            <c:otherwise>
                                <li class="page-item"><a class="page-link"
                                                         href="${pageContext.request.contextPath}/watchAllUserRequestsServlet?page=${i}">${i}</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                    <c:choose>
                        <c:when test="${requestScope.currentPage lt requestScope.noOfPages}">
                            <li class="page-item">
                                <a class="page-link"
                                   href="${pageContext.request.contextPath}/watchAllUserRequestsServlet?page=${requestScope.currentPage + 1}">Next</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item disabled">
                                <a class="page-link" href="#" tabindex="-1">Next</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </nav>
        </div>
    </div>
</div>
</body>
</html>
