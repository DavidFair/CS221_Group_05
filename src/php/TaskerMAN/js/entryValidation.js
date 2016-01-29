// Regexp for general alphanumeric entries and spaces
var ASCIIregExp = /^[a-z\d\-_\s]+$/i;

// Regexp for YY-MM-DD dates
// Thank you http://stackoverflow.com/questions/13194322/php-regex-to-check-date-is-in-yyyy-mm-dd-format
var dateRegExp = /^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/;

// Email RegExp from CS25010
var emailRegExp = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;

function validateEntry()
{
// Everything is just a long if statement chain. It smells but it works.

// Declare variables first
// Regexp for checking task names, used in CS25010

    var taskName = document.forms["taskAdd"]["add_taskName"].value;
    var startDate = document.forms["taskAdd"]["add_startDate"].value;
    var endDate = document.forms["taskAdd"]["add_endDate"].value;
    var numberOfElements = document.forms["taskAdd"]["add_numberOfElements"].value;
    var extraStartDate = startDate;
    var extraEndDate = endDate;

    startDate = new Date();
    endDate = new Date();

    //alert("I'm working!");

    if (taskName == null || taskName == " ")
    {
        alert("The task name is empty.");
        return false;
    } else if (taskName.length > 40)
    {
        alert("The task name is too long.");
        return false;
    } else if (taskName.length <= 2)
    {
        alert("The task name is too short.");
        return false;
    } else if (!ASCIIregExp.test(taskName))
    {
        alert("The task name contains invalid characters.");
        return false;
    } else if (!dateRegExp.test(extraStartDate))
    {
        alert("Start date is not in the correct format. Enter YYYY-MM-DD.");
        return false;
    } else if (!dateRegExp.test(extraEndDate))
    {
        alert("End date is not in the correct format. Enter YYYY-MM-DD.");
        return false;
    } else if (startDate.getDate < endDate.getDate)
    {
        alert("Start date exists after the end date. Please readjust your dates.");
        return false;
    } else if (numberOfElements == null || numberOfElements == " ") {
        alert("This is not an acceptable value for number of elements.");
        return false;
    } else if (isNaN(numberOfElements))
    {
        alert("This is not an acceptable value for number of elements.");
        return false;
    } else if (numberOfElements > 5)
    {
        alert("This is too many elements. You can always add more later.");
        return false;
    } else if (numberOfElements < 1)
    {
        alert("The number of elements cannot be zero or less.");
        return false;
    } else
    {
        return true;
    }
}

