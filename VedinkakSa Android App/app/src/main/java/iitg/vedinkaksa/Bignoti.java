package iitg.vedinkaksa;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Bignoti  extends AppCompatActivity {
    String Info;
    TextView logger;
    //can be send as parameter for future purpose
    // or simply ,make a variable in constants.java and change its value as per the need of the messgae in the app

    String msg="This is the big notification . This can be seen on fullscreen only" +
            "It contains sensitie info       helllpp" +
            "joeoee " +
            "jsiajiajiajia";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bignoti);
        Info = msg;
        logger = findViewById(R.id.logger);
        logger.setText(Info);


    }
}
