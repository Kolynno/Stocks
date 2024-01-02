var ticker = prompt("Ticker:")
var url = "stock?ticker=" + ticker

function setChart() {
    anychart.data.loadCsvFile(url,
        function (data) {
            // create a data table with the loaded data
            var dataTable = anychart.data.table();
            dataTable.addData(data);
            // map the loaded data for the candlestick series
            var mapping = dataTable.mapAs({
                open: 1,
                high: 2,
                low: 3,
                close: 4
            });
            // create a stock chart
            var chart = anychart.stock();
            // create the chart plot
            var plot = chart.plot(0);
            // set the grid settings
            plot
                .yGrid(true)
                .xGrid(true)
                .yMinorGrid(true)
                .xMinorGrid(true);
            // create the candlestick series
            var series = plot.candlestick(mapping);
            series.name(ticker);
            series.legendItem().iconType('rising-falling');
            series.fallingFill("#FF0D0D");
            series.fallingStroke("#FF0D0D");
            series.risingFill("#43FF43");
            series.risingStroke("#43FF43");
            // set the title of the chart
            chart.title("Акция: " + ticker );

            chart.interactivity().zoomOnMouseWheel(true);
            // set the container id for the chart
            chart.container('container');
            // initiate the chart drawing

            var rangePicker = anychart.ui.rangePicker();
            rangePicker.render(chart);
            var rangeSelector = anychart.ui.rangeSelector();
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