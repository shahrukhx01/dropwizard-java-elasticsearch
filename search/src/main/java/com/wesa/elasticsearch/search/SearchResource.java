package com.wesa.elasticsearch.search;

import com.google.inject.Inject;
import com.wesa.elasticsearch.core.Resource;

import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Api("elasticsearch")
@Path("/elasticsearch")
@Produces(MediaType.APPLICATION_JSON)

public class SearchResource extends Resource {
	private final static Logger logger = LoggerFactory.getLogger(SearchResource.class);
	private final JestClient client;

	@Inject
	public SearchResource(JestClient jestClient) {
		this.client = jestClient;
	}

	@POST
	@Path("search/{index}")
	public String getLoggedUSerDetails(@PathParam("index") String index, String query  ) throws IOException {
		Search.Builder searchBuilder = new Search.Builder(query).addIndex(index);
		SearchResult result = client.execute(searchBuilder.build());
		return result.getJsonString();
	}

}
