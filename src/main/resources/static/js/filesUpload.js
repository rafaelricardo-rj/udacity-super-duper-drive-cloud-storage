$(document).on("click", ".file-delete", function(event){

        const id = $(this).attr("data-file-id");
        const csrf = $('#file-form input[name=_csrf]').val();
        if(!isNaN(id)){
            $.post(`${baseurl}file/${id}/delete`, {
                _csrf: csrf,
                _method: 'DELETE'
            },
            function call_back(response) {
                //console.log(response);
                if(response.validated == true){
                    //console.log('deletado')
                    toastr["success"]("File deleted.")
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
        success: function (response) {
            if(response.validated == true){
                toastr["success"]("File uploaded successfully!")
                appendNewFile(response.fileId, response.url, response.filename);
            } else if(response.validated == false){
                response.errorMessages.map( e => toastr["error"](e));
                console.log(response.errorMessages);
            }
            $('#file-form').trigger("reset");
        },
        error: function (status) {
            //let responseError = JSON.parse(xhr.responseText);
            if(status == 'error'){
                responseError.errors.map( e => toastr["error"](e.defaultMessage));
                toastr["error"]("Ops! Something wrong happened!");
            }
        }
    });
}

const appendNewFile = (fileId, url, filename) => {
        const row = `
            <tr id=fileRow-${fileId}>
                <td>
                    <a href="${url}" target="_blank" class="btn btn-success">View</a>
                    <button class="btn btn-danger file-delete"  data-file-id="${fileId}">Delete</button>
                </td>
                <th scope="row">${filename}</th>
            </tr>
        `;

        $('#table-files').prepend(row);
}