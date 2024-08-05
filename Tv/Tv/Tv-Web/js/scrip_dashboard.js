document.addEventListener("DOMContentLoaded", function () {
    const menuLinks = document.querySelectorAll(".nav-link");
    menuLinks.forEach(link => {
        link.addEventListener("click", function () {
            menuLinks.forEach(item => item.classList.remove("active"));
            this.classList.add("active");
            const section = this.id.split('-')[1]; // Obtiene la sección (tvs, sales, users)
            loadSection(section);
        });
    });

    function loadSection(section) {
        const content = document.getElementById("content");
        cleanContent(); // Limpiar el contenido antes de agregar nuevas tarjetas

        let addCard, viewCard;
        switch (section) {
            case 'tvs':
                addCard = createCard('./addtv.html', 'Agregar Televisor', 'resources/icons/agregar-tv.png');
                viewCard = createCard('./viewtvs.html', 'Ver Televisores', 'resources/icons/ver-tv.png');
                break;
            case 'sales':
                addCard = createCard('./addsale.html', 'Agregar Venta', 'resources/icons/agregar-venta.png');
                viewCard = createCard('./viewsales.html', 'Ver Ventas', 'resources/icons/ver-venta.png');
                break;
            case 'users':
                addCard = createCard('./adduser.html', 'Agregar Usuario', 'resources/icons/agregar-usuario.png');
                viewCard = createCard('./viewusers.html', 'Ver Usuarios', 'resources/icons/ver-usuario.png');
                break;
            default:
                return;
        }

        content.appendChild(addCard);
        content.appendChild(viewCard);
    }

    function createCard(href, text, imgSrc) {
        const card = document.createElement("div");
        card.classList.add("card");

        const cardBody = document.createElement("div");
        cardBody.classList.add("card-body");

        const btn = document.createElement('a');
        btn.className = "btn btn-primary";
        btn.href = href;

        const img = document.createElement('img');
        img.src = imgSrc;

        const lbl = document.createElement('h3');
        lbl.textContent = text;

        btn.appendChild(img);
        cardBody.appendChild(btn);
        cardBody.appendChild(lbl);
        card.appendChild(cardBody);

        return card;
    }

    function cleanContent() {
        const content = document.getElementById('content');
        content.innerHTML = "";
    }

    // Cargar la sección inicial
    loadSection('tvs');
});
