package com.supcon.mes.mbap.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.supcon.common.view.base.controller.BaseViewController;
import com.supcon.common.view.base.view.BaseLinearLayout;
import com.supcon.common.view.util.DisplayUtil;
import com.supcon.mes.mbap.R;
import com.supcon.mes.mbap.adapter.CustomContactViewAdapter;
import com.supcon.mes.mbap.beans.BaseContactEntity;
import com.supcon.mes.mbap.beans.IVisitor;
import com.supcon.mes.mbap.controller.MyContactTitleController;
import com.supcon.mes.mbap.controller.MyContactViewController;
import com.supcon.mes.mbap.executor.ContactExecutor;
import com.supcon.mes.mbap.utils.ContactUtil;
import com.supcon.mes.mbap.utils.ScreenUtil;
import com.supcon.mes.mbap.utils.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.FutureTask;

public class CustomContactView<T extends BaseContactEntity> extends BaseLinearLayout {
    private ContactUtil contactUtil;
    private HorizontalScrollView horizontalScrollView;
    private LinearLayout titleView;
    private RecyclerView contentView;
    private ContactViewController contactViewController;
    private ContactViewController contactTitleController;

    private CustomContactViewAdapter adapter;
    private ProgressBar loading;
    private int selectedItem = -1;
    private OnLastStageItemClickListener onLastStageItemClickListener;
    private OnLastStageItemClickListener onMidStageItemClickListener;

    private List<DemoEntity> contactViewTitles = new ArrayList<>();

    public CustomContactView(Context context) {
        super(context);
    }

    public CustomContactView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnLastStageItemClickListener(OnLastStageItemClickListener lastStageItemClickListener) {
        this.onLastStageItemClickListener = lastStageItemClickListener;
    }

    public CustomContactView onLastStageItemClickListener(OnLastStageItemClickListener lastStageItemClickListener) {
        this.onLastStageItemClickListener = lastStageItemClickListener;
        return this;
    }

    public void setOnMidStageItemClickListener(OnLastStageItemClickListener onMidStageItemClickListener) {
        this.onMidStageItemClickListener = onMidStageItemClickListener;
    }

    public CustomContactView titleViewController(ContactViewController titleViewController) {
        this.contactTitleController = titleViewController;
        return this;
    }

    public CustomContactView contentViewController(ContactViewController contentViewController) {
        this.contactViewController = contentViewController;
        return this;
    }

    public BaseContactEntity createDemoOriginEntity() {
        DemoEntity demoEntity = new DemoEntity("总公司", "总公司", false);
        setChildEntity(demoEntity, "第一层分公司");
        for (BaseContactEntity demoEntity1 : demoEntity.subDepartment) {
            DemoEntity demoEntity2 = (DemoEntity) demoEntity1;
            setChildEntity(demoEntity2, "第二层分公司");
            for (BaseContactEntity demoEntity3 : demoEntity2.subDepartment) {
                DemoEntity demoEntity4 = (DemoEntity) demoEntity3;
                setChildEntity(demoEntity4, "第三层分公司");
            }
        }
        return demoEntity;
    }

    public void setChildEntity(DemoEntity cur, String childName) {
        for (int i = 0; i < 10; i++) {
            cur.subDepartment.add(new DemoEntity(childName + i, cur.name));
        }
    }

    @Override
    protected void initView() {
        super.initView();
        horizontalScrollView = findViewById(R.id.horizontalScrollView);
        titleView = findViewById(R.id.titleView);
        contentView = findViewById(R.id.contentView);
        loading = findViewById(R.id.loading);
        initRecyclerView();
    }

    public void refreshTitle(int pos) {
        if (selectedItem != -1) {
            contactViewTitles.get(selectedItem).isSelected = false;
            View childView = titleView.getChildAt(selectedItem);
            contactTitleController.view(childView).visit(contactViewTitles.get(selectedItem));
        }
        contactViewTitles.get(pos).isSelected = true;
        View childView = titleView.getChildAt(pos);
        contactTitleController.view(childView).visit(contactViewTitles.get(pos));
        selectedItem = pos;
    }

