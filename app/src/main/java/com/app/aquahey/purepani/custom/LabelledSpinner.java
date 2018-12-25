package com.app.aquahey.purepani.custom;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.ArrayRes;
import android.support.annotation.ColorRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;


import com.app.aquahey.purepani.R;

import java.util.List;

/**
 * Created by ikram-tiss on 13/12/17.
 */

public class LabelledSpinner extends LinearLayout implements AdapterView.OnItemSelectedListener {

    /**
     * The label positioned above the Spinner, similar to the floating
     * label from a {@code android.support.design.widget.TextInputLayout}.
     */
    private TextView mLabel;

    /**
     * The Spinner widget used in this layout.
     */
    private Spinner mSpinner;

    /**
     * A thin (1dp thick) divider line positioned below the Spinner,
     * similar to the bottom line in an {@link android.widget.EditText}.
     */
    private View mDivider;

    /**
     * A label positioned below the Spinner and thin divider line,
     * which displays error texts. This is similar to a
     * {@code android.support.design.widget.TextInputLayout}).
     */
    private TextView mErrorLabel;

    /**
     * The listener that receives notifications when an item in the
     * AdapterView is selected.
     */
    private OnItemChosenListener mOnItemChosenListener;

    /**
     * The main color used in the widget (the label color and divider
     * color). This may be updated when XML attributes are obtained and
     * again if the color is set programmatically.
     */
    private int mWidgetColor;

    /**
     * The error text that is displayed for when the first item (e.g.
     * a prompt) is selected.
     */
    private CharSequence mDefaultErrorText;

    /**
     * Whether or not the 'default error' is shown. This is the error
     * when the first item, such as a prompt, is selected.
     */
    private boolean mDefaultErrorEnabled;


    public LabelledSpinner(Context context) {
        this(context, null);
    }

    public LabelledSpinner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LabelledSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeLayout(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LabelledSpinner(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initializeLayout(context, attrs);
    }

    /**
     * Sets up views and widget attributes
     *
     * @param context Context passed from constructor
     * @param attrs   AttributeSet passed from constructor
     */
    private void initializeLayout(Context context, AttributeSet attrs) {
        prepareLayout(context);

        mLabel = (TextView) getChildAt(0);
        mSpinner = (Spinner) getChildAt(1);
        mDivider = getChildAt(2);
        mErrorLabel = (TextView) getChildAt(3);

        final TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.LabelledSpinner, 0, 0);

        String labelText = a.getString(R.styleable.LabelledSpinner_labelText);
        mWidgetColor = a.getColor(R.styleable.LabelledSpinner_widgetColor,
                ContextCompat.getColor(context, R.color.colorPrimary));

        mLabel.setText(labelText);
        mLabel.setPadding(0, dpToPixels(16), 0, 0);
        mSpinner.setPadding(0, dpToPixels(8), 0, dpToPixels(8));
        mSpinner.setOnItemSelectedListener(this);

        MarginLayoutParams dividerParams = (MarginLayoutParams) mDivider.getLayoutParams();
        dividerParams.rightMargin = dpToPixels(4);
        dividerParams.bottomMargin = dpToPixels(8);
        mDivider.setLayoutParams(dividerParams);

        mLabel.setTextColor(mWidgetColor);
        mDivider.setBackgroundColor(mWidgetColor);

        alignLabelWithSpinnerItem(4);

        final CharSequence[] entries = a.getTextArray(R.styleable.LabelledSpinner_spinnerEntries);
        if (entries != null) {
            setItemsArray(entries);
        }

        mDefaultErrorEnabled =
                a.getBoolean(R.styleable.LabelledSpinner_defaultErrorEnabled, false);
        mDefaultErrorText = "";
        a.recycle();
    }

    /**
     * Inflates the layout and sets layout parameters
     */
    private void prepareLayout(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.widget_labelled_spinner, this, true);

