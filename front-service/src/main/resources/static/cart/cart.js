angular.module('myMarket').controller('cartController',
function ($scope, $http) {
    const contextPathCore = 'http://localhost:5555/core/api/v1';
    const contextPathCart = 'http://localhost:5555/cart/api/v1/current-cart';

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

// Оформить заказ
    $scope.placeAnOrder = function() {
        $http.get(contextPathCore + '/orders/create')
            .then(function successCallback(response) {
                $scope.loadCart();
                $scope.loadOrders();
            }, function errorCallback(response) {
                console.log(response);
                alert("Cart is empty!");
            });
    }

    $scope.guestPlaceAnOrder = function() {
        alert('To place an order, you need to log in to your account.');
    }

    $scope.loadCart();
});