let urlOdontologoSearch = "http://localhost:8080/odontologos/buscar";
let statusCode;

document
  .getElementById("odontologo-form")
  .addEventListener("submit", async function (event) {
    event.preventDefault(); // Evita el envío del formulario
    const id = document.getElementById("id");

    //validar
    let errorValidation = "";
    if (id.value.trim() === "") {
      errorValidation =
        errorValidation + "\n" + "El campo id no puede estar vacío";
    }

    //lanzar mensaje
    if (errorValidation !== "") {
      statusMessageValidation(errorValidation);
      return;
    }

    const settingsGet = {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
    };

    // Disparar la consulta del login a la API
    urlOdontologoSearch = urlOdontologoSearch + "/" + id.value;
    let jsonDataOdontologo = await fetchData(urlOdontologoSearch, settingsGet);
    let userData =
      jsonDataOdontologo.nombre + " " + jsonDataOdontologo.apellido;
    statusMessage(`Odontologo ${userData} Encontrado!`, "success", statusCode);
  });

async function fetchData(url, settings) {
  try {
    console.log("Lanzar la request a la API...");
    // Perform the GET request
    let response = await fetch(`${url}`, settings);

    // Check if the request was successful
    if (!response.ok) {
      throw new Error("Network response was not ok " + response.statusText);
    }

    // Parse the JSON response
    console.log(response);
    statusCode = response.status;

    let data = await response.json();

    // Log the data
    return data;
  } catch (error) {
    // Handle any errors
    console.log(error);

    console.error("Error fetching no data:");
    //
    statusMessage("Ingrese Id valido", "warning", statusCode);
    //
  }
}
