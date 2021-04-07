// For opening the note modal
function showNoteModal(noteId, noteTitle, noteDescription) {
    $('#note-id').val(noteId ? noteId : '');
    $('#note-title').val(noteTitle ? noteTitle : '');
    $('#note-description').val(noteDescription ? noteDescription : '');
    $('#noteModal').modal('show');
}

const saveNote = () => {
    // check if is to insert new note or update a note.
    let el = document.getElementById("note-id");
    let str = el.value;
    let num = parseInt(str);
    let url = '';
    let method = '';
    let action = '';
    if(isNaN(num) || num == 0 || str == ''){
        // insert new note
        url = baseurl+'note';
        method = 'POST';
        action = 'insert';
    } else if( num > 0){
        // update note
        url = `${baseurl}note/${num}/update`;
        console.log(url);
        method = 'PATCH';
        action = 'update';
    }
    console.log(action);
    let title = $("#note-title").val();
    let description = $("#note-description").val();
    let csrf = $('#note-form input[name=_csrf]').val();

    if(title == ''){
        toastr["error"]('The field Title can not be blank');
    } else if (description == ''){
        toastr["error"]('The field Description can not be blank');
    } else {
        $.post(url, {
            notetitle: title,
            notedescription: description,
            _csrf: csrf,
            _method: method
        },
         function call_back(response) {
          //console.log(response);
          if(response.validated == true){
            $('#noteModal').modal('hide');
            if(action == 'update'){
                $(`#noteRow-${num}`).remove();
            }
            toastr["success"]("Note Saved!")
            appendNewNote(response.noteId, title, description);
            $('#noteModal').modal('hide');
          } else {
            $('#noteModal').modal('hide');
            toastr["error"](response.errorMessages.error)
          }

        }).fail(function (xhr, status, error){
            //console.log(xhr);
            //let responseError = JSON.parse(xhr.responseText);
            if(status == 'error'){
                responseError.errors.map( e => toastr["error"](e.defaultMessage));
                toastr["error"]("Ops! Something wrong happened!")
            }
            //console.log(responseError.errors);
        });
    }
}

const appendNewNote = (noteId, title, description) => {
        const row = `
            <tr id=noteRow-${noteId} class="newNoteTest">
                <td>
                    <button type="button" class="btn btn-success note-edit"
                        data-noteid="${noteId}"
                        data-note-title="${title}"
                        data-note-description="${description}">
                        Edit
                    </button>
                    <button class="btn btn-danger note-delete"  data-noteid="${noteId}">Delete</button>
                </td>
                <th scope="row">${title}</th>
                <td>${description}</td>
            </tr>
        `;

        $('#table-notes').prepend(row);
}

$(document).on("click", ".note-delete", function(event){

        const id = $(this).attr("data-noteid");
        const csrf = $('#note-form input[name=_csrf]').val();
        if(!isNaN(id)){
            $.post(`${baseurl}note/${id}/delete`, {
                _csrf: csrf,
                _method: 'DELETE'
            },
            function call_back(response) {
                //console.log(response.validated);
                if(response.validated == true){
                    //console.log('deletado')
                    toastr["success"]("Note deleted.")
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

 $(document).on("click", ".note-edit", function(event){

    const id = $(this).attr("data-noteid");
    const title = $(this).attr("data-note-title");
    const description = $(this).attr("data-note-description");

    showNoteModal(id, title, description);

  });