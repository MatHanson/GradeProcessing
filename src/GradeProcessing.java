import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;



/**
 *
 */
public class GradeProcessing extends Application {
    // Initialise buttons and textfields
    private TextField StudentID = new TextField();
    private TextField StudentName = new TextField();
    private TextField QuizMarks = new TextField();
    private TextField Weight = new TextField();
    private TextField Ass1Marks = new TextField();
    private TextField Ass2Marks = new TextField();
    private TextField Ass3Marks = new TextField();
    private TextField ExamMarks = new TextField();
    private TextField Marks = new TextField();
    private TextField Grade = new TextField();
    private TextField WeightedMarks = new TextField();
    private TextField AverageMarks = new TextField();
    private TextField TableName = new TextField();
    private Button btCreateTable = new Button("Create Table");
    private Button btStudentMarks = new Button("Student Marks");
    private Button btAverageMarks = new Button("Average Marks");
    private Button btInsertRecord = new Button("Insert Record");

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/GradeProcessing?autoReconnect=true&useSSL=false";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "admin";

    // Create data structure to hold student objects
    ObservableList<Student> data =
            FXCollections.observableArrayList(new Student("11111111", "X", 100, 80, 100, 90, 90),
                    new Student("22222222", "Y", 100, 60, 80, 80, 80));

    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        // Create pane
        BorderPane pane = new BorderPane();

        // Place nodes in the pane
        pane.setTop(gridPane());
        pane.setCenter(tableView());
        pane.setBottom(hBox());

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
        gridPane.add(new Label("New table name: "), 0, 0);
        gridPane.add(TableName, 1, 0);
        gridPane.add(btCreateTable, 2, 0);
        gridPane.add(new Label("Student ID *must be 8 digits"), 0, 1);
        gridPane.add(StudentID, 1, 1);
        gridPane.add(new Label("Student Name"), 0, 2);
        gridPane.add(StudentName, 1, 2);
        gridPane.add(new Label("Quiz Marks (enter 0-100)"), 0, 3);
        gridPane.add(QuizMarks, 1, 3);
        gridPane.add(new Label("Assignment 1 Marks (enter 0-100)"), 0, 4);
        gridPane.add(Ass1Marks, 1, 4);
        gridPane.add(new Label("Assignment 2 Marks (enter 0-100)"), 0, 5);
        gridPane.add(Ass2Marks, 1, 5);
        gridPane.add(new Label("Assignment 3 Marks (enter 0-100)"), 0, 6);
        gridPane.add(Ass3Marks, 1, 6);
        gridPane.add(new Label("Exam Marks (enter 0-100)"), 0, 7);
        gridPane.add(ExamMarks, 1, 7);
        gridPane.add(btInsertRecord, 2, 7);


