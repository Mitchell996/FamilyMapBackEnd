package service;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.stream.JsonReader;
import model.*;
import response.*;
import dataAccess.*;
import request.*;
import java.util.*;
import java.io.*;
//import json.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

//import org.json.simple.*;


public class fillService{

    String[] fname;
    String[] lname;
    eventInfo[] location;
    String[] mnames;

    String user;
    int yearsBack = 0;
    int numPeople = 0;
    int numEvents = 0;
    public class eventSet{
        eventInfo[] data;
        public eventSet(eventInfo[] toUse)
        {
            data = toUse;
        }

    }

    public class eventInfo{
        public String city;
        public double latitude;
        public double longitude;
        public String country;
        public eventInfo(String count, String cit, int latitude, int longitude){
            country = count;
            city = cit;
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }

    public class jsonContainer{
        public String[] data;
        public jsonContainer(String[] name)
        {
            data = name;
        }
    }
    String[] eventTypes = new String[]{ "baptism", "graduation","voter registration"};

    public void generateRandomValues(){
        Gson a = new Gson();



       // try {
           // FileWriter out = new FileWriter("testWorkingDirectoryIsHere.txt");
           // out.append("something");
           // out.close();

            // Map<String, ArrayList<String>> first = a.fromJson(new FileReader("fnames.json"));
           // String str = FileUtils.readFileToString(new File("fnames.json"));
            try {

                String text = readFile("/Users/mitchelljohnson/AndroidStudioProjects/FamilyMapBackEnd/Server/jsons/fnames.json");
                //System.out.println(text);
                jsonContainer one = a.fromJson(text, jsonContainer.class);
                fname = one.data;
               // System.out.println(fname[0]);
                text = readFile("/Users/mitchelljohnson/AndroidStudioProjects/FamilyMapBackEnd/Server/jsons/snames.json");
                jsonContainer two = a.fromJson(text, jsonContainer.class);
                lname = two.data;

                text = readFile("/Users/mitchelljohnson/AndroidStudioProjects/FamilyMapBackEnd/Server/jsons/mnames.json");
                jsonContainer three = a.fromJson(text, jsonContainer.class);
                mnames = three.data;

                text = readFile("/Users/mitchelljohnson/AndroidStudioProjects/FamilyMapBackEnd/Server/jsons/locations.json");
                eventSet events = a.fromJson(text, eventSet.class);
                location = events.data;



            }
            catch(Exception e){
                System.out.println("error with scanner " + e.getMessage());
            }

            //JsonReader first = new JsonReader(new FileReader("fnames.json"));
            //JsonReader last = new JsonReader(new FileReader("snames.json"));
            //JsonReader middle = new JsonReader(new FileReader("mnames.json"));
           //JsonReader locations = new JsonReader(new FileReader("locations.json"));

            //location = a.fromJson(locations, eventInfo.class);
            //first.beginObject();
            //first.beginArray();
           /* while (first.hasNext())
            {
                System.out.println("in first name");
                fname.add(first.nextName());
            }*/
          /*  last.beginObject();
            while (last.hasNext()){
                lname.add(last.nextName());
            }
            middle.beginObject();
            while (middle.hasNext()){
                mnames.add(last.nextName());
            }*/
           /*while (locations.hasNext()){
                location.add(last.nextName());
            }*/
        //}
        //catch(Exception ex ){
        //    System.out.println("an error occured! " + ex.getMessage());
        //}


    }
    /**
     * boolean generatePerson
     * creates a person based off of randomly generated data
     * catches an exception if this person's information already exists
     * @param f the data sent here
     * @return true if successfully created a person
     */
    public response generatePerson(fillRequest f){
        generateRandomValues();
        String[] lines = f.getBody().split("\\r?\\n");
        String username = lines[0];
        int numGenerations = 4;
        if(lines.length > 1) {
             numGenerations = Integer.valueOf(lines[1]);
        }
        user = username;
        personDAO dao = new personDAO();
        dao.deleteDescendants(username);
        makeGenerations(numGenerations, null);
        response resp = new response("successfully added " + numPeople +" and " +numEvents +"to the database");
        numPeople=0;
        numEvents = 0;
        return resp;
        /*
        FIGURE OUT WHAT TO DO HERE AT THE LAB
        We need to make up random data and just plug it in
       and then start pumping out people and events
         */

    }
    public response generatePerson(String username, int numGenerations){
        numPeople = 0;
        numEvents = 0;
        if(numGenerations < 1)
        {
            numGenerations = 4;
        }
        generateRandomValues();
        user = username;
        personDAO dao = new personDAO();
        eventDAO eventdao = new eventDAO();
        eventdao.deleteEventsByDescendant(username);
        dao.deleteDescendants(username);
        userDAO userD = new userDAO();
        userModel m = userD.getUser(username);
        if(m == null){
            response resp = new response("you cannot fill on a user that does not exist");
            return resp;
        }
        String newPersonID = m.personID;
        ArrayList<String> parentIDs = generatePersonForUser(username, numGenerations);
        personUsersModel pu = new personUsersModel(m.getFirstName(), m.getLastName(), m.getGender());
        personModel p = new personModel(username, parentIDs.get(0), parentIDs.get(1), null, pu, newPersonID);
        personDAO persondao = new personDAO();
        persondao.performPostPerson(p);
        generateEventsForUser(p);
        //makeGenerations(numGenerations, null);
        response resp = new response("successfully added " + (numPeople+1) +" people and " + numEvents +" events to the database");
        numPeople=0;
        numEvents = 0;
        //System.out.println("leaving generate person!");
        return resp;

    }
    public ArrayList<String> generatePersonForUser(String username, int numGenerations){
        generateRandomValues();
        user = username;
        if(numGenerations < 1)
        {
            numGenerations = 4;
        }
        personDAO dao = new personDAO();
        eventDAO eventdao = new eventDAO();
        eventdao.deleteEventsByDescendant(username);
        dao.deleteDescendants(username);
        ArrayList<personModel> parents = makeGenerations(numGenerations, null);
        //response resp = new response("successfully added " + numPeople +" and " +numEvents +"to the database");
        ArrayList<String> parentID = new ArrayList<>();
        for(personModel p: parents)
        {
            parentID.add(p.getID());
            parentID.add(p.getSpouse());
        }

        //System.out.println("leaving generate person!");
        return parentID;

    }

