// registro.js
document.getElementById('registroForm').addEventListener('submit', function(e) {
    e.preventDefault();
    
    // Obtener valores
    const name = document.getElementById('name').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirm-password').value;
    
    // Validaciones básicas
    if (password !== confirmPassword) {
        alert('Las contraseñas no coinciden');
        return;
    }
    
    if (password.length < 6) {
        alert('La contraseña debe tener al menos 6 caracteres');
        return;
    }
    
    // Guardar usuario en localStorage
    const userData = {
        name: name,
        email: email,
        password: password
    };
    
    localStorage.setItem('user', JSON.stringify(userData));
    alert('Registro exitoso! Ahora inicia sesión');
    
    // Redirigir al login
    window.location.href = 'login.html';
});