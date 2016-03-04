var moduleName = 'rpsls.controllers';

class GameController {
    constructor($log, gameService) {
        this.$log = $log;
        this.gameService = gameService;
        this.start();
    }

    start() {
        this.gameService.doSomethingCrazy();
    }
}

class HomeController {
    constructor($log) {
        this.$log = $log;
    }
}

class SignInController {
    constructor($rootScope, $http, $log, $timeout, $cookies, sessionService) {
        $rootScope.ready = true;
        this.xsrf_token = $cookies.get('XSRF-TOKEN');
        this.authenticated = false;
        this.$log = $log;
        this.$http = $http;
        this.$timeout = $timeout;
        this.sessionService = sessionService;

        this.removeLoaderWrapper();
        this.checkForUserSession();
    }

    checkForUserSession() {
        this.sessionService.checkUserSession().then(data => {
            if (data.displayName) {
                this.authenticated = true;
                this.displayName = data.displayName;
            } else {
                this.$log.info('Not logged in');
            }
        });
    }

    removeLoaderWrapper() {
        this.$timeout(
            function () {
                angular.element(document.querySelector('#loader-wrapper')).remove()
            }, 1000
        );
    }

    signIn() {
        angular.element(document.querySelector('#connectForm')).submit();
    }
}

angular.module(moduleName, [])
    .controller('homeController', HomeController)
    .controller('signInController', SignInController)
    .controller('gameController', GameController);

export default moduleName;