    public void refreshTitle() {
        titleView.removeAllViews();
        for (int i = 0; i < contactUtil.getTitleList().size(); i++) {
            View childView = LayoutInflater.from(context)
                    .inflate(contactTitleController.layout(), null, false);
            contactTitleController.view(childView).isLast(i == contactUtil.getTitleList().size() - 1).visit(contactUtil.getTitleList().get(i));
            int finalI = i;
            childView.setOnClickListener(v -> {
                contactUtil.clicked(contactUtil.getTitleList().get(finalI));
                refreshTitle();
                adapter.setList(contactUtil.getLast().subDepartment);
            });
            titleView.addView(childView);
        }
    }

    public void setContentValue(BaseContactEntity baseContactEntity) {
        contactUtil = new ContactUtil(baseContactEntity);
    }

    public CustomContactView rootContentEntity(BaseContactEntity baseContactEntity) {
        initContent(baseContactEntity);

        return this;
    }

    public CustomContactView rootSeparatedContentEntity(BaseContactEntity baseContactEntity) {
        initSeparatedContent(baseContactEntity);

        return this;
    }

    private void initSeparatedContent(BaseContactEntity baseContactEntity) {
        if (contactTitleController == null) {
            contactTitleController = new MyContactTitleController(new View(context));
        }
        if (contactViewController == null) {
            contactViewController = new MyContactViewController(new View(context));
        }
        this.contactUtil = new ContactUtil(baseContactEntity);
        titleView.removeAllViews();
        View view = LayoutInflater.from(context).inflate(contactTitleController.layout(), null, false);
        contactTitleController.view(view).visit(baseContactEntity);
        view.setOnClickListener(v -> contactUtil.clicked(baseContactEntity));
        titleView.addView(view);
        if (adapter == null) {
            adapter = new CustomContactViewAdapter(context, baseContactEntity.subDepartment);
            adapter.registerViewController(contactViewController);
            adapter.setOnItemChildViewClickListener((childView, position, action, obj) -> {
                BaseContactEntity baseContactEntity1 = (BaseContactEntity) obj;
                if (action == 0 || (baseContactEntity1.subDepartment == null || baseContactEntity1.subDepartment.size() <= 0)) {
                    if (onLastStageItemClickListener != null)
                        onLastStageItemClickListener.onClick(baseContactEntity1);
                    return;
                }
                if (onMidStageItemClickListener != null && action == 1) {
                    onMidStageItemClickListener.onClick(baseContactEntity1);
                }
                contactUtil.clicked(baseContactEntity1);
                refreshTitle();
                adapter.setList(contactUtil.getLast().subDepartment);
            });
            contentView.setAdapter(adapter);
        } else {
            adapter.setList(baseContactEntity.subDepartment);
        }
    }

    public CustomContactView rootContentEntity(BaseContactEntity baseContactEntity, ContactViewController contactTitleController, ContactViewController contactListController) {
        this.contactTitleController = contactTitleController;
        this.contactViewController = contactListController;

        initContent(baseContactEntity);

        return this;
    }

