/**
 * 
 */
app.controller('FeedController',['$scope','fbService',function($scope,fbService){
 
	$scope.feeds=[];
	
	console.log("fired")
	var loadFeeds = function(){
		fbService.getFeeds().then(function(responce){
			$scope.feeds=responce.data[0];
		},function(error){
			console.log("error while retreiving");
		})
	}
	
	loadFeeds();
	
}]);