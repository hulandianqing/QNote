$(document).ready(function () {
    $("#viewtoedit").click(function () {
        var html = $("#content_view").html();
        $("[name=content]").val(html);
        $("#toeditForm").submit();
    });
});