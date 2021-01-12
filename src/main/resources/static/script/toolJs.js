//请求IP
function getUrl() {
    var url;
    url = 'http://39.106.80.203:8083';
    return url;
}

function show() {
    var loginElement = document.getElementById("loginInput");
    var userInfoElement = document.getElementById("username");
    var addLine = document.getElementById("myOnLine");

    var username = localStorage.getItem("userLoginName");
    var token = localStorage.getItem("token");

    if (!token) {
        loginElement.style.display = "block";
        userInfoElement.style.display = "none";
        addLine.style.display = "none";
    } else {
        loginElement.style.display = "none";
        userInfoElement.style.display = "block";
        addLine.style.display = "block";
    }
    return username;
}


function doctorShow() {
    var doctorName = localStorage.getItem("doctorLoginName");
    return doctorName;
}