    private ArrayList<personModel> makeGenerations(int numGenerations, ArrayList<personModel> fathers)
    {

        if(numGenerations == 0){
            return fathers;
        }
        Random randoGenerator = new Random();
        double gen = (double)numGenerations;
        double currentGen = Math.pow(2.0,gen);
        //System.out.println("first name list: " + fname.length + " last name size: " + lname.length);
        ArrayList<personModel> kids = new ArrayList<>();
        for(int i = 0; i < currentGen; i+=2){
            numPeople+=2;
            int firstIndex = randoGenerator.nextInt(fname.length);
            int lastIndex = randoGenerator.nextInt(lname.length);
            int midIndex = randoGenerator.nextInt(mnames.length);
            //String gender = new String();
            /*if(i%2 == 0)
                gender = "m";
            else{
                gender = "f";
            }*/
            String fullFirst = fname[firstIndex]+ " " + mnames[midIndex];
            String lastname = lname[lastIndex];
            personUsersModel proto = new personUsersModel(fullFirst, lastname, "m");

            personModel male = new personModel(user, null, null, null, proto);
            firstIndex = randoGenerator.nextInt(fname.length);
             lastIndex = randoGenerator.nextInt(lname.length);
             midIndex = randoGenerator.nextInt(mnames.length);
            String otherFirst = fname[firstIndex] + " " + mnames[midIndex];
            String otherLast = lname[lastIndex];
            personUsersModel protof = new personUsersModel(otherFirst, otherLast, "f");

            personModel female = new personModel(user,null, null, male.getID(), protof);
            male.setSpouse(female.getID());
            generateEvents(male, female, numGenerations);
            if(fathers != null)
            {
                personModel maternal = fathers.get(i);
                personModel paternal = fathers.get(i+1);
                female.setFather(maternal.getID());
                female.setMother(maternal.getSpouse());
                male.setFather(paternal.getID());
                male.setMother(paternal.getSpouse());
            }
            personDAO p = new personDAO();
            p.performPostPerson(male);
            p.performPostPerson(female);
            kids.add(male);
        }
            //System.out.println(numGenerations);
        return makeGenerations(numGenerations-1, kids);
    }
    public void generateEventsForUser(personModel userM){

        Random variance = new Random();
        eventDAO eventMaker = new eventDAO();

        int currentYear = 2018;

        yearsBack = currentYear - variance.nextInt(6);

            int doB = (yearsBack - variance.nextInt(20));
            String birthYear =  doB+"";
            eventInfo birthplace = location[variance.nextInt(location.length)];
            eventModel birth = new eventModel(user, userM.getID(), birthplace.latitude+"", birthplace.longitude+"", birthplace.country, birthplace.city,
                    "birth", birthYear);
            eventMaker.postEvent(birth);
            numEvents++;

    }

