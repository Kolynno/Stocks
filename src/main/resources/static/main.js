const ticker = prompt("Ticker:");
const url = "stock?ticker=" + ticker;

function setChart() {
    anychart.data.loadCsvFile(url,
        function (data) {
            const dataTable = anychart.data.table();
            dataTable.addData(data);
            const mapping = dataTable.mapAs({
                open: 1,
                high: 2,
                low: 3,
                close: 4
            });
            const chart = anychart.stock();
            const plot = chart.plot(0);
            plot
                .yGrid(true)
                .xGrid(true)
                .yMinorGrid(true)
                .xMinorGrid(true);
            const series = plot.candlestick(mapping);
            series.name(ticker);
            series.legendItem().iconType('rising-falling');
            series.fallingFill("#FF0D0D");
            series.fallingStroke("#FF0D0D");
            series.risingFill("#43FF43");
            series.risingStroke("#43FF43");
            chart.title("Акция: " + ticker );
            chart.interactivity().zoomOnMouseWheel(true);
            chart.container('container');
            const rangePicker = anychart.ui.rangePicker();
            rangePicker.render(chart);
            const rangeSelector = anychart.ui.rangeSelector();
            rangeSelector.render(chart);
            anychart.format.outputLocale("ru-ru");
            anychart.format.outputDateTimeFormat("dd MMMM yyyy 'г.' HH:mm:ss");
            chart.draw();
        });
}

function setStockListSelect() {
    const stockList = document.getElementById("stockListSelect");
    const xhr = new XMLHttpRequest();
    xhr.open("POST", "/db/getStocksNames", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.send();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            const response = JSON.parse(xhr.responseText);
            const stocks = response.stocks;
            stocks.forEach(stock => {
                stockList.innerHTML += `<option value="${stock}">${stock}</option>`;
            });
        }
    };
}

window.onload = function () {
    setStockListSelect();
    setChart();
}