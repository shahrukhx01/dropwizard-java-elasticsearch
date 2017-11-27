package com.wesa.elasticsearch.search;

import com.wesa.elasticsearch.core.Application;
import com.wesa.elasticsearch.search.configuration.UsersConfiguration;
import com.wesa.elasticsearch.search.managed.StartupListener;

public class SearchApplication extends Application<UsersConfiguration, SearchGuiceModule> {

    public static void main(String[] args) throws Exception {
        new SearchApplication().run(args);
    }

    @SuppressWarnings("unchecked")
	public SearchApplication() {
        super("users", new SearchGuiceModule(), null,
            SearchResource.class);
        setManagedList(new StartupListener());
    }
}
	