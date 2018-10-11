/**
 * 
 */

app.config(function($stateProvider,$urlRouterProvider,$locationProvider){
	
	$urlRouterProvider.otherwise('/');
	
	$stateProvider
	.state('login',{
		url:"/login",
		templateUrl:"templates/login.html",
		controller:'LoginController'
		
	})
	.state('auth',{
		url:"/auth?code",
		templateUrl:"templates/auth.html",
		controller:'ContentController'
	})
	.state('main',{
		url:"/main",
		templateUrl:"templates/main.html"
	})
	
	
	$locationProvider.html5Mode(true);
});

