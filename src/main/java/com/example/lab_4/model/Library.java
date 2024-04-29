package com.example.lab_4.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletContext;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Library {
    private static final String FILE_PATH = "books.json";
    private ServletContext servletContext;

    public Library(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public List<Book> getBooks() {
        String filePath = servletContext.getRealPath(FILE_PATH);
        System.out.println("Reading books from: " + filePath);
        List<Book> books = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }

            JSONObject booksJson = new JSONObject(content.toString());
            JSONArray booksArray = booksJson.getJSONArray("books");

            for (int i = 0; i < booksArray.length(); i++) {
                JSONObject bookJson = booksArray.getJSONObject(i);
                String title = bookJson.getString("title");
                String author = bookJson.getString("author");
                String coverLink = bookJson.getString("coverLink");
                String year = bookJson.getString("year");
                String ISBN = bookJson.getString("ISBN");

                books.add(new Book(title, author, coverLink, year, ISBN));
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return books;
    }

    public void addBook(Book book) {
        String filePath = servletContext.getRealPath(FILE_PATH);
        System.out.println("Writing book to: " + filePath);
        try (FileReader reader = new FileReader(filePath);
             BufferedReader jsonReader = new BufferedReader(reader)) {

            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = jsonReader.readLine()) != null) {
                jsonContent.append(line);
            }

            JSONObject booksJson = new JSONObject(jsonContent.toString());
            JSONArray booksArray = booksJson.getJSONArray("books");

            JSONObject newBook = new JSONObject();
            newBook.put("title", book.getTitle());
            newBook.put("author", book.getAuthor());
            newBook.put("coverLink", book.getCoverLink());
            newBook.put("year", book.getYear());
            newBook.put("ISBN", book.getISBN());
            booksArray.put(newBook);

            try (FileWriter fileWriter = new FileWriter(filePath)) {
                fileWriter.write(booksJson.toString());
                fileWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
