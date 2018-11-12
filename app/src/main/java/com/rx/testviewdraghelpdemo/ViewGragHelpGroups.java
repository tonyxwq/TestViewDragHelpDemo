package com.rx.testviewdraghelpdemo;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;


/**
 * Author:XWQ
 * Time   2018/11/8
 * Descrition: this is ViewGragHelpGroup
 */

public class ViewGragHelpGroups extends ViewGroup
{
    private ViewDragHelper viewDragHelper;

    private int currentPostion;//当前最上层的卡片

    private int beasDistance = 200;//卡片计算基准距离

    private int itemBaseDistance = 40;//每一个卡片结算基准距离

    private float baseScale = 0.9f;//缩放比例

    private int count = 18;

    private int totallCount = 5;

    private int childCount;

    private View mView;

    private boolean isReturn = false;

    private boolean isScreen = false;

    private int scrollDistance;

    public ViewGragHelpGroups(Context context)
    {
        this(context, null);
    }

    public ViewGragHelpGroups(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        viewDragHelper = viewDragHelper.create(this, 1, new ViewDragHelper.Callback()
        {
            @Override
            public boolean tryCaptureView(View child, int pointerId)
            {
                mView = child;
                return true;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy)
            {
             /*   if (top < 0)
                {
                    top = 0;

                } else if (top > (getMeasuredHeight() - child.getMeasuredHeight()))
                {
                    top = getMeasuredHeight() - child.getMeasuredHeight();
                }*/
                return top;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx)
            {
                if (left < 0)
                {
                    //限制左边界
                    left = 0;
                } else if (left > (getMeasuredWidth() - child.getMeasuredWidth()))
                {
                    //限制右边界
                    left = getMeasuredWidth() - child.getMeasuredWidth();
                }
                return left;
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel)
            {
                super.onViewReleased(releasedChild, xvel, yvel);
                if (!isReturn)
                {
                    if (scrollDistance > getMeasuredHeight() / 5)
                    {
                        viewDragHelper.smoothSlideViewTo(releasedChild, releasedChild.getLeft(), getMeasuredHeight());
                        ViewCompat.postInvalidateOnAnimation(ViewGragHelpGroups.this);
                        return;
                    }
                    int centerLeft = getMeasuredWidth() / 2 - releasedChild.getMeasuredWidth() / 2;
                    //视为在右边
                    if (releasedChild.getLeft() < centerLeft)
                    {
                        viewDragHelper.smoothSlideViewTo(releasedChild, 0, releasedChild.getTop());
                        ViewCompat.postInvalidateOnAnimation(ViewGragHelpGroups.this);

                    } else if (releasedChild.getLeft() > centerLeft)
                    {
                        viewDragHelper.smoothSlideViewTo(releasedChild, getMeasuredWidth() - releasedChild.getMeasuredWidth(), releasedChild.getTop());
                        ViewCompat.postInvalidateOnAnimation(ViewGragHelpGroups.this);
                    }

                    int centerTop = getMeasuredHeight() / 2 - releasedChild.getMeasuredHeight() / 2;
                    if (releasedChild.getTop() < centerTop)
                    {
                        viewDragHelper.smoothSlideViewTo(releasedChild, releasedChild.getLeft(), (beasDistance - currentPostion * itemBaseDistance));
                        ViewCompat.postInvalidateOnAnimation(ViewGragHelpGroups.this);

                    } else
                    {
                        viewDragHelper.smoothSlideViewTo(releasedChild, releasedChild.getLeft(), (beasDistance - currentPostion * itemBaseDistance));
                        ViewCompat.postInvalidateOnAnimation(ViewGragHelpGroups.this);
                    }
                }
            }

            @Override
            public void onEdgeTouched(int edgeFlags, int pointerId)
            {
                super.onEdgeTouched(edgeFlags, pointerId);
            }

            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId)
            {
                super.onEdgeDragStarted(edgeFlags, pointerId);
            }

            @Override
            public void onViewDragStateChanged(int state)
            {
                super.onViewDragStateChanged(state);
                switch (state)
                {
                    case 0:
                    case 2:
                        isScreen = false;
                        break;
                    case 1:
                        isScreen = true;
                        break;
                }
            }

            @Override
            public boolean onEdgeLock(int edgeFlags)
            {
                return super.onEdgeLock(edgeFlags);
            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy)
            {
                super.onViewPositionChanged(changedView, left, top, dx, dy);
                //1.计算view移动的百分比
                float fraction = changedView.getLeft() * 1f / (getMeasuredWidth() - changedView.getMeasuredWidth());
                scrollDistance = Math.abs(changedView.getTop());
                if (!isScreen)
                {
                    if (scrollDistance > getMeasuredHeight() / 5)
                    {
                        isScreen = false;
                        isReturn = true;
                        mView.animate().translationY(getMeasuredHeight()).alphaBy(1.0f).setDuration(200).start();
                        new android.os.Handler().postDelayed(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                removeView(mView);
                                requestLayout();
                                isReturn = false;
                            }
                        }, 200);
                    }
                }
            }
        });
    }

    /**
     * 执行伴随动画
     *
     * @param fraction
     */
    private void executeAnim(float fraction, View childView)
    {
        // childView.setScaleY(1+0.5f*fraction);
        // childView.setScaleX(1+0.5f*fraction);
        // childView.setRotation(360 * fraction);//围绕z轴转
        // childView.setRotationX(360 * fraction);//围绕x轴转
        // childView.setRotationY(360 * fraction);//围绕y轴转
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count = getChildCount();
        childCount = count;
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean change, int left, int top, int right, int button)
    {
        int index = totallCount - childCount;
        for (int i = 0; i < getChildCount(); i++)
        {
            View childView = getChildAt(i);
            float scalex = (((i + index) * 1.0f) / count + baseScale);
            childView.layout(0, (beasDistance - i * itemBaseDistance), childView.getMeasuredWidth(), childView.getMeasuredHeight() + (beasDistance - i * itemBaseDistance));
            //childView.setScaleX(scalex);
            childView.animate().scaleX(scalex).setDuration(500).start();
            currentPostion = i;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        //由viewDragHelper 来判断是否应该拦截此事件
        boolean flag = viewDragHelper.shouldInterceptTouchEvent(ev);
        return flag;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        //将触摸事件传给viewDragHelper来解析处理
        viewDragHelper.processTouchEvent(event);
        //消费掉此事件，自己来处理
        return true;
    }

    @Override
    public void computeScroll()
    {
        super.computeScroll();
        if (viewDragHelper.continueSettling(true))
        {
            ViewCompat.postInvalidateOnAnimation(ViewGragHelpGroups.this);
        }
    }
}
