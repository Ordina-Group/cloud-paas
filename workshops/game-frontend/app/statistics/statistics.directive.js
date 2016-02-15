'use strict';

angular.module('gameApp.statistics.directive', [])

.directive('statistic', function () {
    return {
        restrict: 'E',
        scope: {
            color: '@',
            number: '@'
        },
        templateUrl: 'statistics/statistics.directive.html'
    };
});