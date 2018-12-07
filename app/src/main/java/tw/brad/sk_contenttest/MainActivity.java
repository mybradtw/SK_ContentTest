package tw.brad.sk_contenttest;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private ContentResolver cr;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    123);
        }else{
            init();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        init();
    }

    private void init(){
        cr = getContentResolver();

        img = findViewById(R.id.img);

    }


    public void test1(View view) {
        Cursor cursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,null,null,null);

        int count = cursor.getCount();
        Log.v("brad", "count = " + count);

        String[] fields = cursor.getColumnNames();
        for (String field : fields){
            Log.v("brad", field);
        }
        Log.v("brad", "-------");
        Log.v("brad", ContactsContract. Contacts.DISPLAY_NAME);
        Log.v("brad", ContactsContract.CommonDataKinds.Phone.NUMBER);


        while (cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex("display_name"));
            String tel = cursor.getString(cursor.getColumnIndex("data1"));
            Log.v("brad", name + ":" + tel);
        }


    }

    public void test2(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK){
            afterCamera1(data);
        }
    }

    private void afterCamera1(Intent data){
        Log.v("brad", "OK");

        Bitmap bitmap=(Bitmap)data.getExtras().get("data");
        img.setImageBitmap(bitmap);
    }

}
