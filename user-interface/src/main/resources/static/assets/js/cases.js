$('document').ready(function () {

    $('table #shop-btn').on('click', function (event) {
        event.preventDefault();

        let href = $(this).attr('href');

        $.get(href, function (shop, status) {

            console.log('SHopPPPPPP'+shop.id);
            $('#shopId').val(shop.id);
            $('#shopName').html(shop.name);

        })

        $('#addModal').modal();
    });


    /*
        $('table #deleteButton').on('click', function(event) {
            event.preventDefault();

            var href = $(this).attr('href');

            $('#confirmDeleteButton').attr('href', href);

            $('#deleteModal').modal();
        });
    */

    $('#acceptButton').on('click', function (event) {
        event.preventDefault();

        var href = $(this).attr('href');

        $('#confirmAcceptButton').attr('href', href);

        $('#acceptModal').modal();
    });

    $('table #photoButton').on('click', function (event) {
        event.preventDefault();

        var href = $(this).attr('href');
        $('#photoModal #beerPhoto').attr('src', href);
        $('#photoModal').modal();
    });

});