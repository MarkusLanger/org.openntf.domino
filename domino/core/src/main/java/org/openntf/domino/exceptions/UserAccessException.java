/**
 * 
 */
package org.openntf.domino.exceptions;

import java.util.logging.Logger;

/**
 * @author nfreeman
 * 
 */
public class UserAccessException extends OpenNTFNotesException {
	@SuppressWarnings("unused")
	private static final Logger log_ = Logger.getLogger(UserAccessException.class.getName());
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new UserAccessException
	 */
	public UserAccessException() {
		super();
	}

	/**
	 * Creates a new UserAccessException
	 * 
	 * @param message
	 *            the detail message. The detail message is saved for later retrieval by the Throwable.getMessage() method.
	 */
	public UserAccessException(final String message) {
		super(message);
	}

	/**
	 * Creates a new UserAccessException
	 * 
	 * @param cause
	 *            the cause (which is saved for later retrieval by the Throwable.getCause() method). (A null value is permitted, and
	 *            indicates that the cause is nonexistent or unknown.)
	 */

	public UserAccessException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Creates a new UserAccessException
	 * 
	 * @param message
	 *            the detail message. The detail message is saved for later retrieval by the Throwable.getMessage() method.
	 * @param cause
	 *            the cause (which is saved for later retrieval by the Throwable.getCause() method). (A null value is permitted, and
	 *            indicates that the cause is nonexistent or unknown.)
	 */
	public UserAccessException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
