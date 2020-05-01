<%--
  Created by IntelliJ IDEA.
  User: Serg
  Date: 20.04.2020
  Time: 20:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
    тут подключается JSTL core, которая включает основные теги создания циклов, условий и т.д.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>films</title>
 <!-- чтобы использовать наш CSS на странице нужно сделать на него ссылку внутри тега head:  -->
    <link href="<c:url value="/res/style.css"/>" rel="stylesheet" type="text/css"/>

</head>
<body>
<h2>Films</h2>
<table>
    <tr>
        <th>id</th>
        <th>title</th>
        <th>year</th>
        <th>genre</th>
        <th>watched</th>
        <th>action</th>
    </tr>
    <c:forEach var="film" items="${filmsList}" varStatus="i">
        <tr>

            <td>${film.id}</td>
            <td>${film.title}</td>
            <td>${film.year}</td>
            <td>${film.genre}</td>
            <td>${film.watched}</td>
            <td>
                <a href="/edit/${film.id}">edit</a>
                <a href="/delete/${film.id}">delete</a>
            </td>
        </tr>
    </c:forEach>

    <tr>
        <td colspan="7">
            <a href="<c:url value="/add"/>">Add new film</a>
            <c:forEach begin="${1}" end="${pagesCount}" step="1" varStatus="i">
                <c:url value="/" var="url">
                    <c:param name="page" value="${i.index}"/>
                </c:url>
                <a href="${url}">${i.index}</a>
            </c:forEach>
        </td>
    </tr>

</table>


<%--
<h2>Add</h2>

<c:url value="/add" var="add"/>
<a href="${add}">Add new film</a> --%>
        </body>
</html>
