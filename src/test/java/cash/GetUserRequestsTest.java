package cash;

import static org.easymock.EasyMock.*;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.petty_requests.SqlModel.SqlModel;
import com.petty_requests.handlers.GetUserRequests;

import spark.Request;

public class GetUserRequestsTest {
	Request request = null;
	SqlModel model;

	@Before
	public void setup() {
		//setting up request
		request = createNiceMock(Request.class);
		expect(request.params("user_id")).andReturn("1");
		replay(request);
		//setting up model
		model = new SqlModel();
	}

	@After
	public void TearDown() {
		request = null;
		model = null;
		}

	@Test
	public void processRequest() {
		String data = null;
		String userId = request.params("user_id");
		data = GetUserRequests.dataToJson(model.getAllUserRequests(userId));
		System.out.println(data);
		assertThat("should return Json data of requests",data,notNullValue());
	}

}
