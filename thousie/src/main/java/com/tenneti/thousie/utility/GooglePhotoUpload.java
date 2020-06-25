package com.tenneti.thousie.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.Credentials;
import com.google.auth.oauth2.UserCredentials;
import com.google.photos.library.v1.PhotosLibraryClient;
import com.google.photos.library.v1.PhotosLibrarySettings;
import com.google.photos.library.v1.proto.BatchCreateMediaItemsResponse;
import com.google.photos.library.v1.proto.NewMediaItem;
import com.google.photos.library.v1.proto.NewMediaItemResult;
import com.google.photos.library.v1.upload.UploadMediaItemRequest;
import com.google.photos.library.v1.upload.UploadMediaItemResponse;
import com.google.photos.library.v1.util.NewMediaItemFactory;
import com.google.photos.types.proto.Album;
import com.google.photos.types.proto.MediaItem;
import com.google.photos.types.proto.SharedAlbumOptions;
import com.google.rpc.Code;
import com.google.rpc.Status;

public class GooglePhotoUpload {
	
    private static final String CREDENTIALS_FILE_PATH = "<FULL_PATH_FOR_CREDENTIALS>";
    private static final java.io.File DATA_STORE_DIR = new File("/photo-credentials.json");
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final int LOCAL_RECEIVER_PORT = 61984;

    private static final List<String> SCOPES =  Arrays.asList("https://www.googleapis.com/auth/photoslibrary","https://www.googleapis.com/auth/photoslibrary.sharing");
 

	public String uploadPhoto() {
		String url = null;
		try {
		PhotosLibrarySettings settings =
		     PhotosLibrarySettings.newBuilder()
		    .setCredentialsProvider(
		        FixedCredentialsProvider.create(
		        		getUserCredentials(CREDENTIALS_FILE_PATH, SCOPES)
		        		)) 
		    .build();

		PhotosLibraryClient photosLibraryClient =
		    PhotosLibraryClient.initialize(settings);

		    Album createdAlbum = photosLibraryClient.createAlbum("HousieTickets1");
		    String id = createdAlbum.getId();
		    
		    SharedAlbumOptions options =
		            SharedAlbumOptions.newBuilder()
		            .setIsCollaborative(true)
		            .setIsCommentable(true)
		            .build();
		    photosLibraryClient.shareAlbum(id, options);

		    // CAN USE THE ABOVE RESPONSE AS WELL FOR SHARED URL
//		    ShareInfo info = response.getShareInfo();
//		    String urlShared = info.getShareableUrl();

		    url = getUploadToken(photosLibraryClient, id);

		} catch (Exception e) {
		    e.printStackTrace();
		}
		return url;
	}
	
	private String getUploadToken(PhotosLibraryClient photosLibraryClient, String albumId) {
		String url = null;
		try { 
			
			RandomAccessFile file = new RandomAccessFile("C:\\\\Users\\\\vatsa\\\\HousieTickets\\\\tickets_0.png", "r");

			  // Create a new upload request
			  UploadMediaItemRequest uploadRequest =
			      UploadMediaItemRequest.newBuilder()
			              // The media type (e.g. "image/png")
			              .setMimeType("image/png")
			              // The file to upload
			              .setDataFile(file)
			          .build();
			  // Upload and capture the response
			  UploadMediaItemResponse uploadResponse = photosLibraryClient.uploadMediaItem(uploadRequest);
			  if (uploadResponse.getError().isPresent()) {
			    // CAN USE THIS ERROR IF NEEDED
			    // com.google.photos.library.v1.upload.UploadMediaItemResponse.Error error = uploadResponse.getError().get();
			  } else {
			    // If the upload is successful, get the uploadToken
			    String uploadToken = uploadResponse.getUploadToken().get();
			  url = createMediaItem(uploadToken, "tickets","tickets", photosLibraryClient, albumId);
			  }
			} catch (Exception e) {
				e.printStackTrace();
			}
		return url;
	}
	
	
	private String createMediaItem(String uploadToken, String fileName, String itemDescription, PhotosLibraryClient photosLibraryClient, String albumId) {
		String url = null;
		try {
			  // Create a NewMediaItem with the following components:
			  // - uploadToken obtained from the previous upload request
			  // - filename that will be shown to the user in Google Photos
			  // - description that will be shown to the user in Google Photos
			NewMediaItem newMediaItem = NewMediaItemFactory
			          .createNewMediaItem(uploadToken, fileName, itemDescription);
			  List<NewMediaItem> newItems = Arrays.asList(newMediaItem);

			  BatchCreateMediaItemsResponse response = photosLibraryClient.batchCreateMediaItems(newItems);
			  for (NewMediaItemResult itemsResponse : response.getNewMediaItemResultsList()) {
			    Status status = itemsResponse.getStatus();
			    if (status.getCode() == Code.OK_VALUE) {
			      // The item is successfully created in the user's library
			      MediaItem createdItem = itemsResponse.getMediaItem();
			      url = createdItem.getProductUrl();
			    } else {
			      // The item could not be created. Check the status and try again
			    }
			  }
			  
			  BatchCreateMediaItemsResponse resp = photosLibraryClient
				      .batchCreateMediaItems(albumId, newItems);
			  
			  System.out.println(resp.getAllFields());
			} catch (Exception e) {
			  // Handle error
			}
		
		return url;
	}
	
	
	
	
	 private static Credentials getUserCredentials(String credentialsPath, List<String> selectedScopes)
		      throws IOException, GeneralSecurityException {
		    GoogleClientSecrets clientSecrets =
		        GoogleClientSecrets.load(
		            JSON_FACTORY, new InputStreamReader(new FileInputStream(credentialsPath)));
		    String clientId = clientSecrets.getDetails().getClientId();
		    String clientSecret = clientSecrets.getDetails().getClientSecret();

		    GoogleAuthorizationCodeFlow flow =
		        new GoogleAuthorizationCodeFlow.Builder(
		                GoogleNetHttpTransport.newTrustedTransport(),
		                JSON_FACTORY,
		                clientSecrets,
		                selectedScopes)
		            .setDataStoreFactory(new FileDataStoreFactory(DATA_STORE_DIR))
		            .setAccessType("offline")
		            .build();
		    LocalServerReceiver receiver =
		        new LocalServerReceiver.Builder().setPort(LOCAL_RECEIVER_PORT).build();
		    Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
		    return UserCredentials.newBuilder()
		        .setClientId(clientId)
		        .setClientSecret(clientSecret)
		        .setRefreshToken(credential.getRefreshToken())
		        .build();
		  }

}
