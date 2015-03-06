/*
 * Copyright 2013
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

package org.openntf.domino.design.impl;

import java.util.logging.Logger;

/**
 * @author jgallagher
 * 
 */
public final class StyleSheet extends AbstractDesignFileResource implements org.openntf.domino.design.StyleSheet, HasMetadata {
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private static final Logger log_ = Logger.getLogger(StyleSheet.class.getName());

	@Override
	protected boolean enforceRawFormat() {
		return false;
	}

	//
	//	@Override
	//	public String getContent() {
	//		try {
	//			return new String(getFileData(), "UTF-8");
	//		} catch (UnsupportedEncodingException e) {
	//			DominoUtils.handleException(e);
	//			return null;
	//		}
	//	}
	//
	//	@Override
	//	public void setContent(final String content) {
	//		try {
	//			if (content == null) {
	//				setFileData("".getBytes("UTF-8"));
	//			} else {
	//				setFileData(content.getBytes("UTF-8"));
	//			}
	//		} catch (UnsupportedEncodingException e) {
	//			DominoUtils.handleException(e);
	//		}
	//	}

	@Override
	public String getContent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setContent(final String content) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDeployable(final boolean deployable) {
		// TODO Auto-generated method stub

	}

}