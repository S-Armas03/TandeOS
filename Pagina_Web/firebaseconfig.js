  // Import the functions you need from the SDKs you need
  import { initializeApp } from "https://www.gstatic.com/firebasejs/12.13.0/firebase-app.js";
  // TODO: Add SDKs for Firebase products that you want to use
  // https://firebase.google.com/docs/web/setup#available-libraries

  // Your web app's Firebase configuration
  const firebaseConfig = {
    apiKey: "AIzaSyBp-juV4lwaWFdAsjW1QZEszCgS5mcNMSI",
    authDomain: "test-fcc28.firebaseapp.com",
    projectId: "test-fcc28",
    storageBucket: "test-fcc28.firebasestorage.app",
    messagingSenderId: "790219627967",
    appId: "1:790219627967:web:0705472c79b2779935950a"
  };

  // Initialize Firebase
  const app = initializeApp(firebaseConfig);

  import { doc, setDoc } from "firebase/firestore"; 

// Add a new document in collection "cities"
await setDoc(doc(db, "cities", "LA"), {
  name: "Los Angeles",
  state: "CA",
  country: "USA"
});