package com.example.finalmaps;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class PaymentGateway extends AppCompatActivity implements PaymentResultListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_gateway);

        findViewById(R.id.payButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment();
            }
        });
    };

    public void startPayment(){
        Checkout checkout = new Checkout();
        checkout.setImage(R.mipmap.ic_launcher);
        final Activity activity = this;

        try {
            JSONObject options = new JSONObject();
            options.put("name", R.string.app_name);
            options.put("description", "Payment for Anything");
            options.put("send_sms_hash", true);
            options.put("allow_rotation", false);

            options.put("currency", "INR");
            options.put("amount", "100");       // 100 means 1 rupee, if anything needds to be written, then multiply with 100

            JSONObject preFill = new JSONObject();
            preFill.put("email", "email");
            preFill.put("contact", "phone");

            options.put("prefill", preFill);

            checkout.open(activity, options);
        }
        catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }


    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "Payment Successful" + s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Payment Error" + s, Toast.LENGTH_SHORT).show();
    }
}
