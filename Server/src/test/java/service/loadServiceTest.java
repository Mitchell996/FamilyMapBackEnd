package service;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.stream.JsonReader;
import model.*;
import response.*;
import dataAccess.*;
import request.*;

import java.lang.reflect.Array;
import java.util.*;
import java.io.*;
import service.*;
//import json.*;
import java.lang.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dataAccess.authDAO;
import dataAccess.eventDAO;
import dataAccess.personDAO;
import dataAccess.userDAO;

import static org.junit.Assert.*;

public class loadServiceTest {

    private personDAO person;
    private userDAO user;
    private authDAO auth;
    private eventDAO event;

    @Before
    public void setUp(){
        person = new personDAO();
        user = new userDAO();
        auth = new authDAO();
        event = new eventDAO();

    }

    @After
    public void tearDown(){
        clearService clearer = new clearService();
        clearer.clear();
    }

    @Test
    public void load() {
        //check empty body
        loadService loader = new loadService();
        Gson a = new Gson();
        //String empty = new String("");
        //loadRequest request = new loadRequest();

        try{
            String text = readFile("/Users/mitchelljohnson/AndroidStudioProjects/FamilyMapBackEnd/Server/jsons/example.json");
            loadRequest request = a.fromJson(text, loadRequest.class);
            loader.load(request);
            ArrayList<userModel> users =  user.getAllUsers();
            assertNotNull(users.get(0));

        } catch(Exception e){
            System.out.println("error occured!");
        }

    }

    @Test
    public void loadFail(){
        //check empty body
        loadService loader = new loadService();
        Gson a = new Gson();
        //String empty = new String("");
        //loadRequest request = new loadRequest();

        try{
            String text = readFile("/Users/mitchelljohnson/AndroidStudioProjects/FamilyMapBackEnd/Server/jsons/example2.json");
            loadRequest request = a.fromJson(text, loadRequest.class);
            loader.load(request);
            ArrayList<userModel> users =  user.getAllUsers();
            assertNotNull(users.get(0));

        } catch(Exception e){
            System.out.println("error occured! " + e.getMessage());
            assertTrue(true);
        }


    }
    private String readFile(String pathname) throws IOException {

        File file = new File(pathname);
        StringBuilder fileContents = new StringBuilder((int)file.length());

        try (Scanner scanner = new Scanner(file)) {
            while(scanner.hasNextLine()) {
                fileContents.append(scanner.nextLine() + System.lineSeparator());
            }
            return fileContents.toString();
        }
    }
}