package loggerIntegratedPrototype;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import java.io.*;
import java.time.LocalDate;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * <p> Title: Logger Integrated Prototype. </p>
 * <p> Description: 
 * @author Laela Olsen
 * 
 */

public class PrototypeMain extends Application {
	
	// Database files
	File credentialFile = new File("src\\credential_database");
    File effortFile = new File("src\\effort_database");
    File defectFile = new File("src\\defect_database");
    File reportFile = new File("src\\report_database");
    File projectFile = new File("src\\project_database");
    File secretKeyFile = new File("src\\secretKey");
	
	// Helper functions ------------------------------------------
    
    // Returns true if argument contains only valid characters (A-Z a-z 0-9 - _)
	// This is used on user-names and passwords during sign up
	// We should also use it on all other textual input
	public boolean validCharacters(String input)
	{
		for(int i = 0; i < input.length(); i++)
		{
			char c = input.charAt(i);
			if(!Character.isLetterOrDigit(c) && c != '-' && c != '_')
			{
				return false;
			}
		}
		return true;
	}
	
	// Updates the effort activity list view according to the database
	public ObservableList<GridPane> eaRefreshListView()
	{
		ObservableList<GridPane> eaList = FXCollections.observableArrayList();
		try {
    		String currentLine;
    		BufferedReader databaseReader = new BufferedReader(new FileReader(effortFile));
  		
    		GridPane eaListHeaders = null;
            
    		// Horizontal distance between ListView elements
    		double minWidth = 100;
            
        	// Adds headers to the ListView
    		eaListHeaders = new GridPane();
        	eaListHeaders.setHgap(50);
        	eaListHeaders.setPadding(new Insets(5,5,5,5));
    		
        	Label effortNameHeaderLabel = new Label("Name");
        	effortNameHeaderLabel.setMinWidth(minWidth);
        	GridPane.setConstraints(effortNameHeaderLabel, 0, 0);
        	
        	Label effortProjectNameHeaderLabel = new Label("Project Name");
        	effortProjectNameHeaderLabel.setMinWidth(minWidth);
        	GridPane.setConstraints(effortProjectNameHeaderLabel, 1, 0);
        	
        	Label effortLCSHeaderLabel = new Label("Life Cycle Step");
        	effortLCSHeaderLabel.setMinWidth(minWidth);
        	GridPane.setConstraints(effortLCSHeaderLabel, 2, 0);
        	
        	Label effortECHeaderLabel = new Label("Effort Category");
        	effortECHeaderLabel.setMinWidth(minWidth);
        	GridPane.setConstraints(effortECHeaderLabel, 3, 0);
        	
        	Label effortStartHeaderLabel = new Label("Date Started");
        	effortStartHeaderLabel.setMinWidth(minWidth);
        	GridPane.setConstraints(effortStartHeaderLabel, 4, 0);
        	
        	Label effortEndHeaderLabel = new Label("Date Completed");
        	effortEndHeaderLabel.setMinWidth(minWidth);
        	GridPane.setConstraints(effortEndHeaderLabel, 5, 0);
        	
        	Label effortDescriptionHeaderLabel = new Label("Description");
        	effortDescriptionHeaderLabel.setMinWidth(minWidth);
        	GridPane.setConstraints(effortDescriptionHeaderLabel, 6, 0);
        	
        	eaListHeaders.getChildren().addAll(effortNameHeaderLabel, effortProjectNameHeaderLabel, effortLCSHeaderLabel, effortECHeaderLabel, effortStartHeaderLabel, effortEndHeaderLabel, effortDescriptionHeaderLabel);
        	eaList.add(eaListHeaders);
    		
    		// Adds each effort activity from the database to the ListView
        	while((currentLine = databaseReader.readLine()) != null) 
        	{	
	        	// Checks that the current effort activity belongs to the current user
        		if(currentLine.split(",")[0].equals(currentUsername))
	        	{
    				GridPane GridPaneToAdd = new GridPane();
	        		GridPaneToAdd.setHgap(50);
	        		GridPaneToAdd.setPadding(new Insets(5,5,5,5));
	        		
	        		Label effortNameLabel = new Label(currentLine.split(",")[1]);
	        		effortNameLabel.setMinWidth(minWidth);
	        		
	        		Label effortProjectNameLabel = new Label(currentLine.split(",")[2]);
	        		effortProjectNameLabel.setMinWidth(minWidth);
	        		
	        		Label effortLCSLabel = new Label(currentLine.split(",")[3]);
	        		effortLCSLabel.setMinWidth(minWidth);
	        		
	        		Label effortECLabel = new Label(currentLine.split(",")[4]);
	        		effortECLabel.setMinWidth(minWidth);
	        		
	        		Label effortStartLabel = new Label(currentLine.split(",")[5]);
	        		effortStartLabel.setMinWidth(minWidth);
	        		
	        		Label effortEndLabel = new Label(currentLine.split(",")[6]);
	        		effortEndLabel.setMinWidth(minWidth);
	        		
	        		Label effortDescriptionLabel;
	        		// Checks if there is a description
	        		if(currentLine.split(",").length > 7)
    	        	{
	        			effortDescriptionLabel = new Label(currentLine.split(",")[7]);
    	        		effortDescriptionLabel.setMinWidth(minWidth);
    	        	}
    	        	else
    	        	{
    	        		effortDescriptionLabel = new Label("");
    	        		effortDescriptionLabel.setMinWidth(minWidth);
    	        	}
	        		
	        		GridPaneToAdd.getChildren().addAll(effortNameLabel, effortProjectNameLabel, effortLCSLabel, effortECLabel, effortStartLabel, effortEndLabel, effortDescriptionLabel);
	        		GridPane.setConstraints(effortNameLabel, 0, 0);
	        		GridPane.setConstraints(effortProjectNameLabel, 1, 0);
	        		GridPane.setConstraints(effortLCSLabel, 2, 0);
	        		GridPane.setConstraints(effortECLabel, 3, 0);
	        		GridPane.setConstraints(effortStartLabel, 4, 0);
	        		GridPane.setConstraints(effortEndLabel, 5, 0);
	        		GridPane.setConstraints(effortDescriptionLabel, 6, 0);
	        		
	        		eaList.add(GridPaneToAdd);
	        	}
        	}
        	
        	databaseReader.close();     	
        }
        catch(IOException e) {e.printStackTrace();}
		return eaList;
	}
	
