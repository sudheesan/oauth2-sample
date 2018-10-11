/**
 * 
 */
app.controller('LoginController',['$scope','fbService','$location',function($scope,fbService,$location){
    
	$scope.loginWithFaceBook = function(){
		
		fbService.getCode().then(function(repoonce){
			return repoonce;
			console.log("success");
			
		},function(err){
			console.log("eror");
		});
		
	}

}]);