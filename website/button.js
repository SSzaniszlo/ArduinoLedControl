document.getElementById("stopScript").addEventListener("click", stopScriptManager);

function stopScriptManager() {
    const xhr = new XMLHttpRequest();

    xhr.open("POST", "/", true);
    xhr.setRequestHeader("Content-Type", "text");
    
    xhr.onreadystatechange = () => {
      if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
        alert("Success!: " + xhr.responseText)
      }
    };

    xhr.send("stop");
}