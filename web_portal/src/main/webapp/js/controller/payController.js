app.controller('payController' ,function($scope ,$location,payService,$interval,$http){
	
	
	$scope.createNative=function(){
		payService.createNative().success(
			function(response){
				
				//显示订单号和金额
				$scope.money= (response.total_fee/100).toFixed(2);
				$scope.out_trade_no=response.out_trade_no;
				
				//生成二维码
				 var qr=new QRious({
					    element:document.getElementById('qrious'),
						size:250,
						value:response.code_url,
						level:'H'
			     });
				 
				 queryPayStatus();//调用查询
				
			}	
		);	
	}
	
	//调用查询
	queryPayStatus=function(){
		payService.queryPayStatus($scope.out_trade_no).success(
			function(response){
				if(response.success){
					location.href="paysuccess.html#?money="+$scope.money;
				}else{
					if(response.message=='二维码超时'){
						$scope.createNative();//重新生成二维码
					}else{
						location.href="payfail.html";
					}
				}				
			}		
		);		
	}
    $scope.time= 30*60*1000; //30分钟

    $one_minute = $interval(function(){
        $scope.time -= 1000;
        var out_trade_no = $scope.out_trade_no;
        if($scope.time == 0){
            $http({
                method:"get",
                url:'../pay/PayStatus.do?out_trade_no='+out_trade_no,
                data:"",
            }).then(function (response) {
            });
		}
    },1000);

});