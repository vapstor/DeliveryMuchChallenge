package br.com.dmchallenge.model;

import java.util.ArrayList;

public class RepoResponse {
    private Integer count;
    private String next, previous;
    private ArrayList<Repo> results;

    public RepoResponse(Integer count, String next, String previous, ArrayList<Repo> results) {
        this.count = count;
        this.next = next;
        this.previous = previous;
        this.results = results;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public ArrayList<Repo> getResults() {
        return results;
    }

    public void setResults(ArrayList<Repo> results) {
        this.results = results;
    }
}


