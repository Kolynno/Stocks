function sortTable(columnIndex) {
    var table, rows, switching, i, x, y, shouldSwitch, dir;
    table = document.querySelector("table");
    switching = true;
    dir = "asc"; // Направление сортировки (по умолчанию - по возрастанию)

    while (switching) {
        switching = false;
        rows = table.rows;

        for (i = 1; i < rows.length - 1; i++) {
            shouldSwitch = false;

            x = normalizeValue(rows[i].getElementsByTagName("td")[columnIndex].innerText);
            y = normalizeValue(rows[i + 1].getElementsByTagName("td")[columnIndex].innerText);

            // Проверка направления сортировки
            if (dir === "asc") {
                shouldSwitch = x > y;
            } else {
                shouldSwitch = x < y;
            }

            if (shouldSwitch) {
                // Если есть место для перестановки, переставляем строки и помечаем, что была перестановка
                rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
                switching = true;
            }
        }

        // Изменяем направление сортировки для следующего цикла
        if (!switching && dir === "asc") {
            dir = "desc";
            switching = true;
        }
    }
}

// Функция для нормализации значений перед сравнением
function normalizeValue(value) {
    // В данном примере просто предполагается, что значения - это числа, и удаляются пробелы
    return parseFloat(value.replace(/\s/g, '')) || 0;
}
