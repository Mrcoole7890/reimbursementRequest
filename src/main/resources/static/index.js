const btn = document.getElementById("loginSubmit");
const username = document.getElementById("usernameInput");
const password = document.getElementById("password");
const alertLoginZone = document.getElementById("alertLoginZone");
const contentFrame = document.getElementById("context");
const loginFrame = document.getElementById("login");
const colOfContentFrame = document.getElementById("contentFrameCol");

btn.addEventListener("click", () => {
	const xhttp = new XMLHttpRequest();
	xhttp.onload = function() {
		if(findUserByName(JSON.parse(this.responseText),username.value) == null) {
			$("#errorAlert").remove();
			alertLoginZone.appendChild(createDangerAlertDiv("Error: Incorrect Username", "errorAlert"));
		}
		else{
			if(validateUser(findUserByName(JSON.parse(this.responseText),username.value),password.value)) 
				setUpUserPage(findUserByName(JSON.parse(this.responseText),username.value));
			else{
				$("#errorAlert").remove();
				alertLoginZone.appendChild(createDangerAlertDiv("Error: Incorrect Password", "errorAlert"));
			}
		}
	}
	xhttp.open("GET", "/api/v1/employee/");
	xhttp.send();
});

let findUserByName = (listOfJSONUsers,username) => {
	for(let i = 0; i < listOfJSONUsers.length; i++){
		if(listOfJSONUsers[i].employeeName == username) return listOfJSONUsers[i];
	}
	return null;
};

let createDangerAlertDiv = (text,id) => {
	let divNode = document.createElement("div");
	let textNode = document.createTextNode(text);
	divNode.setAttribute("id", id);
	divNode.setAttribute("class", "alert alert-danger form-control");
	divNode.setAttribute("role", "alert");
	divNode.appendChild(textNode);
	return divNode;
};

let validateUser = (jsonUser, password) => {
	if (jsonUser.password == password) return true;
	return false;
};

let clearLoginPage = () => {
	login.remove();
};

let setUpUserPage = (employeeObj) => {
	//I need to check if it is a manager or a employee...
	clearLoginPage();
	if (employeeObj.position == "manager"){
		console.log("Logged in as manager!");
		setupManagerPage(employeeObj);
	}
	else if (employeeObj.position == "employee") {
		console.log("Logged in as employee!");
		setupEmployeePage(employeeObj);
	}
	else {
		console.log("Error: employeeObj.position is " + employeeObj.position);	
	}
};

let setupManagerPage = managerObj => {
	getReimbursmentRequests(null);
	displayManagerData(managerObj);	
};

let setupEmployeePage = employeeObj => {
	console.log(employeeObj.employeeId);
	getReimbursmentRequests(employeeObj.employeeId);
	appendMakeRequestButton(colOfContentFrame, employeeObj);
};

let getReimbursmentRequests = (data) => {
	if(data != null){
		const xhttpReimbursment = new XMLHttpRequest();
		xhttpReimbursment.open("GET", "/api/v1/reimbursmentRequest/" + data);
		xhttpReimbursment.send();
			xhttpReimbursment.onload = function() {
			appendReimbursementCarosel(JSON.parse(this.responseText), false);
		}
	}else{
		const xhttpReimbursment = new XMLHttpRequest();
		xhttpReimbursment.open("GET", "/api/v1/reimbursmentRequest/");
		xhttpReimbursment.send();
			xhttpReimbursment.onload = function() {
			appendReimbursementCarosel(JSON.parse(this.responseText), true);
		}
	}
};

let appendReimbursementCarosel = (data, isManager) => {
	var requests = data;
	console.log(requests);
	const parser = new DOMParser();
	let carocelDOM = parser.parseFromString(carocelHTML, "text/html");
	for(let i = 0; i < requests.length; i++ ){
		
		let tempIndicatorDOM = parser.parseFromString(carouselIndicator, "text/html");
		let tempIndicatorEl = tempIndicatorDOM.body.firstChild;
		tempIndicatorEl.setAttribute("data-slide-to", i);
		carocelDOM.getElementById("indicatorTarget").appendChild(tempIndicatorEl);
		let tempCardDOM = parser.parseFromString(cardHTML, "text/html");
		let cardEl = tempCardDOM.body.firstChild;
		cardEl.setAttribute("id", "card_" + i);
		if(i == 0) cardEl.classList.add("active");
		if(requests[i].status == "denied") {
			cardEl.children[0].classList.add("border-light");
			cardEl.getElementsByClassName("btn-group")[0].remove();
			let statusLabelLoc = cardEl.getElementsByClassName("justify-content-center")[0];
			let statusLabel = document.createElement("H3");
			statusLabel.innerText = "Denied";
			statusLabelLoc.appendChild(statusLabel);
		}
		if(requests[i].status == "approved"){
			cardEl.children[0].classList.add("border-success");
			cardEl.getElementsByClassName("btn-group")[0].remove();
			let statusLabelLoc = cardEl.getElementsByClassName("justify-content-center")[0];
			let statusLabel = document.createElement("H3");
			statusLabel.classList.add("text-success");
			statusLabel.innerText = "Accepted";
			statusLabelLoc.appendChild(statusLabel);
		} 
		if(requests[i].status == "pending") {
			if(!isManager){
				cardEl.children[0].classList.add("border-warning");
				cardEl.getElementsByClassName("btn-group")[0].remove();
				let statusLabelLoc = cardEl.getElementsByClassName("justify-content-center")[0];
				let statusLabel = document.createElement("H3");
				statusLabel.classList.add("text-warning");
				statusLabel.innerText = "Pending";
				statusLabelLoc.appendChild(statusLabel);
			}
			else{
				let acceptButt = cardEl.getElementsByClassName("btn-primary")[0];
				acceptButt.setAttribute("onclick","acceptRequest("+ requests[i].requestId +", \"card_" + (requests[i].requestId -1) + "\");");
				cardEl.children[0].classList.add("border-warning");
				
				let denyButt = cardEl.getElementsByClassName("btn-danger")[0];
				denyButt.setAttribute("onclick","denyRequest("+ requests[i].requestId +", \"card_" + (requests[i].requestId -1)  + "\");");
				cardEl.children[0].classList.add("border-warning");
			}
		}

		cardElHeader = cardEl.getElementsByClassName("cardUsername")[0];
		cardElHeader.innerText = requests[i].employee.employeeName;
		cardElHeaderAmount = cardEl.getElementsByClassName("cardUserAmount")[0];
		cardElHeaderAmount.innerText = "$" + requests[i].amount.toFixed(2);
		cardElDescription = cardEl.getElementsByClassName("cardDescription")[0];
		cardElDescription.innerText = requests[i].description;
		carocelDOM.getElementById("cardTarget").appendChild(cardEl);
	}
	let carocelEl = carocelDOM.body.firstChild;
	contentFrame.appendChild(carocelEl);
	
}

