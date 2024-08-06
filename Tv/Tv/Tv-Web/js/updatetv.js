document.addEventListener('DOMContentLoaded', function() {
    const tvData = JSON.parse(localStorage.getItem("tvData"));

    if (tvData) {
        document.getElementById('serialNumber').value = tvData.serialNumber;
        document.getElementById('resolution').value = tvData.resolution;
        document.getElementById('sizeDisplay').value = tvData.sizeDisplay;
        document.getElementById('technologyDisplay').value = tvData.technologyDisplay;
        document.getElementById('systemOperational').value = tvData.systemOperational;
    }

    document.getElementById("update-tv-form").addEventListener("submit", function(event) {
        event.preventDefault();
        updateTv();
    });
});

function updateTv() {
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

    fetch('http://localhost:8080/Tv/rest/ManagementTv/updateTv', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(tvData)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Error en la respuesta del servidor');
        }
        return response.json();
    })
    .then(data => {
        alert("Tv actualizado correctamente");
        window.location.href = "./dashboard.html";
    })
    .catch(error => {
        alert('Error al actualizar el tv:', error.message);
        console.error('Error al actualizar el tv:', error);
    });
}
