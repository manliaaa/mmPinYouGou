//控制层
app.controller('seckillController' ,function($scope,$controller,$location,typeTemplateService ,itemCatService,uploadService ,seckillService){

    $controller('baseController',{$scope:$scope});//继承

    //读取列表数据绑定到表单中
    $scope.findAll=function(){
        seckillService.findAll().success(
            function(response){
                $scope.list=response;
            }
        );
    }

    $scope.searchEntity={};//定义搜索对象

    //搜索
    $scope.search=function(page,rows){
        seckillService.search(page,rows,$scope.searchEntity).success(
            function(response){
                $scope.list=response.rows;
                $scope.paginationConf.totalItems=response.total;//更新总记录数
            }
        );
    }

    // 查询一个:
    $scope.findById = function(id){
        seckillService.findById(id).success(function(response){
            // {id:xx,name:yy,firstChar:zz}
            $scope.entity = response;
        });
    }
});