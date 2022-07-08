# ivs-automation-helper

##To setup the project just run  
    mvn clean install

In order to use this framework correctly you should consider the following things:

- Pojo classes can be generated from the Data Base tables using the IDEA's generator of POJO
- Pojo classes should not contain any other extra fields except those fields which belong to the table. There's no need in getters and setters.
- Framework uses input parameters for the tests Java strings, which are converted then into POJO
- Sample files in framework (like 'any'.json) are used only for transformation into JAVA objects
- All transformations (data preparation steps, etc.) should be handled using JAVA objects
