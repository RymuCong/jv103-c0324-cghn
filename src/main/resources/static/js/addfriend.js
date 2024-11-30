document.addEventListener('DOMContentLoaded', function () {
    var socket = new SockJS('/ws');
    var stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/user/topic/addfriend', function (notification) {
            var addFriendNotification = JSON.parse(notification.body);
            displayAddFriendNotification(addFriendNotification);
        });
    });

    function displayAddFriendNotification(addFriendNotification) {
        var addFriendNotificationHtml = `
            <div class="notification">
                <div class="notification-avatar">
                    <img src="${addFriendNotification.avatar}" alt="">
                </div>
                <div class="notification-text">
                    <strong>${addFriendNotification.name}</strong> đã gửi lời mời kết bạn
                </div>
                <div class="notification-actions">
                    <button id="accept-friend" data-friend-id="${addFriendNotification.id}" class="btn btn-primary">Chấp nhận</button>
                    <button id="decline-friend" data-friend-id="${addFriendNotification.id}" class="btn btn-secondary">Từ chối</button>
                </div>
            </div>
        `;
        var notificationList = document.querySelector('#new-notification');
        notificationList.insertAdjacentHTML('afterbegin', addFriendNotificationHtml);
    }

    document.querySelectorAll('#accept-friend').forEach(button => {
        button.addEventListener('click', function () {
            var idUser = this.getAttribute('data-friend-id');
            var isUser = {
                idUser: idUser
            };
            stompClient.send("/app/friend.accept", {}, JSON.stringify(isUser));
        });
    });

    document.querySelectorAll('#decline-friend').forEach(button => {
        button.addEventListener('click', function () {
            var idUser = this.getAttribute('data-friend-id');
            var isUser = {
                idUser: idUser
            };
            stompClient.send("/app/friend.decline", {}, JSON.stringify(isUser));
        });
    });

    document.querySelectorAll('.add-friend').forEach(button => {
        button.addEventListener('click', function () {
            const friendId = this.getAttribute('data-friend-id');
            stompClient.send("/app/user.addFriend", {}, friendId);
        });
    });
});