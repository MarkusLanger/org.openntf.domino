/*
 *******************************************************************************
 * Copyright (C) 2009-2012, International Business Machines Corporation and    *
 * others. All Rights Reserved.                                                *
 *******************************************************************************
 */
package com.ibm.icu.impl;

import java.util.Collections;
import java.util.Map;

import com.ibm.icu.text.CurrencyDisplayNames;
import com.ibm.icu.util.ULocale;

@SuppressWarnings("deprecation")
public class CurrencyData {
	public static final CurrencyDisplayInfoProvider provider;

	public static interface CurrencyDisplayInfoProvider {
		CurrencyDisplayInfo getInstance(ULocale locale, boolean withFallback);

		boolean hasData();
	}

	public static abstract class CurrencyDisplayInfo extends CurrencyDisplayNames {
		public abstract Map<String, String> getUnitPatterns();

		public abstract CurrencyFormatInfo getFormatInfo(String isoCode);

		public abstract CurrencySpacingInfo getSpacingInfo();
	}

	public static final class CurrencyFormatInfo {
		public final String currencyPattern;
		public final char monetarySeparator;
		public final char monetaryGroupingSeparator;

		public CurrencyFormatInfo(final String currencyPattern, final char monetarySeparator, final char monetaryGroupingSeparator) {
			this.currencyPattern = currencyPattern;
			this.monetarySeparator = monetarySeparator;
			this.monetaryGroupingSeparator = monetaryGroupingSeparator;
		}
	}

	public static final class CurrencySpacingInfo {
		public final String beforeCurrencyMatch;
		public final String beforeContextMatch;
		public final String beforeInsert;
		public final String afterCurrencyMatch;
		public final String afterContextMatch;
		public final String afterInsert;

		public CurrencySpacingInfo(final String beforeCurrencyMatch, final String beforeContextMatch, final String beforeInsert,
				final String afterCurrencyMatch, final String afterContextMatch, final String afterInsert) {
			this.beforeCurrencyMatch = beforeCurrencyMatch;
			this.beforeContextMatch = beforeContextMatch;
			this.beforeInsert = beforeInsert;
			this.afterCurrencyMatch = afterCurrencyMatch;
			this.afterContextMatch = afterContextMatch;
			this.afterInsert = afterInsert;
		}

		private static final String DEFAULT_CUR_MATCH = "[:letter:]";
		private static final String DEFAULT_CTX_MATCH = "[:digit:]";
		private static final String DEFAULT_INSERT = " ";

		public static final CurrencySpacingInfo DEFAULT = new CurrencySpacingInfo(DEFAULT_CUR_MATCH, DEFAULT_CTX_MATCH, DEFAULT_INSERT,
				DEFAULT_CUR_MATCH, DEFAULT_CTX_MATCH, DEFAULT_INSERT);
	}

	static {
		CurrencyDisplayInfoProvider temp = null;
		try {
			Class<?> clzz = Class.forName("com.ibm.icu.impl.ICUCurrencyDisplayInfoProvider");
			temp = (CurrencyDisplayInfoProvider) clzz.newInstance();
		} catch (Throwable t) {
			temp = new CurrencyDisplayInfoProvider() {
				public CurrencyDisplayInfo getInstance(final ULocale locale, final boolean withFallback) {
					return DefaultInfo.getWithFallback(withFallback);
				}

				public boolean hasData() {
					return false;
				}
			};
		}
		provider = temp;
	}

	public static class DefaultInfo extends CurrencyDisplayInfo {
		private final boolean fallback;

		private DefaultInfo(final boolean fallback) {
			this.fallback = fallback;
		}

		public static final CurrencyDisplayInfo getWithFallback(final boolean fallback) {
			return fallback ? FALLBACK_INSTANCE : NO_FALLBACK_INSTANCE;
		}

		@Override
		public String getName(final String isoCode) {
			return fallback ? isoCode : null;
		}

		@Override
		public String getPluralName(final String isoCode, final String pluralType) {
			return fallback ? isoCode : null;
		}

		@Override
		public String getSymbol(final String isoCode) {
			return fallback ? isoCode : null;
		}

		@Override
		public Map<String, String> symbolMap() {
			return Collections.emptyMap();
		}

		@Override
		public Map<String, String> nameMap() {
			return Collections.emptyMap();
		}

		@Override
		public ULocale getULocale() {
			return ULocale.ROOT;
		}

		@Override
		public Map<String, String> getUnitPatterns() {
			if (fallback) {
				return Collections.emptyMap();
			}
			return null;
		}

		@Override
		public CurrencyFormatInfo getFormatInfo(final String isoCode) {
			return null;
		}

		@Override
		public CurrencySpacingInfo getSpacingInfo() {
			return fallback ? CurrencySpacingInfo.DEFAULT : null;
		}

		private static final CurrencyDisplayInfo FALLBACK_INSTANCE = new DefaultInfo(true);
		private static final CurrencyDisplayInfo NO_FALLBACK_INSTANCE = new DefaultInfo(false);
	}
}
