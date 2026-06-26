/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */

function validarCampos(){
    const email = document.getElementById("email");
    const senha = document.getElementById("senha");
    const btn = document.getElementById("btn-logar");
    
    if (email.value > 0&& senha.value.length > 0){
       btn.disabled = false;
    }
    
}

