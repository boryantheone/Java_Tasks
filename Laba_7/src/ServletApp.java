import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * 2 вариант = 000001
 * 5) При перезагрузке страницы сервлета должно отображаться: 0 – изменение триггера,         !!!
 *  хранящегося в объекте сервлета;
 * 6) Организовать вывод результатов работы сервлета: 0 - строки следуют одна за другой         D
 *  сверху вниз (таблица без видимых границ, состоящая из одного столбца и множества строк);
 * 7) Реализовать при обновлении страницы сервлета: 0 - уменьшение размера текста в             D
 *  таблице до заданной величины, после чего на странице должна появляться надпись
 *  (не в таблице), информирующая о том, что дальнейшее уменьшение не возможно;
 * 8) Реализовать возможность сброса размера текста в таблице через параметр строки             D
 *      url-запроса: 0 - до значения по умолчанию.
 * 9) Среди параметров, передаваемых в сервлет, нужно передавать Ф.И.О студента, и номер его    D
 * группы, которые должны отображаться следующим образом: 0 - в заголовке web-страницы возвращаемой !!!
 *  сервлетом клиенту;
 * 11) При необходимости могут быть изменены порты, по которым контейнер сервлетов              D
 *  Tomcat слушает запросы. Для изменения портов нужно в среде NetBeans войти в
 *  меню «Сервис\Серверы»: 1 — изменить на произвольный.
 */
//


//@WebServlet(name = "ServletApp", value = "/ServletApp")
public class ServletApp extends HttpServlet {

	int triggerServlet;
	int	counter;
	int     minimalFontSize = 5;
	int     fontSize;
	int     summEvenOrNegativeNumbers = 0;
	int     summOddOrNegativeNumbers = 0;
	PrintWriter     out;
	private boolean arrayWithInvalidNumbers = false;

	public ServletApp(){
		counter = 0;
		fontSize = 0;
	}

	/** Главный метод в котором прописана вся логика
	 * Совершать такой запрос чтобы вывести страницу с решением задания
	 * http://localhost:8080/new-servlet?defaultSize=false&array=2_1_-4_34_5423_-23_65_43
	 * Если defaultSize=true, то размер текста снова станет по умолчанию
	 * @param request servlet request
	 * @param response servlet response
	 * @throws IOException if an I/O error occurs
	 */
	public void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		solveTaskFrom1Lab(request.getParameter("array"));
		if (checkDefaultSize(request.getParameter("defaultSize"))) {
			setFontSizeToDefault();
		}
		response.setContentType("text/html;charset=UTF-8");
		out = response.getWriter();
		setHeaderAndOpenHtmlPage();
		checkFontSize();
		setMainTableInHtmlPage();
		closeHtmlPage();
		switchTrigger();
		counter++;
	}

	private void switchTrigger() {
		if ((counter % 2) == 0)
			triggerServlet = 0;
		else
			triggerServlet = 1;
	}

	private void solveTaskFrom1Lab(String arrayFromParameter) {
		String[] arrayWithNumbers = arrayFromParameter.split("_");
		ArrayList<Integer> listInt = new ArrayList<Integer>();
		arrayWithInvalidNumbers = false;
		summOddOrNegativeNumbers = 0;
		summEvenOrNegativeNumbers = 0;
		try {
			for ( String elem: arrayWithNumbers) {
				listInt.add(Integer.parseInt(elem));
			}
			for (Integer number : listInt) {
				if (isNumberEvenOrNegative(number)) {
					summEvenOrNegativeNumbers += number;
				}
				if (isNumberOddOrNegative(number)) {
					summOddOrNegativeNumbers += number;
				}
			}
		}
		catch (Exception ignored){
			summOddOrNegativeNumbers = 0;
			summEvenOrNegativeNumbers = 0;
			arrayWithInvalidNumbers = true;
		}
	}

	private boolean isNumberEvenOrNegative(int number) {
		return number % 2 == 0 && number < 0;
	}

	private boolean isNumberOddOrNegative(int number) { return number % 2 == -1 && number < 0;
	}


	private void closeHtmlPage() {
		out.println("</body>");
		out.println("</html>");
	}

	private void setMainTableInHtmlPage() {
		out.println("<h" + fontSize + ">" +
				"   <table border=0>" +
				"       <tr><td>Триггер в объекте сервлета: " + triggerServlet + "</td>" +
				"       <tr><td>Размер текста: " + fontSize + "</td>" +
				"       <tr><td> Результат вычислений: </td>" +
				"   </table>" +
				"</h" + fontSize + ">");
		if (arrayWithInvalidNumbers) {
			out.println("<h" + fontSize + ">" +
					"   <table border=0>" +
					"<tr><td>Неправильные аргументы!</td>" +
					"   </table>" +
					"</h" + fontSize + ">");
		}
		else {
			out.println("<h" + fontSize + ">" +
					"   <table border=0>" +
					"       <tr><td> Сумма чётных и отрицательных чисел массива: " + summEvenOrNegativeNumbers + "</td>" +
					"       <tr><td> Сумма нечётных и отрицательных чисел массива: " + summOddOrNegativeNumbers + "</td>" +
					"   </table>" +
					"</h" + fontSize + ">");
		}
	}

	private void checkFontSize() {
		if (fontSize < minimalFontSize){
			fontSize++;
		}
		else {
			out.println("<h1> Минимальный размер текста достигнут! </h1>");
		}
	}

	private void setHeaderAndOpenHtmlPage() {
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Васильева Н.Ю. гр.4310</title>");
		out.println("</head>");
		out.println("<body>");
	}

	private void setFontSizeToDefault() {
		fontSize = 0;
	}

	private boolean checkDefaultSize(String isDefaultSizeString) {
		if (isDefaultSizeString != null && isDefaultSizeString.equals("true")){
			return true;
		}
		return false;
	}

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		processRequest(request, response);

	}

}