	// Updates the project list view according to the database
	public ObservableList<GridPane> projectRefreshListView()
	{
		ObservableList<GridPane> projectList = FXCollections.observableArrayList();
		try {
			BufferedReader databaseReader = new BufferedReader(new FileReader(projectFile));
			String currentLine;
	    	
			GridPane projectColumnHeaders = new GridPane();
	    	projectColumnHeaders.setHgap(50);
	    	projectColumnHeaders.setPadding(new Insets(5,5,5,5));
			Label projectNameHeaderLabel = new Label("Project Name");
			projectNameHeaderLabel.setMinWidth(100);
			Label membersHeaderLabel = new Label("Project Members");
			projectColumnHeaders.getChildren().addAll(projectNameHeaderLabel, membersHeaderLabel);
			GridPane.setConstraints(projectNameHeaderLabel, 0, 0);
			GridPane.setConstraints(membersHeaderLabel, 1, 0);
			projectList.add(projectColumnHeaders);
			
	    	while((currentLine = databaseReader.readLine()) != null) 
	    	{
	    		GridPane GridPaneToAdd = new GridPane();
	    		GridPaneToAdd.setHgap(50);
	    		GridPaneToAdd.setPadding(new Insets(5,5,5,5));
	    		Label projectNameLabel = new Label(currentLine.split(",", 2)[0]);
	    		projectNameLabel.setMinWidth(100);
	    		Label membersLabel = new Label(currentLine.split(",", 2)[1]);
	    		GridPaneToAdd.getChildren().addAll(projectNameLabel, membersLabel);
	    		GridPane.setConstraints(projectNameLabel, 0, 0);
	    		GridPane.setConstraints(membersLabel, 1, 0);
	    		
	    		projectList.add(GridPaneToAdd);
	    	}
	    	
	    	databaseReader.close();
		}
		catch(IOException e) {e.printStackTrace();}
		return projectList;
	
	}
	
	// Global variables ------------------------------------------------------
	
	// Currently logged in user's type ("Employee", etc.)
    String currentUserType;
    
    // Currently logged in user's user-name ("testUser1", etc.)
    String currentUsername;
    
    // Used for password encryption CHECK
    String algorithm;
    String key;

