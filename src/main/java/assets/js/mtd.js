var MTD = {
	self : this,
	/**
	 * Get all organizations
	 */
	getOrganizations : function(callback) {
		console.log("Getting organizations");

		$.get('/api/organization', function(data) {
			console.log(data);
			if (callback !== undefined) {
				callback(data);
			}
		});
	},
	/**
	 * Get a single organization with its events
	 */
	getOrganization : function(id, callback) {
		console.log("Getting organization(" + id + ")");
		$.get('/api/organization/' + id, function(data) {
			self.getEvents(id, function(events) {
				data.events = events;

				if (callback !== undefined) {
					callback(data);
				}
			});
		});
	},

	/**
	 * Getting the events for a single organization
	 */
	getEvents : function(orgId, callback) {
		console.log("Getting organization(" + orgId + ") events");
		$.get('/api/organization/' + orgId + '/events', function(events) {
			if (callback !== undefined) {
				callback(events);
			}
		});
	},
	/**
	 * Gets fights for an event
	 */
	getFights: function(eventId, callback){
		console.log("Getting fights for event ("+eventId+")");
		$.get('/api/event/'+eventId+'/fights', function(fights){
			if(callback !== undefined){
				callback(fights);
			}
		});
	}

}