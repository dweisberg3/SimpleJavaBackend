import java.io.*;
import java.util.ArrayList;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class BackendAPI {

	private static ArrayList<User> users;

	public static void main(String[] args) throws IOException {

		HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
		HttpContext context = server.createContext("/userapp/users");
		HttpContext context2 = server.createContext("/userapp/users/");
		OutputStream output = null;
		JSONParser parse = new JSONParser();
		context.setHandler((he) -> {
			
			
			if (he.getRequestMethod().equals("GET")) {
				String payload = getAllUsers();
				output.write(payload.getBytes());
			}
			if (he.getRequestMethod().equals("POST")) {
				StringBuilder sb = new StringBuilder();
				InputStream ios = he.getRequestBody();
				int i;
				while ((i = ios.read()) != -1) {
					sb.append((char) i);
				}
				JSONObject user = new JSONObject(sb.toString());
				String payload = createUser(user);
				output.write(payload.getBytes());
				
			}
			
			output.flush();
			he.close();
		});

		context2.setHandler((he) -> {

			if (he.getRequestMethod().equals("GET")) {
				StringBuilder sb = new StringBuilder();
				InputStream ios = he.getRequestBody();
				int i;
				while ((i = ios.read()) != -1) {
					sb.append((char) i);
				}
				JSONObject user = new JSONObject(sb.toString());
				String payload = getUser(user);
				output.write(payload.getBytes());
			}
			if (he.getRequestMethod().equals("PUT")) {
				StringBuilder sb = new StringBuilder();
				InputStream ios = he.getRequestBody();
				int i;
				while ((i = ios.read()) != -1) {
					sb.append((char) i);
				}
				JSONObject user = new JSONObject(sb.toString());
				String payload = updateUser(user);
				output.write(payload.getBytes());
			}
			if (he.getRequestMethod().equals("DELETE")) {
				StringBuilder sb = new StringBuilder();
				InputStream ios = he.getRequestBody();
				int i;
				while ((i = ios.read()) != -1) {
					sb.append((char) i);
				}
				JSONObject user = new JSONObject(sb.toString());
				String payload = deleteUser(user);
				output.write(payload.getBytes());
			}
			output.flush();
			he.close();
		});
		server.start();

	}

	public static String getAllUsers() {

		JSONArray jsonarr = new JSONArray();
		for (User usr : users) {
			JSONObject obj = new JSONObject();
			obj.put("Name", usr.getName());
			obj.put("Date of Birth", usr.getDateOfBirth());
			obj.put("Address", usr.getAddress());
			jsonarr.add(obj);
		}
		JSONObject allUsers = new JSONObject();
		allUsers.put("All Users", jsonarr);

		return allUsers.toString();

	}

	public static String createUser(JSONObject user) {

		JSONObject response = new JSONObject();
		String name = user.get("Name").toString();
		String dataOfBirth = user.get("Date of Birth").toString();
		String address = user.get("Address").toString();
		if (name.length() == 0 || dataOfBirth.length() == 0 || address.length() == 0) { // check for missing fields
			response.put("response:", "missing fields; user not created");
			return response.toString();
		}
		User usr = new User(name, dataOfBirth, address);
		users.add(usr);
		response.put("response:", "New user added");
		return response.toString();
	}

	public static String getUser(JSONObject user) {

		String name = user.get("Name").toString();
		JSONObject response = new JSONObject();

		for (User usr : users) {
			if (usr.getName().equals(name)) {
			
				response.put("Name:", usr.getName());
				response.put("Date of Birth:", usr.getDateOfBirth());
				response.put("Address:", usr.getAddress());
				return response.toString();
			}
		}

		response.put("response:", name + " is not in our records");
		return response.toString();
	}

	public static String updateUser(JSONObject obj) {

		String name = obj.get("Name").toString();
		JSONObject response = new JSONObject();

		for (User usr : users) {
			if (usr.getName().equals(name)) {
				String address = obj.get("Address").toString();
				if(address.length() == 0) {
					obj.put("response:", "address for " + name +" not provided");
					return response.toString();
				}
				usr.setAddress(obj.get("Address").toString());
				obj.put("response:", name + "'s  address was updated");
				return response.toString();

			}
		}

		obj.put("response:", name + " is not in our records");
		return response.toString();
	}

	public static String deleteUser(JSONObject obj) {

		String name = obj.get("Name").toString();
		JSONObject response = new JSONObject();
		for (User usr : users) {
			if (usr.getName().equals(name)) {
				users.remove(usr);
				response.put("response:", name + " was removed");
				return response.toString();
			}
		}
		response.put("response:", name + " is not in our system");
		return response.toString();

	}

}
