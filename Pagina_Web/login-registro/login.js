import { db } from '../firebase-config.js';
import { collection, getDocs, query, where } from "https://www.gstatic.com/firebasejs/12.14.0/firebase-firestore.js";

// Admin por defecto
const DEFAULT_ADMIN = {
    email: "admin@tandeos.com",
    password: "admin123",
    nombre: "Administrador"
};

document.getElementById('loginForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const remember = document.getElementById('remember').checked;
    
    // Verificar admin por defecto
    if (email === DEFAULT_ADMIN.email && password === DEFAULT_ADMIN.password) {
        const sessionData = {
            email: DEFAULT_ADMIN.email,
            nombre: DEFAULT_ADMIN.nombre,
            loggedIn: true
        };
        
        if (remember) {
            localStorage.setItem('tandeoSession', JSON.stringify(sessionData));
        } else {
            sessionStorage.setItem('tandeoSession', JSON.stringify(sessionData));
        }
        
        alert("Bienvenido Administrador");
        window.location.href = '../index.html';
        return;
    }
    
    // Buscar en Firestore
    try {
        const q = query(collection(db, "Usuarios_Administradores"), where("email", "==", email));
        const querySnapshot = await getDocs(q);
        
        if (!querySnapshot.empty) {
            const userData = querySnapshot.docs[0].data();
            
            if (userData.password === password) {
                const sessionData = {
                    email: email,
                    nombre: userData.nombre,
                    loggedIn: true
                };
                
                if (remember) {
                    localStorage.setItem('tandeoSession', JSON.stringify(sessionData));
                } else {
                    sessionStorage.setItem('tandeoSession', JSON.stringify(sessionData));
                }
                
                alert("Bienvenido " + userData.nombre);
                window.location.href = '../index.html';
            } else {
                alert("Contraseña incorrecta");
            }
        } else {
            alert("Usuario no encontrado");
        }
        
    } catch (error) {
        console.error("Error:", error);
        alert("Error de conexión: " + error.message);
    }
});