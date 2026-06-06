// login.js
document.getElementById('loginForm').addEventListener('submit', function(e) {
    e.preventDefault();
    
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const remember = document.getElementById('remember').checked;
    
    // Obtener usuario registrado
    const registeredUser = JSON.parse(localStorage.getItem('user'));
    
    // Validar credenciales
    if (!registeredUser) {
        alert('No hay cuenta registrada. Por favor regístrate primero.');
        return;
    }
    
    if (email === registeredUser.email && password === registeredUser.password) {
        // Login exitoso
        if (remember) {
            localStorage.setItem('isLoggedIn', 'true');
            localStorage.setItem('currentUser', JSON.stringify({
                name: registeredUser.name,
                email: registeredUser.email
            }));
        } else {
            sessionStorage.setItem('isLoggedIn', 'true');
            sessionStorage.setItem('currentUser', JSON.stringify({
                name: registeredUser.name,
                email: registeredUser.email
            }));
        }
        
        // Mostrar mensaje de éxito
        document.getElementById('successMessage').style.display = 'block';
        
        // Redirigir al index después de 2 segundos
        setTimeout(() => {
            window.location.href = '../index.html';
        }, 2000);
        
    } else {
        alert('Email o contraseña incorrectos');
    }
});

// Toggle password visibility
document.getElementById('passwordToggle').addEventListener('click', function() {
    const passwordInput = document.getElementById('password');
    const type = passwordInput.getAttribute('type') === 'password' ? 'text' : 'password';
    passwordInput.setAttribute('type', type);
    this.classList.toggle('active');
});