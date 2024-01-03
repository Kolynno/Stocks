function sortTable(columnIndex) {
    var table, rows, switching, i, x, y, shouldSwitch;
    table = document.querySelector("table");
    switching = true;

    while (switching) {
        switching = false;
        rows = table.rows;

        for (i = 1; i < rows.length - 1; i++) {
            shouldSwitch = false;

            x = parseFloat(rows[i].getElementsByTagName("td")[columnIndex].innerText);
            y = parseFloat(rows[i + 1].getElementsByTagName("td")[columnIndex].innerText);

            // Check if the two rows should switch places
            shouldSwitch = x < y;

            if (shouldSwitch) {
                // If a switch has been marked, make the switch and mark that a switch has been done
                rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
                switching = true;
            }
        }
    }
}
