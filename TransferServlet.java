package banking;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TransferServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve user and transaction details from request
        User sender = (User) request.getSession().getAttribute("user");
        String recipientAccNo = request.getParameter("recipientAccNo");
        double amount = Double.parseDouble(request.getParameter("amount"));
        User receiver = null;
        String realPathToUsersFile = getServletContext().getRealPath("/users.json");
        
    
        try {
            // Read the content of 'users.json' into a String
            String content = new String(Files.readAllBytes(Paths.get(realPathToUsersFile)));
            JSONArray users = new JSONArray(content);
            
            JSONObject senderJson = null;
            JSONObject receiverJson = null;
            
            // Iterate through users JSONArray to find the sender and receiver JSONObjects
            for (int i = 0; i < users.length(); i++) {
                JSONObject user = users.getJSONObject(i);
                if (user.getString("accNo").equals(sender.getAccountNumber())) {
                    senderJson = user;
                } else if (user.getString("accNo").equals(recipientAccNo)) {
                    receiverJson = user;
                }
            }
            
            if (senderJson != null && receiverJson != null) {
                // Create a User instance for the receiver using receiverJson
                receiver = new User(receiverJson.getString("username"), receiverJson.getString("password"),
                                    receiverJson.getString("email"), receiverJson.getString("phone"));
                receiver.setAccountNumber(receiverJson.getString("accNo"));
                receiver.setBalance(receiverJson.getDouble("balance"));
                
                // Perform transfer operation
                performTransferOperation(senderJson, receiverJson, amount, sender, receiver);
                
                // Optionally, update the application context or session state
                getServletContext().setAttribute("users", users);  // Store the updated users in the application context
                
                // Update the session with the modified sender data
                request.getSession().setAttribute("user", sender);
                
                // Redirect to a success page on successful transfer
                response.sendRedirect("success.jsp");
            } else {
                // Redirect to a failure page if sender or receiver is not found
                response.sendRedirect("failure.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("failure.jsp");
        }
    }
    
    private synchronized void performTransferOperation(JSONObject senderJson, JSONObject receiverJson, double amount, User sender, User receiver) throws Exception {
        // Check sender's balance
        double senderBalance = senderJson.getDouble("balance");
        if (senderBalance < amount) {
            throw new Exception("Insufficient funds for transfer");
        }

        // Update balances in senderJson and receiverJson
        senderJson.put("balance", senderBalance - amount);
        double receiverBalance = receiverJson.getDouble("balance");
        receiverJson.put("balance", receiverBalance + amount);

        // Add a transaction record for both sender and receiver using the addTransaction method
        addTransaction(senderJson, amount, "withdraw");
        addTransaction(receiverJson, amount, "deposit");

        // Update User objects, if your application requires it
        sender.setBalance(senderBalance - amount);
        receiver.setBalance(receiverBalance + amount);
    }
    
    private void addTransaction(JSONObject userJson, double amount, String type) throws JSONException {
        // TODO: Retrieve the user's transaction array from userJson.
        // Create a new transaction JSONObject with necessary details.
        // Add the new transaction to the user's transaction array.
    	// Retrieve the user's transaction array from userJson
        JSONArray transactions = userJson.optJSONArray("transactions");
        if (transactions == null) {
            // If there is no transaction array, create a new one
            transactions = new JSONArray();
            userJson.put("transactions", transactions);
        }

        // Create a new transaction JSONObject with necessary details
        JSONObject newTransaction = new JSONObject();
        newTransaction.put("transactionId", generateTransactionId());
        newTransaction.put("date", new Date().toString()); // Using current date. Format as needed.
        newTransaction.put("amount", amount);
        newTransaction.put("type", type);


        // Add the new transaction to the user's transaction array
        transactions.put(newTransaction);
    }
    
    private String generateTransactionId() {
        // Generate a unique transaction ID (students should implement their own logic)
        return "T" + new Random().nextInt(100000);
    }
    

}
