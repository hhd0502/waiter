package com.hutech.waiter.screens.activity;

import static com.hutech.lib.RetrofitClient.getRetrofit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.hutech.lib.ResultModel.CategoryResultModel;
import com.hutech.lib.ResultModel.ProductsResultModel;
import com.hutech.lib.ResultModel.TableResultModel;
import com.hutech.lib.Services.CategoryService;
import com.hutech.lib.Services.ProductService;
import com.hutech.lib.entity.Order;
import com.hutech.lib.entity.ProductWrapper;
import com.hutech.lib.entity.Table;
import com.hutech.lib.presentation.ProgressDialog;
import com.hutech.waiter.R;
import com.hutech.waiter.adapter.IClickProductItem;
import com.hutech.waiter.adapter.ProductAdapter;
import com.hutech.waiter.adapter.TabCategoryAdapter;
import com.hutech.waiter.dialog.ProductOptionDialog;
import com.tuyenmonkey.mkloader.MKLoader;

import java.util.List;

import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MenuActivity extends AppCompatActivity {
    public static final String BUNDLE = "bundle";
    public static final String TABLE = "table";
    public static MenuActivity instance;

    TextView tableName;
    RecyclerView tabCategory;
    RecyclerView listProducts;
    EditText searchBar;
    TextView quantity;
    LinearLayout layoutEmpty;
    MKLoader icLoader;
    View btnOption;
    View btnBack;
    View btnViewDetail;
    TextView selectedOption;
    ImageView mDummyImgView;
    View viewDetail;


    int actionbarheight;
    public View currentItemView;

    private static Table currentTable;
    private TabCategoryAdapter categoryAdapter;
    private ProductAdapter productAdapter;
    private ProductOptionDialog dialog;
    private PopupMenu popupMenu;
    private String selectedCategory;
    private String searchKey;
    private boolean isSearching;
    private boolean isShowingPopup;

    private ProductWrapper orderedItem;
    private Order order;

    public static void start(Context context, Table table) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(TABLE, table);
        Intent intent = new Intent(context, MenuActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
        currentTable = table;
    }

    private void getViewIdAll() {
        tableName = findViewById(R.id.text_table_name_menu);

        tabCategory = findViewById(R.id.tab_category);
        tabCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        listProducts = findViewById(R.id.recycler_view);
        listProducts.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));

        searchBar = findViewById(R.id.search_bar);
        quantity = findViewById(R.id.text_quantity);
        layoutEmpty = findViewById(R.id.layout_empty);
        icLoader = findViewById(R.id.ic_loader);

        selectedOption = findViewById(R.id.selected_option);
        mDummyImgView = findViewById(R.id.img_cpy);
        viewDetail = findViewById(R.id.view_detail);
        btnOption = findViewById(R.id.btn_option);
//        btnOption.setOnClickListener(v -> {
//            if (isShowingPopup)
//                popupMenu.dismiss();
//            else
//                popupMenu.show();
//            isShowingPopup = !isShowingPopup;
//        });

        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> MenuActivity.super.onBackPressed());

        btnViewDetail = findViewById(R.id.btn_view_details);
        btnViewDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity.getText() == "0")
                {
                    showError("Vui lòng chọn ít nhất một phần để tiếp tục");
                }
                else {
                    icLoader.setVisibility(View.VISIBLE);
                    (new Handler()).postDelayed(() -> {
                        if (order == null)
                        {
                            DetailTemporaryOrderActivity.start(this, currentTable, orderedItem);
                        }
                        else
                        {
                            DetailTemporaryOrderActivity.start(this, currentTable, order, orderedItem);
                        }
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
//                        finish();
                    }, 500);

                }
            }
        });

        popupMenu = new PopupMenu(this, btnOption, R.style.BasePopupMenu);
        popupMenu.inflate(R.menu.popup_menu);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getViewIdAll();
        tableName.setText(currentTable.getName());
        getCategoryList();
        getProductList();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getViewIdAll();
        tableName.setText(currentTable.getName());
        getCategoryList();
        getProductList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getViewIdAll();
        getCategoryList();
        getProductList();
    }

    public void getProductList() {
        ProductService productService = getRetrofit().create(ProductService.class);
        new CompositeDisposable().add(productService.getAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));
    }


    private void handleResponse(ProductsResultModel productsResultModel ) {
        Log.v("menu_call-all-product","Access to server");
        List<ProductsResultModel.Data> data = productsResultModel.getData();
        productAdapter = new ProductAdapter(this, data, new IClickProductItem() {
            @Override
            public void onClick(ProductsResultModel.Data product, int position) {
                gotoDetailProduct(product);
            }

            @Override
            public void onClickToCart(ProductsResultModel.Data product, int position) {
                addToCard(product);
            }
        });

        listProducts.setAdapter(productAdapter);
    }

    private void gotoDetailProduct(ProductsResultModel.Data product) {
        DetailProductActivity.start(this, product);
    }

    private void addToCard(ProductsResultModel.Data product) {

    }


    private void handleError(Throwable throwable) {
        Log.v("menu_call-all-product","Can't Access to server");
    }

    private void getCategoryList() {
        CategoryService categoryService = getRetrofit().create(CategoryService.class);
        new CompositeDisposable().add(categoryService.getAllCategory()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));
    }

    private void handleResponse(CategoryResultModel categoryResultModel) {
        List<CategoryResultModel.Data> data = categoryResultModel.getData();
        categoryAdapter = new TabCategoryAdapter(data, this);
        tabCategory.setAdapter(categoryAdapter);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    private AlertDialog errorDialog = null;
    private ProgressDialog progressDialog = null;
    public void showError(String message) {
        try {
            if (progressDialog != null && progressDialog.isShowing())
                progressDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @OnClick(R.id.btn_option)
    public void onClickOption() {
        if (isShowingPopup)
            popupMenu.dismiss();
        else
            popupMenu.show();
        isShowingPopup = !isShowingPopup;
    }
}