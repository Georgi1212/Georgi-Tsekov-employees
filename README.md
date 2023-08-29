# Georgi-Tsekov-employees

Pair of employees who have worked together

Application that identifies the pair of employees who have worked
together on common projects for the longest period of time.
Input data:
A CSV file with data in the following format:
EmpID, ProjectID, DateFrom, DateTo
Sample data:

143, 12, 2013-11-01, 2014-01-05
218, 10, 2012-05-16, NULL
143, 10, 2009-01-01, 2011-04-27
...

Sample output:
143, 218, 8
Specific requirements
1) DateTo can be NULL, equivalent to today
2) The input data must be loaded to the program from a CSV file
--------------------------------------------------------------------------------
The example csv file location: "src/main/java/com/example/demo/file/newtext.csv"
port: 8081

paths: http://localhost:8081/         -> uploading a csv file;
       http://localhost:8081/records  -> view of the data, which is in the csv file, and the longest pair of employees
