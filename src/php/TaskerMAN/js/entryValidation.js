// @author Oliver Earl

// RegExp for general alphanumeric entries and spaces
var ASCIIregExp = /^[a-z\d\-_\s]+$/i;

// RegExp for YY-MM-DD dates
// Thank you http://stackoverflow.com/questions/13194322/php-regex-to-check-date-is-in-yyyy-mm-dd-format
var dateRegExp = /^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/;

// Email RegExp from CS25010
var emailRegExp = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;

// The code smells but it works.

function validateEntry()
{
    // Declare variables first

    var taskName = document.forms["taskAdd"]["add_taskName"].value;
    var startDate = document.forms["taskAdd"]["add_startDate"].value;
    var endDate = document.forms["taskAdd"]["add_endDate"].value;
    var numberOfElements = document.forms["taskAdd"]["add_numberOfElements"].value;
    var extraStartDate = startDate;
    var extraEndDate = endDate;

    startDate = new Date();
    endDate = new Date();

    if (taskName == null || taskName == " " )
    {
        alert("The task name is empty.");
        return false;
    }

    if (taskName.length > 40)
    {
        alert("The task name is too long.");
        return false;
    }

    if (taskName.length < 2)
    {
        alert("The task name is too small.");
        return false;
    }

    if (!ASCIIregExp.test(taskName))
    {
        alert("The task name contains invalid characters.");
        return false;
    }

    if (!dateRegExp.test(extraStartDate))
    {
        alert("The start date is in an invalid format. Please enter in YYYY-MM-DD format.");
        return false;
    }

    if (!dateRegExp.test(extraEndDate))
    {
        alert("The end date is in an invalid format. Please enter in YYYY-MM-DD format.");
        return false;
    }

    if (startDate.getDate > endDate.getDate)
    {
        alert("The start date is in a point in the future further than the end date.");
        return false;
    }

    if (numberOfElements == null || numberOfElements == " ")
    {
        alert("The number of elements is zero.");
        return false;
    }

    if (numberOfElements > 5)
    {
        alert("This is too many elements. You can manually add more later.");
        return false;
    }

    if (numberOfElements < 1)
    {
        alert("The number of elements cannot be zero or less.");
        return false;
    }

    return true;
}

function validateElements()
{
    // I'm really pleased with the creativity of this function.
    var inputChildren = (document.getElementsByClassName('elementInput').length / 2);

    for (var i = 1; i < inputChildren; i++);
    {
        var formValue = "taskDesc_" + i;
        var taskDesc = document.forms["addElements"][formValue].value;

        var formValue2 = "taskComment_" + i;
        var taskComment = document.forms["addElements"][formValue2].value;

        if (taskDesc == null || taskDesc == " ")
        {
            alert("Description is empty.");
            return false;
        }

        if (taskDesc.length > 45)
        {
            alert("Task description is too long.");
            return false;
        }

        if (taskDesc.length < 2)
        {
            alert("Task description is too short.");
            return false;
        }

        if (!(ASCIIregExp.test(taskDesc)))
        {
            alert("Invalid characters in task description.");
            return false;
        }

        if (taskComment == null || taskComment == " ")
        {
            alert("Comment is empty.");
            return false;
        }

        if (taskComment.length > 45)
        {
            alert("Task comment is too long.");
            return false;
        }

        if (taskComment.length < 2)
        {
            alert("Task comment is too short.");
            return false;
        }

        if (!(ASCIIregExp.test(taskComment)))
        {
            alert("Invalid characters in task comment.");
            return false;
        }
    }
    return true;
}

