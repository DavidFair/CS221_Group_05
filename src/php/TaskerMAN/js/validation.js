/**
 * Created by oliver on 09/12/15.
 */

function loginValidate()
{
    var email = document.forms["login"]["email"].value;
    var emailRegExp = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;

    // Check email length
    if (email.length ==0 || email == null || email == " ")
    {
        alert("PLease enter a valid email address.");
        return false;
    }
    // Length too long check
    if (email.length > 40)
    {
        alert ("This email address is too long. Please specify a different email address.");
        return false;
    }
    // RegExp check
    if (!emailregExp.test(email))
    {
        alert("Please enter a valid email address.");
        return false;
    }
    // Passed tests
    return true;
}