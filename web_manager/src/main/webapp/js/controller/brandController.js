// 定义控制器:
app.controller("brandController",function($scope,$controller,$http,brandService){
	// AngularJS中的继承:伪继承
	$controller('baseController',{$scope:$scope});
	
	// 查询所有的品牌列表的方法:
	$scope.findAll = function(){
		// 向后台发送请求:
		brandService.findAll().success(function(response){
			$scope.list = response;
		});
	}
    $scope.upload = function(){
        var form = new FormData();
        var file = document.getElementById("fileUpload").files[0];
        form.append('file', file);
        $http({
            method: 'POST',
            url: '../brand/getBankListByExcel.do',
            data: form,
            headers: {'Content-Type': undefined},
            transformRequest: angular.identity
        }).success(function (data) {
            console.log('upload success');
        }).error(function (data) {
            console.log('upload fail');
        })
    }
    $scope.import_asset = function () {
        $("#file_asset").click();
    };
    $("#file_asset").on("change", function(){
        var formData = new FormData();
        var file = document.getElementById("file_asset").files[0];
        if(file.name){
            var fileName = file.name.substring(file.name.lastIndexOf(".") + 1);
            if(fileName =="xlsx" || fileName =="xls"){
                formData.append('file', file);
                $http({
                    method:"post",
                url:'../brand/getBankListByExcel.do',
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
                $("#file_asset").val("");
            }
        }
    });


    // 分页查询
	$scope.findByPage = function(page,rows){
		// 向后台发送请求获取数据:
		brandService.findByPage(page,rows).success(function(response){
			$scope.paginationConf.totalItems = response.total;
			$scope.list = response.rows;
		});
	}
	
	// 保存品牌的方法:
	$scope.save = function(){
		// 区分是保存还是修改
		var object;
		if($scope.entity.id != null){
			// 更新
			object = brandService.update($scope.entity);
		}else{
			// 保存
			object = brandService.save($scope.entity);
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
	
	// 查询一个:
	$scope.findById = function(id){
		brandService.findById(id).success(function(response){
			// {id:xx,name:yy,firstChar:zz}
			$scope.entity = response;
		});
	}
	
	// 删除品牌:
	$scope.dele = function(){
		brandService.dele($scope.selectIds).success(function(response){
			// 判断保存是否成功:
			if(response.success==true){
				// 保存成功
				// alert(response.message);
				$scope.reloadList();
				$scope.selectIds = [];
			}else{
				// 保存失败
				alert(response.message);
			}
		});
	}
	
	$scope.searchEntity={};
	
	// 假设定义一个查询的实体：searchEntity
	$scope.search = function(page,rows){
		// 向后台发送请求获取数据:
		brandService.search(page,rows,$scope.searchEntity).success(function(response){
			$scope.paginationConf.totalItems = response.total;
			$scope.list = response.rows;
		});
	}
	//销售查询
    $scope.findtu = function(){
        // 向后台发送请求:
        brandService.findtu().success(function(response){
            $scope.list = response;
        });
    }
	
});
