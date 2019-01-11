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

    // 保存品牌的方法:
    $scope.save = function(){
        // 区分是保存还是修改
        var object;
        if($scope.entity.id != null){
            // 更新
            object = seckillService.update($scope.entity);
        }else{
            // 保存
            object = seckillService.save($scope.entity);
        }
        object.success(function(response){
            // {success:true,message:xxx}
            // 判断保存是否成功:
            if(response.success==true){
                // 保存成功
                alert(response.message);
                $scope.reloadList();
            }else{
                // 保存失败
                alert(response.message);
            }
        });
    }
});