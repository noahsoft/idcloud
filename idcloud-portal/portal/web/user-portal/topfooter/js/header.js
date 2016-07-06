function checkHelp(n) {
    for (var i = 1; i < 100; i++) {
        if ($("#hptt" + i).length > 0) {
            $("#hptt" + i).removeClass("nav_selected");
            $("#hp" + i).hide();
        } else {
            break;
        }
    }
    $("#hptt" + n).addClass("nav_selected");
    $("#hp" + n).show();
}
function omover() {
    var divlist = document.getElementById("lishistory");
    divlist.style.display = "block";
}
function omout() {
    var divlist = document.getElementById("lishistory");
    divlist.style.display = "none";
}
$(function () {
    var publiccontent = "";
    publiccontent += '<div class="quick_btn01">';
    publiccontent += '<div><a href="javascript:void(0);" class="icon_03" id="BizQQWPA1"></a></div>';
    publiccontent += '<div><a href="#" class="icon_04"></a></div>';
    publiccontent += '</div>';
    $("body").append(publiccontent);
});
// 首先获取系统是否加入了服务数量限制
//var flag = "";
//Core.AjaxRequest({
//    url : ws_url + "/rest/configs",
//    async : false,
//    showTimeout:false,
//    params:{
//        configKey:"service.limit.mode"
//    },
//    callback : function (data) {
//        flag = data[0].configValue;
//    }
//});

// 获取服务目录
Core.AjaxRequest({
     url: ws_url + "/rest/services/serviceTree",
     type: "post",
     async: false,
     showWaiting:false,
     callback: function (data) {
         createTopNavServiceLis(data);
         topmsg();
     }
 });

function createTopNavServiceLis(data) {
    var index = 1;
    var colIndex = 0;
    var str = "";
    for (var i = 0; i < data.length; i++) {
        if (data[i].serviceDefineList === "" && data[i].catalogSid == 101) {
        } else {
            str += "<div class='second_menu_txt' style='float: left;margin-left: 32px;'>";
            str += "<h2 style='cursor:pointer; color:#6f6f6f;font-size:14px;"
                   + "border-bottom: 1px solid #d6d6d6;margin-bottom: 15px;font-weight: 700;'>"
                   + data[i].catalogName + "</h2><ul>";
            if (data[i].serviceDefineList.length === 0) {
                str += "<li ><a style='color: grey' href='javascript:void(0)'>暂未开放</a></li>";
            } else {
                for (var j = 0; j < data[i].serviceDefineList.length; j++) {
                    str += "<li ><a style='cursor:pointer' "
                           + "onclick='viewServiceDetailInfo(" +
                           data[i].serviceDefineList[j].serviceSid + ","
                           + data[i].serviceDefineList[j].parentCatalogSid + ")'> "
                           + data[i].serviceDefineList[j].serviceName + "</a></li>";
                }
            }
            str += "</ul></div>";
            index++;
            colIndex++;
        }
    }
    $("#serverUlDiv").append(str);
}

function createTopNavServiceTds(data) {
    var index = 1;
    var colIndex = 0;
    var str = "";
    for (var i = 0; i < data.length; i++) {
        if (data[i].serviceDefineList === "" && data[i].catalogSid == 100) {
        } else {
            str += "<td>";
            str += "<div id='number" + index + "' class='number'>";
            str += "<div id='number" + index + "_content' class='number_content') no-repeat'>";
            str +=
                "<h3 style='width:100%;height:32px; font-size:16px; line-height:37px; font-weight:700;margin:0px;padding:0px;''>"
                + data[i].catalogName + "</h3>";
            str +=
                "<ul style='text-align:left;font-size:14px;margin:0px;padding:0px;list-style:none'>";
            if (data[i].serviceDefineList === "") {
                str +=
                    "<li style='width:100%;height:100px;'><br/><br/><font style='margin-top:50px;margin-left:85px;'>暂未开放</font></li>";
            } else {
                for (var j = 0; j < data[i].serviceDefineList.length; j++) {
                    str +=
                        "<li class='li' onclick='viewServiceDetailInfo("
                        + data[i].serviceDefineList[j].serviceSid + ","
                        + data[i].serviceDefineList[j].parentCatalogSid
                        + ")''>&nbsp;&nbsp;<span style='position:absolute;left:45px;top:5px;'>"
                        + data[i].serviceDefineList[j].serviceName + "</span></li>";
                    if (j == 5) {
                        break;
                    }
                }
            }
            str +=
                "</div><div id='bgc" + index + "' class='bgc'  style='background:" + color[colIndex]
                + ";''><img src='${ctx}" + data[i].imagePath
                + "' width='230' height='230' onclick='mouseover(" + index + ")'/></div>";
            str += "</div></td>";
            index++;
            colIndex++;
        }
    }
    $("#trContainer").append(str);
}

