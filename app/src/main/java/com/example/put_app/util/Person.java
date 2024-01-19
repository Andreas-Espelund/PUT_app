package com.example.put_app.util;


import org.json.JSONException;
import org.json.JSONObject;

public class Person {

    String id;
    String firstName;
    String lastName;
    String passClear;
    String passHash;
    String email;
    String livingCity;
    String yearOfBirth;

    public Person() {
        id = "na";
        firstName = "na";
        lastName = "na";
        passClear = "na";
        passHash = "na";
        email = "na";
        livingCity = "na";
        yearOfBirth = "na";
    }

    public String getId(){return this.id;}

    public String getFirstName(){return this.firstName;}

    public String getLastName(){return this.lastName;}

    public String getPassClear(){return this.passClear;}

    public String getPassHash(){return this.passHash;}

    public String getEmail(){return this.email;}

    public String getLivingCity(){return this.livingCity;}

    public String getYearOfBirth(){return this.yearOfBirth;}



    public static Person parsePerson(JSONObject response) throws JSONException {
        Person person = new Person();

        person.id = response.optString("id", "na");
        person.firstName = response.optString("firstName", "na");
        person.lastName = response.optString("lastName", "na");
        person.passClear = response.optString("passClear", "na");
        person.passHash = response.optString("passHash", "na");
        person.email = response.optString("email", "na");
        person.livingCity = response.optString("livingCity", "na");
        person.yearOfBirth = response.optString("yearOfBirth", "na");

        return person;
    }

}


