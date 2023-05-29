// Siempre debe empezar así:
$(document).ready(function()
{
 // Código jQuery ...
});

// Se puede meter todo lo de html en un div y según se necesite se hace un ("divNombre").hide
/*
    $(function()
    {
        $("#button1").on("click", function()
        {
            $("p").hide();
        });
    });

    -----------------

    $(function()
    {
        $("#button2").on("click", function()
        {
            $("p").show();
        });
    });
*/

// Se puede separar con espacios para llamar a varios:

/*
$(function()
    {
        $("#button2").on("click", function()
        {
            $("p, #ocultar").show();
        });
    });
*/


// Crear un archivo main.js que tenga el contenido de los demás js (requires) y que cargue en el html. Luego jQuery puede llamarlos.
// Debería tener un archivo main que tenga todos los demás js, el archivo main es el jquery además de importar los otros archivos js
// Por otro lado, hacer que cuando pulses un botón se hagan invisibles los otros divs y visibile el q quiero mostrar.
// Identificar al botón con un id, cuando se pulse uno, este debe hacer visible el div del html, cargar su js y hacer invisible los demás.
// 1 archivo html, 1 archivo js main y los demás js. Cada js tendrá su jquery funcionando en si mismo y el main simplemente hace un requrie de todos los demás.

// require, import o includes

