document.addEventListener("DOMContentLoaded", function () {
    const menuLinks = document.querySelectorAll(".nav-link");
    menuLinks.forEach(link => {
        link.addEventListener("click", function () {
            menuLinks.forEach(item => item.classList.remove("active"));
            this.classList.add("active");
        });
    });
});

document.getElementById("button-books").addEventListener("click", function (event) {
    alert("Botón Libro");
    cleanContent();
    loadBooks();
});

document.getElementById("button-borrow").addEventListener("click", function (event) {
    alert("Botón Préstamo");
});

function loadBooks() {
    const content = document.getElementById("content");
    cleanContent(); // Limpiar el contenido antes de agregar nuevas tarjetas

    const cardAdd = document.createElement("div");
    cardAdd.classList.add("card");

    const cardBodyAdd = document.createElement("div");
    cardBodyAdd.classList.add("card-body");
    const btnAdd = document.createElement('a');
    btnAdd.className = "btn btn-primary";
    btnAdd.href = './addbook.html';

    const imgAdd = document.createElement('img');
    imgAdd.src = 'resources/icons/agregar-libro.png';

    const lblAdd = document.createElement('h3');
    lblAdd.textContent = 'Puedes agregar nuevos libros';
    btnAdd.appendChild(imgAdd);
    cardBodyAdd.appendChild(btnAdd);
    cardBodyAdd.appendChild(lblAdd);
    cardAdd.appendChild(cardBodyAdd);
    content.appendChild(cardAdd);

    fetch("http://localhost:8080/Library/rest/ManagementLibrary/getBooks")
        .then(response => response.json())
        .then(data => {
            data.forEach(book => {
                const card = document.createElement("div");
                card.classList.add("card");

                const cardBody = document.createElement("div");
                cardBody.classList.add("card-body");

                const title = document.createElement("h2");
                title.className = "card-title";
                title.textContent = book.name;

                const author = document.createElement("p");
                author.className = "card-text";
                author.textContent = `Autor: ${book.author}`;

                const genre = document.createElement("p");
                genre.className = "card-text";
                genre.textContent = `Género: ${book.genre}`;

                const publisher = document.createElement("p");
                publisher.className = "card-text";
                publisher.textContent = `Editorial: ${book.publisher}`;

                const pageQuantity = document.createElement("p");
                pageQuantity.className = "card-text";
                pageQuantity.textContent = `Páginas: ${book.pageQuantity}`;

                const yearPublisher = document.createElement("p");
                yearPublisher.className = "card-text";
                yearPublisher.textContent = `Año de publicación: ${book.yearPublish}`;

                const btnEliminar = document.createElement('button');
                btnEliminar.className = 'btn-danger';
                btnEliminar.id = `btn-delete-${book.code}`;
                btnEliminar.textContent = 'Eliminar';
                btnEliminar.setAttribute('data-code', book.code);
                btnEliminar.addEventListener('click', function () {
                    const bookCode = this.getAttribute('data-code');
                    deleteBookById(bookCode);
                });

                const btnActualizar = document.createElement('a');
                btnActualizar.className = 'btn-success margin-button';
                btnActualizar.id = `btn-update-${book.code}`;
                btnActualizar.textContent = 'Actualizar';
                btnActualizar.setAttribute('data-code', book.code);
                btnActualizar.addEventListener('click', function () {
                    localStorage.setItem("bookData", JSON.stringify(book));
                    window.location.href = "./updatepage.html";
                });

                cardBody.appendChild(title);
                cardBody.appendChild(author);
                cardBody.appendChild(genre);
                cardBody.appendChild(publisher);
                cardBody.appendChild(pageQuantity);
                cardBody.appendChild(yearPublisher);
                cardBody.appendChild(btnEliminar);
                cardBody.appendChild(btnActualizar);

                card.appendChild(cardBody);
                content.appendChild(card);
            });
        })
        .catch(error => console.error("Error:", error));
}

function cleanContent() {
    const content = document.getElementById('content');
    content.innerHTML = "";
}

function deleteBookById(code) {
    let url = `http://localhost:8080/Library/rest/ManagementLibrary/deleteBook?codeBook=${code}`;
    fetch(url, {
        method: 'DELETE'
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Ocurrió un error en la respuesta del servidor: ' + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            alert("Se eliminó el registro");
            cleanContent();
            loadBooks();
        })
        .catch(error => {
            console.error('Ocurrió el siguiente error con la operación: ', error);
        });
};

loadBooks();
