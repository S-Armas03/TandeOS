import { db } from '../firebase-config.js';
import { collection, getDocs, updateDoc, doc } from "https://www.gstatic.com/firebasejs/12.14.0/firebase-firestore.js";

// Verificar sesión
const session = localStorage.getItem('tandeoSession') || sessionStorage.getItem('tandeoSession');
if (!session) {
    window.location.href = '../login-registro/login.html';
}

let tandeosList = [];

// Cargar tandeos
async function cargarTandeos() {
    const snapshot = await getDocs(collection(db, "Tandeos"));
    const select = document.getElementById('tandeoSelect');
    select.innerHTML = '<option value="">-- Seleccionar --</option>';
    
    tandeosList = [];
    snapshot.forEach(doc => {
        const t = { id: doc.id, ...doc.data() };
        tandeosList.push(t);
        const option = document.createElement('option');
        option.value = doc.id;
        option.textContent = `${t.Zona_Tandeo} - ${t.Ubicacion}`;
        select.appendChild(option);
    });
}

// Cargar datos al seleccionar
document.getElementById('tandeoSelect').addEventListener('change', (e) => {
    const id = e.target.value;
    if (!id) return;
    
    const tandeo = tandeosList.find(t => t.id === id);
    if (tandeo) {
        document.getElementById('zona').value = tandeo.Zona_Tandeo || '';
        document.getElementById('ubicacion').value = tandeo.Ubicacion || '';
        document.getElementById('estado').value = tandeo.Estado || 'Activo';
        document.getElementById('editFormContainer').style.display = 'block';
    }
});

// Guardar cambios
document.getElementById('editarTandeoForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const id = document.getElementById('tandeoSelect').value;
    
    await updateDoc(doc(db, "Tandeos", id), {
        Zona_Tandeo: document.getElementById('zona').value,
        Ubicacion: document.getElementById('ubicacion').value,
        Estado: document.getElementById('estado').value
    });
    
    alert("Actualizado");
    location.reload();
});

cargarTandeos();