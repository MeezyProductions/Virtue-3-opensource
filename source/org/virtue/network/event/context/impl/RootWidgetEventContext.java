package org.virtue.network.event.context.impl;

import org.virtue.network.event.context.GameEventContext;

/**
 * @author Tom
 *
 */
public class RootWidgetEventContext implements GameEventContext {

	private int widgetType;

	public RootWidgetEventContext(int widgetType) {
		this.widgetType = widgetType;
	}

	public int getWidgetType() {
		return widgetType;
	}
}
