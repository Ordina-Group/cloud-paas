import angular from 'angular';
import ngMaterial from 'angular-material';
import ngRoute from 'angular-route';
import controllers from './controllers.js';
import services from './services.js';

console.log('Bootstrapping Angular v' + angular.version.full);

var app = angular.module('rpslsApp', [ngMaterial, 'ngRoute', 'ngAnimate', services, controllers]);

app.config(function ($mdThemingProvider) {
    console.log('Configuring Material Design theme');
    $mdThemingProvider.theme('default')
        .primaryPalette('brown')
        .accentPalette('red');
    $mdThemingProvider.setDefaultTheme('default');
}).config(function ($routeProvider) {
    console.log('Configuring Routes');
    $routeProvider
        .when('/', {
            templateUrl: 'app/home.html',
            controller: 'homeController'
        }).otherwise({redirectTo: '/'});
})
.config(function ($locationProvider) {
    $locationProvider.html5Mode(false);
});
