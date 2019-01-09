 //控制层 
app.controller('specificationController' ,function($scope,$controller,$http   ,specificationService){
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		specificationService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}

	//分页
	$scope.findPage=function(page,rows){			
		specificationService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		specificationService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}
	
	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.specification.id!=null){//如果有ID
			serviceObject=specificationService.update( $scope.entity ); //修改  
		}else{
			serviceObject=specificationService.add( $scope.entity  );//增加 
		}				
		serviceObject.success(
			function(response){
				if(response.success){
					//重新查询 
		        	$scope.reloadList();//重新加载
				}else{
					alert(response.message);
				}
			}		
		);				
	}
	
	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		specificationService.dele( $scope.selectIds ).success(
			function(response){
				if(response.success){
					$scope.reloadList();//刷新列表
					$scope.selectIds = [];
				}						
			}		
		);				
	}
	
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.search=function(page,rows){			
		specificationService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	
	
	$scope.addTableRow = function(){
		$scope.entity.specificationOptionList.push({});
	}
	
	$scope.deleteTableRow = function(index){
		$scope.entity.specificationOptionList.splice(index,1);
	}
    $scope.guige_asset = function () {
        $("#guige_asset").click();
    };
    $("#guige_asset").on("change", function(){
        var formData = new FormData();
        var file = document.getElementById("guige_asset").files[0];
        if(file.name){
            var fileName = file.name.substring(file.name.lastIndexOf(".") + 1);
            if(fileName =="xlsx" || fileName =="xls"){
                formData.append('file', file);
                $http({
                    method:"post",
                    url:'../specification/getBankListByExcel.do',
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
                $("#guige_asset").val("");
            }
        }
    });

});	
