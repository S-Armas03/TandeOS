import { db } from '../firebase-config.js';
import { collection, addDoc, query, where, getDocs } from "https://www.gstatic.com/firebasejs/12.14.0/firebase-firestore.js";

document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('registroForm');
    
    if (!form) {
        console.error("No se encontró el formulario");
        return;
    }
    
    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        
        const name = document.getElementById('nombre').value;
        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;
        const confirmPassword = document.getElementById('confirmPassword').value;
        
        // Validaciones
        if (!name || !email || !password || !confirmPassword) {
            alert("Todos los campos son obligatorios");
            return;
        }
        
        if (password !== confirmPassword) {
            alert("Las contraseñas no coinciden");
            return;
        }
        
        if (password.length < 6) {
            alert("La contraseña debe tener al menos 6 caracteres");
            return;
        }
        
        try {
            // Verificar si el email ya existe
            const q = query(collection(db, "Usuarios_Administradores"), where("email", "==", email));
            const querySnapshot = await getDocs(q);
            
            if (!querySnapshot.empty) {
                alert("Este email ya está registrado");
                return;
            }
            
            // Guardar en Firestore
            const nuevoAdmin = {
                nombre: name,
                email: email,
                password: password,
                fechaRegistro: new Date()
            };
            
            await addDoc(collection(db, "Usuarios_Administradores"), nuevoAdmin);
            
            alert("¡Registro exitoso! Ahora inicia sesión");
            window.location.href = 'login.html';
            
        } catch (error) {
            console.error("Error:", error);
            alert("Error al registrar: " + error.message);
        }
    });
});