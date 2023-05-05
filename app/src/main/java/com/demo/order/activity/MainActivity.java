package com.demo.order.activity;

import androidx.annotation.Nullable;

import android.content.Intent;
import android.util.Log;
import android.view.ViewGroup;

import com.demo.order.App;
import com.demo.order.BaseBindingActivity;
import com.demo.order.adapter.BindAdapter;
import com.demo.order.bean.Car;
import com.demo.order.DBHelper;
import com.demo.order.MenuPopupWindow;
import com.demo.order.bean.Order;
import com.demo.order.databinding.ActivityMainBinding;
import com.demo.order.databinding.ItemCarBinding;
import com.demo.order.databinding.ItemOrderBinding;
import com.demo.order.databinding.PopupwindowMenuBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends BaseBindingActivity<ActivityMainBinding> {
    // Create a list of cars to show in the RecyclerView
    static List<Car> cars = new ArrayList<>();
    static {
        cars.add(new Car(App.randomGenerateReceiver(), App.randomInteger(), App.randomCarFace()));
        cars.add(new Car(App.randomGenerateReceiver(), App.randomInteger(), App.randomCarFace()));
        cars.add(new Car(App.randomGenerateReceiver(), App.randomInteger(), App.randomCarFace()));
        cars.add(new Car(App.randomGenerateReceiver(), App.randomInteger(), App.randomCarFace()));
        cars.add(new Car(App.randomGenerateReceiver(), App.randomInteger(), App.randomCarFace()));
        cars.add(new Car(App.randomGenerateReceiver(), App.randomInteger(), App.randomCarFace()));
    }
    // Create a SimpleDateFormat object to format date strings
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.ENGLISH);
    // Create an adapter to show orders in the RecyclerView
    private BindAdapter<ItemOrderBinding, Order> adapter = new BindAdapter<ItemOrderBinding, Order>() {
        // Override the createHolder method to inflate the layout for an order item
        @Override
        public ItemOrderBinding createHolder(ViewGroup parent) {
            return ItemOrderBinding.inflate(getLayoutInflater());
        }
        // Bind an order item to the view
        @Override
        public void bind(ItemOrderBinding itemOrderBinding, Order order, int position) {
            // Set the receiver name in the view
            itemOrderBinding.tvReceiverName.setText("Receiver:" + order.receiverName);
            // Format the timestamp and set it in the view
            String format = dateFormat.format(Long.parseLong(order.timestamp));
            Log.d("ssss", "bind: " + format);
            itemOrderBinding.tvTimeStart.setText("Pick up time:" + format);
            // Set the pick-up location in the view
            itemOrderBinding.tvStartLoaction.setText("Pick up location:" + order.location);
            // Set the car face image in the view
            itemOrderBinding.face.setImageResource(order.face);
            // Set a click listener to open the order detail activity
            itemOrderBinding.getRoot().setOnClickListener(view -> {
                startActivity(OrderDetailActivity.class, intent -> intent.putExtra("order_detail", order));
            });
            // Set a click listener to share the order information via text message
            itemOrderBinding.ivShare.setOnClickListener(view -> {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,order.toString());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            });
        }
    };
    // Create an adapter to show cars in the RecyclerView
    private BindAdapter<ItemCarBinding, Car> carAdapter = new BindAdapter<ItemCarBinding, Car>() {
        {
            // Add the list of cars to the adapter
            getData().addAll(cars);
        }
        // Override the createHolder method to inflate the layout for a car item

        @Override
        public ItemCarBinding createHolder(ViewGroup parent) {
            return ItemCarBinding.inflate(getLayoutInflater());
        }

        @Override
        public void bind(ItemCarBinding itemOrderBinding, Car car, int position) {
            itemOrderBinding.tvName.setText("Car owner:" + car.name);
            itemOrderBinding.tvPrice.setText("Price:" + car.price + "$/h");
            itemOrderBinding.face.setImageResource(car.face);
            itemOrderBinding.ivShare.setOnClickListener(view -> {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,car.toString());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            });
        }
    };

    @Override
    protected void initListener() {

    }




    @Override
    protected void initData() {
        viewBinder.ivAddNew.setOnClickListener(view -> {
            startActivityForResult(AddOrderStep1Activity.class, null, 100);
        });

        viewBinder.ivMenu.setOnClickListener(view -> {
            PopupwindowMenuBinding binding = PopupwindowMenuBinding.inflate(getLayoutInflater());

            MenuPopupWindow popupWindow = new MenuPopupWindow(binding.getRoot());
            popupWindow.showAsDropDown(viewBinder.ivMenu);
            binding.getRoot().setOnClickListener(view1 -> {
                popupWindow.dismiss();
            });
            binding.tvMyOrder.setOnClickListener(view1 -> {
                viewBinder.recycler.setAdapter(adapter);
                popupWindow.dismiss();
            });
            binding.home.setOnClickListener(view1 -> {
                viewBinder.recycler.setAdapter(carAdapter);
                popupWindow.dismiss();
            });

        });
        adapter.getData().addAll(DBHelper.getHelper().queryMyOrder());
        viewBinder.recycler.setAdapter(carAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            adapter.getData().clear();
            adapter.getData().addAll(DBHelper.getHelper().queryMyOrder());
            adapter.notifyDataSetChanged();
        }
    }
}