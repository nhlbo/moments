package com.example.moments.ui.vuforia.VuforiaAPI;


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.DateUtils;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;


// See the Vuforia Web Services Developer API Specification - https://library.vuforia.com/articles/Solution/How-To-Use-the-Vuforia-Web-Services-API#How-To-Add-a-Target

public class PostNewTarget implements TargetStatusListener {

    //Server Keys
    private String accessKey = Key.kAccessKey;
    private String secretKey = Key.kSecretKey;

    private String url = "https://vws.vuforia.com";
    private String targetName = "[ target name ]";
    private String imageLocation = "[ file system path ]";
    private String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private TargetStatusPoller targetStatusPoller;
    private byte[] image;
    private final float pollingIntervalMinutes = 60;//poll at 1-hour interval

    private String postTarget() throws URISyntaxException, ClientProtocolException, IOException, JSONException {
        HttpPost postRequest = new HttpPost();
        HttpClient client = new DefaultHttpClient();
        postRequest.setURI(new URI(url + "/targets"));
        JSONObject requestBody = new JSONObject();

        setRequestBody(requestBody);
        postRequest.setEntity(new StringEntity(requestBody.toString()));
        setHeaders(postRequest); // Must be done after setting the body

        HttpResponse response = client.execute(postRequest);
        String responseBody = EntityUtils.toString(response.getEntity());
        System.out.println(responseBody);

        JSONObject jobj = new JSONObject(responseBody);

        String uniqueTargetId = jobj.has("target_id") ? jobj.getString("target_id") : "";
        System.out.println("\nCreated target with id: " + uniqueTargetId);

        return uniqueTargetId;
    }

    private void setRequestBody(JSONObject requestBody) throws IOException, JSONException {
        File imageFile = new File(imageLocation);
        if (!imageFile.exists()) {
            System.out.println("File location does not exist!");
            System.exit(1);
        }
        //Read
        byte[] image = FileUtils.readFileToByteArray(imageFile);
        requestBody.put("name", targetName); // Mandatory
        requestBody.put("width", 100.0); // Mandatory
        requestBody.put("image", java.util.Base64.getEncoder().encodeToString(image)); // Mandatory
        requestBody.put("active_flag", 1); // Optional
        requestBody.put("application_metadata", java.util.Base64.getEncoder().encodeToString("Vuforia test metadata".getBytes())); // Optional
        Log.d("image", java.util.Base64.getEncoder().encodeToString(image).toString());
        Log.d("imageLength", String.valueOf(Base64.getEncoder().encodeToString(image).getBytes(StandardCharsets.UTF_8).length));
        Log.d("body", requestBody.toString());
    }

    private void setHeaders(HttpUriRequest request) {
        SignatureBuilder sb = new SignatureBuilder();
        request.setHeader(new BasicHeader("Date", DateUtils.formatDate(new Date()).replaceFirst("[+]00:00$", "")));
        request.setHeader(new BasicHeader("Content-Type", "application/json"));
        request.setHeader("Authorization", "VWS " + accessKey + ":" + sb.tmsSignature(request, secretKey));
        Log.d("Authorization", "VWS " + accessKey + ":" + sb.tmsSignature(request, secretKey));
        Log.d("Header", request.toString());
    }

    /**
     * Posts a new target to the Cloud database;
     * then starts a periodic polling until 'status' of created target is reported as 'success'.
     */
    public void postTargetThenPollStatus() {
        String createdTargetId = "";
        try {
            createdTargetId = postTarget();
            if (!createdTargetId.equals("")) {
                //update vuforiaId firebase
                FirebaseFirestore.getInstance().document("user/" + currentUserId)
                        .update("vuforiaId", createdTargetId)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });

            }
        } catch (URISyntaxException | IOException | JSONException e) {
            e.printStackTrace();
            return;
        }

        // Poll the target status until the 'status' is 'success'
        // The TargetState will be passed to the OnTargetStatusUpdate callback
        if (createdTargetId != null && !createdTargetId.isEmpty()) {
            targetStatusPoller = new TargetStatusPoller(pollingIntervalMinutes, createdTargetId, accessKey, secretKey, this);
            targetStatusPoller.startPolling();
        }
    }

    // Called with each update of the target status received by the TargetStatusPoller
    @Override
    public void OnTargetStatusUpdate(TargetState target_state) {
        if (target_state.hasState) {

            String status = target_state.getStatus();

            System.out.println("Target status is: " + (status != null ? status : "unknown"));

            if (target_state.getActiveFlag() == true && "success".equalsIgnoreCase(status)) {

                targetStatusPoller.stopPolling();

                System.out.println("Target is now in 'success' status");
            }
        }
    }

    public PostNewTarget() {

    }

    public PostNewTarget(String targetName, byte[] image) {
        this.targetName = targetName;
        this.image = image;
    }

    public PostNewTarget(String targetName, String imageLocation) {
        this.targetName = targetName;
        this.imageLocation = imageLocation;
    }

    public static void main(String[] args) throws URISyntaxException, ClientProtocolException, IOException, JSONException {
        PostNewTarget p = new PostNewTarget();
        p.postTargetThenPollStatus();
    }

}
