package org.virtue.network.event.context.impl;

import org.virtue.network.event.context.GameEventContext;

/**
 * @author Tom
 *
 */
public class WidgetEventContext implements GameEventContext {

	private int root;
	private int component;
	private int widgetId;
	private boolean clickable;

	public WidgetEventContext(int root, int component, int widgetId, boolean clickable) {
		this.root = root;
		this.component = component;
		this.widgetId = widgetId;
		this.clickable = clickable;
	}

	public int getRoot() {
		return root;
	}

	public int getComponent() {
		return component;
	}
	
	public int getWidgetId() {
		return widgetId;
	}

	public boolean isClickable() {
		return clickable;
	}
}
