package nthtrung09it.android.hello.animation;

import android.content.Intent;
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

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.fl__layout)
    FrameLayout flContainer;
    @BindView(R.id.ll_lines)
    LinearLayout llLines;
    @BindView(R.id.ll_items)
    LinearLayout llItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LayoutInflater inflater = getLayoutInflater();

        for (int i = 0; i < 10; i++) {
            ItemView view = new ItemView(this);
            llItems.addView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showSecondActivity();
                }
            });

            view.setText("Hello " + i);
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
                        int centerPosition = 0;

                        // measure your views here
                        int totalChildren = llItems.getChildCount();

                        for (int i = 0; i < totalChildren - 1; i++) {
                            ItemView firstItem = (ItemView) llItems.getChildAt(i);
                            ItemView secondItem = (ItemView) llItems.getChildAt(i + 1);

                            if (i == 0) {
                                int paddingLeft = firstItem.getPaddingLeft();

                                View radioButton = firstItem.findViewById(R.id.rb__choice);
                                centerPosition = paddingLeft + radioButton.getWidth() / 2;
                            }

                            View lineView = inflater.inflate(R.layout.item_vertical_line, null);
                            llLines.addView(lineView);

                            View line = lineView.findViewById(R.id.v__line);
                            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) line.getLayoutParams();

                            int topSpace = 0;
                            if (i == 0) {
                                topSpace = firstItem.getHeight() / 2 + firstItem.rbChoice.getHeight() / 2;
                            } else {
                                topSpace = firstItem.rbChoice.getHeight();
                            }

                            params.setMargins(centerPosition - 1, topSpace, 0, 0);

                            int height = firstItem.getHeight() + secondItem.getTopRadio() - firstItem.getBottomRadio();
                            Log.i("TRUNGNTH", "Height: " + height + " secondItem.getTopRadio(): " + secondItem.getTopRadio() + " | firstItem.getBottomRadio(): " + firstItem.getBottomRadio());
                            params.height = height;
                            line.setLayoutParams(params);
                        }
                    }
                }
        );
    }

    private void showSecondActivity() {
        Intent intent = SecondActivity.getCallingIntent(this);
        startActivity(intent);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }
}
