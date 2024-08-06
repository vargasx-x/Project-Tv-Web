document.addEventListener('DOMContentLoaded', function () {
    document.getElementById("add-tv-form").addEventListener("submit", function(event) {
        event.preventDefault(); // Previene que el formulario se envíe de la forma tradicional
        addTv();
    });

    function addTv() {
        let serialNumber = document.getElementById('serialNumber').value;
        let resolution = document.getElementById('resolution').value;
        let sizeDisplay = document.getElementById('sizeDisplay').value;
        let technologyDisplay = document.getElementById('technologyDisplay').value;
        let systemOperational = document.getElementById('systemOperational').value;

        let tvData = {
            serialNumber,
            resolution,
            sizeDisplay,
            technologyDisplay,
            systemOperational
        };

        fetch('http://localhost:8080/Tv/rest/ManagementTv/createTv', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(tvData)
        })
        .then(response => {
            if (response.status === 400) {
                return response.text().then(text => {
                    throw new Error(text);
                });
            }
            if (!response.ok) {
                throw new Error("Ocurrió un error en el servidor: " + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            alert("Tv agregado correctamente");
            window.location.href = "./dashboard.html";
        })
        .catch(error => {
            alert(error.message);
            console.error("Ocurrió el siguiente error con la operación", error);
        });
    }
});
