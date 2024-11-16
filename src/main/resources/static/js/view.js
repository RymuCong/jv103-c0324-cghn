// Xu ly su kien khi an vao thanh tim kiem
document.getElementById('searchInput').addEventListener('keydown', function(event) {
    if (event["keyCode"] === 13) {
        event.preventDefault();
        document.getElementById('searchForm').submit();
    }
});

//Xu ly su kien khi click vao nut upload anh
document.getElementById('uploadImage').addEventListener('click', function(event) {
    event.preventDefault();
    document.getElementById('uploadInput').click();
});

//Xu ly hien thi edit form
document.getElementById('editButton').addEventListener('click', function(event) {
    event.preventDefault();
    UIkit.modal('#editModal').show();
});