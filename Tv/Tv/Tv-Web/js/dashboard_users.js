document.addEventListener("DOMContentLoaded", function() {
    fetch('/Tv/rest/ManagementUser/getUsers')
        .then(response => response.json())
        .then(users => {
            const container = document.getElementById('user-cards-container');
            container.innerHTML = ''; // Limpiar contenido existente

            users.forEach(user => {
                const userCard = document.createElement('div');
                userCard.className = 'user-card';

                userCard.innerHTML = `
                    <h3>${user.nameUser}</h3>
                    <p>Contraseña: ${user.password}</p>
                    <button onclick="deleteUser('${user.nameUser}')">Eliminar</button>
                `;

                container.appendChild(userCard);
            });
        })
        .catch(error => console.error('Error al cargar usuarios:', error));
});

function deleteUser(nameUser) {
    fetch(`/Tv/rest/ManagementUser/deleteUser?nameUser=${nameUser}`, {
        method: 'DELETE',
    })
    .then(response => {
        if (response.ok) {
            document.location.reload(); // Recargar la página para actualizar la lista
        } else {
            console.error('Error al eliminar el usuario');
        }
    })
    .catch(error => console.error('Error en la solicitud:', error));
}
