$(document).ready(function () {
    // Sort table by column index and order
    function sortTable(columnIndex, sortOrder) {
        var tableRows = $('tbody tr');
        tableRows.sort(function (a, b) {
            var aValue = $(a).find('td').eq(columnIndex).text();
            var bValue = $(b).find('td').eq(columnIndex).text();
            if ($.isNumeric(aValue) && $.isNumeric(bValue)) {
                // Compare numeric values
                aValue = parseFloat(aValue);
                bValue = parseFloat(bValue);
            } else {
                // Compare string values
                aValue = aValue.toLowerCase();
                bValue = bValue.toLowerCase();
            }
            if (sortOrder == 'asc') {
                return (aValue > bValue) ? 1 : -1;
            } else {
                return (aValue < bValue) ? 1 : -1;
            }
        });
        // Replace current rows with sorted rows
        $('tbody').html(tableRows);
    }

    // Add click event handlers to table headers
    $('#date-header').click(function () {
        sortTable(0, $(this).attr('sort-order'));
        $(this).attr('sort-order', $(this).attr('sort-order') == 'asc' ? 'desc' : 'asc');
    });

    $('#hour-header').click(function () {
        sortTable(1, $(this).attr('sort-order'));
        $(this).attr('sort-order', $(this).attr('sort-order') == 'asc' ? 'desc' : 'asc');
    });

    $('#client-header').click(function () {
        sortTable(2, $(this).attr('sort-order'));
        $(this).attr('sort-order', $(this).attr('sort-order') == 'asc' ? 'desc' : 'asc');
    });

    $('#phone-header').click(function () {
        sortTable(3, $(this).attr('sort-order'));
        $(this).attr('sort-order', $(this).attr('sort-order') == 'asc' ? 'desc' : 'asc');
    });

    // Add custom sorting function to services header
    $('#services-header').click(function () {
        alert("Cannot sort by Services column");
    });
});