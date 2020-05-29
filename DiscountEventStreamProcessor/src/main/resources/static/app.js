var stompClient = null;
var stompSubscribedClient = null;
var user = null;
var windowedData = null;
var windowediData = null;
var ctx = null;
var discChart = null;

var ictx = null;
var idiscChart = null;

var bgColorPallet = [
    'rgba(255,99,132,1)',
    'rgba(54, 162, 235, 1)',
    'rgba(255, 206, 86, 1)',
    'rgba(75, 192, 192, 1)',
    'rgba(153, 102, 255, 1)',
    'rgba(255, 159, 64, 1)',
    'rgba(255,99,132,1)',
    'rgba(54, 162, 235, 1)',
    'rgba(255, 206, 86, 1)',
    'rgba(75, 192, 192, 1)',
    'rgba(153, 102, 255, 1)',
    'rgba(255, 159, 64, 1)'
  ];

var borderColorPallet = [
    'rgba(255,99,132,1)',
    'rgba(54, 162, 235, 1)',
    'rgba(255, 206, 86, 1)',
    'rgba(75, 192, 192, 1)',
    'rgba(153, 102, 255, 1)',
    'rgba(255, 159, 64, 1)',
    'rgba(255,99,132,1)',
    'rgba(54, 162, 235, 1)',
    'rgba(255, 206, 86, 1)',
    'rgba(75, 192, 192, 1)',
    'rgba(153, 102, 255, 1)',
    'rgba(255, 159, 64, 1)'
  ];

var lineChartOptions = 	{
	title:      {
        display: true,
        text:    "Discount Time Scale"
    },
	bounds: 'data',
	responsive: false,
	animation: {
		duration: 0
	},
	scales: {
		xAxes: [
			{
			type: "time",
			time: {
				unit: 'second',
				unitStepSize: 1,
                displayFormats: {
                    second: 'h:mm:ss a'
                }
            },
			
			distribution: 'series',
			scaleLabel: {
				display: true,
				labelString: '--> Time'
			},
			 offset: true,
			 ticks: {
				 major: {
				 enabled: true,
				 fontStyle: 'bold'
				 },
				 source: 'data',
				 autoSkip: true,
				 autoSkipPadding: 75,
				 maxRotation: 0,
				 sampleSize: 100
			 }
		}
			],
		yAxes: [{
			gridLines: {
				drawBorder: false
			},
			scaleLabel: {
				display: true,
				labelString: 'Discount Applied (%)'
			}
		}]
	},
	tooltips: {
		intersect: false,
		mode: 'index',
		callbacks: {
			label: function(tooltipItem, myData) {
				var label = myData.datasets[tooltipItem.datasetIndex].label || '';
				if (label) {
					label += ': ';
				}
				label += parseFloat(tooltipItem.value).toFixed(2);
				return label;
			}
		}
	}
};
function setConnected(connected) {
	$("#connect").prop("disabled", connected);
	$("#disconnect").prop("disabled", !connected);
	if (connected) {
		$("#conversation").show();
	} else {
		$("#conversation").hide();
	}
	$("#messages").html("");
}

function initChart()
{
	
	ctx = document.getElementById("discChart").getContext('2d');
	discChart = new Chart(ctx, {
		animation: {
			duration: 0
		},
		  type: 'bar',
		  data: {
		    labels: [],
		    datasets: [{
		      label: 'Discount Aggregate By Category(Windowed)',
		      data: [],
		      backgroundColor: [
		        'rgba(255, 99, 132, 0.2)',
		        'rgba(54, 162, 235, 0.2)',
		        'rgba(255, 206, 86, 0.2)',
		        'rgba(75, 192, 192, 0.2)',
		        'rgba(153, 102, 255, 0.2)',
		        'rgba(255, 159, 64, 0.2)',
		        'rgba(255, 99, 132, 0.2)',
		        'rgba(54, 162, 235, 0.2)',
		        'rgba(255, 206, 86, 0.2)',
		        'rgba(75, 192, 192, 0.2)',
		        'rgba(153, 102, 255, 0.2)',
		        'rgba(255, 159, 64, 0.2)'
		      ],
		      borderColor: [
		        'rgba(255,99,132,1)',
		        'rgba(54, 162, 235, 1)',
		        'rgba(255, 206, 86, 1)',
		        'rgba(75, 192, 192, 1)',
		        'rgba(153, 102, 255, 1)',
		        'rgba(255, 159, 64, 1)',
		        'rgba(255,99,132,1)',
		        'rgba(54, 162, 235, 1)',
		        'rgba(255, 206, 86, 1)',
		        'rgba(75, 192, 192, 1)',
		        'rgba(153, 102, 255, 1)',
		        'rgba(255, 159, 64, 1)'
		      ],
		      borderWidth: 0.5
		    }]
		  },
		  options: {
		    responsive: false,
		    scales: {
		      xAxes: [{
		        ticks: {
		        	maxRotation: 90,
		        	minRotation: 5,
		        	fontSize: 7
		        },
		       
				scaleLabel: {
					display: true,
					labelString: 'Category'
				},
				legend: {
				      display: false,
				      position: 'bottom',
				      labels: {
				        fontColor: "#000080",
				      }
				}
		      }],
		      yAxes: [{
		        ticks: {
		        beginAtZero: true
		        },
				scaleLabel: {
					display: true,
					labelString: 'Aggregated Discount Applied ($)'
				}
		        
		      }]
		    }
		  }
		});



}

