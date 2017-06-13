$(document).ready(function () {
    var height = $(document).height() - 150;
    $('#summernote').summernote({
        focus:true,
        minHeight:height,
    });
    $("#savebtn").click(function () {
        var note = $('#summernote');
        if(note.summernote('isEmpty')){
            $(this).popover('show');
            return false;
        }
        $(this).popover('hide');
        $("#note_text").val(note.summernote("code"));
        $("#noteForm").submit();
    });
});