    private void initContent(BaseContactEntity baseContactEntity) {
        if (contactTitleController == null) {
            contactTitleController = new MyContactTitleController(new View(context));
        }
        if (contactViewController == null) {
            contactViewController = new MyContactViewController(new View(context));
        }
        this.contactUtil = new ContactUtil(baseContactEntity);
        titleView.removeAllViews();
        View view = LayoutInflater.from(context).inflate(contactTitleController.layout(), null, false);
        contactTitleController.view(view).visit(baseContactEntity);
        view.setOnClickListener(v -> contactUtil.clicked(baseContactEntity));
        titleView.addView(view);
        if (adapter == null) {
            adapter = new CustomContactViewAdapter(context, baseContactEntity.subDepartment);
            adapter.registerViewController(contactViewController);
            adapter.setOnItemChildViewClickListener((childView, position, action, obj) -> {
                BaseContactEntity baseContactEntity1 = (BaseContactEntity) obj;
                if (baseContactEntity1.subDepartment == null || baseContactEntity1.subDepartment.size() <= 0) {
                    if (onLastStageItemClickListener != null)
                        onLastStageItemClickListener.onClick(baseContactEntity1);
                    return;
                }
                if (onMidStageItemClickListener != null) {
                    onMidStageItemClickListener.onClick(baseContactEntity1);
                }
                contactUtil.clicked(baseContactEntity1);
                refreshTitle();
                adapter.setList(contactUtil.getLast().subDepartment);
            });
            contentView.setAdapter(adapter);
        } else {
            adapter.setList(baseContactEntity.subDepartment);
        }
    }


    public void setContactViewTitles(List<DemoEntity> list, ContactViewController contactViewController) {
        titleView.removeAllViews();
        this.contactTitleController = contactViewController;
        contactViewTitles = list;
        for (int i = 0; i < list.size(); i++) {
            View view = LayoutInflater.from(context).inflate(contactViewController.layout(), null, false);
            int finalI = i;
            view.setOnClickListener(new OnClickListener() {
                FutureTask<Boolean> futureTask;

                @Override
                public void onClick(View v) {
                    refreshTitle(finalI);
                    loading.setVisibility(VISIBLE);
                    if (futureTask != null && !futureTask.isDone()) {
                        futureTask.cancel(true);
                    }
                    if (futureTask == null || futureTask.isDone() || futureTask.isCancelled()) {
                        futureTask = ContactExecutor.getInstance().runWorker(() -> {
                            ContactExecutor.getInstance().runUI(() -> loading.setVisibility(GONE));
                            return null;
                        });
                    }
                }
            });
            contactViewController.view(view).visit(list.get(i));
            titleView.addView(view);
        }
    }

    public void setContactViewController(ContactViewController contactViewController) {
        this.contactViewController = contactViewController;
        adapter.registerViewController(contactViewController);
    }


    public void setAdapter(CustomContactViewAdapter adapter) {
        this.adapter = adapter;
        contentView.setAdapter(adapter);
    }

    private void initRecyclerView() {
        contentView.setLayoutManager(new LinearLayoutManager(context, VERTICAL, false));
        contentView.addItemDecoration(new SpaceItemDecoration(DisplayUtil.dip2px(5, context)));
    }

    @Override
    protected int layoutId() {
        return R.layout.ly_custom_contact;
    }

    public static class DemoEntity extends BaseContactEntity {
        public String name;
        public String info;
        public String code;

        public DemoEntity(String name, String info) {
            this.name = name;
            this.info = info;
        }

        public DemoEntity(String name, String info, Boolean special1) {
            this(name, info);
            this.isLast = special1;
        }

        public void setSpecial2(Boolean special2) {
            this.isSelected = special2;
        }
    }

    public static abstract class ContactViewController<MODEL extends BaseContactEntity> extends BaseViewController implements IVisitor<MODEL> {
        protected View rootView;
        protected Boolean isLast;
        protected Boolean isClicked;

        public View rightView() {
            return null;
        }

        protected abstract void findViewById(View rootView);

        public ContactViewController(View rootView) {
            super(rootView);
            this.rootView = rootView;
        }

        public ContactViewController view(View view) {
            this.rootView = view;
            findViewById(view);
            return this;
        }

        protected ContactViewController isLast(Boolean isLast) {
            this.isLast = isLast;
            return this;
        }

        protected ContactViewController isClicked(Boolean isClicked) {
            this.isClicked = isClicked;
            return this;
        }

        public View view() {
            return rootView;
        }

        public abstract int layout();
    }

    public interface OnLastStageItemClickListener {
        void onClick(BaseContactEntity baseContactEntity);
    }
}
