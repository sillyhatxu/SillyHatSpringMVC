package com.sillyhat.springmvc.stripe.dto;

import java.io.Serializable;
import java.util.List;

public class SourcesDTO implements Serializable {

    private static final long serialVersionUID = 9181222411667774617L;

    /**
     * String representing the object’s type.
     * Objects of the same type share the same value.
     * Always has the value “list”.
     */
    private String object;

    /**
     * The list contains all payment sources that have been attached to the customer.
     */
    private List<CardsDTO> data;

    /**
     * True if this list has another page of items after this one that can be fetched.
     */
    private boolean hasMore;

    /**
     * The URL where this list can be accessed.
     */
    private String url;

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public List<CardsDTO> getData() {
        return data;
    }

    public void setData(List<CardsDTO> data) {
        this.data = data;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
