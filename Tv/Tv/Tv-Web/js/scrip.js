function validateUser() {
    var nameUser = document.getElementById("input-user").value;
    var password = document.getElementById("input-password").value;

    fetch("http://localhost:8080/Library/rest/ManagementUser/validateUser?nameUser=" + nameUser + "&password=" + password)
        .then(response => response.json())
        .then(response => {
            if (response) {
                window.location.href = "./dashboard.html";
            } else {
                alert("Usuario no se encuentra registrado");
            }
        })
        .catch(error => console.error("Error:", error));
}