<?php
// Directorio donde se guardarán los archivos subidos
$target_dir = "uploads/";

// Obtener el nombre del archivo subido
$target_file = $target_dir . basename($_FILES["file"]["name"]);

// Variable para verificar si la subida fue exitosa
$uploadOk = 1;
$fileType = strtolower(pathinfo($target_file, PATHINFO_EXTENSION));

// Verificar si el archivo ya existe
if (file_exists($target_file)) {
    echo "Lo sentimos, el archivo ya existe.";
    $uploadOk = 0;
}

// Verificar el tamaño del archivo (ejemplo: máximo 2MB)
if ($_FILES["file"]["size"] > 2000000) {
    echo "Lo sentimos, su archivo es demasiado grande.";
    $uploadOk = 0;
}

// Permitir solo ciertos tipos de archivos (ejemplo: imágenes y PDF)
$allowed_types = ["jpg", "png", "jpeg", "gif", "pdf", "php", "html"];
if (!in_array($fileType, $allowed_types)) {
    echo "Lo sentimos, solo se permiten archivos JPG, JPEG, PNG, GIF y PDF.";
    $uploadOk = 0;
}

// Comprobar si la variable $uploadOk tiene valor 0 (error en la subida)
if ($uploadOk == 0) {
    echo "Lo sentimos, su archivo no fue cargado.";
} else {
    // Si todo está bien, intenta subir el archivo
    if (move_uploaded_file($_FILES["file"]["tmp_name"], $target_file)) {
        echo "El archivo ". basename($_FILES["file"]["name"]). " Se ha subido con exito";
    } else {
        echo "Lo sentimos, hubo un error al cargar tu archivo.";
    }
}
?>
