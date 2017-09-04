$(document).ready(function () {
    $("[name=delete]").click(function (event) {
        event.preventDefault();
        var target = $(this).parent().parent();
        var data_target = target.attr("data-target");
        if(!target){
            Dialog.show("删除失败",function(){window.location.reload()});
            return;
        }
        $.ajax({
            url: "/qnote/delete/"+data_target,
            type: "POST",
            data: {},
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                var rs = eval(data);
                if(rs.state == 'success'){
                    target.parent().remove();
                }else{
                    if(!rs.message){
                        rs.message = "删除失败";
                    }
                    Dialog.show(rs.message,function(){window.location.reload()});
                }
            },
            error: function (err, textStatus, errorThrown) {
                Dialog.show("删除失败",function(){window.location.reload()});
            }
        });
    });
});