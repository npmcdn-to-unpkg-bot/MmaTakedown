<event>
	<div class="row">
		<div class="col-md-3">
			event picture
		</div>
		<div class="col-md-8">
			<a>{ event.name }</a>
		</div>
		<div class="col-md-1">
			<span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
		</div>
	</div>	
	
	<script>
		var self = this;
		this.on('mount', function(){
			console.log('mounting event');
			console.log(opts);
			self.event = opts.event;
	
		});
		
		getFights(){
			MTD.getFights(self.event.id, function(fights){
				console.log(fights);
				self.fights = fights;
				self.update();
			});
		}
	</script>
</event>

