package com.angarron.vframes.ui.view;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.Html;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.CharacterStyle;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.ToggleButton;

public class RichEditor extends EditText {
    
    private static final int SPAN_TYPE = Spannable.SPAN_EXCLUSIVE_INCLUSIVE;

    // Optional styling button references
    private Checkable boldToggle;
    private Checkable italicsToggle;

    public RichEditor(Context context) {
        super(context);
        initialize();
    }

    public RichEditor(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public RichEditor(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    private void initialize() {
        // Add TextWatcher that reacts to text changes and applies the selected
        // styles
        this.addTextChangedListener(new RichEditTextWatcher());
    }

    /**
     * When the user selects a section of the text, this method is used to
     * toggle the defined style on it. If the selected text already has the
     * style applied, we remove it, otherwise we apply it.
     *
     * @param style
     *            The styles that should be toggled on the selected text.
     */
    private void toggleStyle(int style) {

        Log.d("findme", "toggle style called with style: " + style);

        int selectionStart = this.getSelectionStart();
        int selectionEnd = this.getSelectionEnd();

        //Reverse if necessary. Not sure when this happens.
        if (selectionStart > selectionEnd) {
            int temp = selectionEnd;
            selectionEnd = selectionStart;
            selectionStart = temp;
        }

        //If a range has been selected...
        if (selectionEnd > selectionStart) {

            Spannable text = this.getText();
            boolean exists = false;

            StyleSpan[] styleSpans = text.getSpans(selectionStart, selectionEnd, StyleSpan.class);

            // If the selected text-part already has this style on it, then
            // we need to disable it
            for (StyleSpan styleSpan : styleSpans) {
                if (styleSpan.getStyle() == style) {

                    //If this span affects more than the selection, we need to split the span.
                    int styleStart = text.getSpanStart(styleSpan);
                    int styleEnd = text.getSpanEnd(styleSpan);

                    Log.d("findme", "applying style to selection: " + selectionStart + ", " + selectionEnd);

                    Range preSelectionRange = new Range(styleStart, selectionStart);
                    Range postSelectionRange = new Range(selectionEnd, styleEnd);

                    Log.d("findme", "preSelectionRange: " + preSelectionRange);
                    Log.d("findme", "postSelectionRange: " + postSelectionRange);

                    if (preSelectionRange.isValid()) {
                        text.setSpan(new StyleSpan(style), preSelectionRange.start, preSelectionRange.end,
                                SPAN_TYPE);
                    }

                    if (postSelectionRange.isValid()) {
                        text.setSpan(new StyleSpan(style), postSelectionRange.start, postSelectionRange.end,
                                SPAN_TYPE);
                    }

                    text.removeSpan(styleSpan);

                    //Since we removed the style from the selection, change the button's state.
                    uncheckButton(style);

                    exists = true;
                }
            }

            Log.d("findme", "style exists: " + exists);

            // Else we set style on it
            if (!exists) {
                Log.d("findme", "style did not exist, setting it on: " + new Range(selectionStart, selectionEnd));
                text.setSpan(new StyleSpan(style), selectionStart, selectionEnd, SPAN_TYPE);
                checkButton(style);
            }
        }
    }

    private void checkButton(int style) {
        switch(style) {
            case Typeface.BOLD:
                setChecked(boldToggle, true);
                break;
            case Typeface.ITALIC:
                setChecked(italicsToggle, true);
                break;
        }
    }

    private void setChecked(Checkable toggleButton, boolean b) {
        toggleButton.setChecked(b);
    }

    private void uncheckButton(int style) {
        switch(style) {
            case Typeface.BOLD:
                setChecked(boldToggle, false);
                break;
            case Typeface.ITALIC:
                setChecked(italicsToggle, false);
                break;
        }
    }

    /**
     * This method makes sure that the optional style toggle buttons update
     * their state correctly when the user moves the cursor around the EditText,
     * or when the user selects sections of the text.
     */
    @Override
    public void onSelectionChanged(int selStart, int selEnd) {
        boolean boldExists = false;
        boolean italicsExists = false;

        if (TextUtils.isEmpty(getText())) {
            clearFormatting();
            return;
        }

        // If the user only placed the cursor around
        if (selStart > 0 && selStart == selEnd) {
            CharacterStyle[] styleSpans = this.getText().getSpans(selStart - 1, selStart, CharacterStyle.class);

            for (CharacterStyle styleSpan : styleSpans) {
                if (styleSpan instanceof StyleSpan) {

                    Editable text = getText();
                    int spanStart = text.getSpanStart(styleSpan);
                    int spanEnd = text.getSpanEnd(styleSpan);
                    boolean spanIsValid = (spanEnd > spanStart);

                    if (spanIsValid) {
                        if (((StyleSpan) styleSpan).getStyle() == Typeface.BOLD) {
                            boldExists = true;
                        } else if (((StyleSpan) styleSpan).getStyle() == Typeface.ITALIC) {
                            italicsExists = true;
                        }
                    }
                }
            }
        }

        // Else if the user selected multiple characters
        else {
            CharacterStyle[] styleSpans = this.getText().getSpans(selStart, selEnd, CharacterStyle.class);

            for (CharacterStyle styleSpan : styleSpans) {
                if (styleSpan instanceof StyleSpan) {
                    if (((StyleSpan) styleSpan).getStyle() == Typeface.BOLD) {
                        if (this.getText().getSpanStart(styleSpan) <= selStart
                                && this.getText().getSpanEnd(styleSpan) >= selEnd) {
                            boldExists = true;
                        }
                    } else if (((StyleSpan) styleSpan).getStyle() == Typeface.ITALIC) {
                        if (this.getText().getSpanStart(styleSpan) <= selStart
                                && this.getText().getSpanEnd(styleSpan) >= selEnd) {
                            italicsExists = true;
                        }
                    }
                }
            }
        }

        // Display the format settings
        if (boldToggle != null) {
            if (boldExists) {
                setChecked(boldToggle, true);
                Log.d("findme", "setting bold checked");
            } else {
                setChecked(boldToggle, false);
            }
        }

        if (italicsToggle != null) {
            if (italicsExists) {
                setChecked(italicsToggle, true);
                Log.d("findme", "setting italics checked");
            }
            else {
                setChecked(italicsToggle, false);
            }
        }
    }

    // Style toggle button setters
    public void setBoldToggleButton(Checkable button) {
        boldToggle = button;

        ((View)boldToggle).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                toggleStyle(Typeface.BOLD);
            }
        });
    }

    public void setItalicsToggleButton(ToggleButton button) {
        italicsToggle = button;

        ((View)italicsToggle).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                toggleStyle(Typeface.ITALIC);
            }
        });
    }

    private class RichEditTextWatcher implements TextWatcher {
        @Override
        public void afterTextChanged(Editable editable) {

            if (TextUtils.isEmpty(editable)) {
                clearFormatting();
                return;
            }

            // Add style as the user types if a toggle button is enabled
            int position = Selection.getSelectionStart(RichEditor.this.getText());
            if (position < 0) {
                position = 0;
            }

            if (position > 0) {
                CharacterStyle[] appliedStyles = editable.getSpans(position - 1, position, CharacterStyle.class);

                StyleSpan currentBoldSpan = null;
                StyleSpan currentItalicSpan = null;

                // Look for possible styles already applied to the entered text
                for (CharacterStyle appliedStyle : appliedStyles) {
                    if (appliedStyle instanceof StyleSpan) {
                        if (((StyleSpan) appliedStyle).getStyle() == Typeface.BOLD) {
                            // Bold style found
                            currentBoldSpan = (StyleSpan) appliedStyle;
                        } else if (((StyleSpan) appliedStyle).getStyle() == Typeface.ITALIC) {
                            // Italic style found
                            currentItalicSpan = (StyleSpan) appliedStyle;
                        }
                    }
                }

                // Handle the bold style toggle button if it's present
                if (boldToggle != null) {
                    if (boldToggle.isChecked() && currentBoldSpan == null) {
                        // The user switched the bold style button on and the
                        // character doesn't have any bold
                        // style applied, so we start a new bold style span. The
                        // span is inclusive,
                        // so any new characters entered right after this one
                        // will automatically get this style.
                        editable.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), position - 1, position,
                                SPAN_TYPE);
                    } else if (!boldToggle.isChecked() && currentBoldSpan != null) {
                        // The user switched the bold style button off and the
                        // character has bold style applied.
                        // We need to remove the old bold style span, and define
                        // a new one that end 1 position right
                        // before the newly entered character.
                        int boldStart = editable.getSpanStart(currentBoldSpan);
                        int boldEnd = editable.getSpanEnd(currentBoldSpan);

                        editable.removeSpan(currentBoldSpan);
                        if (boldStart <= (position - 1)) {
                            editable.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), boldStart, position - 1,
                                    SPAN_TYPE);
                        }

                        // The old bold style span end after the current cursor
                        // position, so we need to define a
                        // second newly created style span too, which begins
                        // after the newly entered character and
                        // ends at the old span's ending position. So we split
                        // the span.
                        if (boldEnd > position) {
                            editable.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), position, boldEnd,
                                    SPAN_TYPE);
                        }
                    }
                }

                // Handle the italics style toggle button if it's present
                if (italicsToggle != null && italicsToggle.isChecked() && currentItalicSpan == null) {
                    editable.setSpan(new StyleSpan(android.graphics.Typeface.ITALIC), position - 1, position,
                            SPAN_TYPE);
                } else if (italicsToggle != null && !italicsToggle.isChecked() && currentItalicSpan != null) {
                    int italicStart = editable.getSpanStart(currentItalicSpan);
                    int italicEnd = editable.getSpanEnd(currentItalicSpan);

                    editable.removeSpan(currentItalicSpan);
                    if (italicStart <= (position - 1)) {
                        editable.setSpan(new StyleSpan(android.graphics.Typeface.ITALIC), italicStart, position - 1,
                                SPAN_TYPE);
                    }

                    // Split the span
                    if (italicEnd > position) {
                        editable.setSpan(new StyleSpan(android.graphics.Typeface.ITALIC), position, italicEnd,
                                SPAN_TYPE);
                    }
                }
            }
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // Unused
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Unused
        }
    }

    private void clearFormatting() {
        for (CharacterStyle appliedStyle : getText().getSpans(0, 0, CharacterStyle.class)) {
            getText().removeSpan(appliedStyle);
        }

        if (boldToggle != null) {
            setChecked(boldToggle, false);
        }

        if (italicsToggle != null) {
            setChecked(italicsToggle, false);
        }

    }

    private class Range {
        private int start;
        private int end;

        private Range(int start, int end) {
            this.start = start;
            this.end = end;
        }

        private boolean isValid() {
            return end > start;
        }

        @Override
        public String toString() {
            return "(" + start + "," + end + ")";
        }
    }
}