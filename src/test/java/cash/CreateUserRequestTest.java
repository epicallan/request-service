package cash;

import static org.easymock.EasyMock.*;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.petty_requests.SqlModel.SqlModel;
import com.petty_requests.handlers.CreateUserRequest;
import com.petty_requests.handlers.NewUserRequestPayload;

public class CreateUserRequestTest {

	SqlModel model;
	NewUserRequestPayload payload;

	@Before
	public void setUp() {
		// setting up request
		payload = createNiceMock(NewUserRequestPayload.class);
		expect(payload.getUserId()).andReturn("2");
		expect(payload.isValid()).andReturn(true);
		expect(payload.getOrganisationId()).andReturn("3");
		expect(payload.getReason()).andReturn("travel to client");
		expect(payload.getRequestAmount()).andReturn(5000f);
		replay(payload);
		// setting up model
		model = new SqlModel();

	}

	@After
	public void tearDown() {
		payload = null;
		model = null;

	}

	@Test
	public void processRequest() {
		Object result = null;
		if (payload.isValid()) {
			result = CreateUserRequest.dataToJson(model
					.createUserRequest(payload));
		}
		assertThat("should create new request from payload", result,
				notNullValue());
	}
}
