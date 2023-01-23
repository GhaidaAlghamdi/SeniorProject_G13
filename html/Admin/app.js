

qrcode = new QRCode(document.getElementById("qrcode"), {
    text: "نظم",
    width: 200,
    height: 200,
    colorDark: "#000000",
    colorLight: "#ffffff",
    correctLevel: QRCode.CorrectLevel.H
}),
    generateButton = document.getElementById("generate-button");

generateButton.addEventListener("click", function (e) {
    e.preventDefault();


    qrcode.makeCode(localStorage['userId']);

    if (data == "") {
        alert("You Cannot Leave Fields Empty!");
    }
});



let downloadButton = document.getElementById("download-button"),
    qrCanvas = document.querySelector("canvas"),
    generatedQrCode = document.querySelector("img");

downloadButton.addEventListener("click", function (e) {
    e.preventDefault();
 
        const dataURI = qrCanvas.toDataURL("image / png");
        generatedQrCode.src = dataURI;
        // For Microsoft Edge or Old Browser Only
        if (window.navigator.msSaveBlob) {
            window.navigator.msSaveBlob(qrCanvas.msToBlob(), "qr-code.png")
        } else {
            const a = document.createElement("a");
            document.body.appendChild(a);
            a.href = qrCanvas.toDataURL();
            a.download = "qr-code.png";
            a.click();
            document.body.removeChild(a);
        }
    
});
