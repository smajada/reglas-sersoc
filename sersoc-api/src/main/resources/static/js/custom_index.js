const search = document.querySelector('#search');
const table = document.getElementById('table');
const tr = table.getElementsByTagName('tr');

search.addEventListener('keyup', function() {
    const filter = search.value.toUpperCase(); // Move inside event listener
    for (let i = 0; i < tr.length; i++) {
        let td = tr[i].getElementsByTagName('td')[1];
        if (td) {
            let textValue = td.textContent || td.innerText;
            if (textValue.toUpperCase().indexOf(filter) > -1) {
                tr[i].style.display = '';
            } else {
                tr[i].style.display = 'none';
            }
        }
    }
});

document.addEventListener("DOMContentLoaded", function() {
    // Objeto para almacenar editores de CodeMirror ya inicializados
    let initializedEditors = {};

    // Función para inicializar un editor de CodeMirror
    function initializeEditor(textarea) {
        // Verificar si el editor ya ha sido inicializado para este textarea
        if (!initializedEditors.hasOwnProperty(textarea.id)) {
            // Crear un nuevo editor de CodeMirror
            let editor = CodeMirror.fromTextArea(textarea, {
                lineNumbers: true,
                readOnly: true, // Hacer que el editor sea de solo lectura
                mode: "javascript"
            });

            // Establecer el contenido del editor
            editor.setValue(textarea.value);

            // Almacenar el editor inicializado en el objeto initializedEditors
            initializedEditors[textarea.id] = editor;
        }
    }

    // Obtener todos los modales
    let modals = document.querySelectorAll('.modal');

    // Iterar sobre cada modal
    modals.forEach(function(modal) {
        modal.addEventListener('shown.bs.modal', function () {
            // Obtener el textarea dentro del modal
            let textarea = modal.querySelector('textarea[id^="regla-"]');

            // Inicializar el editor de CodeMirror para el textarea
            initializeEditor(textarea);
        });
    });
});

