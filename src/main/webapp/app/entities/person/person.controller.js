(function() {
    'use strict';

    angular
        .module('pocApp')
        .controller('PersonController', PersonController);

    PersonController.$inject = ['$scope', '$state', 'Person'];

    function PersonController ($scope, $state, Person) {
        var vm = this;

        vm.people = [];

        loadAll();

        function loadAll() {
            Person.query(function(result) {
                vm.people = result;
                vm.searchQuery = null;
            });
        }
    }
})();
