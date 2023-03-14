package com.ivaniv.barber.project.config;

import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CloudImages {
    private static CloudImages cloudImages = null;
    private  Cloudinary cloudinary;
    private String cloud_name = System.getenv("cloud_name");//"dbjkbii2x";
    private String api_key = System.getenv("api_key");//"817491166451287";
    private String api_secret = System.getenv("api_secret");//"kJEtHfV4Y9AWdSdzKLd854ICcCM";

    private CloudImages() {
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloud_name,
                "api_key", api_key,
                "api_secret", api_secret,
                "secure", true));
    }

    public Cloudinary getCloudinary() {
        return cloudinary;
    }

    public static CloudImages getInstance() {
        if (cloudImages == null)
            cloudImages = new CloudImages();
        return cloudImages;
    }

}
