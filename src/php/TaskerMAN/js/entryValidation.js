function validateEntry() {
    // ASCII for checking usernames
    // Used this in CS25010
    var ASCIIregExp = /^[a-z\d\-_\s]+$/i;

    // Grab the value from the task name
    var taskName = documents.forms["taskAdd"][add_taskName].value;

    // Check the task name is not empty or null
    if (taskName == null || taskName == " ") {
        alert("The task name is empty.");
    }

    // Check length of task name
    if (taskName.length > 20) {
        alert("The task name is too long.");
        return false;
    }

    if (taskName.length < 2) {
        alert("The task name is too short.");
        return false;
    }

    // Test for characters using regular expression
    if (!ASCIIregExp.test(taskName)) {
        alert("The task name contains invalid characters.");
        return false;
    }

    // No need for tests for taskAllocated
    // Date tests, these are fun
    // Instantiate date objects

    // Regexp for YYYY-MM-DD dates
    // Thank you http://stackoverflow.com/questions/13194322/php-regex-to-check-date-is-in-yyyy-mm-dd-format
    var dateRegExp = /^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/;

    var startDate = document.forms["taskAdd"]["add_startDate"];
    startDate = new Date();
    var endDate = document.forms["taskAdd"]["add_endDate"];
    endDate = new Date();

    if (!dateRegExp(startDate)) {
        alert("Start date is not in the correct format. Enter YYYY-MM-DD.");
        return false;
    }

    if (!dateRegExp(endDate)) {
        alert("End date is not in the correct format. Enter YYYY-MM-DD>");
        return false;
    }

    // Test dates
    if (startDate.getDate > endDate.getDate) {
        alert("Start Date exists after the end date. Please readjust your dates.");
        return false;
    }

    // Test number of elements
    var numberOfElements = documents.form["taskAdd"]["add_numberOfElements"]

    if (numberOfElements == null | numberOfElements == " " || numberOfElements == NaN) {
        alert("This is not an acceptable value for number of elements.");
        return false;
    }
    if (numberOfElements > 5) {
        alert("This is too many elements. You can always add more later.");
        return false;
    }

    if (numberOfElements < 1)
    {
        alert("The number of elements cannot be 0 or less.");
    }

    return true;
}

function validateElements()
{
    return true;
}