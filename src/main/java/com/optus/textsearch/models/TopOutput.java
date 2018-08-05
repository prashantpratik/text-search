package com.optus.textsearch.models;

import java.io.Serializable;
import java.util.Objects;

public class TopOutput implements Serializable {
    private static final long serialVersionUID = -2217056345412818346L;
    private String word;
    private int count;

    public TopOutput(String word, int count) {
        this.word = word;
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TopOutput topOutput = (TopOutput) o;
        return count == topOutput.count &&
                Objects.equals(word, topOutput.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word, count);
    }

    @Override
    public String toString() {
        return word + "|" + count;
    }
}
