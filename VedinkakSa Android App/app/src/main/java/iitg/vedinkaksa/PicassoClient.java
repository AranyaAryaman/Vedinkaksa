package iitg.vedinkaksa;
/*
 * Coded by - Abhishek Kumar
 * This class is putting the image and Bounding color according to the data on database into Image Based and Symbol Based.
 * */

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class PicassoClient {

    public static void downloadimage(Context c, String image_url, ImageView img, String State_in, int PosX, int PosY, RelativeLayout rl) {      //using picasso client for image and state_in

        if (Constants.sym == 0) {                                                                                               // FOR IMAGE BASED
            if (image_url != null && image_url.length() > 0) {                                          //for coloring in bounding box(on condition); state in which the students are
                if (State_in.toString().equals("DS1")) {
                    img.setBackgroundColor(Color.parseColor("#ff0000"));                     //red
                    Picasso.with(c).load(image_url.toString()).into(img);
                } else if (State_in.toString().equals("DS2")) {
                    img.setBackgroundColor(Color.parseColor("#eeff00"));                    //yellow
                    Picasso.with(c).load(image_url.toString()).into(img);
                } else if (State_in.toString().equals("DS3")) {
                    img.setBackgroundColor(Color.parseColor("#00ff00"));                    //green
                    Picasso.with(c).load(image_url.toString()).into(img);
                } else if (State_in.toString().equals("DS4")) {
                    img.setBackgroundColor(Color.parseColor("#0099ff"));                    //blue
                    Picasso.with(c).load(image_url.toString()).into(img);
                } else if (State_in.toString().equals("DS0")) {
                    img.setBackgroundColor(Color.parseColor("#ffffff"));                    //white
                    Picasso.with(c).load(image_url.toString()).into(img);
                } else {
                    Picasso.with(c).load(image_url.toString()).into(img);
                }
            } else {
                Picasso.with(c).load(R.drawable.like).into(img);                                          // for loading the default image, if no image found in database
            }


        } else if (Constants.sym == 1) {                                                                // FOR SYMBOL BASED
            if (image_url != null && image_url.length() > 0) {                                                //for coloring in bounding box(on condition); state in which the students are


                if (State_in.toString().equals("DS1")) {
                    img.setImageResource(R.drawable.one);                                                    //red 1
                } else if (State_in.toString().equals("DS2")) {
                    img.setImageResource(R.drawable.two);                                                //yellow 2
                } else if (State_in.toString().equals("DS3")) {
                    img.setImageResource(R.drawable.three);                                               //green 3
                } else if (State_in.toString().equals("DS4")) {
                    img.setImageResource(R.drawable.blue);                                                  //blue
                } else if (State_in.toString().equals("DS0")) {
                    img.setImageResource(R.drawable.ic_launcher_background);
                } else {
                    Picasso.with(c).load(image_url.toString()).into(img);
                }
            }
        }
    }
}