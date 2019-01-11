//服务层
app.service('seckillService',function($http){
    this.findAll=function(){
        return $http.get('../seckill/findAll.do');
    }

    this.findPage=function(page,rows){
        return $http.get('../seckill/findPage.do?page='+page+'&rows='+rows);
    }
    this.search=function(page,rows,searchEntity){
        return $http.post('../seckill/search.do?page='+page+"&rows="+rows, searchEntity);
    }

    this.findById=function(id){
        return $http.get("../seckill/findById.do?id="+id);
    }

    this.save = function(entity){
        return $http.post("../seckill/save.do",entity);
    }

    this.update=function(entity){
        return $http.post("../seckill/update.do",entity);
    }
});