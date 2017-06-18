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
                + "<button type='button' class='btn btn-primary' data-dismiss='modal'>Close</button>"
                + "</div>"
                + "  </div>"
                + "</div>"
                + "</div>'";
            $("body").append(modalHTML);
            modal = $("[name=ALERT]");
        }
        return modal;
    },
    show: function (text) {
        var modal = this.getModal();
        modal.find("[class=modal-body]").html(text);
        modal.modal('show');
    }
}