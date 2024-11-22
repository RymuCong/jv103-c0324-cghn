// Xu ly su kien khi an vao thanh tim kiem
document.getElementById('searchInput').addEventListener('keydown', function(event) {
    if (event["keyCode"] === 13) {
        event.preventDefault();
        document.getElementById('searchForm').submit();
    }
});

document.addEventListener('DOMContentLoaded', function() {
    // Get the modal elements
    var editBackgroundModal = UIkit.modal('#editBackgroundModal');
    var editAvatarModal = UIkit.modal('#editAvatarModal');
    var editUserInfoModal = UIkit.modal('#editUserInfoModal');

    // Get the buttons that open the modals
    var editBackgroundBtn = document.querySelector('.profile-cover a');
    var editAvatarBtn = document.querySelector('.profile-image a');
    var editUserInfoBtn = document.querySelector('.nav-profile .button.primary');

    // Add event listeners to the buttons
    editBackgroundBtn.addEventListener('click', function() {
        editBackgroundModal.show();
    });

    editAvatarBtn.addEventListener('click', function() {
        editAvatarModal.show();
    });

    editUserInfoBtn.addEventListener('click', function() {
        editUserInfoModal.show();
    });
});

function previewImage(event, previewId = 'imagePreview') {
    const file = event.target.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function(e) {
            const preview = document.getElementById(previewId);
            preview.src = e.target.result;
            preview.style.display = 'block';
        };
        reader.readAsDataURL(file);
    }
}