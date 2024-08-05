document.addEventListener("DOMContentLoaded", function() {
    fetch("http://localhost:8080/Tv/rest/ManagementTv/getTvs")
        .then(response => {
            if (!response.ok) {
                throw new Error("Error en la respuesta del servidor: " + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            displayTvs(data);
        })
        .catch(error => {
            console.error("Ocurrió un error con la operación:", error);
        });
});

function displayTvs(tvs) {
    const tvList = document.getElementById("tv-list");
    if (tvs.length === 0) {
        tvList.innerHTML = "<p>No hay televisores disponibles.</p>";
        return;
    }

    tvs.forEach(tv => {
        const tvElement = document.createElement("div");
        tvElement.classList.add("tv-item");
        tvElement.innerHTML = `
            <h3>${tv.serialNumber}</h3>
            <p>Resolución: ${tv.resolution}</p>
            <p>Tamaño: ${tv.sizeDisplay}</p>
            <p>Tecnología: ${tv.technologyDisplay}</p>
            <p>Sistema Operativo: ${tv.systemOperational}</p>
        `;
        tvList.appendChild(tvElement);
    });
}
