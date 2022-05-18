package example;

import example.ServerDB;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.SQLException;
import java.util.*;

public class Main {
	public static void main(String[] args) throws  SQLException, ClassNotFoundException, FileNotFoundException {
		Scanner scanner = new Scanner(System.in);

		ServerDB.openDb();

		ServerDB.createTables();

		int command = 0;

		while(true) {
			System.out.println("_________________________________________________________");
			System.out.println("1 - Добавление книги;");
			System.out.println("2 - Добавление места для книг;");
			System.out.println("3 - Посмотреть все книги;");
			System.out.println("4 - Посмотреть все места для книг;");
			System.out.println("5 - Удалить книгу по id;");
			System.out.println("6 - Удалить место по id;");
			System.out.println("7 - Изменить книгу по id;");
			System.out.println("8 - Изменить место по id;");
			System.out.println("9 - Вывод авторов в указанном шкафу в лексикографическом порядке;");
			System.out.println("10 - Вывести суммарный вес изданий в указанном шкафу");
			System.out.println("11 - Показать первое поле у всех книг");
			System.out.println("12 - Показать второе поле у всех книг в лексикографическом порядке");
			System.out.println("13 - Сброс неключевых значений");
			System.out.println("14 - Выйти.");

			System.out.println("Выберите команду для выполнения:");
			try {
				command = scanner.nextInt();
				if (command == 1) {
					scanner.nextLine();
					System.out.println("Введите ФИО автора: ");
					String fullName = scanner.nextLine();
					System.out.println("Введите название издания: ");
					String publicationName = scanner.nextLine();
					System.out.println("Введите издательство: ");
					String publisher = scanner.nextLine();
					System.out.println("Укажите год издания и год написания: ");
					int yearPublic = scanner.nextInt();
					int yearWrite = scanner.nextInt();
					System.out.println("Укажите количество страниц и вес в граммах: ");
					int countPages = scanner.nextInt();
					int weight = scanner.nextInt();
					System.out.println("Укажите id места, где будет находиться книга: ");
					int locationId = scanner.nextInt();
					ServerDB.writeBookInTable(fullName, publicationName, publisher, yearPublic,
							yearWrite, countPages, weight, locationId);
				} if (command == 2) {
					scanner.nextLine();
					System.out.println("Укажите этаж/шкаф/полку рассположения: ");
					int floor = scanner.nextInt();
					int locker = scanner.nextInt();
					int shelf = scanner.nextInt();
					ServerDB.writeLocationInTable(floor, locker, shelf);
				} if (command == 3) {
					ServerDB.showAllBook();
				} if (command == 4) {
					ServerDB.showAllLocation();
				} if (command == 5) {
					scanner.nextLine();
					System.out.println("Укажите id для удаления книги: ");
					int deleteId = scanner.nextInt();
					ServerDB.deleteBook(deleteId);
				} if (command == 6) {
					scanner.nextLine();
					System.out.println("Укажите id для удаления места: ");
					int deleteId = scanner.nextInt();
					ServerDB.deleteLocation(deleteId);
				} if (command == 7) {
					System.out.println("Укажите id для изменения книги: ");
					int changeId = scanner.nextInt();
					if (ServerDB.checkExistsBook(changeId)) {
						System.out.println("Книга с таким id не существует!");
						break ;
					}
					scanner.nextLine();
					System.out.println("Введите нового автора книги : ");
					String author = scanner.nextLine();
					System.out.println("Введите новое издание");
					String publicationName = scanner.nextLine();
					System.out.println("Введите новое издательство: ");
					String publisher = scanner.nextLine();
					System.out.println("Укажите новый год издания и год написания: ");
					int yearPublic = scanner.nextInt();
					int yearWrite = scanner.nextInt();
					System.out.println("Укажите новое количество страниц и вес в граммах: ");
					int countPages = scanner.nextInt();
					int weight = scanner.nextInt();
					System.out.println("Укажите новое id места, где будет находиться книга: ");
					int locationId = scanner.nextInt();
					ServerDB.updateBook(changeId, author, publicationName, publisher, yearPublic,
							yearWrite, countPages, weight, locationId);
				} if (command == 8) {
					System.out.println("Укажите id для изменения места: ");
					int changeId = scanner.nextInt();
					if (ServerDB.checkExistsLocation(changeId)) {
						System.out.println("Место с таким id не существует!");
						break ;
					}
					scanner.nextLine();
					System.out.println("Укажите новый этаж/шкаф/полку рассположения: ");
					int floor = scanner.nextInt();
					int locker = scanner.nextInt();
					int shelf = scanner.nextInt();
					ServerDB.updateLocation(changeId, floor, locker, shelf);
				} if (command == 9) {
					System.out.println("Введите этаж и шкаф соответственно и через пробел: ");
					int floor = scanner.nextInt();
					int locker = scanner.nextInt();
					ServerDB.showAuthorsInLocker(floor, locker);
				} if (command == 10) {
					System.out.println("Введите этаж и шкаф соответственно и через пробел: ");
					int floor = scanner.nextInt();
					int locker = scanner.nextInt();
					ServerDB.showWeightInLocker(floor, locker);
				} if (command == 11) {
					ServerDB.showFirstFieldInAllBooks();
				}
				if (command == 12) {
					ServerDB.showSecondFieldInAllBooksByLexicOrder();
				}
				if (command == 13) {
					System.out.println("13 ryjgrf");
					FileReader file = new FileReader("/Users/boryantheone/IdeaProjects/Java_Labs/Laba_9/Laba_9/src/main/java/example/file.txt");
					Scanner scan = new Scanner(file);
					String ids = scan.nextLine();
					String[] array_id = ids.split(" ");
					for (int i = 0; i < array_id.length - 1; i++) {
						if (ServerDB.checkExistsLocation(Integer.parseInt(array_id[i]))) {
							System.out.println("Место с таким id не существует!");
							continue ;
						} else {
							ServerDB.deleteFieldsFromRow(Integer.parseInt(array_id[i]));
						}
					}
//					System.out.println(scan.nextLine());

				}else if (command == 14) {
					break;
				}
//				else {
//					System.out.println("Такой команды не существует.");
//				}
			} catch (InputMismatchException ex) {
				System.out.println("Попробуй ввести снова...");
				scanner.next();
			}
		}
		ServerDB.closeDB();
	}
}
