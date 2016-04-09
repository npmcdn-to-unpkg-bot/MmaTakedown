<organizations>
	<div class="col-md-3 col-md-offset-1 card" each={organizations}>
		<div class="row">
			<div class="col-md-12 title">
				<div class="image" style="background-image: url('images/{ picture }')">
				</div>
				<h2>{ name }</h2>	
			</div>
			<div class="row">
				<div class="col-md-12 actions">
					<a onclick={ parent.showOrganization }>Events</a>
				</div>
			</div>
		</div>
	</div>
	
	
	<script>
		var self = this;
		
		self.organizations = [];
		this.on('mount', function() {
			//Getting all the organizations
			MTD.getOrganizations(function(orgs){
				self.organizations = orgs;
				self.update();
			});
		});
		
		showOrganization(event){
			var organization = event.item;
			
			console.log(organization);
			
			if(self.orgTag !== undefined){
				self.orgTag.unmount();
			}
			$('body .container').append('<organization data-id="'+organization.id+'"></organization>');
			self.orgTag = riot.mount('organization[data-id="'+organization.id+'"]', organization);
		};
	</script>
</organizations>