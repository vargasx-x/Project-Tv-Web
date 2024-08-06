document.addEventListener("DOMContentLoaded", function() {
    document.getElementById("add-user-button").addEventListener("click", addUser);

    function addUser() {
        let nameUser = document.getElementById("input-name-user").value;
        let password = document.getElementById("input-password").value;

        let userData = {
            nameUser: nameUser,
            password: password
        };

        let url = "http://localhost:8080/Tv/rest/ManagementUser/createUser";

        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userData)
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
            alert("Usuario agregado correctamente");
            window.location.href = "./dashboard.html";
        })
        .catch(error => {
            alert(error.message);
            console.error("Ocurrió el siguiente error con la operación", error);
        });
    }
});
