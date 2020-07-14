google.charts.load('current', {'packages':['corechart']});
google.charts.setOnLoadCallback(drawChart);

/** Fetches Indian energy generation data and uses it to create a chart. */
function drawChart() {

  fetch('/power-data').then(response => response.json()).then((powerData) => {
    const data = new google.visualization.DataTable();
    data.addColumn('string', 'State');
    data.addColumn('number', 'Area');
    data.addColumn('number', 'Percent');
    data.addColumn('string', 'Region');
  

    powerData.forEach((powerDataEntry) => {
      data.addRow([powerDataEntry[0], powerDataEntry[1], powerDataEntry[2], powerDataEntry[3]]);
    });

    var options = {
        title: 'Correlation between area and percent of power used ' +
                'for some Indian states (2017-2020) ',
        hAxis: {title: 'Area'},
        vAxis: {title: 'Percent of Power Used'},
        bubble: {textStyle: {fontSize: 11}},
        colorAxis: {colors: ['yellow', 'red']}      
    };

    const chart = new google.visualization.BubbleChart(document.getElementById('chart-container'));
    chart.draw(data, options);
  });
}