package com.ftpix.mmatakedown.endpoints;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.options;
import static spark.Spark.post;
import static spark.Spark.put;

import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ftpix.mmatakedown.controllers.EventController;
import com.ftpix.mmatakedown.models.Event;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class EventREST {
private final static Logger logger = LoggerFactory.getLogger(OrganizationREST.class);

	
	public static void defineEndpoints(){
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		EventController controller = new EventController();

		///////////////////////////////
		/// ORGANIZATION API
		/////////////////////////////
		options("/api/event", (req, res)->{
			res.header("Allow", "GET,POST,PUT,DELETE,OPTIONS");
			return "";
		});
		
		get("/api/event", (req, res) -> {
			return controller.getAll();
		}, gson::toJson);

		get("/api/event/:id", (req, res) -> {
			return controller.get(Integer.parseInt(req.params(":id")));
		}, gson::toJson);

		
		get("/api/event/:id/fights", (req, res) -> {
			return controller.getFights(Integer.parseInt(req.params(":id")));
		}, gson::toJson);

		post("/api/event", (req, res) -> {
			try {
				Event org = gson.fromJson(req.body(), Event.class);
				return controller.insert(org);
			} catch (Exception e) {
				res.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
				logger.error("Error while creating organization", e);
				return "Error while creating organization";
			}
		}, gson::toJson);

		put("/api/event", (req, res) -> {

			try {
				logger.info("Body: {}", req.body());
				Event org = gson.fromJson(req.body(), Event.class);
				return controller.update(org);
			} catch (Exception e) {
				res.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
				logger.error("Error while updating organization", e);
				return "Error while updating event";
			}

		}, gson::toJson);

		delete("/api/event/:id", (req, res) -> {
			return controller.delete(Integer.parseInt(req.params(":id")));
		}, gson::toJson);
	
	}
}
