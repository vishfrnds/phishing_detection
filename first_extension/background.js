chrome.tabs.onUpdated.addListener(function(tabId, changeInfo, tab) {
    var tablink = tab.url;
    var popups = chrome.extension.getViews({type: "popup"});
    if (0 < popups.length)
        popups[0].variable= 42;
});

chrome.tabs.onCreated.addListener(function(tabId, changeInfo, tab) {         
   var tablink = tab.url;
   var popups = chrome.extension.getViews({type: "popup"});
});