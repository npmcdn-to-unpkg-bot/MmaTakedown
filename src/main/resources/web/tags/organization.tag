<organization>
	<div class="row banner">
		<div class="col-md-4 col-sm-4 image" style="background-image: url('images/{ organization.picture }')">
			&nbsp;
		</div>
		<div class="col-md-8 col-sm-8">
			<h1>{ organization.name }</h1>
		</div>
	</div>
	<div class="events">
		<div class="spinner"></div>
		<div riot-tag="event" each={ event in events } data={ this } ></div>
	</div>
			
	<script>
		var self = this;
		
		
		this.on('mount', function() {
			console.log('Mounting organization');
			console.log(opts);
			self.organization = opts;
			
			
			MTD.getEvents(opts.id, function(events){
				self.events = events;
				self.events.reverse();
				
				self.update();
				$('.spinner').remove();
				$('.events').addClass('showing');
			});
		});
		
		
		
		showFights(clickEvent){
			var event = clickEvent.item;
			console.log(event);
		}
	</script>
</organization>