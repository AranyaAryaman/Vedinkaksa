package iitg.vedinkaksa;
/*
 * Coded by - Abhsihek Kuamr
 * This code will inflate the gridcells(imageview) with the images downloaded from database.
 * This code is also responsible for the getting the full screen gridview.
 * Binding the data downloaded into the PicassoClient.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class Visualisation_CustomAdapter extends BaseAdapter {

    Context c;
    ArrayList<Visualisation_student_table> student_dtb;                                                          // for all list operations
    RelativeLayout rl;
    LayoutInflater inflater;


    public Visualisation_CustomAdapter(Context c, ArrayList<Visualisation_student_table> student_dtb) {
        this.c = c;
        this.student_dtb = student_dtb;

        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }


    @Override
    public int getCount() {
        return student_dtb.size();
    }

    @Override
    public Object getItem(int position) {
        return student_dtb.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listitems, parent, false);


        }

        ImageView img = convertView.findViewById(R.id.imga);


        rl = convertView.findViewById(R.id.rl);
        rl.getLayoutParams().height = ((Constants.ht - (Constants.abb + Constants.st)) / Constants.rowss);
        rl.getLayoutParams().width = (Constants.wd / Constants.coll);


        final Visualisation_student_table visualisation_student_table = student_dtb.get(position);       //Bind Data


        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(c, "State = " + visualisation_student_table.getState_in() + "\n" + "Sitting Position =  " + visualisation_student_table.getPosX() + "," + visualisation_student_table.getPosY(), Toast.LENGTH_SHORT).show();
            }
        });


        //image,state
        PicassoClient.downloadimage(c, visualisation_student_table.getImage_url(), img, visualisation_student_table.getState_in(), visualisation_student_table.getPosX(), visualisation_student_table.getPosY(), rl); //for displaying image_url and State_in


        return convertView;
    }


}