	@SuppressWarnings("unchecked")
	public void start(Stage primaryStage) throws Exception {
   
		primaryStage.setTitle("Logger Integrated Prototype");
		
		double screenWidth = 720;
		double screenHeight = 540;
		
		double scrollPaneWidth = 700;
		double scrollPaneHeight = 300;
		
		// Encryption cipher is set up CHECK
		
		algorithm = "AES";
        try
        {
        	BufferedReader databaseReader = new BufferedReader(new FileReader(secretKeyFile));
        	key = databaseReader.readLine();
        	databaseReader.close();
        }
        catch(IOException e) {e.printStackTrace();}
		
		// Log In Interface -------------------------------------------------------------
		
		// Creates the pane and scene for the login interface
        VBox root = new VBox(20);
        Scene mainScene = new Scene(root, screenWidth, screenHeight);
        primaryStage.setScene(mainScene);
		
		// Creates and formats the Logger title
        Label loggerTitle = new Label("Logger");
        loggerTitle.setFont(new Font(loggerTitle.getFont().toString(), 30));
        
        // loginGrid will hold the user-name and password input areas
        GridPane loginGrid = new GridPane();
        
        // Creates and formats the user-name area
        Label usernameLabel = new Label("Username:");
        TextField usernameTextField = new TextField ();
        loginGrid.getColumnConstraints().add(new ColumnConstraints(80));
        loginGrid.getColumnConstraints().add(new ColumnConstraints(200));
        loginGrid.getChildren().addAll(usernameLabel, usernameTextField);
        GridPane.setConstraints(usernameLabel, 0, 0);
        GridPane.setConstraints(usernameTextField, 1, 0);
        GridPane.setMargin(usernameLabel, new Insets(5, 5, 5, 5));
        GridPane.setMargin(usernameTextField, new Insets(5, 5, 5, 5));
        
        // Creates and formats the password area
        Label passwordLabel = new Label("Password:");
        PasswordField passwordTextField = new PasswordField ();
        loginGrid.getChildren().addAll(passwordLabel, passwordTextField);
        GridPane.setConstraints(passwordLabel, 0, 1);
        GridPane.setConstraints(passwordTextField, 1, 1);
        GridPane.setMargin(passwordLabel, new Insets(5, 5, 5, 5));
        GridPane.setMargin(passwordTextField, new Insets(5, 5, 5, 5));
        
        // Creates a box to hold the user-name and password fields
        HBox loginGridContainer = new HBox(10);
        loginGridContainer.setAlignment(Pos.CENTER);
        loginGridContainer.getChildren().add(loginGrid);

    	// Creates three types of users to select using radio buttons
    	RadioButton employeeSelect = new RadioButton("Employee");
    	RadioButton managerSelect = new RadioButton("Manager");
    	RadioButton sysAdminSelect = new RadioButton("System Administrator");
        
    	// Inserts the user types into a toggle group
    	ToggleGroup userTypeGroup = new ToggleGroup();
 		employeeSelect.setToggleGroup(userTypeGroup);
 		managerSelect.setToggleGroup(userTypeGroup);
 		sysAdminSelect.setToggleGroup(userTypeGroup);
 		
 		// Creates a box to hold the radio buttons
 		GridPane userTypeGroupBox = new GridPane();
 		userTypeGroupBox.setAlignment(Pos.CENTER);
 		userTypeGroupBox.getChildren().addAll(employeeSelect, managerSelect, sysAdminSelect);
 		userTypeGroupBox.setVgap(5);
 		GridPane.setRowIndex(employeeSelect, 0);
        GridPane.setColumnIndex(employeeSelect, 0);
        GridPane.setRowIndex(managerSelect, 1);
        GridPane.setColumnIndex(managerSelect, 0);
        GridPane.setRowIndex(sysAdminSelect, 2);
        GridPane.setColumnIndex(sysAdminSelect, 0);
        
        // Selects "Employee" by default so there's never nothing selected
        employeeSelect.setSelected(true);

        // Once a user-name and password have been inputed, this button will allow
        // a user to create a new account with those details (Handler below)
        Button signupButton = new Button();
        signupButton.setText("Sign up");
        
        // Once a user-name and password has been inputed, this button will allow
        // a user to enter the tool (Handler below)
        Button loginButton = new Button();
        loginButton.setText("Log in");
        
        // Creates a box to hold the buttons present in the main scene
        HBox buttons = new HBox(40);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(signupButton, loginButton);  
        HBox.setMargin(signupButton, new Insets(10));
        HBox.setMargin(loginButton, new Insets(10));
        
        // errorMessage will display messages to the user if they enter invalid info
        Label errorMessage = new Label();
        
        // All elements are added to the main VBox
        root.setAlignment(Pos.TOP_CENTER);
        root.getChildren().addAll(new Label(), loggerTitle, loginGridContainer, userTypeGroupBox, buttons, errorMessage);
        
        primaryStage.show();
        
        // Option Select Interface ------------------------------------------------------------- 
        
        // Creates the pane and scene for the option select interface
        VBox optionSelectPane = new VBox(20);
        optionSelectPane.setAlignment(Pos.TOP_CENTER);
        Scene optionSelectScene = new Scene(optionSelectPane, screenWidth, screenHeight);
        
        // Adds a title depending on the user type
     	Label loggerTitle2 = new Label("Logger");
        loggerTitle2.setFont(new Font(loggerTitle2.getFont().toString(), 30));
		
		// Buttons for each option (Handlers below)
        Button effortActivities = new Button("Effort Activities");
		Button defects = new Button("Defects");
		Button projects = new Button("Projects");
		Button reports = new Button("Reports");
		Button accounts = new Button("Accounts");
        
        // Allows a user to return to the login page
        Button logoutButton = new Button();
        logoutButton.setText("Log out");
        
        logoutButton.setOnAction(new EventHandler<ActionEvent>() {
        	 
        	public void handle(ActionEvent event) {
        		employeeSelect.setSelected(true);
        		optionSelectPane.getChildren().clear();
        		currentUserType = "";
        		currentUsername = "";
        		primaryStage.setScene(mainScene); 
        	}
        });
        
        // Effort Activity Interface -------------------------------------------------------------
        
        // Creates the pane and scene for the effort activity interface
        VBox eaPane = new VBox(20);
        eaPane.setAlignment(Pos.TOP_CENTER);
        Scene eaScene = new Scene(eaPane, screenWidth, screenHeight);
        
        Label loggerTitle3 = new Label("Logger");
        loggerTitle3.setFont(new Font(loggerTitle3.getFont().toString(), 30));
        
        // Contains the ListView of effort activities
        ScrollPane eaScrollPane = new ScrollPane();
        eaScrollPane.setPrefSize(scrollPaneWidth, scrollPaneHeight);
        
        // Contains the effort activities
        ListView<GridPane> eaListView = new ListView<GridPane>();
        eaListView.setPrefSize(scrollPaneWidth, scrollPaneHeight);
   
        eaScrollPane.setContent(eaListView);

        // The interface at the bottom of the Effort Activity Scene for input entry
        GridPane eaGridPane = new GridPane();
        eaGridPane.setHgap(10);
        eaGridPane.setVgap(10);
        eaGridPane.setPadding(new Insets(10,10,10,10));
        
        // Labels for each input Node
        Label eaNameLabel = new Label("Name");
        Label eaProjectLabel = new Label("Project");
        Label eaLCSLabel = new Label("Life Cycle Step");
        Label eaECLabel = new Label("Effort Category");
        Label eaStartLabel = new Label("Date Started");
        Label eaEndLabel = new Label("Date Completed");
        Label eaDescriptionLabel = new Label("Description");
        
        //Input Nodes
        TextField eaName = new TextField();
        
        ChoiceBox<String> eaProject = new ChoiceBox<String>();
        
        // Reads the names of each project to populate the project ChoiceBox
        String line;
        try
        {
        	BufferedReader databaseReader = new BufferedReader(new FileReader(projectFile));
        	while ((line = databaseReader.readLine()) != null) {
				eaProject.getItems().add(line.split(",")[0]);
			}
        	databaseReader.close();
        }
        catch(IOException e) {e.printStackTrace();}
        
        
        @SuppressWarnings("rawtypes")
		ChoiceBox eaLCS = new ChoiceBox();
        eaLCS.getItems().addAll("Planning", "Information Gathering", "Information Understanding", 
        		"Verifying", "Outlining", "Drafting", "Finalizing", "Team Meeting", "Coach Meeting", 
        		"Stakeholder Meeting", new Separator(), "Problem Understanding", "Conceptual Design Plan", 
        		"Requirements", "Conceptual Design", "Conceptual Design Review", "Detailed Design Plan", 
        		"Detailed Design/Prototype", "Detailed Design Review", "Implementation Plan", 
        		"Test Case Generation", "Solution Specification", "Solution Review", 
        		"Solution Implementation", "Unit/System Test", "Reflection", "Repository Update");
        
        ChoiceBox<String> eaEC = new ChoiceBox<String>();
        eaEC.getItems().addAll("Plans", "Deliverables", "Other");
          
        DatePicker eaStart = new DatePicker();
        
        DatePicker eaEnd = new DatePicker();
        
        TextArea eaDescription = new TextArea();
        eaDescription.setPrefColumnCount(20);
        
        eaGridPane.getChildren().addAll(eaNameLabel, eaProjectLabel, eaLCSLabel, eaECLabel, eaStartLabel, eaEndLabel, eaDescriptionLabel, eaName, eaProject, eaLCS, eaEC, eaStart, eaEnd, eaDescription);
        
        GridPane.setConstraints(eaNameLabel, 0, 0);
        GridPane.setConstraints(eaProjectLabel, 1, 0);
        GridPane.setConstraints(eaLCSLabel, 2, 0);
        GridPane.setConstraints(eaECLabel, 3, 0);
        GridPane.setConstraints(eaName, 0, 1);
        GridPane.setConstraints(eaProject, 1, 1);
        GridPane.setConstraints(eaLCS, 2, 1);
        GridPane.setConstraints(eaEC, 3, 1);
        GridPane.setConstraints(eaStartLabel, 0, 2);
        GridPane.setConstraints(eaEndLabel, 1, 2);
        GridPane.setConstraints(eaDescriptionLabel, 2, 2);
        GridPane.setConstraints(eaStart, 0, 3);
        GridPane.setConstraints(eaEnd, 1, 3);
        GridPane.setConstraints(eaDescription, 2, 3, 2, 1);
        
        // Buttons for the effort activity interface
        HBox eaButtons = new HBox(40);
        eaButtons.setAlignment(Pos.CENTER);
        
        Button logEffortActivityButton = new Button("Log Effort Activity");
        Button deleteEffortActivityButton = new Button("Delete Effort Activity");
        Button eaBackButton = new Button("Back");
        
        HBox.setMargin(logEffortActivityButton, new Insets(10));
        HBox.setMargin(deleteEffortActivityButton, new Insets(10));
        HBox.setMargin(eaBackButton, new Insets(10));
        
        eaButtons.getChildren().addAll(logEffortActivityButton, deleteEffortActivityButton, eaBackButton);
        
        eaPane.getChildren().addAll(new Label(), loggerTitle3, eaScrollPane, eaGridPane, eaButtons);
        
        // Defect Interface -------------------------------------------------------------
        
        // Report Interface -------------------------------------------------------------
        
        // Project Interface -------------------------------------------------------------
        
        // Creates the pane and scene for the project interface
        VBox projectBox = new VBox(20);
        projectBox.setAlignment(Pos.TOP_CENTER);
        Scene projectScene = new Scene(projectBox, screenWidth, screenHeight);
        
        Label loggerTitle4 = new Label("Logger");
        loggerTitle4.setFont(new Font(loggerTitle4.getFont().toString(), 30));
        
        // Contains the ListView of projects
        ScrollPane projectScrollPane = new ScrollPane();
        projectScrollPane.setPrefSize(scrollPaneWidth, scrollPaneHeight);
        
        // Contains the list of projects
        ListView<GridPane> projectListView = new ListView<GridPane>();
        projectListView.setPrefSize(scrollPaneWidth, scrollPaneHeight);

        projectListView.setItems(projectRefreshListView());
        
        projectScrollPane.setContent(projectListView);
        
        // Labels for each input Node
        Label projectNameLabel = new Label("Project Name");
        Label projectMembersLabel = new Label("Members");
        
        // Input Nodes
        TextField projectName = new TextField();
        TextArea projectMembers = new TextArea();
        projectMembers.setPromptText("username1, username2, ...");
        
        // Changes the input fields when a project from the list is selected
        projectListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<GridPane>() {
            @Override
            public void changed(ObservableValue<? extends GridPane> observable, GridPane oldValue, GridPane newValue) {
                
            	if(newValue != null && !((Label)newValue.getChildren().get(0)).getText().equals("Project Name"))
            	{
            		projectName.setText(((Label)newValue.getChildren().get(0)).getText());
            		projectMembers.setText(((Label)newValue.getChildren().get(1)).getText());
            		projectName.setStyle("");
            	}
            	// Nothing is shown if the user selects nothing or selects the list headers
            	else if(newValue != null &&((Label)newValue.getChildren().get(0)).getText().equals("Project Name"))
		        {
            		projectName.setText("");
            		projectMembers.setText("");
            		projectName.setStyle("");
		        }
            }
        });
        
