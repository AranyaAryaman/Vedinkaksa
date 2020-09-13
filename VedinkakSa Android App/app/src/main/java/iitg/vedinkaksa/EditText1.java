package iitg.vedinkaksa;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;

import static java.lang.Math.max;

// Custom editText class which captures all keyboard events and required values from the keyboard for the typing model
public class EditText1 extends EditText implements TextWatcher, View.OnFocusChangeListener {
    int len = 0;
    boolean hadFocus = false;
    Date initialTime;
    int maxLengthWithoutBks = 0;

    public EditText1(Context context) {
        super(context);
        setOnFocusChangeListener(this);
        initialTime = Calendar.getInstance().getTime();
    }

    public EditText1(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnFocusChangeListener(this);
        initialTime = Calendar.getInstance().getTime();
    }

    public EditText1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnFocusChangeListener(this);
        initialTime = Calendar.getInstance().getTime();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    // This function runs whenever any keyboard event occurs.
    // we are maintaining the maximum length of the word that has been typed so far without pressing the delete key.
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Constants.touch_count += 1;
        Log.i("KeyboardText", "char: " + s + " start: " + start + " before: " + before + " count: " + count);
        if (s.length() < maxLengthWithoutBks) {
            Constants.max_length = max(maxLengthWithoutBks, Constants.max_length);
            maxLengthWithoutBks = 0;
        } else {
            Constants.key_presses_without_bks += 1;
            maxLengthWithoutBks += 1;
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    // function is called whenever the user either taps on the textbox, or taps out of it.
    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        Log.i("KeyBoard", "OnFocusChange called");
        if (hasFocus) {
            Log.i("touchService", "changing isKeyboard to true");
            Constants.isKeyboardOn = true;
        } else {
            Log.i("typeService", "changing isKeyboard to false");
            Constants.isKeyboardOn = false;
        }
        if (!hadFocus && hasFocus) {
            hadFocus = true;
            initialTime = Calendar.getInstance().getTime();
        } else if (!hasFocus) {
            hadFocus = false;
            Date currentTime = Calendar.getInstance().getTime();
            updateTypingSpeed(currentTime);
//            service.startTouchService();
        }
    }

    // updates the global variable - typing speed, after every focus change
    public void updateTypingSpeed(Date currentTime) {
        long difference = (currentTime.getTime() - initialTime.getTime());
        int typingSpeed = (int) ((float) Constants.no_of_words * 1000 / difference);

        int prev_typing_speed = Constants.typ_spd;
        int prev_word_count = Constants.no_of_words;

        Constants.typ_spd = (prev_typing_speed * prev_word_count + typingSpeed) / (prev_word_count + 1);
        initialTime = currentTime;
        len = 0;

        Log.i("KeyboardText", "typing speed : " + Constants.typ_spd);
    }
}
