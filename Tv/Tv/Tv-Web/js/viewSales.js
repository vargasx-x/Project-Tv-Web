document.addEventListener('DOMContentLoaded', function() {
    fetchSales();
});

function fetchSales() {
    const url = 'http://localhost:8080/rest/ManagementSale/getSales'; // Cambia la URL si es necesario

    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error('Error en la respuesta del servidor');
            }
            return response.json();
        })
        .then(sales => {
            displaySales(sales);
        })
        .catch(error => {
            console.error('Error al obtener las ventas:', error);
        });
}

function displaySales(sales) {
    const salesList = document.getElementById('sales-list');
    salesList.innerHTML = ''; // Limpiar el contenedor

    sales.forEach(sale => {
        const saleCard = document.createElement('div');
        saleCard.className = 'card-sale';

        const saleId = document.createElement('h3');
        saleId.textContent = `Código de Venta: ${sale.idSale}`;

        const tvCode = document.createElement('p');
        tvCode.textContent = `Código del Televisor: ${sale.televisor ? sale.televisor.serialNumber : 'No disponible'}`;

        const saleDate = document.createElement('p');
        saleDate.textContent = `Fecha de Venta: ${sale.saleDate}`;

        const salePrice = document.createElement('p');
        salePrice.textContent = `Precio de Venta: ${sale.salePrice}`;

        const deleteButton = document.createElement('button');
        deleteButton.className = 'btn btn-danger';
        deleteButton.textContent = 'Eliminar';
        deleteButton.onclick = function() {
            deleteSale(sale.idSale);
        };

        saleCard.appendChild(saleId);
        saleCard.appendChild(tvCode);
        saleCard.appendChild(saleDate);
        saleCard.appendChild(salePrice);
        saleCard.appendChild(deleteButton);
        salesList.appendChild(saleCard);
    });
}

function deleteSale(idSale) {
    const url = `http://localhost:8080/rest/ManagementSale/deleteSale?idSale=${encodeURIComponent(idSale)}`;

    fetch(url, {
        method: 'DELETE'
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Error en la respuesta del servidor');
        }
        return response.json();
    })
    .then(data => {
        alert('Venta eliminada correctamente');
        fetchSales(); // Actualiza la lista de ventas
    })
    .catch(error => {
        console.error('Error al eliminar la venta:', error);
    });
}
