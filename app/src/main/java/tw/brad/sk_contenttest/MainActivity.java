package tw.brad.sk_contenttest;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private ContentResolver cr;

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
    }


    public void test1(View view) {
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null,null,null,null);

        int count = cursor.getCount();
        Log.v("brad", "count = " + count);

        String[] fields = cursor.getColumnNames();
        for (String field : fields){
            Log.v("brad", field);
        }
        Log.v("brad", "-------");
        while (cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex("display_name"));
            Log.v("brad", name);
        }


    }
}
