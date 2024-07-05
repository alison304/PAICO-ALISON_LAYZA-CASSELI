const url = "http://localhost:8080/turnos/";

window.addEventListener("load", function () {
  //Configuración de la request del fetch
  const settings = {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
  };

  listarTurnos(url, settings);
});

function listarTurnos(url, settings) {
  let statusCode = 0;
  console.log(settings);
  console.log("Lanzar la request a la API...");

  fetch(`${url}listar`, settings)
    .then((response) => {
      console.log(response);

      statusCode = response.status;
      // Manejar el error de la request.
      if (response.ok) return response.json();

      // En caso de que la propiedad ok de la respuesta en false
      return Promise.reject(response);
    })
    .then((data) => {
      console.log("Promesa cumplida💍");
      console.log(data);

      const responseData = JSON.stringify(data);
      console.log(responseData);
      //console.log(data.status);

      const appointmentDetails = extractAppointmentDetails(
        JSON.parse(responseData)
      );
      console.log(appointmentDetails);
      createTableRows(appointmentDetails);
    })
    .catch((err) => {
      console.warn("Promesa rechazada 🙅🏻‍♀️");
      console.log(err);

      if (err.status) {
        err.text().then((errText) => {
          console.log("Status Code " + err.status);
          console.log(errText);
          if (errText) {
            statusMessage(errText, "error", err.status);
          }
        });
      } else {
        console.log("Time out network issue ");
      }
    });
}

function extractAppointmentDetails(appointments) {
  return appointments.map((appointment) => {
    const { id, fechaYHora, odontologo, paciente } = appointment;
    return {
      id,
      fechaYHora,
      nombreOdontologo: `${odontologo.nombre} ${odontologo.apellido}`,
      nombrePaciente: `${paciente.nombre} ${paciente.apellido}`,
    };
  });
}

// const appointmentDetails = extractAppointmentDetails(jsonResponse);

appointmentDetails.forEach((detail) => {
  console.log(
    `Turno Registrado! el ${detail.fechaYHora} con el Odontologo ${detail.nombreOdontologo} y paciente ${detail.nombrePaciente}`
  );
});

// Function to create table rows
function createTableRows(jsonData) {
  const tableBody = document.querySelector("#data-table tbody");
  jsonData.forEach((item) => {
    let id;
    const tr = document.createElement("tr");
    Object.entries(item).forEach((row) => {
      key = row["0"];
      value = row["1"];
      if (key == "id") {
        id = value;
        const td = document.createElement("td");
        td.style = "display:none;";
        td.textContent = value;
        tr.appendChild(td);
      } else {
        const td = document.createElement("td");
        td.textContent = value;
        tr.appendChild(td);
      }
    });
    var td = document.createElement("td");
    console.log(id);
    td.innerHTML = `<a href="#" class="theme-btn btn-style-one"><span class="btn-title" onclick="deleteRow(${id}); return false">Eliminar</span></a>`;
    tr.appendChild(td);
    tableBody.appendChild(tr);
  });
}

function deleteRow(id) {
  //Configuración de la request del fetch
  const settings = {
    method: "DELETE",
    headers: {
      "Content-Type": "application/json",
    },
  };

  let statusCode = 0;
  console.log(settings);
  console.log("Lanzar la request a la API...");

  fetch(`${url}eliminar/${id}`, settings)
    .then((response) => {
      console.log(response);

      statusCode = response.status;
      // Manejar el error de la request.
      if (response.ok) return "ok";

      // En caso de que la propiedad ok de la respuesta en false
      return Promise.reject(response);
    })
    .then((data) => {
      console.log("Promesa cumplida💍");
      console.log(data);

      statusMessage(`Turnos eliminado!`, "success", statusCode);
    })
    .catch((err) => {
      console.warn("Promesa rechazada 🙅🏻‍♀️");
      console.log(err);

      if (err.status) {
        err.text().then((errText) => {
          console.log("Status Code " + err.status);
          console.log(errText);
          if (errText) {
            statusMessage(errText, "warning", err.status);
          }
        });
      } else {
        console.log("Time out network issue ");
      }
    });
}
