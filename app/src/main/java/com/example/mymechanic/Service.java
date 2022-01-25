package com.example.mymechanic;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Service extends AppCompatActivity {

    EditText name,amount, date, mechanic, payment, sName;
    Button insert, update, delete, view;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        name = findViewById(R.id.name);
        amount = findViewById(R.id.amount);
        date = findViewById(R.id.date);
        mechanic = findViewById(R.id.mechanic);
        payment = findViewById(R.id.payment);
        sName = findViewById(R.id.sName);

        insert = findViewById(R.id.btnInsert);
        update = findViewById(R.id.btnUpdate);
        delete = findViewById(R.id.btnDelete);
        view = findViewById(R.id.btnView);

        DB = new DBHelper(this);

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameTXT = name.getText().toString();
                String amountTXT = amount.getText().toString();
                String dateTXT = date.getText().toString();
                String mechanicTXT = mechanic.getText().toString();
                String paymentTXT = payment.getText().toString();
                String sNameTXT = sName.getText().toString();

                Boolean checkinsertdata = DB.insertuserdata(nameTXT, amountTXT, dateTXT, mechanicTXT, paymentTXT,sNameTXT);
                if (checkinsertdata==true)
                    Toast.makeText(Service.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(Service.this, "New Entry Not Inserted", Toast.LENGTH_SHORT).show();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameTXT = name.getText().toString();
                String amountTXT = amount.getText().toString();
                String dateTXT = date.getText().toString();
                String mechanicTXT = mechanic.getText().toString();
                String paymentTXT = payment.getText().toString();
                String sNameTXT = sName.getText().toString();

                Boolean checkupdatedata = DB.updateuserdata(nameTXT, amountTXT, dateTXT, mechanicTXT, paymentTXT,sNameTXT);
                if (checkupdatedata==true)
                    Toast.makeText(Service.this, "Updated", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(Service.this, "Not Updated", Toast.LENGTH_SHORT).show();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameTXT = name.getText().toString();

                Boolean checkdeletedata = DB.deletedata(nameTXT);
                if (checkdeletedata==true)
                    Toast.makeText(Service.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(Service.this, "Not Deleted", Toast.LENGTH_SHORT).show();
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = DB.getdata();
                if (res.getCount()==0){
                    Toast.makeText(Service.this, "Entry Unavailable", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("Name:"+res.getString(0)+"\n");
                    buffer.append("Amount:"+res.getString(1)+"\n");
                    buffer.append("Date:"+res.getString(2)+"\n");
                    buffer.append("Mechanic:"+res.getString(3)+"\n");
                    buffer.append("Payment Method:"+res.getString(4)+"\n");
                    buffer.append("Service Name:"+res.getString(5)+"\n\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(Service.this);
                builder.setCancelable(true);
                builder.setTitle("Service Entries");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });
    }
}
