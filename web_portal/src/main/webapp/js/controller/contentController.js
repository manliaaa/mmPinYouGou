app.controller("contentController",function($scope,contentService){

	$scope.contentList = [];
	// 根据分类ID查询广告的方法:
	$scope.findByCategoryId = function(categoryId){
		contentService.findByCategoryId(categoryId).success(function(response){
			$scope.contentList[categoryId] = response;
		});
	}

    $scope.conList = [];
    // 根据分类ID查询广告的方法:
    $scope.findHome = function(categoryId){
        contentService.findHome(categoryId).success(function(response){
            $scope.conList[categoryId] = response;
        });
    }
    $scope.cList =[];
    // 根据分类ID查询广告的方法:
    $scope.selectItemCat1List = function(entity){
        contentService.selectItemCat1List(entity).success(function(response){
            $scope.cList[entity.name] = response;
        });
    }


//查询商品分类信息
    $scope.findItemCatList=function () {
        contentService.findItemCatList().success(function (response) {
            $scope.itemCatList=response;
        })
    }
	
	//搜索,跳转到portal系统查询列表页面(传递参数）
	$scope.search=function(){
		location.href="http://localhost:8080/search.html#?keywords="+$scope.keywords;
	}
	
});