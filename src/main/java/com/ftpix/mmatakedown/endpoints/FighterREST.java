package com.ftpix.mmatakedown.endpoints;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.options;
import static spark.Spark.post;
import static spark.Spark.put;

import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ftpix.mmatakedown.controllers.FighterController;
import com.ftpix.mmatakedown.models.Fighter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class FighterREST {
	private final static Logger logger = LoggerFactory.getLogger(FighterREST.class);

	public static void defineEndpoints() {
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		FighterController controller = new FighterController();

		///////////////////////////////
		/// ORGANIZATION API
		/////////////////////////////
		options("/api/fighter", (req, res) -> {
			res.header("Allow", "GET,POST,PUT,DELETE,OPTIONS");
			return "";
		});

		get("/api/fighter", (req, res) -> {
			return controller.getAll();
		}, gson::toJson);

		get("/api/fighter/:id", (req, res) -> {
			return controller.get(Integer.parseInt(req.params(":id")));
		}, gson::toJson);

		get("/api/fighter/:id/fights", (req, res) -> {
			return controller.getFights(Integer.parseInt(req.params(":id")));
		}, gson::toJson);
		
		get("/api/fighter/:id/refresh", (req, res) -> {
			return controller.refresh(Integer.parseInt(req.params(":id")));
		}, gson::toJson);

		
		get("/api/fighter/search/:query", (req, res) -> {
			return controller.search(req.params(":query"));
		}, gson::toJson);

		
		
		post("/api/fighter", (req, res) -> {
			try {
				Fighter org = gson.fromJson(req.body(), Fighter.class);
				return controller.insert(org);
			} catch (Exception e) {
				res.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
				logger.error("Error while creating organization", e);
				return "Error while creating organization";
			}
		}, gson::toJson);

		put("/api/fighter", (req, res) -> {

			try {
				Fighter org = gson.fromJson(req.body(), Fighter.class);
				return controller.update(org);
			} catch (Exception e) {
				res.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
				logger.error("Error while updating organization", e);
				return "Error while creating organization";
			}

		}, gson::toJson);

		delete("/api/fighter/:id", (req, res) -> {
			return controller.delete(Integer.parseInt(req.params(":id")));
		}, gson::toJson);

	}

}
