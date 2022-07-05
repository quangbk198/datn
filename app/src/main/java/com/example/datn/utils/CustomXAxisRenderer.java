package com.example.datn.utils;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.renderer.XAxisRenderer;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class CustomXAxisRenderer extends XAxisRenderer {
    public CustomXAxisRenderer(ViewPortHandler viewPortHandler, XAxis xAxis, Transformer trans) {
        super(viewPortHandler, xAxis, trans);
    }

    @Override
    protected void drawLabel(Canvas c, String formattedLabel, float x, float y, MPPointF anchor, float angleDegrees) {
        float labelHeight = mXAxis.getTextSize();
        float labelInterval = 5f;

        //split value
        String[] labels = formattedLabel.split(" ");

        //line 1
        Paint mFirstLine = new Paint(Paint.ANTI_ALIAS_FLAG);
        mFirstLine.setColor(Color.WHITE);
        mFirstLine.setTextAlign(Paint.Align.LEFT);
        mFirstLine.setTextSize(Utils.convertDpToPixel(10f));
        mFirstLine.setTypeface(mXAxis.getTypeface());

        //line 2
        Paint secondLine = new Paint(Paint.ANTI_ALIAS_FLAG);
        secondLine.setColor(Color.WHITE);
        secondLine.setTextAlign(Paint.Align.LEFT);
        secondLine.setTextSize(Utils.convertDpToPixel(10f));
        secondLine.setTypeface(mXAxis.getTypeface());

        //setting line
        if ((labels.length > 1)) {
            Utils.drawXAxisValue(c, labels[0], x, y, mFirstLine, anchor, angleDegrees);
            Utils.drawXAxisValue(c, labels[1], x, y + labelHeight + labelInterval, secondLine, anchor, angleDegrees);
        } else {
            Utils.drawXAxisValue(c, formattedLabel, x, y, mFirstLine, anchor, angleDegrees);
        }

    }
}
