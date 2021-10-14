<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<head>
    <title>User list</title>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr/>
    <h2>Users</h2>
    <a href="users?action=create">Add User</a>
    <br><br>
    <h2>Current authenticated user: id ${auth}</h2>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Role</th>
            <th colspan="2">Action</th>
        </tr>
        </thead>
        <c:forEach items="${users}" var="user">
            <jsp:useBean id="user" type="ru.javawebinar.topjava.model.User"/>
            <tr>
                <td>${user.id}</td>
                <td><a href="users?action=select&id=${user.id}">${user.name}</a></td>
                <td>${user.email}</td>
                <td></td>
                <td><a href="users?action=delete&id=${user.id}">Delete</a> | <a href="users?action=update&id=${user.id}">Update</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>