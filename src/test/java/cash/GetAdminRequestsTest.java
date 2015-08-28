package cash;

import static org.easymock.EasyMock.*;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import spark.Request;

import com.petty_requests.SqlModel.SqlModel;
import com.petty_requests.handlers.GetAdminRequests;

public class GetAdminRequestsTest {
	Request request = null;
	SqlModel model;
	

	@Before
	public void setup() {
		// setting up request
		request = createNiceMock(Request.class);
		expect(request.params("user_id")).andReturn("2");
		replay(request);
		// setting up model
		model = new SqlModel();
	}

	@After
	public void tearDown() {
		request = null;
		model = null;
	}

	@Test
	public void processRequest() {
		String data = null;
		String adminUserId = request.params("user_id");
		data = GetAdminRequests.dataToJson(model.getAdminUserRequests(adminUserId));
		System.out.println(data);
		assertThat("Should return user requests for a specif orgnisation to an admin",data,notNullValue());
	}

}
