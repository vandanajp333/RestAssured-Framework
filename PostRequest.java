import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.junit.Before;
import org.junit.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class PostRequest {
	
	Properties property = new Properties();

	@Before
	public void read() throws IOException {
		FileInputStream fis = new FileInputStream("//home//ranjith//Documents//workspace2//JIRA.Test//src//test//resources//testData.properties");
		property.load(fis);
	}
	
	@Test
	public void getIt() {
		RestAssured.baseURI = property.getProperty("baseURI1");
		
		given().
		contentType("application/json").
		queryParam("https", "false").
		when().
		get("/entries").
		then().
		assertThat().
		statusCode(200).and().body("count", equalTo(99)).
		extract();
		System.out.println("success");
	}
	
	@Test
	public void getIt2() {
		RestAssured.baseURI = property.getProperty("baseURI2");
		
		given().
		contentType("application/json").
		queryParam("id", 1).
		when().
		get("/1").
		then().
		assertThat().
		statusCode(200).and().body("id", equalTo(1)).
		extract();
		System.out.println("success");
	}
	
	@Test
	public void createIt() {
		RestAssured.baseURI = property.getProperty("baseURI2");
		
		Response response1 = given().
		queryParam("id", 1).
		body(Payload.payloadContent()).			   
		when().post(URLPaths.requestPath()).
		then().
		statusCode(201).and().header("server", "cloudflare").extract().response();
		String resFormat=response1.asString();
		System.out.println(resFormat);
		JsonPath js = new JsonPath(resFormat);
		System.out.println(js.get("title"));
		String id=js.get("id");
		//when().delete("/url/"+id)
		System.out.println("success");
		
	}
	
}
