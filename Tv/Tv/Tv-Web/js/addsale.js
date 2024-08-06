document.addEventListener('DOMContentLoaded', function () {
    document.getElementById("add-sale-form").addEventListener("submit", function(event) {
        event.preventDefault(); // Previene que el formulario se envíe de la forma tradicional
        addSale();
    });

    function addSale() {
        let idSale = document.getElementById('input-sale-id').value;
        let televisor = document.getElementById('input-tv-code').value;
        let saleDate = document.getElementById('input-sale-date').value;
        let salePrice = document.getElementById('input-sale-price').value;

        let saleData = {
            idSale,
            televisor,
            saleDate,
            salePrice
        };

        fetch('http://localhost:8080/Tv/rest/ManagementTv/createTve', { // Cambia la URL al endpoint correcto
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(saleData)
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
            alert("Venta agregada correctamente");
            window.location.href = "./dashboard.html"; // Redirige a la página del dashboard o a donde necesites
        })
        .catch(error => {
            alert(error.message);
            console.error("Ocurrió el siguiente error con la operación", error);
        });
    }
});
