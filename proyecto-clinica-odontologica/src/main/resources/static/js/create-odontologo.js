document
  .getElementById("odontologo-form")
  .addEventListener("submit", function (event) {
    event.preventDefault(); // Evita el envío del formulario

    const userName = document.getElementById("userName");
    const lastName = document.getElementById("lastName");
    const matricula = document.getElementById("matricula");

    const url = "http://localhost:8080/odontologos/";

    //validar
    let errorValidation = "";
    //const nameRule = /^[a-zA-Z\s]*$/;
    const nameRule = /^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+$/; // Expresión regular para nombres válidos
    //const matriculaRule = /^[0-9]*$/;
    //const matriculaRule = MAT-\\d{5};

    if (!nameRule.test(userName.value)) {
      errorValidation =
        errorValidation + "\n" + "El nombre solo puede contener letras";
    }
    if (userName.value.trim() === "") {
      errorValidation =
        errorValidation + "\n" + "El nombre no puede estar vacío";
    }
    if (userName.value.length < 2 || userName.value.length > 50) {
      errorValidation =
        errorValidation + "\n" + "El nombre debe tener mínimo 2 caracteres.";
    }
    if (lastName.value.trim() === "") {
      errorValidation =
        errorValidation + "\n" + "El apellido no puede estar vacío";
    }
    if (!nameRule.test(lastName.value)) {
      errorValidation =
        errorValidation + "\n" + "El apellido solo puede contener letras";
    }
    if (lastName.value.length < 2 || lastName.value.length > 50) {
      errorValidation =
        errorValidation + "\n" + "El apellido debe tener mínimo 2 caracteres.";
    }
    if (matricula.value.trim() === "") {
      errorValidation =
        errorValidation + "\n" + "La matricula no puede estar vacío";
    }
    // if (!matriculaRule.test(matricula.value)) {
    //   errorValidation = errorValidation + "\n" + "La matricula solo puede contener números";
    // }
    if (matricula.value.length < 6 || matricula.value.length > 10) {
      errorValidation =
        errorValidation +
        "\n" +
        "La matricula debe tener entre mínimo 6 caracteres.";
    }

    //lanzar mensaje
    if (errorValidation !== "") {
      statusMessageValidation(errorValidation);
      return;
    }

    //Cuerpo de la request (petición al servidor)
    const datos = {
      nombre: userName.value,
      apellido: lastName.value,
      matricula: matricula.value,
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
    registrarOdontologo(url, settings);
  });

function registrarOdontologo(url, settings) {
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

      statusMessage(
        `Odontologo ${userData} registrado!`,
        "success",
        statusCode
      );
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
