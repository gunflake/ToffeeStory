package com.toffeestory.backend.post;

import com.toffeestory.backend.account.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Slf4j
@Service
public class PostService {
    @Autowired
    InterestPostRepository interestPostRepository;

    public void updateInterest(Post post, Account account, Byte valueCode, Boolean useState) {
        InterestPost interestPost;

        InterestPost.UseType useType = InterestPost.UseType.UNINTERESTED;

        if(!useState) useType = InterestPost.UseType.INTEREST;

        //해당 정보가 있으면 찾아서 상태 값 변경
        if(interestPostRepository.findAllByPostAndAccount(post, account).isPresent()) {
            interestPost = interestPostRepository.findAllByPostAndAccount(post, account).orElseThrow(() -> new RuntimeException());

            if(valueCode == 0) {
                interestPost.setLikeState(useType);
            } else {
                interestPost.setBookmarkState(useType);
            }
        } else {
            interestPost = new InterestPost();

            interestPost.setPost(post);
            interestPost.setAccount(account);

            if(valueCode == 0) {
                interestPost.setLikeState(useType);
            } else {
                interestPost.setBookmarkState(useType);
            }
        }

        interestPostRepository.save(interestPost);
    }

    public void resize(String inputImagePath,
                              String outputImagePath, int scaledWidth, int scaledHeight)
            throws IOException {
        // reads input image
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = ImageIO.read(inputFile);

        // creates output image
        BufferedImage outputImage = new BufferedImage(scaledWidth,
                scaledHeight, inputImage.getType());

        // scales the input image to the output image
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();

        // extracts extension of output file
        String formatName = outputImagePath.substring(outputImagePath
                .lastIndexOf(".") + 1);

        // writes to output file
        boolean write = ImageIO.write(outputImage, formatName, new File(outputImagePath));
    }

    public void resize(String inputImagePath,
                              String outputImagePath, double percent) throws IOException {

        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = ImageIO.read(inputFile);

        int scaledWidth = (int) (inputImage.getWidth() * percent);
        int scaledHeight = (int) (inputImage.getHeight() * percent);
        resize(inputImagePath, outputImagePath, scaledWidth, scaledHeight);
    }



}
