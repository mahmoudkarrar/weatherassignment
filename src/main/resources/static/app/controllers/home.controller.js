(function () {
    'use strict';

    angular
        .module('app')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['UserService', '$rootScope', 'FlashService'];
    function HomeController(UserService, $rootScope, FlashService) {
        var vm = this;

        vm.user = null;
        vm.notes = [];
        vm.defaultNotes = [];
        vm.getWeather = getWeather;
        vm.temp = null;
        vm.note = null;
        vm.admin = null;
        vm.saveNote = saveNote;
        vm.setDefaultAsTodayNote = setDefaultAsTodayNote;
        initController();

        function initController() {
            loadCurrentUser();

        }

        function loadCurrentUser() {

            UserService.GetByEmail($rootScope.globals.currentUser.email)
                .then(function (user) {
                    vm.user = user;
                    vm.user_id = user.id;
                    vm.admin = user.admin === 'Y';
                    if (vm.admin) {
                        UserService.GetAdminNotes(user.id).then(function (data) {
                            vm.notes = data;
                        });

                        UserService.GetDefaultNotes().then(function (data) {
                            vm.defaultNotes = data;
                        });
                    }
                });

        }


        function getWeather() {

            vm.dataLoading = true;
            UserService.GetWeather(vm.city, vm.country, function (response) {
                if (response.success) {
                    //get notes
                    vm.temp = response.temperature;
                    UserService.GetTemperatureNote(vm.temp, function (note) {
                        console.log('note ' + note);
                        vm.note = note;
                    });
                } else {
                    FlashService.Error('No Data available');

                }
            });
        }

        function saveNote() {
            //  alert('save note');
            UserService.SaveNote(vm.note, vm.user_id)
                .then(function (response) {
                    if (response) {
                        FlashService.Success('Note saved successful', true);
                        vm.notes = response;

                    } else {
                        FlashService.Error(response.message);
                    }
                });
        }

        function setDefaultAsTodayNote(note) {

            //alert(note);
            UserService.SaveNote(note, vm.user_id)
                .then(function (response) {
                    if (response) {
                        FlashService.Success('Note saved successful', true);
                        vm.notes = response;

                    } else {
                        FlashService.Error(response.message);
                    }
                });
        }

    }

})();