    protected void generateEvents(personModel male, personModel female, int numGenerations){

        ArrayList<personModel> couple = new ArrayList<>();
        couple.add(male);
        couple.add(female);
        Random variance = new Random();
        eventDAO eventMaker = new eventDAO();
        //double gen = (double)numGenerations;
        int currentYear = 2018;
        //if(yearsBack == 0) {
            yearsBack = currentYear - (numGenerations*40);
            for( personModel person : couple) {
                //System.out.println("genereating events..");

                int doB = (yearsBack - variance.nextInt(20));
                String birthYear =  doB+"";
                eventInfo birthplace = location[variance.nextInt(location.length)];
                eventModel birth = new eventModel(user, person.getID(), birthplace.latitude+"", birthplace.longitude+"", birthplace.country, birthplace.city,
                        "birth", birthYear);
                eventMaker.postEvent(birth);
                numEvents++;

                // }
                int personNumEvents = variance.nextInt(2);

                for (int i = 0; i < personNumEvents; i++) {
                    //System.out.println("event being created");
                    int eventType = variance.nextInt(3);
                    String year = (variance.nextInt(40) + yearsBack) + "";
                    eventInfo toInput = location[variance.nextInt(location.length)];
                    eventModel m = new eventModel(user, person.getID(), toInput.latitude+"", toInput.longitude+"", toInput.country, toInput.city, eventTypes[eventType], year);
                    eventMaker.postEvent(m);
                    numEvents++;
                }

                if (doB < 1930+variance.nextInt(20))
                {
                    int dateofDeath = (yearsBack + 40 + variance.nextInt(20));
                    String deathYear = dateofDeath+"";
                    eventInfo deathPlace = location[variance.nextInt(location.length)];
                    eventModel death = new eventModel(user, person.getID(), deathPlace.latitude+"", deathPlace.longitude+"", deathPlace.country, deathPlace.city,
                            "death", deathYear);
                    numEvents++;
                    eventMaker.postEvent(death);
                }
            }

            int dateOfMarriage = yearsBack + variance.nextInt(40);
            String marriageYear = dateOfMarriage+"";
            eventInfo MarriagePlace = location[variance.nextInt(location.length)];
            eventModel marriageMale = new eventModel(user, male.getID(), MarriagePlace.latitude+"", MarriagePlace.longitude+"", MarriagePlace.country, MarriagePlace.city,
                "marriage", marriageYear);
        eventModel marriageFemale = new eventModel(user, female.getID(), MarriagePlace.latitude+"", MarriagePlace.longitude+"", MarriagePlace.country, MarriagePlace.city,
                "marriage", marriageYear);
            eventMaker.postEvent(marriageMale);
            eventMaker.postEvent(marriageFemale);
        numEvents++;
        numEvents++;



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







    /**
     * boolean fill
     * calls generatePerson multiple times to make a bunch of people.
     * @param numGenerations is the number of generations we generate
     * @return true if successful
     */


}



