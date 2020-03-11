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
    public class LoginState
        {
            private boolean login = false;
            private String loginUrl;
        }

    @Override
    public void doGet (HttpServletRequest request, HttpServletResponse response) throws IOException
    {   
        LoginState person = new LoginState();

        response.setContentType("application/json;");
        
        //Create UserService instance
        UserService userService = UserServiceFactory.getUserService();
        
        if(userService.isUserLoggedIn())
        {
            person.login = true;
        }
        else
        {
            String loginUrl = userService.createLoginURL("/index.html");
            person.loginUrl = loginUrl;
        }
        
        Gson loginGson = new Gson();
        String loginJson = loginGson.toJson(person);
        response.getWriter().println(loginJson);
    }
}