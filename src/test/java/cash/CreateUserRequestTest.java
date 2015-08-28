package cash;

import static org.easymock.EasyMock.*;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import spark.Request;

import com.petty_requests.SqlModel.SqlModel;

public class CreateUserRequestTest {
	Request request = null;
	SqlModel model;

	@Before
	public void setUp() {
		// setting up request
		request = createNiceMock(Request.class);
		expect(request.params("user_id")).andReturn("1");
		replay(request);
		// setting up model
		model = new SqlModel();

	}

	@After
	public void tearDown() {

	}
	
	@Test
	public void processRequest() {

	}
}
