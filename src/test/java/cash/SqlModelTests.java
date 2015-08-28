package cash;

import java.util.Date;
import java.util.List;

import org.easymock.EasyMock;

import static org.easymock.EasyMock.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.sql2o.Connection;

import com.petty_requests.SqlModel.SqlModel;
import com.petty_requests.handlers.ProcessRequestPayload;
import com.petty_requests.main.RandomUUID;
import com.petty_requests.models.ProcessedRequest;
import com.petty_requests.models.UserRequest;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class SqlModelTests {
	private SqlModel sqlModel;
	private String requestId = RandomUUID.GenerateUUID();
	ProcessRequestPayload processRequestPayload;
	List<UserRequest> userRequests = null;
	UserRequest userRequest = null;
	ProcessedRequest processedRequest = null;

	@Before
	public void setUp() {
		sqlModel = new SqlModel();
		// TODO insert data into database to work with these suits
	}

	@After
	public void tearDown() {
		String sql = "Delete from request where request_id = :request_id ";
		try (Connection con = sqlModel.getSql2o().open()) {
			con.createQuery(sql).addParameter("request_id", requestId)
					.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		userRequests = null;
		userRequest = null;
		
	}
	
	@Test
	public void editRequest() {
		boolean isEdited = false;
		
		String edit_sql = "UPDATE request SET status = :status,"
				+ "approval_count = approval_count + 1 WHERE request_id = :request_id ";
		
		String trail_sql = "INSERT INTO admin_req_fk (admin_id, request_id, user_id) "
				+ "VALUES (:admin_id,:request_id,:user_id)";
		
		String processed_request = "SELECT request.*,organizations.approval_count AS max_approvals "
				+ "FROM request INNER JOIN organizations ON organizations.organization_id = request.organization_id "
				+ "WHERE request.request_id = :request_id";

		try (Connection con = sqlModel.getSql2o().beginTransaction()) {
			con.createQuery(edit_sql).addParameter("request_id", "1")
					.addParameter("status", 1).executeUpdate();

			processedRequest = con.createQuery(processed_request)
					.addParameter("request_id", "1")
					.executeAndFetchFirst(ProcessedRequest.class);

			con.createQuery(trail_sql).addParameter("admin_id", "1")
					.addParameter("request_id", 1)
					.addParameter("user_id", processedRequest.getUserId())
					.executeUpdate();

			con.commit();
			isEdited = true;
		}
		// Todo create better matcher
		assertThat("should make an edit on a request and have side effects",
				isEdited, is(true));

	}

	@Test
	public void getAllUserRequests() {

		String sql = "SELECT * FROM petty_cash.request where user_id = :user_id";
		try (Connection con = sqlModel.getSql2o().open()) {
			userRequests = con.createQuery(sql).addParameter("user_id", "1")
					.executeAndFetch(UserRequest.class);
			userRequest = userRequests.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertThat("should return a users request", userRequest.getReason(),
				notNullValue());
	}

	@Test
	public void getAdminUserRequests() {
		List<UserRequest> userRequests = null;
		UserRequest userRequest = null;
		String sql = "SELECT request.* FROM request INNER JOIN users ON "
				+ "request.`organization_id` = users.`organization_id` "
				+ "WHERE users.`user_id` = :user_id";
		try (Connection con = sqlModel.getSql2o().open()) {
			userRequests = con.createQuery(sql).addParameter("user_id", "2")
					.executeAndFetch(UserRequest.class);
			userRequest = userRequests.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertThat(
				"should return requests attached to an organisation for an admin ",
				userRequest.getReason(), notNullValue());
	}

	@Test
	public void createRequest() {
		boolean isCreated = false;

		String sql = "INSERT INTO request (user_id,request_id,mod_date,request_amount,"
				+ "approval_count,status,reason,organization_id)"
				+ "VALUES (:user_id,:request_id,:mod_date,"
				+ ":request_amount,:approval_count,:status,:reason,:organization_id)";
		try (Connection con = sqlModel.getSql2o().open()) {
			con.createQuery(sql).addParameter("user_id", "3")
					.addParameter("request_id", requestId)
					.addParameter("mod_date", new Date())
					.addParameter("request_amount", 30000)
					.addParameter("approval_count", 1)
					.addParameter("status", 0).addParameter("reason", "travel")
					.addParameter("organization_id", "3").executeUpdate();

			isCreated = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertThat("should create new User", isCreated, is(true));
	}
}
