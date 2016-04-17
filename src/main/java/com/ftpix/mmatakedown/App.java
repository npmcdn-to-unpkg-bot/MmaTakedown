package com.ftpix.mmatakedown;

import static spark.Spark.*;

import java.io.File;
import java.io.FileInputStream;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.quartz.DateBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ftpix.mmatakedown.db.DB;
import com.ftpix.mmatakedown.endpoints.DataRefresh;
import com.ftpix.mmatakedown.endpoints.EventREST;
import com.ftpix.mmatakedown.endpoints.FightREST;
import com.ftpix.mmatakedown.endpoints.FighterREST;

import com.ftpix.mmatakedown.endpoints.OrganizationREST;
import com.ftpix.mmatakedown.jobs.RefreshDatabase;
import com.ftpix.mmatakedown.models.Organization;

import spark.ModelAndView;
import spark.template.jade.JadeTemplateEngine;

/**
 * Hello world!
 *
 */
public class App {

    private final static Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        try {
            staticFileLocation("/web");

            //MVCEndpoints.defineEndpoints();

            port(Constants.PORT);

            get("/", (req, res) -> {
                Map<String, Object> model = new HashMap<String, Object>();

                return new ModelAndView(model, "index");
            }, new JadeTemplateEngine());

            
            //End points
            OrganizationREST.defineEndpoints();
            EventREST.defineEndpoints();
            FightREST.defineEndpoints();
            FighterREST.defineEndpoints();

            DataRefresh.defineEndPoints();
            
            // serving file from cache
            get("/cache/*", (request, response) -> {
                File file = new File(Constants.CACHE_FOLDER + request.splat()[0]);
                logger.info("Looking for file [{}]", file.getAbsolutePath());

                if (file.exists()) {
                    response.raw().setContentType("application/octet-stream");
                    response.raw().setHeader("Content-Disposition", "attachment; filename=" + file.getName());

                    FileInputStream in = new FileInputStream(file);

                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = in.read(buffer)) > 0) {
                        response.raw().getOutputStream().write(buffer, 0, len);
                    }


                    in.close();
                    return response.raw();
                } else {
                    response.status(404);
                    return "";
                }

            });

            // Setting content type to JSON for api calls
            after("/api/*", (request, response) -> {
                response.header("Content-Encoding", "gzip");
                response.type("application/json");
            });

            prepareData();
            prepareJobs();
        } catch (Exception e) {
            logger.error("Error while initializing the application, exiting...", e);
            System.exit(0);
        }
    }

    /**
     * Preset data for famous MMA Organizations
     * 
     * @throws SQLException
     */
    private static void prepareData() throws SQLException {
        Organization ufc = DB.organizationDao.queryForId(1);
        if (ufc == null) {
            ufc = new Organization();
            ufc.setFollowing(false);
            ufc.setGetPrelims(false);
            ufc.setId(1);
        }
        ufc.setName("UFC");
        ufc.setShortName("ufc");
        ufc.setToRemoveFromEventName("UFC");
        ufc.setSherdogUrl("http://www.sherdog.com/organizations/Ultimate-Fighting-Championship-2");
        ufc.setPicture("ufc.jpg");
        // ufc.setSearchInterceptor(UfcInterceptor.class.getCanonicalName());

        DB.organizationDao.createOrUpdate(ufc);

        Organization invicta = DB.organizationDao.queryForId(2);
        if (invicta == null) {
            invicta = new Organization();
            invicta.setFollowing(false);
            invicta.setGetPrelims(false);
            invicta.setId(2);
        }
        invicta.setName("Invicta FC");
        invicta.setShortName("invicta");
        invicta.setToRemoveFromEventName("invicta,fc");
        invicta.setSherdogUrl("http://www.sherdog.com/organizations/Invicta-Fighting-Championships-4469");
        invicta.setSearchInterceptor("");
        invicta.setPicture("invicta.jpg");
        DB.organizationDao.createOrUpdate(invicta);
    }

    /**
     * Create the scheduling jobs for refreshing the data and fetching events
     * 
     * @throws SchedulerException
     */
    private static void prepareJobs() throws SchedulerException {
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler scheduler = sf.getScheduler();

        Date runTime = DateBuilder.evenMinuteDate(new Date());

        JobDetail job = JobBuilder.newJob(RefreshDatabase.class).withIdentity("RefreshDB", "MMA").build();

        Trigger trigger =
                          TriggerBuilder.newTrigger().withIdentity("RefreshDB", "MMA")
                                        .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(6).repeatForever())
                                        .build();

        scheduler.scheduleJob(job, trigger);
        logger.info(job.getKey() + " will run at: " + runTime);

        scheduler.start();
    }

}
