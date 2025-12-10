package com.example.bmi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText weightText, feetText, inchText;
    private FacadeBMIOperations facade;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        weightText = findViewById(R.id.edWeight);
        feetText = findViewById(R.id.edFeet);
        inchText = findViewById(R.id.edInch);
        Button calculateBtn = findViewById(R.id.btnEnter);
        
        facade = new FacadeBMIOperations(this);
        
        calculateBtn.setOnClickListener(v -> calculateBMI());
    }
    
    private void calculateBMI() {
        try {
            float weight = Float.parseFloat(weightText.getText().toString());
            float feet = Float.parseFloat(feetText.getText().toString());
            float inches = Float.parseFloat(inchText.getText().toString());
            
            CommandBMICommands.CommandInvoker invoker = new CommandBMICommands.CommandInvoker();
            CommandBMICommands.CalculateCommand cmd = 
                new CommandBMICommands.CalculateCommand(facade, weight, feet, inches);
            
            invoker.setCommand(cmd);
            invoker.execute();
            
            float bmi = invoker.getResult();
            Intent intent = new Intent(this, DataTable.class);
            intent.putExtra("bmi", String.format("%.2f", bmi));
            startActivity(intent);
            
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
