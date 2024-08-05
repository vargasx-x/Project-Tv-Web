document.getElementById("delete-tv-button").addEventListener("click", deleteTv);

function deleteTv() {
    let serialNumber = document.getElementById("input-serial-number-delete").value;

    let url = `http://localhost:8080/Tv/rest/ManagementTv/deleteTv/${serialNumber}`;

    fetch(url, {
        method: 'DELETE',
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Ocurrió un error en el servidor: " + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            alert("Se eliminó el registro");
            window.location.href = "./dashboard.html";
        })
        .catch(error => {
            console.error("Ocurrió el siguiente error con la operación", error);
        });
}
