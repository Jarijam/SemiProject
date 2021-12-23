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
	        type: 'line'
	    },
	    title: {
	        text: 'Monthly Average Temperature'
	    },
	    subtitle: {
	        text: 'Source: WorldClimate.com'
	    },
	    xAxis: {
	    	categories: d.time	
	    },
	    yAxis: {
	        title: {
	            text: 'Temperature (°C)'
	        }
	    },
	    plotOptions: {
	        line: {
	            dataLabels: {
	                enabled: true
	            },
	            enableMouseTracking: false
	        }
	    },
	    series: d.data
	});
};

function getdata(){
	$.ajax({
		url:'rchart.mc',
		success:function(d){
			display(d);
			//alert(d);
		}
	});
}

$(document).ready(function(){
	$('#c1').click(function(){
		getdata();
	});
	$('#c2').click(function(){
		getdata2();
	});
});

</script>

<h1>R_CHART</h1>

	<button id="c1">Chart</button>
	<button id="c2">Chart</button>

<div id="container"></div>