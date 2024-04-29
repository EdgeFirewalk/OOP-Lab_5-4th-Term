package com.example.lab_4.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import com.example.lab_4.model.Book;
import com.example.lab_4.model.Library;
import org.json.*;
import java.util.List;

@WebServlet(name = "LibraryController", value = "/LibraryController")
public class LibraryController extends HttpServlet {
    private Library library;

    public LibraryController() {
        library = new Library();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");

        List<Book> books = library.getBooks();
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
            library.addBook(newBook);

            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }
}
