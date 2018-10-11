/**
 * 
 */

app.controller('ContentController',['$scope','fbService','$location','$state','$timeout',function($scope,fbService,$location,$state,$timeout){
 
	if ( $location.search().hasOwnProperty( 'code' ) ) {
		   var myvalue = $location.search()['code'];
		   fbService.getAccess(myvalue).then(function(responce){
			   $timeout(function () {
				  
			    }, 2000);
			   $state.go('main');
		   },function(err){
			   $scope.isLoading=false;
			   $state.go('login');
			   
		   })
	}
	else{
		 $state.go('login');
	}
}]);