document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("add-tv-button").addEventListener("click", addTV);
});

function addTV() {
    let codeTV = document.getElementById("input-code-tv").value;
    let nameTV = document.getElementById("input-name-tv").value;
    let brandTV = document.getElementById("input-brand-tv").value;
    let sizeTV = document.getElementById("input-size-tv").value;
    let yearManufactureTV = document.getElementById("input-year-manufacture-tv").value;
    let priceTV = document.getElementById("input-price-tv").value;

    let tvData = {
        code: codeTV,
        name: nameTV,
        brand: brandTV,
        size: sizeTV,
        yearManufacture: yearManufactureTV,
        price: priceTV
    };

    console.log("Datos del TV a enviar:", tvData);

    let url = "http://localhost:8080/Tv/rest/ManagementTV/createTV";

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
            alert("Se agregó el TV con éxito.");
            window.location.href = "./dashboard.html";
        })
        .catch(error => {
            console.error("Ocurrió el siguiente error con la operación:", error);
            alert("Error al agregar el TV. Por favor, intente nuevamente.");
        });
}
