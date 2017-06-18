$(document).ready(function () {
    var height = $(document).height() - 150;
    $('#summernote').summernote({
        focus:true,
        minHeight:height
    });
    $("#savebtn").click(function () {
        var note = $('#summernote');
        if(note.summernote('isEmpty')){
            Dialog.show("请先编辑");
            return false;
        }
        var content = note.summernote("code");
        var preview = $("#preview");
        $("#note_text").val(content);
        preview.html(content);
        var previewText = preview.text();
        if(previewText){
            if(previewText.length > 20){
                previewText = previewText.substring(0,20)+"...";
            }
        }else{
            previewText = "";
        }
        $("[name=title]").val(previewText);
        $("#noteForm").submit();
    });
});