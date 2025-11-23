package cn.argentoaskia.awt.layout;

import java.awt.*;

public class GBC extends GridBagConstraints {

    public GBC(int gridX, int gridY){
        this.gridx = gridX;
        this.gridy = gridY;
    }

    public GBC(int gridX, int gridY,
               int gridWidth, int gridheight){
        this(gridX, gridY);
        this.gridheight = gridheight;
        this.gridwidth = gridWidth;
    }

    public GBC setAnchor(int anchor){
        this.anchor = anchor;
        return this;
    }

    public GBC setFill(int fill){
        this.fill = fill;
        return this;
    }

    public GBC setInsets(int all){
        this.insets = new Insets(all, all, all, all);
        return this;
    }

    public GBC setInsets(int topAndBottom, int leftAndRight){
        this.insets = new Insets(topAndBottom, leftAndRight, topAndBottom, leftAndRight);
        return this;
    }

    public GBC setInsets(int top, int bottom, int leftAndRight){
        this.insets = new Insets(top, leftAndRight, bottom, leftAndRight);
        return this;
    }
    public GBC setInsets(int top, int right, int bottom, int left){
        this.insets = new Insets(top, left, bottom, right);
        return this;
    }
    public GBC setWeight(double weightX, double weightY){
        this.weightx = weightX;
        this.weighty = weightY;
        return this;
    }
    public GBC setIpad(int ipadX, int ipadY){
        this.ipadx = ipadX;
        this.ipady = ipadY;
        return this;
    }

}
