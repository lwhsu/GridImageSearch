package org.lwhsu.android.gridimagesearch.models;

import java.io.Serializable;

public class ImageSearchSetting implements Serializable {
    private static final long serialVersionUID = -2495046655336101281L;

    public String size;
    public String color;
    public String type;
    public String siteFilter;

    @Override
    public String toString() {
        return "ImageSearchSetting [size=" + size + ", color=" + color + ", type=" + type
                + ", siteFilter=" + siteFilter + "]";
    }
}
