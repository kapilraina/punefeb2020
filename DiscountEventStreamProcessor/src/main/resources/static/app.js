var stompClient = null;
var stompSubscribedClient = null;
var user = null;
var windowedData = null;
var windowediData = null;
var ctx = null;
var discChart = null;

var ictx = null;
var idiscChart = null;

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
	
	ctx = document.getElementById("discChart");
	discChart = new Chart(ctx, {
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
		      borderWidth: 1
		    }]
		  },
		  options: {
		    responsive: false,
		    scales: {
		      xAxes: [{
		        ticks: {
		      // maxRotation: 90,
		      // minRotation: 80
		        }
		      }],
		      yAxes: [{
		        ticks: {
		       // beginAtZero: true
		        }
		      }]
		    }
		  }
		});



}

function initiChart()
{
	
	ictx = document.getElementById("idiscChart");
	idiscChart = new Chart(ictx, {
	    type: 'bubble',
	    data: {datasets:[]},
		  options: {
			  
			    scales: {
			      xAxes: [{
			        type: 'time',
			        distribution: 'series'
			      }]
			    }
			  }
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
			initChart();
			initiChart();
			
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
	
	if (windowediData === null) {
		
		initiChart();

	} 
	
	if ($("#i" + category + "").length) {

		$("#idata_" + category + "").fadeOut(function() {
			$(this).text(discountApplied).fadeIn();
		});
		
		$("#idatats_" + category + "").fadeOut(function() {
			$(this).text(timestamp).fadeIn();
		});

	} else {
		$("#imessages").append(
				"<tr id='i" + category + "'><td>" + category
						+ "</td><td id=idata_" + category + ">" + discountApplied
						+ "</td><td id=idatats_"+category +">"+timestamp+"</td></tr>");
	}
	
	 var ds = { label:[category],data:[{x: new Date(), y: discountApplied, r: 20}] };

	
	 idiscChart.data.datasets.push(ds);
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