/*
 * Copyright OpenNTF 2013
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at:
 * 
 * http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or 
 * implied. See the License for the specific language governing 
 * permissions and limitations under the License.
 */
package org.openntf.domino.impl;

import java.util.Vector;

import lotus.domino.NotesException;

import org.openntf.domino.utils.DominoUtils;
import org.openntf.domino.utils.Factory;

// TODO: Auto-generated Javadoc
/**
 * The Class NotesCalendarNotice.
 */
public class NotesCalendarNotice extends Base<org.openntf.domino.NotesCalendarNotice, lotus.domino.NotesCalendarNotice> implements
		org.openntf.domino.NotesCalendarNotice {

	/**
	 * Instantiates a new notes calendar notice.
	 * 
	 * @param delegate
	 *            the delegate
	 * @param parent
	 *            the parent
	 */
	public NotesCalendarNotice(lotus.domino.NotesCalendarNotice delegate, org.openntf.domino.Base<?> parent) {
		super(delegate, parent);
	}

	/* (non-Javadoc)
	 * @see org.openntf.domino.NotesCalendarNotice#accept(java.lang.String)
	 */
	@Override
	public void accept(String comments) {
		try {
			getDelegate().accept(comments);
		} catch (NotesException e) {
			DominoUtils.handleException(e);
		}
	}

	/* (non-Javadoc)
	 * @see org.openntf.domino.NotesCalendarNotice#acceptCounter(java.lang.String)
	 */
	@Override
	public void acceptCounter(String comments) {
		try {
			getDelegate().acceptCounter(comments);
		} catch (NotesException e) {
			DominoUtils.handleException(e);
		}
	}

	/* (non-Javadoc)
	 * @see org.openntf.domino.NotesCalendarNotice#counter(java.lang.String, lotus.domino.DateTime, lotus.domino.DateTime)
	 */
	@Override
	public void counter(String comments, lotus.domino.DateTime start, lotus.domino.DateTime end) {
		try {
			getDelegate().counter(comments, (lotus.domino.DateTime) toLotus(start), (lotus.domino.DateTime) toLotus(end));
		} catch (NotesException e) {
			DominoUtils.handleException(e);
		}
	}

	/* (non-Javadoc)
	 * @see org.openntf.domino.NotesCalendarNotice#counter(java.lang.String, lotus.domino.DateTime, lotus.domino.DateTime, boolean)
	 */
	@Override
	public void counter(String comments, lotus.domino.DateTime start, lotus.domino.DateTime end, boolean keepPlaceholder) {
		try {
			getDelegate().counter(comments, (lotus.domino.DateTime) toLotus(start), (lotus.domino.DateTime) toLotus(end), keepPlaceholder);
		} catch (NotesException e) {
			DominoUtils.handleException(e);
		}
	}

	/* (non-Javadoc)
	 * @see org.openntf.domino.NotesCalendarNotice#decline(java.lang.String)
	 */
	@Override
	public void decline(String comments) {
		try {
			getDelegate().decline(comments);
		} catch (NotesException e) {
			DominoUtils.handleException(e);
		}
	}

	/* (non-Javadoc)
	 * @see org.openntf.domino.NotesCalendarNotice#decline(java.lang.String, boolean)
	 */
	@Override
	public void decline(String comments, boolean keepInformed) {
		try {
			getDelegate().decline(comments, keepInformed);
		} catch (NotesException e) {
			DominoUtils.handleException(e);
		}
	}

	/* (non-Javadoc)
	 * @see org.openntf.domino.NotesCalendarNotice#declineCounter(java.lang.String)
	 */
	@Override
	public void declineCounter(String comments) {
		try {
			getDelegate().declineCounter(comments);
		} catch (NotesException e) {
			DominoUtils.handleException(e);
		}
	}

	/* (non-Javadoc)
	 * @see org.openntf.domino.NotesCalendarNotice#delegate(java.lang.String, java.lang.String)
	 */
	@Override
	public void delegate(String commentsToOrganizer, String delegateTo) {
		try {
			getDelegate().delegate(commentsToOrganizer, delegateTo);
		} catch (NotesException e) {
			DominoUtils.handleException(e);
		}
	}

	/* (non-Javadoc)
	 * @see org.openntf.domino.NotesCalendarNotice#delegate(java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public void delegate(String commentsToOrganizer, String delegateTo, boolean keepInformed) {
		try {
			getDelegate().delegate(commentsToOrganizer, delegateTo, keepInformed);
		} catch (NotesException e) {
			DominoUtils.handleException(e);
		}
	}

	/* (non-Javadoc)
	 * @see org.openntf.domino.NotesCalendarNotice#getAsDocument()
	 */
	@Override
	public Document getAsDocument() {
		try {
			// TODO This should really come from the doc's database
			return Factory.fromLotus(getDelegate().getAsDocument(), Document.class, this.getParent());
		} catch (NotesException e) {
			DominoUtils.handleException(e);
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see org.openntf.domino.NotesCalendarNotice#getNoteID()
	 */
	@Override
	public String getNoteID() {
		try {
			return getDelegate().getNoteID();
		} catch (NotesException e) {
			DominoUtils.handleException(e);
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see org.openntf.domino.NotesCalendarNotice#getOutstandingInvitations()
	 */
	@Override
	public Vector<org.openntf.domino.NotesCalendarNotice> getOutstandingInvitations() {
		try {
			return Factory.fromLotusAsVector(getDelegate().getOutstandingInvitations(), org.openntf.domino.NotesCalendarNotice.class, this
					.getParent());
		} catch (NotesException e) {
			DominoUtils.handleException(e);
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see org.openntf.domino.impl.Base#getParent()
	 */
	@Override
	public NotesCalendar getParent() {
		return (NotesCalendar) super.getParent();
	}

	/* (non-Javadoc)
	 * @see org.openntf.domino.NotesCalendarNotice#getUNID()
	 */
	@Override
	public String getUNID() {
		try {
			return getDelegate().getUNID();
		} catch (NotesException e) {
			DominoUtils.handleException(e);
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see org.openntf.domino.NotesCalendarNotice#isOverwriteCheckEnabled()
	 */
	@Override
	public boolean isOverwriteCheckEnabled() {
		try {
			return getDelegate().isOverwriteCheckEnabled();
		} catch (NotesException e) {
			DominoUtils.handleException(e);
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see org.openntf.domino.NotesCalendarNotice#read()
	 */
	@Override
	public String read() {
		try {
			return getDelegate().read();
		} catch (NotesException e) {
			DominoUtils.handleException(e);
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see org.openntf.domino.NotesCalendarNotice#removeCancelled()
	 */
	@Override
	public void removeCancelled() {
		try {
			getDelegate().removeCancelled();
		} catch (NotesException e) {
			DominoUtils.handleException(e);
		}
	}

	/* (non-Javadoc)
	 * @see org.openntf.domino.NotesCalendarNotice#requestInfo(java.lang.String)
	 */
	@Override
	public void requestInfo(String comments) {
		try {
			getDelegate().requestInfo(comments);
		} catch (NotesException e) {
			DominoUtils.handleException(e);
		}
	}

	/* (non-Javadoc)
	 * @see org.openntf.domino.NotesCalendarNotice#sendUpdatedInfo(java.lang.String)
	 */
	@Override
	public void sendUpdatedInfo(String comments) {
		try {
			getDelegate().sendUpdatedInfo(comments);
		} catch (NotesException e) {
			DominoUtils.handleException(e);
		}
	}

	/* (non-Javadoc)
	 * @see org.openntf.domino.NotesCalendarNotice#setOverwriteCheckEnabled(boolean)
	 */
	@Override
	public void setOverwriteCheckEnabled(boolean flag) {
		try {
			getDelegate().setOverwriteCheckEnabled(flag);
		} catch (NotesException e) {
			DominoUtils.handleException(e);
		}
	}

	/* (non-Javadoc)
	 * @see org.openntf.domino.NotesCalendarNotice#tentativelyAccept(java.lang.String)
	 */
	@Override
	public void tentativelyAccept(String comments) {
		try {
			getDelegate().tentativelyAccept(comments);
		} catch (NotesException e) {
			DominoUtils.handleException(e);
		}
	}
}
