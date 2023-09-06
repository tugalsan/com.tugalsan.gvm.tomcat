package com.tugalsan.gvm.web;

//WHEN RUNNING IN NETBEANS, ALL DEPENDENCIES SHOULD HAVE TARGET FOLDER!
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import org.apache.catalina.startup.Tomcat;

public class Main {

    public static void main(String... args) throws Exception {
        File baseFolder = new File(System.getProperty("user.dir"));
        File appsFolder = new File(baseFolder, "web-apps");

        var tomcat = new Tomcat();
        tomcat.setBaseDir(baseFolder.getAbsolutePath());
        tomcat.setPort(8080);
        tomcat.getHost().setAppBase(appsFolder.getAbsolutePath());

        // Call the connector to create the default connector.
        tomcat.getConnector();

        tomcat.addWebapp("", appsFolder.getAbsolutePath());
        var wrapper = tomcat.addServlet("", "hello", new HelloServlet());
        wrapper.setLoadOnStartup(1);
        wrapper.addMapping("/*");

        tomcat.start();
        tomcat.getServer().await();
    }

    private static class HelloServlet extends HttpServlet {

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            resp.setStatus(200);

            try (var w = resp.getWriter()) {
                w.write("Hello from Tomcat native image!");
                w.flush();
            }
        }
    }
}
