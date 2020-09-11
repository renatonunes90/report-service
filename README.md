# Report Service

## Description:

Report Service is a sales data analysis system that imports batches of files and produces a report based on
information present in it.

There are 3 types of data within the files and they can be distinguished by their identifier that will be present
in the first column of each row, where the column separator is the character "ç".

- Salesman data: Salesman data has the identifier 001 and follows the following format:

001çCPFçNameçSalary

- Client data: Client data has the identifier 002 and follows the following format:

002çCNPJçNameçBusiness Area

- Sales data: Sales data have the identifier 003 and follow the following format:

003çSale IDç[Item ID-Item Quantity-Item Price]çSalesman name

The system continuously reads all files within the default HOMEPATH/data/ in directory and places the output 
file in HOMEPATH/data/out.

The following data is printed on the output file:
- Number of clients in the input file
- Number of salesmen in the input file
- Most expensive sale ID
- The worst salesman

If any error occurs while the process, the system will log the error in the file HOMEPATH/data/out/log.txt and removes the wrong file generating report only if has a minimum of information to do it. 

# How to compile

To compile the system you must have maven installed and just execute the follow command line in project root folder:

```
mvn package
```

# How to run

Before start the application you must set the environment variable "HOMEPATH" to indicates where the system must get the input files and generate output files.
To do this, already exists a file "run.bat" to set the environment variable and start the application. You just need to change the value of the HOMEPATH inside the file to the right path.
If you don't want to use the provided bat file, you can set manually the environment variable and execute the
jar file generated in the previous section. The command line to execute jar file would be like this:

```
java -jar target/service-report.jar
```



