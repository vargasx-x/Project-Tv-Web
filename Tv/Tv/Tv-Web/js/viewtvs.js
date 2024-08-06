document.addEventListener('DOMContentLoaded', function() {
    fetchTvs();
});

function fetchTvs() {
    const url = 'http://localhost:8080/Tv/rest/ManagementTv/getTvs';

    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error('Error en la respuesta del servidor');
            }
            return response.json();
        })
        .then(tvs => {
            displayTvs(tvs);
        })
        .catch(error => {
            console.error('Error al obtener los televisores:', error);
        });
}

function displayTvs(tvs) {
    const tvList = document.getElementById("tv-list");
    tvList.innerHTML = '';
    if (tvs.length === 0) {
        tvList.innerHTML = "<p>No hay televisores disponibles.</p>";
        return;
    }

    tvs.forEach(tv => {
        const tvCard = document.createElement('div');
        tvCard.className = 'card-tv';

        const serialNumber = document.createElement('h3');
        serialNumber.textContent = `Numero de Serie: ${tv.serialNumber}`;

        const resolution = document.createElement('p');
        resolution.textContent = `Resolución: ${tv.resolution}`;

        const sizeDisplay = document.createElement('p');
        sizeDisplay.textContent = `Tamaño Pantalla: ${tv.sizeDisplay}`;

        const technologyDisplay = document.createElement('p');
        technologyDisplay.textContent = `Tecnología Pantalla: ${tv.technologyDisplay}`;
        
        const systemOperational = document.createElement('p');
        systemOperational.textContent = `Sistema Operativo: ${tv.systemOperational}`;

        const deleteButton = document.createElement('button');
        deleteButton.className = 'btn btn-danger';
        deleteButton.textContent = 'Eliminar';
        deleteButton.onclick = function() {
            deleteUser(tv.serialNumber);
        };

        const btnActualizar = document.createElement('a');
        btnActualizar.className = 'btn btn-success margin-button';
        btnActualizar.id = `btn-update-${tv.serialNumber}`;
        btnActualizar.textContent = 'Actualizar';
        btnActualizar.setAttribute('data-code', tv.serialNumber);
        btnActualizar.addEventListener('click', function () {
            localStorage.setItem("tvData", JSON.stringify(tv));
            window.location.href = "./updatetv.html";
        });

        tvCard.appendChild(serialNumber);
        tvCard.appendChild(resolution);
        tvCard.appendChild(sizeDisplay);
        tvCard.appendChild(technologyDisplay);
        tvCard.appendChild(systemOperational);
        tvCard.appendChild(btnActualizar);
        tvCard.appendChild(deleteButton);
        tvList.appendChild(tvCard);
    });
}

function deleteUser(serialNumber) {
    const url = `http://localhost:8080/Tv/rest/ManagementTv/deleteTv?serialNumber=${encodeURIComponent(serialNumber)}`;

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
        alert('Tv eliminado correctamente');
        fetchTvs();
    })
    .catch(error => {
        console.error('Error al eliminar el tv:', error);
    });
}

function updateTv(tv) {
    const url = 'http://localhost:8080/Tv/rest/ManagementTv/updateTv';

    fetch(url, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(tv)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Error en la respuesta del servidor');
        }
        return response.json();
    })
    .then(data => {
        alert('Tv actualizado correctamente');
        fetchTvs();
    })
    .catch(error => {
        console.error('Error al actualizar el tv:', error);
    });
}
