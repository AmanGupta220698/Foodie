package com.aman.androidfoodie;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aman.androidfoodie.Common.Common;
import com.aman.androidfoodie.Database.Database;
import com.aman.androidfoodie.Model.Order;
import com.aman.androidfoodie.Model.Request;
import com.aman.androidfoodie.ViewHolder.CartAdaptor;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileOutputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Cart extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    
    FirebaseDatabase database;
    DatabaseReference requests;
ImageView qrImage;
Button btnPlace,btnQr,btnDownload;
    TextView txtTotalPrice;
    List<Order> cart=new ArrayList<>();
    CartAdaptor adaptor;
    String status="0";
    private static final  String Channel_ID="simplified_coding";
    private static final  String Channel_NAME="Simplified Coding";
    private static final  String Channel_DESC="Simplified Coding Notification";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(Channel_ID,Channel_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(Channel_DESC);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        //init Firebase
        database=FirebaseDatabase.getInstance();
        requests=database.getReference("Requests");
        
        //init
        recyclerView=findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        
        txtTotalPrice=findViewById(R.id.total);
        btnPlace=findViewById(R.id.btnPlaceOrder);
        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cart.size()>0)
                showAlertDialog();
                else
                    Toast.makeText(Cart.this, "Your Cart is Empty", Toast.LENGTH_SHORT).show();
            }
        });
        
        loadListFood();
    }

    private void showAlertDialog() {
        final AlertDialog.Builder showDialog=new AlertDialog.Builder(Cart.this);
        showDialog.setTitle("One More Step");
        showDialog.setMessage("Enter Your Valid Address");

        final EditText  edtAddress=new EditText(Cart.this);
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        edtAddress.setLayoutParams(lp);
        showDialog.setView(edtAddress);
        showDialog.setCancelable(false);
        showDialog.setIcon(R.drawable.ic_action_cart);
        showDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //create new request
                Request request=new Request(
                        Common.currentUser.getPhone(),

                        Common.currentUser.getName(),
                        edtAddress.getText().toString(),

                        txtTotalPrice.getText().toString(),
                        cart
                );
                //submit to firebase
                requests.child(String.valueOf(System.currentTimeMillis())).setValue(request);

                new Database(getBaseContext()).cleanCart();
                displayNotification();
                showDialog1(Common.currentUser.getPhone());
                Toast.makeText(Cart.this, "Thank you For order", Toast.LENGTH_SHORT).show();

        }
        });
        showDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        showDialog.show();
    }

    private void showDialog1(final String phone) {
        final String phone1=phone;

            AlertDialog.Builder alertDialog=new AlertDialog.Builder(Cart.this);
            alertDialog.setTitle("Show The QrCode To Restaurant");
            alertDialog.setMessage("Kindly Take The Screenshot of Qr Code");

            LayoutInflater inflater=this.getLayoutInflater();
            View add_menu_layout=inflater.inflate(R.layout.qr_layout,null);
            qrImage=add_menu_layout.findViewById(R.id.qrcode);
            btnQr=add_menu_layout.findViewById(R.id.btnGenerate);

            btnQr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        MultiFormatWriter multiFormatWriter=new MultiFormatWriter();
                        BitMatrix bitMatrix = multiFormatWriter.encode(phone1, BarcodeFormat.QR_CODE,500,500);
                        BarcodeEncoder barcodeEncoder=new BarcodeEncoder();
                        Bitmap bitmap=barcodeEncoder.createBitmap(bitMatrix);
                        qrImage.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }
            });



            alertDialog.setView(add_menu_layout);
            alertDialog.setIcon(R.drawable.ic_action_cart);

            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    Toast.makeText(Cart.this, "Thank you For order", Toast.LENGTH_SHORT).show();

                    finish();



                }
            });

            alertDialog.setCancelable(false);
            alertDialog.show();

        }


    private void displayNotification(){
        NotificationCompat.Builder mBuilder=new NotificationCompat.Builder(this,Channel_ID)
                .setSmallIcon(R.drawable.ic_shopping_cart1_black_24dp)
                .setContentTitle("Order Update")
                .setContentText("Order is Successfully Placed")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat mNotificationMgr=NotificationManagerCompat.from(this);
        mNotificationMgr.notify(1,mBuilder.build());
    }
    private void loadListFood() {
        cart=new Database(this).getCarts();

        adaptor=new CartAdaptor(cart,this);
        adaptor.notifyDataSetChanged();
        recyclerView.setAdapter(adaptor);


        int total =0;
        for(Order order:cart)
            total+=(Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));
        Locale locale=new Locale("en","IN");
        NumberFormat fmt=NumberFormat.getCurrencyInstance(locale);
      txtTotalPrice.setText(fmt.format(total));
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
         if(item.getTitle().equals(Common.DELETE)){
            deleteCart(item.getOrder());
        }
        return super.onContextItemSelected(item);
    }

    private void deleteCart(int order) {
        cart.remove(order);
        new Database(this).cleanCart();

        for(Order item:cart){
            new Database(this).addToCart(item);
        }
        loadListFood();

    }



}
