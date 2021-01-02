$(document).on("click", ".file-delete", function(event){

        const id = $(this).attr("data-file-id");
        const csrf = $('#file-form input[name=_csrf]').val();
        if(!isNaN(id)){
            $.post(`${baseurl}file/${id}/delete`, {
                _csrf: csrf,
                _method: 'DELETE'
            },
            function call_back(response) {
                console.log(response);
                if(response.validated == true){
                    console.log('deletado')
                    $( event.target ).closest( "tr" ).remove();
                }
            }).fail(function (xhr, status, error){
                let responseError = JSON.parse(xhr.responseText);
                if(status == 'error'){
                    console.log(responseError.errors);
                }
            });
        }
 });

const uploadFile = () => {
    // Get form
    const form = $('#file-form')[0];
    // Create an FormData object
    const data = new FormData(form);
    // use $.ajax() to upload file
    $.ajax({
        url: `${baseurl}file`,
        type: "POST",
        data: data,
        enctype: 'multipart/form-data',
        processData: false,
        contentType: false,
        cache: false,
        success: function (res) {
            console.log(res);
        },
        error: function (err) {
            console.error(err);
        }
    });
}