package com.efun.platform.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.widget.RadioButton;

import com.efun.core.tools.EfunResourceUtil;
import com.efun.platform.utils.Const;

public class MaterialRadioButton extends RadioButton {
    private int size;
    private int radios;
    private int borderWidth;
    private int dotMargin;
    private int paddingLeft;
    private int[] colors;
    public MaterialRadioButton(Context context) {
        super(context);
        init(null);
    }

    public MaterialRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MaterialRadioButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(size==0){
            size = getHeight()/2;
            radios = size/2;
            initDrawables();
        }
    }

    private void init(AttributeSet attrs) {

        if (attrs != null) {
        	int resId = attrs.getAttributeResourceValue(Const.namespace, "theme_color",  -2);
        	int colorsId = EfunResourceUtil.findArrayIdByName(getContext(), "efun_pf_blood");
    		if(resId!=-2){
    			colorsId = resId;
    		}
            colors = getResources().getIntArray(colorsId);
            resId = attrs.getAttributeResourceValue(Const.namespace, "border_width",  -2);
    		if(resId!=-2){
    			borderWidth = getResources().getDimensionPixelSize(resId);
    		}else{
    			borderWidth = 4;
    		}
    		
            resId = attrs.getAttributeResourceValue(Const.namespace, "dot_margin",  -2);
    		if(resId!=-2){
    			dotMargin = getResources().getDimensionPixelSize(resId);
    		}else{
    			dotMargin = 2;
    		}
            resId = attrs.getAttributeResourceValue(Const.namespace, "padding_left",  -2);
    		if(resId!=-2){
    			paddingLeft = getResources().getDimensionPixelSize(resId);
    		}else{
    			paddingLeft = 0;
    		}
        }
    }
    
    private int getColor(int index){
        return colors[index];
    }

    private void initDrawables(){
        // creating unchecked-enabled state drawable
        GradientDrawable uncheckedEnabled = new GradientDrawable();
        uncheckedEnabled.setCornerRadius(radios);
        uncheckedEnabled.setSize(size, size);
        uncheckedEnabled.setColor(Color.TRANSPARENT);
        uncheckedEnabled.setStroke(borderWidth, getColor(2));

        // creating checked-enabled state drawable
        GradientDrawable checkedOutside = new GradientDrawable();
        checkedOutside.setCornerRadius(size);
        checkedOutside.setSize(size, size);
        checkedOutside.setColor(Color.TRANSPARENT);
        checkedOutside.setStroke(borderWidth, getColor(2));

        PaintDrawable checkedCore = new PaintDrawable(getColor(2));
        checkedCore.setCornerRadius(size);
        checkedCore.setIntrinsicHeight(size);
        checkedCore.setIntrinsicWidth(size);
        InsetDrawable checkedInside = new InsetDrawable(checkedCore,
                borderWidth + dotMargin, borderWidth + dotMargin,
                borderWidth + dotMargin, borderWidth + dotMargin);

        Drawable[] checkedEnabledDrawable = {checkedOutside, checkedInside};
        LayerDrawable checkedEnabled = new LayerDrawable(checkedEnabledDrawable);

        // creating unchecked-enabled state drawable
        GradientDrawable uncheckedDisabled = new GradientDrawable();
        uncheckedDisabled.setCornerRadius(radios);
        uncheckedDisabled.setSize(size, size);
        uncheckedDisabled.setColor(Color.TRANSPARENT);
        uncheckedDisabled.setStroke(borderWidth, getColor(3));

        // creating checked-disabled state drawable
        GradientDrawable checkedOutsideDisabled = new GradientDrawable();
        checkedOutsideDisabled.setCornerRadius(radios);
        checkedOutsideDisabled.setSize(size, size);
        checkedOutsideDisabled.setColor(Color.TRANSPARENT);
        checkedOutsideDisabled.setStroke(borderWidth, getColor(3));

        PaintDrawable checkedCoreDisabled = new PaintDrawable(getColor(3));
        checkedCoreDisabled.setCornerRadius(radios);
        checkedCoreDisabled.setIntrinsicHeight(size);
        checkedCoreDisabled.setIntrinsicWidth(size);
        InsetDrawable checkedInsideDisabled = new InsetDrawable(checkedCoreDisabled,
                borderWidth + dotMargin, borderWidth + dotMargin,
                borderWidth + dotMargin, borderWidth + dotMargin);

        Drawable[] checkedDisabledDrawable = {checkedOutsideDisabled, checkedInsideDisabled};
        LayerDrawable checkedDisabled = new LayerDrawable(checkedDisabledDrawable);


        StateListDrawable states = new StateListDrawable();
        states.addState(new int[]{-android.R.attr.state_checked, android.R.attr.state_enabled}, uncheckedEnabled);
        states.addState(new int[]{android.R.attr.state_checked, android.R.attr.state_enabled}, checkedEnabled);
        states.addState(new int[]{-android.R.attr.state_checked, -android.R.attr.state_enabled}, uncheckedDisabled);
        states.addState(new int[]{android.R.attr.state_checked, -android.R.attr.state_enabled}, checkedDisabled);
        setButtonDrawable(states);

        // setting padding for avoiding text to be appear on icon
        if(paddingLeft == 0){
        	setPadding(size/4*5, 0, 0, 0);
        }else{
        	setPadding(paddingLeft, 0, 0, 0);
        }
        setTextColor(getColor(2));
    }


}
