function WsCtrl($scope) {
    var vm = this;
    vm.users = [];

    angular.element('.modal').modal();
    setConnect();

    function setConnectedElements() {
        angular.element(".manipulate").toggleClass("disabled");
    }

    function setConnect() {
        stompClient = Stomp.over(new SockJS('/hw-ms-websocket'));
        stompClient.connect({}, function (frame) {
            setConnectedElements();
            sendConnect();
            console.log('connected: ' + frame);
            stompClient.subscribe('/topic/response', function (msg) {
                vm.setUsers(JSON.parse(msg.body).users)
            });
        });
    }

    vm.connectWs = function () {
        setConnect();
    };

    function sendConnect() {
        stompClient.send("/app/connect", {}, JSON.stringify({'method': 'connect'}));
    }

    vm.submitUser = function () {
        stompClient.send("/app/save", {}, JSON.stringify({'user': $scope.userForm}));
        _clearFormData();
    };

    vm.editUser = function (user) {
        $scope.userForm.id = user.id;
        $scope.userForm.surname = user.surname;
        $scope.userForm.name = user.name;
    };

    vm.disconnectWs = function () {
        if (stompClient !== null) {
            stompClient.disconnect();
        }
        setConnectedElements();
        console.log("Disconnected");
    };

    vm.deleteUser = function (user, index) {
        stompClient.send("/app/delete", {}, JSON.stringify({'user': user}));
    };

    vm.setUsers = function (users) {
        vm.users = users;
        $scope.$apply();
    };

    function _clearFormData() {
        $scope.userForm.id = null;
        $scope.userForm.surname = "";
        $scope.userForm.name = ""
    }
}

angular
    .module('UserWsApp', [])
    .controller('WsCtrl', WsCtrl);
