//chrome.tabs.onUpdated.addListener(function(tabId, changeInfo, tab){
// document.addEventListener('DOMContentLoaded', function () {
function onWindowLoad() {
    message = document.getElementById("result");
  chrome.tabs.executeScript(null, {
    file: "getPagesSource.js"
  }, function() {
    // If you try and inject into an extensions page or the webstore/NTP you'll get an error
    if (chrome.runtime.lastError) {
      message.innerText = 'There was an error injecting script : \n' + chrome.runtime.lastError.message;
    }
  });

}
window.onload = onWindowLoad;

chrome.runtime.onMessage.addListener(function(request, sender) {
  if (request.action == "getSource") {
      chrome.tabs.getSelected(null, function (tab) {
            var tablink = tab.url;
          	var xmlhttp;
            if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
                xmlhttp = new XMLHttpRequest();
            }
            else {// code for IE6, IE5
                xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
            }
            var url = "http://localhost:8080/anti_phishing_response/calc";
            var method = "POST";
            var postData = encodeURIComponent(request.source);
            tablink = encodeURIComponent(tablink);
            var async = true;
            xmlhttp.open(method, url, async);

            xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-16");
            xmlhttp.send("webPage="+postData+"&url=" + tablink);
          xmlhttp.onreadystatechange = function () {
              document.getElementById("result").innerHTML = "done"+xmlhttp.responseText;
              
          }
        });

  }
});

