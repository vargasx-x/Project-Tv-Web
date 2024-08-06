document.addEventListener('DOMContentLoaded', function() {
    fetchUsers();
});

function fetchUsers() {
    const url = 'http://localhost:8080/Tv/rest/ManagementUser/getUsers'; // Cambia la URL si es necesario

    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error('Error en la respuesta del servidor');
            }
            return response.json();
        })
        .then(users => {
            displayUsers(users);
        })
        .catch(error => {
            console.error('Error al obtener los usuarios:', error);
        });
}

function displayUsers(users) {
    const userList = document.getElementById('user-list');
    userList.innerHTML = ''; // Limpiar el contenedor

    users.forEach(user => {
        const userCard = document.createElement('div');
        userCard.className = 'card-user';

        const userName = document.createElement('h3');
        userName.textContent = `Usuario: ${user.nameUser}`;

        const userPassword = document.createElement('p');
        userPassword.textContent = `Contraseña: ${user.password}`;
        
        const deleteButton = document.createElement('button');
        deleteButton.className = 'btn';
        deleteButton.textContent = 'Eliminar';
        deleteButton.onclick = function() {
            deleteUser(user.nameUser);
        };

        userCard.appendChild(userName);
        userCard.appendChild(userPassword);
        userCard.appendChild(deleteButton);
        userList.appendChild(userCard);
    });
}

function deleteUser(nameUser) {
    const url = `http://localhost:8080/Tv/rest/ManagementUser/deleteUser?nameUser=${encodeURIComponent(nameUser)}`;

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
        alert('Usuario eliminado correctamente');
        fetchUsers(); // Actualiza la lista de usuarios
    })
    .catch(error => {
        console.error('Error al eliminar el usuario:', error);
    });
}

