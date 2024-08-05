document.getElementById("update-tv-button").addEventListener("click", updateTv);

function updateTv() {
    let serialNumber = document.getElementById("input-serial-number-update").value;
    let resolution = document.getElementById("input-resolution-update").value;
    let sizeDisplay = document.getElementById("input-size-display-update").value;
    let technologyDisplay = document.getElementById("input-technology-display-update").value;
    let systemOperational = document.getElementById("input-system-operational-update").value;

    let tvData = {
        serialNumber: serialNumber,
        resolution: resolution,
        sizeDisplay: sizeDisplay,
        technologyDisplay: technologyDisplay,
        systemOperational: systemOperational
    };

    let url = "http://localhost:8080/Tv/rest/ManagementTv/updateTv";

    fetch(url, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(tvData)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Ocurrió un error en el servidor: " + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            alert("Se actualizó el registro");
            window.location.href = "./dashboard.html";
        })
        .catch(error => {
            console.error("Ocurrió el siguiente error con la operación", error);
        });
}
