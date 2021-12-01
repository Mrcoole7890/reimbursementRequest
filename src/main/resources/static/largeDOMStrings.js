let carocelHTML = 
`			  <div id="carouselExampleIndicators" class="carousel slide gener" data-ride="carousel">
				  <ol id="indicatorTarget" class="carousel-indicators">
				    <!--<li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>-->

				  </ol>
				  <div id="cardTarget" class="carousel-inner">

				  </div>
				  <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
				    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
				    <span class="sr-only">Previous</span>
				  </a>
				  <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
				    <span class="carousel-control-next-icon" aria-hidden="true"></span>
				    <span class="sr-only">Next</span>
				  </a>
				</div>`;
				
let cardHTML = 
`                    <div class="carousel-item">
				      	<div class="card d-block w-100">
				      	  <div class="card-header">
				      	    <div class="d-flex justify-content-between">
								<div class="cardUsername"></div>
								<div class="cardUserAmount"></div>
						  	</div>
						  </div>
						  <div class="card-body">
						    <p class="card-text cardDescription text-wrap" style="width: 60rem;"></p>
						    <div class="d-flex justify-content-center">
							  	<div class="btn-group" role="group" aria-label="Basic example" id="aod">
									<button type="button" class="btn btn-primary">Accept</button>
									<button type="button" class="btn btn-danger">Deny</button>
								</div>
						    </div>
						  </div>
						</div>
				    </div>
`;

let carouselIndicator = 
`<li data-target="#carouselExampleIndicators" class="requestBar"></li>`;

let modal = 
`<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
<div class="modal-dialog" role="document">
<div class="modal-content">
  <div class="modal-header">
    <h5 class="modal-title" id="exampleModalLabel">Make a Request</h5>
    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
      <span aria-hidden="true">&times;</span>
    </button>
  </div>
  <div class="modal-body">
  	<div class="form-group">
  		<input type="number" class="form-control" id="request_amount" min="0" step=".01" data-bind="value:replyNumber" placeholder="Enter requested amount" />
		<textarea type="" class="form-control" id="request_description" placeholder="Describe your request"></textarea>
	</div>
  </div>

  <div class="modal-footer">
    <button id="cancelRequestButton" type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
    <button id="submitRequestButton" type="button" class="btn btn-primary" data-dismiss="modal">Submit Request</button>
  </div>
</div>
</div>
</div>
`;