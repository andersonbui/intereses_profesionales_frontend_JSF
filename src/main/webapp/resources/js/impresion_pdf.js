/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



function generarpdf() {
    var printContents = document.getElementById('EstadisticaForm').innerHTML;

    var mywindow = window.open("", "Resultados del uso de la plataforma", "height=400,width=600");
    mywindow.document.write("<html><head><title>Resultados del uso de la plataforma</title></html></head>");
//      /*optional stylesheet*/ //mywindow.document.write("<link rel="stylesheet" href="main.css" type="text/css" />");
    mywindow.document.write(document.head.innerHTML);
    mywindow.document.write("</head><body>");
    mywindow.document.write(printContents);
    mywindow.document.write("</body></html>");
    mywindow.document.close(); // necessary for IE >= 10
    mywindow.focus(); // necessary for IE >= 10
    
    let onload = function() {        
        if (navigator.appName === 'Microsoft Internet Explorer'){
            window.print();
        }else{
            mywindow.print();
        }
        mywindow.close();
    }
    try{
        mywindow.onload = onload;
    }catch (exception) {
        window.setTimeout(onload, 3000);
    }

    return true;
}