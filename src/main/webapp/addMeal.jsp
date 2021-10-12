<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Add Meal</title>
</head>
<body>
<h2>${param.action == 'add' ? 'Add meal' : 'Edit meal'}</h2>
<form method="post" action="meals">
    <label>
        <input type="hidden" name="id" size="45" value="${meal.id}"/>
    </label>
    <table>
        <tr>
            <th>Date time</th>
            <td>
                <label>
                    <input type="datetime-local" name="dateTime" size="45" value="${meal.dateTime}" required/>
                </label>
            </td>
        </tr>
        <tr>
            <th>Description</th>
            <td>
                <label>
                    <input type="text" name="description" size="45" value="${meal.description}" required/>
                </label>
            </td>
        </tr>
        <tr>
            <th>Calories</th>
            <td>
                <label>
                    <input type="number" name="calories" size="45" value="${meal.calories}" required>
                </label>
            </td>
        </tr>
        <tr>
            <td>
                <button type="submit">${param.action == 'add' ? 'Add' : 'Edit'}</button>
            </td>
            <td>
                <button type="button" onclick="window.location='http://localhost:8080/topjava/meals'">Cancel</button>
            </td>
        </tr>
    </table>
</form>
<br><br>
<a href="index.html">Home</a>
</body>
</html>