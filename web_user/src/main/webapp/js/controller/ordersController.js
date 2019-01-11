//控制层
app.controller('ordersController' ,function($scope,$controller,ordersService) {
    $controller('baseController', {$scope: $scope});//继承

    $scope.searchEntity={};
    //搜索
    $scope.search=function(page,rows){
        ordersService.search(page,rows,$scope.searchEntity).success(
            function(response){
                $scope.list=response.rows;
                $scope.paginationConf.totalItems=response.total;//更新总记录数
            }
        );
    }




});