$(document).ready(function () {
    var height = $(document).height() - 150;
    var width = document.body.clientWidth;
    if(width < 760){
        height = height - 80;
    }
    var summernote = $('#summernote');
    summernote.summernote({
        focus:true,
        minHeight:height,
        maxHeight:height
    });
    $("[name=savebtn]").click(function () {
        var note = $('#summernote');
        if(note.summernote('isEmpty')){
            Dialog.show("请先编辑");
            return false;
        }
        var content = note.summernote("code");
        $("[name=content]").val(content);
        var preview = $("#preview");
        preview.html(content);
        var previewText = preview.text();
        if(previewText.length > 20){
            previewText = previewText.substring(0,20)+"...";
        }
        $("[name=title]").val(previewText);
        $("#noteForm").submit();
    });
});