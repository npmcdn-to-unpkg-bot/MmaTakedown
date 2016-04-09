$(document).ready(function(){
	
	
	$('.event').click(function(){
		showFights($(this));
	});
	
	/**
	 * showing fights of an event
	 */
	function showFights(source){
		source.next('.fights').toggleClass('showing');
	}
});