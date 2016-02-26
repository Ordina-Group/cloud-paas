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
    this.$log = $log
  }
}

class SignInController {
  constructor($http, $log) {
    this.signedIn = false;
    this.$log = $log;
    this.$http = $http;

    this.checkConnection();
  }

  checkConnection() {
    this.$log.info('Checking connection');
    this.$http({
      url: '/play',
      method: 'GET'
    }).then(
      function (response) {
          return response.data;
      },
      function (error) {
          console.log('Error [' + error + '] when checking connection');
          return {};
    });
  }
}

angular.module(moduleName, [])
    .controller('homeController', HomeController)
    .controller('signInController', SignInController)
    .controller('gameController', GameController);

export default moduleName;
