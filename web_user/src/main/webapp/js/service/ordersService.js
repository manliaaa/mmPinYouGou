//服务层
app.service('ordersService',function($http){
    //搜索
    this.search=function(page,rows,searchEntity){
        return $http.post('../user/search.do?page='+page+"&rows="+rows, searchEntity);
    }


    // 地址管理
    this.addresses=function(searchEntity){
        return $http.post('../user/addresses.do',searchEntity);
    }
});