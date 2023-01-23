/*===== MENU SHOW Y HIDDEN =====*/



const navMenu = document.getElementById('nav-menu'),
    toggleMenu = document.getElementById('nav-toggle'),
    closeMenu = document.getElementById('nav-close')

// SHOW
toggleMenu.addEventListener('click', ()=>{
    navMenu.classList.toggle('show')
})

// HIDDEN
closeMenu.addEventListener('click', ()=>{
    navMenu.classList.remove('show')
})

/*===== MOUSEMOVE HOME IMG =====*/
document.addEventListener('mousemove', move);
function move(e){
    this.querySelectorAll('.move').forEach(layer =>{
        const speed = layer.getAttribute('data-speed')

        const x = (window.innerWidth - e.pageX*speed)/120
        const y = (window.innerHeight - e.pageY*speed)/120

        layer.style.transform = `translateX(${x}px) translateY(${y}px)`
    })
}

/*===== GSAP ANIMATION =====*/
// NAV
gsap.from('.nav__logo, .nav__toggle', {opacity: 0, duration: 1, delay:2, y: 10})
gsap.from('.nav__item', {opacity: 0, duration: 1, delay: 2.1, y: 30, stagger: 0.2,})

// HOME
gsap.from('.home__title', {opacity: 0, duration: 1, delay:1.6, y: 30})
gsap.from('.home__description', {opacity: 0, duration: 1, delay:1.8, y: 30})
gsap.from('.home__button', {opacity: 0, duration: 1, delay:2.1, y: 30})
gsap.from('.home__img', {opacity: 0, duration: 1, delay:1.3, y: 30})

// // Import the functions you need from the SDKs you need
//   import { initializeApp } from "https://www.gstatic.com/firebasejs/9.15.0/firebase-app.js";
//   // TODO: Add SDKs for Firebase products that you want to use
//   // https://firebase.google.com/docs/web/setup#available-libraries

//   // Your web app's Firebase configuration
//   const firebaseConfig = {
//     apiKey: "AIzaSyA7owPqbAzwMchDqPcIRcYUOOxbkwH_8TI",
//     authDomain: "nodhem-1.firebaseapp.com",
//     databaseURL: "https://nodhem-1-default-rtdb.firebaseio.com",
//     projectId: "nodhem-1",
//     storageBucket: "nodhem-1.appspot.com",
//     messagingSenderId: "961135619621",
//     appId: "1:961135619621:web:b65bcafb3fc4a6c082d9cc"
//   };

//   // Initialize Firebase
//   const app = initializeApp(firebaseConfig);

//   import{
//   getDatabase,ref,set,child,update,remove}
//   from "https://www.gstatic.com/firebasejs/9.15.0/firebase-database.js";
//   const db = getDatabase();
//     var idbox = document.getElementById("id");
//     var namebox = document.getElementById("name");
//     var email = document.getElementById("email");
//     var password = document.getElementById("pass");
//     var phonenum = document.getElementById("phone");

//     var insBtn=document.getElementById("insBtn");

//     function insertdata(){
//     set(ref(db,"TheParent/"+rollbox.value),{
//     parentid:idbox.value,
//     nameofparent:namebox.value,
//     parentemail:email.value,
//     phoneNumber:phonenum.value,
//     passParent:password.value
//     })

//     .then(()=>{
//     alert("data added");
//     })
//     .catch((error)=>{
//     alert("problem occurd");}


//     });



//  }

//  insBtn.addEventListener("click",insertdata);
