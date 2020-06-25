package com.tenneti.thousie.utility;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.imageio.ImageIO;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class ImgBBPhotoUpload {

	private final String URI = "https://api.imgbb.com/1/upload?key=<YOUR_IMGBB_KEY>";

	public String uploadPhoto(String imgFilePath) {
		String url = null;

		try {
			BufferedImage sourceimage = ImageIO.read(new File(imgFilePath));

			ByteArrayOutputStream bytes = new ByteArrayOutputStream();
			ImageIO.write(sourceimage, "png", bytes);
			String resultantimage = Base64.encode(bytes.toByteArray());

			MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();

			MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
			body.add("image", resultantimage);

			HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> response = restTemplate.postForEntity(URI, requestEntity, String.class);

			JSONObject obj = new JSONObject(response.getBody());
			url = obj.getJSONObject("data").getString("url");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return url;

	}
}
