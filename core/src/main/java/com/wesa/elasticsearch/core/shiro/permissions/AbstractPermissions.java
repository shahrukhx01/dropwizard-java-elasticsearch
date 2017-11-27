package com.wesa.elasticsearch.core.shiro.permissions;

import org.apache.shiro.authz.permission.DomainPermission;

/**
 */
public abstract class AbstractPermissions extends DomainPermission {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private String action;

  public AbstractPermissions(String actions, String targets) {
    super(actions, targets);
    this.action=actions;
  }

  public String getAction() {
    return action;
  }
}