package com.supcon.mes.mbap.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.AppCompatImageView;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.supcon.mes.mbap.R;

public class CustomRoundTextImageView extends AppCompatImageView {
    private Paint paint;
    private int roundWidth = 5;
    private int roundHeight = 5;
    private Paint paint2;
    private final TextPaint mTextPaint = new TextPaint();

    private int mTextColor = Color.BLACK;
    private int mTextSize = 22;
    private int mTextPadding = 5;
    private String mTextString;

    public CustomRoundTextImageView(Context context, AttributeSet attrs,
                                    int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);

    }

    public CustomRoundTextImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);

    }

    public CustomRoundTextImageView(Context context) {
        super(context);
        init(context, null);
        //this.setBackgroundColor(0xFFFFFF);

    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = null;
            try {
                a = context.obtainStyledAttributes(attrs, R.styleable.CustomRoundTextImageView);
                roundWidth      = a.getDimensionPixelSize(R.styleable.CustomRoundTextImageView_round_width, roundWidth);
                roundHeight     = a.getDimensionPixelSize(R.styleable.CustomRoundTextImageView_round_height, roundHeight);
                mTextString     = a.getString(R.styleable.CustomRoundTextImageView_text);
                mTextColor      = a.getColor(R.styleable.CustomRoundTextImageView_text_color, Color.BLACK);
                mTextSize       = a.getDimensionPixelSize(R.styleable.CustomRoundTextImageView_round_text_size, 22);
                mTextPadding    = a.getDimensionPixelSize(R.styleable.CustomCircleTextImageView_citv_text_padding, 5);
            }
            catch (Exception e){

            }
            if (a != null)
                a.recycle();
        } else {
            float density = context.getResources().getDisplayMetrics().density;
            roundWidth = (int) (roundWidth * density);
            roundHeight = (int) (roundHeight * density);
        }
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

        paint2 = new Paint();
        paint2.setXfermode(null);

        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setTypeface(Typeface.create(new String(), Typeface.BOLD_ITALIC));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMeasureSpecMode=MeasureSpec.getMode(widthMeasureSpec);
        int widthMeasureSpecSize=MeasureSpec.getSize(widthMeasureSpec);
        int heightMeasureSpecMode=MeasureSpec.getMode(heightMeasureSpec);
        int heightMeasureSpecSize=MeasureSpec.getSize(heightMeasureSpec);

        if(!TextUtils.isEmpty(mTextString))
        {
            int textMeasuredSize= (int) (mTextPaint.measureText(mTextString));
            textMeasuredSize+=2*mTextPadding;
            if(widthMeasureSpecMode==MeasureSpec.AT_MOST&&heightMeasureSpecMode==MeasureSpec.AT_MOST)
            {
                if(textMeasuredSize>getMeasuredWidth()||textMeasuredSize>getMeasuredHeight())
                {
                    setMeasuredDimension(textMeasuredSize,textMeasuredSize);
                }
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas2 = new Canvas(bitmap);
        super.draw(canvas2);
        canvas2.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
        drawLeftUp(canvas2);
        drawRightUp(canvas2);
        drawLeftDown(canvas2);
        drawRightDown(canvas2);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
        canvas.drawBitmap(bitmap, 0, 0, paint2);

        if (!TextUtils.isEmpty(mTextString)) {
            if(mTextString.length() < 4){
                Paint.FontMetricsInt fm = mTextPaint.getFontMetricsInt();
                canvas.drawText(mTextString,
                        getWidth() / 2 - mTextPaint.measureText(mTextString) / 2,
                        getHeight() / 2 - fm.descent + (fm.bottom - fm.top) / 2, mTextPaint);
            }
            else {
                StaticLayout layout = new StaticLayout(mTextString, mTextPaint, getWidth() - 30, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
                // 这里的参数300，表示字符串的长度，当满300时，就会换行，也可以使用“\r\n”来实现换行
                canvas.save();
                canvas.translate(20, 20);//从100，100开始画
                layout.draw(canvas);
                canvas.restore();//别忘了restore
            }

        }
    }

    private void drawLeftUp(Canvas canvas) {
        Path path = new Path();
        path.moveTo(0, roundHeight);
        path.lineTo(0, 0);
        path.lineTo(roundWidth, 0);
        path.arcTo(new RectF(0, 0, roundWidth * 2, roundHeight * 2), -90, -90);
        path.close();
        canvas.drawPath(path, paint);
    }

    private void drawLeftDown(Canvas canvas) {
        Path path = new Path();
        path.moveTo(0, getHeight() - roundHeight);
        path.lineTo(0, getHeight());
        path.lineTo(roundWidth, getHeight());
        path.arcTo(new RectF(0, getHeight() - roundHeight * 2,
                0 + roundWidth * 2, getHeight()), 90, 90);
        path.close();
        canvas.drawPath(path, paint);
    }

    private void drawRightDown(Canvas canvas) {
        Path path = new Path();
        path.moveTo(getWidth() - roundWidth, getHeight());
        path.lineTo(getWidth(), getHeight());
        path.lineTo(getWidth(), getHeight() - roundHeight);
        path.arcTo(new RectF(getWidth() - roundWidth * 2, getHeight()
                - roundHeight * 2, getWidth(), getHeight()), 0, 90);
        path.close();
        canvas.drawPath(path, paint);
    }

    private void drawRightUp(Canvas canvas) {
        Path path = new Path();
        path.moveTo(getWidth(), roundHeight);
        path.lineTo(getWidth(), 0);
        path.lineTo(getWidth() - roundWidth, 0);
        path.arcTo(new RectF(getWidth() - roundWidth * 2, 0, getWidth(), 0 + roundHeight * 2), -90, 90);
        path.close();
        canvas.drawPath(path, paint);
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        super.setOnClickListener(l);
        if (l != null)
            setOnClickListener(l);
    }

    public String getTextString() {
        return mTextString;
    }


    public void setText(@StringRes int TextResId) {
        setText(getResources().getString(TextResId));
    }
    public void setText(String textString)
    {
        this.mTextString=textString;
        invalidate();

    }

    public int getTextColor() {
        return mTextColor;
    }

    public void setTextColor(@ColorInt int mTextColor) {
        this.mTextColor = mTextColor;
        mTextPaint.setColor(mTextColor);
        invalidate();
    }

    public void setTextColorResource(@ColorRes int colorResource) {
        setTextColor(getResources().getColor(colorResource));
    }

    public int getTextSize() {
        return mTextSize;
    }
    public void setTextSize(int textSize)
    {
        this.mTextSize=textSize;
        mTextPaint.setTextSize(textSize);
        invalidate();
    }

    public int getTextPadding() {
        return mTextPadding;
    }

    public void setTextPadding(int mTextPadding) {
        this.mTextPadding = mTextPadding;
        invalidate();
    }


    public void setTypeface(Typeface typeface) {
        mTextPaint.setTypeface(typeface);
        invalidate();
    }
}
