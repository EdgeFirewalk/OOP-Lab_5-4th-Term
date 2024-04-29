package com.example.lab_4.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Library {
    private static final String FILE_PATH = "books.json";

    public List<Book> getBooks() {
        List<Book> books = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
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
        try (FileReader reader = new FileReader(FILE_PATH);
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

            try (FileWriter fileWriter = new FileWriter(FILE_PATH)) {
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
