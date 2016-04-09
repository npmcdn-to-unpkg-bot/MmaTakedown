package com.ftpix.mmatakedown.endpoints;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.options;
import static spark.Spark.post;
import static spark.Spark.put;

import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ftpix.mmatakedown.controllers.FightController;
import com.ftpix.mmatakedown.models.Fight;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class FightREST {
	private final static Logger logger = LoggerFactory.getLogger(FightREST.class);

	public static void defineEndpoints(){
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		FightController controller = new FightController();

		///////////////////////////////
		/// ORGANIZATION API
		/////////////////////////////
		options("/api/fight", (req, res)->{
			res.header("Allow", "GET,POST,PUT,DELETE,OPTIONS");
			return "";
		});
		
		get("/api/fight", (req, res) -> {
			return controller.getAll();
		}, gson::toJson);

		get("/api/fight/:id", (req, res) -> {
			return controller.get(Integer.parseInt(req.params(":id")));
		}, gson::toJson);

		post("/api/fight", (req, res) -> {
			try {
				Fight org = gson.fromJson(req.body(), Fight.class);
				return controller.insert(org);
			} catch (Exception e) {
				res.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
				logger.error("Error while creating organization", e);
				return "Error while creating organization";
			}
		}, gson::toJson);

		put("/api/fight", (req, res) -> {

			try {
				Fight org = gson.fromJson(req.body(), Fight.class);
				return controller.update(org);
			} catch (Exception e) {
				res.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
				logger.error("Error while updating organization", e);
				return "Error while creating organization";
			}

		}, gson::toJson);

		delete("/api/fight/:id", (req, res) -> {
			return controller.delete(Integer.parseInt(req.params(":id")));
		}, gson::toJson);
	
	}
}
