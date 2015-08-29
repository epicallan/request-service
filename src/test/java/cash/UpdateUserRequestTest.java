package cash;
import static org.easymock.EasyMock.*;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.petty_requests.SqlModel.SqlModel;
import com.petty_requests.handlers.ProcessRequestPayload;
import com.petty_requests.handlers.UpdateUserRequest;
import com.petty_requests.models.ProcessedRequest;

public class UpdateUserRequestTest {
	
	SqlModel model;
	ProcessRequestPayload payload;
	
	@Before public void setUp(){
		// setting up model
		model = new SqlModel();
		payload = createNiceMock(ProcessRequestPayload.class);
		expect(payload.getStatus()).andReturn(1).anyTimes();
		expect(payload.getRequestId()).andReturn("3").anyTimes();
		expect(payload.getAdminId()).andReturn("1").anyTimes();;
		replay(payload);
	}
	
	@After public void tearDown(){
		deleteTestDbEntry();
		model = null;
		payload = null;
		
	}
	public void deleteTestDbEntry(){
		String sql = "DELETE FROM admin_req_fk WHERE admin_id = '1' "
				+ "AND request_id = '3' AND user_id = '3'";
		model.deleteRecord(sql);
	}
	
	
	@Test public void processRequest(){
		ProcessedRequest processedRequest = model.editRequest(payload);
		String json = UpdateUserRequest.dataToJson(processedRequest);
		assertThat("should update request obj and have side "
				+ "effects and return new processed request",json,notNullValue());
		
	}
	
}

