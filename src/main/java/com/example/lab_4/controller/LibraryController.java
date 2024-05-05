package com.example.lab_4.controller;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.example.lab_4.model.Book;
import org.json.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "LibraryController", value = "/LibraryController")
public class LibraryController extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/oop_labs_books";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new ServletException("MySQL JDBC Driver not found", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");

        List<Book> books = getBooksFromDB();
        JSONArray jsonArray = new JSONArray();

        for (Book book : books) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("title", book.getTitle());
            jsonObject.put("author", book.getAuthor());
            jsonObject.put("coverLink", book.getCoverLink());
            jsonObject.put("year", book.getYear());
            jsonObject.put("ISBN", book.getISBN());
            jsonArray.put(jsonObject);
        }

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("books", jsonArray);

        response.getWriter().write(jsonResponse.toString());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (BufferedReader requestReader = request.getReader()) {
            StringBuilder requestJsonContent = new StringBuilder();
            String requestLine;
            while ((requestLine = requestReader.readLine()) != null) {
                requestJsonContent.append(requestLine);
            }

            JSONObject requestJson = new JSONObject(requestJsonContent.toString());
            String bookName = requestJson.getString("title");
            String authorName = requestJson.getString("author");
            String coverLink = requestJson.getString("coverLink");
            String year = requestJson.getString("year");
            String ISBN = requestJson.getString("ISBN");

            Book newBook = new Book(bookName, authorName, coverLink, year, ISBN);
            addBookToDB(newBook);

            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    private List<Book> getBooksFromDB() {
        List<Book> books = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM books");

            while (resultSet.next()) {
                String ISBN = resultSet.getString("ISBN");
                String title = resultSet.getString("book_name");
                String author = resultSet.getString("author_name");
                String coverLink = resultSet.getString("cover_link");
                String year = resultSet.getString("year");

                books.add(new Book(title, author, coverLink, year, ISBN));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    private void addBookToDB(Book book) {
        String insertQuery = "INSERT INTO books (ISBN, book_name, author_name, cover_link, year) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, book.getISBN());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setString(3, book.getAuthor());
            preparedStatement.setString(4, book.getCoverLink());
            preparedStatement.setString(5, book.getYear());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