function validateUserEntry() {
    var email = document.forms["userAdd"]["add_email"].value;
    var firstName = document.forms["userAdd"]["add_firstName"].value;
    var lastName = document.forms["userAdd"]["add_lastName"].value;

    if (email == null || email == " ") {
        alert("The email field is empty.");
        return false;
    }

    if (email.length > 45) {
        alert("The email address is too long.");
        return false;
    }

    if (!(emailRegExp.test(email))) {
        alert("The email address contains invalid characters.");
        return false;
    }

    if (firstName == null || firstName == " ") {
        alert("The first name field is empty.");
        return false;
    }

    if (firstName.length > 15) {
        alert("The first name field is too long.");
        return false;
    }

    if (firstName.length < 2) {
        alert("The first name field is too small.");
        return false;
    }

    if (!(ASCIIregExp.test(firstName))) {
        alert("The first name contains invalid characters.");
        return false;
    }

    if (lastName == null || lastName == " ") {
        alert("The last name field is empty.");
        return false;
    }

    if (lastName.length > 15) {
        alert("The last name field is too long.");
        return false;
    }

    if (lastName.length < 2) {
        alert("The last name field is too small.");
        return false;
    }

    if (!(ASCIIregExp.test(lastName))) {
        alert("The last name contains invalid characters.");
        return false;
    }

    return true;
}
function validateUserEdit() {
    var email = document.forms["userEdit"]["edit_email"].value;
    var firstName = document.forms["userEdit"]["edit_firstName"].value;
    var lastName = document.forms["userEdit"]["edit_lastName"].value;

    if (email == null || email == " ") {
        alert("The email field is empty.");
        return false;
    }

    if (email.length > 45) {
        alert("The email address is too long.");
        return false;
    }

    if (!(emailRegExp.test(email))) {
        alert("The email address contains invalid characters.");
        return false;
    }

    if (firstName == null || firstName == " ") {
        alert("The first name field is empty.");
        return false;
    }

    if (firstName.length > 15) {
        alert("The first name field is too long.");
        return false;
    }

    if (firstName.length < 2) {
        alert("The first name field is too small.");
        return false;
    }

    if (!(ASCIIregExp.test(firstName))) {
        alert("The first name contains invalid characters.");
        return false;
    }

    if (lastName == null || lastName == " ") {
        alert("The last name field is empty.");
        return false;
    }

    if (lastName.length > 15) {
        alert("The last name field is too long.");
        return false;
    }

    if (lastName.length < 2) {
        alert("The last name field is too small.");
        return false;
    }

    if (!(ASCIIregExp.test(lastName))) {
        alert("The last name contains invalid characters.");
        return false;
    }
    return true;
}

function validateTaskEdit() {
    var taskName = document.forms["taskEdit"]["edit_taskEdit"].value;
    var startDate = document.forms["taskEdit"]["edit_startDate"].value;
    var endDate = document.forms["taskEdit"]["edit_endDate"].value;
    var extraStartDate = startDate;
    var extraEndDate = endDate;

    startDate = new Date();
    endDate = new Date();

    if (taskName == null || taskName == " ") {
        alert("The task name is empty.");
        return false;
    }

    if (taskName.length > 40) {
        alert("The task name is too long.");
        return false;
    }

    if (taskName.length < 2) {
        alert("The task name is too small.");
        return false;
    }

    if (!(ASCIIregExp.test(taskName))) {
        alert("There are invalid characters in the task name.");
        return false;
    }

    if (!dateRegExp.test(extraStartDate)) {
        alert("The start date is in an invalid format. Please enter in YYYY-MM-DD format.");
        return false;
    }

    if (!dateRegExp.test(extraEndDate)) {
        alert("The end date is in an invalid format. Please enter in YYYY-MM-DD format.");
        return false;
    }

    if (startDate.getDate > endDate.getDate) {
        alert("The start date is in a point in the future further than the end date.");
        return false;
    }
    return true;
}

function validateExtraElement()
{
    var taskDesc = document.forms["extraElement"]["taskDesc"].value;
    var taskComment = document.forms["extraElement"]["taskComment"].value;

    if (taskDesc == null || taskDesc == " ")
    {
        alert("Description is empty.");
        return false;
    }

    if (taskDesc.length > 45)
    {
        alert("Task description is too long.");
        return false;
    }

    if (taskDesc.length < 2)
    {
        alert("Task description is too small.");
        return false;
    }

    if (!(ASCIIregExp.test(taskDesc)))
    {
        alert("Invalid characters in task description.");
        return false;
    }

    if (taskComment == null || taskComment == " ")
    {
        alert("Comment is empty.");
        return false;
    }

    if (taskComment.length > 45)
    {
        alert("Task comment is too long.");
        return false;
    }

    if (taskComment.length < 2)
    {
        alert("Task comment is too small.");
        return false;
    }

    if (!(ASCIIregExp.test(taskComment)))
    {
        alert("Invalid comment in task comment.");
        return false;
    }
    return true;
}