package cz.cvut.promod.gui.settings;

import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: lucky
 * Date: 20.8.2010
 * Time: 18:26:05
 */
public class SettingPageData {
    private List<SettingPageData> children = new LinkedList<SettingPageData>();
    private String name = null;
    private Icon icon = null;
    private SettingPagePanel panel = null;

    public SettingPageData(String name, Icon icon, SettingPagePanel panel) {
        this.name = name;
        this.icon = icon;
        this.panel = panel;
    }

    public SettingPageData(String name, SettingPagePanel panel) {
        this.name = name;
        this.panel = panel;
    }

    public String getName() {
        return this.name;
    }

    public Icon getIcon() {
        return this.icon;
    }

    public List<SettingPageData> getChildren() {
        return new ArrayList<SettingPageData>(this.children);
    }

    public void addChildren(SettingPageData pageInfo) {
        if (!this.children.contains(pageInfo)) {
            this.children.add(pageInfo);
        }
    }

    public SettingPagePanel getSettingPage() {
        return this.panel;
    }
}
