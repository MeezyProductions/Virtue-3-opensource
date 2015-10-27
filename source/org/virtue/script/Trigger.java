package org.virtue.script;

public class Trigger {

	public static final int NONE = 0;
	public static final int OBJ = 1;
	public static final int LOC = 2;
	public static final int NPC = 3;
	public static final int PLR = 4;
	public static final int ITEM = 5;
	private String type;
	private Object object;
	private Object subject;
	private int subjectTarget;
	private int objectTarget;

	/**
	 * @param type
	 * @param subject
	 * @param object
	 * @param subjectTarget
	 * @param objectTarget
	 */
	public Trigger(String type, Object subject, Object object, int subjectTarget, int objectTarget) {
		this.type = type;
		this.subject = subject;
		this.object = object;
		this.subjectTarget = subjectTarget;
		this.objectTarget = objectTarget;
	}

	/**
	 * @param type
	 * @param subject
	 * @param object
	 */
	public Trigger(String type, Object subject, Object object) {
		this.type = type;
		this.subject = subject;
		this.object = object;
	}

	/**
	 * @param set
	 *            type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param set
	 *            object
	 */
	public void setObject(Object object) {
		this.object = object;
	}

	/**
	 * @return get Object
	 */
	public Object getObject() {
		return object;
	}

	/**
	 * @param set
	 *            subject
	 */
	public void setSubject(Object subject) {
		this.subject = subject;
	}

	/**
	 * @return get subject
	 */
	public Object getSubject() {
		return subject;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((object == null) ? 0 : object.hashCode());
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Trigger))
			return false;
		Trigger other = (Trigger) obj;
		if (object == null) {
			if (other.object != null)
				return false;
		} else if (!object.equals(other.object))
			return false;
		if (subject == null) {
			if (other.subject != null)
				return false;
		} else if (!subject.equals(other.subject))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	public int getSubjectTarget() {
		return subjectTarget;
	}

	public int getObjectTarget() {
		return objectTarget;
	}

}
