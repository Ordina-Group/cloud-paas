var moduleName = 'rpsls.services';

class GameService {

    constructor($log, $http) {
        this.$log = $log;
        this.$http = $http;
    }

    doSomethingCrazy() {
        this.$log.info("I'm doing something really crazy now!");
        this.$log.debug("I'm doing something really crazy now!");
        this.$log.warn("I'm doing something really crazy now!");
        this.$log.error("I'm doing something really crazy now!");
    }
}

class SessionService {

    constructor($log, $http) {
        this.$log = $log;
        this.$http = $http;
    }

    checkUserSession() {
        return this.$http({
            url: '/session',
            method: 'GET'
        }).then(
            function (response) {
                return response.data;
            },
            function (error) {
                return error.data;
            }
        );
    }
}

angular.module(moduleName, [])
    .service('gameService', GameService)
    .service('sessionService', SessionService);

export default moduleName;