//服务层
app.service('orderService',function($http){
    this.findAll=function(){
        return $http.get('../order/findAll.do');
    }

    this.findPage=function(page,rows){
        return $http.get('../order/findPage.do?page='+page+'&rows='+rows);
    }
    this.search=function(page,rows,searchEntity){
        return $http.post('../order/search.do?page='+page+"&rows="+rows, searchEntity);
    }

    this.findById=function(Id){
        return $http.get("../order/findById.do?Id="+Id);
    }
});