let appendMakeRequestButton = (contentFrame, employeeObj) => {
	const parser = new DOMParser();
	makeRequestButton = document.createElement("button");
	flexRow = document.createElement("div");
	flexRow.classList.add("d-flex");
	flexRow.classList.add("justify-content-center");
	makeRequestButton.setAttribute("type", "button");
	makeRequestButton.setAttribute("data-toggle","modal");
	makeRequestButton.setAttribute("data-target","#exampleModal");
	makeRequestButton.classList.add("btn");
	makeRequestButton.classList.add("btn-primary");
	makeRequestButton.addEventListener("click", () => {
		
	});
	makeRequestButton.innerHTML = "Make New Request";
	flexRow.appendChild(makeRequestButton);
	contentFrame.appendChild(flexRow);
	modalDom = parser.parseFromString(modal, "text/html");
	modalEl = modalDom.body.firstChild;
	contentFrame.appendChild(modalEl);
	submitRequestButton = document.getElementById("submitRequestButton");
	cancelRequestButton = document.getElementById("cancelRequestButton");
	submitRequestButton.addEventListener("click", () => {
		//console.log("Submit Button Works");
		
		let data = {
				description: document.getElementById("request_description").value,
				amount: document.getElementById("request_amount").value,
				employeeId: employeeObj.employeeId
		};
		console.log(data);
		const xhttpReimbursment = new XMLHttpRequest();
		xhttpReimbursment.open("PUT", "/api/v1/reimbursmentRequestSubmission");
		xhttpReimbursment.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
		xhttpReimbursment.send(JSON.stringify(data));
		xhttpReimbursment.onload = function() {
			console.log(this.responseText);
		}
		
		document.getElementById("request_amount").value = "";
		document.getElementById("request_description").value = "";
	});
	cancelRequestButton.addEventListener("click", () => {
		document.getElementById("request_amount").value = "";
		document.getElementById("request_description").value = "";
	});
};

let displayManagerData = managerObj => {
	
};

let testAjaxPost = () => {
	const xhttpmeme = new XMLHttpRequest();
	let data = {
		employeeName: "John",
		position: "manager",
		password: "meme"
	}	
	xhttpmeme.onload = function() {
		console.log("data sent");
		console.log(this.responseText);
	}
	xhttpmeme.open("PUT", "/api/v1/employee/", false);
	xhttpmeme.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
	xhttpmeme.send(JSON.stringify(data));
};

let acceptRequest = (request_id, card_id) => {
	let card = document.getElementById(card_id);
	let data = {
		status: "approved"
	};
	const xhttp = new XMLHttpRequest();
	xhttp.onload = function() {
		console.log("data sent!");
	};
	xhttp.open("PUT", "/api/v1/reimbursmentRequest/" + request_id);
	xhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
	xhttp.send(JSON.stringify(data));
	card.children[0].classList.replace("border-warning","border-success");
	card.getElementsByClassName("btn-group")[0].remove();
	let statusLabelLoc = card.getElementsByClassName("justify-content-center")[0];
	let statusLabel = document.createElement("H3");
	statusLabel.classList.add("text-success");
	statusLabel.innerText = "Accepted";
	statusLabelLoc.appendChild(statusLabel);
};

let denyRequest = (request_id, card_id) => {
	let card1 = document.getElementById(card_id);
	let data = {
		status: "denied"
	};
	const xhttp = new XMLHttpRequest();
	xhttp.onload = function() {
		console.log("data sent!");
	};
	xhttp.open("PUT", "/api/v1/reimbursmentRequest/" + request_id);
	xhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
	xhttp.send(JSON.stringify(data));
	card1.children[0].classList.replace("border-warning","border-light");
	card1.getElementsByClassName("btn-group")[0].remove();
	let statusLabelLoc = card1.getElementsByClassName("justify-content-center")[0];
	let statusLabel = document.createElement("H3");
	statusLabel.innerText = "Denied";
	statusLabelLoc.appendChild(statusLabel);
};