        // Contains the input Nodes
        GridPane projectGridPane = new GridPane();
        projectGridPane.setHgap(10);
        projectGridPane.setVgap(10);
        projectGridPane.setPadding(new Insets(10,10,10,10));
        
        GridPane.setConstraints(projectNameLabel, 0, 0);
        GridPane.setConstraints(projectMembersLabel, 1, 0);
        GridPane.setConstraints(projectName, 0, 1);
        GridPane.setConstraints(projectMembers, 1, 1);
        
        projectGridPane.getChildren().setAll(projectNameLabel, projectMembersLabel, projectName, projectMembers);
 
        // Buttons for the project interface (Handlers below)
        HBox projectButtons = new HBox(40);
        projectButtons.setAlignment(Pos.CENTER);
        
        Button createProject = new Button("Create Project");
        Button editProject = new Button("Change Project Members");
        Button deleteProject = new Button("Delete Project");
        Button projectBackButton = new Button("Back");
        
        HBox.setMargin(createProject, new Insets(10));
        HBox.setMargin(deleteProject, new Insets(10));
        HBox.setMargin(eaBackButton, new Insets(10));
        
        projectButtons.getChildren().addAll(createProject, editProject, deleteProject, projectBackButton);
        
        projectBox.getChildren().addAll(new Label(), loggerTitle4, projectScrollPane, projectGridPane, projectButtons);
     
