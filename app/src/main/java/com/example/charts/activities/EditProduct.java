package com.example.charts.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.charts.adapters.Adapterlist;
import com.example.charts.adapters.CustomAdapterforlistview;
import com.example.charts.dataBase.DatabaseHelper;
import com.example.charts.dataBase.Databasemanger;
import com.example.charts.productBuilder.Product;
import com.example.charts.productBuilder.ProductBuilder;
import com.example.charts.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.ByteArrayOutputStream;

public class EditProduct extends AppCompatActivity {
    ImageView image,qr_image;
    EditText titel,descreption,salaryy,itemnumber , sale;
    Button update,delet;

    Databasemanger databasemanger;

    Spinner catagories,sizeofitem;

    Adapterlist adapterlist;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PERMISSION_REQUEST_CAMERA = 1;
    String catagoryname, sizeof_product;
    TextView qr_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);


        //coulmn names
        image=findViewById(R.id.imageonedit);
        titel=findViewById(R.id.titelonedit);
        descreption=findViewById(R.id.desonedit);
        salaryy=findViewById(R.id.salaryonedit);
        sale = findViewById(R.id.salelonedit);
        itemnumber=findViewById(R.id.itemnumberonedit);
        catagories=findViewById(R.id.spinner_catagores);
        sizeofitem=findViewById(R.id.spinner_sizes);
        qr_result=findViewById(R.id.qr_result);
        qr_image=findViewById(R.id.qr_image);


        //Buttons
        delet=findViewById(R.id.deletonedit);
        update =findViewById(R.id.editonedit);

        databasemanger=Databasemanger.getInstance(this);


        //select the catagory
        Cursor allcatagores=databasemanger.fetchcatagory();
        adapterlist=new Adapterlist(this,allcatagores);
        catagories.setAdapter(adapterlist);
        catagories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Cursor cursor1=(Cursor)parent.getItemAtPosition(position);
                catagoryname=cursor1.getString(cursor1.getColumnIndexOrThrow(DatabaseHelper.catagoryname));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //select the size

        String[] stringArray = {"s", "m", "l", "xl","xxl","Nosize"};
        CustomAdapterforlistview adapterofsizes=new CustomAdapterforlistview(this,stringArray);
        sizeofitem.setAdapter(adapterofsizes);
        sizeofitem.setSelection(5);
        sizeofitem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                sizeof_product =parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        //get id from intent
        Intent from_recycler_view=getIntent();
        long id =from_recycler_view.getLongExtra("id",-1);
        Cursor cursor ;
        if(id!=-1) {
            cursor=databasemanger.fetchWithId(id);

            if (cursor != null && cursor.moveToFirst()) {


                byte[] imageData = cursor.getBlob(cursor.getColumnIndexOrThrow(DatabaseHelper.image));
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                image.setImageBitmap(bitmap);
                titel.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.titel)));
                descreption.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.description)));
                salaryy.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.salary)));
                sale.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.sale)));
                itemnumber.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.itemNumber)));
                sizeof_product =cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.size));
                catagoryname=cursor.getString((cursor.getColumnIndexOrThrow(DatabaseHelper.category)));

                //size in spinner
                int index_of_size = -1;
                for (int i = 0; i < stringArray.length; i++) {
                    if (stringArray[i].equals(sizeof_product)) {
                        index_of_size = i;
                        break;
                    }
                }
                // Use the index as needed
                if (index_of_size != -1) {
                    // The index is found
                    Toast.makeText(this, "Index of " + sizeof_product + ": " + index_of_size, Toast.LENGTH_SHORT).show();
                    sizeofitem.setSelection(index_of_size);

                } else {
                    // The size is not found in the array
                    Toast.makeText(this, "Size not found in the array", Toast.LENGTH_SHORT).show();
                }


                //catagory in spinner
                int indexof_catagory = -1;
                if (allcatagores.moveToFirst()) {
                    do {
                        String categoryName = allcatagores.getString(allcatagores.getColumnIndexOrThrow(DatabaseHelper.catagoryname));
                        if (categoryName.equals(catagoryname)) {
                            indexof_catagory = allcatagores.getPosition();
                            catagories.setSelection(indexof_catagory);
                            break;
                        }
                    } while (allcatagores.moveToNext());
                }


                // Close the cursor when done with it
                cursor.close();

            }}




        image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
                return true;
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Getting titel name
                String tit = titel.getText().toString();
                String descreptionn = descreption.getText().toString();
                String salaryString = salaryy.getText().toString();
                String saleString = sale.getText().toString();
                String itemNumberString = itemnumber.getText().toString();
                String qr_code_text=qr_result.getText().toString();

                // Reset errors
                titel.setError(null);
                descreption.setError(null);
                salaryy.setError(null);
                itemnumber.setError(null);
                qr_result.setError(null);

                // Check if any of the fields are empty
                if (tit.isEmpty()) {
                    titel.setError("Title is required");
                    return;
                }

                if (descreptionn.isEmpty()) {
                    descreption.setError("Description is required");
                    return;
                }

                if (salaryString.isEmpty()) {
                    salaryy.setError("Salary is required");
                    return;
                }

                if (catagoryname.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "please select the catagory ", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (sizeof_product.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "please select the size ", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (itemNumberString.isEmpty()) {
                    itemnumber.setError("Item number is required");
                    return;
                }

                if (qr_code_text.isEmpty()) {
                    qr_result.setError("Item qr is required");
                    return;
                }


                // Check if an image has been selected
                Drawable drawable = image.getDrawable();
                if (drawable == null) {
                    Toast.makeText(getApplicationContext(), "Please select an image", Toast.LENGTH_SHORT).show();
                    return;
                }else
                {
                    int imageWidth=0;
                    int imageHeight=0;

                    if (drawable instanceof BitmapDrawable) {
                        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();

                        imageWidth = bitmap.getWidth();
                        imageHeight = bitmap.getHeight();

                        if(imageWidth>1306||imageHeight>1189){
                            Toast.makeText(getApplicationContext(), "Please make sure that the image isless than 1180*1300 ", Toast.LENGTH_SHORT).show();
                            return;
                        }


                    }}

                // Convert salary and itemNumber to integers
                int salary = Integer.parseInt(salaryString);
                int itemNumber = Integer.parseInt(itemNumberString);
                float sales = Float.parseFloat(saleString);
                // Convert the Drawable to a Bitmap
                Bitmap bitmap = drawableToBitmap(drawable);
                ByteArrayOutputStream bytearry = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, bytearry);
                byte[] imagee = bytearry.toByteArray();

                Product new_product=new ProductBuilder()
                        .setTitel(tit)
                        .setcategory(catagoryname)
                        .setdescription(descreptionn)
                        .setNumber(itemNumber)
                        .setByte_image(imagee)
                        .setsize(sizeof_product)
                        .setsalary(salary)
                        .setsale(sales)
                        .setQr_code(qr_code_text)
                        .build();

                boolean insert=databasemanger.updateProductData(id,new_product);
                if (insert) {
                    startActivity(new Intent(getApplicationContext(), MangeAllProducts.class));
                    finish();
                    Toast.makeText(getApplicationContext(), "Data has been saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Data not saved", Toast.LENGTH_SHORT).show();
                }
            }
        });

        delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               int delel_ok= databasemanger.deletat(id);
               if(delel_ok==1){
                   Toast.makeText(getApplicationContext(), "product has been deleted", Toast.LENGTH_SHORT).show();
               }
               else {
                   Toast.makeText(getApplicationContext(), "product not deleted ", Toast.LENGTH_SHORT).show();

               }
                startActivity(new Intent(getApplicationContext(),MangeAllProducts.class));
                finish();
            }
        });

        checkCameraPermission();
        qr_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initQRCodeScanner();
            }
        });



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            image.setImageURI(selectedImageUri);
            // Assuming 'imageView' is your ImageView instance
            Drawable drawable = image.getDrawable();
            int imageWidth=0;
            int imageHeight=0;

            if (drawable instanceof BitmapDrawable) {
                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();

                imageWidth = bitmap.getWidth();
                imageHeight = bitmap.getHeight();

                // Use imageWidth and imageHeight as needed
            } else {
                // Handle the case where the drawable is not a BitmapDrawable
            }


            Toast.makeText(this, "the Size of the image is "+imageWidth+"*"+imageHeight, Toast.LENGTH_LONG).show();
        }

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Scan cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                qr_result.setText(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    private Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap;

        if (drawable instanceof BitmapDrawable) {
            bitmap = ((BitmapDrawable) drawable).getBitmap();
        } else {
            int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();

            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        }

        return bitmap;
    }

    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.CAMERA},
                    PERMISSION_REQUEST_CAMERA);
        }
    }

    private void initQRCodeScanner() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setOrientationLocked(true);
        integrator.setPrompt("Scan a QR code or barcode");
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.initiateScan();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initQRCodeScanner();
            } else {
                Toast.makeText(this, "Camera permission is required", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }
}