document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("add-user-button").addEventListener("click", function () {
        const username = document.getElementById("input-username").value;
        const password = document.getElementById("input-password").value;

        if (username && password) {
            fetch("http://localhost:8080/Tv/rest/ManagementUser/addUser", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    nameUser: username,
                    password: password
                })
            })
            .then(response => response.json())
            .then(data => {
                alert("Usuario añadido correctamente");
                // Limpiar los campos después de agregar el usuario
                document.getElementById("input-username").value = "";
                document.getElementById("input-password").value = "";
            })
            .catch(error => {
                console.error("Error:", error);
                alert("Ocurrió un error al agregar el usuario");
            });
        } else {
            alert("Por favor, complete todos los campos.");
        }
    });
});
