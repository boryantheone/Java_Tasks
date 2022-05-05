<%--
  Created by IntelliJ IDEA.
  User: boryantheone
  Date: 05.05.2022
  Time: 09:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
        <jsp:useBean id="mybean" scope="session" class="JavaBean" />
        <%
            String summEvenOrNegativeNumbers;
            String summOddOrNegativeNumbers;

            if (mybean.isArrayWithInvalidNumbers()) {
                summEvenOrNegativeNumbers = "Неверные агрументы!";
                summOddOrNegativeNumbers = "Неверные агрументы!";
            }
            else {
                summEvenOrNegativeNumbers = String.valueOf(mybean.getSummEvenOrNegativeNumbers());
                summOddOrNegativeNumbers = String.valueOf(mybean.getSummOddOrNegativeNumbers());
            }
        %>


    <h1>
        <table >
            <tr><td>Сумма нечетных и отрицательных чисел: <%= summOddOrNegativeNumbers %></td>
            <tr><td>Сумма четных и отрицательных чисел: <%= summEvenOrNegativeNumbers %></td>
        </table>
    </h1>

</body>
</html>
