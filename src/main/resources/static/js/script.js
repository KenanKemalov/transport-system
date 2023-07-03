// $('#example').DataTable();


$(document).ready(function () {
    $('#example').DataTable({
        "paging": true,
        "lengthChange": false,
        "searching": false,
        "ordering": true,
        "info": true,
        "autoWidth": false,
        "pageLength": 5 // Set the number of items per page here
    });
    $('#example10').DataTable({
        "paging": true,
        "lengthChange": true,
        "searching": false,
        "ordering": true,
        "info": true,
        "autoWidth": false,
        "pageLength": 10 // Set the number of items per page here
    });
    // $('#example').DataTable();
    // $('.dataTables_length').addClass('bs-select');
});