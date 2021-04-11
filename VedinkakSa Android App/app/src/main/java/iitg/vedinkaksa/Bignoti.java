package iitg.vedinkaksa;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static iitg.vedinkaksa.Constants.big_message;

public class Bignoti extends AppCompatActivity {
    String Info;
    TextView logger;
    //can be send as parameter for future purpose
    // or simply ,make a variable in constants.java and change its value as per the need of the messgae in the app



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bignoti);
        Info = big_message;
        logger = findViewById(R.id.logger);
        logger.setText(Info);


    }
}
