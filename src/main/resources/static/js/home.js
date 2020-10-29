const baseurl = "http://localhost:8080/";
// For opening the note modal
function showNoteModal(noteId, noteTitle, noteDescription) {
    $('#note-id').val(noteId ? noteId : '');
    $('#note-title').val(noteTitle ? noteTitle : '');
    $('#note-description').val(noteDescription ? noteDescription : '');
    $('#noteModal').modal('show');
}

// For opening the credentials modal
function showCredentialModal(credentialId, url, username, password) {
    $('#credential-id').val(credentialId ? credentialId : '');
    $('#credential-url').val(url ? url : '');
    $('#credential-username').val(username ? username : '');
    $('#credential-password').val(password ? password : '');
    $('#credentialModal').modal('show');
}

const saveNote = () => {
    let title = $("#note-title").val();
    let description = $("#note-description").val();
    let csrf = $('#note-form input[name=_csrf]').val();
    $.post(baseurl+'note', {
        notetitle: title,
        notedescription: description,
        _csrf: csrf
     },
     function call_back(response) {
      console.log(response);
      /*if(data == 'salvo'){
          $('#modal-delete-file').modal('hide');
          $(`#file_${idFile}`).remove();
      } else {
          result.error.map( e => toastr["error"](e));
      }*/
    });

}