// 查看服务的详细信息&productParentSid="+data[i].serviceDefineList[j].parentCatalogSid+"
function viewServiceDetailInfo(serviceSid, parentSid) {
    if (typeof(serviceSid) == 'number' && typeof(parentSid) == 'number') {
        var str = "productSid=" + serviceSid + "&productParentSid=" + parentSid;
        window.location = "product.html?" + str;
    }
}

//  		$("#feedback").click(function(){
//  			window.location.href = ctx + "/pages/feedback.jsp";
//  		});
var preIndex;
function mouseover(index) {
    if (navigator.userAgent.indexOf("MSIE") <= 0) {
        return;
    }
    $("#bgc" + index + "").animate({top: "-240px"}, "fast", function () {
        $("#bgc" + preIndex + "").animate({top: "0px"}, "fast");
        preIndex = index;
    });
}

// 退出
function logout() {
    Core.AjaxRequest({
                         url: ws_url + "/rest/user/logout",
                         type: "POST",
                         callback: function (data) {
                             if (data.resultStatus == "true") {
                                 window.location.href = ctx + "/pages/login.jsp";
                             }
                         }
                     });
}

function showMsgDiv(obj) {
    $(obj).next("div").css("display", "block");
}

function hideMsgDiv(obj) {
    $(obj).next("div").css("display", "none");
}

function backToTop() {
    document.documentElement.scrollTop = document.body.scrollTop = 0;
}
var currentUser = "";
function topmsg() {
    //function setCurrentUser() {
    if ($.cookie('USER_SID') == 'undefined' || $.cookie('USER_SID') === '' || $.cookie('USER_SID')
                                                                              == null) {

    } else {
        Core.AjaxRequest({
             url: ws_url + "/rest/user/current/" + $.cookie('USER_SID'),
             type: "GET",
             async: false,
             showWaiting: false,
             callback: function (data) {
                 currentUser = data;
             }
         });
    }

    if (currentUser != null && currentUser !== '' && currentUser !== "") {
        if (currentUser.serviceLimitQuantity != null && currentUser.serviceLimitQuantity > 0) {
            $("#usermsg").append(Core.sayHello() + currentUser.realName);
        } else {
            $("#usermsg").append(
                '<span style="color: #cecece;">' + Core.sayHello() + currentUser.realName
                + '</span>');
        }
    } else {
        $("#usermsg").append('<a href="login.html" id="login" >登录</a>');
        $("#usermsg").append('<span style="color: grey">&nbsp;&nbsp;|&nbsp;&nbsp;</span><a'
                             + ' href="resetpwd.html">忘记密码</a>');
    }
    if (currentUser != null && currentUser !== '' && currentUser !== "") {
        $("#topmsg").append('<a onclick="logingout()" href="#" id="logout">退出</a>');
    } else {
        var registerPage = "company-register.jsp";
        $("#topmsg").append('<a href="register.html">注册</a>');
    }
}
function logingout() {
    $.removeCookie('IDC_TOKEN', {path: "/"});
    $.removeCookie('USER_SID', {path: "/"});
    window.location.href = "index.html";
}

//if (currentUser != null && currentUser != '' && currentUser != "") {
//                    if(currentUser.serviceLimitQuantity != null &&
// currentUser.serviceLimitQuantity > 0 && flag == "true"){ document.write(Core.sayHello() +
// currentUser.realName + '（<span
// style="color:#ff9900">您暂时最多能选购'+currentUser.serviceLimitQuantity+'个云服务</span>）'); }else{
// document.write('<span style="color: #cecece;">'+ currentUser.realName + '</span>'); }

//                } else {
//                    document.write('<a href="javascript:goLoginPage();" id="login" ><fmt:message
// key="header.login"/></a>'); document.write('&nbsp;|&nbsp;<a href="' + ctx +
// '/pages/register/lost-password.jsp"><fmt:message key="login.forgetPassword"/></a>'); }