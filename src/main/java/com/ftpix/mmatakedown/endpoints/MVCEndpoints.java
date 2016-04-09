package com.ftpix.mmatakedown.endpoints;

import static spark.Spark.get;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

import com.ftpix.mmatakedown.controllers.OrganizationController;
import com.ftpix.mmatakedown.models.Event;
import com.ftpix.mmatakedown.models.Organization;

import spark.ModelAndView;
import spark.template.jade.JadeTemplateEngine;

public class MVCEndpoints {

	public static void defineEndpoints() {
		
		get("/", (req, res) -> {
			Map<String, Object> model = new HashMap<String, Object>();

			// getting organizations
			model.put("orgs", new OrganizationController().getAll());

			return new ModelAndView(model, "index");
		}, new JadeTemplateEngine());
		
		
		get("/organization/:id", (req, res) -> {
			Map<String, Object> model = new HashMap<String, Object>();
			
			OrganizationController controller= new OrganizationController();
			
			Organization org = controller.get(Integer.parseInt(req.params(":id")));
			List<Event> events = controller.getEvents(org.getId());
			Collections.reverse(events);
			// getting organizations
			model.put("org", org);
			model.put("events", events);
			
			return new ModelAndView(model, "organization");
		}, new JadeTemplateEngine());
	}
}
