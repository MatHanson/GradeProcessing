import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.*;


/**
 *
 */
public class GradeProcessing extends Application {
    // Initialise buttons and textfields
    private TextField StudentID = new TextField();
    private TextField SearchStudentID = new TextField();
    private TextField StudentName = new TextField();
    private TextField QuizMarks = new TextField();
    private TextField Ass1Marks = new TextField();
    private TextField Ass2Marks = new TextField();
    private TextField Ass3Marks = new TextField();
    private TextField ExamMarks = new TextField();
    private TextField Grade = new TextField();
    private TextField TableName = new TextField();
    private TextField NewTableName = new TextField();
    private TextField SearchTableName = new TextField();
    private Button btCreateTable = new Button("Create Table");
    private Button btInsertRecord = new Button("Insert Record");
    private Button btUpdateRecord = new Button("Update Record");
    private Button btSearchRecord = new Button("Search Record");

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/GradeProcessing?autoReconnect=true&useSSL=false";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "admin";

    // Create data structure to hold student objects
    ObservableList<Student> data =
            FXCollections.observableArrayList();

    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        // Create pane
        BorderPane pane = new BorderPane();

        // Place nodes in the pane
        pane.setTop(gridPane());
        pane.setCenter(tableView());

        // Create a scene and place it in the stage
        Scene scene = new Scene(pane);
        primaryStage.setTitle("Grade Processing-Programming in Java 2"); // Set title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
    }

    private GridPane gridPane() {
        // Create UI
        GridPane gridPane = new GridPane();
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        // Add labels and textboxes
        // New Table section
        gridPane.add(new Label("Create new Table"), 0, 0);
        gridPane.add(new Label("New table name: "), 0, 1);
        gridPane.add(NewTableName, 1, 1);
        gridPane.add(btCreateTable, 2, 1);

        // Insert/Update section
        gridPane.add(new Label(""), 0, 2);
        gridPane.add(new Label("Insert/Update Record"), 0, 3);
        gridPane.add(new Label("Table Name"), 0, 4);
        gridPane.add(TableName, 1, 4);
        gridPane.add(new Label("Student ID"), 0, 5);
        gridPane.add(StudentID, 1, 5);
        gridPane.add(new Label("Student Name"), 0, 6);
        gridPane.add(StudentName, 1, 6);
        gridPane.add(new Label("Quiz Marks (enter 0-100)"), 0, 7);
        gridPane.add(QuizMarks, 1, 7);
        gridPane.add(new Label("Assignment 1 Marks (enter 0-100)"), 2, 4);
        gridPane.add(Ass1Marks, 3, 4);
        gridPane.add(new Label("Assignment 2 Marks (enter 0-100)"), 2, 5);
        gridPane.add(Ass2Marks, 3, 5);
        gridPane.add(new Label("Assignment 3 Marks (enter 0-100)"), 2, 6);
        gridPane.add(Ass3Marks, 3, 6);
        gridPane.add(new Label("Exam Marks (enter 0-100)"), 2, 7);
        gridPane.add(ExamMarks, 3, 7);
        gridPane.add(btInsertRecord, 1, 8);
        gridPane.add(btUpdateRecord, 2, 8);

        // Search section
        gridPane.add(new Label(""), 0, 9);
        gridPane.add(new Label("Search for Student"), 0, 10);
        gridPane.add(new Label("Enter Table name:"), 0, 11);
        gridPane.add(SearchTableName, 1, 11);
        gridPane.add(new Label("Enter Student ID:"), 0, 12);
        gridPane.add(SearchStudentID, 1, 12);
        gridPane.add(btSearchRecord, 2, 12);


        // Set properties for UI
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(10, 0, 10, 0));
        TableName.setAlignment(Pos.BOTTOM_RIGHT);
        NewTableName.setAlignment(Pos.BOTTOM_RIGHT);
        StudentID.setAlignment(Pos.BOTTOM_RIGHT);
        StudentName.setAlignment(Pos.BOTTOM_RIGHT);
        Grade.setAlignment(Pos.BOTTOM_RIGHT);
        QuizMarks.setAlignment(Pos.BOTTOM_RIGHT);
        Ass1Marks.setAlignment(Pos.BOTTOM_RIGHT);
        Ass2Marks.setAlignment(Pos.BOTTOM_RIGHT);
        Ass3Marks.setAlignment(Pos.BOTTOM_RIGHT);
        ExamMarks.setAlignment(Pos.BOTTOM_RIGHT);
        SearchStudentID.setAlignment(Pos.BOTTOM_RIGHT);
        SearchTableName.setAlignment(Pos.BOTTOM_RIGHT);

        // Process events
        btCreateTable.setOnAction(e -> {
            try {
                createTable();
            } catch (SQLException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
        });
        btInsertRecord.setOnAction(e -> {
            try {
                insertRecord();
            } catch (SQLException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
        });
        btUpdateRecord.setOnAction(e -> {
            try {
                updateRecord();
            } catch (SQLException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
        });
        btSearchRecord.setOnAction(e -> {
            try {
                searchRecord();
            } catch (SQLException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
        });

        return gridPane;
    }

    public TableView tableView() {
        // Create UI
        TableView<Student> tableView = new TableView<>();
        // Set width of table
        tableView.setMaxWidth(750);

        // Add columns and populate with variables from Student objects
        TableColumn IDColumn = new TableColumn("ID");
        IDColumn.setMinWidth(80);
        IDColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("studentID"));

        TableColumn NameColumn = new TableColumn("Name");
        NameColumn.setMinWidth(80);
        NameColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("studentName"));

        TableColumn QuizMarkColumn = new TableColumn("Quiz Mark");
        QuizMarkColumn.setMinWidth(80);
        QuizMarkColumn.setCellValueFactory(new PropertyValueFactory<Student, Integer>("quizMarks"));

        TableColumn Ass1Column = new TableColumn("A1");
        Ass1Column.setMinWidth(30);
        Ass1Column.setCellValueFactory(new PropertyValueFactory<Student, Integer>("ass1Marks"));

        TableColumn Ass2Column = new TableColumn("A2");
        Ass2Column.setMinWidth(30);
        Ass2Column.setCellValueFactory(new PropertyValueFactory<Student, Integer>("ass2Marks"));

        TableColumn Ass3Column = new TableColumn("A3");
        Ass3Column.setMinWidth(30);
        Ass3Column.setCellValueFactory(new PropertyValueFactory<Student, Integer>("ass3Marks"));

        TableColumn ExamColumn = new TableColumn("Exam");
        ExamColumn.setMinWidth(30);
        ExamColumn.setCellValueFactory(new PropertyValueFactory<Student, Integer>("examMarks"));

        TableColumn ResultsColumn = new TableColumn("Results");
        ResultsColumn.setMinWidth(60);
        ResultsColumn.setCellValueFactory(new PropertyValueFactory<Student, Integer>("results"));

        TableColumn GradesColumn = new TableColumn("Grade");
        GradesColumn.setMinWidth(60);
        GradesColumn.setCellValueFactory(new PropertyValueFactory<Student, Integer>("grade"));

        // Assign the columns to tableView
        tableView.getColumns().addAll(IDColumn, NameColumn, QuizMarkColumn, Ass1Column, Ass2Column, Ass3Column,
                ExamColumn, ResultsColumn, GradesColumn);

        // Assign the data list to tableView
        tableView.setItems(data);

        return tableView;
    }


    public static class Student {
        // Declare variables
        private final SimpleStringProperty studentID;
        private final SimpleStringProperty studentName;
        private final SimpleIntegerProperty quizMarks;
        private final SimpleIntegerProperty ass1Marks;
        private final SimpleIntegerProperty ass2Marks;
        private final SimpleIntegerProperty ass3Marks;
        private final SimpleIntegerProperty examMarks;
        private final SimpleIntegerProperty results;
        private final SimpleStringProperty grade;

        // Student constructor
        public Student(String studentID, String name,
                        int quizMarks, int ass1Marks, int ass2Marks, int ass3Marks, int examMarks, int results,
                        String grade) {
            // Initialise variables using the arguments passed to the method
            this.studentID = new SimpleStringProperty(studentID);
            this.studentName = new SimpleStringProperty(name);
            this.quizMarks = new SimpleIntegerProperty(quizMarks);
            this.ass1Marks = new SimpleIntegerProperty(ass1Marks);
            this.ass2Marks = new SimpleIntegerProperty(ass2Marks);
            this.ass3Marks = new SimpleIntegerProperty(ass3Marks);
            this.examMarks = new SimpleIntegerProperty(examMarks);
            this.results = new SimpleIntegerProperty(results);
            this.grade = new SimpleStringProperty(grade);

        }

        // Public getters for object variables
        public String getStudentID() {
            return studentID.get();
        }
        public String getStudentName() {
            return studentName.get();
        }
        public int getQuizMarks() {
            return quizMarks.get();
        }
        public int getAss1Marks() {
            return ass1Marks.get();
        }
        public int getAss2Marks() {
            return ass2Marks.get();
        }
        public int getAss3Marks() {
            return ass3Marks.get();
        }
        public int getExamMarks() {
            return examMarks.get();
        }
        public double getResults() {
            return results.get();
        }
        public String getGrade() {
            return grade.get();
        }
    }

    private void createTable() throws SQLException, ClassNotFoundException {
        Connection conn = null;
        Statement stmt = null;
        String tableName = NewTableName.getText();
        String sql = "CREATE TABLE " + tableName +
                " (STUDENTID INTEGER not NULL, " +
                " STUDENTNAME VARCHAR(255), " +
                " QUIZ INTEGER, " +
                " ASS1 INTEGER, " +
                " ASS2 INTEGER, " +
                " ASS3 INTEGER, " +
                " EXAM INTEGER, " +
                " RESULTS INTEGER, " +
                " GRADE VARCHAR(5), " +
                " PRIMARY KEY ( STUDENTID ))";
        try {
            // Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            // Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Create statement
            stmt = conn.createStatement();

            // Execute statement
            stmt.executeUpdate(sql);

        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    conn.close();
            }catch(SQLException se){
            }// do nothing
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
    }

    private void updateRecord() throws SQLException, ClassNotFoundException {
        Connection conn = null;
        Statement stmt = null;
        String tableName = TableName.getText();
        String ID = StudentID.getText();
        String name = StudentName.getText();
        int quizMarks = Integer.parseInt(QuizMarks.getText());
        int ass1Marks = Integer.parseInt(Ass1Marks.getText());
        int ass2Marks = Integer.parseInt(Ass2Marks.getText());
        int ass3Marks = Integer.parseInt(Ass3Marks.getText());
        int examMarks = Integer.parseInt(ExamMarks.getText());
        int results = (int) ((quizMarks* 0.05) + (ass1Marks * 0.15) + (ass2Marks * 0.2) + (ass3Marks * 0.1)
                + (examMarks * 0.5));
        String grade = calculateGrade(results);

        String sql = "UPDATE " + tableName +
                " SET STUDENTNAME='" + name +
                "', QUIZ = " + quizMarks +
                ", ASS1 = " + ass1Marks +
                ", ASS2 = " + ass2Marks +
                ", ASS3 = " + ass3Marks +
                ", EXAM = " + examMarks +
                ", RESULTS = " + results +
                ", GRADE = '" + grade +
                "' WHERE STUDENTID = " + ID;

        try {
            // Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            // Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Create statement
            stmt = conn.createStatement();

            // Execute statement
            stmt.executeUpdate(sql);

        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    conn.close();
            }catch(SQLException se){
            }// do nothing
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
    }

    private void insertRecord() throws SQLException, ClassNotFoundException {
        Connection conn = null;
        Statement stmt = null;
        String tableName = TableName.getText();
        String ID = StudentID.getText();
        String name = StudentName.getText();
        int quizMarks = Integer.parseInt(QuizMarks.getText());
        int ass1Marks = Integer.parseInt(Ass1Marks.getText());
        int ass2Marks = Integer.parseInt(Ass2Marks.getText());
        int ass3Marks = Integer.parseInt(Ass3Marks.getText());
        int examMarks = Integer.parseInt(ExamMarks.getText());
        int results = (int) ((quizMarks* 0.05) + (ass1Marks * 0.15) + (ass2Marks * 0.2) + (ass3Marks * 0.1)
                        + (examMarks * 0.5));
        String grade = calculateGrade(results);

        String sql = "INSERT INTO " + tableName +
                " VALUES ('" +
                ID +"', '" +
                name +"', '" +
                quizMarks +"', '" +
                ass1Marks +"', '" +
                ass2Marks +"', '" +
                ass3Marks +"', '" +
                examMarks +"', '" +
                results +"', '" +
                grade +"')";
        try {
            // Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            // Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Create statement
            stmt = conn.createStatement();

            // Execute statement
            stmt.executeUpdate(sql);

        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    conn.close();
            }catch(SQLException se){
            }// do nothing
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
    }

    private void searchRecord() throws SQLException, ClassNotFoundException {
        Connection conn = null;
        Statement stmt = null;
        String tableName = SearchTableName.getText();
        String studentID = SearchStudentID.getText();
        int quizMarks = 0;
        int ass1Marks = 0;
        int ass2Marks = 0;
        int ass3Marks = 0;
        int examMarks = 0;
        int results = 0;
        String grade = null;
        String studentName = null;

        String sql = "SELECT * FROM " + tableName +
                " WHERE STUDENTID = '" +
                studentID + "'";

        try {
            // Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            // Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Create statement
            stmt = conn.createStatement();

            // Execute statement
            ResultSet resultSet = stmt.executeQuery(sql);

            while (resultSet.next()) {
                studentID = resultSet.getString(1);
                studentName = resultSet.getString(2);
                quizMarks = Integer.parseInt(resultSet.getString(3));
                ass1Marks = Integer.parseInt(resultSet.getString(4));
                ass2Marks = Integer.parseInt(resultSet.getString(5));
                ass3Marks = Integer.parseInt(resultSet.getString(6));
                examMarks = Integer.parseInt(resultSet.getString(7));
                results = Integer.parseInt(resultSet.getString(8));
                grade = resultSet.getString(9);

                //System.out.println(studentID + studentName + quizMarks + ass1Marks + ass2Marks + ass3Marks +
                // examMarks
                //        + results + grade);


            }
            // Create new student object by passing variables to constructor
            Student student = new Student(studentID, studentName, quizMarks, ass1Marks, ass2Marks, ass3Marks,
                    examMarks, results, grade);

            // Add the student object to the data list
            data.add(student);
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    conn.close();
            }catch(SQLException se){
            }// do nothing
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
    }

    // Method to calculate the grade of a student
    public static String calculateGrade(double results) {
        // Declare grade variable
        String grade;

        // If results argument is less than 50
        if(results<50){
            // Assign FL to grade
            grade="FL";
        }
        // If results argument is greater than or equal to 50 and less than 65
        else if(results>=50 && results<65){
            // Assign PS to grade
            grade="PS";
        }

        // If results argument is greater than or equal to 65 and less than 75
        else if(results>=65 && results<75){
            // // Assign CR to grade
            grade="CR";
        }

        // If results argument is greater than or equal to 75 and less than 85
        else if(results>=75 && results<85){
            // // Assign DI to grade
            grade="DI";
        }
        else
            // Assign HD to grade
            grade="HD";

        // Return grade variable
        return grade;
    }


    public static void main(String[] args)  {
        launch(args);






    }
}
