package com.demo.order.activity;

import com.demo.order.App;
import com.demo.order.BaseBindingActivity;
import com.demo.order.bean.Order;
import com.demo.order.databinding.ActivityOrderDetialBinding;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class OrderDetailActivity extends BaseBindingActivity<ActivityOrderDetialBinding> {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss a", Locale.ENGLISH);

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

        // Get the order detail from the intent extra
        Order detail = (Order) getIntent().getSerializableExtra("order_detail");

        // Set the UI elements using the order detail data and the viewBinder object
        viewBinder.tvSender.setText("Form sender " + App.user.username);
        viewBinder.tvStartTime.setText("Pick up time: " + dateFormat.format(Long.parseLong(detail.timestamp)));
        viewBinder.tvReceiverName.setText("To receiver: " + detail.receiverName);
        viewBinder.tvEndTime.setText("Drop off time: " + dateFormat.format(Long.parseLong(detail.timestamp) + 60 * 1000 * 60 * 2));
        viewBinder.tvWeight.setText("Weight\n" + detail.weight + "kg");
        viewBinder.tvWidth.setText("Width\n" + detail.width + "m");
        viewBinder.tvHeight.setText("Height\n" + detail.height + "m");
        viewBinder.tvLength.setText("Length\n" + detail.length + "m");
        viewBinder.tvQuantity.setText("Quantity\n26");
        viewBinder.tvType.setText("Type\n" + detail.type);
        viewBinder.face.setImageResource(detail.face);

    }
}