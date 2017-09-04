var Dialog = {
    getModal: function () {
        var modal = $("[name=ALERT]");
        if (modal.length == 0) {
            var modalHTML = "<div class='modal fade bs-example-modal-sm' name='ALERT' tabindex='-1' role='dialog' aria-labelledby='modalLabel'>"
                + "<div class='modal-dialog modal-sm' role='document'>"
                + "<div class='modal-content'>" +
                "<div class='modal-body'>"
                + "</div>"
                + "<div class='modal-footer'>"
                + "<button type='button' name='btn-primary' class='btn btn-primary'>Close</button>"
                + "</div>"
                + "  </div>"
                + "</div>"
                + "</div>'";
            $("body").append(modalHTML);
            modal = $("[name=ALERT]");
        }
        return modal;
    },
    show: function (text,callback) {
        var modal = this.getModal();
        modal.find("[class=modal-body]").html(text);
        var btn = modal.find("[name=btn-primary]");
        if(btn.length > 0){
            if(callback){
                btn.click(function(){
                    try {
                        callback();
                    } finally {
                        modal.modal('hide');
                    }
                });
            }else{
                btn.click(function(){});
            }
        }
        modal.modal('show');
    }
}