angular.module('myMarket').controller('orderController',
function ($scope, $http) {
    const contextPathCore = 'http://localhost:5555/core/api/v1';

// Загрузить список заказов
    $scope.loadOrders = function() {
        $http.get(contextPathCore + '/orders').then(function(response) {
            $scope.OrdersList = response.data;
        });
    }

    $scope.loadOrders();
});