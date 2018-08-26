package com.plivo.sampleplivo.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import com.plivo.sampleplivo.R;

public class CallButton extends AppCompatButton {

    public enum STATE {
        NA, // disabled state
        IDLE, // can call state
        ON_CALL
    }

    public interface OnStateChangeListener {
        void onStateChanged(STATE state);
    }

    private STATE btnState;

    private OnStateChangeListener listener;

    public CallButton(Context context) {
        super(context);
    }

    public CallButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CallButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CallState, 0, 0);
        setState(STATE.values()[a.getInt(R.styleable.CallState_state_call, 0)]);
    }

    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        if (btnState != STATE.NA) {
            final int[] drawableStates = super.onCreateDrawableState(extraSpace + 1);
            mergeDrawableStates(drawableStates, new int[]{R.attr.state_call});
            return drawableStates;
        } else {
            return super.onCreateDrawableState(extraSpace);
        }
    }

    public void setOnStateChangeListener(OnStateChangeListener listener) {
        this.listener = listener;
    }

    public void setState(STATE state) {
        btnState = state;
        if (listener != null) {
            listener.onStateChanged(btnState);
        }
        refreshDrawableState();
    }

    public STATE getState() {
        return btnState;
    }
}
