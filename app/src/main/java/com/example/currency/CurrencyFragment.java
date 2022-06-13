package com.example.currency;

import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class CurrencyFragment extends Fragment {
    // max value is accepted
    private final long MAX = (long)Math.pow(10,5)*Integer.MAX_VALUE;

    View view;
    Spinner fromSpinner;
    Spinner toSpinner;
    TextView fromTextView;
    TextView toTextView;
    String fromCurr;
    String toCurr;
    // currency rate converts to 1 dollar
    HashMap<String, Double> currencyRate;
    List<String> currArray;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_currency, container, false);

        // initialize for all views
        initViews();
        // initialize for currency rate hashmap
        initCurrRate();
        // init adapter for currency spinners
        initAdapter();

        // catch spinner selected
        spinnerSelected();
        // catch numbers clicked
        numberClicked();
        // catch backspace button clicked
        backspaceClicked();
        // catch 'CE' button clicked
        ceClicked();
        // catch textview change
        textViewChanged();
        // Inflate the layout for this fragment
        return view;
    }

    private void textViewChanged() {
        fromTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                double currency = Double.parseDouble(s.toString());
                calculate(currency, currencyRate.get(fromCurr), currencyRate.get(toCurr));
            }
        });
    }

    private void calculate(double currency, double fromExchangeRate, double toExchangeRate){
        double result = currency * fromExchangeRate / toExchangeRate;
        toTextView.setText(String.format("%.2f", result));
    }

    // handle when click on 'CE' button
    private void ceClicked() {
        view.findViewById(R.id.ceButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromTextView.setText("0");
            }
        });
    }

    // handle when clicked on backspace button
    private void backspaceClicked() {
        view.findViewById(R.id.backspaceButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder fromEditTextString = new StringBuilder(fromTextView.getText().toString());
                if (fromEditTextString.length() > 1) {
                    fromEditTextString.deleteCharAt(fromEditTextString.length()-1);
                    fromTextView.setText(fromEditTextString);
                } else if (fromEditTextString.length() == 1) {
                    fromEditTextString.setCharAt(0, '0');
                    fromTextView.setText(fromEditTextString);
                }
            }
        });
    }

    private void numberClicked() {
        view.findViewById(R.id._0Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendNumber('0');
            }
        });

        view.findViewById(R.id._1Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendNumber('1');
            }
        });

        view.findViewById(R.id._2Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendNumber('2');
            }
        });

        view.findViewById(R.id._3Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendNumber('3');
            }
        });

        view.findViewById(R.id._4Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendNumber('4');
            }
        });

        view.findViewById(R.id._5Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendNumber('5');
            }
        });

        view.findViewById(R.id._6Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendNumber('6');
            }
        });

        view.findViewById(R.id._7Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendNumber('7');
            }
        });

        view.findViewById(R.id._8Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendNumber('8');
            }
        });

        view.findViewById(R.id._9Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendNumber('9');
            }
        });
    }

    // handle after clicked on a number button
    private void appendNumber(char c) {
        String fromVal = fromTextView.getText().toString();
        if (fromVal.equals("0")) fromVal = "" + c;
        else fromVal += c;
        if(Long.parseLong(fromVal) <= MAX){
            fromTextView.setText(fromVal);
        } else {
            AlertDialog alertDialogBuilder = new AlertDialog.Builder(view.getContext()).create();
            alertDialogBuilder.setMessage("Cannot calculate with too large number!");
            alertDialogBuilder.show();
        }
    }

    private void spinnerSelected() {
        fromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fromCurr = currArray.get(position);
                if (!fromTextView.getText().toString().equals("0")) fromTextView.setText("0");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        toSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                toCurr = currArray.get(position);
                if (!fromTextView.getText().toString().equals("0")) fromTextView.setText("0");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initViews(){
        fromSpinner = view.findViewById(R.id.fromSpinner);
        toSpinner = view.findViewById(R.id.toSpinner);
        fromTextView = view.findViewById(R.id.fromTextView);
        toTextView = view.findViewById(R.id.toTextView);
    }

    private void initCurrRate(){
        currencyRate = new HashMap<>();
        currencyRate.put("United State - Dollar", 1.0);
        currencyRate.put("Europe - Euro", 1.0519);
        currencyRate.put("VietNam - VNDong", 0.00004315);
        currencyRate.put("Japan - Yen", 0.007439);
        currencyRate.put("Kore - Won", 0.0007817);
    }

    private void initAdapter(){
        currArray = new ArrayList<>();

        Set keySet = currencyRate.keySet();
        Iterator iterator = keySet.iterator();
        while(iterator.hasNext()) {
            currArray.add((String)iterator.next());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(),
                android.R.layout.simple_dropdown_item_1line, currArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromSpinner.setAdapter(adapter);
        toSpinner.setAdapter(adapter);
    }
}