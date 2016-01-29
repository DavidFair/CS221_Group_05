function validateEntry() {
    // Everything is just a long if statement chain. It smells but it works.

// Declare variables first
// Regexp for checking task names, used in CS25010
    var ASCIIregExp = /^[a-z\d\-_\s]+$/i;

// Regexp for YY-MM-DD dates
// Thank you http://stackoverflow.com/questions/13194322/php-regex-to-check-date-is-in-yyyy-mm-dd-format
    var dateRegExp = /^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/;

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
    return true;
}