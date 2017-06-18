$(document).ready(function () {
    $("#auth").submit(function (event) {
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
        $form.submit();
    });
});

function showDialog(text){
    var dialog = $("#dialog");
    dialog.text(text);
    dialog.removeClass("hidden");
}