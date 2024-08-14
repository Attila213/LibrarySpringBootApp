// login.js

$(document).ready(function() {
    $("#loginForm input[type='button']").click(function(e) {
        e.preventDefault(); // Prevent the default form submission

        var email = $("#email").val();
        var password = $("#password").val();

        // AJAX request to the server
        $.ajax({
            url: '/loginProcess',  // The endpoint where the server processes the login
            type: 'POST',
            data: {
                email: email,
                password: password
            },
            success: function(response) {
                var res = JSON.parse(response);
                if(res.status === "success") {
                    window.location.href = '/';
                    $("#message").text(res.message).css("color", "green");
                } else {
                    window.location.href = '/';
                    $("#message").text(res.message).css("color", "red");
                }
            },
            error: function() {
                window.location.href = '/';
                $("#message").text("An error occurred. Please try again.").css("color", "red");
            }
        });
    });

    $("#registerForm input[type='button']").click(function(e) {
        e.preventDefault(); // Prevent the default form submission

        var email = $("#email").val();
        var password = $("#password").val();
        var phone = $("#phone").val();
        var lastname = $("#lastname").val();
        var firstname = $("#firstname").val();
        var address = $("#address").val();

        // AJAX request to the server
        $.ajax({
            url: '/registerProcess',
            type: 'POST',
            data: {
                firstname:firstname,
                lastname:lastname,
                phone:phone,
                address:address,
                email: email,
                password: password
            },
            success: function(response) {
                var res = JSON.parse(response);
                if(res.status === "success") {
                    window.location.href = '/';

                    $("#message").text(res.message).css("color", "green");
                } else {
                    $("#message").text(res.message).css("color", "red");
                }
            },
            error: function() {
                console.log(this.data)
                $("#message").text("An error occurred. Please try again.").css("color", "red");
            }
        });
    });

});
