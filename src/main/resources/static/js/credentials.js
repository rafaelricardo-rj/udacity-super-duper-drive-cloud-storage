// For opening the credentials modal
function showCredentialModal(credentialId, url, username, password) {
    $('#credential-id').val(credentialId ? credentialId : '');
    $('#credential-url').val(url ? url : '');
    $('#credential-username').val(username ? username : '');
    $('#credential-password').val(password ? password : '');
    $('#credentialModal').modal('show');
}

const saveCredential = () => {
    // check if is to insert new credential or update a credential.
    let el = document.getElementById("credential-id");
    let str = el.value;
    let num = parseInt(str);
    let actionUrl = '';
    let method = '';
    let action = '';
    if(isNaN(num) || num == 0 || str == ''){
        // insert new note
        actionUrl = baseurl+'credential';
        method = 'POST';
        action = 'insert';
    } else if( num > 0){
        // update note
        actionUrl = `${baseurl}credential/${num}/update`;
        method = 'PATCH';
        action = 'update';
    }
    console.log(actionUrl);
    console.log(action);
    let url = $("#credential-url").val();
    let username = $("#credential-username").val();
    let password = $("#credential-password").val();
    let csrf = $('#credential-form input[name=_csrf]').val();

    if(url == ''){
        toastr["error"]('The field URL can not be blank');
    } else if (username == ''){
        toastr["error"]('The field Username can not be blank');
    } else if (password == ''){
              toastr["error"]('The field Password can not be blank');
    } else {
            $.post(actionUrl, {
                url: url,
                username: username,
                password: password,
                _csrf: csrf,
                _method: method
            },
             function call_back(response) {
              console.log(response);
              if(response.validated == true){
                $('#credentialModal').modal('hide');
                if(action == 'update'){
                    $(`#credentialRow-${num}`).remove();
                }
                appendNewCredential(response.credentialId, url, username, response.encryptedPassword);
              }
            }).fail(function (xhr, status, error){
                let responseError = JSON.parse(xhr.responseText);
                if(status == 'error'){
                    responseError.errors.map( e => toastr["error"](e.defaultMessage));
                }
                console.log(responseError.errors);
            });
    }
}

const appendNewCredential = (credentialId, url, username, encryptedPassword) => {
        const row = `
            <tr id=credentialRow-${credentialId} class="newCredentialTest">
                <td>
                    <button type="button" class="btn btn-success credential-open"
                        data-credential-id="${credentialId}"
                        data-credential-url="${url}"
                        data-credential-username="${username}"
                    >Edit</button>
                    <button class="btn btn-danger credential-delete"  data-credentialid="${credentialId}">Delete</button>
                </td>
                <th scope="row">${url}</th>
                <td>${username}</td>
                <td>${encryptedPassword}</td>
            </tr>
        `;

        $('#table-credentials').prepend(row);
}

$(document).on("click", ".credential-delete", function(event){

        const id = $(this).attr("data-credentialid");
        const csrf = $('#credential-form input[name=_csrf]').val();
        if(!isNaN(id)){
            $.post(`${baseurl}credential/${id}/delete`, {
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

$(document).on("click", ".credential-open", function(event){
        const button = $(this);

        const id = $(this).attr("data-credential-id");
        const csrf = $('#credential-form input[name=_csrf]').val();

        const url = $(this).attr("data-credential-url");
        const username = $(this).attr("data-credential-username");

        if(!isNaN(id)){
            $('.credential-open').attr("disabled","disabled");
            $(this).html("......");//loading
            $.post(`${baseurl}credential/${id}/fetch`, {
                _csrf: csrf,
                _method: 'POST'
            },
            function call_back(response) {
                $(".credential-open").html("Edit");
                if(response.validated == true){
                    $('.credential-open').removeAttr("disabled");
                    const id = response.credentialId;
                    const password = response.decryptedPassword;
                    showCredentialModal(id, url, username, password);
                }
            }).fail(function (xhr, status, error){
                $(".credential-open").html("Edit");
                let responseError = JSON.parse(xhr.responseText);
                if(status == 'error'){
                    console.log(responseError.errors);
                }
                $('.credential-open').removeAttr("disabled");
            });
        }
 });