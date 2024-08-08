document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('delete-sale-button').addEventListener('click', () => {
        const saleCode = document.getElementById('input-sale-code-delete').value.trim();
        if (saleCode) {
            deleteSale(saleCode);
        } else {
            alert('Por favor, ingrese el código de la venta.');
        }
    });
});

function deleteSale(saleCode) {
    const url = `http://localhost:8080/Tv/rest/ManagementSale/deleteSale?idSale=${encodeURIComponent(saleCode)}`;

    fetch(url, {
        method: 'DELETE'
    })
    .then(response => {
        if (response.ok) {
            alert('Venta eliminada correctamente.');
            document.getElementById('input-sale-code-delete').value = ''; // Limpiar el campo
        } else {
            return response.text().then(text => { throw new Error(text); });
        }
    })
    .catch(error => {
        console.error('Error al eliminar la venta:', error);
        alert('No se pudo eliminar la venta: ' + error.message);
    });
}
