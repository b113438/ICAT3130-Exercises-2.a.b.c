package com.example.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    //Function to activate the button_camera
    private void activate_button()
    {
        Button button_camera = (Button) findViewById(R.id.button_camera);
        button_camera.setEnabled(true); //The button is initialized as "false" in the layout, but I want an active button
    }

    //Function to ask for the needed permissions
    private void request_permission_camera()
    {
        //if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)  Another way to do it
        if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED)
        {
            requestPermissions(new String[]{Manifest.permission.CAMERA},1);
        }
        else
        {
            activate_button();
        }
    }

    //Function to see if you have accepted the permissions
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        if (requestCode == 1 && grantResults[0] != PackageManager.PERMISSION_GRANTED)
        {
            AlertDialog.Builder pop_up = new AlertDialog.Builder(this);
            pop_up.setMessage("Permission not accepted.");
        }
        else if(requestCode == 1) //grantResults[0] permissions will be granted
        {
            activate_button();
        }
    }

    //Function to open the camera and take a picture
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public void take_picture(View view) //We put view as parameter in order to link it to the layout
    {
        Intent intent_picture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent_picture.resolveActivity(getPackageManager()) != null)
        {
            startActivityForResult(intent_picture, REQUEST_IMAGE_CAPTURE);
        }
    }

    //Function to get the result from taken the picture
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)
        {
            System.out.println("Successful.");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        request_permission_camera();
    }
}