import { db } from './firebase-config.js';
import { collection, getDocs } from "https://www.gstatic.com/firebasejs/12.14.0/firebase-firestore.js";

// Verificar sesión
function verificarSesion() {
    const session = localStorage.getItem('tandeoSession') || sessionStorage.getItem('tandeoSession');
    
    if (!session) {
        window.location.href = 'login-registro/login.html';
        return null;
    }
    return JSON.parse(session);
}

function mostrarUsuario(session) {
    if (session) {
        const nombre = session.nombre || session.email.split('@')[0];
        document.getElementById('userName').textContent = nombre;
        document.getElementById('userNameHeader').textContent = nombre;
        document.getElementById('userEmail').textContent = session.email;
        document.getElementById('userAvatar').textContent = nombre.charAt(0).toUpperCase();
    }
}

// Cargar estadísticas
async function cargarEstadisticas() {
    try {
        const snapshot = await getDocs(collection(db, "Tandeos"));
        const tandeos = [];
        snapshot.forEach(doc => tandeos.push(doc.data()));
        
        document.getElementById('totalTandeos').textContent = tandeos.length;
        document.getElementById('tandeosActivos').textContent = tandeos.filter(t => t.Estado !== 'Pasado').length;
        document.getElementById('tandeosCompletados').textContent = tandeos.filter(t => t.Estado === 'Pasado').length;
    } catch (error) {
        console.error("Error:", error);
    }
}

// Cargar tandeos
async function cargarTandeos() {
    try {
        const snapshot = await getDocs(collection(db, "Tandeos"));
        const tbody = document.getElementById('tablaTandeos');
        
        if (snapshot.empty) {
            tbody.innerHTML = '<tr><td colspan="5">No hay tandeos</td></tr>';
            return;
        }
        
        tbody.innerHTML = '';
        snapshot.forEach(doc => {
            const t = doc.data();
            const row = tbody.insertRow();
            let clase = t.Estado === 'Pasado' ? 'badge-success' : 'badge-warning';
            row.innerHTML = `
                <td>${t.Zona_Tandeo || 'N/A'}</td>
                <td>${t.Ubicacion || 'N/A'}</td>
                <td>${t.Fecha_Inicio || 'N/A'}</td>
                <td>${t.Fecha_Final || 'N/A'}</td>
                <td><span class="badge ${clase}">${t.Estado || 'Activo'}</span></td>
            `;
        });
    } catch (error) {
        console.error("Error:", error);
    }
}

// Cerrar sesión
document.getElementById('logoutBtn').addEventListener('click', () => {
    localStorage.removeItem('tandeoSession');
    sessionStorage.removeItem('tandeoSession');
    window.location.href = 'login-registro/login.html';
});

// Inicializar
const session = verificarSesion();
if (session) {
    mostrarUsuario(session);
    cargarEstadisticas();
    cargarTandeos();
}