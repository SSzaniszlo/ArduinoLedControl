function sendRGBData() {
    
  const red = document.getElementById("red").value;

  console.log(red);

  const green = document.getElementById("green").value;
  const blue = document.getElementById("blue").value; 

  const dataString = red.toString() + ":" + green.toString() + ":" + blue.toString();
    
  
  const xhr = new XMLHttpRequest();

    xhr.open("POST", "/", true);
    xhr.setRequestHeader("Content-Type", "data");

    xhr.onreadystatechange = () => {
      if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
        alert("Success!: " + xhr.responseText)
      }
    };

    xhr.send(dataString);
}