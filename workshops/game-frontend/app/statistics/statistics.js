'use strict';

angular.module('gameApp.statistics', ['ngRoute'])

.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/statistics', {
        templateUrl: 'statistics/statistics.html',
        controller: 'StatisticsCtrl'
    });
}])

.controller('StatisticsCtrl', ['$http', '$scope', function ($http, $scope) {

    $scope.countGames = 0;
    $scope.countPlayers = 0;

    $http.get('http://game-data-api.cfapps.io/games').success(function (data) {
        console.log(data);
        $scope.countGames = data.length;
    });

    $http.get('http://game-data-api.cfapps.io/players').success(function (data) {
        console.log(data);
        $scope.countPlayers = Object.keys(data).length;;
    });
}]);