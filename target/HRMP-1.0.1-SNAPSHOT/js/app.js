var app = angular.module("empApp", ['ngRoute']);

//全局服务
app.factory("empsService",[function (){
    var emps = null;
    var emp = null;
    return {
        emps : emps,
        emp:emp
    }
}]);

//路由
app.config(["$routeProvider","$locationProvider",function($routeProvider,$locationProvider){
    $locationProvider.hashPrefix('');
    $routeProvider
        .when('/', {
            templateUrl: "emps.html",
            controller: "empCtrl"
        })
        .when('/update', {
            templateUrl: "update_emp.html",
            controller: "updateCtrl"
        })
        .when('/save', {
            templateUrl: "new_emp.html",
        })
        .when('/queryById', {
            templateUrl: "query_by_id_emp.html",
            controller: "queryByCtrl"
        })
        .when('/queryById_B', {
            templateUrl: "query_by_id_emp.html",
            controller: "queryByCtrl"
        })
        .when('/queryByName', {
            templateUrl: "query_by_name_emp.html",
            controller: "queryByCtrl"
        })
        .when('/queryByName_B', {
            templateUrl: "query_by_name_emp.html",
            controller: "queryByCtrl"
        })
        .otherwise({
            redirectTo: '/emps'
        });
}]);