function validateElements()
{
    var form = document.getElementById("addElements");
    var inputChildren = (form.getElementsByClassName('elementInput').length / 2);
    for (var i = 1; i < inputChildren; i++)
    {
        var formValue = "taskDesc_" + i;
        var taskDesc = document.forms["addElements"][formValue].value;
        if (taskDesc == null || taskDesc == " ")
        {
            alert("Description is empty.");
            return false;
        } else if (taskDesc.length > 45)
        {
            alert("Task description is too long.");
            return false;
        } else if (taskDesc.length < 3)
        {
            alert("Task description is too short.");
            return false;
        } else if (!(ASCIIregExp.test(taskDesc)))
        {
            alert("Task description is too short.");
            return false;
        } else if (!(ASCIIregExp.test(taskDesc)))
        {
            alert("Invalid characters in task description.");
            return false;
        } else if (taskComment == null || taskComment == " ")
        {
            alert("Comment is empty.");
            return false;
        } else if (taskComment.length > 45)
        {
            alert("Task comment is too long.");
            return false;
        } else if (taskComment.length < 3)
        {
            alert("Task comment is too short.");
            return false;
        } else if (!(ASCIIregExp.test(taskComment)))
        {
            alert("Invalid characters in task comment.");
            return false;
        } else
        {
            return true;
        }
    }

    function validateUserEntry()
    {
        var email = document.forms["userAdd"]["add_email"].value;
        var firstName = document.forms["userAdd"]["add_firstName"].value;
        var lastName = document.forms["userAdd"]["add_lastName"].value;
        var isManager = document.forms["userAdd"]["add_isManager"].value;

        if (email == null || email == " ")
        {
            alert("The email field is empty.");
            return false;
        } else if (email.length > 45)
        {
            alert("The email address is too long.");
            return false;
        } else if (!emailRegExp.test(email))
        {
            alert("This is not a valid email address.");
            return false;
        } else if (firstName == null || email == " ")
        {
            alert("The first name field is empty.");
            return false;
        } else if (firstName.length > 15) {
            alert("The first name entered is too long.");
            return false;
        } else if (firstName.length < 2) {
            alert("The first name entered is too short.");
            return false;
        } else if (!ASCIIregExp.test(firstName))
        {
            alert("Invalid data in the first name field");
            return false;
        } else if (lastName == null || lastName == " ")
        {
            alert("The last name field is empty.");
            return false;
        } else if (lastName.length > 15) {
            alert("The last name entered is too long.");
            return false;
        } else if (lastName.length < 2) {
            alert("The last name entered is too short.");
            return false;
        } else if (!ASCIIregExp.test(lastName))
        {
            alert("Invalid data in the last name field");
            return false;
        } else if (isManager > 1)
        {
            alert("Invalid value specified for isManager");
            return false;
        } else
        {
            return true;
        }
    }

    function validateUserEdit()
    {
        var email = document.forms["userEdit"]["edit_email"].value;
        var firstName = document.forms["userEdit"]["edit_firstName"].value;
        var lastName = document.forms["userEdit"]["edit_lastName"].value;
        var isManager = document.forms["userEdit"]["edit_isManager"].value;

        if (email == null || email == " ")
        {
            alert("The email field is empty.");
            return false;
        } else if (email.length > 45)
        {
            alert("The email address is too long.");
            return false;
        } else if (!emailRegExp.test(email))
        {
            alert("This is not a valid email address.");
            return false;
        } else if (firstName == null || email == " ")
        {
            alert("The first name field is empty.");
            return false;
        } else if (firstName.length > 15) {
            alert("The first name entered is too long.");
            return false;
        } else if (firstName.length < 2) {
            alert("The first name entered is too short.");
            return false;
        } else if (!ASCIIregExp.test(firstName))
        {
            alert("Invalid data in the first name field");
            return false;
        } else if (lastName == null || lastName == " ")
        {
            alert("The last name field is empty.");
            return false;
        } else if (lastName.length > 15) {
            alert("The last name entered is too long.");
            return false;
        } else if (lastName.length < 2) {
            alert("The last name entered is too short.");
            return false;
        } else if (!ASCIIregExp.test(lastName))
        {
            alert("Invalid data in the last name field");
            return false;
        } else if (isManager > 1)
        {
            alert("Invalid value specified for isManager");
            return false;
        } else
        {
            return true;
        }
    }

    function validateTaskEdit()
    {
        return true;
    }

    function validateExtraElement() {
        var taskDesc = document.forms["extraElement"]["taskDesc"].value;
        var taskComment = document.forms["extraElement"]["taskComment"].value;

        if (taskDesc == null || taskDesc == " ") {
            alert("Description is empty.");
            return false;
        } else if (taskDesc.length > 45) {
            alert("Task description is too long.");
            return false;
        } else if (taskDesc.length < 3) {
            alert("Task description is too short.");
            return false;
        } else if (!(ASCIIregExp.test(taskDesc))) {
            alert("Invalid characters in task description.");
            return false;
        } else if (taskComment == null || taskComment == " ") {
            alert("Comment is empty.");
            return false;
        } else if (taskComment.length > 45) {
            alert("Task comment is too long.");
            return false;
        } else if (taskComment.length < 3) {
            alert("Task comment is too short.");
            return false;
        } else if (!(ASCIIregExp.test(taskComment))) {
            alert("Invalid characters in task comment.");
            return false;
        } else {
            return true;
        }
    }
}