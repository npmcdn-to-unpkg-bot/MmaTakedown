package com.ftpix.mmatakedown.endpoints;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.options;
import static spark.Spark.post;
import static spark.Spark.put;

import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ftpix.mmatakedown.controllers.OrganizationController;
import com.ftpix.mmatakedown.models.Organization;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class OrganizationREST {
	private final static Logger logger = LoggerFactory.getLogger(OrganizationREST.class);

	public static void defineEndpoints() {
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		OrganizationController controller = new OrganizationController();

		///////////////////////////////
		/// ORGANIZATION API
		/////////////////////////////
		options("/api/organization", (req, res) -> {
			res.header("Allow", "GET,POST,PUT,DELETE,OPTIONS");
			return "";
		});

		get("/api/organization", (req, res) -> {
			return controller.getAll();
		}, gson::toJson);

		get("/api/organization/:id", (req, res) -> {
			return controller.get(Integer.parseInt(req.params(":id")));
		}, gson::toJson);
		
		
		get("/api/organization/:id/events", (req, res) -> {
			return controller.getEvents(Integer.parseInt(req.params(":id")));
		}, gson::toJson);
		
		

		post("/api/organization", (req, res) -> {
			try {
				Organization org = gson.fromJson(req.body(), Organization.class);
				return controller.insert(org);
			} catch (Exception e) {
				res.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
				logger.error("Error while creating organization", e);
				return "Error while creating organization";
			}
		}, gson::toJson);

		put("/api/organization", (req, res) -> {

			try {
				Organization org = gson.fromJson(req.body(), Organization.class);
				return controller.update(org);
			} catch (Exception e) {
				res.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
				logger.error("Error while updating organization", e);
				return "Error while creating organization";
			}

		}, gson::toJson);

		delete("/api/organization/:id", (req, res) -> {
			return controller.delete(Integer.parseInt(req.params(":id")));
		}, gson::toJson);


	}

}
