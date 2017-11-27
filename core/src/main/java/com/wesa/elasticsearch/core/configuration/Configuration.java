package com.wesa.elasticsearch.core.configuration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.knowm.dropwizard.sundial.SundialConfiguration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Configuration extends io.dropwizard.Configuration {
  @Valid
  @NotNull
  private SundialConfiguration sundial = new SundialConfiguration();

  @JsonProperty("swagger")
  public SwaggerBundleConfiguration swaggerBundleConfiguration;


  boolean autoMigrationEnabled = true;

  private String elasticsearch; 
  
  public String getElasticsearch() {
	return elasticsearch;
}

public void setElasticsearch(String elasticsearch) {
	this.elasticsearch = elasticsearch;
}

private String baseFilePath;

  private String companyImagesDirectory;

  private String geoLiteFileLocation;

  public String getBaseFilePath() {
    return baseFilePath;
  }

  public void setBaseFilePath(String baseFilePath) {
    this.baseFilePath = baseFilePath;
  }

  public String getCompanyImagesDirectory() {
	return companyImagesDirectory;
}

public void setCompanyImagesDirectory(String companyImagesDirectory) {
	this.companyImagesDirectory = companyImagesDirectory;
}

public boolean isAutoMigrationEnabled() {
    return autoMigrationEnabled;
  }

  public void setAutoMigrationEnabled(boolean autoMigrationEnabled) {
    this.autoMigrationEnabled = autoMigrationEnabled;
  }


  @JsonProperty("sundial")
  public SundialConfiguration getSundial() {
    return sundial;
  }

  public void setSundial(SundialConfiguration sundial) {
    this.sundial = sundial;
  }

  public String getGeoLiteFileLocation() {
    return geoLiteFileLocation;
  }

  public void setGeoLiteFileLocation(String geoLiteFileLocation) {
    this.geoLiteFileLocation = geoLiteFileLocation;
  }
}
