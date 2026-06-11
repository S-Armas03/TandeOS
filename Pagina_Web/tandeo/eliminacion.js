import { db } from '../firebase-config.js';
import { collection, getDocs, deleteDoc, doc } from "https://www.gstatic.com/firebasejs/12.14.0/firebase-firestore.js";

// Verificar sesión
const session = localStorage.getItem('tandeoSession') || sessionStorage.getItem('tandeoSession');
if (!session) {
    window.location.href = '../login-registro/login.html';
}

let tandeoAEliminar = null;

// Cargar tandeos
async function cargarTandeos() {
    const snapshot = await getDocs(collection(db, "Tandeos"));
    const tbody = document.getElementById('tablaTandeos');
    
    if (snapshot.empty) {
        tbody.innerHTML = '</table><td colspan="4">No hay tandeos</td></tr>';
        return;
    }
    
    tbody.innerHTML = '';
    snapshot.forEach(doc => {
        const t = doc.data();
        const row = tbody.insertRow();
        row.innerHTML = `
            <td>${t.Zona_Tandeo || 'N/A'}</td>
            <td>${t.Ubicacion || 'N/A'}</td>
            <td>${t.Estado || 'Activo'}</td>
            <td><button class="btn-delete" data-id="${doc.id}" data-nombre="${t.Zona_Tandeo}">🗑️</button></td>
        `;
    });
    
    document.querySelectorAll('.btn-delete').forEach(btn => {
        btn.addEventListener('click', () => {
            tandeoAEliminar = btn.getAttribute('data-id');
            document.getElementById('confirmModal').style.display = 'flex';
        });
    });
}

// Confirmar eliminación
document.getElementById('confirmarEliminar').addEventListener('click', async () => {
    if (tandeoAEliminar) {
        await deleteDoc(doc(db, "Tandeos", tandeoAEliminar));
        alert("✅ Eliminado");
        location.reload();
    }
});

function cerrarModal() {
    document.getElementById('confirmModal').style.display = 'none';
}

window.cerrarModal = cerrarModal;

cargarTandeos();