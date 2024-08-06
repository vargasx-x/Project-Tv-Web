document.getElementById("add-user-button").addEventListener("click", addUser);

function addUser() {
    // Obtener valores del formulario
    let nameUser = document.getElementById("input-name-user").value;
    let password = document.getElementById("input-password").value;

    // Verificar si los campos están vacíos
    if (!nameUser || !password) {
        alert("Por favor, complete todos los campos.");
        return;
    }

    // Crear objeto de usuario
    let userData = {
        nameUser: nameUser,
        password: password
    };

    // URL del endpoint REST
    let url = "http://localhost:8080/Tv/rest/ManagementUser/createUser";

    // Realizar solicitud POST
    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(userData)
    })
    .then(response => {
        if (!response.ok) {
            return response.text().then(text => { throw new Error(text); });
        }
        return response.json();
    })
    .then(data => {
        alert("Usuario agregado con éxito.");
        window.location.href = "./dashboard.html";
    })
    .catch(error => {
        console.error("Ocurrió el siguiente error con la operación:", error);
        alert("Error al agregar el usuario. Por favor, intente nuevamente.");
    });
}
