package com.gholbanet.chat.sample.repository;

import java.util.List;

import com.gholbanet.chat.sample.model.Person;

/**
 * Created by omayib on 22/09/17.
 */

public interface RepositoryTransactionListener {
    public void onLoadAlumnusSucceeded(List<Person> alumnus);
}
