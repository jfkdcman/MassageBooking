package com.example.massagebooker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.Calendar;

import static android.widget.Toast.makeText;


public class BottomBarFragment extends Fragment{
    public static final String ARG_TITLE = "arg_title";
    public static final String ARG_POSITION = "arg_position";
    private TextView textView;
    Button finalBookButtonId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int layout_id = 0;
        String title = getArguments().getString(ARG_TITLE, "");
        int position = getArguments().getInt(ARG_POSITION);

        final View rootView;

        switch(position) {
            case 0: //Home
                layout_id = R.layout.home_fragment;
                rootView = inflater.inflate(layout_id, container, false);
                LinearLayout llayout_noBookingLinearLayoutId = (LinearLayout) rootView.findViewById(R.id.noBookingLinearLayoutId);
                LinearLayout llayout_hasBookingLinearLayoutId = (LinearLayout) rootView.findViewById(R.id.hasBookingLinearLayoutId);


                TextView bookingMassageTypeId = (TextView) rootView.findViewById(R.id.bookingMassageTypeId);
                TextView bookingDateAndTimeId = (TextView) rootView.findViewById(R.id.bookingDateAndTimeId);
                TextView bookingLocationId = (TextView) rootView.findViewById(R.id.bookingLocationId);

                if(MainActivity.BOOKING_STATUS) {
                    llayout_noBookingLinearLayoutId.setVisibility(View.GONE);
                    llayout_hasBookingLinearLayoutId.setVisibility(View.VISIBLE);
                    bookingMassageTypeId.setText(MainActivity.MASSAGE_TYPE);
                    bookingDateAndTimeId.setText(MainActivity.DATE +", "+MainActivity.TIME);
                    bookingLocationId.setText("123 Rizal St., Poblacion, Talisay City, Cebu");
                } else {
                    llayout_noBookingLinearLayoutId.setVisibility(View.VISIBLE);
                    llayout_hasBookingLinearLayoutId.setVisibility(View.GONE);
                }


                LinearLayout llayout_aromatherapyClickButtonId = (LinearLayout) rootView.findViewById(R.id.aromatherapyClickButtonId);

                llayout_aromatherapyClickButtonId.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MainActivity.CALL_SWITCH_FROM_FRAG_VALUE=6;
                        MainActivity.switchButtonId.callOnClick();
                    }
                });

                Button button_bookingButtonId = (Button) rootView.findViewById(R.id.bookingButtonId);
                final TextView textViewButtonClick = (TextView) rootView.findViewById(R.id.textViewButtonClick);
                button_bookingButtonId.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        textViewButtonClick.setText("Booking...");
                        MainActivity.CALL_SWITCH_FROM_FRAG_VALUE=4;
                        MainActivity.switchButtonId.callOnClick();
                    }
                });
                break;
            case 1: //History
                layout_id = R.layout.history_fragment;
                rootView = inflater.inflate(layout_id, container, false);
                break;
            case 2: //Announcement
                layout_id = R.layout.announcement_fragment;
                rootView = inflater.inflate(layout_id, container, false);
                break;
            case 3: //Account
                layout_id = R.layout.account_fragment;
                rootView = inflater.inflate(layout_id, container, false);
                break;
            case 4: //Booking
                layout_id = R.layout.booking_fragment;
                rootView = inflater.inflate(layout_id, container, false);

                //better spinner MASSAGE TYPES
                final MaterialBetterSpinner materialDesignSpinner_mtype = (MaterialBetterSpinner) rootView.findViewById(R.id.android_material_design_spinner);
                final String[] SPINNERLIST = {"Aromatherapy", "Swedish", "Deep Tissue", "Sports", "Shiatsu", "Thai", "Prenatal", "Couples"};

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(rootView.getContext(),
                        android.R.layout.simple_dropdown_item_1line, SPINNERLIST);
                materialDesignSpinner_mtype.setAdapter(arrayAdapter);

                materialDesignSpinner_mtype.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        String spinner_value= adapterView.getItemAtPosition(position).toString();
                        materialDesignSpinner_mtype.setSelection(position);
                        Toast.makeText(rootView.getContext(), ""+position, Toast.LENGTH_SHORT).show();
                    }
                });

                dateAndTimePicker(rootView);

                // DURATION OF MASSAGE
                final MaterialBetterSpinner materialDesignSpinner_duration = (MaterialBetterSpinner) rootView.findViewById(R.id.android_material_duration_spinner);
                final String[] SPINNERLIST_DURATION = {"1 hour", "1 hr and 30 minutes", "2 hours"};

                ArrayAdapter<String> arrayAdapter_duration = new ArrayAdapter<String>(rootView.getContext(),
                        android.R.layout.simple_dropdown_item_1line, SPINNERLIST_DURATION);
                materialDesignSpinner_duration.setAdapter(arrayAdapter_duration);

                materialDesignSpinner_duration.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        String spinner_value= adapterView.getItemAtPosition(position).toString();
                        materialDesignSpinner_duration.setSelection(position);
                    }
                });
                //END OF DURATION OF MASSAGE

                // NOTE PICKER
                addNotePicker(rootView);

                finalBookButtonId = (Button) rootView.findViewById(R.id.finalBookButtonId);
                finalBookButtonId.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final TextView dateTextViewId=(TextView) rootView.findViewById(R.id.dateTextViewId);
                        final TextView timeTextViewId=(TextView) rootView.findViewById(R.id.timeTextViewId);
                        Toast.makeText(rootView.getContext(), ""+SPINNERLIST[materialDesignSpinner_mtype.getSelectionStart()]+" "+SPINNERLIST_DURATION[materialDesignSpinner_duration.getSelectionStart()], Toast.LENGTH_SHORT).show();
                        MainActivity.MASSAGE_TYPE = ""+SPINNERLIST[materialDesignSpinner_mtype.getSelectionStart()];
                        MainActivity.DATE = ""+dateTextViewId.getText();
                        MainActivity.TIME =""+timeTextViewId.getText();
                        MainActivity.CALL_SWITCH_FROM_FRAG_VALUE=5;
                        MainActivity.switchButtonId.callOnClick();
                    }
                });
                break;
            case 5: //Booking Loading Screen
                layout_id = R.layout.bookingloading_fragment;
                rootView = inflater.inflate(layout_id, container, false);
                if(!MainActivity.BOOKING_STATUS){
                    int secondsDelayed = 1;
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            //wala lang
                            MainActivity.BOOKING_STATUS = true;
                            MainActivity.CALL_SWITCH_FROM_FRAG_VALUE=0;
                            MainActivity.switchButtonId.callOnClick();
                        }
                    }, secondsDelayed * 2000);



                }

                break;
            case 6: //Booking Loading Screen
                layout_id = R.layout.massagetype_fragment;
                rootView = inflater.inflate(layout_id, container, false);

                Button button_massageTypeButtonId = (Button) rootView.findViewById(R.id.bookingButtonId);

                if(MainActivity.BOOKING_STATUS){
                    button_massageTypeButtonId.setText("You already have an upcoming booking");
                    button_massageTypeButtonId.setBackgroundColor(Color.GRAY);
                    button_massageTypeButtonId.setEnabled(false);
                } else {
                    button_massageTypeButtonId.setEnabled(true);

                }
                button_massageTypeButtonId.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MainActivity.CALL_SWITCH_FROM_FRAG_VALUE=4;
                        MainActivity.switchButtonId.callOnClick();
                    }
                });


                break;
            default:
                layout_id = R.layout.home_fragment;
                rootView = inflater.inflate(layout_id, container, false);
        }
        //rootView = inflater.inflate(layout_id, container, false);
        return rootView;
    }

    public String[] massageType(View rootView) {

        final String[] mt = {""};
        final MaterialBetterSpinner materialDesignSpinner = (MaterialBetterSpinner) rootView.findViewById(R.id.android_material_design_spinner);
        final String[] SPINNERLIST = {"Aromatherapy", "Swedish", "Deep Tissue", "Sports", "Shiatsu", "Thai", "Prenatal", "Couples"};

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(rootView.getContext(),
                android.R.layout.simple_dropdown_item_1line, SPINNERLIST);
        materialDesignSpinner.setAdapter(arrayAdapter);



        materialDesignSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mt[0] =""+adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //materialDesignSpinner.setSelection(1);
        return mt;
    }

    public void dateAndTimePicker(final View rootView) {
        ImageView btnDateAndTimePicker= (ImageView) rootView.findViewById(R.id.changeDateAndTimeButtonId);
        final TextView dateTextViewId=(TextView) rootView.findViewById(R.id.dateTextViewId);
        final TextView timeTextViewId=(TextView) rootView.findViewById(R.id.timeTextViewId);
        final String[] date = new String[1];
        btnDateAndTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(rootView.getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                String month = monthIntToStringConverter(monthOfYear);

                                dateTextViewId.setText(month +" "+dayOfMonth+ ", " + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

                datePickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        // Get Current Time
                        final Calendar ct = Calendar.getInstance();
                        int mHour = ct.get(Calendar.HOUR_OF_DAY);
                        int mMinute = ct.get(Calendar.MINUTE);

                        // Launch Time Picker Dialog
                        TimePickerDialog timePickerDialog = new TimePickerDialog(rootView.getContext(),
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay,
                                                          int minute) {

                                        timeTextViewId.setText(hourOfDay + ":" + minute);
                                    }
                                }, mHour, mMinute, false);
                        timePickerDialog.show();
                    }
                });
            }
        });
    }

    public String monthIntToStringConverter(int monthOfYear) {
        String month;
        switch (monthOfYear+1){
            case 1:
                month = "January";
                break;
            case 2:
                month = "February";
                break;
            case 3:
                month = "March";
                break;
            case 4:
                month = "April";
                break;
            case 5:
                month = "May";
                break;
            case 6:
                month = "June";
                break;
            case 7:
                month = "July";
                break;
            case 8:
                month = "August";
                break;
            case 9:
                month = "September";
                break;
            case 10:
                month = "October";
                break;
            case 11:
                month = "November";
                break;
            case 12:
                month = "December";
                break;
            default:
                month = "January";
        }
        return month;
    }

    public String durationPicker (View rootView) {


            final MaterialBetterSpinner materialDesignSpinner = (MaterialBetterSpinner) rootView.findViewById(R.id.android_material_duration_spinner);
            String[] SPINNERLIST = {"1 hour", "1 hr and 30 minutes", "2 hours"};

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(rootView.getContext(),
                    android.R.layout.simple_dropdown_item_1line, SPINNERLIST);
            materialDesignSpinner.setAdapter(arrayAdapter);

            materialDesignSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    String spinner_value= adapterView.getItemAtPosition(position).toString();
                    materialDesignSpinner.setSelection(position);
                }
            });

            //end better spinner
            return SPINNERLIST[materialDesignSpinner.getSelectionStart()];
    }

    String floorUnit;
    String additionalNote;
    public void addNotePicker(final View rootView) {
        ImageView editNoteButtonId = (ImageView) rootView.findViewById(R.id.editNoteButtonId);

        editNoteButtonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                final Dialog dialog = new Dialog(rootView.getContext());
                dialog.setContentView(LL);
                dialog.setTitle("Title");

                final EditText inputFloorUnit = (EditText) rootView.findViewById(R.id.inputFloorUnit);
                final EditText inputAdditionalNote = (EditText) rootView.findViewById(R.id.inputAdditionalNote);
                inputAdditionalNote.setInputType(InputType.TYPE_CLASS_TEXT);
                inputFloorUnit.setInputType(InputType.TYPE_CLASS_TEXT);

                dialog.show();
                */
            }
        });

    }

}