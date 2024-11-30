document.addEventListener('DOMContentLoaded', function () {
    var socket = new SockJS('/ws');
    var stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/add_comment', function (payload) {
            var comment = JSON.parse(payload.body);
            addCommentToPost(comment);
        });
    });

    function addCommentToPost(comment) {
        var postId = comment.postId;
        var postComments = document.querySelector(`#post-${postId}`);
        if (postComments) {
            var commentHtml = `
                <div class="post-comments-single">
                    <div class="post-comment-avatar">
                        <img src="${comment.commentedByAvatar}" alt="">
                    </div>
                    <div class="post-comment-text">
                        <div class="post-comment-text-inner">
                            <h6>${comment.commentedByName}</h6>
                            <p>${comment.content}</p>
                        </div>
                        <div class="uk-text-small">
                            <span>${comment.timeDifference}</span>
                        </div>
                    </div>
                    <a href="#" class="post-comment-opt"></a>
                </div>
            `;
            postComments.insertAdjacentHTML('beforeend', commentHtml);
        }
    }



    document.querySelectorAll('.add-comment').forEach(button => {
        button.addEventListener('click', function () {
            var postId = this.getAttribute('data-post-id');
            var contentInput = document.querySelector(`#content-${postId}`);
            var content = contentInput.value;

            if (content.trim() !== '') {
                var comment = {
                    postId: postId,
                    content: content
                };

                stompClient.send("/app/user.addComment", {}, JSON.stringify(comment));
                contentInput.value = '';
            }
        });
    });
});

// document.addEventListener('DOMContentLoaded', function () {
//     var socket = new SockJS('/ws');
//     var stompClient = Stomp.over(socket);
//
//     stompClient.connect({}, function (frame) {
//         console.log('Connected: ' + frame);
//         stompClient.subscribe('/topic/add_comment', function (payload) {
//             var comment = JSON.parse(payload.body);
//             addCommentToPost(comment);
//             showComment(comment);
//         });
//     });
//
//     function addCommentToPost(comment) {
//         var postId = comment.postId;
//         var postComments = document.querySelector(`#post-${postId} .post-comments`);
//         if (postComments) {
//             var commentHtml = `
//                 <div class="post-comments-single">
//                     <div class="post-comment-avatar">
//                         <img src="${comment.commentedByAvatar}" alt="">
//                     </div>
//                     <div class="post-comment-text">
//                         <div class="post-comment-text-inner">
//                             <h6>${comment.commentedByName}</h6>
//                             <p>${comment.content}</p>
//                         </div>
//                         <div class="uk-text-small">
//                             <span>${comment.timeDifference}</span>
//                         </div>
//                     </div>
//                 </div>
//             `;
//             postComments.insertAdjacentHTML('beforeend', commentHtml);
//         }
//     }
//
//     function showComment(comment) {
//         var commentList = document.getElementById('comment-list-' + comment.postId);
//         var commentElement = document.createElement('div');
//         commentElement.className = 'post-comments-single';
//         commentElement.innerHTML = `
//             <div class="post-comment-avatar">
//                 <img src="${comment.commentedByAvatar}" alt="">
//             </div>
//             <div class="post-comment-text">
//                 <div class="post-comment-text-inner">
//                     <h6>${comment.commentedByName}</h6>
//                     <p>${comment.content}</p>
//                 </div>
//                 <div class="uk-text-small">
//                     <span>${comment.timeAgo}</span>
//                 </div>
//             </div>
//         `;
//         commentList.appendChild(commentElement);
//     }
//
//     document.querySelectorAll('.add-comment').forEach(button => {
//         button.addEventListener('click', function () {
//             var postId = this.getAttribute('data-post-id');
//             var contentInput = document.querySelector(`#content-${postId}`);
//             var content = contentInput.value;
//
//             if (content.trim() !== '') {
//                 var comment = {
//                     postId: postId,
//                     content: content
//                 };
//
//                 stompClient.send("/app/user.addComment", {}, JSON.stringify(comment));
//                 contentInput.value = '';
//             }
//         });
//     });
// });