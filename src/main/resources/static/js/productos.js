const formProducto = document.getElementById('formProducto');
const formCategoria = document.getElementById('formCategoria');

function nuevoProducto() {
    formProducto.reset();
    document.getElementById('pId').value = '';
    document.getElementById('tituloProducto').textContent = 'Nuevo producto';
    abrirModal('modalProducto');
}

function editarProducto(boton) {
    const d = boton.dataset;
    formProducto.reset();
    document.getElementById('pId').value = d.id;
    document.getElementById('pNombre').value = d.nombre || '';
    document.getElementById('pPrecio').value = d.precio || '';
    document.getElementById('pStock').value = d.stock || '';
    document.getElementById('pCategoria').value = d.categoriaId || '';
    document.getElementById('tituloProducto').textContent = 'Editar producto';
    abrirModal('modalProducto');
}

async function eliminarProducto(id) {
    if (!confirm('¿Eliminar este producto?')) return;
    const r = await llamarApi('/api/productos/' + id, 'DELETE');
    if (r.ok) location.reload();
    else alert(r.errores.join('\n'));
}

formProducto.addEventListener('submit', async (e) => {
    e.preventDefault();
    const id = document.getElementById('pId').value;
    const cuerpo = {
        nombre: document.getElementById('pNombre').value.trim(),
        precio: numeroONulo(document.getElementById('pPrecio').value),
        stock: numeroONulo(document.getElementById('pStock').value),
        categoriaId: numeroONulo(document.getElementById('pCategoria').value)
    };
    const url = id ? '/api/productos/' + id : '/api/productos';
    const r = await llamarApi(url, id ? 'PUT' : 'POST', cuerpo);
    if (r.ok) location.reload();
    else mostrarErrores('erroresProducto', r.errores);
});

// Crear categoría desde su propio modal y dejarla seleccionada en el producto
formCategoria.addEventListener('submit', async (e) => {
    e.preventDefault();
    const cuerpo = {
        nombre: document.getElementById('cNombre').value.trim(),
    };
    const r = await llamarApi('/api/categorias', 'POST', cuerpo);
    if (r.ok) {
        document.getElementById('pCategoria').add(new Option(r.datos.nombre, r.datos.id, true, true));
        cerrarModal('modalCategoria');
    } else {
        mostrarErrores('erroresCategoria', r.errores);
    }
});
