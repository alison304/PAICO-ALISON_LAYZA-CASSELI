document
  .getElementById("paciente-form")
  .addEventListener("submit", function (event) {
    event.preventDefault(); // Evita el envío del formulario

    const userName = document.getElementById("userName");
    const lastName = document.getElementById("lastName");
    const dni = document.getElementById("dni");
    const fechaIngreso = document.getElementById("fechaIngreso");
    const calle = document.getElementById("calle");
    const numero = document.getElementById("numero");
    const localidad = document.getElementById("localidad");
    const provincia = document.getElementById("provincia");

    const url = "http://localhost:8080/pacientes/";

    //validar

    let errorValidation = "";
    const nameRule = /^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+$/;
    const regexSoloNumeros = /^[0-9]*$/;

    if (!nameRule.test(userName.value)) {
      errorValidation =
        errorValidation + "\n" + "El nombre solo puede contener letras";
    }
    if (userName.value.trim() === "") {
      errorValidation =
        errorValidation + "\n" + "El campo nombre no puede estar vacío";
    }
    if (userName.value.length < 3 || userName.value.length > 50) {
      errorValidation =
        errorValidation + "\n" + "El nombre debe tener minimo 3 caracteres.";
    }
    if (lastName.value.trim() === "") {
      errorValidation =
        errorValidation + "\n" + "El campo apellido no puede estar vacío";
    }
    if (!nameRule.test(lastName.value)) {
      errorValidation =
        errorValidation + "\n" + "El apellido solo puede contener letras";
    }
    if (lastName.value.length < 3 || lastName.value.length > 50) {
      errorValidation =
        errorValidation + "\n" + "El apellido debe tener minimo 3 caracteres.";
    }
    if (dni.value.trim() === "") {
      errorValidation =
        errorValidation + "\n" + "El campo DNI no puede estar vacío";
    }
    if (!regexSoloNumeros.test(dni.value)) {
      errorValidation =
        errorValidation + "\n" + "El DNI solo debe contener números.";
    }
    if (dni.value.length != 8) {
      errorValidation =
        errorValidation + "\n" + "El DNI debe tener exactamente 8 carácteres.";
    }
    if (fechaIngreso.value.trim() === "") {
      errorValidation =
        errorValidation + "\n" + "La fecha de ingreso no puede estar vacía";
    }
    if (calle.value.trim() === "") {
      errorValidation =
        errorValidation + "\n" + "El campo calle no puede estar vacío";
    }
    if (calle.value.length < 2 || calle.value.length > 50) {
      errorValidation =
        errorValidation +
        "\n" +
        "La campo calle debe tener minimo 2 caracteres.";
    }
    if (numero.value.trim() === "") {
      errorValidation =
        errorValidation + "\n" + "El campo número no puede estar vacío";
    }
    if (numero.value.length < 1 || numero.value.length > 5) {
      errorValidation =
        errorValidation +
        "\n" +
        "El campo número debe tener minimo 1 caracter.";
    }
    if (localidad.value.trim() === "") {
      errorValidation =
        errorValidation + "\n" + "EL campo localidad no puede estar vacío";
    }
    if (localidad.value.length < 2 || localidad.value.length > 50) {
      errorValidation =
        errorValidation +
        "\n" +
        "El campo localidad debe tener minimo 2 caracteres.";
    }
    if (provincia.value.trim() === "") {
      errorValidation =
        errorValidation + "\n" + "El campo provincia no puede estar vacío";
    }
    if (provincia.value.length < 2 || provincia.value.length > 50) {
      errorValidation =
        errorValidation +
        "\n" +
        "El campo provincia debe tener minimo 2 caracteres.";
    }

    //lanzar mensaje
    if (errorValidation !== "") {
      statusMessageValidation(errorValidation);
      return;
    }

    //Cuerpo de la request (petición al servidor)
    const domicilio = {
      calle: calle.value,
      numero: Number(numero.value),
      localidad: localidad.value,
      provincia: provincia.value,
    };

    const datos = {
      nombre: userName.value,
      apellido: lastName.value,
      dni: Number(dni.value),
      fechaIngreso: fechaIngreso.value,
      domicilio: domicilio,
    };

    console.log(datos);

    //Configuración de la request del fetch
    const settings = {
      method: "POST",
      body: JSON.stringify(datos),
      headers: {
        "Content-Type": "application/json",
      },
    };

    // Disparar la consulta del login a la API
    registrarPaciente(url, settings);
  });

function registrarPaciente(url, settings) {
  let statusCode = 0;
  console.log(settings);
  console.log("Lanzar la request a la API...");

  fetch(`${url}registrar`, settings)
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

      console.log(data.status);

      userData = data.nombre + " " + data.apellido;

      statusMessage(`Paciente ${userData} registrado!`, "success", statusCode);
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
