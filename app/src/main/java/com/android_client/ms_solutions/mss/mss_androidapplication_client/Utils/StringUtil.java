package com.android_client.ms_solutions.mss.mss_androidapplication_client.Utils;

/**
 * Created by Walid Zhani @Walid.Zhy7 on 21/03/2018.
 */

import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;

import com.android_client.ms_solutions.mss.mss_androidapplication_client.DialogBoxes.AlertMessageBox;
import com.android_client.ms_solutions.mss.mss_androidapplication_client.LoginActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

   // public static EditText editTxt_email_UserName;

    public static boolean isNullOrWhitespace(String string) {
        boolean NullOrWhitespace;

         if (string == null || string.isEmpty() || string.trim().isEmpty() || string == ""){
             NullOrWhitespace = true;
         }else{
             NullOrWhitespace = false;
         }

         return NullOrWhitespace;
    }

    public static boolean isUserName (String string)
    {
        boolean valid;

        if (isNullOrWhitespace(string) || !isMatch("^[a-zA-Z]{3,15}$",string)   /*!string.contains("@") ||  !Patterns.EMAIL_ADDRESS.matcher(string).matches()*/  )
        {
            // regex : /^[a-zA-Z0-9]+([_ - +]?[a-zA-Z0-9])*$/
            // regex : ^[a-z0-9_-]{3,15}$
           // editTxt_email_UserName.setError("enter a valid email address Or Username");
            //return android.util.Patterns.EMAIL_ADDRESS.matcher(string).matches();
            valid = false;
        }
        else {
          //  editTxt_email_UserName.setError(null);
            valid = true;
        }

        return valid;
    }

    public static boolean isPhoneNumber(String string){

        boolean valid;
        String regexPhoneNumberValidation = "^[0-9]*$"; // Only Numbers

        if(isNullOrWhitespace(string) || !isMatch(regexPhoneNumberValidation,string) || string.length() < 8 || string.length() > 15 ){
            valid = false;
        }else {
            valid = true;
        }

        return valid;
    }

    public static boolean isEmailAddress(String string){

        boolean valid;
        String regexEmailValidation = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";

        if (isNullOrWhitespace(string) || !isMatch(regexEmailValidation,string)){
            valid = false;
        }else {
            valid = true;
        }

        return valid;
    }

    public static boolean isMatch(String regex,String text)
    {
        try {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(text);
            return matcher.matches();
        } catch (RuntimeException e) {
            return false;
        }
    }

    public static boolean isValidPasswordLength(String string){
        boolean valid;

        if (isNullOrWhitespace(string) || string.length() < 4 || string.length() > 15) {
            //editTxtPasswd.setError("Password should be between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            //editTxtPasswd.setError(null);
            valid = true;
        }

        return valid;
    }

    public static String passwordStrength(String inputPassword){

        String passwordStrength = "";

        //String strongPassword = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\\$%\\^&\\*])(?=.{8,})";
        //String mediumPassword = "^(((?=.*[a-z])(?=.*[A-Z]))|((?=.*[a-z])(?=.*[0-9]))|((?=.*[A-Z])(?=.*[0-9])))(?=.{6,})";

        String strongPassword = "^.*(?=.{12,})(?=.*\\d)(?=.*[a-zA-Z]).*$";
        String mediumPassword = "^.*(?=.{7,})(?=.*\\d)(?=.*[a-zA-Z]).*$";

        if (isMatch(strongPassword,inputPassword)){
            passwordStrength = "StrongPassword";
        }else if (isMatch(mediumPassword,inputPassword)){
            passwordStrength = "MediumPassword";
        }else{
            passwordStrength = "WeakPassword";
        }

        return passwordStrength;
    }


    public static boolean isPasswordsConfirmedEquals(String newPassword,String confirmNewPassword){
        boolean valid;

        if (!newPassword.equals(confirmNewPassword)){
            valid = false;
        }else {
            valid = true;
        }

        return valid;
    }

}
