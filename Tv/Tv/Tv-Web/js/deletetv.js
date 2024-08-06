document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('delete-tv-button').addEventListener('click', () => {
        const serialNumber = document.getElementById('input-serial-number-delete').value.trim();
        if (serialNumber) {
            deleteTv(serialNumber);
        } else {
            alert('Por favor, ingrese el número de serie.');
        }
    });
});

function deleteTv(serialNumber) {
    const url = `http://localhost:8080/Tv/rest/ManagementTv/deleteTv?serialNumber=${encodeURIComponent(serialNumber)}`;

    fetch(url, {
        method: 'DELETE'
    })
    .then(response => {
        if (response.ok) {
            alert('Televisor eliminado correctamente.');
        } else {
            return response.text().then(text => { throw new Error(text); });
        }
    })
    .catch(error => {
        console.error('Error al eliminar el televisor:', error);
        alert('No se pudo eliminar el televisor: ' + error.message);
    });
}
