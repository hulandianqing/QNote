$(document).ready(function () {
    $("#auth").submit(function (event) {
        event.preventDefault();

        var $form = $(this);
        var username = $form.find('input[name="username"]').val();
        var pwd = $form.find('input[name="password"]').val();
        if(!username){
            showDialog("请输入用户名");
            return;
        }
        if(!pwd){
            showDialog("请输入密码");
            return;
        }
        $.ajax({
            url: "/login",
            type: "post",
            data: {
                username: username,
                password: pwd
            },
            success: function (data, textStatus, jqXHR) {
                window.location.reload();
            },
            error: function (jqXHR, textStatus, errorThrown) {
                if (jqXHR.status === 401) {
                    if(jqXHR.responseJSON.message){
                        showDialog(jqXHR.responseJSON.message)
                    }else{
                        showDialog("账号或密码错误");
                    }
                } else {
                    throw new Error("an unexpected error occured: " + errorThrown);
                }
            }
        });
    });
});

function showDialog(text){
    var dialog = $("#dialog");
    dialog.text(text);
    dialog.removeClass("hidden");
}