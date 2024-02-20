package com.learn.services;

import com.learn.models.Error;
import com.learn.models.Otp;
import com.learn.models.User;
import com.learn.models.UserPassword;
import com.learn.repo.OtpRepository;
import com.learn.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Random;

@Service
public class OtpService {

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    private final Random random =new SecureRandom();

    public ResponseEntity generateOtp(String userName) {
        try {

            Otp byUserName = otpRepository.findByUserName(userName);

            int genratedOtp = 100000 + random.nextInt(900000);

            Otp otp=new Otp();
            if (byUserName != null) {
                byUserName.setOtp(genratedOtp);
                byUserName.setModifyTime(System.currentTimeMillis());

                otpRepository.save(byUserName);

                return new ResponseEntity<>("OTP sent Successfully",HttpStatus.OK);

            }else {
                otp.setUserName(userName);
                otp.setOtp(genratedOtp);
                otpRepository.save(otp);
                return new ResponseEntity<>("OTP sent Successfully",HttpStatus.OK);
            }

        } catch (Exception e) {
            e.printStackTrace();
            Error error = Error.builder().Code(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()).message("Error while generating the OTP. Failed to generate OTP").build();
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity resetPassword(UserPassword userPassword,String userName,int otp) {
        try {

            User userByUsername = userRepository.getUserByUsername(userName);



            if (validateOtp(otp,userName)){
                userByUsername.setPassword(bCryptPasswordEncoder.encode(userPassword.getPassword()));
                return new ResponseEntity<>("Password reset successfully", HttpStatus.OK);
            }
            return new ResponseEntity<>("Invalid or expired OTP. Password reset failed", HttpStatus.REQUEST_TIMEOUT);
        } catch (Exception e) {
            e.printStackTrace();
            Error error = Error.builder().Code(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()).message("Error while updating the user").build();
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Boolean validateOtp(int otp,String userName){
        Otp byUserName = otpRepository.findByUserName(userName);

        if (byUserName.getOtp()==otp){
            long currentTimeMillis = System.currentTimeMillis();
            long otpTimeMillis = byUserName.getModifyTime();
            if (currentTimeMillis-otpTimeMillis<= 5*60*1000){
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }
}
