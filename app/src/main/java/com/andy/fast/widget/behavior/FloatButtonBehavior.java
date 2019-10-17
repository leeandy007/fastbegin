package com.andy.fast.widget.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * Behavior for Float Button
 * Created by wing on 11/8/16.
 */

public class FloatButtonBehavior extends BaseBehavior {

  public FloatButtonBehavior(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
    if(canInit) {
      mAnimateHelper = ScaleAnimateHelper.get(child);
      canInit = false;
    }
    return super.layoutDependsOn(parent, child, dependency);
  }


  @Override
  protected void onNestPreScrollInit(View child) {

  }
}
