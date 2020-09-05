
$(function () {

  var url = "/api/viewHistory";

  // Get data when first time open
  $.get(url, function (data, status) {
    if (status == 'success') {
      console.log(data);
      $(data).ready( function () {
        var table = $('#dataTable').DataTable({
          destroy: true,
          searching: true,
          data: data,
          columns: [
            // { data: '_id' },
            { data: 'ID' },
            { data: 'Title' },
            { data: 'Tags' },
            { data: 'Date' },
            { data: 'UserID' },
            { data: '_id' , render : function ( data, type, row, meta ) {
              return type === 'display'  ?
              '<botton onclick="Delete(`'+data+'`)" class="btn btn-danger" >Delete</botton>' : data;
            }},
          ]
        });
    } );
    }
});
});

  
function Delete(id){
  Model(id);
}

function Model(id){
  $('#confirmModal').modal('toggle');
  $("#confirmdelete").click(function () {
    console.log("delete "+id);
    // #15 Get a delete product and go back to product list 
    // use $.get and winidow.location.href
    $.ajax({
        url: '/api/viewHistory/'+id,
        type: 'DELETE',
        success: function (result) {
            window.location.href = "ViewHistory.html";
        }
    });
    // ===============================
});
}