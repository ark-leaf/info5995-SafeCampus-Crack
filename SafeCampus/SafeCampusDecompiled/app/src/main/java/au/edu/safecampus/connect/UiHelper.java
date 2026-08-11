package au.edu.safecampus.connect;

import android.app.Activity;
import android.graphics.Color;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/* JADX INFO: loaded from: classes.dex */
public class UiHelper {

    public static class Screen {
        public final LinearLayout content;
        public final ScrollView root;

        Screen(ScrollView root, LinearLayout content) {
            this.root = root;
            this.content = content;
        }
    }

    public static Screen baseScreen(Activity activity, String title) {
        ScrollView scrollView = new ScrollView(activity);
        scrollView.setFillViewport(true);
        scrollView.setBackgroundColor(Color.rgb(245, 247, 250));
        LinearLayout content = new LinearLayout(activity);
        content.setOrientation(1);
        int pad = dp(activity, 24);
        content.setPadding(pad, pad, pad, pad);
        scrollView.addView(content, new FrameLayout.LayoutParams(-1, -2));
        TextView heading = new TextView(activity);
        heading.setText(title);
        heading.setTextColor(Color.rgb(25, 49, 77));
        heading.setTextSize(28.0f);
        heading.setPadding(0, 0, 0, dp(activity, 18));
        content.addView(heading);
        return new Screen(scrollView, content);
    }

    public static TextView body(Activity activity, String text) {
        TextView view = new TextView(activity);
        view.setText(text);
        view.setTextColor(Color.rgb(29, 37, 48));
        view.setTextSize(18.0f);
        view.setPadding(0, dp(activity, 8), 0, dp(activity, 8));
        return view;
    }

    public static TextView small(Activity activity, String text) {
        TextView view = new TextView(activity);
        view.setText(text);
        view.setTextColor(Color.rgb(74, 83, 96));
        view.setTextSize(14.0f);
        view.setPadding(0, dp(activity, 6), 0, dp(activity, 12));
        return view;
    }

    public static EditText input(Activity activity, String hint) {
        EditText input = new EditText(activity);
        input.setHint(hint);
        input.setSingleLine(false);
        input.setMinLines(1);
        input.setPadding(dp(activity, 12), dp(activity, 8), dp(activity, 12), dp(activity, 8));
        input.setLayoutParams(margins(activity));
        return input;
    }

    public static Button button(Activity activity, String text) {
        Button button = new Button(activity);
        button.setText(text);
        button.setAllCaps(false);
        button.setLayoutParams(margins(activity));
        return button;
    }

    private static LinearLayout.LayoutParams margins(Activity activity) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -2);
        params.setMargins(0, dp(activity, 6), 0, dp(activity, 6));
        return params;
    }

    private static int dp(Activity activity, int value) {
        return Math.round(value * activity.getResources().getDisplayMetrics().density);
    }
}
