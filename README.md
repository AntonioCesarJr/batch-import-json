### Import Json to database

* Create MySql database (MySql 8.0.13) and configure application.properties file;**

*  Start then program with following commnad:
```
java -jar ./target/batch-import-json-0.0.1-SNAPSHOT.jar {{"path to json files folder"}}
```

* The system will read all json files on folder and persist all data on MySql, then you can use the following query to extract data:

    ``` 
    select * from empresa e
    inner join poder p on p.empresa_id = e.id
    inner join representante r on r.empresa_id = e.id
    inner join restricao re on re.empresa_id = e.id;
    ```
