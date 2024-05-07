getBooksFromLibrary();

function getBooksFromLibrary() {
    const shelf = document.querySelector('.shelf__inner');

    fetch('/LibraryController')
        .then(res => res.json())
        .then(libObj => {
            shelf.innerHTML = '';
            let html = '';
            libObj.books.forEach(book => {
                html += `<div class="book">
                            <div class="book__buttons">
                                <div 
                                    class="book__button edit-book-button" 
                                    onclick="openEditModal(
                                        '${book.coverLink}',
                                        '${book.title}',
                                        '${book.author}',
                                        '${book.year}',
                                        '${book.ISBN}'
                                    )"
                                ">
                                    <img class="app-icon" src="../img/edit-icon.png" alt="Edit book">
                                </div>
                                <div
                                    class="book__button delete-book-button"
                                    onclick="deleteBookFromLibrary('${book.ISBN}')"
                                >
                                    <img class="app-icon" src="../img/delete-icon.png" alt="Delete book">
                                </div>
                            </div>
                            <img class="book__cover" src="${book.coverLink}" alt="${book.title}">
                            <p class="book__name">${book.title}</p>
                            <p class="book__author">${book.author}</p>
                            <p class="book__year">${book.year}</p>
                         </div>`;
            });
            shelf.innerHTML = html;
            console.log(`Книги (${libObj.books.length}) успешно получены с сервера!`);
        });
}

function addNewBook() {
    const bookName = document.querySelector('#name-input').value;
    const authorName = document.querySelector('#author-input').value;
    const coverLink = document.querySelector('#cover-input').value;
    const year = document.querySelector('#year-input').value;
    const ISBN = document.querySelector('#ISBN-input').value;

    fetch('/LibraryController', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify({
            "title": bookName,
            "author": authorName,
            "coverLink": coverLink,
            "year": year,
            "ISBN": ISBN
        })
    }).then(() => {
        getBooksFromLibrary();

        document.querySelector('#name-input').value = '';
        document.querySelector('#author-input').value = '';
        document.querySelector('#cover-input').value = '';
        document.querySelector('#year-input').value = '';
        document.querySelector('#ISBN-input').value = '';
    })
}

function openEditModal(coverLink, bookTitle, bookAuthor, bookYear, bookISBN) {
    const editBookModalWindow = document.querySelector('.edit-book-modal');

    document.querySelector('#edit-name-input').value = bookTitle;
    document.querySelector('#edit-author-input').value = bookAuthor;
    document.querySelector('#edit-cover-input').value = coverLink;
    document.querySelector('#edit-year-input').value = bookYear;
    document.querySelector('#edit-ISBN-input').value = bookISBN;

    oldBookTitle = bookTitle;
    oldBookAuthor = bookAuthor;

    document.body.style.overflow = 'hidden';

    editBookModalWindow.classList.remove('hidden-element');
}

function closeEditModal() {
    const editBookModalWindow = document.querySelector('.edit-book-modal');

    document.body.style.overflow = 'visible';

    editBookModalWindow.classList.add('hidden-element');
}

let oldBookTitle;
let oldBookAuthor;
function editBookOnSite() {
    const updatedBookName = document.querySelector('#edit-name-input').value;
    const updatedAuthorName = document.querySelector('#edit-author-input').value;
    const updatedCoverLink = document.querySelector('#edit-cover-input').value;
    const updatedYear = document.querySelector('#edit-year-input').value;
    const updatedISBN = document.querySelector('#edit-ISBN-input').value;

    fetch('/LibraryController', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify({
            "oldTitle": oldBookTitle,
            "oldAuthor": oldBookAuthor,
            "title": updatedBookName,
            "author": updatedAuthorName,
            "coverLink": updatedCoverLink,
            "year": updatedYear,
            "ISBN": updatedISBN
        })
    }).then(() => {
        getBooksFromLibrary();
        closeEditModal();
    });
}

function deleteBookFromLibrary(ISBN) {
    fetch('/LibraryController', {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify({
            "ISBN": ISBN
        })
    }).then(() => {
        getBooksFromLibrary();
    });
}
