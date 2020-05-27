var stompClient = null;
var stompSubscribedClient = null;
var user = null;
var windowedData = null;
var ctx = null;
var discChart = null;

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

function connect() {
	{

		var socket = new SockJS('/websock');
		stompClient = Stomp.over(socket);
		stompClient.connect({}, function(frame) {
			setConnected(true);
			console.log("Connected ");
			stompClient.subscribe('/topic/messages', function(payload) {
				onMessageReceived(payload);

			});
			stompClient.send("/app/register", {}, {});
			// ctx = document.getElementById("discChart").getContext('2d');
			var ctx = $('#discChart');
			discChart = new Chart(ctx, {
				type : 'bar',
				data : {},
				options : {}
			});
			discChart.data.labels = new Array();
			discChart.data.datasets = new Array();
			discChart.data.datasets.push({label:'Aggregated Discounts',data: new Array()});

		});

		/*
		 * setTimeout(function() { {
		 * 
		 * 
		 * console.log("Connected " + user); } }, 3000);
		 */
	}

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

			discChart.data.labels = new Array();
			discChart.data.datasets = new Array();
			discChart.data.datasets.push({label:'Aggregated Discounts',data: new Array()});
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
	discChart.data.labels.push(category);
	discChart.data.datasets.forEach((dataset) => {
        dataset.data.push(windowTotal);
    });
	discChart.update();

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