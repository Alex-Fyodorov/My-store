(function () {
    angular
        .module('myMarket', ['ngRoute', 'ngStorage'])
        .config(config)
        .run(run);

    function config($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'welcome/welcome.html',
                controller: 'welcomeController'
            })
            .when('/store', {
                templateUrl: 'products/products.html',
                controller: 'productController'
            })
            .when('/cart', {
                templateUrl: 'cart/cart.html',
                controller: 'cartController'
            })
            .when('/orders', {
                templateUrl: 'orders/orders.html',
                controller: 'orderController'
            })
            .otherwise({
                redirectTo: '/'
            });
    }

//При перезагрузке заставляет снова подвязывать токен к запросам
    function run($rootScope, $http, $localStorage) {
        if ($localStorage.springWebUser) {
            try {
                let jwt = $localStorage.springWebUser.token;
                let payload = JSON.parse(atob(jwt.split('.')[1]));
                let currentTime = parseInt(new Date().getTime() / 1000);
                if (currentTime > payload.exp) {
                    console.log("Token is expired!!!");
                    delete $localStorage.springWebUser.token;
                    $http.defaults.headers.common.Authorization = '';
                    $scope.tryToLogout();
                }
            } catch (e) {
            }

            if ($localStorage.springWebUser) {
                $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.springWebUser.token;
            }

//            if (!$localStorage.marchMarketGuestCartId) {
//                $http.get('http://localhost:5555/cart/api/v1/cart/generate_id')
//                    .then(function (response) {
//                        $localStorage.marchMarketGuestCartId = response.data.value;
//                    });
//            }
        }
    }
})();



angular
    .module('myMarket', ['ngStorage'])
    .controller('indexController',
        function ($scope, $rootScope, $http, $localStorage, $location) {
    const contextPathCore = 'http://localhost:5555/core/api/v1';
    const contextPathCart = 'http://localhost:5555/cart/api/v1/current-cart';

//При перезагрузке заставляет снова подвязывать токен к запросам
//    if ($localStorage.springWebUser) {
//        try {
//            let jwt = $localStorage.springWebUser.token;
//            let payload = JSON.parse(atob(jwt.split('.')[1]));
//            let currentTime = parseInt(new Date().getTime() / 1000);
//            if (currentTime > payload.exp) {
//                console.log("Token is expired!!!");
//                alert("Token is expired!!!");
//                $scope.tryToLogout();
//            } else {
//                $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.springWebUser.token;
//            }
//        } catch (e) {
//        }
//    }

//Вход в учётную запись
    $scope.tryToAuth = function() {
        $http.post('http://localhost:5555/auth/authenticate', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.springWebUser = {username: $scope.user.username, token: response.data.token};
                    $scope.user.username = null;
                    $scope.user.password = null;
                    $location.path('/');
                }
            }, function errorCallback(response) {
                alert("Incorrect username or password.")
            });
    };

//Получение имени пользователя
//    $scope.getUsername = function() {
//        return $localStorage.springWebUser.username;
//    };

//Выход из учётной записи
    $scope.tryToLogout = function() {
        delete $localStorage.springWebUser;
        $http.defaults.headers.common.Authorization = '';
        if ($scope.user.username) {
            $scope.user.username = null;
        }
        if ($scope.user.password) {
            $scope.user.password = null;
        }
        $location.path('/');
    };

//Проверка, вошёл ли пользователь
    $rootScope.isUserLoggedIn = function() {
        if ($localStorage.springWebUser) {
            return true;
        } else {
            return false;
        }
    };

//Регистрация нового пользователя
    $scope.tryToRegistration = function() {
        $http.post('http://localhost:5555/auth/api/v1/users', $scope.user)
            .then(function successCallback(response) {
                $rootScope.isNeedNewUser = false;
                $scope.tryToAuth();
                $scope.user.email = null;
            }, function errorCallback(response) {
                //console.log(response);
                alert("Что-то пошло не так. Звиняйте!");
            });
    };

//Заявка на регистрацию нового пользователя
    $rootScope.requestNewUser = function() {
        if ($rootScope.isNeedNewUser) {
            $rootScope.isNeedNewUser = false;
        } else {
            $rootScope.isNeedNewUser = true;
        }
    };

//Проверка, нужна ли регистрация нового пользователя
    $rootScope.isNeedToAddNewUser = function() {
        return $rootScope.isNeedNewUser;
    };
});