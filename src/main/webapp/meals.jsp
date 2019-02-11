<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<div align="center">
    <h3><a href="index.html">Home</a></h3>
    <h2>Meals</h2>
    <table border="2">
        <c:forEach var="mealTo" items="${listMeals}">
            <jsp:useBean id="mealTo" type="ru.javawebinar.topjava.model.MealTo"/>
            <tr>
                <td width="100" align="center">
                        ${mealTo.description}
                </td>
                <td width="150" align="center">
                        ${mealTo.dateTime}
                </td>
                <td width="40" align="center">
                        ${mealTo.calories}
                </td>
                <td bgcolor="${(mealTo.excess) ? "red" : "green"}" width="50">
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>