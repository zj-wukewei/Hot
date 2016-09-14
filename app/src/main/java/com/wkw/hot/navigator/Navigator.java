package com.wkw.hot.navigator;

import android.content.Context;
import android.content.Intent;
import com.wkw.hot.ui.AboutActivity;
import com.wkw.hot.ui.react.MyReactActivity;
import javax.inject.Inject;

/**
 * Created by wukewei on 16/9/14.
 */
public class Navigator {

    @Inject
    public Navigator() {

    }

    public void navigateToAbout(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, AboutActivity.class);
            context.startActivity(intent);
        }
    }
    public void navigateToMyReact(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, MyReactActivity.class);
            context.startActivity(intent);
        }
    }
}
