$('document').ready(function () {
    $("#btn-signup").hide();
    $("#divPasswordConfirm").hide();
    $(function () {

        $("#InputPasswordConfirm").keyup(function () {

            const confirmPassword = $("#InputPasswordConfirm").val();
            const password = $("#InputPassword").val();

            if (password !== confirmPassword) {

                $("#divCheckPasswordMatch").html("*Passwords do not match :(").addClass('text-danger').removeClass('text-success');
                $("#btn-signup").hide();

            } else {
                $("#divCheckPasswordMatch").html("").removeClass('text-danger');
                $("#btn-signup").show();
            }
        });


        $("#InputPassword").keyup(function () {

            const confirmPassword = $("#InputPasswordConfirm").val();
            const password = $("#InputPassword").val();

            if (confirmPassword !== '') {
                if (password !== confirmPassword) {

                    $("#divCheckPasswordMatch").html("*Passwords do not match :( ").addClass('text-danger').removeClass('text-success');
                    $("#btn-signup").hide();

                } else {
                    $("#divCheckPasswordMatch").html("").removeClass('text-danger');
                    $("#btn-signup").show();
                }
            }

            let strength = 1;
            const arr = [/.{5,}/, /[a-z]+/, /[0-9]+/, /[A-Z]+/];

            jQuery.map(arr, function(regexp) {
                if(password.match(regexp))
                    strength++;
            });

            console.log("Strength::::"+strength);

            if (strength < 5) {
                $("#divPasswordConfirm").hide();
                $("#divCheckPasswordLen").html("*Password too weak! Make sure you are entering at least 6 characters long password with at least an uppercase letter, a number or a special character included!").addClass('text-danger').removeClass('text-success');

            } else {
                $("#divCheckPasswordLen").html("").removeClass('text-danger').addClass('text-success');
                $("#divPasswordConfirm").show();
            }
        });
    });
});