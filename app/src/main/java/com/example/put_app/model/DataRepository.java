package com.example.put_app.model;



import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.put_app.util.Person;
import com.example.put_app.util.Hash;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class DataRepository {

    private final String BASE_URL = "http://10.0.2.2:8080";
    private RequestQueue requestQueue;

    private Person currentUser;
    public DataRepository(Context context) {
        this.requestQueue = Volley.newRequestQueue(context);
    }


    /**
     * Sets the current user.
     *
     * @param person The Person object representing the current user.
     */
    public void setCurrentUser(Person person) { currentUser = person; }

    /**
     * Retrieves the current user.
     *
     * @return The current Person object.
     */
    public Person getCurrentUser() { return currentUser; }

    /**
     * Performs a login operation.
     *
     * @param email Email of the user trying to log in.
     * @param password Password of the user.
     * @param responseListener Listener for successful response.
     * @param errorListener Listener for error response.
     */
    public void logIn(String email, String password, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
        String ph = Hash.toMD5(password);
        String url = BASE_URL + "/methodPostRemoteLogin?em=" + Uri.encode(email) + "&ph=" + Uri.encode(ph);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, responseListener, errorListener);
        requestQueue.add(jsonObjectRequest);
    }

    /**
     * Changes the user's password.
     *
     * @param email Email of the user changing the password.
     * @param newPassword The new password.
     * @param responseListener Listener for successful response.
     * @param errorListener Listener for error response.
     */
    public void changePassword(String email, String newPassword, Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        String ph = Hash.toMD5(newPassword);
        String url = BASE_URL + "/methodPostChangePasswd?em=" + Uri.encode(email) + "&np=" + Uri.encode(newPassword) + "&ph=" + Uri.encode(ph);

        Log.d("PASSWORD", url);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, responseListener, errorListener);
        requestQueue.add(stringRequest);
    }

    /**
     * Uploads a photo.
     *
     * @param userId ID of the user uploading the photo.
     * @param tagId Tag ID associated with the photo.
     * @param fileName Name of the file being uploaded.
     * @param encodedImage base64 encoded image to be uploaded.
     * @param responseListener Listener for successful response.
     * @param errorListener Listener for error response.
     */
    public void uploadPhoto(String userId, String tagId, String fileName, String encodedImage, Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        String url = BASE_URL + "/postMethodUploadPhoto";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, responseListener, errorListener) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("userId", userId);
                params.put("tagId", tagId);
                params.put("fileName", fileName);
                params.put("imageStringBase64", encodedImage);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    /**
     * Downloads a photo.
     *
     * @param fileName Name of the file to download.
     * @param responseListener Listener for successful response, returns a Bitmap.
     * @param errorListener Listener for error response.
     */
    public void downloadPhoto(String fileName, Response.Listener<Bitmap> responseListener, Response.ErrorListener errorListener) {
        String url = BASE_URL + "/getMethodDownloadPhoto?fileName=" + Uri.encode(fileName);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    byte[] decodedString = Base64.decode(response, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    responseListener.onResponse(decodedByte);
                },
                errorListener
        );

        requestQueue.add(stringRequest);
    }


    /**
     * Fetches tags associated with a specific user.
     *
     * @param userId The ID of the user whose tags are to be fetched.
     * @param responseListener A listener for handling the successful server response.
     * @param errorListener A listener for handling errors in the request.
     */
    public void getMyTags(String userId, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
        String url = BASE_URL + "/getMethodMyTags?id=" + Uri.encode(userId);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, responseListener, errorListener);
        requestQueue.add(jsonObjectRequest);
    }


    /**
     * Inserts a new tag for a user's photo.
     *
     * @param userId The ID of the user who is adding the tag.
     * @param indexUpdateTag The index where the tag should be inserted.
     * @param newTagDes Description for the new tag.
     * @param newTagPho The photo associated with the new tag.
     * @param newTagLoc The location associated with the new tag.
     * @param newTagPeopleName Names of people associated with the new tag.
     * @param responseListener A listener for handling the successful server response.
     * @param errorListener A listener for handling errors in the request.
     */
    public void insertNewTag(String userId, String indexUpdateTag, String newTagDes, String newTagPho, String newTagLoc, String newTagPeopleName, Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        String url = BASE_URL + "/postInsertNewTag";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, responseListener, errorListener) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("userId", userId);
                params.put("indexUpdateTag", indexUpdateTag);
                params.put("newTagDes", newTagDes);
                params.put("newTagPho", newTagPho);
                params.put("newTagLoc", newTagLoc);
                params.put("newTagPeopleName", newTagPeopleName);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }



    /**
     * Updates an existing tag for a user's photo.
     *
     * @param userId The ID of the user who is updating the tag.
     * @param indexUpdateTag The index of the tag to be updated.
     * @param updateTagDes Updated description for the tag.
     * @param updateTagPho Updated photo for the tag.
     * @param updateTagLoc Updated location for the tag.
     * @param updateTagPeopleName Updated names of people for the tag.
     * @param responseListener A listener for handling the successful server response.
     * @param errorListener A listener for handling errors in the request.
     */
    public void updateTag(String userId, String indexUpdateTag, String updateTagDes, String updateTagPho, String updateTagLoc, String updateTagPeopleName, Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        String url = BASE_URL + "/postUpdateTag";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, responseListener, errorListener) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("userId", userId);
                params.put("indexUpdateTag", indexUpdateTag);
                params.put("updateTagDes", updateTagDes);
                params.put("updateTagPho", updateTagPho);
                params.put("updateTagLoc", updateTagLoc);
                params.put("updateTagPeopleName", updateTagPeopleName);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