        setOrientation(LinearLayout.VERTICAL);
        setLayoutParams(new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    /**
     * @return the label (a {@link TextView}) from this
     * compound view
     */
    public TextView getLabel() {
        return mLabel;
    }

    /**
     * @return the {@link Spinner} widget from this compound
     * view
     */
    public Spinner getSpinner() {
        return mSpinner;
    }

    /**
     * @return the divider line {@link View} underneath the
     * Spinner
     */
    public View getDivider() {
        return mDivider;
    }

    /**
     * @return the error label (a {@link TextView}) from this
     * compound view, which displays error texts
     */
    public TextView getErrorLabel() {
        return mErrorLabel;
    }

    /**
     * Sets the text the label is to display.
     *
     * @param labelText The CharSequence value to be displayed on the label.
     * @attr ref R.styleable#LabelledSpinner_labelText
     * @see #setLabelText(int)
     * @see #getLabelText()
     */
    public void setLabelText(CharSequence labelText) {
        mLabel.setText(labelText);
    }

    /**
     * @return the text shown on the floating label
     */
    public CharSequence getLabelText() {
        return mLabel.getText();
    }

    /**
     * Sets the text the label is to display.
     *
     * @param labelTextId The string resource identifier which refers to
     *                    the string value which is to be displayed on
     *                    the label.
     * @attr ref R.styleable#LabelledSpinner_labelText
     * @see #setLabelText(CharSequence)
     * @see #getLabelText()
     */
    public void setLabelText(@StringRes int labelTextId) {
        mLabel.setText(getResources().getString(labelTextId));
    }

    /**
     * @return the color used as the label and divider line
     */
    public int getColor() {
        return mWidgetColor;
    }

    /**
     * Sets the color to use for the label text and the divider line
     * underneath.
     *
     * @param colorRes The color resource identifier which refers to the
     *                 color that is to be displayed on the widget.
     * @attr ref R.styleable#LabelledSpinner_widgetColor
     * @see #getColor()
     */
    public void setColor(@ColorRes int colorRes) {
        mWidgetColor = ContextCompat.getColor(getContext(), colorRes);
        mLabel.setTextColor(mWidgetColor);
        mDivider.setBackgroundColor(mWidgetColor);
    }

    /**
     * @return the error text that is displayed when the first item of
     * the Spinner (e.g. a prompt) is selected
     */
    public CharSequence getDefaultErrorText() {
        return mDefaultErrorText;
    }

    /**
     * Sets the error text that is displayed when the first item of the
     * Spinner (e.g. a prompt such as 'Select an item...') is selected.
     * <p>
     * This will only have an effect if the default error option is
     * enabled.
     *
     * @param error The error text to be displayed.
     * @see #getDefaultErrorText()
     * @see #setDefaultErrorEnabled(boolean)
     */
    public void setDefaultErrorText(CharSequence error) {
        mDefaultErrorText = error;
    }

    /**
     * Sets a boolean deciding whether the default error can be
     * displayed (this is where an error is shown if the first item of
     * the Spinner, such as a prompt, is selected).
     *
     * @param enabled Whether or not the default error is enabled.
     * @attr ref R.styleable#LabelledSpinner_defaultErrorEnabled
     */
    public void setDefaultErrorEnabled(boolean enabled) {
        mDefaultErrorEnabled = enabled;
    }

    /**
     * Sets the array of items to be used in the Spinner.
     *
     * @param arrayResId The identifier of the array to use as the data
     *                   source (e.g. {@code R.array.myArray})
     * @attr ref R.styleable#LabelledSpinner_spinnerEntries
     * @see #setItemsArray(int, int, int)
     * @see #setItemsArray(CharSequence[])
     * @see #setItemsArray(CharSequence[], int, int)
     * @see #setItemsArray(List)
     * @see #setItemsArray(List, int, int)
     */
    public void setItemsArray(@ArrayRes int arrayResId) {
        setItemsArray(arrayResId, android.R.layout.simple_spinner_item,
                android.R.layout.simple_spinner_dropdown_item);
    }

    /**
     * Sets the array of items to be used in the Spinner.
     *
     * @param arrayResId      The identifier of the array to use as the data
     *                        source (e.g. {@code R.array.myArray})
     * @param spinnerItemRes  The identifier of the layout used to create
     *                        views (e.g. {@code R.layout.my_item})
     * @param dropdownViewRes The layout resource to create the drop down
     *                        views (e.g. {@code R.layout.my_dropdown})
     * @attr ref R.styleable#LabelledSpinner_spinnerEntries
     * @see #setItemsArray(int)
     * @see #setItemsArray(CharSequence[])
     * @see #setItemsArray(CharSequence[], int, int)
     * @see #setItemsArray(List)
     * @see #setItemsArray(List, int, int)
     */
    public void setItemsArray(@ArrayRes int arrayResId, @LayoutRes int spinnerItemRes,
                              @LayoutRes int dropdownViewRes) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(),
                arrayResId,
                spinnerItemRes);
        adapter.setDropDownViewResource(dropdownViewRes);
        mSpinner.setAdapter(adapter);
    }

    /**
     * Sets the array of items to be used in the Spinner.
     *
     * @param itemsArray The array used as the data source
     * @attr ref R.styleable#LabelledSpinner_spinnerEntries
     * @see #setItemsArray(int)
     * @see #setItemsArray(int, int, int)
     * @see #setItemsArray(CharSequence[], int, int)
     * @see #setItemsArray(List)
     * @see #setItemsArray(List, int, int)
     */
    public void setItemsArray(CharSequence[] itemsArray) {
        setItemsArray(itemsArray, android.R.layout.simple_spinner_item,
                android.R.layout.simple_spinner_dropdown_item);
    }

    /**
     * Sets the array of items to be used in the Spinner.
     *
     * @param itemsArray      The array used as the data source
     * @param spinnerItemRes  The identifier of the layout used to create
     *                        views (e.g. {@code R.layout.my_item})
     * @param dropdownViewRes The layout resource to create the drop down
     *                        views (e.g. {@code R.layout.my_dropdown})
     * @attr ref R.styleable#LabelledSpinner_spinnerEntries
     * @see #setItemsArray(int)
     * @see #setItemsArray(int, int, int)
     * @see #setItemsArray(CharSequence[])
     * @see #setItemsArray(List)
     * @see #setItemsArray(List, int, int)
     */
    public void setItemsArray(CharSequence[] itemsArray, @LayoutRes int spinnerItemRes,
                              @LayoutRes int dropdownViewRes) {
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(
                getContext(),
                spinnerItemRes,
                itemsArray);
        adapter.setDropDownViewResource(dropdownViewRes);
        mSpinner.setAdapter(adapter);
    }

    /**
     * Sets the array of items to be used in the Spinner.
     *
     * @param list The List used as the data source
     * @attr ref R.styleable#LabelledSpinner_spinnerEntries
     * @see #setItemsArray(int)
     * @see #setItemsArray(int, int, int)
     * @see #setItemsArray(CharSequence[])
     * @see #setItemsArray(CharSequence[], int, int)
     * @see #setItemsArray(List, int, int)
     */
    public void setItemsArray(List<?> list) {
        setItemsArray(list, android.R.layout.simple_spinner_item,
                android.R.layout.simple_spinner_dropdown_item);
    }

    /**
     * Sets the array of items to be used in the Spinner.
     *
     * @param list            The list to be used as the data source.
     * @param spinnerItemRes  The identifier of the layout used to create
     *                        views (e.g. {@code R.layout.my_item})
     * @param dropdownViewRes The layout resource to create the drop down
     *                        views (e.g. {@code R.layout.my_dropdown})
     * @attr ref R.styleable#LabelledSpinner_spinnerEntries
     * @see #setItemsArray(int)
     * @see #setItemsArray(int, int, int)
     * @see #setItemsArray(CharSequence[])
     * @see #setItemsArray(CharSequence[], int, int)
     * @see #setItemsArray(List)
     */
    public void setItemsArray(List<?> list, @LayoutRes int spinnerItemRes,
                              @LayoutRes int dropdownViewRes) {
        ArrayAdapter<?> adapter = new ArrayAdapter<>(
                getContext(),
                spinnerItemRes,
                list);
        adapter.setDropDownViewResource(dropdownViewRes);
        mSpinner.setAdapter(adapter);
    }

    /**
     * Sets the Adapter used to provide the data for the Spinner.
     * This would be similar to setting an Adapter for a normal Spinner
     * component.
     *
     * @param adapter The Adapter which would provide data for the Spinner
     */
    public void setCustomAdapter(BaseAdapter adapter) {
        mSpinner.setAdapter(adapter);
    }

    /**
     * Sets the currently selected item.
     *
     * @param position Index (starting at 0) of the data item to be selected.
     */
    public void setSelection(int position) {
        mSpinner.setSelection(position);
    }

    /**
     * Sets the currently selected item.
     *
     * @param position Index (starting at 0) of the data item to be selected.
     * @param animate  Whether or not the transition should be animated
     */
    public void setSelection(int position, boolean animate) {
        mSpinner.setSelection(position, animate);
    }

    /**
     * @return the interface which handles selection of items in the Spinner
     */
    public OnItemChosenListener getOnItemChosenListener() {
        return mOnItemChosenListener;
    }

    /**
     * Register a callback to be invoked when an item in this AdapterView has
     * been selected.
     * This would be similar to setting an OnItemSelectedListener for a normal
     * Spinner component.
     *
     * @param onItemChosenListener The callback that will run
     */
    public void setOnItemChosenListener(OnItemChosenListener onItemChosenListener) {
        mOnItemChosenListener = onItemChosenListener;
    }

    /**
     * Implemented method from {@link AdapterView.OnItemSelectedListener}
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (mOnItemChosenListener != null) {
            if (mDefaultErrorEnabled) {
                if (position == 0) {
                    // If the first item is selected (e.g. a prompt), the error is shown
                    mErrorLabel.setText(mDefaultErrorText);
                    mDivider.setBackgroundColor(ContextCompat.getColor(
                            getContext(), R.color.colorPrimaryDark));
                } else {
                    mErrorLabel.setText(" ");
                    mDivider.setBackgroundColor(mWidgetColor);
                }
            }

            // 'this' refers to this LabelledSpinner component
            mOnItemChosenListener.onItemChosen(this, parent, view, position, id);
        }
    }

    /**
     * Implemented method from {@link AdapterView.OnItemSelectedListener}
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        if (mOnItemChosenListener != null) {
            // 'this' refers to this LabelledSpinner component
            mOnItemChosenListener.onNothingChosen(this, parent);
        }
    }

    /**
     * Adds a 4dp left margin to the label and divider line underneath so that
     * it aligns with the Spinner item text. By default, the additional 4dp
     * margin will not be added.
     * <p>
     * Note: By default, however, a 4dp margin will be added so that the label
     * and divider align correctly with other UI components, such as the label
     * in a {@code android.support.design.widget.TextInputLayout}. This means
     * that if {@code indentLabel} is true, an 8dp left margin will be added
     * (this would be the 4dp margin to align with other UI components with
     * an additional 4dp margin to align the label with the Spinner item text.
     * Also note that if {@code indentLabel} is true, the label and divider
     * will not be aligned with other UI components as they would be 4dp
     * further right from them.
     * </p>
     *
     * @param indentLabel Whether or not the label will be indented
     * @see #alignLabelWithSpinnerItem(int)
     */
    public void alignLabelWithSpinnerItem(boolean indentLabel) {
        if (indentLabel) {
            alignLabelWithSpinnerItem(8);
        } else {
            alignLabelWithSpinnerItem(4);
        }
    }

    /**
     * A helper method responsible for adding left margins to the label and
     * divider line underneath, used to align these to the start of the Spinner
     * item text.
     *
     * @param indentDps The density-independent pixel value for the left margin
     * @see #alignLabelWithSpinnerItem(boolean)
     */
    private void alignLabelWithSpinnerItem(int indentDps) {
        MarginLayoutParams labelParams =
                (MarginLayoutParams) mLabel.getLayoutParams();
        labelParams.leftMargin = dpToPixels(indentDps);
        mLabel.setLayoutParams(labelParams);
        mErrorLabel.setLayoutParams(labelParams);

        MarginLayoutParams dividerParams =
                (MarginLayoutParams) mDivider.getLayoutParams();
        dividerParams.leftMargin = dpToPixels(indentDps);
        mDivider.setLayoutParams(dividerParams);
    }

    /**
     * A helper method responsible for the conversion of dp/dip (density-independent
     * pixel) values to pixels, so that they can be used when setting layout
     * parameters such as margins.
     *
     * @param dps The density-independent pixel value
     * @return The pixel value from the conversion
     */
    private int dpToPixels(int dps) {
        if (dps == 0) {
            return 0;
        }
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dps * scale + 0.5f);
    }


    /**
     * Interface definition for a callback to be invoked when an item in this
     * LabelledSpinner's Spinner view has been selected.
     */
    public interface OnItemChosenListener {

        /**
         * Callback method to be invoked when an item in this LabelledSpinner's
         * spinner view has been selected. This callback is invoked only when
         * the newly selected position is different from the previously selected
         * position or if there was no selected item.
         *
         * @param labelledSpinner The LabelledSpinner where the selection
         *                        happened. This view contains the AdapterView.
         * @param adapterView     The AdapterView where the selection happened. Note
         *                        that this AdapterView is part of the LabelledSpinner
         *                        component.
         * @param itemView        The view within the AdapterView that was clicked.
         * @param position        The position of the view in the adapter.
         * @param id              The row id of the item that is selected.
         */
        void onItemChosen(View labelledSpinner, AdapterView<?> adapterView, View itemView,
                          int position, long id);

        /**
         * Callback method to be invoked when the selection disappears from this
         * view. The selection can disappear for instance when touch is activated
         * or when the adapter becomes empty.
         *
         * @param labelledSpinner The LabelledSpinner view that contains the
         *                        AdapterView.
         * @param adapterView     The AdapterView that now contains no selected item.
         */
        void onNothingChosen(View labelledSpinner, AdapterView<?> adapterView);
    }
}