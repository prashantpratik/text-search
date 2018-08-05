package com.optus.textsearch.models;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class SearchInput implements Serializable {
    private static final long serialVersionUID = -7342608334895445157L;
    private List<String> searchText;

    public List<String> getSearchText() {
        return searchText;
    }

    public void setSearchText(List<String> searchText) {
        this.searchText = searchText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchInput input = (SearchInput) o;
        return Objects.equals(searchText, input.searchText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(searchText);
    }
}
