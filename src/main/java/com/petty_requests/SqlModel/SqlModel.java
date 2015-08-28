package com.petty_requests.SqlModel;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sql2o.Sql2o;
import org.sql2o.Connection;

import com.petty_requests.handlers.NewUserRequestPayload;
import com.petty_requests.handlers.ProcessRequestPayload;
import com.petty_requests.main.RandomUUID;
import com.petty_requests.models.ProcessedRequest;
import com.petty_requests.models.Model;
import com.petty_requests.models.UserRequest;

public class SqlModel implements Model {

	private Sql2o sql2o;

	List<UserRequest> userRequests = null;

	public SqlModel() {
		try {
			sql2o = new Sql2o(
					"jdbc:mysql://localhost/petty_cash?useUnicode=true&characterEncoding=UTF-8",
					"root", "root");
			columnMappings();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void columnMappings() {
		Map<String, String> colMaps = new HashMap<String, String>();
		colMaps.put("created_on", "createdOn");
		colMaps.put("approval_count", "approvalCount");
		colMaps.put("request_amount", "requestAmount");
		colMaps.put("mod_date", "ModDate");
		colMaps.put("user_id", "userId");
		colMaps.put("request_id", "requestId");
		colMaps.put("organization_id", "organisationId");
		colMaps.put("max_approvals", "maxApprovals");
		sql2o.setDefaultColumnMappings(colMaps);
	}

	public Sql2o getSql2o() {
		return sql2o;
	}

	public void setSql2o(Sql2o sql2o) {
		this.sql2o = sql2o;
	}

	@Override
	public ProcessedRequest editRequest(ProcessRequestPayload payload) {
		String edit_sql = "UPDATE request SET status = :status,"
				+ "approval_count = approval_count + 1 WHERE request_id = :request_id ";
		ProcessedRequest processedRequest = null;

		if (!existsAdminActionOnRequest(payload.getRequestId(),payload.getAdminId())) {
			try (Connection con = sql2o.beginTransaction()) {
				con.createQuery(edit_sql).addParameter("request_id",payload.getRequestId())
						.addParameter("status",payload.getStatus()).executeUpdate();

				processedRequest = processRequest(payload.getRequestId(), con);

				createAdminRequestTrail(processedRequest.getUserId(),payload.getRequestId(),
						payload.getAdminId(), con);
				con.commit();
			}
		}

		return processedRequest;
	}

	private ProcessedRequest processRequest(String requestId, Connection con) {
		ProcessedRequest request = null;
		String sql = "SELECT request.*,organizations.approval_count AS max_approvals "
				+ "FROM request INNER JOIN organizations ON organizations.organization_id = request.organization_id "
				+ "WHERE request.request_id = :request_id";
		try {
			request = con.createQuery(sql)
					.addParameter("request_id", requestId)
					.executeAndFetchFirst(ProcessedRequest.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return request;
	}

	public boolean createUserRequest(NewUserRequestPayload payload) {
		boolean isCreated = false;
		String sql = "INSERT INTO request (user_id,request_id,mod_date,request_amount,"
				+ "approval_count,status,reason,organization_id)"
				+ "VALUES (:user_id,:request_id,:mod_date,"
				+ ":request_amount,:approval_count,:status,:reason,:organization_id)";
		try (Connection con = sql2o.open()) {
			con.createQuery(sql)
					.addParameter("user_id", payload.getUserId())
					.addParameter("request_id", RandomUUID.GenerateUUID())
					.addParameter("mod_date", new Date())
					.addParameter("request_amount", payload.getRequestAmount())
					.addParameter("approval_count", 1)
					.addParameter("status", 0)
					.addParameter("reason", payload.getReason())
					.addParameter("organization_id",
							payload.getOrganisationId()).executeUpdate();
			isCreated = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isCreated;
	}

	public List<UserRequest> getAllUserRequests(String userId) {
		String sql = "SELECT * FROM petty_cash.request where user_id = :user_id";

		try (Connection con = sql2o.open()) {
			userRequests = con.createQuery(sql).addParameter("user_id", userId)
					.executeAndFetch(UserRequest.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userRequests;
	}

	public List<UserRequest> getAdminUserRequests(String admin) {
		String sql = "SELECT request.* FROM request INNER JOIN users ON "
				+ "request.`organization_id` = users.`organization_id` "
				+ "WHERE users.`user_id` = :user_id";
		try (Connection con = sql2o.open()) {
			userRequests = con.createQuery(sql).addParameter("user_id", "2")
					.executeAndFetch(UserRequest.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userRequests;
	}

	private boolean existsAdminActionOnRequest(String requestId, String adminId) {
		boolean exists = true;

		String sql = "SELECT COUNT(*) FROM admin_req_fk WHERE "
				+ "admin_req_fk.request_id =:requestId AND "
				+ "admin_req_fk.admin_id = :admin_id";
		try (Connection con = sql2o.open()) {
			Integer count = con.createQuery(sql)
					.addParameter("admin_id", adminId)
					.addParameter("request_id", requestId)
					.addParameter("admin_id", adminId)
					.executeAndFetchFirst(Integer.class);
			if (count == null) {
				exists = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return exists;
	}

	private void createAdminRequestTrail(String userId, String requestId,
			String adminId, Connection con) {
		String trail_sql = "INSERT INTO admin_req_fk (admin_id, request_id, user_id) "
				+ "VALUES (:admin_id,:request_id,:user_id)";
		try {
			con.createQuery(trail_sql).addParameter("admin_id", adminId)
					.addParameter("request_id", requestId)
					.addParameter("user_id", userId).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
