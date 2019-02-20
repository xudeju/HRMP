//导航控制器
app.controller("navaCtrl",["$scope",function ($scope) {
    $scope.username = sessionStorage.username;
}]);

//全部emp子页控制器
app.controller("empCtrl",["$scope","$http","empsService",function($scope,$http,empsService){
    //找到全部emp
    $http.get("http://localhost:8080/HRM/EmpFindAllServlet").then(function(res){
        $scope.emps = res.data.data;
    },function(err){
        sessionStorage.errCode = "404";
        sessionStorage.errMsg = "findAll error";
        window.location.href = "http://localhost:8080/HRM/error.html";
    });

    //详情flag
    $scope.showDetail = function(){
        if(this.flag == null){
            this.flag = true;
        }else if(this.flag == true){
            this.flag = false;
        }else{
            this.flag = true;
        }
    }

    //删除
    $scope.deleteBtn = function (empno) {
        $http.post("http://localhost:8080/HRM/EmpDeleteServlet?empno="+empno).then(function(res){
            var result = res.data;
            if(result.code == 200){
                alert(result.msg);
                window.location.href = "http://localhost:8080/HRM/emp_main.html";
            }else{
                alert(result.msg);
            }
        },function(err){
            sessionStorage.errCode = "404";
            sessionStorage.errMsg = "delete error";
            window.location.href = "http://localhost:8080/HRM/error.html";
        });
    }

    //更新
    $scope.updateBtn = function(emp) {
        empsService.emp = emp;
        window.location.href = "#/update";
    }

}]);

//底部查询控制器
app.controller("searchCtrl",["$scope","$http","empsService",function($scope,$http,empsService){
    //根据条件查找
    var url = "http://localhost:8080/HRM/EmpFindAllServlet";
    var id_link = null;
    var name_link = null;
    $scope.queryBtn = function () {
        //find by id页面路由用flag
        if(id_link == null){
            id_link = "#/queryById";
        }else if(id_link == "#/queryById"){
            id_link = "#/queryById_B";
        }else if(id_link == "#/queryById_B"){
            id_link = "#/queryById";
        }

        //find by name页面路由用flag
        if(name_link == null){
            name_link = "#/queryByName";
        }else if(name_link == "#/queryByName"){
            name_link = "#/queryByName_B";
        }else if(name_link == "#/queryByName_B"){
            name_link = "#/queryByName";
        }

        //判断查找类型并拼接url用于get请求
        if($scope.queryType == "empno"){
            url = "http://localhost:8080/HRM/EmpFindByIdServlet?empno="+$scope.queryCon;
        }else if($scope.queryType == "ename"){
            url = "http://localhost:8080/HRM/EmpFindByNameServlet?ename="+$scope.queryCon;
        }
        //请求数据
        $http.get(url).then(function(res){
            empsService.emps = res.data.data;
            if($scope.queryType == "empno"){
                window.location.href = id_link;
            }else if($scope.queryType == "ename"){
                window.location.href = name_link;

            }
        },function(err){
            sessionStorage.errCode = "404";
            sessionStorage.errMsg = "find by id/name error";
            window.location.href = "http://localhost:8080/HRM/error.html";
        });
    }
}]);

//条件查找子页控制器
app.controller("queryByCtrl",["$scope","$http","empsService",function($scope,$http,empsService){
    $scope.emps = empsService.emps;

    //详情flag
    $scope.showDetail = function(event){
        if(this.flag == null){
            this.flag = true;
        }else if(this.flag == true){
            this.flag = false;
        }else{
            this.flag = true;
        }
    }

    //删除
    $scope.deleteBtn = function (empno) {
        $http.post("http://localhost:8080/HRM/EmpDeleteServlet?empno="+empno).then(function(res){
            var result = res.data;
            if(result.code == 200){
                alert(result.msg);
                window.location.href = "http://localhost:8080/HRM/emp_main.html";
            }else{
                alert(result.msg);
            }
        },function(err){
            sessionStorage.errCode = "404";
            sessionStorage.errMsg = "delete error";
            window.location.href = "http://localhost:8080/HRM/error.html";
        });
    }
    //更新
    $scope.updateBtn = function(emp) {
        empsService.emp = emp;
        window.location.href = "#/update";
    }
}]);

//更新子页控制器
app.controller("updateCtrl",["$scope","$http","empsService",function($scope,$http,empsService){
    $scope.updateEmp = empsService.emp;
}]);
