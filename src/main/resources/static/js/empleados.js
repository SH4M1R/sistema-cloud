const formEmpleado = document.getElementById('formEmpleado');
const formRol = document.getElementById('formRol');

function nuevoEmpleado() {
    formEmpleado.reset();
    document.getElementById('eId').value = '';
    document.getElementById('tituloEmpleado').textContent = 'Nuevo empleado';
    abrirModal('modalEmpleado');
}

function editarEmpleado(boton) {
    const d = boton.dataset;
    formEmpleado.reset();
    document.getElementById('eId').value = d.id;
    document.getElementById('eNombres').value = d.nombres || '';
    document.getElementById('eApellidos').value = d.apellidos || '';
    document.getElementById('eTelefono').value = d.telefono || '';
    document.getElementById('eRol').value = d.rolId || '';
    document.getElementById('tituloEmpleado').textContent = 'Editar empleado';
    abrirModal('modalEmpleado');
}

async function eliminarEmpleado(id) {
    if (!confirm('¿Eliminar este empleado?')) return;
    const r = await llamarApi('/api/empleados/' + id, 'DELETE');
    if (r.ok) location.reload();
    else alert(r.errores.join('\n'));
}

formEmpleado.addEventListener('submit', async (e) => {
    e.preventDefault();
    const id = document.getElementById('eId').value;
    const cuerpo = {
        nombres: document.getElementById('eNombres').value.trim(),
        apellidos: document.getElementById('eApellidos').value.trim(),
        telefono: document.getElementById('eTelefono').value.trim(),
        rolId: numeroONulo(document.getElementById('eRol').value)
    };
    const url = id ? '/api/empleados/' + id : '/api/empleados';
    const r = await llamarApi(url, id ? 'PUT' : 'POST', cuerpo);
    if (r.ok) location.reload();
    else mostrarErrores('erroresEmpleado', r.errores);
});

// Crear rol desde su propio modal y dejarlo seleccionado en el empleado
formRol.addEventListener('submit', async (e) => {
    e.preventDefault();
    const r = await llamarApi('/api/roles', 'POST', {
        nombre: document.getElementById('rNombre').value.trim()
    });
    if (r.ok) {
        document.getElementById('eRol').add(new Option(r.datos.nombre, r.datos.id, true, true));
        cerrarModal('modalRol');
    } else {
        mostrarErrores('erroresRol', r.errores);
    }
});
