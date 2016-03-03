var moduleName = 'rpsls.services';

class GameService {

    constructor($log, $http) {
        this.$log = $log;
        this.$http = $http;
    }

    doSomethingCrazy() {
        this.$log.info("I'm doing something really crazy now!");
    }
}

angular.module(moduleName, [])
    .service('gameService', GameService);

export default moduleName;