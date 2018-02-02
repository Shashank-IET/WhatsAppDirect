package com.example.shashank.whatsappdirect;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.shashank.whatsappdirect.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

//    918934906717

    ActivityMainBinding binding;
    String smsNumber = "918953632902"; // E164 format without '+' sign

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    public void sendText(View view) {

//        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
//        whatsappIntent.setType("text/plain");
//        whatsappIntent.setPackage("com.whatsapp");
//        whatsappIntent.putExtra(Intent.EXTRA_TEXT, "The text you wanted to share");
//        try {
//            startActivity(whatsappIntent);
//        } catch (android.content.ActivityNotFoundException ex) {
//        binding.logs.setText(ex.getMessage());
//            Toast.makeText(this, "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();
//        }

//        Intent sendIntent = new Intent(Intent.ACTION_SEND);
//        sendIntent.setType("text/plain");
//        sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
//        sendIntent.putExtra("jid", smsNumber + "@s.whatsapp.net"); //phone number without "+" prefix
//        sendIntent.setPackage("com.whatsapp");
//        try {
//            startActivity(sendIntent);
//        }catch (Exception ex){
//        binding.logs.setText(ex.getMessage());
//            ex.printStackTrace();
//            Toast.makeText(this, "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();
//        }

        Uri mUri = Uri.parse("https://api.whatsapp.com/send?phone=" + smsNumber + "&text=" + "hello");
        Intent mIntent = new Intent("android.intent.action.VIEW", mUri);
        mIntent.setPackage("com.whatsapp");
        startActivity(mIntent);
    }

    public void sendImage(View view) {

        Intent intent = new Intent();
        // Show only images, no videos or anything else
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        // Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 100);

    }

    public void sendBoth(View view) {

        Intent intent = new Intent();
        // Show only images, no videos or anything else
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        // Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 101);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            //Target whatsapp:
            shareIntent.setPackage("com.whatsapp");
            //Add text and then Image URI
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Enjoy the image!");
            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
            shareIntent.setType("image/jpeg");
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            shareIntent.putExtra("jid", smsNumber + "@s.whatsapp.net"); //phone number without "+" prefix

            try {
                startActivity(shareIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                binding.logs.setText(ex.getMessage());
                ex.printStackTrace();
                Toast.makeText(this, "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();
            }
        }

        // FAILURE CASE
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            Uri mUri = Uri.parse("https://api.whatsapp.com/send?phone=" + smsNumber);
            Intent mIntent = new Intent("android.intent.action.View", mUri);
            mIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
            mIntent.setType("image/jpeg");
            mIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            mIntent.setPackage("com.whatsapp");
            try {
                startActivity(mIntent);
            } catch (Exception ex) {
                ex.printStackTrace();
                binding.logs.setText(ex.getMessage());
                Toast.makeText(this, "Whatsapp died!", Toast.LENGTH_SHORT).show();
            }
        }

        if (resultCode != RESULT_OK)
            Toast.makeText(this, "Improper result received!", Toast.LENGTH_SHORT).show();
    }
}
