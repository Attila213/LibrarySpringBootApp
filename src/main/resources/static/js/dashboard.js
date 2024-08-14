$(document).ready(function() {
    let books = [];

    var fullUrl = window.location.href;

    var pathname = window.location.pathname;

    if(pathname === "/dashboard"){
        pageAJAX = "loan/loanProcess";
        pageAPI = '/api/books'
        loanText = 'Loan'
    }

    if(pathname === "/profile"){
        pageAJAX = "loan/deleteLoan";
        pageAPI = '/api/loanedbooks'
        loanText = 'Return'
    }

    function fetchBooks() {

        fetch(pageAPI)
            .then(response => response.json())
            .then(data => {
                books = data;
                displayBooks(books);
            });
    }

    fetchBooks();

    $('#authorSearch, #titleSearch, #categorySearch').on('input', function() {
        filterBooks();
    });

    function filterBooks() {
        const authorFilter = $('#authorSearch').val().toLowerCase();
        const titleFilter = $('#titleSearch').val().toLowerCase();
        const categoryFilter = $('#categorySearch').val().toLowerCase();

        const filteredBooks = books.filter(book => {
            return (
                (book.author.firstName.toLowerCase().includes(authorFilter) ||
                    book.author.lastName.toLowerCase().includes(authorFilter)) &&
                book.title.toLowerCase().includes(titleFilter) &&
                book.category.categoryName.toLowerCase().includes(categoryFilter)
            );
        });

        displayBooks(filteredBooks);
    }

    function displayBooks(books) {
        const tableBody = $('#bookTable');
        tableBody.empty();

        books.forEach(book => {
            const row = `
                <tr>
                    <td>${book.title}</td>
                    <td>${book.author.firstName} ${book.author.lastName}</td>
                    <td>${book.category.categoryName}</td>
                    <td>${book.publicationYear}</td>
                    <td><button class="loan-button" value="${book.bookID}">`+loanText+`</button></td>
                </tr>
            `;
            tableBody.append(row);
        });
    }

    $('#bookTable').on('click', '.loan-button', function() {
        const bookID = $(this).val();

        $.ajax({
            url: pageAJAX,
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                bookid: bookID
            }),
            success: function(message) {
                alert(message);

                fetchBooks();
            },
            error: function(xhr, status, error) {
                console.error('Error:', error);
                alert('An error occurred while processing the loan.');
            }
        });
    });
});
