package property.classtest.example.com.example.classtest.transition;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import property.classtest.example.com.codeanim.R;

/**
 * Created by z on 2016/8/31.
 * 展示ListView过渡动画的适配器
 */
public class TransitionAdapter extends BaseAdapter {
    //上下文对象
    Context mcontext;

    /**
     *
     * @param context 上下文
     *                本适配器构造方法
     */
    public TransitionAdapter(Context context){
        mcontext=context;
    }
    //注意数据不宜过多，超出屏幕范围产生滚动效果不能看到，而且动画可能会失效

    @Override
    public int getCount() {
        return 8;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //未做优化
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(mcontext);
        convertView=inflater.inflate(R.layout.item_transition,null);
        return convertView;
    }
}
