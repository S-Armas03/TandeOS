// Import the functions you need from the SDKs you need
  import { initializeApp } from "https://www.gstatic.com/firebasejs/12.14.0/firebase-app.js";
  import { getFirestore } from "https://www.gstatic.com/firebasejs/12.14.0/firebase-firestore.js";
  // TODO: Add SDKs for Firebase products that you want to use
  // https://firebase.google.com/docs/web/setup#available-libraries

  // Your web app's Firebase configuration
  const firebaseConfig = {
    apiKey: "AIzaSyCiivg-OQC1bJV9FnjYohRyHXem6ZlQdYQ",
    authDomain: "tandeos.firebaseapp.com",
    projectId: "tandeos",
    storageBucket: "tandeos.firebasestorage.app",
    messagingSenderId: "73455913793",
    appId: "1:73455913793:web:e8e5e6703bae4dd28eafc0"
  };

  // Initialize Firebase
  const app = initializeApp(firebaseConfig);
  export const db = getFirestore(app);