        // Set properties for UI
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(10, 0, 10, 0));
        StudentID.setAlignment(Pos.BOTTOM_RIGHT);
        StudentName.setAlignment(Pos.BOTTOM_RIGHT);
        Marks.setAlignment(Pos.BOTTOM_RIGHT);
        Weight.setAlignment(Pos.BOTTOM_RIGHT);
        Grade.setAlignment(Pos.BOTTOM_RIGHT);
        QuizMarks.setAlignment(Pos.BOTTOM_RIGHT);
        Ass1Marks.setAlignment(Pos.BOTTOM_RIGHT);
        Ass2Marks.setAlignment(Pos.BOTTOM_RIGHT);
        ExamMarks.setAlignment(Pos.BOTTOM_RIGHT);
        WeightedMarks.setAlignment(Pos.BOTTOM_RIGHT);

        return gridPane;
    }

    public TableView tableView() {
        // Create UI
        TableView<Student> tableView = new TableView<>();
        // Set width of table
        tableView.setMaxWidth(552);

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
        Ass1Column.setMinWidth(50);
        Ass1Column.setCellValueFactory(new PropertyValueFactory<Student, Integer>("ass1Marks"));

        TableColumn Ass2Column = new TableColumn("A2");
        Ass2Column.setMinWidth(50);
        Ass2Column.setCellValueFactory(new PropertyValueFactory<Student, Integer>("ass2Marks"));

        TableColumn Ass3Column = new TableColumn("A3");
        Ass2Column.setMinWidth(50);
        Ass2Column.setCellValueFactory(new PropertyValueFactory<Student, Integer>("ass3Marks"));

        TableColumn ExamColumn = new TableColumn("Exam");
        ExamColumn.setMinWidth(50);
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

    private HBox hBox() {

        // Create hbox object
        HBox hbox = new HBox(5); // spacing = 5

        // Add elements to hbox
        hbox.getChildren().addAll(new Label("Average Marks:"), AverageMarks, btAverageMarks, btStudentMarks);

        //Set properties for UI
        hbox.setAlignment(Pos.CENTER);
        hbox.setPadding(new Insets(10, 0, 10, 0));
        GridPane.setHalignment(btStudentMarks, HPos.RIGHT);
        // Make Average Marks text box greyed out and uneditable
        AverageMarks.setEditable(false);
        AverageMarks.setDisable(true);
        AverageMarks.setMaxWidth(50);

        // Process events
        btStudentMarks.setOnAction(e -> studentMarks());
        //btAverageMarks.setOnAction(e -> averageMarks());
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

        return hbox;
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
        private final double results;
        private final String grade;

        // Student constructor
        private Student(String studentID, String name,
                        int quizMarks, int ass1Marks, int ass2Marks, int ass3Marks, int examMarks) {
            // Initialise variables using the arguments passed to the method
            this.studentID = new SimpleStringProperty(studentID);
            this.studentName = new SimpleStringProperty(name);
            this.quizMarks = new SimpleIntegerProperty(quizMarks);
            this.ass1Marks = new SimpleIntegerProperty(ass1Marks);
            this.ass2Marks = new SimpleIntegerProperty(ass2Marks);
            this.ass3Marks = new SimpleIntegerProperty(ass3Marks);
            this.examMarks = new SimpleIntegerProperty(examMarks);
            // Calculate results
            this.results = (quizMarks* 0.05) + (ass1Marks * 0.15) + (ass2Marks * 0.2) + (ass3Marks * 0.1)
                    + (examMarks * 0.5);
            // Call the calculateGrade method and pass results variable
            this.grade = calculateGrade(results);
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
            return this.results;
        }
        public String getGrade() {
            return this.grade;
        }
    }

    private void createTable() throws SQLException, ClassNotFoundException {
        Connection conn = null;
        Statement stmt = null;
        String tableName = TableName.getText();
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

    private void updateTable() throws SQLException, ClassNotFoundException {
        Connection conn = null;
        Statement stmt = null;
        String tableName = TableName.getText();
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

    private void insertRecord() throws SQLException, ClassNotFoundException {
        Connection conn = null;
        Statement stmt = null;
        String tableName = TableName.getText();
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
                StudentID.getText() +"', '" +
                StudentName.getText() +"', '" +
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

    // Method to create a new student object and add to the list
    public void studentMarks() {
        // Declare and initialise variables
        String ID = StudentID.getText();
        String name = StudentName.getText();
        int quizMarks = Integer.parseInt(QuizMarks.getText());
        int ass1Marks = Integer.parseInt(Ass1Marks.getText());
        int ass2Marks = Integer.parseInt(Ass2Marks.getText());
        int ass3Marks = Integer.parseInt(Ass3Marks.getText());
        int examMarks = Integer.parseInt(ExamMarks.getText());
        // Create new student object by passing variables to constructor
        Student student = new Student(ID, name, quizMarks, ass1Marks, ass2Marks, ass3Marks, examMarks);
        // Add the student object to the data list
        data.add(student);
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

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        launch(args);






    }
}