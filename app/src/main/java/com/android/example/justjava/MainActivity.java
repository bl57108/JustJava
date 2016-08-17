package com.android.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     *
     * This method is called when the order button is clicked.
     *
     */
    public void submitOrder(View view) {
        //Find the user's name
        EditText nameFeild = (EditText) findViewById(R.id.name_feild);
        String name = nameFeild.getText().toString();
        //Log.v("Mainactivity", "Name: " + name);

        //Figure out if the user wants whipped cream
        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
           boolean hasWhippedCream = whippedCreamCheckbox.isChecked();

        //Figure out if the user wants chocolate
        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
            boolean hasChocolate = chocolateCheckbox.isChecked();
        //Log.v("MainActivity", "has whipped cream" + hasWhippedCream);

        //List of items that are passed through to the calculatePrice method
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(name, price, hasWhippedCream, hasChocolate);
        displayMessage(priceMessage);

        //Send entent to email app to submit order
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));//this only allows email to handle this
        intent.putExtra(Intent.EXTRA_EMAIL, "bl57108@gmail.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
         displayMessage(priceMessage);
//        startActivity(Intent.createChooser(intent, "Send Email"));

    }

    /**
     * THis method calculates the price of the order.
     *
     * @return total price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int basePrice = 5;

        //Add $1 if the user wants whipped cream
        if (addWhippedCream) {
            basePrice = basePrice + 1 ;
            }

        //Add $2 if the user wants chocolate
        if (addChocolate) {
            basePrice = basePrice +2 ;
        }

        //Calculate the total order price by multiplying by quantity
        return quantity * basePrice;
    }

    /**
     *
     * Create summary of the order.
     *
     * @param price of the order
     * @param addWhippedCream is whether or not the user wants whipped cream
     * @param addChocolate is whether or not the user wants chocolate
     * @return text summary
     */
    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate){
        String priceMessage = getString(R.string.order_summary_name, name);
        priceMessage += "\nAdd whipped cream? " + addWhippedCream;
        priceMessage += "\nAdd chocolate? " + addChocolate;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: $" + price;
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(message);
    }
    /**
     * This method is called when the plus button is clicked
     */
    public void increment(View view) {
        if (quantity == 100) {

            //Show a error toast message if over 100 coffees
            Toast.makeText(this, "You cannot have more than 100 coffes", Toast.LENGTH_SHORT).show();

            //Exit this method early because there is nothing left to do
            return;
        }
        quantity = quantity + 1;
        display(quantity);
    }


    /**
     * This method is called when the minus button is clicked
     */
    public void decrement(View view) {
        if (quantity == 1) {

            //Show a error toast message if under 0 coffees
            Toast.makeText(this, "You cannot have less than 0 coffes", Toast.LENGTH_SHORT).show();

            //Exit this method early because there is nothing left to do
            return;
        }
        quantity = quantity - 1;
        display(quantity);
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }
}
