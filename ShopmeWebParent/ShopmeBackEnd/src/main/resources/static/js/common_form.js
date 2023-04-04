$(document).ready(
    function () {
        setTimeout(function () {
            $("#successMessage").fadeOut("slow");
        }, 3000);

        $("#SuccessMessageCloseBtn").on("click", function () {
            $("#successMessage").fadeOut("slow");
        })
        $("#buttonCancel").on("click", function () {
            window.location = moduleURL;
        });

        $("#fileImage").change(function () {
            fileSize = this.files[0].size;

            if (fileSize > 100*1024) {
                this.setCustomValidity("You must choose an image less than 100KB!");
                this.reportValidity();
            } else {
                this.setCustomValidity("");
                showImageThumbnail(this);
            }

        })
    }
);

function showImageThumbnail(fileInput) {
    var file = fileInput.files[0];
    var reader = new FileReader();
    reader.onload = function (e) {
        $("#thumbnail").attr("src", e.target.result);
    }

    reader.readAsDataURL(file);
}