function initiChart()
{
	
	ictx = document.getElementById("idiscChart").getContext('2d');
	idiscChart = new Chart(ictx, {
	    type: 'line',
	    data: {labels:[], datasets:[]},
		options: lineChartOptions
	});


}


function connect() {
	

		var socket = new SockJS('/websock');
		stompClient = Stomp.over(socket);
		stompClient.connect({}, function(frame) {
			setConnected(true);
			console.log("Connected ");
			stompClient.subscribe('/topic/messages', function(payload) {
				onMessageReceived(payload);

			});
			
			stompClient.subscribe('/topic/messages_i', function(payload) {
				oniMessageReceived(payload);

			});
			stompClient.send("/app/register", {}, {});
			// initChart();
			// initiChart();
			
		/*
		 * setTimeout(function() { {
		 * 
		 * 
		 * console.log("Connected " + user); } }, 3000);
		 */
	});

}

function disconnect() {

	if (stompClient != null) {

		stompClient.send("/app/unregister", {}, {});
		stompClient.disconnect();
	}
	stompClient = null;
	setConnected(false);
	user = null;
	console.log("Disconnected");
}

function sendMessage() {

	stompClient.send("/app/message", {}, JSON.stringify({
		'message' : $("#message").val(),
		'type' : 'CHAT',
		'sender' : user
	}));
	console.log("Sent..**");
}

function onMessageReceived(payload) {
	var messageContent = "";
	var messageObj = JSON.parse(payload.body);
	var windowStart = messageObj.windowStart;
	var windowEnd = messageObj.windowEnd
	var category = messageObj.category
	var windowTotal = messageObj.windowTotal

	if (windowedData === null) {
		windowedData = {};
		windowedData.windowStart = windowStart;
		windowedData.windowEnd = windowEnd;
		windowedData.category = category;
		windowedData.windowTotal = windowTotal;
		$("#timeRange").text(windowStart + " - " + windowEnd);
		initChart();

	} else {

		if (windowStart !== windowedData.windowStart
				|| windowEnd !== windowedData.windowEnd) {

			$("#messages").empty();
			windowedData.windowStart = windowStart;
			windowedData.windowEnd = windowEnd;
			windowedData.category = category;
			windowedData.windowTotal = windowTotal;

			$("#timeRange").fadeOut(function() {
				$(this).text(windowStart + " - " + windowEnd).fadeIn();
			});
			initChart();

		}

	}

	if ($("#" + category + "").length) {

		$("#data_" + category + "").fadeOut(function() {
			$(this).text(windowTotal).fadeIn();
		});

	} else {
		$("#messages").append(
				"<tr id='" + category + "'><td>" + category
						+ "</td><td id=data_" + category + ">" + windowTotal
						+ "</td></tr>");
	}

	// chart
	// alert(discChart);
	var labelIndex = discChart.data.labels.indexOf(category);
	
	if (labelIndex === -1)
	{
		discChart.data.labels.push(category);
		discChart.data.datasets.forEach((dataset) => {
	        dataset.data.push(windowTotal);
	    });
	}
	else
	{
		// alert(labelIndex);
		discChart.data.datasets.forEach((dataset) => {
	        dataset.data.splice(labelIndex,0,windowTotal);
	    });
	}
	

	discChart.update();

}

function oniMessageReceived(payload) {
	// console.log(JSON.stringify(payload));
	var messageObj = JSON.parse(payload.body);
	var windowStart = messageObj.windowStart;
	var windowEnd = messageObj.windowEnd
	var category = messageObj.category
	var discountApplied = messageObj.discountApplied;
	var timestamp = messageObj.timestamp;
	var fromattedtimestamp = messageObj.formattedTimestamp;
	
	if (windowediData === null) {
		
		initiChart();
		windowediData = messageObj;
	} 
	
	if ($("#i" + category + "").length) {

		$("#idata_" + category + "").fadeOut(function() {
			$(this).text(discountApplied).fadeIn();
		});
		
		$("#idatats_" + category + "").fadeOut(function() {
			$(this).text(fromattedtimestamp).fadeIn();
		});

	} else {
		$("#imessages").append(
				"<tr id='i" + category + "'><td>" + category
						+ "</td><td id=idata_" + category + ">" + discountApplied
						+ "</td><td id=idatats_"+category +">"+fromattedtimestamp+"</td></tr>");
	}
	
	
	var ds = {'x': new Date(timestamp), 'y': discountApplied}
	 var labelIndex = idiscChart.data.labels.indexOf(category);
	
	if (labelIndex === -1)
	{
		idiscChart.data.labels.push(category);
		var dso = {'label':category,'borderWidth': 1, 'borderColor': borderColorPallet[idiscChart.data.labels.length + 1],
					'data': [ds] };
		dso.pointRadius = 0;
		dso.fill = false;
		lineTension =  0;
		borderWidth = 2
		idiscChart.data.datasets.push(dso);
	}
	else
	{
		var child = idiscChart.data.datasets[labelIndex];
		if(child.data.length > 1000)
			child.data = [];
		child.data.push(ds);
		// idiscChart.data.datasets.splice(labelIndex,0,child);
	}
	
	 idiscChart.update();	
	
}

$(function() {
	$("form").on('submit', function(e) {
		e.preventDefault();
	});
	$("#connect").click(function() {
		connect();
	});
	$("#disconnect").click(function() {
		disconnect();
	});	
	$("#send").click(function() {
		sendMessage();
	});
});