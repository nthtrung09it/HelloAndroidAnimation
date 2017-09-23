package nthtrung09it.android.hello.animation;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author Kernel
 * @version 1.0
 * @since 23/09/2017
 */
public class ItemView extends LinearLayout {

    public View rbChoice;
    public TextView tvItemText;

    public ItemView(Context context) {
        super(context);

        init(context);
    }

    public ItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    private void init(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.item_view, this, true);

        setClickable(true);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setBackgroundResource(R.drawable.selectable_item_background);

        setPadding(dp2px(8), dp2px(8), dp2px(8), dp2px(8));

        rbChoice = findViewById(R.id.rb__choice);
        tvItemText = findViewById(R.id.tv__item_text);
    }

    private int dp2px(int dp) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

    }

    public int getTopRadio() {

        int totalHeight = getHeight();
        int radioHeight = rbChoice.getHeight();
        return (totalHeight - radioHeight) / 2;
    }

    public int getBottomRadio() {

        int totalHeight = getHeight();
        int radioHeight = rbChoice.getHeight();
        return totalHeight / 2 + radioHeight / 2;
    }

    public void setText(String text) {
        tvItemText.setText(text);
    }
}
