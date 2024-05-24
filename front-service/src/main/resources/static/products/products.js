angular.module('myMarket').controller('productController',
function ($scope, $http) {
    const contextPathCore = 'http://localhost:5555/core/api/v1';
    const contextPathCart = 'http://localhost:5555/cart/api/v1/current-cart';

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

    $scope.loadCategories();
    $scope.loadProducts();
});