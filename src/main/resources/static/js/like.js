document.addEventListener('DOMContentLoaded', function () {
    var socket = new SockJS('/ws/like');
    var stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/like_post', function (payload) {
            var likeResponse = JSON.parse(payload.body);
            loadLikeCount(likeResponse);
        });
    });

    function loadLikeCount(likeResponse) {
        var postId = likeResponse.postId;
        document.getElementById('like-count-' + postId).innerText = likeResponse.likeCount;
        var likeButton = document.querySelector('.like-button[data-post-id="' + postId + '"]');
        likeButton.classList.add('text-primary');
    }

    document.querySelectorAll('.like-button').forEach(button => {
        button.addEventListener('click', function () {
            var postId = this.getAttribute('data-post-id');
            stompClient.send("/app/user.likePost", {}, JSON.stringify(postId));
            postId.value = '';
        });
    });
});





