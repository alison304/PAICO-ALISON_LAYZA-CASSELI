const urlPacienteList = "http://localhost:8080/pacientes/listar";
const urlOdontologoList = "http://localhost:8080/odontologos/listar";
const urlTurnoCreate = "http://localhost:8080/turnos/registrar";
let jsonDataOdontologo;
let jsonDataPaciente;
let statusCode;

document.addEventListener("DOMContentLoaded", async function () {
  const settingsGet = {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
  };
  jsonDataOdontologo = await fetchData(urlOdontologoList, settingsGet);
  jsonDataPaciente = await fetchData(urlPacienteList, settingsGet);
  console.log(jsonDataOdontologo);
  console.log(jsonDataPaciente);

  const pacienteSelect = document.getElementById("pacienteSelect");
  const odontologoSelect = document.getElementById("odontologoSelect");

  jsonDataPaciente.forEach((item) => {
    const option = document.createElement("option");
    option.value = item.id;
    option.textContent = item.nombre + " " + item.apellido;
    pacienteSelect.appendChild(option);
  });

  jsonDataOdontologo.forEach((item) => {
    const option = document.createElement("option");
    option.value = item.id;
    option.textContent = item.nombre + " " + item.apellido;
    odontologoSelect.appendChild(option);
  });
});

document
  .getElementById("appointment-form")
  .addEventListener("submit", async function (event) {
    event.preventDefault(); // Evita el envío del formulario
    const fecha = document.getElementById("fecha");
    const hora = document.getElementById("hora");
    const odontologo = document.getElementById("odontologoSelect");
    const paciente = document.getElementById("pacienteSelect");

    //validar
    let errorValidation = "";
    if (fecha.value.trim() === "") {
      errorValidation =
        errorValidation + "\n" + "El campo fecha no puede estar vacío";
    }
    if (hora.value.trim() === "") {
      errorValidation =
        errorValidation + "\n" + "El campo hora no puede estar vacío";
    }
    if (odontologo.value.trim() === "") {
      errorValidation =
        errorValidation + "\n" + "El campo odontólogo no puede estar vacío";
    }
    if (paciente.value.trim() === "") {
      errorValidation =
        errorValidation + "\n" + "El campo paciente no puede estar vacío";
    }

    //lanzar mensaje
    if (errorValidation !== "") {
      statusMessageValidation(errorValidation);
      return;
    }

    odontologoId = Number(odontologo.value);
    pacienteId = Number(paciente.value);
    const foundOdontologo = jsonDataOdontologo.find(
      (item) => item.id === odontologoId
    );
    const foundPaciente = jsonDataPaciente.find(
      (item) => item.id === pacienteId
    );
    delete foundOdontologo.id;
    delete foundPaciente.id;

    //Cuerpo de la request (petición al servidor)
    const datos = {
      fechaYHora: fecha.value + " " + hora.value,
      odontologo: foundOdontologo,
      paciente: foundPaciente,
    };
    console.log(datos);

    const settingsPost = {
      method: "POST",
      body: JSON.stringify(datos),
      headers: {
        "Content-Type": "application/json",
      },
    };

    // Disparar la consulta del login a la API
    let jsonDataTurno = await fetchData(urlTurnoCreate, settingsPost);
    console.log(jsonDataTurno);
    statusMessage(
      `Turno Registrado! el ${jsonDataTurno.fechaYHora} con el Odontologo ${jsonDataTurno.odontologo.nombre} ${jsonDataTurno.odontologo.apellido}. ${jsonDataTurno.odontologo.matricula}`,
      "success",
      200
    );
  });

async function fetchData(url, settings) {
  try {
    console.log("Lanzar la request a la API...");
    // Perform the GET request
    let response = await fetch(`${url}`, settings);
    statusCode = response.status;

    // Check if the request was successful
    if (!response.ok) {
      throw new Error("Network response was not ok " + response.statusText);
    }

    // Parse the JSON response
    let data = await response.json();

    // Log the data
    return data;
  } catch (error) {
    // Handle any errors
    console.error("Error fetching data:", error);
    return null;
  }
}
