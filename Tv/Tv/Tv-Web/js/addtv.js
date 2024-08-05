document.getElementById("add-tv-button").addEventListener("click", addTv);

function addTv() {
    let serialNumber = document.getElementById("input-serial-number").value;
    let resolution = document.getElementById("input-resolution").value;
    let sizeDisplay = document.getElementById("input-size-display").value;
    let technologyDisplay = document.getElementById("input-technology-display").value;
    let systemOperational = document.getElementById("input-system-operational").value;

    let tvData = {
        serialNumber: serialNumber,
        resolution: resolution,
        sizeDisplay: sizeDisplay,
        technologyDisplay: technologyDisplay,
        systemOperational: systemOperational
    };

    let url = "http://localhost:8080/Tv/rest/ManagementTv/createTv";

    fetch(url, {
        method: 'POST',
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
            alert("Se agregó el registro");
            window.location.href = "./dashboard.html";
        })
        .catch(error => {
            console.error("Ocurrió el siguiente error con la operación", error);
        });
}
