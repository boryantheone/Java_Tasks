<%--
  Created by IntelliJ IDEA.
  User: boryantheone
  Date: 05.05.2022
  Time: 09:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Главная страница</title>
</head>
<body>
<jsp:useBean id="mybean" scope="session" class="kai.lab.java.JavaBean" />
<%
    try {
        String array = request.getParameter("array");
        String[] arrayWithNumbers = array.split(" ");
        ArrayList<Integer> listInt = new ArrayList<Integer>();
        mybean.setSummOddOrNegativeNumbers(0);
        mybean.setSummEvenOrNegativeNumbers (0);
        mybean.setArrayWithInvalidNumbers(false);

        for ( String elem: arrayWithNumbers) {
            listInt.add(Integer.parseInt(elem));
        }
        for (Integer number : listInt) {
            if (isNumberEvenOrNegative(number)) {
                mybean.setSummEvenOrNegativeNumbers (mybean.getSummEvenOrNegativeNumbers() + number);
            }
            if (isNumberOddOrNegative(number)) {
                mybean.setSummOddOrNegativeNumbers(mybean.getSummOddOrNegativeNumbers() + number);
            }
        }
    }
    catch (Exception ignored){
        mybean.setSummOddOrNegativeNumbers(0);
        mybean.setSummEvenOrNegativeNumbers (0);
        mybean.setArrayWithInvalidNumbers(true);
    }
%>

<%!
    private boolean isNumberEvenOrNegative(int number) {
        return number % 2 == 0 && number < 0;
    }

    private boolean isNumberOddOrNegative(int number) {
        return number % 2 == -1 && number < 0;
    }
%>

    <h3>Введите числа через пробел</h3>
    <form name="Input form" action="main.jsp">
        <label>
            <input type="text" name="array" />
        </label>
        <input type="submit" value="Подтвердить" name="button2" />
    </form>
    <a href="finish.jsp"> Узнать результат на финишной странице</a>

    <h3>Счетчик на главной странице: ${mybean.countPageRefresh}</h3>
    <%
        mybean.setCountPageRefresh(mybean.getCountPageRefresh() + 1);
    %>
</body>
</html>
