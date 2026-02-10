package com.agrosense.backend.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    @Value("${cloudinary.cloud-name}")
    private String cloudName;

    @Value("${cloudinary.api-key}")
    private String apiKey;

    @Value("${cloudinary.api-secret}")
    private String apiSecret;

    private Cloudinary cloudinary;

    @PostConstruct
    public void init() {
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret,
                "secure", true
        ));
    }

    /**
     * Upload an image to Cloudinary
     * @param file The image file to upload
     * @param folder The folder to store the image in (e.g., "profile-photos")
     * @param publicId The public ID for the image (optional, will be auto-generated if null)
     * @return The secure URL of the uploaded image
     */
    public String uploadImage(MultipartFile file, String folder, String publicId) throws IOException {
        Map<String, Object> options = ObjectUtils.asMap(
                "folder", folder,
                "resource_type", "image",
                "overwrite", true
        );

        if (publicId != null) {
            options.put("public_id", publicId);
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> result = cloudinary.uploader().upload(file.getBytes(), options);
        
        // Build URL with transformations for profile photos
        String secureUrl = (String) result.get("secure_url");
        // Add transformation to URL: crop to 500x500, focus on face
        if (secureUrl != null && secureUrl.contains("/upload/")) {
            secureUrl = secureUrl.replace("/upload/", "/upload/w_500,h_500,c_fill,g_face/");
        }
        return secureUrl;
    }

    /**
     * Delete an image from Cloudinary
     * @param publicId The public ID of the image to delete (including folder path)
     */
    public void deleteImage(String publicId) throws IOException {
        cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
    }

    /**
     * Extract public ID from a Cloudinary URL
     * @param url The Cloudinary URL
     * @return The public ID
     */
    public String extractPublicId(String url) {
        if (url == null || !url.contains("cloudinary.com")) {
            return null;
        }
        // URL format: https://res.cloudinary.com/cloud-name/image/upload/v1234567890/folder/filename.ext
        try {
            String[] parts = url.split("/upload/");
            if (parts.length > 1) {
                String pathWithVersion = parts[1];
                // Remove version number if present (v1234567890/)
                if (pathWithVersion.startsWith("v")) {
                    int slashIndex = pathWithVersion.indexOf('/');
                    if (slashIndex > 0) {
                        pathWithVersion = pathWithVersion.substring(slashIndex + 1);
                    }
                }
                // Remove file extension
                int dotIndex = pathWithVersion.lastIndexOf('.');
                if (dotIndex > 0) {
                    return pathWithVersion.substring(0, dotIndex);
                }
                return pathWithVersion;
            }
        } catch (Exception e) {
            // Log and return null
            System.err.println("Failed to extract public ID from URL: " + url);
        }
        return null;
    }
}
