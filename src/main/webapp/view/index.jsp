<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="icon" type="image/png" href="../img/favicon.png">
    <link rel="stylesheet" href="../styles/index.css">
    <title>LibWay</title>
</head>
<body>
<div class="edit-book-modal hidden-element">
    <div class="container">
        <div class="edit-book-modal__inner">
            <p class="edit-book-modal__close-button" onclick="closeEditModal()">+</p>
            <p class="site-block-title">— Измените книгу на сайте —</p>
            <div class="add-book__inputs">
                <input id="edit-name-input" class="add-book__input" type="text" placeholder="Название книги">
                <input id="edit-author-input" class="add-book__input" type="text" placeholder="Автор книги">
                <input id="edit-cover-input" class="add-book__input" type="text" placeholder="Ссылка на обложку книги">
                <input id="edit-year-input" class="add-book__input" type="text" placeholder="Год выхода книги">
                <input id="edit-ISBN-input" class="add-book__input" type="text" placeholder="ISBN книги">
            </div>
            <button class="add-book__button" onclick="editBookOnSite()">Изменить</button>
        </div>
    </div>
</div>
<div class="header">
    <div class="container">
        <div class="header__inner">
            <div class="site-logo">
                <img class="site-logo__img" src="https://static.thenounproject.com/png/751623-200.png">
                <p class="site-logo__name">Lib<span class="site-text-span">Way</span></p>
            </div>
        </div>
    </div>
</div>
<div class="add-book">
    <div class="container">
        <div class="add-book__inner">
            <p class="site-block-title">— Добавьте свою книгу —</p>
            <div class="add-book__inputs">
                <input id="name-input" class="add-book__input" type="text" placeholder="Название книги">
                <input id="author-input" class="add-book__input" type="text" placeholder="Автор книги">
                <input id="cover-input" class="add-book__input" type="text" placeholder="Ссылка на обложку книги">
                <input id="year-input" class="add-book__input" type="text" placeholder="Год выхода книги">
                <input id="ISBN-input" class="add-book__input" type="text" placeholder="ISBN книги">
            </div>
            <button class="add-book__button" onclick="addNewBook()">Добавить</button>
        </div>
    </div>
</div>
<div class="shelf">
    <div class="container">
        <div class="shelf__inner"></div>
    </div>
</div>
<script src="../js/librarianController.js"></script>
</body>
</html>