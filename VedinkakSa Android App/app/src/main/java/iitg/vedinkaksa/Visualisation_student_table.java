package iitg.vedinkaksa;

/*
Coded by - Abhishek Kumar
* This code is a simple getter and setter.
 */
public class Visualisation_student_table {

    int id;
    String State_in;
    String image_url;
    int PosX;
    int PosY;

    public int getPosX() {
        return PosX;
    }

    public void setPosX(int posX) {
        PosX = posX;
    }

    public int getPosY() {
        return PosY;
    }

    public void setPosY(int posY) {
        PosY = posY;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getState_in() {
        return State_in;
    }

    public void setState_in(String state_in) {
        State_in = state_in;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}


