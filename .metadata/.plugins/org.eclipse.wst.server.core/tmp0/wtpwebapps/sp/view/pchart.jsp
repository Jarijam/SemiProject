<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<style>
#container {
    height: 400px;
    border: 2px solid blue;
}
</style>
<script>
function display(d){
	Highcharts.chart('container', {
	    chart: {
	        type: 'column'
	    },
	    title: {
	        text: 'World\'s largest cities per 2017'
	    },
	    subtitle: {
	        text: 'Source: <a href="http://en.wikipedia.org/wiki/List_of_cities_proper_by_population">Wikipedia</a>'
	    },
	    xAxis: {
	        type: 'category',
	        labels: {
	            rotation: -45,
	            style: {
	                fontSize: '13px',
	                fontFamily: 'Verdana, sans-serif'
	            }
	        }
	    },
	    yAxis: {
	        min: 0,
	        title: {
	            text: 'Population (millions)'
	        }
	    },
	    legend: {
	        enabled: false
	    },
	    tooltip: {
	        pointFormat: 'Population in 2017: <b>{point.y:.1f} millions</b>'
	    },
	    series: [{
	        name: 'Population',
	        data: d,
	        dataLabels: {
	            enabled: true,
	            rotation: -90,
	            color: '#FFFFFF',
	            align: 'right',
	            format: '{point.y:.1f}', // one decimal
	            y: 10, // 10 pixels down from the top
	            style: {
	                fontSize: '13px',
	                fontFamily: 'Verdana, sans-serif'
	            }
	        }
	    }]
	});
};
function getData(){
	$.ajax({
		url:'pchart.mc',
		success:function(d){
			display(d);
		}
	});
};
$(document).ready(function(){
	getData();
});
</script>
<h1>P_CHART</h1>
<div id="container"></div>




