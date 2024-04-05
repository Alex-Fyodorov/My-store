angular.module('appProduct', ['ngStorage']).controller('productController',
function ($scope, $rootScope, $http, $localStorage) {
    const contextPathCore = 'http://localhost:8190/my-store-core/api/v1';
    const contextPathCart = 'http://localhost:8191/cart/api/v1/current-cart';

//При перезагрузке заставляет снова подвязывать токен к запросам
    if ($localStorage.springWebUser) {
        try {
            let jwt = $localStorage.springWebUser.token;
            let payload = JSON.parse(atob(jwt.split('.')[1]));
            let currentTime = parseInt(new Date().getTime() / 1000);
            if (currentTime > payload.exp) {
                console.log("Token is expired!!!");
                alert("Token is expired!!!");
                $scope.tryToLogout();
            } else {
                $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.springWebUser.token;
            }
        } catch (e) {
        }
    }

//Вход в учётную запись
    $scope.tryToAuth = function() {
        $http.post('http://localhost:8190/my-store-core/auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.springWebUser = {username: $scope.user.username, token: response.data.token};
                    $scope.user.username = null;
                    $scope.user.password = null;
                    $scope.loadCart();
                }
            }, function errorCallback(response) {
                alert("Incorrect username or password.")
            });
    };

//Получение имени пользователя
    $scope.getUsername = function() {
        return $localStorage.springWebUser.username;
    };

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
        $http.post(contextPathCore + '/users', $scope.user)
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

//Загрузка списка категорий
    $scope.loadCategories = function() {
        $http.get(contextPathCore + '/categories')
            .then(function(response) {
                console.log(response.data);
                $CategoriesList = response.data;
            });
    };

//Загрузка списка продуктов
    $scope.loadProducts = function () {
        $http({
            url: contextPathCore + '/products',
            method: 'get',
            params: {
                page: $scope.loadProducts ? $scope.loadProducts.page : 1,
                sort: $scope.loadProducts ? $scope.loadProducts.sort : null,
                title_part: $scope.loadProducts ? $scope.loadProducts.title_part : null,
                min_price: $scope.loadProducts ? $scope.loadProducts.min_price : null,
                max_price: $scope.loadProducts ? $scope.loadProducts.max_price : null,
                category_id: $scope.loadProducts ? $scope.loadProducts.category_id : null
            }
        }).then(function(response) {
            console.log(response.data);
            $scope.ProductsList = response.data.content;
            $scope.totalPages = response.data.totalPages;
        });
    }

//Добавить продукт
    $scope.addProduct = function () {
        $http.post(contextPathCore + '/products', $scope.newProduct)
            .then(function(response) {
                $scope.loadProducts();
                $scope.newProduct = null;
            });
    }

//Удалить продукт
    $scope.deleteProduct = function (productId) {
        $http.delete(contextPathCore + '/products/' + productId).then(function(response) {
            $scope.loadProducts();
        });
    }

//Изменить цену продукта
    $scope.changePrice = function (productId, newPrice) {
        $http({
            url:contextPathCore + '/products',
            method: 'patch',
            params: {
                product_id: productId,
                new_price: newPrice
            }
        }).then(function(response) {
            console.log(productId, newPrice);
            //console.log(newPrice);
            $scope.loadProducts();
        });
    }

//Обнуление фильтров
    $scope.reset = function () {
        $scope.loadProducts.title_part = null;
        $scope.loadProducts.min_price = null;
        $scope.loadProducts.max_price = null;
        $scope.loadProducts.category_id = null;
        $scope.loadProducts();
    }

//Сортировка
    $scope.sort = function (s) {
        $scope.loadProducts.sort = s;
        $scope.loadProducts();
    }

//Переключение страниц
    $scope.page = function (p) {
    $scope.loadProducts.page = p;
    $scope.loadProducts();
    }

//Изменить количество продуктов в корзине или положить продукт в корзину
    $scope.changeQuantity = function (productId, delta) {
        $http({
            url:contextPathCart + '/add',
            method: 'get',
            params: {
                product_id: productId,
                delta: delta
            }
        }).then(function(response) {
            $scope.loadCart();
        });
    }

//Загрузка списка корзины
    $scope.loadCart = function () {
        $http.get(contextPathCart).then(function(response) {
            console.log(response.data);
            $scope.CartList = response.data.items;
            $scope.totalPrice = response.data.totalPrice;
        });
    }

//Удалить продукт из корзины
    $scope.deleteFromCart = function (productId) {
        $http.get(contextPathCart + '/delete/' + productId).then(function(response) {
            $scope.loadCart();
        });
    }

    $scope.loadCategories();
    $scope.loadProducts();
    $scope.loadCart();
});