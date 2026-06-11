import { db } from '../firebase-config.js';
import { collection, addDoc } from "https://www.gstatic.com/firebasejs/12.14.0/firebase-firestore.js";

// Verificar sesión
const session = localStorage.getItem('tandeoSession') || sessionStorage.getItem('tandeoSession');
if (!session) {
    window.location.href = '../login-registro/login.html';
}

// Mostrar usuario
const userData = JSON.parse(session);
document.getElementById('userName').textContent = userData.nombre || userData.email.split('@')[0];
document.getElementById('userEmail').textContent = userData.email;
document.getElementById('userAvatar').textContent = (userData.nombre || userData.email.charAt(0)).charAt(0).toUpperCase();

// Registrar tandeo
document.getElementById('registroTandeoForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const tandeoData = {
        Zona_Tandeo: document.getElementById('zona').value,
        Ubicacion: document.getElementById('ubicacion').value,
        Fecha_Inicio: document.getElementById('fechaInicio').value,
        Fecha_Final: document.getElementById('fechaFinal').value,
        Estado: document.getElementById('estado').value,
        fechaCreacion: new Date()
    };
    
    try {
        await addDoc(collection(db, "Tandeos"), tandeoData);
        alert("✅ Tandeo registrado");
        document.getElementById('registroTandeoForm').reset();
    } catch (error) {
        alert("Error: " + error.message);
    }
});