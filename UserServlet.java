package banking;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */

    public UserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username = request.getParameter("username");
		String password = request.getParameter("password");
//		
//		
		System.out.println(username + " " + password);
		response.getWriter().append("Served at: ").append(request.getContextPath());
//		 String password = request.getParameter("password");
	    
	    // Logging to the console
	    System.out.println("Username: " + username + ", Password: " + password);

	    // Respond back to client
	    response.setContentType("text/html");
	    response.getWriter().append("Request received at: ").append(request.getContextPath())
	            .append("<br/>Username: ").append(username)
	            .append("<br/>Password: ").append(password);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		System.out.println("POST");
		System.out.println(action);
        if ("register".equals(action)) {
            try {
				handleRegistration(request, response ,  getServletContext());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } else if ("login".equals(action)) {
            try {
				handleLogin(request, response,  getServletContext());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
		
	}
	
	

	

	
	public Transaction[] convertJsonToTransactions(JSONArray transactionsJsonArray) throws JSONException {
	    Transaction[] transactions = new Transaction[transactionsJsonArray.length()];

	    for (int i = 0; i < transactionsJsonArray.length(); i++) {
	        JSONObject transactionJson = transactionsJsonArray.getJSONObject(i);
	        String transactionId = transactionJson.getString("transactionId");
	        String date = transactionJson.getString("date");
	        double amount = transactionJson.getDouble("amount");
	        String type = transactionJson.getString("type");

	        // Assuming Transaction has a constructor that matches these fields
	        transactions[i] = new Transaction(transactionId, date, amount, type);
	    }

	    return transactions;
	}

	public Loan convertJsonToLoan(JSONObject loanDetailsJson) throws JSONException {
	    String loanType = loanDetailsJson.getString("type"); // Assuming there's a type field
	    double principalAmount = loanDetailsJson.getDouble("principalAmount");
	    double interestRate = loanDetailsJson.getDouble("interestRate");
	    int loanPeriod = loanDetailsJson.getInt("tenure");

	    if ("EducationLoan".equals(loanType)) {
	        return new EducationLoan(principalAmount, interestRate, loanPeriod);
	    } else if ("PersonalLoan".equals(loanType)) {
	        return new PersonalLoan(principalAmount, interestRate, loanPeriod);
	    } else {
	        // Handle unknown loan type or throw exception
	        throw new IllegalArgumentException("Unknown loan type: " + loanType);
	    }
	}

	
	private void handleLogin(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext) throws IOException, JSONException {
	    
		
		
		String username = request.getParameter("username");
	    String password = request.getParameter("password");
	    
	    System.out.println(username + " " + password);
	    
	    JSONArray users = readUsers(servletContext);
	    System.out.println(users);
	    System.out.println("Users have been read");
	    for (int i = 0; i < users.length(); i++) {
	    	System.out.println("HI inside the loop");
	        JSONObject userJson = users.getJSONObject(i);
	        System.out.println("HI inside the loop");
	        System.out.println(userJson);

	        if (userJson.getString("username").equals(username) && userJson.getString("password").equals(password)) {
	            // Create a User object from JSONObject

	        	String email = userJson.getString("email");
	        	String phone = userJson.getString("phone");
	        	String accNo = userJson.getString("accNo");
	        	boolean isActive = userJson.getBoolean("isActive");
	        	double balance = userJson.getDouble("balance");
	        	boolean haveTakenLoan = userJson.getBoolean("haveTakenLoan");
	        	Loan loanDetails = null;
	        	if (haveTakenLoan) {
	        	    JSONObject loanDetailsJson = userJson.optJSONObject("loanDetails"); // Use optJSONObject to handle if it's not present
	        	    if (loanDetailsJson != null) {
	        	        loanDetails = convertJsonToLoan(loanDetailsJson); // Convert only if not null
	        	    }
	        	}
	        	String typeOfAccount = userJson.getString("typeOfAccount");
	        	JSONArray transactionsJsonArray = userJson.getJSONArray("transactions");
	        	// Convert transactionsJsonArray to Transaction[] array
	        	Transaction[] transactions = convertJsonToTransactions(transactionsJsonArray); // You need to implement this method
	        	boolean isAdmin = userJson.getBoolean("isAdmin");
	        		
	        	// Assuming typeOfAccount is a string representing the account type
	        	Account accType = null;
	        	

	        	// Check the value of typeOfAccount and instantiate the appropriate account type
	        	if ("Savings".equals(typeOfAccount)) {
	        	    accType = new SavingsAccount();
	        	} else if ("Salary".equals(typeOfAccount)) {
	        	    accType = new SalaryAccount();
	        	} else if ("Current".equals(typeOfAccount)) {
	        	    accType = new CurrentAccount();
	        	} else {
	        	    // Handle the case when the account type is unknown or invalid
	        	    // For example, you can throw an exception or set accType to null
	        	    // Depending on your application's requirements
	        	    // Handle accordingly based on your use case
	        	    // Example:
	        	    throw new IllegalArgumentException("Invalid account type: " + typeOfAccount);
	        	}

	        	// Now accType holds the instance of the appropriate account type based on the value of typeOfAccount

	        	// Now create the User object with all the extracted data
	        	User user = new User(accNo, username, password, email, phone, isActive, balance, haveTakenLoan, loanDetails, accType, transactions, isAdmin);	        

        // Store User object in session
	            request.getSession().setAttribute("user", user);

	            try {
	                request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
	            } catch (ServletException e) {
	                System.out.println("Servlet Exception");
	                e.printStackTrace();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	            return;
	        }
	    }

	    // Login failed
	    response.sendRedirect("login.html?error=invalid_credentials");
	}

	
    private void handleRegistration(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext) throws IOException, JSONException {
		// Get the real path to 'users.json' in the server context
		String realPathToUsersFile = servletContext.getRealPath("/users.json");
		System.out.println("Real Path for writing: " + realPathToUsersFile);
	
		// Retrieve user details from the request parameters
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
	
		// TODO: Create a new User object using the retrieved parameters.
		User user = new User(username, password,  email,  phone);
		// The User constructor should take username, email, phone, and password as arguments.
		
	
		// TODO: Convert the new User object to a JSON string.
		
		// Hint: Use the Gson library to convert the User object to a JSON string.
		   // Create a Gson object
        Gson gson = new Gson();
        String jsonString = gson.toJson(user);
		JSONArray users = readUsers(servletContext); // Ensure this method is correctly implemented
	
		// TODO: Add the new user's JSON object to the 'users' JSONArray.
		JSONObject jsonObject = new JSONObject(jsonString);
		users.put(jsonObject); 
		try (FileWriter file = new FileWriter(realPathToUsersFile)) {
            file.write(users.toString(4)); // 4 is the indent-factor for better readability
        } catch (IOException e) {
            System.err.println("Error writing the users file: " + e.getMessage());
            throw new IOException("Error writing to users file.", e);
        }

        // Verification: Print the updated 'users.json' content
        try (Scanner scanner = new Scanner(new File(realPathToUsersFile))) {
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
            throw new FileNotFoundException("The user file does not exist.");
        }

        // Redirect to the login page after registration
        response.sendRedirect("login.html");
        
	}
	
	private JSONArray readUsers(ServletContext servletContext) throws IOException, JSONException {
		// Log the path retrieval
	    System.out.println("Inside readUsers");
	    String fullPath = servletContext.getRealPath("/users.json");
	    System.out.println(fullPath);

	    // Create a File object with 'fullPath' and check if it exists
	    File file = new File(fullPath);
	    if (!file.exists()) {
	        // Throw an IOException if the file does not exist
	        throw new IOException("The file 'users.json' does not exist at the path: " + fullPath);
	    }

	    try {
	        // Read the content of 'users.json' into a String
	        String content = new String(Files.readAllBytes(Paths.get(fullPath)));

	        // Convert the String content to a JSONArray and return it
	        return new JSONArray(content);
	    } catch (JSONException e) {
	        // Log JSON parsing error
	        System.err.println("Error parsing JSON from 'users.json': " + e.getMessage());
	        throw e;  // Re-throw the exception if needed
	    }
	    
	}
	

}