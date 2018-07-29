package com.manju7.encryption_decryption;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {

    EditText inputN , inputP;
    TextView encry , decry;
    Button buttonEn , buttonDe;
    String outputEn ;
    String ASE="AES/ECB/PKCS5PADDING";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputN=(EditText) findViewById(R.id.editTextN);
        inputP=(EditText) findViewById(R.id.editTextP);

        encry=(TextView) findViewById(R.id.texten);
        decry = (TextView) findViewById(R.id.textden);

        buttonEn =(Button) findViewById(R.id.encry);
        buttonDe =(Button) findViewById(R.id.decry);

        buttonEn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    outputEn = encrypt(inputN.getText().toString(), inputP.getText().toString());
                    encry.setText(outputEn);
                    inputP.setText("");
                    inputN.setText("");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        buttonDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    outputEn=decrypt(outputEn,inputP.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                decry.setText(outputEn);
            }
        });


    }

    private String decrypt(String outputEn, String password) throws Exception {

        SecretKeySpec key =generateKey(password);
        Cipher c = Cipher.getInstance(ASE);
        c.init(Cipher.DECRYPT_MODE,key);
        byte [] decodeValue = Base64.decode(outputEn,Base64.DEFAULT);
        byte [] decVal = c.doFinal(decodeValue);
        String decryptedValue = new String(decVal);
        return decryptedValue;
    }

    private String encrypt(String name , String password) throws  Exception{

        SecretKeySpec key = generateKey(name);
        Cipher c = Cipher.getInstance(ASE);
        c.init(Cipher.ENCRYPT_MODE,key);
        byte[] encVal = c.doFinal(password.getBytes());
        String encryptedValue = Base64.encodeToString(encVal,Base64.DEFAULT);
        return encryptedValue;

    }

    private SecretKeySpec generateKey(String password) throws Exception {

        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = password.getBytes("UTF-8");
        digest.update(bytes,0,bytes.length);
        byte[] key = digest.digest();
        SecretKeySpec secretKeySpec = new SecretKeySpec(key,"AES");
        return secretKeySpec;
    }
}
