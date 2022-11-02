package com.example.mobile_assignment_2.add;

public class CustomItem {

    private String spinnerItemName;
    private int spinnerItemImage;

    public CustomItem(String spinnerItemName, int spinnerItemImage) {
        this.spinnerItemName = spinnerItemName;
        this.spinnerItemImage = spinnerItemImage;
    }

    public void setSpinnerItemImage(int spinnerItemImage) {
        this.spinnerItemImage = spinnerItemImage;
    }

    public void setSpinnerItemName(String spinnerItemName) {
        this.spinnerItemName = spinnerItemName;
    }

    public int getSpinnerItemImage() {
        return spinnerItemImage;
    }

    public String getSpinnerItemName() {
        return spinnerItemName;
    }
}
