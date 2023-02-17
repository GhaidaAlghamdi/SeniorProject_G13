import { initializeApp } from "https://www.gstatic.com/firebasejs/9.14.0/firebase-app.js";
import { getDatabase, ref, child, set, get } from "https://www.gstatic.com/firebasejs/9.14.0/firebase-database.js";

// Your web app's Firebase configuration
const firebaseConfig = {
    apiKey: "AIzaSyB6Vef9JnY3xB0GL2PwE_HbXLgt3HeOAfw",
    authDomain: "authentication-7b4e2.firebaseapp.com",
    databaseURL: "https://authentication-7b4e2-default-rtdb.firebaseio.com",
    projectId: "authentication-7b4e2",
    storageBucket: "authentication-7b4e2.appspot.com",
    messagingSenderId: "25287000851",
    appId: "1:25287000851:web:cf131084c87dbd757c10e6"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
const db = getDatabase(app);
document.addEventListener("load", function () {
   alert("Welocoime");
   // getParents();
});

function getParents() {
    alert("Test");
    const dbRef = ref(getDatabase());
    get(child(dbRef, `Parent`)).then((snapshot) => {
        if (snapshot.exists()) {

            snapshot.forEach((childSnapshot) => {
                var key = childSnapshot.key;
                var data = childSnapshot.val();

                document.getElementById('tdBody').innerHTML += '<tr><td>"' + data["name"] + '"</td><td>Salazar Rayo</td><td><span class="status green"></span> Bueno </td></tr>';
            });
        } else {
            console.log("No data available");
        }
    }).catch((error) => {
        console.error(error);
    });
}


/*
                  <tr>
                      <td>Jhon Deiby</td>
                      <td>Salazar Rayo</td>
                      <td>
                          <span class="status green"></span> Bueno
                      </td>
                  </tr>
                  <tr>
                      <td>Victor Manuel</td>
                      <td>Ciro Ledesma</td>
                      <td>
                          <span class="status red"></span> Malo
                      </td>
                  </tr>


*/