package nthtrung09it.android.hello.animation;

import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.fl__layout)
    FrameLayout flContainer;
    @BindView(R.id.ll_items)
    LinearLayout llItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LayoutInflater inflater = getLayoutInflater();

        for (int i = 0; i < 10; i++) {
            View view = inflater.inflate(R.layout.my_view, null);
            llItems.addView(view);

            TextView tvItemText = view.findViewById(R.id.tv__item_text);
            tvItemText.setText("Hello " + i);
        }

        llItems.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    public void onGlobalLayout() {

                        //Remove the listener before proceeding
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            llItems.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        } else {
                            llItems.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        }

                        // measure your views here
                        int totalChildren = llItems.getChildCount();
                        for (int i = 0; i < totalChildren; i++) {
                            View itemView = llItems.getChildAt(i);

                            Rect myViewRect = new Rect();
                            itemView.getGlobalVisibleRect(myViewRect);
                            float x = myViewRect.left;
                            float y = myViewRect.top;

                            Log.i("TrungNTH", i + " || Location: " + x + " | " + y);
                        }
                    }
                }
        );
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }
}
