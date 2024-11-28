document.addEventListener('DOMContentLoaded', function () {
    var socket = new SockJS('/ws/notification');
    var stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/user/topic/notification', function (payload) {
            var notification = JSON.parse(payload.body);
            showNotification(notification);
        });
    });

    function showNotification(notification) {
        var notificationHtml = `
            <li>
                <div>
                    <span class="notification-avatar">
                        <img src="${notification.senderAvatar}" alt="">
                    </span>
                    <span class="notification-text">
                        <strong>${notification.senderName}</strong>
                        <span class="text-primary">${notification.message}</span>
                        <br><span class="text-primary">của bạn</span>
                        <br><span class="time-ago">${notification.timeDifference}</span>
                    </span>
                </div>
            </li>
        `;
        var notificationList = document.querySelector('.dropdown-notifications-content ul');
        notificationList.insertAdjacentHTML('afterbegin', notificationHtml);

        // Increment notification count
        var notificationCountElement = document.querySelector('.opts_icon span');
        var notificationCount = parseInt(notificationCountElement.textContent);
        notificationCountElement.textContent = notificationCount + 1;

        // Display notification for 5 seconds
        setTimeout(function () {
            notificationList.removeChild(notificationList.firstChild);
            notificationCountElement.textContent = notificationCount;
        }, 5000);
    }
});