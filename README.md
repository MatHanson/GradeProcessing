# Grade Processing

This program consists of a GUI created using JavaFX to interact with a MySQL database, allowing creation of tables, inserting and updating records, and searching for records.

### Main Screen
The main screen consists of three separate functional areas – Create new Table, Insert/Update Record, and Search for Student. At the bottom of the screen, there is a table to show the student details that have been searched.

#### Create Table
The Create Table button will execute an SQL statement to create a new table, using the text from the NewTable TextField as the table name.

#### Insert/Update Record
The Insert Record button will execute an SQL statement using the values from the TextFields to insert a new record into the value of the TableName TextField.
The Update Record button will execute an SQL statement using the values from the TextFields to update the record that matches the StudentID TextField in the relevant table.

#### Search Record
The Search Record button will execute an SQL statement to search for a relation in the table provided where the Student ID matches. The relation will then be displayed in the table below.
