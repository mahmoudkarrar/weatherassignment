(function () {
    'use strict';

    var app = angular
        .module('app')
        .factory('UserService', UserService);

    app.config(function ($httpProvider) {
        $httpProvider.defaults.headers.common = {};
        $httpProvider.defaults.headers.post = {};
        $httpProvider.defaults.headers.get = {};
        $httpProvider.defaults.headers.put = {};
        $httpProvider.defaults.headers.patch = {};
    });

    UserService.$inject = ['$http'];
    function UserService($http) {
        var service = {};


        service.GetByEmail = GetByEmail;
        service.Create = Create;

        service.GetWeather = GetWeather;
        service.GetTemperatureNote = GetTemperatureNote;
        service.SaveNote = SaveNote;
        service.GetAdminNotes = GetAdminNotes;
        service.GetDefaultNotes = GetDefaultNotes;
        return service;


        function GetByEmail(email) {
            return $http.post('/users/login', {email: email}, {headers: {'Content-Type': 'application/json'}}).then(handleSuccess, handleError('Error getting user by email'));
        }

        function Create(user) {

            return $http.post('/users/create', {
                    userName: user.userName,
                    email: user.email,
                    admin: 'N',
                    password: user.password,
                    mobileNumber: user.mobile
                },
                {headers: {'Content-Type': 'application/json'}}).then(handleSuccess, handleError('Error creating user '));
        }


        function GetWeather(city, country, callback) {

            //  $http.defaults.headers.common['Access-Control-Allow-Origin'] = '*';

            return $http({
                method: 'POST',
                url: 'http://www.webservicex.net/globalweather.asmx/GetWeather',
                timeout: 10000,
                data: {'CityName': city, 'CountryName': country},  // Query Parameters (GET)
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                    'Access-Control-Allow-Origin': '*',
                    'Access-Control-Allow-Methods': 'DELETE, HEAD, GET, OPTIONS, POST, PUT',
                    'Access-Control-Allow-Headers': 'Content-Type, Content-Range, Content-Disposition, Content-Description',
                    'Access-Control-Max-Age': '1728000'
                },
                transformResponse: function (data) {
                    // string -> XML document object
                    console.log(data);
                    return data;
                }
            }).success(function (data) {
                console.log(data);  // XML document object
                handleSuccess(data);
            }).error(function (data) {
                console.error(data);
                handleError(data);
            });
            //   response.temprature = response.data.response;

            //          response = { success: true, temperature: 1 };
            //           }, function errorCallback(response) {
            //             response.temprature =1;
            //           response.success = true;
            //     });;

        }

        function GetTemperatureNote(temp) {
            return $http.get('/temperature/note/' + temp).then(handleSuccess, handleError('Error getting note'));

        }

        function SaveNote(note, user_id) {
            console.log('user id ' + user_id);
            return $http.post('/temperature/create', {note: note, userId: user_id},
                {headers: {'Content-Type': 'application/json'}}).then(handleSuccess, handleError('Error saving note'));

        }

///temperature
        function GetAdminNotes(userId) {
            return $http.get('/temperature/allnotes/' + userId).then(handleSuccess, handleError('Error getting admin weather notes'));
        }

        function GetDefaultNotes() {
            return $http.get('/temperature/defaultnotes').then(handleSuccess, handleError('Error getting default weather notes'));
        }

        function handleSuccess(res) {
            return res.data;
        }

        function handleError(error) {
            return function () {
                return {success: false, message: error};
            };
        }
    }

})();
