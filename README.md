# Aberystwyth University CS221 2015/2016 - Group 05 #

For this module we had to create a system capable of creating tasks, assigning them to users and view them locally even offline.The three components which made up the program were:

- TaskerCLI - A Java application which ran locally and allowed a user to view their tasks
- TaskerMAN - A website which connected to a SQL server and provided a user with the ability to manage tasks such as creating/editing/deleting them.
- TaskerSRV - BASH scripts which create a SQL server with the correct tables, and FK constraints. 

All programming was completed in a single week by the group, to achieve the required functionality in time unit tests, documentation and additional testing could not be completed. Because of this the program may be prone to failure due to bugs. 

After the source code was submitted the group reflected on some aspects which everyone learnt from this project this includes:

- Create unit tests and a harness which exercies code paths and run to test for regressions
- Any new regressions need to be added to the unit tests, in the project all testing was completed manually tying up a group member for the entire project
- Nobody within the Java team had experience with Swing, this caused many bugs and eventually the team had to change the design as our knowledge expanded
- Use MVC to power the GUIs 
- Document the program as new features are added instead of down the line
- Don't store PDF files within the repo only their source Word documents. 

*Note this is a completed project: Any pull requests or issues will probably go unnoticed. Additionally no changed can be made to this project and all changes will have to be completed in a fork.*