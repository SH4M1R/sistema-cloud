// Utilidades compartidas por las vistas

function abrirModal(id) {
    const modal = document.getElementById(id);
    modal.classList.remove('hidden');
    modal.classList.add('flex');
}

function cerrarModal(id) {
    const modal = document.getElementById(id);
    modal.classList.add('hidden');
    modal.classList.remove('flex');
    const form = modal.querySelector('form');
    if (form) form.reset();
    const errores = modal.querySelector('.errores');
    if (errores) errores.classList.add('hidden');
}

function mostrarErrores(idContenedor, errores) {
    const contenedor = document.getElementById(idContenedor);
    contenedor.replaceChildren(...errores.map(texto => {
        const p = document.createElement('p');
        p.textContent = texto;
        return p;
    }));
    contenedor.classList.remove('hidden');
}

function numeroONulo(valor) {
    return valor === '' ? null : Number(valor);
}

// Llama a la API REST. Devuelve { ok, datos, errores }
async function llamarApi(url, metodo, cuerpo) {
    const resp = await fetch(url, {
        method: metodo,
        headers: { 'Content-Type': 'application/json' },
        body: cuerpo ? JSON.stringify(cuerpo) : undefined
    });

    // Sesión vencida: Spring Security redirige al login
    if (resp.redirected && resp.url.includes('/login')) {
        window.location.href = '/login';
        return { ok: false, errores: ['Sesión expirada'] };
    }

    if (resp.ok) {
        const texto = await resp.text();
        return { ok: true, datos: texto ? JSON.parse(texto) : null };
    }

    let errores;
    try {
        const datos = await resp.json();
        errores = datos.error ? [datos.error] : Object.values(datos);
    } catch (e) {
        errores = ['Error inesperado (' + resp.status + ')'];
    }
    return { ok: false, errores };
}
