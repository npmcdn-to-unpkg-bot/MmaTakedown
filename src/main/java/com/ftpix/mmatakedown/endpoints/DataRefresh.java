package com.ftpix.mmatakedown.endpoints;

import static spark.Spark.get;

import com.ftpix.mmatakedown.controllers.EventController;
import com.ftpix.mmatakedown.db.DB;
import com.ftpix.mmatakedown.jobs.subjobs.EventJobs;
import com.ftpix.mmatakedown.models.Event;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * This class will trigger the refresh on different part of the model by checking grabbing the last data from sherdog
 * @author gz
 *
 */
public class DataRefresh {

	
	public static void defineEndPoints() {
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		EventController eventController = new EventController();
		EventJobs eventJobs = new EventJobs();
		
		get("/refresh/event/:id", (req, res)->{
			Event event = eventController.get(Integer.parseInt(req.params(":id")));
			
			return eventJobs.refreshEvent(event);
			
		}, gson::toJson);
	}
}
