package cn.argentoaskia.awt.widgets.diy;

import org.omg.CORBA.ULongLongSeqHelper;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PageSelector extends Panel{
    public static void main(String[] args) {
        System.out.println("[2".matches("\\[(0|[1-9][0-9]*)\\]"));
    }

    // widget Panel
    private Panel[] widgetPanels;
    private final Map<String, Panel> widgetPanelMappings;
    private final CardLayout cardLayout;
    // 用于设置widgetPanels
    private int index;

    // 更改为CardLayout
    public PageSelector(){
        super();
        // 设置PageContainer的布局为CardLayout
        cardLayout = new CardLayout();
        setLayout(cardLayout);
        index = 0;
        widgetPanelMappings = new HashMap<>();
    }
    public PageSelector(int hgap, int vgap){
        super();
        cardLayout = new CardLayout(hgap, vgap);
        setLayout(cardLayout);
        index = 0;
        widgetPanelMappings = new HashMap<>();
    }
    private void initWidgetPanels(int panelSize){
        for (int i = 0; i < panelSize; i++) {
            Panel panel = new Panel(new BorderLayout());
            widgetPanelMappings.put(panel.getName(), panel);
            add(panel, panel.getName());
        }
    }
    private void initWidgetPanels(Component... pageComponents){
        for (int i = 0; i < pageComponents.length; i++) {
            Panel panel = new Panel(new BorderLayout());
            widgetPanelMappings.put(panel.getName(), panel);
            panel.add(pageComponents[i]);
            add(panel, panel.getName());
            index++;
        }
    }

    public PageSelector(int totalPage){
        this();
        initWidgetPanels(totalPage);
    }
    public PageSelector(int totalPage, int hgap, int vgap){
        this(hgap, vgap);
        initWidgetPanels(totalPage);
    }
    public PageSelector(Component... pageComponents){
        this();
        initWidgetPanels(pageComponents);
    }
    public PageSelector(int hgap, int vgap, Component... pageComponents){
        this(hgap, vgap);
        initWidgetPanels(pageComponents);
    }

    public int getHgap() {
        return cardLayout.getHgap();
    }
    public int getVgap(){
        return cardLayout.getVgap();
    }
    public String[] getAllPagesNames(){
        final Set<String> pageNamesSet = widgetPanelMappings.keySet();
        String[] pageNames = new String[pageNamesSet.size()];
        int index = 0;
        for (String pageName : pageNamesSet) {
            pageNames[index++] = pageName;
        }
        return pageNames;
    }

    // 用于逐个配置页面
    public boolean hasEmptyPage(){
        return index < widgetPanelMappings.size();
    }
    public Panel nextEmptyPage(){
        if (widgetPanels == null){
            widgetPanels = widgetPanelMappings.values().toArray(new Panel[0]);
        }
        if (!hasEmptyPage()){
            return null;
        }
        return widgetPanels[index++];
    }


    private boolean isNonNegativeNumber(String str){
        return str.matches("(0|[1-9][0-9]*)");
    }

    private void updateControllerPagesList(int op, String... pageNames){
        if (useDefaultController && controller != null){
            controller.updatePageList(op, pageNames);
        }
    }

    public Panel selectPage(String pageName){
        return widgetPanelMappings.get(pageName);
    }

    public void registerPage(String pageName, Component component){
        Panel newPage = new Panel();
        newPage.setLayout(new BorderLayout());
        newPage.add(component);
        if (pageName == null){
            pageName = newPage.getName();
        }
        widgetPanelMappings.put(pageName, newPage);
        add(newPage, pageName);
        index++;
        updateControllerPagesList(controller.UPDATE_FOR_ADD, pageName);
    }

    public Panel newPage(String pageName){
        Panel newPage = new Panel();
        if (pageName == null){
            pageName = newPage.getName();
        }
        // TODO: 2024/7/2 same name?
        widgetPanelMappings.put(pageName, newPage);
        add(newPage, pageName);
        index++;
        updateControllerPagesList(controller.UPDATE_FOR_ADD, pageName);
        // return panel obj for client config！
        return newPage;
    }
    public Panel newPage(){
        return newPage(null);
    }

    public Panel removePage(String pageName){
        final Panel panel = widgetPanelMappings.get(pageName);
        // page not found!
        if (panel == null){
            return null;
        }
        remove(panel);
        index--;
        updateControllerPagesList(controller.UPDATE_FOR_DELETE, pageName);
        return widgetPanelMappings.remove(pageName);
    }

    public boolean renamePage(String pageName, String newPageName){
        Panel panel = widgetPanelMappings.get(pageName);
        if (panel == null){
            return false;
        }
        // 新名字已经存在
        if (widgetPanelMappings.containsKey(newPageName)){
            return false;
        }
        panel = removePage(pageName);
        add(panel, newPageName);
        updateControllerPagesList(controller.UPDATE_FOR_RENAME, pageName, newPageName);
        widgetPanelMappings.put(newPageName, panel);
        return true;
    }

    public boolean isPageExist(String pageName){
        return widgetPanelMappings.containsKey(pageName);
    }

    public Panel replacePageComponent(String pageName, Component newComponent){
        Panel panel = removePage(pageName);
        Panel newPage = new Panel(new BorderLayout());
        newPage.add(newComponent);
        add(newPage, pageName);
        widgetPanelMappings.put(pageName, newPage);
        return panel;
    }

    // operations for page
    public void previousPage(){
        cardLayout.previous(this);
    }
    public void nextPage(){
        cardLayout.next(this);
    }

    public void firstPage(){
        cardLayout.first(this);
    }

    public void lastPage(){
        cardLayout.last(this);
    }
    public void locatePage(String pageName){
        cardLayout.show(this, pageName);
    }


    // default Controller
    private static boolean useDefaultController = false;
    private DefaultPageContainerController controller = null;
    // use default Controller
    public static Panel useDefaultController(PageSelector pageContainer){
        useDefaultController = true;
        // 互相关联
        DefaultPageContainerController controller = new DefaultPageContainerController(pageContainer);
        pageContainer.controller = controller;
        return controller;
    }

    public static class DefaultPageContainerController extends Panel{
        private Button prePageButton;
        private Button nextPageButton;
        private Button firstPageButton;
        private Button lastPageButton;
        private Choice pagesList;
        private Button jumpButton;
        private void initDefaultController(){
            setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));

            Panel buttonPanel = new Panel();
            prePageButton = new Button("previous");
            nextPageButton = new Button("next");
            firstPageButton = new Button("first");
            lastPageButton = new Button("last");
            prePageButton.setPreferredSize(new Dimension(55, 25));
            nextPageButton.setPreferredSize(new Dimension(55, 25));
            firstPageButton.setPreferredSize(new Dimension(55, 25));
            lastPageButton.setPreferredSize(new Dimension(55, 25));
            buttonPanel.add(prePageButton);
            buttonPanel.add(nextPageButton);
            buttonPanel.add(firstPageButton);
            buttonPanel.add(lastPageButton);

            Panel jumpPanel = new Panel();
            jumpButton = new Button("jump");
            jumpButton.setPreferredSize(new Dimension(55, 25));
            pagesList = new Choice();
            final String[] allPagesNames = pageContainerProxy.getAllPagesNames();
            if (allPagesNames.length != 0){
                for (String pageName :
                        allPagesNames) {
                    pagesList.add(pageName);
                }
                pagesList.select(0);
            }
            jumpPanel.add(pagesList);
            jumpPanel.add(jumpButton);

            add(buttonPanel);
            add(jumpPanel);
        }
        private void initButtonEvents(){
            prePageButton.addActionListener(e -> pageContainerProxy.previousPage());
            nextPageButton.addActionListener(e -> pageContainerProxy.nextPage());
            firstPageButton.addActionListener(e -> pageContainerProxy.firstPage());
            lastPageButton.addActionListener(e -> pageContainerProxy.lastPage());
            jumpButton.addActionListener(e -> {
                final String selectedPageName = pagesList.getSelectedItem();
                pageContainerProxy.locatePage(selectedPageName);
            });
        }
        final int UPDATE_FOR_ADD = 1;
        final int UPDATE_FOR_DELETE = -1;
        final int UPDATE_FOR_RENAME = 0;
        void updatePageList(int op, String... pageNames){
            if (op == UPDATE_FOR_ADD){
                for (String pageName :
                        pageNames) {
                    pagesList.add(pageName);
                }
            }
            if (op == UPDATE_FOR_DELETE){
                for (String pageName :
                        pageNames) {
                    pagesList.remove(pageName);
                }
            }
            if (op == UPDATE_FOR_RENAME && pageNames.length == 2){
                pagesList.remove(pageNames[0]);
                pagesList.add(pageNames[1]);
            }
        }

        private final PageSelector pageContainerProxy;
        public DefaultPageContainerController(PageSelector pageContainer){
            this.pageContainerProxy = pageContainer;
            initDefaultController();
            initButtonEvents();
        }

    }

    // default Creator
    private static boolean useDefaultCreator = false;












    // default page button
    private boolean usePageButtons;
    private Panel pagesButtonsPanel;
    private Map<String, Button> pagesButtons;
    private void initPageButtons(){

    }

}
// Page widget, 分页组件编写
