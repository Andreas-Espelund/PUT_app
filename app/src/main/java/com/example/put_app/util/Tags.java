package com.example.put_app.util;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Tags {

    // also defined in file server.java
    static final int TAGS_ITEMS_ARRAY_SIZE = 5;

    String id;
    String numberOfTags;
    String tagId[];
    String tagDes[];
    String tagPhoto[];
    String tagLocation[];
    String tagPeopleName[];


    public Tags() {
        tagId = new String[TAGS_ITEMS_ARRAY_SIZE];
        tagDes = new String[TAGS_ITEMS_ARRAY_SIZE];
        tagPhoto = new String[TAGS_ITEMS_ARRAY_SIZE];
        tagLocation = new String[TAGS_ITEMS_ARRAY_SIZE];
        tagPeopleName = new String[TAGS_ITEMS_ARRAY_SIZE];
        for (int i=0; i < TAGS_ITEMS_ARRAY_SIZE; i++) {
            tagId[i] = "na";
            tagDes[i] = "na";
            tagPhoto[i] = "na";
            tagLocation[i] = "na";
            tagPeopleName[i] = "na";
        }
    }

    public String getId(){return this.id;}

    public String getNumberOfTags(){return this.numberOfTags;}

    public String getTagId(int index){return this.tagId[index];}

    public String getTagDes(int index){return this.tagDes[index];}

    public String getTagPhoto(int index){return this.tagPhoto[index];}

    public String getTagLocation(int index){return this.tagLocation[index];}

    public String getTagPeopleName(int index){return this.tagPeopleName[index];}


    public void setId(String i){this.id = i;}

    public void setNumberOfTags(String nOt){this.numberOfTags = nOt;}

    public void setTagId(int index, String i){this.tagId[index] = i;}

    public void setTagDes(int index, String des){this.tagDes[index] = des;}

    public void setTagPhoto(int index, String pho){this.tagPhoto[index] = pho;}

    public void setTagLocation(int index, String loc){this.tagLocation[index] = loc;}

    public void setTagPeopleName(int index, String sta){this.tagPeopleName[index] = sta;}


    /**
     * Parses a JSON string to create a Tags object.
     *
     * @param jsonObject The JSON string to parse.
     * @return A Tags object populated with data from the JSON string.
     */
    public static Tags parseFromJson(JSONObject jsonObject) throws JSONException {
        Tags tags = new Tags();

        tags.id = jsonObject.getString("id");
        tags.numberOfTags = jsonObject.getString("numberOfTags");

        tags.tagId = jsonArrayToStringArray(jsonObject.getJSONArray("tagId"));
        tags.tagDes = jsonArrayToStringArray(jsonObject.getJSONArray("tagDes"));
        tags.tagPhoto = jsonArrayToStringArray(jsonObject.getJSONArray("tagPhoto"));
        tags.tagLocation = jsonArrayToStringArray(jsonObject.getJSONArray("tagLocation"));
        tags.tagPeopleName = jsonArrayToStringArray(jsonObject.getJSONArray("tagPeopleName"));

        return tags;
    }

    private static String[] jsonArrayToStringArray(JSONArray jsonArray) throws JSONException {
        String[] array = new String[TAGS_ITEMS_ARRAY_SIZE];
        for (int i = 0; i < TAGS_ITEMS_ARRAY_SIZE; i++) {
            array[i] = jsonArray.optString(i, "na");
        }
        return array;
    }


}

