function validateUser() {
    var nameUser = document.getElementById("input-user").value;
    var password = document.getElementById("input-password").value;

    if (!nameUser || !password) {
        alert("Por favor, ingrese nombre de usuario y contraseña.");
        return;
    }

    fetch("http://localhost:8080/Tv/rest/ManagementUser/validateUser", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            nameUser: nameUser,
            password: password
        })
    })
    .then(response => response.json())
    .then(response => {
        if (response.valid) {
            window.location.href = "./dashboard.html";
        } else {
            alert("Usuario no se encuentra registrado");
        }
    })
    .catch(error => console.error("Error:", error));
}
