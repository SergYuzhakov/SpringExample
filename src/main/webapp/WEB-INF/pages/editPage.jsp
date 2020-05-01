<%--
  Created by IntelliJ IDEA.
  User: Serg
  Date: 20.04.2020
  Time: 20:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <c:if test="${empty film.title}">
        <title>Add</title>
    </c:if>
    <c:if test="${!empty film.title}">
        <title>Edit</title>
    </c:if>
</head>
<%--
Т.е. мы просто проверяем поле film.title.
Если оно пустое, значит это новый фильм,
мы должны заполнить для него все данные и добавить в список.
Если это поле не пустое, значит это фильм из списка и его нужно просто изменить.
--%>
<body>
<c:if test="${empty film.title}">
    <c:url value="/add" var="var"/>
</c:if>
<c:if test="${!empty film.title}">
    <c:url value="/edit" var="var"/>
</c:if>

<form action="${var}" method="POST">

    <c:if test="${!empty film.title}">
        <input type="hidden" name="id" value="${film.id}">
    </c:if>

    <label for="title">Title</label>
    <input type="text" name="title" id="title">
    <label for="year">Year</label>
    <input type="text" name="year" id="year">
    <label for="genre">Genre</label>
    <input type="text" name="genre" id="genre">
    <label for="watched">Watched</label>
    <input type="text" name="watched" id="watched">

    <c:if test="${empty film.title}">
        <input type="submit" value="Add new film">
    </c:if>
    <c:if test="${!empty film.title}">
        <input type="submit" value="Edit film">
    </c:if>

</form>
</body>
</html>
