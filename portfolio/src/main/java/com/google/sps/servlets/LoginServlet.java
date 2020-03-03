package com.google.sps.servlets;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.util.concurrent.TimeUnit;

@WebServlet("/login")
public class LoginServlet extends HttpServlet
{
    @Override
    public void doGet (HttpServletRequest request, HttpServletResponse response) throws IOException
    {   
        //Sets response type to html. We are writing directly to the page.
        response.setContentType("text/html");
        //Create UserService instance
        UserService userService = UserServiceFactory.getUserService();
        //if user is logged in we want to redirect back to index.html
        if(userService.isUserLoggedIn())
        {
            String userEmail = userService.getCurrentUser().getEmail();
            response.getWriter().println("<h1> You are already logged into " + userEmail+ "</h1>");
            String logoutUrl = userService.createLogoutURL("/index.html");
            response.getWriter().println("<p><a href=\"" + logoutUrl + "\">Logout</a>.</p>");
        }
        else //If user is not logged in prompt them to do so.
        {
            // To log someone in we must have a place to go to after login
            String afterLoginRedirect = "/index.html";
            //Now that we have somewhere to go to after login we can create loginURl
            String loginUrl = userService.createLoginURL(afterLoginRedirect);
            response.getWriter().println("<p>Login <a href=\"" + loginUrl + "\">here</a>.</p>");
        }
    }
}