package property.classtest.example.com.example.classtest.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import property.classtest.example.com.codeanim.R;
import property.classtest.example.com.example.BaseActivity;

/**
 * @author  z 2016-08-31
 * @description 布局过度动画的demo类
 */
public class Transition extends BaseActivity {
    //被添加的View容器
    LinearLayout ll_container;
    //当前被添加的按钮数量
    int  i_count;
    //自定义布局过度动画
    LayoutTransition transition=new LayoutTransition();
    //展示item的过渡动画列表
    ListView lv_transition;



    @Override
    protected int setContent() {
        return R.layout.activity_transition;
    }

    @Override
    protected void initView() {
        ll_container=(LinearLayout)findViewById(R.id.ll_container);
        lv_transition=(ListView)findViewById(R.id.lv_transition);
        lv_transition.setAdapter(new TransitionAdapter(this));


        //设置数据列表
//        setListView();//执行过渡动画
        //装载过渡动画
        setTransition();
    }

    /**
     * 代码的方式来加载过渡动画
     */
    protected void setListView() {
        //设置列表的适配器

        //加载单个item的动画
        Animation rightin= AnimationUtils.loadAnimation(this,R.anim.rightin);
        //根据单个的item的动画创建布局过度动画控制器
        LayoutAnimationController lc=new LayoutAnimationController(rightin);
        //设置动画组的延迟
        lc.setDelay(0.3f);
        //设置动画组的播放顺序
        lc.setOrder(LayoutAnimationController.ORDER_RANDOM);
        //给目标ListView设置item布局过渡动画
        lv_transition.setLayoutAnimation(lc);
    }

    @Override
    protected void setListener() {

    }
    /**
     *  向某容器中添加按钮（点击addButton后触发添加按钮事件）
     * @param view  监听的View
     */

    public  void addButton(View view){
        //数量递增
        i_count++;
        //实例化一个按钮
        Button button=new Button(this);
        //给按钮设置文本
        button.setText("button"+i_count);
        //实例化一个布局参数
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,//设置父布局高度为自适应
                ViewGroup.LayoutParams.WRAP_CONTENT);//设置父不与宽度为自适应
        //给待添加的按钮设置布局参数
        button.setLayoutParams(params);
        //添加到容器中,子view大于2个时在第二个位置加入控件，否则直接添加
        if(i_count>2){
            ll_container.addView(button,1);
        }else {
            ll_container.addView(button);
        }
    }

    /**
     * 想某容器中添加删除按钮（点击removeButton按钮后出发删除按钮事件）
     * @param view 监听的View
     */

    public  void removeButton(View view){
        //判断当前是否有足够的按钮
        if(i_count>0){
            //数量递减
            i_count--;
            //删除下标为0的控件
            ll_container.removeViewAt(0);
        }
    }

    /**
     * 设置布局的过渡动画
     */
    private  void setTransition(){
        //给指定容器设置过渡动画
        ll_container.setLayoutTransition(transition);
        //自定义添加View的动画
        //target参数填null，最终setAnimator的时候会自动设置
        ValueAnimator appearAnim= ObjectAnimator.ofFloat(null,"rotationY",90f,0);
        //设置动画的默认播放时间300ms
        appearAnim.setDuration(transition.getDuration(LayoutTransition.APPEARING));
        //给过渡动画指定动画类型和动画效果
        transition.setAnimator(LayoutTransition.APPEARING,appearAnim);

        //自定义删除View的动画
        //target参数填null，最终setAnimator的时候会自动设置
        ValueAnimator disappearAnim = ObjectAnimator.ofFloat(null, "rotationY", 0f, 90f, 0f);
        //设置一个默认的播放时间(300ms)
        disappearAnim.setDuration(transition.getDuration(LayoutTransition.DISAPPEARING));
        //给过渡动画指定动画类型和动画效果
        transition.setAnimator(LayoutTransition.DISAPPEARING, disappearAnim);

        //对于CHANGE_APPEARING 和CHANGE_DISAPPEARING 类型的过渡动画
        //1. 必须使用PropertyValuesHolder来构造动画
        //2. 必须同时指定动画的left top right bottom属性 否则无法正确的显示
        //3. 动画结束后也需要将View改为原样，否则会出现BUG
        //4. 构建ObjectAnimator时需要设置对象

        //设置CHANGE_APPEARING类型的动画
        PropertyValuesHolder left=PropertyValuesHolder.ofInt("left",0,1);
        PropertyValuesHolder top=PropertyValuesHolder.ofInt("top",0,1);
        PropertyValuesHolder right=PropertyValuesHolder.ofInt("right",0,1);
        PropertyValuesHolder bottom=PropertyValuesHolder.ofInt("bottom",0,1);
        //编写自定义的CHANGE_APPEARING动画
        PropertyValuesHolder scaleX=PropertyValuesHolder.ofFloat("scaleX",1f,2f,1f);
        //通过PropertyValuesHolder构建动画
        ValueAnimator  cgapAnim=ObjectAnimator.ofPropertyValuesHolder(ll_container,left,right,top,bottom,scaleX);
        //为view复原
        cgapAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                ((View)((ObjectAnimator)animation).getTarget()).setScaleX(1f);
            }
        });
        //给过渡动画指定动画类型和动画效果
        transition.setAnimator(LayoutTransition.CHANGE_APPEARING,cgapAnim);
        //设置动画组之间的延迟
        transition.setStagger(LayoutTransition.CHANGE_APPEARING,30);
        //这里借用了CHANGE_APPEARING
        transition.setAnimator(LayoutTransition.CHANGE_DISAPPEARING,cgapAnim);
        //设置动画组之间的延迟
        transition.setStagger(LayoutTransition.CHANGE_DISAPPEARING,30);
    }

}
