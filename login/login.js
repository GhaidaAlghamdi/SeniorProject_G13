
function login() {

                var email = document.getElementById("email").value;
                var password = document.getElementById("pass").value;


                    firebase.auth().signInWithEmailAndPassword(email, password)
                  .then((userCredential) => {
                    // Signed in
                    var user = userCredential.user;
                    window.location.href = "http://www.w3schools.com";
                  })
                  .catch((error) => {
                    var errorCode = error.code;
                    var errorMessage = error.message;
                  });



}