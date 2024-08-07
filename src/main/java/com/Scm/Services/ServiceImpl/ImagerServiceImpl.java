package com.Scm.Services.ServiceImpl;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Scm.Helper.AppConstant;
import com.Scm.Services.ImageService;
import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;

@Service
public class ImagerServiceImpl implements ImageService {

    private Cloudinary cloudinary;
                                                         // here use construcotr injection 
                                                          // we can use @autowired anotation 

    public ImagerServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }


    @Override
    public String uploadImage(MultipartFile contactImage, String filename) {

        // code likhnaa hia jo image ko upload kar rha ho

        try {
            byte[] data = new byte[contactImage.getInputStream().available()];
            contactImage.getInputStream().read(data);
            cloudinary.uploader().upload(data, ObjectUtils.asMap(
                    "public_id", filename));

            return this.getUrlFromPublicId(filename);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        // and return raha hoga : url

    }


    @Override
    public String getUrlFromPublicId(String publicId) {
       
        return cloudinary
                .url()
                .transformation(
                        new Transformation<>()
                                .width(AppConstant.CONTACT_IMAGE_WIDTH)
                                .height(AppConstant.CONTACT_IMAGE_HEIGHT)
                                .crop(AppConstant.CONTACT_IMAGE_CROP))
                .generate(publicId);

    }


   
}
