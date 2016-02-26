var moduleName = 'rpsls.services';

class GameService {

    constructor($log, $http) {
        this.$log = $log;
        this.$http = $http;
    }

    static doSomethingCrazy() {
        $log.info('CRRRAZZZZZYYYYY!')
    }
}

angular.module(moduleName, [])
    .service('gameService', GameService);

export default moduleName;