// dashboard.js
let currentUser = null;

// Verificar autenticación
firebase.auth().onAuthStateChanged(async (user) => {
    if (!user) {
        window.location.href = 'login-registro/login.html';
        return;
    }
    
    currentUser = user;
    
    // Mostrar información del usuario
    document.getElementById('userName').textContent = user.displayName || user.email.split('@')[0];
    document.getElementById('userEmail').textContent = user.email;
    document.getElementById('userAvatar').textContent = user.email.charAt(0).toUpperCase();
    
    // Cargar estadísticas
    await cargarEstadisticas();
    await cargarUltimosTandeos();
});

// Cargar estadísticas
async function cargarEstadisticas() {
    try {
        const tandeosRef = firebase.firestore().collection('tandeos');
        const snapshot = await tandeosRef.where('userId', '==', currentUser.uid).get();
        
        const tandeos = [];
        snapshot.forEach(doc => tandeos.push({ id: doc.id, ...doc.data() }));
        
        const total = tandeos.length;
        const activos = tandeos.filter(t => t.estado === 'activo').length;
        const pendientes = tandeos.filter(t => t.estado === 'pendiente').length;
        
        // Contar tandeos de hoy
        const hoy = new Date().toISOString().split('T')[0];
        const hoyCount = tandeos.filter(t => t.fecha === hoy).length;
        
        // Actualizar UI
        document.getElementById('totalTandeos').textContent = total;
        document.getElementById('tandeosActivos').textContent = activos;
        document.getElementById('tandeosPendientes').textContent = pendientes;
        document.getElementById('tandeosHoy').textContent = hoyCount;
        
    } catch (error) {
        console.error('Error al cargar estadísticas:', error);
    }
}

// Cargar últimos 5 tandeos
async function cargarUltimosTandeos() {
    try {
        const snapshot = await firebase.firestore()
            .collection('tandeos')
            .where('userId', '==', currentUser.uid)
            .orderBy('fechaCreacion', 'desc')
            .limit(5)
            .get();
        
        const tbody = document.getElementById('ultimosTandeos');
        
        if (snapshot.empty) {
            tbody.innerHTML = '<tr><td colspan="5" class="text-center">No hay tandeos registrados</td></tr>';
            return;
        }
        
        tbody.innerHTML = '';
        snapshot.forEach(doc => {
            const tandeo = doc.data();
            const row = tbody.insertRow();
            
            // Determinar clase del badge según estado
            let badgeClass = 'badge-success';
            if (tandeo.estado === 'pendiente') badgeClass = 'badge-warning';
            if (tandeo.estado === 'cancelado') badgeClass = 'badge-danger';
            
            row.innerHTML = `
                <td>${tandeo.nombreTandeo || 'Sin nombre'}</td>
                <td>${tandeo.fecha || 'N/A'}</td>
                <td>${tandeo.horaInicio || 'N/A'}</td>
                <td>${tandeo.ubicacion || 'N/A'}</td>
                <td><span class="badge ${badgeClass}">${tandeo.estado || 'activo'}</span></td>
            `;
        });
        
    } catch (error) {
        console.error('Error al cargar tandeos:', error);
        document.getElementById('ultimosTandeos').innerHTML = '<tr><td colspan="5" class="text-center">Error al cargar datos</td></tr>';
    }
}

// Cerrar sesión
document.getElementById('logoutBtn').addEventListener('click', async () => {
    try {
        await firebase.auth().signOut();
        window.location.href = 'login-registro/login.html';
    } catch (error) {
        console.error('Error al cerrar sesión:', error);
    }
});