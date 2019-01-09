 //控制层 
app.controller('itemCatController' ,function($scope,$controller,$http   ,itemCatService){
	
	$controller('baseController',{$scope:$scope});//继承
	
    // //读取列表数据绑定到表单中
	// $scope.findAll=function(){
	// 	itemCatService.findAll().success(
	// 		function(response){
	// 			$scope.list=response;
	// 		}
	// 	);
	// }
	//
	// //分页
	// $scope.findPage=function(page,rows){
	// 	itemCatService.findPage(page,rows).success(
	// 		function(response){
	// 			$scope.list=response.rows;
	// 			$scope.paginationConf.totalItems=response.total;//更新总记录数
	// 		}
	// 	);
	// }
	//
	// //查询实体
	// $scope.findOne=function(id){
	// 	itemCatService.findOne(id).success(
	// 		function(response){
	// 			$scope.entity= response;
	// 		}
	// 	);
	// }
	
	// //保存
	// $scope.save=function(){
	// 	var serviceObject;//服务层对象
	// 	if($scope.entity.id!=null){//如果有ID
	// 		serviceObject=itemCatService.update( $scope.entity ); //修改
	// 	}else{
	// 		serviceObject=itemCatService.add( $scope.entity  );//增加
	// 	}
	// 	serviceObject.success(
	// 		function(response){
	// 			if(response.success){
	// 				//重新查询
	// 	        	$scope.reloadList();//重新加载
	// 			}else{
	// 				alert(response.message);
	// 			}
	// 		}
	// 	);
	// }
	//
	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		itemCatService.dele( $scope.selectIds ).success(
			function(response){
				if(response.success){
					$scope.reloadList();//刷新列表
					$scope.selectIds = [];
				}						
			}		
		);				
	}
    $scope.fenlei_asset = function () {
        $("#fenlei_asset").click();
    };
    $("#fenlei_asset").on("change", function(){
        var formData = new FormData();
        var file = document.getElementById("fenlei_asset").files[0];
        if(file.name){
            var fileName = file.name.substring(file.name.lastIndexOf(".") + 1);
            if(fileName =="xlsx" || fileName =="xls"){
                formData.append('file', file);
                $http({
                    method:"post",
                    url:'../itemCat/getBankListByExcel.do',
                    data:formData,
                    headers : {
                        'Content-Type' : undefined
                    },
                    transformRequest : angular.identity
                }).then(function (response) {

                    if(response.status == 200){
                        alert("文件上传成功！！！");
                        window.location.reload();
                    }else{
                        alert("文件上传失败！！！");
                    }
                });
            }else{
                alert("文件格式不正确，请上传以.xlsx，.xls 为后缀名的文件。");
                $("#fenlei_asset").val("");
            }
        }
    });
	
	// $scope.searchEntity={};//定义搜索对象
	//
	// //搜索
	// $scope.search=function(page,rows){
	// 	itemCatService.search(page,rows,$scope.searchEntity).success(
	// 		function(response){
	// 			$scope.list=response.rows;
	// 			$scope.paginationConf.totalItems=response.total;//更新总记录数
	// 		}
	// 	);
	// }
	//
	// 根据父ID查询分类
	// $scope.findByParentId =function(parentId){
	// 	itemCatService.findByParentId(parentId).success(function(response){
	// 		$scope.list=response;
	// 	});
	// }
	
	// 定义一个变量记录当前是第几级分类
	$scope.grade = 1;
	
	$scope.setGrade = function(value){
		$scope.grade = value;
	}
	
	$scope.selectList = function(p_entity){
		
		if($scope.grade == 1){
			$scope.entity_1 = null;
			$scope.entity_2 = null;
		}
		if($scope.grade == 2){
			$scope.entity_1 = p_entity;
			$scope.entity_2 = null;
		}
		if($scope.grade == 3){
			$scope.entity_2 = p_entity;
		}
		
		$scope.findByParentId(p_entity.id);
	}
    $scope.parentId=0;//上级ID

    //根据上级ID显示下级列表
    $scope.findByParentId=function(parentId){
        $scope.parentId=parentId;//记住上级ID
        itemCatService.findByParentId(parentId).success(
            function(response){
                $scope.list=response;
            }
        );
    }
    //保存
    $scope.save=function(){
        var serviceObject;//服务层对象
        if($scope.entity.id!=null){//如果有ID
            serviceObject=itemCatService.update( $scope.entity ); //修改
        }else{
            $scope.entity.parentId=$scope.parentId;//赋予上级ID
            serviceObject=itemCatService.add( $scope.entity  );//增加
        }
        serviceObject.success(
            function(response){
                if(response.success){
                    //重新查询
                    $scope.findByParentId($scope.parentId);//重新加载
                }else{
                    alert(response.message);
                }
            }
        );
    }
    $scope.specList={data:[]}
    // 查询关联的品牌信息:
    $scope.findSpecList = function(){
        itemCatService.selectOptionList().success(function(response){
            $scope.specList = {data:response};
        });
    }









});	
