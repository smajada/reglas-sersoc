document.addEventListener("DOMContentLoaded", function() {
    // Objeto para almacenar editores de CodeMirror ya inicializados
    var initializedEditors = {};

    // Función para inicializar un editor de CodeMirror
    function initializeEditor(textarea) {
        // Verificar si el editor ya ha sido inicializado para este textarea
        if (!initializedEditors.hasOwnProperty(textarea.id)) {
            // Crear un nuevo editor de CodeMirror
            var editor = CodeMirror.fromTextArea(textarea, {
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
    var modals = document.querySelectorAll('.modal');

    // Iterar sobre cada modal
    modals.forEach(function(modal) {
        modal.addEventListener('shown.bs.modal', function (event) {
            // Obtener el textarea dentro del modal
            var textarea = modal.querySelector('textarea[id^="regla-"]');

            // Inicializar el editor de CodeMirror para el textarea
            initializeEditor(textarea);
        });
    });
});
