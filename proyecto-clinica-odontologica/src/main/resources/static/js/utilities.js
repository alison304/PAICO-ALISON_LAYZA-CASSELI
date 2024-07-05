function statusMessage(er, iconType, stat = "") {
  Swal.fire({
    title: er,
    text: "Status Code: " + stat,
    icon: iconType,
    confirmButtonColor: "#3333ffff",
    cancelButtonColor: "#d33",
    confirmButtonText: "Aceptar",
    background: "black",
    allowOutsideClick: false,
  }).then((result) => {
    if (result.isConfirmed) {
      location.reload();
    }
  });
}

function statusMessageValidation(er, stat = "") {
  Swal.fire({
    title: er,
    text: "" + stat,
    icon: "warning",
    confirmButtonColor: "#3333ffff",
    cancelButtonColor: "#d33",
    confirmButtonText: "Aceptar",
    background: "black",
    allowOutsideClick: false,
  });
}