        //Account Interface -------------------------------------------------------------
        
        
        // Button EventHandlers -------------------------------------------------------------
        EventHandler<ActionEvent> signUpButtonEventHandler = new EventHandler<ActionEvent>() {
 
            @Override
            // The info given is checked for validity
            public void handle(ActionEvent event) {
            	
            	boolean duplicateUsername = false;
            	String line; // Holds one line from the credential database
                
            	// Checks if the user-name is already taken
            	try
                {
                	BufferedReader databaseReader = new BufferedReader(new FileReader(credentialFile));
                	while ((line = databaseReader.readLine()) != null) {
						if(usernameTextField.getText().equals(line.split(",")[0]))
						{
							duplicateUsername = true;
							break;
						}
					}
                	databaseReader.close();
                }
                catch(IOException e) {e.printStackTrace();}
            	
            	if(duplicateUsername)
                {
                    errorMessage.setTextFill(Color.color(1,0,0));
            		errorMessage.setText("Username \"" + usernameTextField.getText() + "\" unavailable");
                }
            	
            	// Checks if the user-name or password do not fall within the character bounds
            	else if(usernameTextField.getText().length() < 3 || usernameTextField.getText().length() > 16)
            	{
                    errorMessage.setTextFill(Color.color(1,0,0));
            		errorMessage.setText("Username must be between 3 and 16 characters");
            	}
            	else if(passwordTextField.getText().length() < 8 || passwordTextField.getText().length() > 128)
            	{
                    errorMessage.setTextFill(Color.color(1,0,0));
            		errorMessage.setText("Password must be between 8 and 128 characters");
            		passwordTextField.setText("");
            	}
            	// Checks credentials for illegal characters
            	else if(!validCharacters(usernameTextField.getText()) || !validCharacters(passwordTextField.getText()))
            	{
            		errorMessage.setTextFill(Color.color(1,0,0));
            		errorMessage.setText("Username and password can only contain letters (A-Z, a-z) , digits (0-9), -, and _");
            		passwordTextField.setText("");
            	}
            	// Input is valid
            	else
                {
            		if(userTypeGroup.getSelectedToggle().equals(employeeSelect))
            		{
            			currentUserType = "Employee";
            		}
            		else if(userTypeGroup.getSelectedToggle().equals(managerSelect))
            		{
            			currentUserType = "Manager";
            		}
            		else if(userTypeGroup.getSelectedToggle().equals(sysAdminSelect))
            		{
            			currentUserType = "System Administrator";
            		}
            		// Credentials are written to the database
            		try
            		{   		
		
            			BufferedWriter databaseWriter = new BufferedWriter(new FileWriter(credentialFile, true));
            			
            			//CHECK encrypt password at this line
            			SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), algorithm);
            	        Cipher cipher = Cipher.getInstance(algorithm);
            	        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            	        byte[] valueBytes = passwordTextField.getText().getBytes();
            	        int blockSize = cipher.getBlockSize();
            	        
            	        byte[] paddedBytes = new byte[blockSize * ((valueBytes.length + blockSize - 1) / blockSize)];
            	        System.arraycopy(valueBytes, 0, paddedBytes, 0, valueBytes.length); 
            	        byte[] encryptedValue = cipher.doFinal(paddedBytes);
            	        String encryptedMessage = Base64.getEncoder().encodeToString(encryptedValue);
            			
            			
            			databaseWriter.write(usernameTextField.getText() + "," + encryptedMessage + "," + currentUserType);
            			databaseWriter.newLine();
	            		databaseWriter.close();
            		}
            		catch(Exception e) {e.printStackTrace();}
            		
                    errorMessage.setTextFill(Color.color(0,0,0));
            		errorMessage.setText("Account created. Welcome, "+ currentUserType + " " + usernameTextField.getText()+"!");
            		usernameTextField.setText("");
            		passwordTextField.setText("");
            		employeeSelect.setSelected(true);
            		currentUserType = "";
                }
            }
        };
        signupButton.setOnAction(signUpButtonEventHandler);
        
        EventHandler<ActionEvent> loginButtonEventHandler = new EventHandler<ActionEvent>() {
 
        	// Credentials are checked for validity and whether they're in the database
        	@Override
            public void handle(ActionEvent event) {
        		boolean validUser = false;
            	String line = null;
            	// Searches credential_database for the given credentials
            	try
                {
                	BufferedReader databaseReader = new BufferedReader(new FileReader(credentialFile));               	
                	while ((line = databaseReader.readLine()) != null) {
                		//CHECK
                		SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), algorithm);
                        Cipher cipher = Cipher.getInstance(algorithm);
                        cipher.init(Cipher.DECRYPT_MODE, keySpec);
                        byte[] decodedValue = Base64.getDecoder().decode(line.split(",")[1]);
                        byte[] decryptedValue = cipher.doFinal(decodedValue);
                        String plainText = (new String(decryptedValue));
                        plainText = plainText.trim();
                        
            		    if(usernameTextField.getText().equals(line.split(",")[0]) && 
								passwordTextField.getText().equals(new String(plainText)) && 
								(userTypeGroup.getSelectedToggle().equals(employeeSelect) && 
								line.split(",")[2].equals("Employee") || 
								userTypeGroup.getSelectedToggle().equals(managerSelect) && 
								line.split(",")[2].equals("Manager") || 
								userTypeGroup.getSelectedToggle().equals(sysAdminSelect) && 
								line.split(",")[2].equals("System Administrator")))
						{
							validUser = true;
							break;
						}
					}
                	databaseReader.close();
                }
                catch(Exception e) {e.printStackTrace();}
            	
            	// Upon successful login, scene is changed and access is granted
            	if(validUser)
                {
            		if(userTypeGroup.getSelectedToggle().equals(employeeSelect))
            		{
            			currentUserType = "Employee";
            		}
            		else if(userTypeGroup.getSelectedToggle().equals(managerSelect))
            		{
            			currentUserType = "Manager";
            		}
            		else if(userTypeGroup.getSelectedToggle().equals(sysAdminSelect))
            		{
            			currentUserType = "System Administrator";
            		}
            		
            		currentUsername = usernameTextField.getText();
            		
            		usernameTextField.setText("");
            		passwordTextField.setText("");
            		errorMessage.setTextFill(Color.color(0,0,0));
            		errorMessage.setText("");
                
            		// The option select interface is set up
            		optionSelectPane.getChildren().addAll(new Label(), loggerTitle2);
            		optionSelectPane.getChildren().add(new Label("Access granted. Welcome, " + currentUserType + " " + currentUsername + "!"));
                    
    				// Determines the needed buttons based on the radio buttons from the primary scene
    				if (line.split(",")[2].equals("Employee")) {
    					optionSelectPane.getChildren().addAll(effortActivities, defects, projects);
    				} else if (line.split(",")[2].equals("Manager")) {
    					optionSelectPane.getChildren().addAll(effortActivities, defects, reports, projects);
    				} else if (line.split(",")[2].equals("System Administrator")) {
    					optionSelectPane.getChildren().addAll(effortActivities, defects, reports, projects, accounts);
    				}

            		optionSelectPane.getChildren().add(logoutButton);
            		primaryStage.setScene(optionSelectScene);          		
                }
            	// An error message is displayed if either field is left blank
            	else if(usernameTextField.getText().length() == 0)
            	{
                    errorMessage.setTextFill(Color.color(1,0,0));
            		errorMessage.setText("Username field cannot be left blank");
            	}
            	else if(passwordTextField.getText().length() == 0) 
            	{
                    errorMessage.setTextFill(Color.color(1,0,0));
            		errorMessage.setText("Password field cannot be left blank");
            	}
            	// The credentials were not found in the database
            	else
                {
                    errorMessage.setTextFill(Color.color(1,0,0));
            		errorMessage.setText("Incorrect username, password, or account type");
                	passwordTextField.setText("");
                }
            }
        };
        loginButton.setOnAction(loginButtonEventHandler);
        
     // Handles entering the effort activities interface
        EventHandler<ActionEvent> effortActivitiesEventHandler = new EventHandler<ActionEvent>() {
        	@Override
            public void handle(ActionEvent event) {
        		// If any input Nodes were previously red, they are set back to default black
        		eaName.setStyle("");
        		eaProject.setStyle("");
        		eaLCS.setStyle("");
        		eaEC.setStyle("");
        		eaStart.setStyle("");
        		eaEnd.setStyle("");
        		
        		// Sets up the ListView based on effort_database
        		eaListView.setItems(eaRefreshListView());
	        	
	        	// Refreshes the list of projects using the project_database
        		String line;
	        	eaProject.getItems().clear();
	            try
	            {
	            	BufferedReader databaseReader = new BufferedReader(new FileReader(projectFile));
	            	while ((line = databaseReader.readLine()) != null) {
	    				eaProject.getItems().add(line.split(",")[0]);
	    			}
	            	databaseReader.close();
	            }
	            catch(IOException e) {e.printStackTrace();}
	        		
	        	primaryStage.setScene(eaScene);	
        	}
        };
        effortActivities.setOnAction(effortActivitiesEventHandler);
        
        // Handles clicking on an effort activity ListView element
        eaListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<GridPane>() {
            @Override
            public void changed(ObservableValue<? extends GridPane> observable, GridPane oldValue, GridPane newValue) {
                
            	// Checks that the element clicked was not the header row
            	if(newValue != null && !((Label)newValue.getChildren().get(0)).getText().equals("Name"))
            	{
            		eaName.setText(((Label)newValue.getChildren().get(0)).getText());
            		eaProject.getSelectionModel().select(((Label)newValue.getChildren().get(1)).getText());
            		eaLCS.getSelectionModel().select(((Label)newValue.getChildren().get(2)).getText());
            		eaEC.getSelectionModel().select(((Label)newValue.getChildren().get(3)).getText());
            		
            		eaStart.setValue(LocalDate.of(Integer.parseInt(((Label)newValue.getChildren().get(4)).getText().split("-")[0]), Integer.parseInt(((Label)newValue.getChildren().get(4)).getText().split("-")[1]), Integer.parseInt(((Label)newValue.getChildren().get(4)).getText().split("-")[2])));
            		eaEnd.setValue(LocalDate.of(Integer.parseInt(((Label)newValue.getChildren().get(5)).getText().split("-")[0]), Integer.parseInt(((Label)newValue.getChildren().get(5)).getText().split("-")[1]), Integer.parseInt(((Label)newValue.getChildren().get(5)).getText().split("-")[2])));
            		eaDescription.setText(((Label)newValue.getChildren().get(6)).getText());
            		
            		eaName.setStyle("");
            		eaProject.setStyle("");
            		eaLCS.setStyle("");
            		eaEC.setStyle("");
            		eaStart.setStyle("");
            		eaEnd.setStyle("");
            	}
            	// The element clicked was the header row
            	else if(newValue != null &&((Label)newValue.getChildren().get(0)).getText().equals("Name"))
		        {
            		eaName.setText("");
            		eaProject.getSelectionModel().clearSelection();
            		eaLCS.getSelectionModel().clearSelection();
            		eaEC.getSelectionModel().clearSelection();
            		eaStart.setValue(null);
            		eaEnd.setValue(null);
            		eaDescription.setText("");
            		
            		eaName.setStyle("");
            		eaProject.setStyle("");
            		eaLCS.setStyle("");
            		eaEC.setStyle("");
            		eaStart.setStyle("");
            		eaEnd.setStyle("");
		        }
            }
        });
        
     // Handles creating or editing an effort activity 
	    EventHandler<ActionEvent> logEffortActivityEventHandler = new EventHandler<ActionEvent>() {
	    	@Override
	        public void handle(ActionEvent event) {
        		// All input Nodes are set to default black upon successful creation or edit
	    		eaName.setStyle("");
        		eaProject.setStyle("");
        		eaLCS.setStyle("");
        		eaEC.setStyle("");
        		eaStart.setStyle("");
        		eaEnd.setStyle("");
	    		try
        		{   		
    					
	        		boolean eaDuplicateName = false;
	        		boolean eaInvalid = false;
	        		
	        		BufferedReader databaseReader = new BufferedReader(new FileReader(effortFile));
                	String currentLine;
                	// Checks whether to edit an activity (name already exists) or create a new effort effort
                	while((currentLine = databaseReader.readLine()) != null) 
                	{
                		if(currentLine.split(",")[1].equals(eaName.getText()))
                		{
                			eaDuplicateName = true;
                		}
                	}
                	// These if statements check if various input Nodes are blank
                	// and set them to have a red outline if they are
                	if(eaName.getText().equals(""))
                	{
                		eaName.setStyle("-fx-effect: dropshadow(three-pass-box, tomato, 3, 0.8, 0, 0);");
                		eaInvalid = true;
                	}
                	if(eaProject.getSelectionModel().getSelectedItem() == null)
                	{
                		eaProject.setStyle("-fx-effect: dropshadow(three-pass-box, tomato, 3, 0.8, 0, 0);");
                		eaInvalid = true;
                	}
                	if(eaLCS.getSelectionModel().getSelectedItem() == null)
                	{
                		eaLCS.setStyle("-fx-effect: dropshadow(three-pass-box, tomato, 3, 0.8, 0, 0);");
                		eaInvalid = true;
                	}
                	if(eaEC.getSelectionModel().getSelectedItem() == null)
                	{
                		eaEC.setStyle("-fx-effect: dropshadow(three-pass-box, tomato, 3, 0.8, 0, 0);");
                		eaInvalid = true;
                	}
                	if(eaStart.getValue() == null)
                	{
                		eaStart.setStyle("-fx-effect: dropshadow(three-pass-box, tomato, 3, 0.8, 0, 0);");
                		eaInvalid = true;
                	}
                	if(eaEnd.getValue() == null)
                	{
                		eaEnd.setStyle("-fx-effect: dropshadow(three-pass-box, tomato, 3, 0.8, 0, 0);");
                		eaInvalid = true;
                	}
                	// Checks if the start time is after the end time
                	if(eaStart.getValue().isAfter(eaEnd.getValue()))
                	{
                		eaStart.setStyle("-fx-effect: dropshadow(three-pass-box, tomato, 3, 0.8, 0, 0);");
                		eaEnd.setStyle("-fx-effect: dropshadow(three-pass-box, tomato, 3, 0.8, 0, 0);");
                		eaInvalid = true;
                	}
                	databaseReader.close();
                	
	    			// A new, valid, effort activity is created
                	if(!eaDuplicateName && !eaInvalid)
	        		{
	    				BufferedWriter databaseWriter = new BufferedWriter(new FileWriter(effortFile, true));
	        			databaseWriter.write(currentUsername + "," +
	        					eaName.getText() + "," + 
	        					eaProject.getSelectionModel().getSelectedItem() + "," + 
	        					eaLCS.getSelectionModel().getSelectedItem() + "," + 
	        					eaEC.getSelectionModel().getSelectedItem() +  "," +
	        					eaStart.getValue().toString() + "," +
	        					eaEnd.getValue().toString() + "," +
	        					eaDescription.getText());
	        			databaseWriter.newLine();
	            		databaseWriter.close();
	        		}
	    			// The effort activity specified is edited
                	else if(eaDuplicateName && !eaInvalid)
	    			{
	    				BufferedReader file = new BufferedReader(new FileReader(effortFile));
		    	        StringBuffer inputBuffer = new StringBuffer();
		    	        String line;
	
		    	        // Reads through effort_database and edits the correct entry
		    	        while ((line = file.readLine()) != null) {
		    	            if(line.split(",")[1].equals(eaName.getText()))
		    	            {
		    	            	inputBuffer.append(currentUsername + "," +
			        					eaName.getText() + "," + 
			        					eaProject.getSelectionModel().getSelectedItem() + "," + 
			        					eaLCS.getSelectionModel().getSelectedItem() + "," + 
			        					eaEC.getSelectionModel().getSelectedItem() +  "," +
			        					eaStart.getValue().toString() + "," +
			        					eaEnd.getValue().toString() + "," +
			        					eaDescription.getText());
		    	            }
		    	            else
	    	            	{
		    	            	inputBuffer.append(line);
	    	            	}
		    	            inputBuffer.append('\n');
		    	        }
		    	        file.close();
	
		    	        FileOutputStream fileOut = new FileOutputStream(effortFile);
		    	        fileOut.write(inputBuffer.toString().getBytes());
		    	        fileOut.close();
	
	    			}
 	
                }
                catch(IOException e) {e.printStackTrace();}
                
	    		// Refreshes the ListView based on effort_database to reflect the change
	    		eaListView.setItems(eaRefreshListView());
	    	}
	    };
	    logEffortActivityButton.setOnAction(logEffortActivityEventHandler);
	    
	 // Handles deleting an effort activity 
	    EventHandler<ActionEvent> deleteEffortActivityEventHandler = new EventHandler<ActionEvent>() {
	    	@Override
	        public void handle(ActionEvent event) {
	    		try {
	    	        BufferedReader file = new BufferedReader(new FileReader(effortFile));
	    	        StringBuffer inputBuffer = new StringBuffer();
	    	        String line;

	    	     // Reads through project_database and removes the given project
	    	        while ((line = file.readLine()) != null) {
	    	            if(!((line.split(",")[1].equals(eaName.getText())) && (line.split(",")[0].equals(currentUsername))))
	    	            {
	    	            	inputBuffer.append(line);
	    	            	inputBuffer.append('\n');
	    	            }
	    	        }
	    	        file.close();

	    	        FileOutputStream fileOut = new FileOutputStream(effortFile);
	    	        fileOut.write(inputBuffer.toString().getBytes());
	    	        fileOut.close();
	    	        
	    	        eaListView.setItems(eaRefreshListView());
	    	    } 
	    		catch (Exception e) {e.printStackTrace();}
	    	}
	    };
	    deleteEffortActivityButton.setOnAction(deleteEffortActivityEventHandler);
        
     // Handles entering the defects interface
        EventHandler<ActionEvent> defectsEventHandler = new EventHandler<ActionEvent>() {
        	@Override
            public void handle(ActionEvent event) {
        		
        	}
        };
        defects.setOnAction(defectsEventHandler);
        
     // Handles entering the reports interface
        EventHandler<ActionEvent> reportsEventHandler = new EventHandler<ActionEvent>() {
        	@Override
            public void handle(ActionEvent event) {
        	}
        };
        reports.setOnAction(reportsEventHandler);
        
     // Handles entering the projects interface
        EventHandler<ActionEvent> projectsEventHandler = new EventHandler<ActionEvent>() {
        	@Override
            public void handle(ActionEvent event) {
        		primaryStage.setScene(projectScene);
        		projectListView.getSelectionModel().clearSelection();
        	}
        };
        projects.setOnAction(projectsEventHandler);
        
     // Handles creating a project
	    EventHandler<ActionEvent> createProjectEventHandler = new EventHandler<ActionEvent>() {
	    	@Override
	        public void handle(ActionEvent event) {
	    		try
        		{   					
	    			projectName.setStyle("");
	    			
	    			boolean projectInvalidName = false;
	        		
	    			BufferedReader databaseReader = new BufferedReader(new FileReader(projectFile));
                	String currentLine;
                	// Checks if the given project name has already been used
                	while((currentLine = databaseReader.readLine()) != null) 
                	{
                		if(currentLine.split(",")[0].equals(projectName.getText()))
                		{
                			projectName.setStyle("-fx-effect: dropshadow(three-pass-box, tomato, 3, 0.8, 0, 0);");
                			projectInvalidName = true;
                		}
                	}
                	// Checks if the given project name is blank
                	if(projectName.getText().equals(""))
                	{
                		projectName.setStyle("-fx-effect: dropshadow(three-pass-box, tomato, 3, 0.8, 0, 0);");
                		projectInvalidName = true;
                	}
                	databaseReader.close();
	    			
                	// Check if the project name is valid
                	if(!projectInvalidName)
	        		{
	    				BufferedWriter databaseWriter = new BufferedWriter(new FileWriter(projectFile, true));
	        			databaseWriter.write(projectName.getText() + "," + projectMembers.getText());
	        			databaseWriter.newLine();
	            		databaseWriter.close();
	            		
	            		projectListView.setItems(projectRefreshListView());
	        		}
        		}
        		catch(Exception e) {e.printStackTrace();}
	    	}
	    };
	    createProject.setOnAction(createProjectEventHandler);
	    
	 // Handles editing the member list of a project
	    EventHandler<ActionEvent> editProjectEventHandler = new EventHandler<ActionEvent>() {
	    	@Override
	        public void handle(ActionEvent event) {
		    	// Only non-employees can edit and delete projects
	    		if(!currentUserType.equals("Employee"))
		    	{
	    			try {
		    	        BufferedReader file = new BufferedReader(new FileReader(projectFile));
		    	        StringBuffer inputBuffer = new StringBuffer();
		    	        String line;
	
		    	        // Reads through project_database and edits the given project
		    	        while ((line = file.readLine()) != null) {
		    	            if(line.split(",")[0].equals(projectName.getText()))
		    	            {
		    	            	inputBuffer.append(line.split(",")[0] + "," + projectMembers.getText());
		    	            }
		    	            else
	    	            	{
		    	            	inputBuffer.append(line);
	    	            	}
		    	            inputBuffer.append('\n');
		    	        }
		    	        file.close();
	
		    	        FileOutputStream fileOut = new FileOutputStream(projectFile);
		    	        fileOut.write(inputBuffer.toString().getBytes());
		    	        fileOut.close();
	            		
		    	        projectListView.setItems(projectRefreshListView());
		    	    } 
		    		catch (Exception e) {e.printStackTrace();}
	    		}
	    	}
	    };
	    editProject.setOnAction(editProjectEventHandler);
	    
	 // Handles deleting a project
	    EventHandler<ActionEvent> deleteProjectEventHandler = new EventHandler<ActionEvent>() {
	    	@Override
	        public void handle(ActionEvent event) {
	    		if(!currentUserType.equals("Employee"))
		    	{
	    			try {
		    	        BufferedReader file = new BufferedReader(new FileReader(projectFile));
		    	        StringBuffer inputBuffer = new StringBuffer();
		    	        String line;
	
		    	     // Reads through project_database and removes the given project
		    	        while ((line = file.readLine()) != null) {
		    	            if(!line.split(",")[0].equals(projectName.getText()))
		    	            {
		    	            	inputBuffer.append(line);
		    	            	inputBuffer.append('\n');
		    	            }
		    	        }
		    	        file.close();
	
		    	        FileOutputStream fileOut = new FileOutputStream(projectFile);
		    	        fileOut.write(inputBuffer.toString().getBytes());
		    	        fileOut.close();
		    	        
		    	        projectListView.setItems(projectRefreshListView());
		    	    } 
		    		catch (Exception e) {e.printStackTrace();}
	    		}
	    	}
	    };
	    deleteProject.setOnAction(deleteProjectEventHandler);
        
     // Handles entering the accounts interface
        EventHandler<ActionEvent> accountsEventHandler = new EventHandler<ActionEvent>() {
        	@Override
            public void handle(ActionEvent event) {
        		     		
        	}
        };
        accounts.setOnAction(accountsEventHandler);
     
     // Handles returning to the option select Scene 
	    EventHandler<ActionEvent> backEventHandler = new EventHandler<ActionEvent>() {
	    	@Override
	        public void handle(ActionEvent event) {
	    		primaryStage.setScene(optionSelectScene);
	    	}
	    };
	    eaBackButton.setOnAction(backEventHandler);
	    projectBackButton.setOnAction(backEventHandler);
   
    }
	
	public static void main(String[] args)
	{
		launch(args);
	}

}
