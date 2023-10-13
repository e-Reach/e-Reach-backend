package org.ereach.inc.data.dtos.request;

import org.springframework.web.multipart.MultipartFile;

public class CloudUploadRequest {

    private MultipartFile file;
    private String ownerEmail;
}
