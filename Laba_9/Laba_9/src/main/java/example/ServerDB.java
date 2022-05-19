package example;

import java.sql.*;

public  class ServerDB {
	private static Connection connection;
	private static  PreparedStatement request;

	public  static void openDb() throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		connection = DriverManager.getConnection("jdbc:sqlite:test.db");
		System.out.println("Подключился к бд!");
	}

	public static void createTables() throws SQLException {
		Statement createTable = connection.createStatement();

		createTable.executeUpdate("create table if not exists Location" +
				"(id integer primary key autoincrement ," +
				"floor integer, locker varchar(32), shelf integer);");

		createTable.executeUpdate("create table if not exists Book " +
				"(id integer primary key autoincrement, " +
				"author varchar(56), publication_name varchar(32), " +
				"publisher varchar(32), year_public integer , pages integer, " +
				"year_write integer , weight integer , " +
				"location_id integer, foreign key(Location_id) references Location(id));");

		createTable.close();
	}

	public static void writeBookInTable(String fullName, String titlePublication, String publisher,
										int yearPublic, int yearWrite, int countPages, int weight,
										int locationId) {
		try {
			request = connection.prepareStatement("INSERT INTO Book" +
					"(author, publication_name, publisher, year_public, pages, year_write, weight, location_id)" +
					"VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
			request.setString(1, fullName);
			request.setString(2, titlePublication);
			request.setString(3, publisher);
			request.setInt(4, yearPublic);
			request.setInt(5, countPages);
			request.setInt(6, yearWrite);
			request.setInt(7, weight);
			request.setInt(8, locationId);
			request.executeUpdate();
			System.out.println("Книга автора " + fullName + " создана!");
			request.close();
		} catch (SQLException e) {
			System.out.println("Произошла ошибка при добавление книги...");
		}
	}

	public static void writeLocationInTable(int floor, int locker, int shelf) {
		try {
			request = connection.prepareStatement("INSERT INTO Location" +
					"(floor, locker, shelf) VALUES (?, ?, ?)");
			request.setInt(1, floor);
			request.setInt(2, locker);
			request.setInt(3, shelf);
			request.executeUpdate();
			System.out.println("Место на " + floor + " этаже создано");
			request.close();
		} catch (SQLException e) {
			System.out.println("Произошла ошибка при создании нового места...");
		}
	}

	public static void showAllBook() {
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM Book ORDER BY id");
			StringBuilder tableBook = new StringBuilder(
					" id|                 author       |      publication_name       | publisher |" +
							"year_public|pages|year_write|weight|loc_id\n");
			while (resultSet.next()) {
				tableBook.append(String.format(
						"%3d %30s %30s %10s %11d %5d %10d %6d %8d\n",
						resultSet.getInt("id"),
						resultSet.getString("author"),
						resultSet.getString("publication_name"),
						resultSet.getString("publisher"),
						resultSet.getInt("year_public"),
						resultSet.getInt("pages"),
						resultSet.getInt("year_write"),
						resultSet.getInt("weight"),
						resultSet.getInt("location_id")));
			}
			System.out.println(tableBook);
			statement.close();
		} catch (SQLException e) {
			System.out.println("Произошла ошибка при просмотре...");
		}
	}

	public static void showAllLocation() {
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM Location ORDER BY id");
			StringBuilder tableLocation = new StringBuilder("id | floor | locker | shelf\n");
			while (resultSet.next()) {
				tableLocation.append(String.format(
						"%d  |  %d   |   %d   |  %d\n",
						resultSet.getInt("id"),
						resultSet.getInt("floor"),
						resultSet.getInt("locker"),
						resultSet.getInt("shelf")));
			}
			System.out.println(tableLocation);
			statement.close();
		} catch (SQLException e) {
			System.out.println("Произошла ошибка при просмотре...");
		}

	}

	public static void deleteBook(int deleteId) {
		try {
			request = connection.prepareStatement("DELETE FROM Book WHERE id = ?");
			request.setInt(1, deleteId);
			if (request.executeUpdate() == 0)
				System.out.println("Ошибка при удалении книги " + deleteId + " (книги не существует)");
			else
				System.out.println("Книга " + deleteId + " удалена...");
		} catch (SQLException e) {
			System.out.println("Ошибка при удалении книги " + deleteId);
		}
	}

	public static void deleteLocation(int deleteId) {
		try {
			request = connection.prepareStatement("DELETE FROM Location WHERE id = ?");
			request.setInt(1, deleteId);
			if (request.executeUpdate() == 0)
				System.out.println("Ошибка при удалении места " + deleteId + " (места не существует)");
			else
				System.out.println("Место " + deleteId + " удалено...");
		} catch (SQLException e) {
			System.out.println("Ошибка при удалении места " + deleteId);
		}
	}

	public static boolean checkExistsBook(int id) {
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM Book WHERE id = " + id);
			resultSet.next();
			return resultSet.getInt("count") == 1;
		} catch (SQLException e) {
			return false;
		}
	}

	public static boolean checkExistsLocation(int id) {
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM Location WHERE id = " + id);
			resultSet.next();
			return resultSet.getInt("count") == 1;
		} catch (SQLException e) {
			return false;
		}
	}

	public static void updateBook(int changeId, String fullName, String titlePublication, String publisher,
								  int yearPublic, int yearWrite, int countPages, int weight,
								  int locationId) {
		try {
			request = connection.prepareStatement("UPDATE Book SET author = ?," +
					" publication_name = ?, publisher = ?, year_public = ?, pages = ?, year_write = ?," +
					" weight = ?, location_id = ? WHERE id = ?");
			request.setString(1, fullName);
			request.setString(2, titlePublication);
			request.setString(3, publisher);
			request.setInt(4, yearPublic);
			request.setInt(5, countPages);
			request.setInt(6, yearWrite);
			request.setInt(7, weight);
			request.setInt(8, locationId);
			request.setInt(9, changeId);
			request.executeUpdate();
			System.out.println("Книга автора " + fullName + " обновлена!");
			request.close();
		} catch (SQLException e) {
			System.out.println("Произошла ошибка при обновлении книги!");
		}
	}

	public static void updateLocation(int changeId, int floor, int locker, int shelf) {
		try {
			request = connection.prepareStatement("UPDATE Location SET floor = ?, " +
					"locker = ?, shelf = ? WHERE id = ?");
			request.setInt(1, floor);
			request.setInt(2, locker);
			request.setInt(3, shelf);
			request.setInt(4, changeId);
			request.executeUpdate();
			System.out.println("Место на " + floor + " этаже обновлено.");
			request.close();
		} catch (SQLException e) {
			System.out.println("Произошла ошибка при обновлении места!");
		}

	}

	public static void showAuthorsInLocker(int floor, int locker) {
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT author FROM Book " +
					"WHERE location_id in(SELECT id FROM Location WHERE floor = "
					+ floor + " AND locker = " + locker + ") ORDER BY author");
			StringBuilder authors = new StringBuilder();
			while(resultSet.next()) {
				authors.append(String.format(
						"%s\n",
						resultSet.getString("author")));
			}
			if (authors.length() == 0) {
				System.out.println("Ничего не найдено!");
			} else {
				System.out.println(authors);
			}
			statement.close();
		} catch (SQLException e) {
			System.out.println("Произошла ошибка при чтении!");
		}
	}

	public static void showWeightInLocker(int floor, int locker) {
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT sum(weight) as sum FROM Book WHERE location_id" +
					" in(SELECT id FROM Location WHERE floor = " + floor + " AND " +
					"locker = " + locker + ")");
			resultSet.next();
			System.out.println(resultSet.getInt("sum"));
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void showFirstFieldInAllBooks() {
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery( "select author from Book");
			StringBuilder table = new StringBuilder();
			while (resultSet.next()) {
				table.append(resultSet.getString("author"))
						.append("\n");
			}
			System.out.println(table);

		} catch (SQLException e) {
			System.out.println("Произошла ошибка при просмотре!");
		}
	}

	public static void showSecondFieldInAllBooksByLexicOrder() {
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(
					"select publication_name from Book order by Book.publication_name");
			StringBuilder table = new StringBuilder();
			while (resultSet.next()) {
				table.append(resultSet.getString("publication_name"))
						.append("\n");
			}
			System.out.println(table);
		} catch (SQLException e) {
			System.out.println("Произошла ошибка при просмотре!");
		}
	}

	public static void deleteFieldsFromRow(int id) {

		try {
			request = connection.prepareStatement("UPDATE Book SET author = ? , publication_name  = ?, publisher = ?, year_public = ?, pages = ?, year_write = ?, weight = ?, location_id = ? WHERE id = ?");
			request.setString(1, "");
			request.setString(2, "");
			request.setString(3,  "");
			request.setInt(4, 0);
			request.setInt(5, 0);
			request.setInt(6, 0);
			request.setInt(7, 0);
			request.setInt(8, 0);
			request.setInt(9, id);
			request.executeUpdate();
			request.close();
		} catch (SQLException e) {
			System.out.println("Произошла ошибка при сбросе!");
		}
	}


	public static void closeDB() throws SQLException {
		connection.close();
	}

}
