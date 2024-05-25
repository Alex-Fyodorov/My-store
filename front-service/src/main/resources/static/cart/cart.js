angular.module('myMarket')
    .controller('cartController', function ($scope, $http, $location, $localStorage) {
    const contextPathCore = 'http://localhost:5555/core/api/v1';
    const contextPathCart = 'http://localhost:5555/cart/api/v1/current-cart/';

// Изменить количество продуктов в корзине или положить продукт в корзину
    $scope.changeQuantity = function (productId, delta) {
        $http({
            url:contextPathCart + $localStorage.myMarketGuestCartId + '/add',
            method: 'get',
            params: {
                product_id: productId,
                delta: delta
            }
        }).then(function(response) {
            $scope.loadCart();
        });
    };

// Загрузка списка корзины
    $scope.loadCart = function () {
        $http.get(contextPathCart + $localStorage.myMarketGuestCartId)
            .then(function(response) {
                console.log(response.data);
                $scope.CartList = response.data.items;
                $scope.totalPrice = response.data.totalPrice;
        });
    };

// Удалить продукт из корзины
    $scope.deleteFromCart = function (productId) {
        $http.get(contextPathCart + $localStorage.myMarketGuestCartId + '/delete/' + productId)
            .then(function(response) {
                $scope.loadCart();
        });
    };

// Оформить заказ часть 1
    $scope.placeAnOrderPartOne = function() {
        if ($scope.CartList.length > 0) {
            $location.path('/create_order');
        } else {
            alert("Cart is empty!");
        }
    };

// Оформить заказ часть 2
    $scope.placeAnOrderPartTwo = function() {
        $http.post(contextPathCore + '/orders/create/' + $localStorage.myMarketGuestCartId, $scope.newOrder)
            .then(function successCallback(response) {
                $scope.loadCart();
                $location.path('/');
            }, function errorCallback(response) {
                console.log(response);
                if (response.data.errorFieldsMessages) {
                    alert(response.data.errorFieldsMessages);
                } else {
                    alert("None of the fields are filled in!");
                }
            });
    };

// При попытке оформить заказ, не войдя в учётную запись
    $scope.guestPlaceAnOrder = function() {
        alert('To place an order, you need to log in to your account.');
    };

    $scope.loadCart();
});