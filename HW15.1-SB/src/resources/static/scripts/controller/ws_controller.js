function WsCtrl($scope) {
    var vm = this;
    vm.users = [];

    angular.element('.modal').modal();

    function setConnected(connected) {
        angular.element("#connect").toggleClass("disabled")
        angular.element("#disconnect").toggleClass("disabled")
    }

    vm.connectWs = function () {
        stompClient = Stomp.over(new SockJS('/hw-ms-websocket'));
        stompClient.connect({}, function (frame) {
            setConnected(true);
            sendConnect();
            console.log('connected: ' + frame);
            stompClient.subscribe('/topic/response', function (msg) {
                vm.revertUsers(JSON.parse(msg.body).users)
            });
        });
    };

    function sendConnect() {
        stompClient.send("/app/connect", {}, JSON.stringify({'method': 'connect'}));
    }

    vm.submitUser = function() {
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
        setConnected(false);
        console.log("Disconnected");
    };

    vm.sendMsg = function sendMsg() {
        stompClient.send("/app/message", {}, JSON.stringify({'method': 'get'}));
    };

    vm.deleteUser = function (user, index) {
        stompClient.send("/app/delete", {}, JSON.stringify({'user': user}));
    };

    vm.revertUsers = function (users) {
        vm.users = users;
        $scope.$apply();
    }

    function _clearFormData() {
        $scope.userForm.id = null;
        $scope.userForm.surname = "";
        $scope.userForm.name = ""
    }
}

angular
    .module('UserWsApp', [])
    .controller('WsCtrl', WsCtrl);

function init_modal() {
    angular.element('.modal').modal();
}