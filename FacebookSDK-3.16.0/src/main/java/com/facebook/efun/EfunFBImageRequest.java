package com.facebook.efun;

import java.net.URI;
import java.net.URISyntaxException;

import com.facebook.internal.ImageRequest;

public class EfunFBImageRequest {

	public static URI getProfilePictureUrl(
            String userId,
            int width,
            int height)
            throws URISyntaxException {
		return ImageRequest.getProfilePictureUrl(userId, width, height);
	}

}
